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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Invalidates the user HTTP session and restarts the application.
 *
 * @author Kevin A. Lee
 * @email kevin.lee@buildmeister.com
 */
public class LogoutSession extends HttpServlet {
    /**
     * Generated serialization identifier.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Invalidate the session.
     *
     * @param request
     *            the request
     * @param response
     *            the response
     * @throws ServletException on servlet failure
     * @throws IOException on IO failure
     *
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // invalidate the current http session, if one exists

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        // restarts the application

        getServletContext().getRequestDispatcher("/index.jsp").forward(request,
                response);
    } // doGet

} // LogoutSession
