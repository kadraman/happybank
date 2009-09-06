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

import java.math.BigDecimal;

import junit.framework.TestCase;
import net.sourceforge.happybank.model.TransRecord;

import org.joda.time.DateTime;

/**
 * JUnit test for TransRecord class.
 *
 * @author
 */
public class TestTransRecord extends TestCase {

    /**
     * Test transrecord.
     */
    private TransRecord t1;

    /**
     * Default constructor for TestTransRecord.
     *
     * @param arg0 the parameter
     */
    public TestTransRecord(final String arg0) {
        super(arg0);
    } // TestTransRecord

    /**
     * Setup the transrecord.
     */
    protected final void setUp() {
        // create a transaction record
        t1 = new TransRecord("C", new BigDecimal("50.25"));
        t1.setTimeStamp(new DateTime());
    } // setUp

    /**
     * Test getters.
     */
    public final void testGetters() {
        assertTrue(!t1.equals(null));
        BigDecimal amount = new BigDecimal("50.25");
        assertEquals(t1.getTransType(), "C");
        assertEquals(t1.getTransAmt(), amount);
        t1.setTransType("D");
        assertEquals(t1.getTransType(), "D");
        DateTime d1 = new DateTime();
        t1.setTimeStamp(d1);
        assertEquals(t1.getTimeStamp(), d1);
    } // testGetters

    /**
     * Test compare.
     */
    public final void testCompare() {
        DateTime d1 = new DateTime();
        DateTime d2 = new DateTime();
        d2.plusMinutes(5);
        t1.setTimeStamp(d1);
        TransRecord t2 = new TransRecord("C", new BigDecimal("50"));
        t2.setTimeStamp(d2);
        TransRecord t3 = new TransRecord("D", new BigDecimal("100"));
        t3.setTimeStamp(d2);
        assertEquals(t2.compareTo(t1), 1);
        assertEquals(t2.compareTo(t3), 1);
    } // testCompare

} // TestTransRecord
