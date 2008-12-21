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
 * JUnit test for TransRecord class
 * 
 * @author
 */
public class TestBank extends TestCase {

    private BankingFacade bank = new BankingFacade();

    /**
     * constructor for TestBank
     * 
     * @param arg0
     */
    public TestBank(String arg0) {
        super(arg0);
        // JDBC JUnit tests do not work yet - FIX!!
        bank.setJndi(false);
    } // TestBank

    protected void setUp() {
        try {
            bank.addCustomer("120", "Mr", "A", "Customer");
            bank.addCustomer("130", "Mr", "Ano", "Customer");
            bank.addAccount("101-2001", "120", "Checking");
            bank.addAccount("101-2002", "120", "Savings");
            bank.addAccount("101-2010", "130", "Checking");
            bank.addAccount("101-2011", "130", "Savings");
            bank.addAccount("101-2012", "130", "Savings");
            bank.deposit("101-2002", new BigDecimal("100.00"));
            bank.deposit("101-2002", new BigDecimal("50.00"));
            bank.deposit("101-2010", new BigDecimal("1000.45"));
            bank.deposit("101-2011", new BigDecimal("123.69"));
        } catch (BankException e) {
            e.printStackTrace();
        }
    } // setUp

    protected void tearDown() {
        try {
            bank.deleteAccount("101-2001");
            bank.deleteAccount("101-2002");
            bank.deleteAccount("101-2010");
            bank.deleteAccount("101-2011");
            bank.deleteAccount("101-2012");
            bank.deleteCustomer("120");
            bank.deleteCustomer("130");
        } catch (BankException e) {
            e.printStackTrace();
        }
    } // tearDown

    public void testGetCustomers() {
        // get single customer
        Customer c1 = null;
        try {
            c1 = bank.getCustomer("120");
        } catch (BankException ex) {
            ex.printStackTrace();
        }
        assertEquals(c1.getFirstName(), "A");
        assertEquals(c1.getLastName(), "Customer");
        // force exception
        try {
            c1 = bank.getCustomer("201");
            fail("getCustomer hasn't caused an exception when it should");
        } catch (BankException ex) {
        }
        /*
         * try { // get all customers // Customer[] customers =
         * bank.getCustomers(); //assertEquals(customers.length, 4); // 6
         * default plus our 2 new ones } catch (BankException e) { // TODO
         * Auto-generated catch block e.printStackTrace(); }
         */
    } // testGetCustomers

    public void testGetAccounts() {
        // get a customers account
        Account a1 = null;
        try {
            a1 = bank.getAccount("101-2002");
        } catch (BankException ex) {
            ex.printStackTrace();
        }
        assertEquals(a1.getType(), "Savings");
        assertEquals(a1.getBalance(), new BigDecimal("150.00"));
        // force exception
        try {
            a1 = bank.getAccount("101-2099");
            fail("getAccount hasn't caused an exception when it should");
        } catch (BankException ex) {
        }
        // get all customers accounts
        try {
            Account[] accounts = bank.getAccounts("120");
            // assertEquals(accounts.length, 4);
            assertEquals(accounts[0].getBalance(), new BigDecimal("0.00"));
            assertEquals(accounts[1].getBalance(), new BigDecimal("150.00"));
        } catch (BankException ex) {
            ex.printStackTrace();
        }
        // force exception
        try {
            Account[] accounts = bank.getAccounts(null);
            fail("getAccounts hasn't caused an exception when it should");
        } catch (BankException ex) {
        }
    } // testGetAccounts

    public void testGetTransactions() {
        // get customer account transactions
        try {
            TransRecord[] transactions = bank.getTransactions("101-2002");
            // assertEquals(transactions.length, 6); // could be a problem here
            assertEquals(transactions[1].getTransAmt(),
                    new BigDecimal("100.00"));
            assertEquals(transactions[2].getTransAmt(), new BigDecimal("50.00"));
        } catch (BankException ex) {
            ex.printStackTrace();
        }
        // force exception
        try {
            TransRecord[] transactions = bank.getTransactions("101-999");
            // fail("getTransactions hasn't caused an exception as it should");
        } catch (BankException ex) {
        }
    } // testGetTransactions

    public void testDeposit() {
        // deposit
        try {
            BigDecimal balance = bank.deposit("101-2002", new BigDecimal(
                    "50.00"));
            assertEquals(balance, new BigDecimal("200.00"));
        } catch (BankException ex) {
            ex.printStackTrace();
        }
        // force exception
        try {
            BigDecimal balance = bank.deposit("101-999",
                    new BigDecimal("50.00"));
            fail("deposit hasn't caused an exception as it should");
        } catch (BankException ex) {
        }
    } // testDeposit

    public void testWithdraw() {
        // withdraw
        try {
            BigDecimal balance = bank.withdraw("101-2002", new BigDecimal(
                    "75.00"));
            assertEquals(balance, new BigDecimal("75.00"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // withdraw too much - exception
        try {
            BigDecimal balance = bank.withdraw("101-2002", new BigDecimal(
                    "500.00"));
            fail("withdraw hasn't caused an exception as it should");
        } catch (Exception ex) {
        }
        // force exception
        try {
            BigDecimal balance = bank.withdraw("101-999", new BigDecimal(
                    "50.00"));
            fail("withdraw hasn't caused an exception as it should");
        } catch (Exception ex) {
        }
    } // testWithdraw

    public void testTransfer() {
        // transfer
        try {
            BigDecimal balance = bank.transfer("101-2002", "101-2001",
                    new BigDecimal("10.00"));
            assertEquals(balance, new BigDecimal("140.00"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // transfer too much - exception
        try {
            BigDecimal balance = bank.transfer("101-2002", "101-2001",
                    new BigDecimal("500.00"));
            fail("transfer hasn't caused an exception as it should");
        } catch (Exception ex) {
        }
        // force exception
        try {
            BigDecimal balance = bank.transfer("101-999", "101-2001",
                    new BigDecimal("100.00"));
            fail("transfer hasn't caused an exception as it should");
        } catch (Exception ex) {
        }
        // and again
        try {
            BigDecimal balance = bank.transfer("101-2002", "101-999",
                    new BigDecimal("100.00"));
            fail("transfer hasn't caused an exception as it should");
        } catch (Exception ex) {
        }
    } // testTransfer

    public void testAddDeleteCustomer() {
        // add customer
        try {
            bank.addCustomer("101", "Mr", "New", "Customer");
        } catch (BankException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // delete customer
        try {
            Customer c1 = bank.getCustomer("101");
            assertNotNull(c1);
            c1 = bank.deleteCustomer("101");
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

    public void testAddDeleteAccount() {
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
            Account a2 = bank.deleteAccount("101-999");
            fail("deleteAccount hasn't caused an exception as it should");
        } catch (BankException ex) {
            // ignore
        }
    } // testAddDeleteAccount

} // TestBank
