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

import java.math.BigDecimal;
import java.util.Calendar;

import net.sourceforge.happybank.model.TransRecord;

import junit.framework.TestCase;


/**
 * JUnit test for TransRecord class
 * 
 * @author
 */
public class TestTransRecord extends TestCase {

	private TransRecord t1;

	/**
	 * constructor for TestTransRecord
	 * 
	 * @param arg0
	 */
	public TestTransRecord(String arg0) {
		super(arg0);
	}

	protected void setUp() {
		// create some transaction records
		t1 = new TransRecord("C", new BigDecimal("50.25"));
		t1.setTimeStamp(Calendar.getInstance());
	} // setUp

	public void testGetters() {
		assertTrue(!t1.equals(null));
		BigDecimal amount = new BigDecimal("50.25");
		assertEquals(t1.getTransType(), "C");
		assertEquals(t1.getTransAmt(), amount);
		t1.setTransType("D");
		assertEquals(t1.getTransType(), "D");
		Calendar c1 = Calendar.getInstance();
		t1.setTimeStamp(c1);
		assertEquals(t1.getTimeStamp(), c1);
	} // testGetters

	public void testCompare() {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.add(Calendar.MINUTE, +5);
		t1.setTimeStamp(c1);
		TransRecord t2 = new TransRecord("C", new BigDecimal("50"));
		t2.setTimeStamp(c2);
		TransRecord t3 = new TransRecord("D", new BigDecimal("100"));
		t3.setTimeStamp(c2);
		assertEquals(t2.compareTo(t1), -1);
		assertEquals(t2.compareTo(t3), 1);
	}

} // TestTransRecord
