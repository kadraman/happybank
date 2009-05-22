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
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import net.sourceforge.happybank.exception.BankException;
import net.sourceforge.happybank.facade.BankingFacade;
import net.sourceforge.happybank.model.Account;
import net.sourceforge.happybank.model.Customer;


/**
 * Allow a customer account be viewed.
 * 
 * @author Kevin A. Lee
 * @email kevin.lee@buildmeister.com
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
	private List<Customer> customers;
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
			List<Customer> customers, StringBuffer currentCustomerID,
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
		String[] custIds = new String[customers.size()];
		Iterator<Customer> custIter = customers.iterator();
        Customer cust = null;
        int i = 0;
        while (custIter.hasNext()) {
            cust = custIter.next();
			custIds[i++] = cust.getId();
			if (custIds[i].equals(currentCustomerId.toString())) {
				custName = cust.getTitle() + " "
						+ cust.getFirstName() + " "
						+ cust.getLastName();
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

} // AccountViewer
