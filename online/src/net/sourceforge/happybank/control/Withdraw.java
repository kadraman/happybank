/*
 * Copyright 2005-2008 Kevin A. Lee
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

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sourceforge.happybank.exception.InvalidAmountException;
import net.sourceforge.happybank.facade.BankingFacade;
import net.sourceforge.happybank.model.Account;

/**
 * Performs a withdrawal on the specified account.
 *
 * @author Kevin A. Lee
 * @email kevin.lee@buildmeister.com
 */
public class Withdraw implements Command {
    /**
     * Withdraw the funds.
     *
     * @param request
     *            the request
     * @param response
     *            the response
     * @return string containing page to forward to.
     * @throws Exception on failure
     *
     */
    public String execute(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        // Parameters

        HttpSession session = request.getSession(false);

        String accountNumber = (String) session.getAttribute("accountNumber");
        BigDecimal amount = new BigDecimal(request.getParameter("amount"));

        // Control logic

        if (amount.equals(new BigDecimal(0.00))) {
            throw new InvalidAmountException("Invalid Amount...");
        }

        BankingFacade banking = new BankingFacade();

        banking.withdraw(accountNumber, amount);
        Account account = banking.getAccount(accountNumber);

        // Response

        request.setAttribute("account", account);

        return "/accountDetails.jsp";
    } // execute

} // Withdraw
