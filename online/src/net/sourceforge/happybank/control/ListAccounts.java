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
import net.sourceforge.happybank.model.Customer;


/**
 * list the customers accounts
 * 
 * @author 
 */
public class ListAccounts extends HttpServlet {
	private static final long serialVersionUID = 2479265177904294399L;

	// add logging attributes

	/*
	 * @see javax.servlet.http.HttpServlet#void
	 *      (javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		performTask(req, resp);
	}

	/*
	 * @see javax.servlet.http.HttpServlet#void
	 *      (javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		performTask(req, resp);
	}

	public void performTask(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			// Parameters
			// Get input parameter and keep it on the HTTP session
			String customerNumber = req.getParameter("customerNumber");
			HttpSession session = req.getSession();

			if (customerNumber == null) {
				customerNumber = (String) session
						.getAttribute("customerNumber");
			} else {
				session.setAttribute("customerNumber", customerNumber);
			}

			// Control logic
			// Create the new banking façade
			BankingFacade banking = new BankingFacade();

			// Retrieve customer and related accounts
			Customer customer = banking.getCustomer(customerNumber);
			Account[] accounts = banking.getAccounts(customerNumber);

			// Response
			// Set the request attributes for future rendering
			req.setAttribute("customer", customer);
			req.setAttribute("accounts", accounts);

			// Call the presentation renderer
			getServletContext().getRequestDispatcher("/listAccounts.jsp")
					.forward(req, resp);

		} catch (Exception e) {
			req.setAttribute("message", e.getMessage());
			req.setAttribute("forward", "index.jsp");
			getServletContext().getRequestDispatcher("/showException.jsp")
					.forward(req, resp);
		}
	}
}
