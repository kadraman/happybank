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
 * Java bean representing a customer.
 *
 * @author Kevin A. Lee
 * @email kevin.lee@buildmeister.com
 */
public class Customer implements Serializable {

    /**
     * Generated serialization identifier.
     */
    private static final long serialVersionUID = 1L;
    /**
     * The id of the customer.
     */
    private String id;
    /**
     * The title of the customer, e.g. Mr/Mrs.
     */
    private String title;
    /**
     * The first (christian) name of the customer.
     */
    private String firstName;
    /**
     * The last (surname) of the customer.
     */
    private String lastName;
    /**
     * The customer's username for login.
     */
    private String userName;
    /**
     * The customer's password for login.
     */
    private String password;

    /**
     * Default no-arg constructor.
     *
     */
    public Customer() {
        super();
    } // Customer

    /**
     * Default constructor to create new customer.
     *
     * @param customerid
     *            unique identifier for the customer
     */
    public Customer(final String customerid) {
        setId(customerid);
    } // Customer

    /**
     * Get the customer id.
     *
     * @return customer id
     */
    public final String getId() {
        return id;
    } // getId

    /**
     * Get customers first name.
     *
     * @return customers first name
     */
    public final String getFirstName() {
        return firstName;
    } // getFirstName

    /**
     * Get customers last name.
     *
     * @return customers last name
     */
    public final String getLastName() {
        return lastName;
    } // getLastName

    /**
     * Get customers title.
     *
     * @return customers title, e.g. Mr/Mrs
     */
    public final String getTitle() {
        return title;
    } // getTitle

    /**
     * Get customers username.
     *
     * @return the customers username
     */
    public final String getUserName() {
        return userName;
    } // getUserName

    /**
     * Get the customers password.
     *
     * @return the customers password
     */
    public final String getPassword() {
        return password;
    } // getPassword

    /**
     * Set customers first name.
     *
     * @param aFirstName
     *            customers first name
     */
    public final void setFirstName(final String aFirstName) {
        this.firstName = aFirstName;
    } // setFirstName

    /**
     * Set customers last name.
     *
     * @param aLastName
     *            customers last name
     */
    public final void setLastName(final String aLastName) {
        this.lastName = aLastName;
    } // setLastName

    /**
     * Set customers title.
     *
     * @param aTitle
     *            customers title, i.e. Mr/Mrs
     */
    public final void setTitle(final String aTitle) {
        this.title = aTitle;
    } // setTitle

    /**
     * Set customers unique id.
     *
     * @param aId
     *            customers unique id
     */
    public final void setId(final String aId) {
        this.id = aId;
    } // setId

    /**
     * Set the customers username.
     *
     * @param aUserName
     *            customer username
     */
    public final void setUserName(final String aUserName) {
        this.userName = aUserName;
    } // setUserName

    /**
     * Set the customers password.
     *
     * @param aPassword
     *            customers password
     */
    public final void setPassword(final String aPassword) {
        this.password = aPassword;
    }

    /**
     * Produce a formated output of the customer.
     *
     * @return formatted customer
     */
    public final String toString() {
        return "Customer " + id + " " + title + " " + firstName + " "
                + lastName;
    } // toString

} // Customer

