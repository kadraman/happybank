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
import net.sourceforge.happybank.model.CustomerAccount;
import net.sourceforge.happybank.model.TransRecord;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 * Data Access Object for Bank.
 * 
 * @author Kevin A. Lee
 * @email kevin.lee@buildmeister.com
 */
public class BankDAO extends SqlMapClientDaoSupport {
    
    /**
     * Get a specific customer.
     * 
     * @param customerID the id of the customer
     * @return Customer the customer
     * @throws CustomerDoesNotExistException if the customer does not exist or
     *         cannot be found
     */
    public Customer getCustomer(final String customerID)
            throws CustomerDoesNotExistException {
        if (customerID == null) {
            throw new CustomerDoesNotExistException("Customer number is null");
        }
        // get the customer by their id
        Customer customer = (Customer) getSqlMapClientTemplate()
                .queryForObject("getCustomer", customerID);
        if (customer == null) {
            throw new CustomerDoesNotExistException("Customer not found: "
                    + customerID);
        }
        return customer;
    } // getCustomer
    
    /**
     * Get a specific customer by their username.
     * 
     * @param username the username of the customer
     * @return Customer the customer
     * @throws CustomerDoesNotExistException if the customer does not exist or
     *         cannot be found
     */
    public Customer getCustomerByUsername(final String username)
            throws CustomerDoesNotExistException {
        if (username == null) {
            throw new 
                CustomerDoesNotExistException("Customer username is null");
        }
        // get the customer by their username
        Customer customer = (Customer) getSqlMapClientTemplate()
                .queryForObject("getCustomerByUsername", username);
        if (customer == null) {
            throw new CustomerDoesNotExistException("Customer not found: "
                    + username);
        }
        return customer;
    } // getCustomer
    
    /**
     * Get the customers of the bank.
     * 
     * @return array of customers
     * @throws BankException if customers cannot be located
     */
    public List<Customer> getCustomers() throws BankException {
        return getSqlMapClientTemplate().queryForList("getCustomers", null);
    } // getCustomers
    
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
    public Customer addCustomer(final String customerID, final String title,
            final String first, final String last) throws BankException {
        if (customerID == null) {
            throw new CustomerDoesNotExistException("Customer number is null");
        }
        // create temporary customer
        Customer ctmp = new Customer(customerID);
        ctmp.setFirstName(first);
        ctmp.setLastName(last);
        ctmp.setTitle(title);
        ctmp.setUserName(first + "." + last);
		ctmp.setPassword("password"); // default password
        getSqlMapClientTemplate().insert("addCustomer", ctmp);
        return ctmp;
    } // addCustomer
    
    /**
     * Delete the specified customer from the bank.
     * 
     * @param customerID the id of the customer
     * @return Customer the customer
     * @throws CustomerDoesNotExistException if customer does not exist
     */
    public Customer deleteCustomer(final String customerID)
            throws CustomerDoesNotExistException {
        if (customerID == null) {
            throw new CustomerDoesNotExistException("Customer number is null");
        }
        getSqlMapClientTemplate().delete("deleteCustomer", customerID);
        // TODO: exception if customer does not exist
        return null;
    } // deleteCustomer
    
    /**
     * Get the account with a specific id.
     * 
     * @param accountID account to get
     * @return the account
     * @throws AccountDoesNotExistException if account does not exist
     * @throws BankException on other failure
     */
    public Account getAccount(final String accountID)
            throws AccountDoesNotExistException, BankException {
        if (accountID == null) {
            throw new AccountDoesNotExistException("Account number is null");
        }
        // get the account by its id
        Account account = (Account) getSqlMapClientTemplate().queryForObject(
                "getAccount", accountID);
        if (account == null) {
            throw new AccountDoesNotExistException("Account not found: "
                    + accountID);
        }
        return account;
    } // getAccount
    
    /**
     * Get the accounts of a specific customer.
     * 
     * @param customerID the id of the customer to get accounts of
     * @return array of accounts
     * @throws CustomerDoesNotExistException if the customer does not exist
     * @throws BankException on other failure
     */
    public List<Account> getAccounts(final String customerID)
            throws CustomerDoesNotExistException, BankException {
        if (customerID == null) {
            throw new CustomerDoesNotExistException("Customer number is null");
        }
        return getSqlMapClientTemplate()
                .queryForList("getAccounts", customerID);
    } // getAccounts
    
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
    public Account addAccount(final String accountID, final String customerID,
            final String type) throws CustomerDoesNotExistException,
            BankException {
        if (accountID == null) {
            throw new AccountDoesNotExistException("Account number is null");
        }
        // create temporary account
        Account atmp = new Account(accountID);
        atmp.setBalance(new BigDecimal(0));
        atmp.setType(type);
        if (type.equals("Checking")) {
            atmp.setDiscriminator("C");
        } else {
            atmp.setDiscriminator("S");
        }
        // add the account
        getSqlMapClientTemplate().insert("addAccount", atmp);
        // associate the account with the customer
        CustomerAccount catmp = new CustomerAccount(customerID, accountID);
        getSqlMapClientTemplate().insert("addCustomerAccount", catmp);
        return atmp;
    } // addAccount
    
