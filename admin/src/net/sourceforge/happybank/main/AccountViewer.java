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

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import net.sourceforge.happybank.exception.BankException;
import net.sourceforge.happybank.facade.BankingFacade;
import net.sourceforge.happybank.model.Account;
import net.sourceforge.happybank.model.Customer;


/**
 * allow a customer account be viewed
 * 
 * @author
 */
public class AccountViewer extends JDialog {

	private static final long serialVersionUID = 1L;
	private String sCMDOK = "cmd.ok";
	private JButton okButton = null;
	private JTextField idTextField;
	private JTextField custNameTextField;
	private JTextField balanceTextField;
	private JComboBox accTypeComboBx;
	private JComboBox custIdComboBx;

	private Account account;
	private Customer[] customers;
	private StringBuffer currentCustomerId;
	private boolean readOnly;

	/**
	 * Creates new AccountViewer
	 * 
	 * @param parent
	 *            the frame which owns the dialog
	 * @param modal
	 *            whether the dialog should be made modal or not
	 * @param account
	 *            the acount to edit
	 * @param readOnly
	 *            whether the account is editable or not
	 */
	public AccountViewer(Frame parent, boolean modal, Account account,
			Customer[] customers, StringBuffer currentCustomerID,
			boolean readOnly) {
		super(parent, modal);
		this.account = account;
		this.customers = customers;
		this.currentCustomerId = currentCustomerID;
		this.readOnly = readOnly;
		initComponents();
		pack();
		centerWindow();
	} // AccountViewer

	/*
	 * Centers the window on the screen.
	 */
	private void centerWindow() {
		Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit()
				.getScreenSize());
		Point center = new Point((int) screen.getCenterX(), (int) screen
				.getCenterY());
		Point newLocation = new Point(center.x - this.getWidth() / 2, center.y
				- this.getHeight() / 2);
		if (screen.contains(newLocation.x, newLocation.y, this.getWidth(), this
				.getHeight())) {
			this.setLocation(newLocation);
		}
	} // centerWindow()

	/**
	 * draw the FileProperties
	 */
	private void initComponents() {

		// set properties of window
		Container contents = getContentPane();
		contents.setLayout(new BorderLayout());
		setTitle("Account Viewer");
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				dialogDone(sCMDOK);
			}
		});
		getAccessibleContext().setAccessibleDescription("Account Viewer");

		// add the labels
		JPanel labelPanel = new JPanel(new GridLayout(6, 2, 10, 10));
		Border emptyBdr = BorderFactory.createEmptyBorder(15, 15, 15, 15);
		labelPanel.setBorder(emptyBdr);

		// account id
		JLabel idLabel = new JLabel("ID:");
		labelPanel.add(idLabel);
		idTextField = new JTextField(account.getId());
		idTextField.setSize(200, 20);
		labelPanel.add(idTextField);

		// account type
		JLabel typeLabel = new JLabel("Type:");
		labelPanel.add(typeLabel);
		String[] items = { "Current", "Savings" };
		accTypeComboBx = new JComboBox(items);
		for (int i = 0; i < items.length; i++) {
			if (account.getType().equals(items[i])) {
				accTypeComboBx.setSelectedIndex(i);
			}
		}
		accTypeComboBx.setSize(200, 20);
		labelPanel.add(accTypeComboBx);

		// owner
		int selItem = 0;
		String custName = null;
		JLabel ownerIdLabel = new JLabel("Owner ID:");
		labelPanel.add(ownerIdLabel);
		String[] custIds = new String[customers.length];
		for (int i = 0; i < customers.length; i++) {
			custIds[i] = customers[i].getId();
			if (custIds[i].equals(currentCustomerId.toString())) {
				custName = customers[i].getTitle() + " "
						+ customers[i].getFirstName() + " "
						+ customers[i].getLastName();
				selItem = i;
			}
		}
		custIdComboBx = new JComboBox(custIds);
		custIdComboBx.setSelectedIndex(selItem);
		custIdComboBx.setSize(200, 20);

		labelPanel.add(custIdComboBx);
		JLabel ownerNameLabel = new JLabel("Owner name:");
		labelPanel.add(ownerNameLabel);
		custNameTextField = new JTextField(custName);
		custNameTextField.setEnabled(false);
		custNameTextField.setSize(200, 20);
		labelPanel.add(custNameTextField);

		// balance
		JLabel balanceLabel = new JLabel("Balance:");
		labelPanel.add(balanceLabel);
		balanceTextField = new JTextField(account.getBalance().toString());
		balanceTextField.setSize(200, 20);
		labelPanel.add(balanceTextField);

		// are we in read only mode
		if (readOnly) {
			idTextField.setEnabled(false);
			custNameTextField.setEnabled(false);
			balanceTextField.setEnabled(false);
			accTypeComboBx.setEnabled(false);
			custIdComboBx.setEnabled(false);
		}

		contents.add(labelPanel, BorderLayout.NORTH);

		// ok button
		JPanel butPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		okButton = new JButton();
		okButton.setText("OK");
		okButton.setActionCommand(sCMDOK);
		butPanel.add(okButton);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				dialogDone(event);
			}
		});
		contents.add(butPanel, BorderLayout.SOUTH);

		getRootPane().setDefaultButton(okButton);

	} // initComponents()

	/**
	 * Act on the dialog event
	 * 
	 * @param actionCommand
	 *            may be null
	 */
	private void dialogDone(Object actionCommand) {
		String cmd = null;
		if (actionCommand != null) {
			if (actionCommand instanceof ActionEvent) {
				cmd = ((ActionEvent) actionCommand).getActionCommand();
			} else {
				cmd = actionCommand.toString();
			}
		}
		if (cmd == null) {
			// do nothing
		} else if (cmd.equals(sCMDOK)) {
			// don't need to do anything here
		}
		setVisible(false);
		dispose();
	} // dialogDone()

	/**
	 * This main() is provided for debugging purposes, to display a sample
	 * dialog.
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame() {

			private static final long serialVersionUID = 1L;

			public Dimension getPreferredSize() {
				return new Dimension(200, 100);
			}
		};
		frame.setTitle("Debugging frame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(false);

		StringBuffer mcurrentCustomerId = new StringBuffer("101");
		String mcurrentAccountId = "101-1001";
		Account maccount = null;
		BankingFacade mbank = new BankingFacade();
		Customer[] mcustomers = null;
		try {
			mcustomers = mbank.getCustomers();
		} catch (BankException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Account[] accounts = mbank.getAccounts("101");
			for (int i = 0; i < accounts.length; i++) {
				if (accounts[i].getId().equals(mcurrentAccountId)) {
					maccount = accounts[i];
					break;
				}
			}
		} catch (Exception e) {
		}

		AccountViewer dialog = new AccountViewer(frame, true, maccount,
				mcustomers, mcurrentCustomerId, false);
		dialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				System.exit(0);
			}

			public void windowClosed(WindowEvent event) {
				System.exit(0);
			}
		});
		dialog.pack();
		dialog.setVisible(true);

		try {
			mbank.associate(mcurrentCustomerId.toString(), maccount.getId());
		} catch (BankException e) {
		}
		System.out.println("Account is: " + maccount.getId() + " "
				+ maccount.getType() + " " + maccount.getBalance()
				+ " owned by " + mcurrentCustomerId);
	} // main()

} // AccountViewer
