/*
 * Copyright 2005-2009 Kevin A. Lee
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */


package net.sourceforge.happybank.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import net.sourceforge.happybank.exception.CustomerDoesNotExistException;
import net.sourceforge.happybank.model.Account;
import net.sourceforge.happybank.model.Customer;

/**
 * Validates the customers login details.
 * 
 * @author Kevin A. Lee
 * @email kevin.lee@buildmeister.com
 */
public class ValidateLogin extends BaseServlet {
    
    /**
     * Generated serialization identifier.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Validate the login details.
     * 
     * @param request the request
     * @param response the response
     * @throws ServletException on servlet failure
     * @throws IOException on IO failure
     */
    @SuppressWarnings("unchecked")
    protected void performTask(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            // parameters:
            
            // get input parameters and keep them on the HTTP session
            String customerUsername = request.getParameter("user");
            String customerPassword = request.getParameter("pass");
            HttpSession session = request.getSession();
            
            if (customerUsername == null) {
                customerUsername = (String) session.getAttribute("user");
            } else {
                session.setAttribute("user", customerUsername);
            }
            
            if (customerPassword == null) {
                customerPassword = (String) session.getAttribute("pass");
            } else {
                session.setAttribute("pass", customerUsername);
            }
            
            // set response type and create JSON object
            response.setContentType("application/json");
            PrintWriter rOut = response.getWriter();
            JSONObject jObj = new JSONObject();
            jObj.put("code", new Integer(0)); // assume failure
            
            // control logic:
            
            // check customer login details
            Customer customer = null;
            try {
                customer = getBank().getCustomerByUsername(customerUsername);
            } catch (CustomerDoesNotExistException ex) {
                jObj.put("message", "The <b>username</b> is not recognized.");
                jObj.put("field", "user");
                rOut.println(jObj);
                rOut.close();                
            }
            
            if (customer != null) {
                if (customerPassword.equals(customer.getPassword())) {
                    // valid login, retrieve customer accounts
                    List<Account> accounts = getBank().getAccounts(
                            customer.getId());                   
                    
                    // set session attributes for future rendering
                    session.setAttribute("customer", customer);
                    session.setAttribute("accounts", accounts);
                    session.setAttribute("customerNumber", customer.getId());
                    
                    jObj.put("code", new Integer(1)); // success
                    jObj.put("cid", customer.getId());
                    rOut.println(jObj);
                    rOut.close();                    
                } else {
                    jObj.put("message",
                    "The <b>password</b> is incorrect for the specified user.");
                    jObj.put("field", "pass");
                    rOut.println(jObj);
                    rOut.close();
                }
            }
        } catch (Exception ex) {
            response.setContentType("application/json");
            PrintWriter rOut = response.getWriter();
            JSONObject jObj = new JSONObject();
            jObj.put("code", new Integer(0));
            jObj.put("message", ex.getMessage());
            rOut.println(jObj);
            rOut.close();
        }
    } // performTask
    
} // ValidateLogin
