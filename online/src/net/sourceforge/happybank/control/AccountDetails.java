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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sourceforge.happybank.model.Account;

/**
 * Lists the given account's details and lets the user perform transactions.
 * 
 * @author Kevin A. Lee
 * @email kevin.lee@buildmeister.com
 */
public class AccountDetails extends BaseServlet {
    /**
     * Generated serialization identifier.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Gets the details of the account and places it in the current session.
     * 
     * @param request
     *            the request
     * @param response
     *            the response
     * @throws ServletException on servlet failure
     * @throws IOException on IO failure
     */
    protected void performTask(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            // parameters:
            
            // get input parameter and keep it on the HTTP session
            // if parameter was not passed, try to find it in the session
            
            String accountNumber = request.getParameter("accountNumber");
            
            HttpSession session = request.getSession();
            
            if (accountNumber == null) {
                accountNumber = (String) session.getAttribute("accountNumber");
            } else {
                session.setAttribute("accountNumber", accountNumber);
            }
                              
            Account account = getBank().getAccount(accountNumber);
            
            // response:
            
            // set the request attributes for future rendering
            
            request.setAttribute("account", account);
            
            // call the presentation renderer
            
            getServletContext().getRequestDispatcher("/accountDetails.jsp")
                    .forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("message", ex.getMessage());
            request.setAttribute("forward", "ListAccounts");
            
            getServletContext().getRequestDispatcher("/showException.jsp")
                    .forward(request, response);
        }
    } // performTask
    
} // AccountDetails
