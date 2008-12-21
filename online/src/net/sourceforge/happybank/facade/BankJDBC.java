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

package net.sourceforge.happybank.facade;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import net.sourceforge.happybank.exception.AccountDoesNotExistException;
import net.sourceforge.happybank.exception.BankException;
import net.sourceforge.happybank.exception.CustomerDoesNotExistException;
import net.sourceforge.happybank.exception.InsufficientFundsException;
import net.sourceforge.happybank.model.Account;
import net.sourceforge.happybank.model.Customer;
import net.sourceforge.happybank.model.TransRecord;

import org.hsqldb.jdbc.jdbcDataSource;

/**
 * The bank functions as a coordinator class for the banking system. It is
 * implemented as a singleton, and is initialised via JDBC to HSQLDB.
 * 
 * @author Kevin A. Lee
 * @email kevin.lee@buildmeister.com
 */
public final class BankJDBC implements Bank {

    /**
     * Controls whether to use JNDI (defaults to true for web application).
     */
    private boolean jndi = true;
    /**
     * Random number generator for timestamps.
     */
    private java.util.Random random = new java.util.Random();
    /**
     * The bank singleton instance.
     */
    private static BankJDBC instance = new BankJDBC();

    /**
     * Gets the Bank instance.
     * 
     * @param jndi
     *            whether to use jndi or not
     * @return the bank instance
     */
    public static BankJDBC getInstance(final boolean jndi) {
        instance.setJndi(jndi);
        return instance;
    }

    /**
     * Are we using JNDI?
     * 
     * @return true if using JNDI, else false.
     */
    public boolean isJndi() {
        return jndi;
    }

    /**
     * Set whether we are using JNDI.
     * 
     * @param j
     *            true to set jndi, else false.
     */
    public void setJndi(final boolean j) {
        this.jndi = j;
    }

    /**
     * Get a specific customer.
     * 
     * @param customerID
     *            the id of the customer
     * @return Customer the customer
     * @throws CustomerDoesNotExistException
     *             if the customer does not exist or cannot be found
     */
    public Customer getCustomer(final String customerID)
            throws CustomerDoesNotExistException {
        if (customerID == null) {
            throw new CustomerDoesNotExistException("Customer number is null");
        }
        Customer customer = getCustomerJDBC(customerID);
        if (customer == null) {
            throw new CustomerDoesNotExistException("Customer not found: "
                    + customerID);
        }
        return customer;
    } // getCustomer

    /**
     * Get a specific customer by their username.
     * 
     * @param username
     *            the username of the customer
     * @return Customer the customer
     * @throws CustomerDoesNotExistException
     *             if the customer does not exist or cannot be found
     */
    public Customer getCustomerByUsername(final String username)
            throws CustomerDoesNotExistException {
        if (username == null) {
            throw new CustomerDoesNotExistException("Customer username is null");
        }
        Customer customer = getCustomerByUsernameJDBC(username);
        if (customer == null) {
            throw new CustomerDoesNotExistException("Customer not found: "
                    + username);
        }
        return customer;
    } // getCustomer

    /**
     * Get the customers of the bank.
     * 
     * @return array of customers
     * @throws BankException
     */
    public Customer[] getCustomers() throws BankException {
        return getCustomersJDBC();
    } // getCustomers

    /**
     * Add a new customer to the bank.
     * 
     * @param customerID
     *            the id of the customer
     * @param title
     *            the title of the customer, i.e. Mr/Mrs
     * @param first
     *            the firstname of the customer
     * @param last
     *            the lastname of the customer
     * @return Customer the customer
     * @throws BankException
     * @throws CustomerDoesNotExistException
     */
    public Customer addCustomer(final String customerID, final String title,
            final String first, final String last) throws BankException {
        if (customerID == null) {
            throw new CustomerDoesNotExistException("Customer number is null");
        }
        Customer customer = addCustomerJDBC(customerID, title, first, last);
        if (customer == null) {
            throw new CustomerDoesNotExistException("Unable to add customer: "
                    + customerID);
        }
        return customer;
    } // addCustomer

