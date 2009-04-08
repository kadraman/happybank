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

import net.sourceforge.happybank.exception.BankException;
import net.sourceforge.happybank.facade.BankingFacade;
import net.sourceforge.happybank.model.Account;
import net.sourceforge.happybank.model.Customer;
import net.sourceforge.happybank.model.TransRecord;

import junit.framework.TestCase;

/**
 * JUnit test for Bank facade.
 * 
 * @author Kevin A. Lee
 * @email kevin.lee@buildmeister.com
 */
public class TestBank extends TestCase {

    /**
     * The banking facade test.
     */
    private BankingFacade bank = new BankingFacade();

    /**
     * Default constructor for TestBank.
     * 
     * @param arg0
     *            the parameter.
     */
    public TestBank(final String arg0) {
        super(arg0);
        // not testing JNDI
        bank.setJndi(false);
    } // TestBank

    /**
     * Add some new accounts and customers.
     */
    protected final void setUp() {
        try {
            bank.addCustomer("120", "Mr", "A", "Customer");
            bank.addCustomer("130", "Mr", "Ano", "Customer");
            bank.addAccount("120-2001", "120", "Checking");
            bank.addAccount("120-2002", "120", "Savings");
            bank.addAccount("120-2010", "130", "Checking");
            bank.addAccount("120-2011", "130", "Savings");
            bank.addAccount("120-2012", "130", "Savings");
            bank.deposit("120-2002", new BigDecimal("100.00"));
            delay(5);
            bank.deposit("120-2002", new BigDecimal("50.00"));
            delay(5);
            bank.deposit("120-2010", new BigDecimal("1000.45"));
            delay(5);
            bank.deposit("120-2011", new BigDecimal("123.69"));
        } catch (BankException e) {
            e.printStackTrace();
        }
    } // setUp

    /**
     * Remove the accounts and customers.
     */
    protected final void tearDown() {
       try {
            bank.deleteAccount("120-2001");
            bank.deleteAccount("120-2002");
            bank.deleteAccount("120-2010");
            bank.deleteAccount("120-2011");
            bank.deleteAccount("120-2012");
            bank.deleteCustomer("120");
            bank.deleteCustomer("130");
        } catch (BankException e) {
            e.printStackTrace();
        }
    } // tearDown

    /**
     * Test getting customers.
     */
    public final void testGetCustomers() {
        // get single customer
        Customer c1 = null;
        try {
            c1 = bank.getCustomer("120");
        } catch (BankException e) {
            e.printStackTrace();
        }
        assertEquals(c1.getFirstName(), "A");
        assertEquals(c1.getLastName(), "Customer");
        // force exception
        try {
            c1 = bank.getCustomer("201");
            fail("getCustomer hasn't caused an exception when it should");
        } catch (BankException e) {
            // ignore
        }
        //TODO: get all customers
    } // testGetCustomers

    /**
     * Test getting accounts.
     */
    public final void testGetAccounts() {
        // get a customers account
        Account a1 = null;
        try {
            a1 = bank.getAccount("120-2002");
        } catch (BankException e) {
            e.printStackTrace();
        }
        assertEquals(a1.getType(), "Savings");
        assertEquals(a1.getBalance(), new BigDecimal("150.00"));
        // force exception
        try {
            a1 = bank.getAccount("101-2099");
            fail("getAccount hasn't caused an exception when it should");
        } catch (BankException e) {
           // ignore
        }
        // get all customers accounts
        try {
            Account[] accounts = bank.getAccounts("120");
            // assertEquals(accounts.length, 4);
            assertEquals(accounts[0].getBalance(), new BigDecimal("0"));
            assertEquals(accounts[1].getBalance(), new BigDecimal("150.00"));
        } catch (BankException e) {
            e.printStackTrace();
        }
        // force exception
        try {
            Account[] accounts = bank.getAccounts(null);
            fail("getAccounts hasn't caused an exception when it should");
        } catch (BankException e) {
            // ignore
        }
    } // testGetAccounts

    /**
     * Test getting transactions.
     */
    public final void testGetTransactions() {
        // get customer account transactions
        try {
            TransRecord[] transactions = bank.getTransactions("120-2002");
            // assertEquals(transactions.length, 6); // could be a problem here
            assertEquals(transactions[0].getTransAmt(),
                    new BigDecimal("50.00"));
            assertEquals(transactions[1].getTransAmt(),
                    new BigDecimal("100.00"));
        } catch (BankException e) {
            e.printStackTrace();
        }
        // force exception
        try {
            TransRecord[] transactions = bank.getTransactions("120-999");
            // fail("getTransactions hasn't caused an exception as it should");
        } catch (BankException e) {
            // ignore
        }
    } // testGetTransactions

