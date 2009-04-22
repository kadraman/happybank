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
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sourceforge.happybank.exception.InvalidAmountException;
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
    protected void performTask(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            // parameters:
            
            HttpSession session = request.getSession(false);
            String accountID1 = (String) session.getAttribute("accountNumber");
            String accountID2 = (String) request.getParameter(
                    "destinationAccount").toString();
            BigDecimal amount = new BigDecimal(request.getParameter("amount"));
            
            // control logic:
            
            if (amount.equals(new BigDecimal(0.00))) {
                throw new InvalidAmountException("Invalid amount...");
            }
            
            getBank().transfer(accountID1, accountID2, amount);
            Account account = getBank().getAccount(accountID1);
            
            // response:
            
            request.setAttribute("account", account);
            
            // call the presentation renderer
            
            getServletContext().getRequestDispatcher("/accountDetails.jsp")
                    .forward(request, response);
            
        } catch (Exception ex) {
            request.setAttribute("message", ex.getMessage());
            request.setAttribute("forward", "index.jsp");
            getServletContext().getRequestDispatcher("/showException.jsp")
                    .forward(request, response);
        }
    } // performTask
    
} // TransferFunds
