/*
 * Copyright 2005-2010 Kevin A. Lee
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

import org.joda.time.DateTime;

/**
 * Java bean representing an account transaction.
 * 
 * @author Kevin A. Lee
 * @email me@kevinalee.com
 */

public class TransRecord implements Comparable, Serializable {
    
    /**
     * Generated serialization identifier.
     */
    private static final long    serialVersionUID = 1L;
    /**
     * Transaction id
     */
    private Integer              id;
    /**
     * Timestamp of the transaction.
     */
    private DateTime             transDate;
    /**
     * The account the transaction is being applied to.
     */
    private String               accid;
    /**
     * The type of the transaction, e.g. credit or debit.
     */
    private String               transType;
    /**
     * The amount of the transaction.
     */
    private java.math.BigDecimal transAmt;
    
    /**
     * Default no-arg constructor.
     */
    public TransRecord() {
        super();
    } // TransRecord
    
    /**
     * Default constructor to create new transaction.
     * 
     * @param type type of transaction (credit or debit)
     * @param amount amount of transaction
     */
    public TransRecord(final String type, final BigDecimal amount) {
        setTransDate(new DateTime());
        setTransType(type);
        setTransAmt(amount);
    } // TransRecord

    /**
     * Get id of transaction
     */
    public final Integer getId() {
        return id;
    } // getId

    /**
     * Get time stamp of transaction.
     * 
     * @return time stamp of transaction
     */
    public final DateTime getTransDate() {
        return transDate;
    } // getTransDate
    
    /**
     * Get the account the transaction has been applied to.
     * 
     * @return the account id
     */
    public String getAccid() {
        return accid;
    } // getAccid
    
    /**
     * Get transaction type.
     * 
     * @return transaction type (credit or debit)
     */
    public final String getTransType() {
        return transType;
    } // getTransType
    
    /**
     * Get the amount of the transaction.
     * 
     * @return amount of transaction
     */
    public final java.math.BigDecimal getTransAmt() {
        return transAmt;
    } // getTransAmt
    
    /**
     * Set time stamp of transaction.
     * 
     * @param aTimeStamp time stamp of transaction
     */
    public final void setTransDate(final DateTime aTimeStamp) {
        this.transDate = aTimeStamp;
    } // setTimeStamp
    
    /**
     * Set the account the transactions is being applied to.
     * 
     * @param aid the account
     */
    public void setAccid(String aid) {
        this.accid = aid;
    } // setAccid
    
    /**
     * Set transaction type.
     * 
     * @param aTransType type of transaction (credit or debit)
     */
    public final void setTransType(final String aTransType) {
        this.transType = aTransType;
    } // setTransType
    
    /**
     * Set the amount of the transaction.
     * 
     * @param aTransAmt amount of the transaction
     */
    public final void setTransAmt(final BigDecimal aTransAmt) {
        this.transAmt = aTransAmt;
    } // setTransAmt
    
    /**
     * Compare two TransRecords: required to enable sorting of transactions.
     * 
     * @param anObject transaction to compare with
     * @return 1 if transaction records are equal else -1
     */
    @Override
    public final int compareTo(final Object anObject) {
        TransRecord other = (TransRecord) anObject;
        if (getTransDate().isEqual(other.getTransDate())) {
            return 1;
        } else {
            return -1;
        }
    } // compareTo
    
    /**
     * Formatted output of account.
     * 
     * @return formatted output
     */
    @Override
    public final String toString() {
        return "TransRect " + id + " : " + transDate + " " + transType + " " + transAmt;
    } // toString
    
} // TransRecord
