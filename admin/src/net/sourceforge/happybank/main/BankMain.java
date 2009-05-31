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

package net.sourceforge.happybank.main;

import java.util.Iterator;
import java.util.List;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import net.sourceforge.happybank.exception.AccountDoesNotExistException;
import net.sourceforge.happybank.exception.BankException;
import net.sourceforge.happybank.facade.BankingFacade;
import net.sourceforge.happybank.model.Account;
import net.sourceforge.happybank.model.Customer;
import net.sourceforge.happybank.tablemodel.AccountData;
import net.sourceforge.happybank.tablemodel.AccountTableModel;


/**
 * Administration client for Bank.
 * 
 * @author Kevin A. Lee
 * @email kevin.lee@buildmeister.com
 */
public class BankMain {

    /**
     * The banking facade.
     */
    private BankingFacade bank = null;
    
    /**
     * default constructor
     */
    public BankMain() {
        // get context and facade
        ApplicationContext ctx = new FileSystemXmlApplicationContext(
                "applicationContext.xml");
        bank = (BankingFacade) ctx.getBean("bankManager");
        initComponents();
        frame.pack();
    } // BankAdminMain

    /**
     * loads the customer account data
     * 
     * @return success (positive) or failure (negative)
     */
    protected int loadAccounts() {
        try {            
            List<Customer> customers = bank.getCustomers();
            Iterator<Customer> custIter = customers.iterator();
            Customer cust = null;
            while (custIter.hasNext()) {
                cust = custIter.next();
                String cid = cust.getId();
                Customer customer = bank.getCustomer(cid);
                List<Account> accounts = bank.getAccounts(cid);
                Iterator<Account> accIter = accounts.iterator();
                Account acc = null;
                while (accIter.hasNext()) {
                    acc = accIter.next();
                    String aid = acc.getId();
                    acc = bank.getAccount(aid);
                    int row = accountEntries.getSelectedRow();
                    if (acc == null)
                        break;
                    accountModel.insert(
                            new AccountData(aid, acc.getType(), 
				    acc.getBalance(),
                                    cust.getFirstName(), 
				    cust.getLastName(), cid), row + 1);
                }
            }

            // refresh table
            accountEntries.tableChanged(new TableModelEvent(accountModel, 0,
                    accountModel.getRowCount(), TableModelEvent.ALL_COLUMNS,
                    TableModelEvent.INSERT));
            accountEntries.repaint();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error reading account data",
                    "Read Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        return 0;
    } // loadFile

    /*
     * edit or view an existing account
     */
    private void onViewAccount() {

        List<Customer> customers = null;
        try {
            customers = bank.getCustomers();
        } catch (BankException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        Account account = null;
        StringBuffer currentCustomerId = new StringBuffer();

        // if we have nothing to delete
        if (accountEntries.getSelectedRowCount() < 1) {
            return;
        }

        // get the selected customer
        int rowSelected = accountEntries.getSelectedRow();
        try {
            currentCustomerId.append(accountEntries.getValueAt(rowSelected, 5)
                    .toString());
            account = bank.getAccount(accountEntries.getValueAt(
                    rowSelected, 0).toString());
        } catch (AccountDoesNotExistException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Unable to read account "
                    + account.getId(), "Account Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (BankException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        AccountViewer ae = new AccountViewer(frame, true, account, customers,
                currentCustomerId, true);
        ae.pack();
        ae.setVisible(true);

    } // onEditAccount

    /*
     * exit the application
     */
    private void onExit() {
        frame.setVisible(false);
        System.exit(0);
    } // onExit

    /*
     * initialise the GUI and event handlers
     */
    private void initComponents() {
        frame = new javax.swing.JFrame();
        menuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuItemExit = new javax.swing.JMenuItem();
        menuActions = new javax.swing.JMenu();
        menuItemView = new javax.swing.JMenuItem();
        separator2 = new javax.swing.JSeparator();
        menuHelp = new javax.swing.JMenu();
        separator4 = new javax.swing.JSeparator();
        menuItemAbout = new javax.swing.JMenuItem();
        accountEntries = new javax.swing.JTable();
        accountModel = new AccountTableModel();

        /*
         * File menu
         */
        menuFile.setMnemonic('F');
        menuFile.setText("File");
        // - Exit option
        menuItemExit.setMnemonic('X');
        menuItemExit.setText("Exit");
        menuItemExit.setActionCommand("Exit");
        menuItemExit.addActionListener(new ActionHandler());
        menuFile.add(menuItemExit);
        menuBar.add(menuFile);

        // Actions menu
        menuActions.setMnemonic('A');
        menuActions.setText("Actions");
        // - View option
        menuItemView.setMnemonic('V');
        menuItemView.setAccelerator(KeyStroke.getKeyStroke('V', Toolkit
                .getDefaultToolkit().getMenuShortcutKeyMask()));
        menuItemView.setText("View");
        menuItemView.setActionCommand("View");
        menuItemView.addActionListener(new ActionHandler());
        menuActions.add(menuItemView);
        menuActions.add(separator2);

        // Help menu
        menuHelp.setMnemonic('H');
        menuHelp.setText("Help");
        // - About option
        menuHelp.add(separator4);
        menuItemAbout.setMnemonic('A');
        menuItemAbout.setText("About");
        menuItemAbout.setActionCommand("About");
        menuItemAbout.addActionListener(new ActionHandler());
        menuHelp.add(menuItemAbout);
        menuBar.add(menuHelp);

        /*
         * configure the TabListCellRenderer
         */
        accountEntries.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        accountEntries.setAutoCreateColumnsFromModel(false);
        accountEntries.setModel(accountModel);
        accountEntries.getTableHeader().setReorderingAllowed(false);
        accountEntries.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        accountEntries.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                // capture single click
                if (evt.getClickCount() == 1
                        && SwingUtilities.isLeftMouseButton(evt)) {
                    // ignore
                }
                // capture double click
                if (evt.getClickCount() == 2) {
                    // edit the account
                    int row = accountEntries.rowAtPoint(evt.getPoint());
                    accountEntries.setRowSelectionInterval(row, row);
                    onViewAccount();
                }
            } // mouseClicked
        }); // MouseAdapter

