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

package net.sourceforge.happybank.facade;

import java.math.BigDecimal;
import java.util.List;

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
public class BankingFacade implements IBankManager {

    /**
     * Generated serialization identifier.
     */
    private static final long serialVersionUID = 1L;   

    /**
     * The Bank singleton.
     */
    private BankDAO bankDao = null;

    /**
     * Default constructor to create Data Access Object.
     * @param dao the Data Access Object
     */
    public BankingFacade(BankDAO dao) { 
        this.bankDao = dao;
    } // BankingFacade
    
    /**
     * Set the Bank Data Access Object.
     * @param dao the Data Access Object
     */
    public void setBankDao(BankDAO dao) {
        this.bankDao = dao;
    } // setBankDao
    
    /**
     * Get the Bank Data Access Object.
     * @return the Data Access Object
     */
    public BankDAO getBankDao() {
        return this.bankDao;
    } // getBankDao   

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
        return this.bankDao.addCustomer(customerID, title, first, last);
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
        return this.bankDao.deleteCustomer(customerID);
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
        return this.bankDao.getCustomer(customerID);
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
        return this.bankDao.getCustomerByUsername(username);
    }

    /**
     * Get the customers of the bank.
     * 
     * @return array of customers
     * @throws BankException if customers cannot be located
     */
    public List<Customer> getCustomers() throws BankException {
        return this.bankDao.getCustomers();
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
        this.bankDao.associate(customerID, accountID);
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
        return this.bankDao.addAccount(accountID, customerID, type);
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
        return this.bankDao.deleteAccount(accountID);
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
        return this.bankDao.getAccount(accountID);
    }

    /**
     * Get the accounts of a specific customer.
     * 
     * @param customerID the id of the customer to get accounts of
     * @return array of accounts
     * @throws CustomerDoesNotExistException if the customer does not exist
     * @throws BankException on other failure
     */
    public List<Account> getAccounts(String customerID)
            throws CustomerDoesNotExistException, BankException {
        return this.bankDao.getAccounts(customerID);
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
        return this.bankDao.accountOwner(accountID);
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
        return this.bankDao.deposit(accountID, amount);
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
        return this.bankDao.withdraw(accountID, amount);
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
        return this.bankDao.transfer(accountID1, accountID2, amount);
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
    public List<TransRecord> getTransactions(String accountID)
            throws AccountDoesNotExistException, BankException {
        return this.bankDao.getTransactions(accountID);
    }

} // BankingFacade