    /**
     * Delete an account in the bank.
     * 
     * @param accountID the id of the account
     * @return Account an account object
     * @throws AccountDoesNotExistException if the account does not exist
     * @throws BankException on other failure
     */
    public Account deleteAccount(final String accountID)
            throws AccountDoesNotExistException, BankException {
        if (accountID == null) {
            throw new AccountDoesNotExistException("Account number is null");
        }
        // unassociate the account with the customer
        getSqlMapClientTemplate().delete("deleteCustomerAccount", accountID);
        // delete account's transactions (if any)
        getSqlMapClientTemplate().delete("deleteTransactions", accountID);
        // delete account
        getSqlMapClientTemplate().delete("deleteAccount", accountID);
        // TODO: exception if account does not exist
        return null;
    } // deleteAccount
    
    /**
     * Associate a customer and bank account.
     * 
     * @param customerID id of the customer
     * @param accountID id of the account
     * @throws AccountDoesNotExistException if account does not exist
     * @throws CustomerDoesNotExistException if the customer does not exist
     * @throws BankException on other failure
     */
    public void associate(final String customerID, final String accountID)
            throws CustomerDoesNotExistException, AccountDoesNotExistException,
            BankException {
        // associate the account with the customer
        CustomerAccount catmp = new CustomerAccount(customerID, accountID);
        getSqlMapClientTemplate().insert("addCustomerAccount", catmp);
    } // associate
    
    /**
     * Get the customer of a bank account.
     * 
     * @param accountID id of the account
     * @return the id of the customer
     * @throws AccountDoesNotExistException if the account does not exist
     * @throws CustomerDoesNotExistException if the customer does not exist
     * @throws BankException on other failure
     */
    public String accountOwner(final String accountID)
            throws AccountDoesNotExistException, CustomerDoesNotExistException,
            BankException {
        if (accountID == null) {
            throw new AccountDoesNotExistException("Account number is null");
        }
        // get the account account
        String cid = (String) getSqlMapClientTemplate().queryForObject(
                "getAccountOwner", accountID);
        if (cid == null) {
            throw new CustomerDoesNotExistException(
                    "Bank get account owner failed: " + accountID);
        }
        return cid;
    } // accountOwner
    
    /**
     * Get the transaction records for a specific account.
     * 
     * @param accountID the id of the account
     * @return array of transactions
     * @throws AccountDoesNotExistException if account does not exist
     * @throws BankException on other failure
     */
    public List<TransRecord> getTransactions(final String accountID)
            throws AccountDoesNotExistException, BankException {
        if (accountID == null) {
            throw new AccountDoesNotExistException("Account number is null");
        }
        return getSqlMapClientTemplate().queryForList("getTransactions",
                accountID);
    } // getTransactionsJDBC
    
    /**
     * Deposit funds into a specific account.
     * 
     * @param accountID the id of the account
     * @param amount the amount to deposit
     * @return the balance of the account
     * @throws AccountDoesNotExistException if the account does not exist
     * @throws BankException on other failure
     */
    public BigDecimal deposit(final String accountID, final BigDecimal amount)
            throws AccountDoesNotExistException, BankException {
        Account atmp = getAccount(accountID);
        // deposit funds
        atmp.deposit(amount);
        // update account
        getSqlMapClientTemplate().update("updateAccount", atmp);
        // create transaction record
        TransRecord transTmp = new TransRecord("C", amount);
        transTmp.setAccid(atmp.getId());
        getSqlMapClientTemplate().insert("addTransaction", transTmp);
        return atmp.getBalance();
    } // deposit
    
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
    public BigDecimal withdraw(final String accountID, final BigDecimal amount)
            throws AccountDoesNotExistException, InsufficientFundsException,
            BankException {
        Account atmp = getAccount(accountID);
        // withdraw funds
        atmp.withdraw(amount);
        // update account
        getSqlMapClientTemplate().update("updateAccount", atmp);
        // create transaction record
        TransRecord transTmp = new TransRecord("D", amount);
        transTmp.setAccid(atmp.getId());
        getSqlMapClientTemplate().insert("addTransaction", transTmp);
        return atmp.getBalance();
    } // withdraw
    
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
    public BigDecimal transfer(final String accountID1,
            final String accountID2, final BigDecimal amount)
            throws AccountDoesNotExistException, InsufficientFundsException,
            BankException {
        Account account1 = getAccount(accountID1);
        Account account2 = getAccount(accountID2);
        // withdraw from account 1
        account1.withdraw(amount);
        getSqlMapClientTemplate().update("updateAccount", account1);
        TransRecord transTmp = new TransRecord("D", amount);
        transTmp.setAccid(account1.getId());
        getSqlMapClientTemplate().insert("addTransaction", transTmp);
        // deposit in account 2
        account2.deposit(amount);
        getSqlMapClientTemplate().update("updateAccount", account2);
        transTmp = new TransRecord("C", amount);
        transTmp.setAccid(account2.getId());
        getSqlMapClientTemplate().insert("addTransaction", transTmp);
        return account1.getBalance();
    } // transfer
           
} // BankDAO