        // set the column widths and alignment
        for (int k = 0; k < AccountTableModel.COLUMNS.length; k++) {
            TableCellEditor zipper = new DefaultCellEditor(new JTextField());
            DefaultTableCellRenderer textRenderer = new DefaultTableCellRenderer();
            textRenderer
                    .setHorizontalAlignment(AccountTableModel.COLUMNS[k].cAlignment);
            TableColumn column = new TableColumn(k,
                    AccountTableModel.COLUMNS[k].cWidth, textRenderer, zipper);
            accountEntries.addColumn(column);
        }

        // set the table header
        JTableHeader header = accountEntries.getTableHeader();
        header.setUpdateTableInRealTime(false);

        /*
         * create the selection area
         */
        accountPanel = new JPanel();
        accountPanel.setLayout(new BorderLayout());
        scrollPane = new JScrollPane(accountEntries);
        scrollPane
                .setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new java.awt.Dimension(750, 300));
        accountPanel.add(scrollPane);
        frame.getContentPane().add(accountPanel, java.awt.BorderLayout.CENTER);

        /*
         * layout the frame
         */
        frame.setJMenuBar(menuBar);
        frame.setTitle(APP_NAME);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screenSize.width / 2 - 300,
                screenSize.height / 2 - 200);
        // add a listener for the close event
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onExit();
            }
        });

        loadAccounts();
    } // initComponents

    // Generic action event handler class
    class ActionHandler implements ActionListener {
        /**
         * generic action event handler
         * 
         * @param e
         *            action event
         */
        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();
            if (cmd.equals("View")) {
                onViewAccount();
            } else if (cmd.equals("Exit")) {
                onExit();
            } // Exit
        }
    } // ActionHandler

    /**
     * launch the application, parsing any command lines
     */
    public static void main(String[] args) {
        // force System look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception exc) {
            System.err.println("Error loading look and feel: " + exc);
        }

        // fire up the program
        BankMain admin = new BankMain();
        admin.frame.setVisible(true);
    } // main

    /*
     * variables
     */
    private javax.swing.JFrame frame;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenuItem menuItemExit;
    private javax.swing.JMenu menuActions;
    private javax.swing.JMenuItem menuItemView;
    private javax.swing.JSeparator separator2;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JSeparator separator4;
    private javax.swing.JMenuItem menuItemAbout;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JPanel accountPanel;
    private javax.swing.JTable accountEntries;
    private AccountTableModel accountModel;

    /*
     * constants
     */
    static final private String APP_NAME = "HappyBank Admin Client";

} // BankAdminMain
