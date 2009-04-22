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
 * Bank Interface.
 *
 * @author Kevin A. Lee
 * @email kevin.lee@buildmeister.com
 */
public interface IBankManager {

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
    Customer addCustomer(String customerID, String title, String first,
            String last) throws BankException;
    
    /**
     * Delete the specified customer from the bank.
     * 
     * @param customerID the id of the customer
     * @return Customer the customer
     * @throws CustomerDoesNotExistException if customer does not exist
     * @throws BankException otherwise
     */
    Customer deleteCustomer(final String customerID)
            throws BankException, CustomerDoesNotExistException;

    /**
     * Get a specific customer.
     * 
     * @param customerID the id of the customer
     * @return Customer the customer
     * @throws CustomerDoesNotExistException if the customer does not exist or
     *         cannot be found
     */
    Customer getCustomer(String customerID)
            throws CustomerDoesNotExistException;

    /**
     * Get a specific customer by their username.
     * 
     * @param username the username of the customer
     * @return Customer the customer
     * @throws CustomerDoesNotExistException if the customer does not exist or
     *         cannot be found
     */
    Customer getCustomerByUsername(String username)
            throws CustomerDoesNotExistException;

    /**
     * Get the customers of the bank.
     * 
     * @return array of customers
     * @throws BankException if customers cannot be located
     */
    List<Customer> getCustomers() throws BankException;

    /**
     * Associate a customer and bank account.
     * 
     * @param customerID id of the customer
     * @param accountID id of the account
     * @throws AccountDoesNotExistException if account does not exist
     * @throws CustomerDoesNotExistException if the customer does not exist
     * @throws BankException on other failure
     */
    void associate(String customerID, String accountID)
            throws BankException, CustomerDoesNotExistException,
            AccountDoesNotExistException;

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
    Account addAccount(String accountID, String customerID, String type)
            throws CustomerDoesNotExistException, BankException;

    /**
     * Delete an account in the bank.
     * 
     * @param accountID the id of the account
     * @return Account an account object
     * @throws AccountDoesNotExistException if the account does not exist
     * @throws BankException on other failure
     */
    Account deleteAccount(String accountID)
            throws AccountDoesNotExistException, BankException;

    /**
     * Get the account with a specific id.
     * 
     * @param accountID account to get
     * @return the account
     * @throws AccountDoesNotExistException if account does not exist
     * @throws BankException on other failure
     */
    Account getAccount(String accountID)
            throws AccountDoesNotExistException, BankException;

    /**
     * Get the accounts of a specific customer.
     * 
     * @param customerID the id of the customer to get accounts of
     * @return array of accounts
     * @throws CustomerDoesNotExistException if the customer does not exist
     * @throws BankException on other failure
     */
    List<Account> getAccounts(String customerID)
            throws CustomerDoesNotExistException, BankException;

    /**
     * Get the customer of a bank account.
     * 
     * @param accountID id of the account
     * @return the id of the customer
     * @throws AccountDoesNotExistException if the account does not exist
     * @throws CustomerDoesNotExistException if the customer does not exist
     * @throws BankException on other failure
     */
    String accountOwner(String accountID)
            throws AccountDoesNotExistException, CustomerDoesNotExistException,
            BankException;

    /**
     * Deposit funds into a specific account.
     * 
     * @param accountID the id of the account
     * @param amount the amount to deposit
     * @return the balance of the account
     * @throws AccountDoesNotExistException if the account does not exist
     * @throws BankException on other failure
     */
    BigDecimal deposit(String accountID, BigDecimal amount)
            throws AccountDoesNotExistException, BankException;

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
    BigDecimal withdraw(String accountID, BigDecimal amount)
            throws InsufficientFundsException, AccountDoesNotExistException,
            BankException;

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
    BigDecimal transfer(final String accountID1,
            final String accountID2, final BigDecimal amount)
            throws AccountDoesNotExistException, InsufficientFundsException,
            BankException;

    /**
     * Get the transaction records for a specific account.
     * 
     * @param accountID the id of the account
     * @return array of transactions
     * @throws AccountDoesNotExistException if account does not exist
     * @throws BankException on other failure
     */
    List<TransRecord> getTransactions(String accountID)
            throws AccountDoesNotExistException, BankException;

} // Bank
