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

/**
 * Java bean representing a customer account mapping.
 * 
 * @author Kevin A. Lee
 * @email kevin.lee@buildmeister.com
 */
public class CustomerAccount implements Serializable {
    
    /**
     * Generated serialization identifier.
     */
    private static final long serialVersionUID = 1L;
    /**
     * The id of the customer.
     */
    private String            cid;
    /**
     * The id of the account.
     */
    private String            aid;
    
    /**
     * Default no-arg constructor.
     */
    public CustomerAccount() {
        super();
    } // CustomerAccount
    
    /**
     * Default constructor to create new customer account mapping.
     * 
     * @param custid customer id
     * @param accid account id
     */
    public CustomerAccount(final String custid, final String accid) {
        setCustomerId(custid);
        setAccountId(accid);
    } // Customer
    
    /**
     * Get the customer id.
     * 
     * @return customer id
     */
    public final String getCustomerId() {
        return cid;
    } // getCustomerId
    
    /**
     * Get the account id.
     * 
     * @return account id
     */
    public final String getAccountId() {
        return aid;
    } // getAccountId
    
    /**
     * Set customer id.
     * 
     * @param id customer id
     */
    public final void setCustomerId(final String id) {
        this.cid = id;
    } // setCustomerId
    
    /**
     * Set account id.
     * 
     * @param id account id
     */
    public final void setAccountId(final String id) {
        this.aid = id;
    } // setAccountId
    
    /**
     * Produce a formated output of the customer account mapping.
     * 
     * @return formatted customer account mapping
     */
    public final String toString() {
        return "Customer " + cid + ", Account " + aid;
    } // toString
    
} // CustomerAccount
