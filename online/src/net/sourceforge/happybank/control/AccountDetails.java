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

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sourceforge.happybank.facade.BankingFacade;
import net.sourceforge.happybank.model.Account;

/**
 * Lists the given account's details and lets the user perform transactions.
 *
 * @author Kevin A. Lee
 * @email kevin.lee@buildmeister.com
 */
public class AccountDetails extends HttpServlet {
    /**
     * Generated serialization identifier.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Forward to get request.
     *
     * @param request
     *            the request
     * @param response
     *            the response
     * @throws SevletException
     * @throws IOException
     *
     */
    public final void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        performTask(request, response);
    } // doGet

    /**
     * Forward to post request.
     *
     * @param request
     *            the request
     * @param response
     *            the response
     * @throws SevletException
     * @throws IOException
     *
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        performTask(request, response);
    } // doPost

    /**
     * Gets the details of the account and places it in the current session.
     *
     * @param request
     *            the request
     * @param response
     *            the response
     * @throws SevletException
     * @throws IOException
     *
     */
    public void performTask(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            // Parameters

            // Get input parameter and keep it on the HTTP session
            // If parameter was not passed, try to find it in the session

            String accountNumber = request.getParameter("accountNumber");

            HttpSession session = request.getSession();

            if (accountNumber == null) {
                accountNumber = (String) session.getAttribute("accountNumber");
            } else {
                session.setAttribute("accountNumber", accountNumber);
            }

            // Control logic

            // Create the new banking façade

            BankingFacade banking = new BankingFacade();

            Account account = banking.getAccount(accountNumber);

            // Response

            // Set the request attributes for future rendering

            request.setAttribute("account", account);

            // Call the presentation renderer

            getServletContext().getRequestDispatcher("/accountDetails.jsp")
                    .forward(request, response);
        } catch (Exception e) {
            request.setAttribute("message", e.getMessage());
            request.setAttribute("forward", "ListAccounts");

            getServletContext().getRequestDispatcher("/showException.jsp")
                    .forward(request, response);
        }
    } // performTask

} // AccountDetails
