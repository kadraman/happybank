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

package net.sourceforge.happybank.model;

import java.io.Serializable;
import java.math.BigDecimal;

import net.sourceforge.happybank.exception.InsufficientFundsException;

/**
 * Java bean representing a customer's account.
 *
 * @author Kevin A. Lee
 * @email kevin.lee@buildmeister.com
 */
public class Account implements Comparable, Serializable {

    /**
     * Generated serialization identifier.
     */
    private static final long serialVersionUID = 1L;
    /**
     * The id of the account.
     */
    private String id;
    /**
     * The type of the account: checking or savings.
     */
    private String type;
    /**
     * The discriminator of the account: "C" or "S".
     */
    private String discriminator;
    /**
     * The balance of the account.
     */
    private BigDecimal balance;

    /**
     * Default no-arg constructor.
     */
    public Account() {
        super();
    } // Account

    /**
     * Default constructor to create new account.
     *
     * @param accountid
     *            the id of the account
     */
    public Account(final String accountid) {
        setId(accountid);
        setBalance(new BigDecimal(0.00));
    } // Account

    // getters

    /**
     * Get the account id.
     *
     * @return account id
     */
    public final String getId() {
        return id;
    } // getId

    /**
     * Get the account type.
     *
     * @return account type
     */
    public final String getType() {
        return type;
    } // GetType

    /**
     * Get the discrimator for the account.
     * @return "C" or "S"
     */
    public String getDiscriminator() {
        return discriminator;
    } // getDiscriminator

    /**
     * Get the account balance.
     *
     * @return account balance
     */
    public final BigDecimal getBalance() {
        return balance;
    } // getBalance

    // setters

    /**
     * Set the account id.
     *
     * @param aId
     *            the account id
     */
    public final void setId(final String aId) {
        this.id = aId;
    } // setId

    /**
     * Set the account type.
     *
     * @param aType
     *            the account type
     */
    public final void setType(final String aType) {
        this.type = aType;
    } // setType

    /**
     * Set the discriminator for the account.
     * @param disc "C" or "S"
     */
    public void setDiscriminator(String disc) {
        this.discriminator = disc;
    } // setDiscriminator
    
    /**
     * Set the account balance.
     *
     * @param aBalance
     *            the account balance
     */
    public final void setBalance(final BigDecimal aBalance) {
        this.balance = aBalance;
    } // setBalance

    // business methods

    /**
     * Deposit funds into an account.
     *
     * @param amount
     *            amount to deposit
     * @return the account balance
     */
    public final BigDecimal deposit(final BigDecimal amount) {
        setBalance(getBalance().add(amount));
        return getBalance();
    } // getBalance

    /**
     * Withdraw funds from an account.
     *
     * @param amount
     *            amount to withdraw
     * @return the account balance
     * @throws InsufficientFundsException
     *            if there are insufficient funds in the account
     */
    public final BigDecimal withdraw(final BigDecimal amount)
            throws InsufficientFundsException {
        if (getBalance().compareTo(amount) == -1) {
            throw new InsufficientFundsException(
                    "Insufficient funds for withdrawal");
        }
        setBalance(getBalance().subtract(amount));
        return getBalance();
    } // withdraw

    /**
     * Compare two Accounts: required to enable sorting of accounts.
     *
     * @param anObject
     *            account to compare to
     * @return 0 if accounts are equal else 0 < n > 0 based on normal string
     *         compareTo method
     */
    public final int compareTo(final Object anObject) {
        Account other = (Account) anObject;
        return getId().compareTo(other.getId());
    } // compareTo

    /**
     * Formatted output of account.
     *
     * @return formatted output
     */
    public final String toString() {
        return "Account " + id + " " + type + " " + balance;
    } // toString

} // Account
