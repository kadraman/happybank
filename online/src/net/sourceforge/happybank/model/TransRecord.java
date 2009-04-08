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

package net.sourceforge.happybank.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Java bean representing an account transaction.
 *
 * @author Kevin A. Lee
 * @email kevin.lee@buildmeister.com
 */

public class TransRecord implements Comparable, Serializable {

    /**
     * Generated serialization identifier.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Timestamp of the transaction.
     */
    private Calendar timeStamp;
    /**
     * The type of the transaction, e.g. credit or debit.
     */
    private String transType;
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
     * @param type
     *            type of transaction (credit or debit)
     * @param amount
     *            amount of transaction
     */
    public TransRecord(final String type, final BigDecimal amount) {
        setTimeStamp(Calendar.getInstance());
        setTransType(type);
        setTransAmt(amount);
    } // TransRecord

    /**
     * Get time stamp of transaction.
     *
     * @return time stamp of transaction
     */
    public final Calendar getTimeStamp() {
        return timeStamp;
    } // getTimeStamp

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
     * @param aTimeStamp
     *            time stamp of transaction
     */
    public final void setTimeStamp(final Calendar aTimeStamp) {
        this.timeStamp = aTimeStamp;
    } // setTimeStamp

    /**
     * Set transaction type.
     *
     * @param aTransType
     *            type of transaction (credit or debit)
     */
    public final void setTransType(final String aTransType) {
        this.transType = aTransType;
    } // setTransType

    /**
     * Set the amount of the transaction.
     *
     * @param aTransAmt
     *            amount of the transaction
     */
    public final void setTransAmt(final BigDecimal aTransAmt) {
        this.transAmt = aTransAmt;
    } // setTransAmt

    /**
     * Compare two TransRecords: required to enable sorting of transactions.
     *
     * @param anObject
     *            transaction to compare with
     * @return 1 if transaction records are equal else -1
     */
    public final int compareTo(final Object anObject) {
        TransRecord other = (TransRecord) anObject;
        if (getTimeStamp().before(other.getTimeStamp())) {
            return -1;
        } else {
            return 1;
        }
    } // compareTo

    /**
     * Formatted output of account.
     *
     * @return formatted output
     */
    public final String toString() {
        return "TransRect " + timeStamp + " " + transType + " " + transAmt;
    } // toString

} // TransRecord
