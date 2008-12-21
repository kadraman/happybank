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
 * JUnit test for customer class
 * 
 * @author
 */
public class TestCustomer extends TestCase {

	private Customer c1;

	/**
	 * constructor for TestCustomer
	 * 
	 * @param arg0
	 */
	public TestCustomer(String arg0) {
		super(arg0);
	}

	protected void setUp() {
		// create some customers
		c1 = new Customer("101");
		c1.setTitle("Mr");
		c1.setFirstName("A");
		c1.setLastName("Customer");
	} // setUp

	public void testGetters() {
		assertEquals(c1.getId(), "101");
		assertEquals(c1.getTitle(), "Mr");
		assertEquals(c1.getFirstName(), "A");
		assertEquals(c1.getLastName(), "Customer");
		c1.setId("102");
		assertEquals(c1.getId(), "102");
	} // testGetters

} // TestCustomer
