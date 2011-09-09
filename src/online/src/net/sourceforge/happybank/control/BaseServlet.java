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

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.happybank.facade.BankingFacade;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Base Servlet Action Class.
 * 
 * @author Kevin A. Lee
 * @email kevin.lee@buildmeister.com
 */
public abstract class BaseServlet extends HttpServlet {
    
    /**
     * Generated serialization identifier.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * The Bank facade.
     */
    private BankingFacade bank = null;
    
    /**
     * Get the Bank facade.
     * @return the Bank facade
     */
    protected BankingFacade getBank() {
        return this.bank;
    } // getBank
    
    /**
     * Initialise spring application context and facade.
     * @param config servlet config
     * @throws ServletException on error
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
        // load Spring framework Web context
        WebApplicationContext wac = WebApplicationContextUtils
                .getRequiredWebApplicationContext(config.getServletContext());
        // set banking facade
        bank = (BankingFacade) wac.getBean("bankManager");
    } // init
    
    /**
     * Forward to get request.
     * 
     * @param request the request
     * @param response the response
     * @throws ServletException on servlet failure
     * @throws IOException on IO failure
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        performTask(request, response);
    } // doGet
    
    /**
     * Forward to post request.
     * 
     * @param request the request
     * @param response the response
     * @throws ServletException on servlet failure
     * @throws IOException on IO failure
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        performTask(request, response);
    } // doPost
    
    /**
     * Perform a task.
     * 
     * @param request the request
     * @param response the response
     * @throws ServletException on servlet failure
     * @throws IOException on IO failure
     */
    protected void performTask(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // implement me!!!
    } // performTask    
    
} // BaseServlet
