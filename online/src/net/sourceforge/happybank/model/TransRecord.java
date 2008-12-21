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
 * The banking transaction record model class.
 * 
 * @author
 */

public class TransRecord implements Comparable, Serializable {

    private static final long serialVersionUID = 1L;
    // Business data
    private Calendar timeStamp;
    private String transType;
    private java.math.BigDecimal transAmt;

    /**
     * default no-arg constructor
     */
    public TransRecord() {
        super();
    }

    /**
     * default parameterised constructor
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
    }

    // getters

    /**
     * get time stamp of transaction
     * 
     * @return time stamp of transaction
     */
    public final Calendar getTimeStamp() {
        return timeStamp;
    }

    /**
     * get transaction type
     * 
     * @return transaction type (credit or debit)
     */
    public final String getTransType() {
        return transType;
    }

    /**
     * get the amount of the transaction
     * 
     * @return amount of transaction
     */
    public final java.math.BigDecimal getTransAmt() {
        return transAmt;
    }

    // setters

    /**
     * set time stamp of transaction
     * 
     * @param timeStamp
     *            time stamp of transaction
     */
    public final void setTimeStamp(final Calendar timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * set transaction type
     * 
     * @param transType
     *            type of transaction (credit or debit)
     */
    public final void setTransType(final String transType) {
        this.transType = transType;
    }

    /**
     * set the amount of the transaction
     * 
     * @param transAmt
     *            amount of the transaction
     */
    public final void setTransAmt(final java.math.BigDecimal transAmt) {
        this.transAmt = transAmt;
    }

    /**
     * compare two TransRecord: required to enable sorting of transactions
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
     * formatted output of account
     * 
     * @return formatted output
     */
    public final String toString() {
        return "TransRect " + timeStamp + " " + transType + " " + transAmt;
    } // toString

} // TransRecord

// Fake update
