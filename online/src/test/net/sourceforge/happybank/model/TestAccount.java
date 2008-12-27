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
 * JUnit test for account class.
 *
 * @author Kevin A. Lee
 * @email kevin.lee@buildmeister.com
 */
public class TestAccount extends TestCase {

    /**
     * Test account.
     */
    private Account a1;

    /**
     * Default constructor for TestAccount.
     *
     * @param arg0
     *            the argument
     */
    public TestAccount(final String arg0) {
        super(arg0);
    } // TestAccount

    /**
     * Setup the test account.
     */
    protected final void setUp() {
        // create the account
        a1 = new Account("101-1001");
        a1.setType("Current");
        a1.setBalance(new BigDecimal("100.25"));
    } // setUp

    /**
     * Cleanup the test account.
     */
    protected final void tearDown() {
        // clear the account
        a1 = null;
    } // setUp

    /**
     * Test getters.
     */
    public final void testGetters() {
        assertTrue(!a1.equals(null));
        BigDecimal balance = new BigDecimal("100.25");
        assertEquals(a1.getId(), "101-1001");
        assertEquals(a1.getType(), "Current");
        assertEquals(a1.getBalance(), balance);
        a1.setId("101-1002");
        assertEquals(a1.getId(), "101-1002");
    } // testGetters

    /**
     * Test deposit method.
     */
    public final void testDeposit() {
        BigDecimal balance = new BigDecimal("150.50");
        assertEquals(a1.deposit(new BigDecimal("50.25")), balance);
    } // testDeposit

    /**
     * Test withdraw method.
     */
    public final void testWithdraw() {
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
            System.out
                    .println("Caught Insufficient Funds exception as expected");
        }
    } // testWithdraw

    /**
     * Test compare method.
     */
    public final void testCompare() {
        Account a2 = new Account("101-1001");
        a2.setType("Savings");
        a2.setBalance(new BigDecimal("12.25"));
        assertEquals(a1.compareTo(a2), 0);
    } // testCompare

} // TestAccount