    /**
     * delete the specified customer from the bank
     * 
     * @param customerID
     *            the id of the customer
     * @throws CustomerDoesNotExistException
     *             , BankException
     */
    public Customer deleteCustomer(final String customerID)
            throws CustomerDoesNotExistException, BankException {
        if (customerID == null) {
            throw new CustomerDoesNotExistException("Customer number is null");
        }
        deleteCustomerJDBC(customerID);
        return null;
    } // deleteCustomer

    /**
     * get the account with a specific id
     * 
     * @param accountID
     *            account to get
     * @return the account
     * @throws AccountDoesNotExistException
     *             , BankException
     */
    public Account getAccount(final String accountID)
            throws AccountDoesNotExistException, BankException {
        Connection con = connect(true);
        return getAccount(con, accountID);
    } // getAccount

    // internal get Account
    protected Account getAccount(final Connection con, final String accountID)
            throws AccountDoesNotExistException, BankException {
        if (accountID == null) {
            throw new AccountDoesNotExistException("Account number is null");
        }
        Account account = getAccountJDBC(con, accountID);
        if (account == null) {
            throw new AccountDoesNotExistException("Account not found: "
                    + accountID);
        }
        return account;
    } // getAccount

    /**
     * get the accounts of a specific customer
     * 
     * @param customerID
     *            the id of the customer to get accounts of
     * @return array of accounts
     * @throws CustomerDoesNotExistException
     *             , BankException
     */
    public Account[] getAccounts(final String customerID)
            throws CustomerDoesNotExistException, BankException {
        if (customerID == null) {
            throw new CustomerDoesNotExistException("Customer number is null");
        }
        return getAccountsJDBC(customerID);
    } // getAccounts

    /**
     * add an account to the bank
     * 
     * @param accountID
     *            the id of the account
     * @param customerID
     *            the id of the customer
     * @param type
     *            the type of the account
     * @throws CustomerDoesNotExistException
     *             , BankException
     */
    public Account addAccount(final String accountID, final String customerID, final String type)
            throws CustomerDoesNotExistException, BankException {
        if (accountID == null) {
            throw new AccountDoesNotExistException("Account number is null");
        }
        addAccountJDBC(accountID, customerID, type);
        associateJDBC(customerID, accountID);
        Account account = new Account(accountID);
        // account.setType(type);
        return account;
    } // addAccount

    /**
     * delete an account in the bank
     * 
     * @param accountID
     *            the id of the account
     * @throws AccountDoesNotExistException
     *             , BankException
     */
    public Account deleteAccount(final String accountID)
            throws AccountDoesNotExistException, BankException {
        if (accountID == null) {
            throw new AccountDoesNotExistException("Account number is null");
        }
        deleteTransactionsJDBC(accountID);
        String customerID = getAccountOwnerJDBC(accountID);
        unassociateJDBC(customerID, accountID);
        deleteAccountJDBC(accountID);
        return null;
    } // deleteAccount

    /**
     * associate a customer and bank account
     * 
     * @param customerID
     *            id of the customer
     * @param accountID
     *            id of the account
     */
    public void associate(final String customerID, final String accountID)
            throws CustomerDoesNotExistException, AccountDoesNotExistException,
            BankException {
        // TODO Auto-generated method stub
    } // associate

    /**
     * get the customer of a bank account
     * 
     * @param accountID
     *            id of the account
     * @return the id of the customer
     * @throws AccountDoesNotExistException
     *             , CustomerDoesNotExistException
     */
    public String accountOwner(final String accountID)
            throws AccountDoesNotExistException, CustomerDoesNotExistException,
            BankException {
        if (accountID == null) {
            throw new AccountDoesNotExistException("Account number is null");
        }
        String customerID = getAccountOwnerJDBC(accountID);
        if (customerID == null) {
            throw new CustomerDoesNotExistException(
                    "Bank get account owner failed: " + accountID);
        }
        return customerID;
    } // accountOwner

    /**
     * get the transaction records for a specific account
     * 
     * @param accountID
     *            the id of the account
     * @return array of transactions
     * @throws BankException
     */
    public TransRecord[] getTransactions(final String accountID)
            throws AccountDoesNotExistException, BankException {
        if (accountID == null) {
            throw new AccountDoesNotExistException("Account number is null");
        }
        return getTransactionsJDBC(accountID);
    } // getTransactionsJDBC

