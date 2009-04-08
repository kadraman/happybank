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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Generic Command interface.
 *
 * @author Kevin A. Lee
 * @email kevin.lee@buildmeister.com
 */
public interface Command {
    /**
     * Execute the command.
     *
     * @param request
     *            the request
     * @param response
     *            the response
     * @return string containing page to forward to.
     * @throws Exception on failure
     *
     */
    String execute(HttpServletRequest request, HttpServletResponse response)
            throws Exception;
} // Command
