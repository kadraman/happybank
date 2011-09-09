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

package net.sourceforge.happybank.facade;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import net.sourceforge.happybank.exception.BankException;
import net.sourceforge.happybank.model.Account;
import net.sourceforge.happybank.model.Customer;
import net.sourceforge.happybank.model.TransRecord;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;


/**
 * Test Class for Banking Facade.
 * 
 * @author Kevin A. Lee
 * @email kevin.lee@buildmeister.com
 */
final class BankTest {
    
    /**
     * Private constructor.
     */
    private BankTest() {
        // empty
    }
    
    /**
     * Test method.
     * 
     * @param args command line arguments
     */
    public static void main(final String[] args) {
                       
        try {
            // get context and facade
            ApplicationContext ctx = new FileSystemXmlApplicationContext(
                    "build/applicationContext.xml");
            BankingFacade bank = (BankingFacade) ctx.getBean("bankManager");
            
            // get all customers
            List<Customer> customers = bank.getCustomers();
            Iterator<Customer> custIter = customers.iterator();
            Customer cust = null;
            while (custIter.hasNext()) {
                cust = custIter.next();
                String cid = cust.getId();
                Customer customer = bank.getCustomer(cid);
                System.out.println(customer.toString());
                
                // get customers accounts
                List<Account> accounts = bank.getAccounts(cid);
                Iterator<Account> accIter = accounts.iterator();
                Account acc = null;
                while (accIter.hasNext()) {
                    acc = accIter.next();
                    String aid = acc.getId();
                    Account account = bank.getAccount(aid);
                    System.out.println("\t> " + account.toString());
                    
                    // get account transactions
                    List<TransRecord> transactions = bank.getTransactions(aid);
                    Iterator<TransRecord> transIter = transactions.iterator();
                    TransRecord tr = null;
                    while (transIter.hasNext()) {
                        tr = transIter.next();
                        System.out.println("\t\t>" + tr.toString());
                    }
                }
            }
            
            // test creation, deletion and withdrawal
            bank.addCustomer("999", "Mr", "First", "Last");
            bank.addAccount("999-99", "999", "Checking");
            bank.deposit("999-99", new BigDecimal(1000.0));
            bank.withdraw("999-99", new BigDecimal(500.0));
            bank.deleteAccount("999-99");
            bank.deleteCustomer("999");
            
        } catch (BankException ex) {
            ex.printStackTrace();
        }
    } // main
    
} // BankTest
