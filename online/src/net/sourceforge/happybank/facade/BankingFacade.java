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

package net.sourceforge.happybank.facade;

import java.io.Serializable;
import java.math.BigDecimal;

import net.sourceforge.happybank.exception.AccountDoesNotExistException;
import net.sourceforge.happybank.exception.BankException;
import net.sourceforge.happybank.exception.CustomerDoesNotExistException;
import net.sourceforge.happybank.exception.InsufficientFundsException;
import net.sourceforge.happybank.model.Account;
import net.sourceforge.happybank.model.Customer;
import net.sourceforge.happybank.model.TransRecord;

/**
 * JavaBean Banking, implementing the banking example façade.
 * 
 * @author
 */
public class BankingFacade implements Serializable {

    private static final long serialVersionUID = 1L;
    // the switches
    private boolean jndi = true;

    // the Bank
    private transient Bank bank;

    private Bank getBank() {
        if (bank == null) {
            bank = (Bank) BankJDBC.getInstance(jndi);
        }
        return bank;
    }

    public boolean isJndi() {
        return jndi;
    }

    public void setJndi(boolean jndi) {
        this.jndi = jndi;
    }

    // business methods

    // Customers:

    public Customer addCustomer(String customerID, String title, String first,
            String last) throws BankException {
        return getBank().addCustomer(customerID, title, first, last);
    }

    public Customer deleteCustomer(String customerID)
            throws CustomerDoesNotExistException, BankException {
        return getBank().deleteCustomer(customerID);
    }

    public Customer getCustomer(String customerID)
            throws CustomerDoesNotExistException, BankException {
        return getBank().getCustomer(customerID);
    }

    public Customer getCustomerByUsername(String username)
            throws CustomerDoesNotExistException, BankException {
        return getBank().getCustomerByUsername(username);
    }

    public Customer[] getCustomers() throws BankException {
        return getBank().getCustomers();
    }

    public void associate(String customerID, String accountID)
            throws CustomerDoesNotExistException, AccountDoesNotExistException,
            BankException {
        getBank().associate(customerID, accountID);
    }

    // Accounts:
    public Account addAccount(String accountID, String customerID, String type)
            throws CustomerDoesNotExistException, BankException {
        return getBank().addAccount(accountID, customerID, type);
    }

    public Account deleteAccount(String accountID)
            throws AccountDoesNotExistException, BankException {
        return getBank().deleteAccount(accountID);
    }

    public Account getAccount(String accountID)
            throws AccountDoesNotExistException, BankException {
        return getBank().getAccount(accountID);
    }

    public Account[] getAccounts(String customerID)
            throws CustomerDoesNotExistException, BankException {
        return getBank().getAccounts(customerID);
    }

    public String accountOwner(String accountID)
            throws AccountDoesNotExistException, CustomerDoesNotExistException,
            BankException {
        return getBank().accountOwner(accountID);
    }

    public BigDecimal deposit(String accountID, BigDecimal amount)
            throws AccountDoesNotExistException, BankException {
        return getBank().deposit(accountID, amount);
    }

    public BigDecimal withdraw(String accountID, BigDecimal amount)
            throws InsufficientFundsException, AccountDoesNotExistException,
            BankException {
        return getBank().withdraw(accountID, amount);
    }

    public BigDecimal transfer(String account1, String account2,
            BigDecimal amount) throws InsufficientFundsException,
            AccountDoesNotExistException, BankException {
        return getBank().transfer(account1, account2, amount);
    }

    // Transactions:
    public TransRecord[] getTransactions(String accountID)
            throws AccountDoesNotExistException, BankException {
        return getBank().getTransactions(accountID);
    }

} // BankingTest
