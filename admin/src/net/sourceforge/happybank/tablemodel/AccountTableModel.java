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

package net.sourceforge.happybank.tablemodel;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

/**
 * Table model for representing customer accounts.
 * 
 * @author Kevin A. Lee
 * @email kevin.lee@buildmeister.com
 */
public class AccountTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	// table headings
	public static final ColumnData[] COLUMNS = {
			new ColumnData("Account Id", 100, JLabel.LEFT),
			new ColumnData("Type", 100, JLabel.LEFT),
			new ColumnData("Balance", 150, JLabel.RIGHT),
			new ColumnData("First Name", 150, JLabel.LEFT),
			new ColumnData("Last Name", 150, JLabel.LEFT),
			new ColumnData("Customer ID", 100, JLabel.LEFT) };

	public static final int COL_ID = 0;
	public static final int COL_TYPE = 1;
	public static final int COL_BALANCE = 2;
	public static final int COL_FIRST = 3;
	public static final int COL_LAST = 4;
	public static final int COL_CUST_ID = 5;

	private SimpleDateFormat mFrm;
	private Vector mVector;

	private int sortedColumnIndex = -1;
	private boolean sortedColumnAscending = false;

	/**
	 * default constructor
	 */
	public AccountTableModel() {
		mFrm = new SimpleDateFormat("MM/dd/yy hh:mm");
		mVector = new Vector();
		mVector.removeAllElements();
	} // AccountTableModel

	/**
	 * @return Returns the mFrm.
	 */
	public SimpleDateFormat getMFrm() {
		return mFrm;
	}

	/**
	 * @param frm
	 *            The mFrm to set.
	 */
	public void setMFrm(SimpleDateFormat frm) {
		mFrm = frm;
	}

	/**
	 * @return Returns the mVector.
	 */
	public Vector getMVector() {
		return mVector;
	}

	/**
	 * @param vector
	 *            The mVector to set.
	 */
	public void setMVector(Vector vector) {
		mVector = vector;
	}

	/**
	 * @return Returns the sortedColumnAscending.
	 */
	public boolean isSortedColumnAscending() {
		return sortedColumnAscending;
	}

	/**
	 * @param sortedColumnAscending
	 *            The sortedColumnAscending to set.
	 */
	public void setSortedColumnAscending(boolean sortedColumnAscending) {
		this.sortedColumnAscending = sortedColumnAscending;
	}

	/**
	 * @return Returns the sortedColumnIndex.
	 */
	public int getSortedColumnIndex() {
		return sortedColumnIndex;
	}

	/**
	 * @param sortedColumnIndex
	 *            The sortedColumnIndex to set.
	 */
	public void setSortedColumnIndex(int sortedColumnIndex) {
		this.sortedColumnIndex = sortedColumnIndex;
	}

	/**
	 * get the number of rows in the model
	 * 
	 * @return number of rows in model
	 */
	public int getRowCount() {
		return mVector == null ? 0 : mVector.size();
	} // getRowCount

	/**
	 * get the number of columns in the model
	 * 
	 * @return number of colums in the model
	 */
	public int getColumnCount() {
		return COLUMNS.length;
	} // getColumnCount

	/**
	 * get the name of a specific column
	 * 
	 * @param column
	 *            column to get name of
	 * @return the name of the column
	 */
	public String getColumnName(int column) {
		return COLUMNS[column].cTitle;
	} // getColumnName

	/**
	 * get whether cell is editable - overriden to return false
	 * 
	 * @param nRow
	 *            row
	 * @param nCol
	 *            column
	 * @return false
	 */
	public boolean isCellEditable(int nRow, int nCol) {
		return false;
	} // isCellEditable

	/**
	 * get the object in a particular cell
	 * 
	 * @param nRow
	 *            row
	 * @param nCol
	 *            column
	 * @return object at specified row and column
	 */
	public Object getValueAt(int nRow, int nCol) {
		if (nRow < 0 || nRow >= getRowCount())
			return "";
		AccountData row = (AccountData) mVector.elementAt(nRow);
		switch (nCol) {
		case COL_ID:
			return row.cId;
		case COL_TYPE:
			return row.cType;
		case COL_BALANCE:
			return row.cBalance;
		case COL_FIRST:
			return row.cFirstName;
		case COL_LAST:
			return row.cLastName;
		case COL_CUST_ID:
			return row.cCustomerId;
		default:
		}
		return "";
	} // getValueAt

	/**
	 * set the object in a particular cell
	 * 
	 * @param nRow
	 *            row
	 * @param nCol
	 *            column
	 * @param value
	 *            object to set
	 */
	public void setValueAt(Object value, int nRow, int nCol) {
		if (nRow < 0 || nRow >= getRowCount())
			return;
		AccountData row = (AccountData) mVector.elementAt(nRow);
		String svalue = value.toString();

		switch (nCol) {
		case COL_ID:
			row.cId = svalue;
			break;
		case COL_TYPE:
			row.cType = svalue;
			break;
		case COL_BALANCE:
			try {
				row.cBalance = new BigDecimal(svalue);
			} catch (NumberFormatException e) {
				break;
			}
			break;
		case COL_FIRST:
			row.cFirstName = svalue;
			break;
		case COL_LAST:
			row.cLastName = svalue;
			break;
		case COL_CUST_ID:
			row.cCustomerId = svalue;
			break;
		default:
		}
	} // setObjectAt

	/**
	 * insert an object at a specific row
	 * 
	 * @param obj
	 *            object
	 * @param row
	 *            row
	 */
	public void insert(Object obj, int row) {
		if (row < 0)
			row = 0;
		if (row > mVector.size())
			row = mVector.size();
		mVector.insertElementAt(obj, row);
	} // insert

} // AccountTableModel