    /**
     * deposit funds into a specific account
     * 
     * @param accountID
     *            the id of the account
     * @param amount
     *            the amount to deposit
     * @return the balance of the account
     * @throws AccountDoesNotExistException
     *             , BankException
     */
    public BigDecimal deposit(final String accountID, final BigDecimal amount)
            throws AccountDoesNotExistException, BankException {
        Connection con = connect(false);
        Account account = getAccount(con, accountID);
        account.deposit(amount);
        updateAccountJDBC(con, account);
        TransRecord transaction = new TransRecord("C", amount);
        addTransactionJDBC(con, accountID, transaction);
        commit(con);
        return account.getBalance();
    } // deposit

    /**
     * withdraw funds from a specific account
     * 
     * @param accountID
     *            the id of the account
     * @param amount
     *            the amount to withdraw
     * @return the balance of the account
     * @throws AccountDoesNotExistException
     * @throws InsufficientFundsException
     * @throws BankException
     */
    public BigDecimal withdraw(final String accountID, final BigDecimal amount)
            throws AccountDoesNotExistException, InsufficientFundsException,
            BankException {
        Connection con = connect(false);
        Account account = getAccount(con, accountID);
        account.withdraw(amount);
        updateAccountJDBC(con, account);
        TransRecord transaction = new TransRecord("D", amount);
        addTransactionJDBC(con, accountID, transaction);
        commit(con);
        return account.getBalance();
    } // withdraw

    /**
     * transfer funds from one account to another
     * 
     * @param accountID1
     *            id of the first account
     * @param accountID2
     *            id of the second account
     * @param amount
     *            the amount to transfer
     * @return the balance of the first account
     * @throws AccountDoesNotExistException
     * @throws InsufficientFundsException
     * @throws BankException
     */
    public BigDecimal transfer(final String accountID1, final String accountID2,
            final BigDecimal amount) throws AccountDoesNotExistException,
            InsufficientFundsException, BankException {
        Connection con = connect(false);
        Account account1 = getAccount(con, accountID1);
        Account account2 = getAccount(con, accountID2);
        // withdraw from account 1
        account1.withdraw(amount);
        updateAccountJDBC(con, account1);
        TransRecord transaction1 = new TransRecord("D", amount);
        addTransactionJDBC(con, accountID1, transaction1);
        // deposit in account 2
        account2.deposit(amount);
        updateAccountJDBC(con, account2);
        TransRecord transaction2 = new TransRecord("C", amount);
        addTransactionJDBC(con, accountID2, transaction2);
        commit(con);
        return account1.getBalance();
    } // transfer

    /*
     * internal JDBC methods
     */

