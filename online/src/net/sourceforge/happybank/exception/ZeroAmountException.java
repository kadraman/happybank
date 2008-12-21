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

package net.sourceforge.happybank.exception;

/**
 * This exception is thrown when the user types in an invalid currency amount of
 * zero when performing a transaction.
 * 
 * @author
 */
public class ZeroAmountException extends BankException {

	private static final long serialVersionUID = 1L;

	/**
	 * default constructor
	 * 
	 * @param s
	 *            title of the exception
	 */
	public ZeroAmountException(String s) {
		super(s);
	}

	/**
	 * return the key of the exception
	 * 
	 * @return string containing the exception key
	 */
	public String getMessageKey() {
		return "error.zeroAmountException";
	} // getMessageKey

} // ZeroAmountException
