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

import java.math.BigDecimal;

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
public interface Bank {

    // Customers:
    public Customer addCustomer(String customerID, String title, String first,
            String last) throws BankException;

    public Customer deleteCustomer(String customerID) throws BankException,
            CustomerDoesNotExistException;

    public Customer getCustomer(String customerID)
            throws CustomerDoesNotExistException;

    public Customer getCustomerByUsername(String username)
            throws CustomerDoesNotExistException;

    public Customer[] getCustomers() throws BankException;

    public void associate(String customerID, String accountID)
            throws BankException, CustomerDoesNotExistException,
            AccountDoesNotExistException;

    // Accounts:
    public Account addAccount(String accountID, String customerID, String type)
            throws CustomerDoesNotExistException, BankException;

    public Account deleteAccount(String accountID)
            throws AccountDoesNotExistException, BankException;

    public Account getAccount(String accountID)
            throws AccountDoesNotExistException, BankException;

    public Account[] getAccounts(String customerID)
            throws CustomerDoesNotExistException, BankException;

    public String accountOwner(String accountID)
            throws AccountDoesNotExistException, CustomerDoesNotExistException,
            BankException;

    public BigDecimal deposit(String accountID, BigDecimal amount)
            throws AccountDoesNotExistException, BankException;

    public BigDecimal withdraw(String accountID, BigDecimal amount)
            throws InsufficientFundsException, AccountDoesNotExistException,
            BankException;

    public BigDecimal transfer(String account1, String account2,
            BigDecimal amount) throws InsufficientFundsException,
            AccountDoesNotExistException, BankException;

    // Transactions:
    public TransRecord[] getTransactions(String accountID)
            throws AccountDoesNotExistException, BankException;

} // Bank