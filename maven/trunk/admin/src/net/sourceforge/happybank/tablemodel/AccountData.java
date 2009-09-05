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

package net.sourceforge.happybank.tablemodel;

import java.math.BigDecimal;

/**
 * Encapsulation of an account file for table representation.
 * 
 * @author Kevin A. Lee
 * @email kevin.lee@buildmeister.com
 */
public class AccountData {

	public String cId;
	public String cType;
	public BigDecimal cBalance;
	public String cFirstName;
	public String cLastName;
	public String cCustomerId;

	/**
	 * default constructor - no parameters
	 * 
	 */
	public AccountData() {
		cId = "";
		cType = "";
		cBalance = new BigDecimal(0);
		cFirstName = "";
		cLastName = "";
		cCustomerId = "";
	} // AccountData

	/**
	 * parameterised constructor
	 * 
	 * @param id
	 *            account id
	 * @param type
	 *            type of account - current or savings
	 * @param balance -
	 *            balance of account
	 * @param firstName -
	 *            customer's first name
	 * @param lastName -
	 *            customer's last name
	 */
	public AccountData(String id, String type, BigDecimal balance,
			String firstName, String lastName, String customerId) {
		cId = id;
		cType = type;
		cBalance = balance;
		cFirstName = firstName;
		cLastName = lastName;
		cCustomerId = customerId;
	} // AccountData

} // AccountData
