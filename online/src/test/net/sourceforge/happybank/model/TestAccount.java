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

package test.net.sourceforge.happybank.model;

import java.math.BigDecimal;

import net.sourceforge.happybank.exception.InsufficientFundsException;
import net.sourceforge.happybank.model.Account;

import junit.framework.TestCase;


/**
 * JUnit test for account class
 * 
 * @author
 */
public class TestAccount extends TestCase {

	private Account a1;

	/**
	 * constructor for TestAccount
	 * 
	 * @param arg0
	 */
	public TestAccount(String arg0) {
		super(arg0);
	}

	protected void setUp() {
		// create some customers
		a1 = new Account("101-1001");
		a1.setType("Current");
		a1.setBalance(new BigDecimal("100.25"));
	} // setUp

	public void testGetters() {
		assertTrue(!a1.equals(null));
		BigDecimal balance = new BigDecimal("100.25");
		assertEquals(a1.getId(), "101-1001");
		assertEquals(a1.getType(), "Current");
		assertEquals(a1.getBalance(), balance);
		a1.setId("101-1002");
		assertEquals(a1.getId(), "101-1002");
	} // testGetters

	public void testDeposit() {
		BigDecimal balance = new BigDecimal("150.50");
		assertEquals(a1.deposit(new BigDecimal("50.25")), balance);
	} // testDeposit

	public void testWithdraw() {
		BigDecimal balance = new BigDecimal("70.01");
		try {
			assertEquals(a1.withdraw(new BigDecimal("30.24")), balance);
		} catch (InsufficientFundsException ex) {
			ex.printStackTrace();
		}
		try {
			// withdraw too much
			a1.withdraw(new BigDecimal("10000.00"));
			fail("witdhraw hasn't caused an exception when it should");
		} catch (InsufficientFundsException ex) {
		}
	} // testWithdraw

	public void testCompare() {
		Account a2 = new Account("101-1001");
		a2.setType("Savings");
		a2.setBalance(new BigDecimal("12.25"));
		assertEquals(a1.compareTo(a2), 0);
	} // testCompare

} // TestAccount
