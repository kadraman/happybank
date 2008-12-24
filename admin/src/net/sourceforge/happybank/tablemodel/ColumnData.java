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

/**
 * Encapsulation of a column for table layout.
 * 
 * @author Kevin A. Lee
 * @email kevin.lee@buildmeister.com
 */
public class ColumnData {

	public String cTitle;
	public int cWidth;
	public int cAlignment;

	/**
	 * parameterised constructor
	 * 
	 * @param title
	 *            text title of column
	 * @param width
	 *            width of column
	 * @param alignment
	 *            alignment of text
	 */
	public ColumnData(String title, int width, int alignment) {
		cTitle = title;
		cWidth = width;
		cAlignment = alignment;
	} // ColumnData

} // ColumnData