    /**
     * Test depositing funds.
     */
    public final void testDeposit() {
        // deposit
        try {
            BigDecimal balance = bank.deposit("120-2002", new BigDecimal(
                    "50.00"));
            assertEquals(balance, new BigDecimal("200.00"));
        } catch (BankException e) {
            e.printStackTrace();
        }
        // force exception
        try {
            BigDecimal balance = bank.deposit("120-999",
                    new BigDecimal("50.00"));
            fail("deposit hasn't caused an exception as it should");
        } catch (BankException ex) {
            // ignore
        }
    } // testDeposit

    /**
     * Test withdrawing funds.
     */
    public final void testWithdraw() {
        // withdraw
        try {
            BigDecimal balance = bank.withdraw("120-2002", new BigDecimal(
                    "75.00"));
            assertEquals(balance, new BigDecimal("75.00"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // withdraw too much - exception
        try {
            BigDecimal balance = bank.withdraw("120-2002", new BigDecimal(
                    "500.00"));
            fail("withdraw hasn't caused an exception as it should");
        } catch (Exception e) {
            // ignore
        }
        // force exception
        try {
            BigDecimal balance = bank.withdraw("120-999", new BigDecimal(
                    "50.00"));
            fail("withdraw hasn't caused an exception as it should");
        } catch (Exception e) {
            // ignore
        }
    } // testWithdraw

    /**
     * Test transfering funds.
     */
    public final void testTransfer() {
        // transfer
        try {
            BigDecimal balance = bank.transfer("120-2002", "120-2001",
                    new BigDecimal("10.00"));
            assertEquals(balance, new BigDecimal("140.00"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // transfer too much - exception
        try {
            BigDecimal balance = bank.transfer("120-2002", "120-2001",
                    new BigDecimal("500.00"));
            fail("transfer hasn't caused an exception as it should");
        } catch (Exception ex) {
            // ignore
        }
        // force exception
        try {
            BigDecimal balance = bank.transfer("120-999", "120-2001",
                    new BigDecimal("100.00"));
            fail("transfer hasn't caused an exception as it should");
        } catch (Exception e) {
            // ignore
        }
        // and again
        try {
            BigDecimal balance = bank.transfer("120-2002", "120-999",
                    new BigDecimal("100.00"));
            fail("transfer hasn't caused an exception as it should");
        } catch (Exception e) {
            // ignore
        }
    } // testTransfer

    /**
     * Test adding and deleting a customer.
     */
    public final void testAddDeleteCustomer() {
        // add customer
        try {
            bank.addCustomer("201", "Mr", "New", "Customer");
        } catch (BankException e) {
            e.printStackTrace();
        }
        // delete customer
        try {
            Customer c1 = bank.getCustomer("201");
            assertNotNull(c1);
            c1 = bank.deleteCustomer("201");
            assertNull(c1);
        } catch (BankException e) {
            e.printStackTrace();
        }
        // force exception
        try {
            Customer c2 = bank.deleteCustomer("999");
            fail("deleteCustomer hasn't caused an exception as it should");
        } catch (BankException e) {
            // ignore
        }
    } // testAddDeleteCustomer

    /**
     * Test adding and deleting an account.
     */
    public final void testAddDeleteAccount() {
        // add account
        try {
            bank.addAccount("120-1111", "120", "C");
        } catch (BankException e) {
            e.printStackTrace();
        }
        // delete account
        try {
            Account a1 = bank.getAccount("120-1111");
            assertNotNull(a1);
            a1 = bank.deleteAccount("120-1111");
            assertNull(a1);
        } catch (BankException e) {
            e.printStackTrace();
        }
        // force exception
        try {
            Account a2 = bank.deleteAccount("120-999");
            fail("deleteAccount hasn't caused an exception as it should");
        } catch (BankException ex) {
            // ignore
        }
    } // testAddDeleteAccount
    
    /**
     * Delay (sleep) for a number of milliseconds.
     *
     * @param delayTime
     *            milliseconds to delay.
     */
    private void delay(final int delayTime) {
        try {
            Thread.sleep(delayTime);
        } catch (InterruptedException e) {
           // ignore
        }
    } // delay

} // TestBank
