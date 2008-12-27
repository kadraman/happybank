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

package test.net.sourceforge.happybank.model;

import net.sourceforge.happybank.model.Customer;
import junit.framework.TestCase;

/**
 * JUnit test for customer class.
 *
 * @author Kevin A. Lee
 * @email kevin.lee@buildmeister.com
 */
public class TestCustomer extends TestCase {

    /**
     * The test customer.
     */
    private Customer c1;

    /**
     * Default constructor for TestCustomer.
     *
     * @param arg0 the argument
     */
    public TestCustomer(final String arg0) {
        super(arg0);
    } // TestCustomer

    /**
     * Setup the customer.
     */
    protected final void setUp() {
        // create a customer
        c1 = new Customer("101");
        c1.setTitle("Mr");
        c1.setFirstName("A");
        c1.setLastName("Customer");
        c1.setUserName("customer");
        c1.setPassword("password");
    } // setUp

    /**
     * Test getters.
     */
    public final void testGetters() {
        assertEquals(c1.getId(), "101");
        assertEquals(c1.getTitle(), "Mr");
        assertEquals(c1.getFirstName(), "A");
        assertEquals(c1.getLastName(), "Customer");
        assertEquals(c1.getUserName(), "customer");
        assertEquals(c1.getPassword(), "password");
        c1.setId("102");
        assertEquals(c1.getId(), "102");
    } // testGetters

} // TestCustomer