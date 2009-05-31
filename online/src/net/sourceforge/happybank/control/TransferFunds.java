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
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import net.sourceforge.happybank.model.Account;

/**
 * Transfers the given amount of money from the source account to the
 * destination one.
 * 
 * @author Kevin A. Lee
 * @email kevin.lee@buildmeister.com
 */
public class TransferFunds extends BaseServlet {
    
    /**
     * Generated serialization identifier.
     */
    private static final long serialVersionUID = 1L;   
    
    /**
     * Transfer the funds.
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
            
            HttpSession session = request.getSession(false);
            String accountID1 = (String) session.getAttribute("accountNumber");
            String accountID2 = (String) request.getParameter(
                    "destinationAccount").toString();
            
            // get all of the customers accounts
            String customerId = (String) session.getAttribute("customerNumber");
            List<Account> accounts = getBank().getAccounts(customerId);
            
            // get the amount to transfer
            BigDecimal amount = new BigDecimal(request.getParameter("amount"));
            
            // set response type and create JSON object
            response.setContentType("application/json");
            PrintWriter rOut = response.getWriter();
            JSONObject jObj = new JSONObject();
            jObj.put("code", new Integer(0)); // assume failure
            
            // control logic:
            
            if (amount.equals(new BigDecimal(0.00))) {
                jObj.put("message", "A valid <b>amount</b> is required.");
                jObj.put("field", "amount");
                rOut.println(jObj);
                rOut.close();
            }
            
            getBank().transfer(accountID1, accountID2, amount);
            
            jObj.put("code", new Integer(1)); // success
            rOut.println(jObj);
            rOut.close();            
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
    
} // TransferFunds
