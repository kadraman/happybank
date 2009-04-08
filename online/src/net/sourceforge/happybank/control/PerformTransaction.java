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

/**
 * Performs the selected transaction on the customer's account.
 * 
 * @author Kevin A. Lee
 * @email kevin.lee@buildmeister.com
 */
public class PerformTransaction extends HttpServlet {
    /**
     * Generated serialization identifier.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Perform a transaction on the bank.
     *
     * @param request
     *            the request
     * @param response
     *            the response
     * @throws ServletException on servlet failure
     * @throws IOException on IO failure
     *
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Create a new command object of the given type

            Command command = (Command) Class.forName(
                    "net.sourceforge.happybank.control."
                            + request.getParameter("transaction"))
                    .newInstance();

            // Execute the command and find out the page
            // responsible for the response rendering

            String forward = command.execute(request, response);

            // Forward the request to the response renderer

            getServletContext().getRequestDispatcher(forward).forward(request,
                    response);
        } catch (Exception e) {
            request.setAttribute("message", e.getMessage());
            request.setAttribute("forward", "AccountDetails");

            getServletContext().getRequestDispatcher("/showException.jsp")
                    .forward(request, response);
        }
    } // doPost
    
} // PerformTransaction
