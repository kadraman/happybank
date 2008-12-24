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

import net.sourceforge.happybank.exception.CustomerDoesNotExistException;
import net.sourceforge.happybank.facade.BankingFacade;
import net.sourceforge.happybank.model.Account;
import net.sourceforge.happybank.model.Customer;

/**
 * Validates the customers login details.
 *
 * @author Kevin A. Lee
 * @email kevin.lee@buildmeister.com
 */
public class ValidateLogin extends HttpServlet {

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
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        performTask(request, response);
    }

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
    }

    /**
     * Validate the login details.
     *
     * @param request
     *            the request
     * @param response
     *            the response
     * @throws ServletException
     * @throws IOException
     *
     */
    public void performTask(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Parameters

            // Get input parameter and keep it on the HTTP session

            String customerUsername = request.getParameter("customerUsername");
            String customerPassword = request.getParameter("customerPassword");
            HttpSession session = request.getSession();

            if (customerUsername == null) {
                customerUsername = (String) session
                        .getAttribute("customerUsername");
            } else {
                session.setAttribute("customerUsername", customerUsername);
            }

            if (customerPassword == null) {
                customerPassword = (String) session
                        .getAttribute("customerPassword");
            } else {
                session.setAttribute("customerPassword", customerUsername);
            }

            // Control logic
            // Create the new banking façade
            BankingFacade banking = new BankingFacade();

            // Check customer login details
            Customer customer = null;
            try {
                customer = banking.getCustomerByUsername(customerUsername);
            } catch (CustomerDoesNotExistException ex) {
                request.setAttribute("message", "invalid username");
                getServletContext().getRequestDispatcher("/index.jsp").forward(
                        request, response);
            }

            if (customer != null) {
                if (customerPassword.equals(customer.getPassword())) {
                    // Retrieve customer accounts
                    Account[] accounts = banking.getAccounts(customer.getId());

                    // Response
                    // Set the request attributes for future rendering
                    request.setAttribute("customer", customer);
                    request.setAttribute("accounts", accounts);
                    request.setAttribute("message", null);
                    session.setAttribute("customerNumber", customer.getId());

                    // Call the presentation renderer
                    getServletContext().getRequestDispatcher(
                            "/listAccounts.jsp").forward(request, response);
                } else {
                    request.setAttribute("message", "invalid password");
                    getServletContext().getRequestDispatcher("/index.jsp")
                            .forward(request, response);
                }
            }
        } catch (Exception e) {
            request.setAttribute("message", e.getMessage());
            request.setAttribute("forward", "index.jsp");
            getServletContext().getRequestDispatcher("/showException.jsp")
                    .forward(request, response);
        }
    } // performTask

} // ValidateLogin
