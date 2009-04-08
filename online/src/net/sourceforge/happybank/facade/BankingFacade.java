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
 * Java bean Banking facade.
 *
 * @author Kevin A. Lee
 * @email kevin.lee@buildmeister.com
 */
public class BankingFacade implements Serializable {

    /**
     * Generated serialization identifier.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Controls whether to use JNDI (defaults to true for web application).
     */
    private boolean jndi = true;

    /**
     * The Bank singleton.
     */
    private transient Bank bank;

    /**
     * Get the bank instance.
     * @return the bank
     */
    private Bank getBank() {
        if (bank == null) {
            bank = (Bank) BankJDBC.getInstance(jndi);
        }
        return bank;
    }

    /**
     * Are we using Jndi to access the database.
     * @return true if using jndi else false
     */
    public boolean isJndi() {
        return jndi;
    }

    /**
     * Set whether to use jndi.
     * @param bjndi set to true if jndi is to be used else false
     */
    public void setJndi(boolean bjndi) {
        this.jndi = bjndi;
    }

    // business methods

    // Customers:

    /**
     * Add a new customer to the bank.
     * 
     * @param customerID the id of the customer
     * @param title the title of the customer, i.e. Mr/Mrs
     * @param first the firstname of the customer
     * @param last the lastname of the customer
     * @return Customer the customer
     * @throws BankException id customer cannot be added
     */
    public Customer addCustomer(String customerID, String title, String first,
            String last) throws BankException {
        return getBank().addCustomer(customerID, title, first, last);
    }

    /**
     * Delete the specified customer from the bank.
     * 
     * @param customerID the id of the customer
     * @return Customer the customer
     * @throws CustomerDoesNotExistException if customer does not exist
     * @throws BankException otherwise
     */
    public Customer deleteCustomer(String customerID)
            throws CustomerDoesNotExistException, BankException {
        return getBank().deleteCustomer(customerID);
    }

    /**
     * Get a specific customer.
     * 
     * @param customerID the id of the customer
     * @return Customer the customer
     * @throws CustomerDoesNotExistException if the customer does not exist or
     *         cannot be found
     */
    public Customer getCustomer(String customerID)
            throws CustomerDoesNotExistException {
        return getBank().getCustomer(customerID);
    }

    /**
     * Get a specific customer by their username.
     * 
     * @param username the username of the customer
     * @return Customer the customer
     * @throws CustomerDoesNotExistException if the customer does not exist or
     *         cannot be found
     */
    public Customer getCustomerByUsername(String username)
            throws CustomerDoesNotExistException {
        return getBank().getCustomerByUsername(username);
    }

    /**
     * Get the customers of the bank.
     * 
     * @return array of customers
     * @throws BankException if customers cannot be located
     */
    public Customer[] getCustomers() throws BankException {
        return getBank().getCustomers();
    }

    /**
     * Associate a customer and bank account.
     * 
     * @param customerID id of the customer
     * @param accountID id of the account
     * @throws AccountDoesNotExistException if account does not exist
     * @throws CustomerDoesNotExistException if the customer does not exist
     * @throws BankException on other failure
     */
    public void associate(String customerID, String accountID)
            throws CustomerDoesNotExistException, AccountDoesNotExistException,
            BankException {
        getBank().associate(customerID, accountID);
    }

    // Accounts:
    
    /**
     * Add an account to the bank.
     * 
     * @param accountID the id of the account
     * @param customerID the id of the customer
     * @param type the type of the account
     * @throws CustomerDoesNotExistException if customer does not exist
     * @throws BankException on other failure
     * @return Account the new account object
     */
    public Account addAccount(String accountID, String customerID, String type)
            throws CustomerDoesNotExistException, BankException {
        return getBank().addAccount(accountID, customerID, type);
    }

    /**
     * Delete an account in the bank.
     * 
     * @param accountID the id of the account
     * @return Account an account object
     * @throws AccountDoesNotExistException if the account does not exist
     * @throws BankException on other failure
     */
    public Account deleteAccount(String accountID)
            throws AccountDoesNotExistException, BankException {
        return getBank().deleteAccount(accountID);
    }

    /**
     * Get the account with a specific id.
     * 
     * @param accountID account to get
     * @return the account
     * @throws AccountDoesNotExistException if account does not exist
     * @throws BankException on other failure
     */
    public Account getAccount(String accountID)
            throws AccountDoesNotExistException, BankException {
        return getBank().getAccount(accountID);
    }

    /**
     * Get the accounts of a specific customer.
     * 
     * @param customerID the id of the customer to get accounts of
     * @return array of accounts
     * @throws CustomerDoesNotExistException if the customer does not exist
     * @throws BankException on other failure
     */
    public Account[] getAccounts(String customerID)
            throws CustomerDoesNotExistException, BankException {
        return getBank().getAccounts(customerID);
    }

    /**
     * Get the customer of a bank account.
     * 
     * @param accountID id of the account
     * @return the id of the customer
     * @throws AccountDoesNotExistException if the account does not exist
     * @throws CustomerDoesNotExistException if the customer does not exist
     * @throws BankException on other failure
     */
    public String accountOwner(String accountID)
            throws AccountDoesNotExistException, CustomerDoesNotExistException,
            BankException {
        return getBank().accountOwner(accountID);
    }

    /**
     * Deposit funds into a specific account.
     * 
     * @param accountID the id of the account
     * @param amount the amount to deposit
     * @return the balance of the account
     * @throws AccountDoesNotExistException if the account does not exist
     * @throws BankException on other failure
     */
    public BigDecimal deposit(String accountID, BigDecimal amount)
            throws AccountDoesNotExistException, BankException {
        return getBank().deposit(accountID, amount);
    }

    /**
     * Withdraw funds from a specific account.
     * 
     * @param accountID the id of the account
     * @param amount the amount to withdraw
     * @return the balance of the account
     * @throws AccountDoesNotExistException if account does not exist
     * @throws InsufficientFundsException if there are not enough funds in the
     *         account
     * @throws BankException on other failure
     */
    public BigDecimal withdraw(String accountID, BigDecimal amount)
            throws InsufficientFundsException, AccountDoesNotExistException,
            BankException {
        return getBank().withdraw(accountID, amount);
    }

    /**
     * Transfer funds from one account to another.
     * 
     * @param accountID1 id of the first account
     * @param accountID2 id of the second account
     * @param amount the amount to transfer
     * @return the balance of the first account
     * @throws AccountDoesNotExistException if account does not exist
     * @throws InsufficientFundsException if there are insufficient funds in the
     *         account
     * @throws BankException on other failure
     */
    public BigDecimal transfer(String accountID1, String accountID2,
            BigDecimal amount) throws InsufficientFundsException,
            AccountDoesNotExistException, BankException {
        return getBank().transfer(accountID1, accountID2, amount);
    }

    // Transactions:
    
    /**
     * Get the transaction records for a specific account.
     * 
     * @param accountID the id of the account
     * @return array of transactions
     * @throws AccountDoesNotExistException if account does not exist
     * @throws BankException on other failure
     */
    public TransRecord[] getTransactions(String accountID)
            throws AccountDoesNotExistException, BankException {
        return getBank().getTransactions(accountID);
    }

} // BankingFacade