    protected Customer getCustomerJDBC(final String customerID) {
        Connection con = connect(true);
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String select = "SELECT * FROM CUSTOMER WHERE \"CUSTOMERID\" = ?";
        Customer cust = null;
        try {
            stmt = con.prepareStatement(select);
            stmt.setString(1, customerID);
            rs = stmt.executeQuery();
            while (rs.next()) {
                cust = new Customer();
                cust.setId(customerID);
                cust.setFirstName(rs.getString("FIRSTNAME"));
                cust.setLastName(rs.getString("LASTNAME"));
                cust.setTitle(rs.getString("TITLE"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            cust = null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }
        }
        return cust;
    } // getCustomerJDBC

    protected Customer getCustomerByUsernameJDBC(final String username) {
        Connection con = connect(true);
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String select = "SELECT * FROM CUSTOMER WHERE \"USERID\" = ?";
        Customer cust = null;
        try {
            stmt = con.prepareStatement(select);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            while (rs.next()) {
                cust = new Customer();
                cust.setId(rs.getString("CUSTOMERID"));
                cust.setFirstName(rs.getString("FIRSTNAME"));
                cust.setLastName(rs.getString("LASTNAME"));
                cust.setTitle(rs.getString("TITLE"));
                cust.setUserName(username);
                cust.setPassword(rs.getString("PASSWORD"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            cust = null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }
        }
        return cust;
    } // getCustomerJDBC

    protected Customer[] getCustomersJDBC() {
        Connection con = connect(true);
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String select = "SELECT C.\"CUSTOMERID\", C.\"TITLE\", C.\"FIRSTNAME\", "
                + " C.\"LASTNAME\" FROM CUSTOMER C "
                + "ORDER BY C.\"CUSTOMERID\"";
        Vector v = new Vector();
        Customer ctmp = null;
        try {
            stmt = con.prepareStatement(select);
            rs = stmt.executeQuery();
            while (rs.next()) {
                ctmp = new Customer(rs.getString(1));
                ctmp.setTitle(rs.getString(2));
                ctmp.setFirstName(rs.getString(3));
                ctmp.setLastName(rs.getString(4));
                v.add(ctmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            v = new Vector();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }
        }
        return (Customer[]) v.toArray(new Customer[v.size()]);
    } // getCustomersJDBC

    protected Customer addCustomerJDBC(final String customerID, final String title,
            final String first, final String last) throws BankException {
        Connection con = connect(true);
        PreparedStatement stmt = null;
        String insert = "INSERT INTO CUSTOMER "
                + "(\"CUSTOMERID\", \"TITLE\", \"FIRSTNAME\", \"LASTNAME\", "
                + "\"USERID\", \"PASSWORD\") VALUES (?, ?, ?, ?, ?, ?)";
        int result = 0;
        try {
            stmt = con.prepareStatement(insert);
            stmt.setString(1, customerID);
            stmt.setString(2, title);
            stmt.setString(3, first);
            stmt.setString(4, last);
            stmt.setString(5, "cust" + customerID);
            stmt.setString(6, "password");
            result = stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BankException("JDBC INSERT failed: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (result != 1) {
                    throw new BankException("JDBC INSERT failed: rows="
                            + result);
                }
            } catch (SQLException e) {
            }
        }
        Customer ctmp = new Customer(customerID);
        ctmp.setFirstName(first);
        ctmp.setLastName(last);
        ctmp.setTitle(title);
        ctmp.setId("cust" + customerID);
        return ctmp;
    } // addCustomerJDBC

    protected void deleteCustomerJDBC(final String customerID) throws BankException {
        Connection con = connect(true);
        PreparedStatement stmt = null;
        String insert = "DELETE FROM CUSTOMER WHERE \"CUSTOMERID\" = ?";
        int result = 0;
        try {
            stmt = con.prepareStatement(insert);
            stmt.setString(1, customerID);
            result = stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BankException("JDBC DELETE failed: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (result != 1) {
                    throw new BankException("JDBC DELETE failed: rows="
                            + result);
                }
            } catch (SQLException e) {
            }
        }
    } // deleteCustomerJDBC

    protected void addAccountJDBC(final String accountID, final String customerID,
            final String type) throws BankException {
        Connection con = connect(true);
        PreparedStatement stmt = null;
        String insert = "INSERT INTO ACCOUNT "
                + "(\"ACCID\", \"DISCRIMINATOR\") VALUES (?, ?)";
        int result = 0;
        try {
            stmt = con.prepareStatement(insert);
            stmt.setString(1, accountID);
            stmt.setString(2, type);
            result = stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BankException("JDBC INSERT failed: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (result != 1) {
                    throw new BankException("JDBC INSERT failed: rows="
                            + result);
                }
            } catch (SQLException e) {
            }
        }
    } // addAccountJDBC

    protected void deleteAccountJDBC(final String accountID) throws BankException {
        Connection con = connect(true);
        PreparedStatement stmt = null;
        String insert = "DELETE FROM ACCOUNT WHERE \"ACCID\" = ?";
        int result = 0;
        try {
            stmt = con.prepareStatement(insert);
            stmt.setString(1, accountID);
            result = stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BankException("JDBC DELETE failed: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (result != 1) {
                    throw new BankException("JDBC DELETE failed: rows="
                            + result);
                }
            } catch (SQLException e) {
            }
        }
    } // deleteAccountJDBC

    protected Account getAccountJDBC(final Connection con, final String accountID) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String select = "SELECT * FROM ACCOUNT WHERE \"ACCID\" = ?";
        Account account = null;
        try {
            stmt = con.prepareStatement(select);
            stmt.setString(1, accountID);
            rs = stmt.executeQuery();
            while (rs.next()) {
                account = new Account();
                account.setId(accountID);
                account.setType(rs.getString("ACCTYPE"));
                account.setBalance(rs.getBigDecimal("BALANCE"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            account = null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
            }
        }
        return account;
    } // getAccountJDBC

    protected Account[] getAccountsJDBC(final String customerID) {
        Connection con = connect(true);
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String select = "SELECT A.\"ACCID\", A.\"ACCTYPE\", A.\"BALANCE\" "
                + "FROM ACCOUNT A, CUSTACCT C "
                + "WHERE C.\"CUSTOMERID\" = ? AND C.\"ACCID\" = A.\"ACCID\" "
                + "ORDER BY A.\"ACCID\"";
        Vector v = new Vector();
        Account acct = null;
        try {
            stmt = con.prepareStatement(select);
            stmt.setString(1, customerID);
            rs = stmt.executeQuery();
            while (rs.next()) {
                acct = new Account();
                acct.setId(rs.getString("ACCID"));
                acct.setType(rs.getString("ACCTYPE"));
                acct.setBalance(rs.getBigDecimal("BALANCE"));
                v.add(acct);
            }
        } catch (Exception e) {
            e.printStackTrace();
            v = new Vector();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }
        }
        return (Account[]) v.toArray(new Account[v.size()]);
    } // getAccountsJDBC

    protected String getAccountOwnerJDBC(final String accountID) throws BankException {
        Connection con = connect(true);
        PreparedStatement stmt = null;
        String select = "SELECT \"CUSTOMERID\" FROM CUSTACCT WHERE \"ACCID\" = ?";
        ResultSet rs = null;
        String customerID = null;
        try {
            stmt = con.prepareStatement(select);
            stmt.setString(1, accountID);
            rs = stmt.executeQuery();
            while (rs.next()) {
                customerID = rs.getString("CUSTOMERID");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BankException("JDBC INSERT failed: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }
        }
        return customerID;
    } // associateJDBC

    protected void updateAccountJDBC(final Connection con, final Account account)
            throws BankException {
        PreparedStatement stmt = null;
        String update = "UPDATE ACCOUNT SET \"BALANCE\" = ? WHERE \"ACCID\" = ?";
        int result = 0;
        try {
            stmt = con.prepareStatement(update);
            stmt.setBigDecimal(1, account.getBalance());
            stmt.setString(2, account.getId());
            result = stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BankException("JDBC UPDATE failed: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (result != 1) {
                    throw new BankException("JDBC UPDATE failed: rows= "
                            + result);
                }
            } catch (SQLException e) {
            }
        }
    } // updateAccountJDBC

    protected void associateJDBC(final String customerID, final String accountID)
            throws BankException {
        Connection con = connect(true);
        PreparedStatement stmt = null;
        String insert = "INSERT INTO CUSTACCT (\"CUSTOMERID\", \"ACCID\") VALUES (?, ?)";
        int result = 0;
        try {
            stmt = con.prepareStatement(insert);
            stmt.setString(1, customerID);
            stmt.setString(2, accountID);
            result = stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BankException("JDBC INSERT failed: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (result != 1) {
                    throw new BankException("JDBC INSERT failed: rows="
                            + result);
                }
            } catch (SQLException e) {
            }
        }
    } // associateJDBC

    protected void unassociateJDBC(final String customerID, final String accountID)
            throws BankException {      
        Connection con = connect(true);
        PreparedStatement stmt = null;
        String delete = "DELETE FROM CUSTACCT WHERE (\"ACCID\") = (?)";
        int result = 0;
        try {
            stmt = con.prepareStatement(delete);
            // stmt.setString(1, customerID);
            stmt.setString(1, accountID);
            result = stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BankException("JDBC DELETE failed: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (result != 1) {
                    throw new BankException("JDBC DELETE failed: rows="
                            + result);
                }
            } catch (SQLException e) {
            }
        }
    } // unassociateJDBC

