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

/**
 * Simple model of a customer
 *
 * @author Kevin A. Lee
 * @email kevin.lee@buildmeister.com
 */
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    // The customer business data
    private String id;
    private String title;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;

    /**
     * default constructor
     * 
     */
    public Customer() {
        super();
    } // Customer

    /**
     * constructor with id
     * 
     * @param customerid
     *            unique identifier for the customer
     */
    public Customer(final String customerid) {
        setId(customerid);
    } // Customer

    /**
     * get the customer id
     * 
     * @return customer id
     */
    public final String getId() {
        return id;
    } // getId

    /**
     * get customers first name
     * 
     * @return customers first name
     */
    public final String getFirstName() {
        return firstName;
    } // getFirstName

    /**
     * get customers last name
     * 
     * @return customers last name
     */
    public final String getLastName() {
        return lastName;
    } // getLastName

    /**
     * get customers title
     * 
     * @return customers title, i.e. Mr/Mrs
     */
    public final String getTitle() {
        return title;
    } // getTitle

    public final String getUserName() {
        return userName;
    }

    public final String getPassword() {
        return password;
    }

    /**
     * set customers first name
     * 
     * @param firstName
     *            customers first name
     */
    public final void setFirstName(final String firstName) {
        this.firstName = firstName;
    } // setFirstName

    /**
     * set customers last name
     * 
     * @param lastName
     *            customers last name
     */
    public final void setLastName(final String lastName) {
        this.lastName = lastName;
    } // setLastName

    /**
     * set customers title
     * 
     * @param title
     *            customers title, i.e. Mr/Mrs
     */
    public final void setTitle(final String title) {
        this.title = title;
    } // setTitle

    /**
     * set customers unique id
     * 
     * @param id
     *            customers unique id
     */
    public final void setId(final String id) {
        this.id = id;
    } // setId

    public final void setUserName(final String userName) {
        this.userName = userName;
    }

    public final void setPassword(final String password) {
        this.password = password;
    }

    /**
     * produce a formated output of the customer
     * 
     * @return formatted customer
     */
    public final String toString() {
        return "Customer " + id + " " + title + " " + firstName + " "
                + lastName;
    } // toString

} // Customer