    protected TransRecord[] getTransactionsJDBC(final String accountID)
            throws BankException {
        Connection con = connect(true);
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String select = "SELECT * FROM TRANSRECORD WHERE \"ACCID\" = ? ORDER BY \"TRANSID\"";
        Vector v = new Vector();
        TransRecord trans = null;
        try {
            stmt = con.prepareStatement(select);
            stmt.setString(1, accountID);
            rs = stmt.executeQuery();
            while (rs.next()) {
                trans = new TransRecord();
                Calendar cal = Calendar.getInstance();
                cal.setTime(rs.getDate("TRANSID"));
                trans.setTimeStamp(cal);
                trans.setTransType(rs.getString("TRANSTYPE"));
                trans.setTransAmt(rs.getBigDecimal("TRANSAMT"));
                v.add(trans);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BankException("JDBC SELECT failed: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }
        }
        return (TransRecord[]) v.toArray(new TransRecord[v.size()]);
    } // getTransactionsJDBC

    protected void addTransactionJDBC(final Connection con, final String accountID,
            final TransRecord transaction) throws BankException {
        PreparedStatement stmt = null;
        String insert = "INSERT INTO TRANSRECORD "
                + "(\"TRANSID\", \"ACCID\", \"TRANSTYPE\", \"TRANSAMT\") "
                + "VALUES (?, ?, ?, ?)";
        int result = 0;
        try {
            stmt = con.prepareStatement(insert);
            java.sql.Timestamp ts = new java.sql.Timestamp(transaction
                    .getTimeStamp().getTime().getTime());
            ts.setNanos(ts.getNanos() + random.nextInt(999999));
            stmt.setTimestamp(1, ts);
            stmt.setString(2, accountID);
            stmt.setString(3, transaction.getTransType());
            stmt.setBigDecimal(4, transaction.getTransAmt());
            result = stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BankException("JDBC INSERT failed: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (result != 1) {
                    throw new BankException("JDBC INSERT failed: rows= "
                            + result);
                }
            } catch (SQLException e) {
            }
        }
    } // addTransactionJDBC

    protected void deleteTransactionsJDBC(final String accountID)
            throws BankException {
        Connection con = connect(true);
        PreparedStatement stmt = null;
        String delete = "DELETE FROM TRANSRECORD WHERE (\"ACCID\") = (?)";
        int result = 0;
        try {
            stmt = con.prepareStatement(delete);
            stmt.setString(1, accountID);
            result = stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BankException("JDBC DELETE failed: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (result != 1) {
                    throw new BankException("JDBC DELETE failed: rows= "
                            + result);
                }
            } catch (SQLException e) {
            }
        }
    } // deleteTransactionsJDBC

    protected Connection connect(final boolean autoCommit) {
        Connection conn = null;
        try {
            if (this.jndi) {
                Context initContext = new InitialContext();
                Context envContext = (Context) initContext
                        .lookup("java:/comp/env");
                if (envContext == null) {
                    throw new Exception("Error: No Context");
                }
                DataSource ds = (DataSource) envContext.lookup("jdbc/hbdb");
                if (ds == null) {
                    throw new Exception("Error: No DataSource");
                }
                if (ds != null) {
                    conn = ds.getConnection();
                }
                conn.setAutoCommit(autoCommit);
            } else {
                jdbcDataSource dataSource = new jdbcDataSource();
                dataSource.setDatabase("jdbc:hsqldb:hsql://localhost/xdb");
                conn = dataSource.getConnection("sa", "");
            }
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return conn;
    } // connect

    protected void commit(final Connection con) throws BankException {     
        try {
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BankException("JDBC COMMIT failed: " + e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }
        }
    } // commit

    /*
     * test method
     */
    public static void main(final String[] args) {

        try {
            BankingFacade bf = new BankingFacade();
            bf.setJndi(false);
            Customer[] customers = bf.getCustomers();
            for (int i = 0; i < customers.length; i++) {
                String id = customers[i].getId();
                Customer customer = bf.getCustomer(id);
                System.out.println(customer.toString());
                Account[] accounts = bf.getAccounts(id);
                for (int j = 0; j < accounts.length; j++) {
                    String accid = accounts[j].getId();
                    Account account = bf.getAccount(accid);
                    System.out.println(account.toString());
                    TransRecord[] transrecords = bf.getTransactions(accid);
                    for (int k = 0; k < transrecords.length; k++) {
                        System.out.println(transrecords[k].toString());
                    }
                }
            }
        } catch (BankException ex) {
            ex.printStackTrace();
        }
    }

} // BankJDBC

