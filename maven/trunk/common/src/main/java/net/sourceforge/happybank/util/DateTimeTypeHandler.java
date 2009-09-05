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

package net.sourceforge.happybank.util;

import java.sql.SQLException;
import java.sql.Timestamp;

import org.joda.time.DateTime;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

/**
 * DateTime handler for iBatis.
 *
 * @author Kevin A. Lee
 * @email kevin.lee@buildmeister.com
 */
public class DateTimeTypeHandler implements TypeHandlerCallback {
    
    /**
     * Get result.
     * @param getter getter
     * @return result Object
     * @throws SQLException on error
     */
    public Object getResult(ResultGetter getter) throws SQLException {
        Timestamp ts = getter.getTimestamp();
        return new DateTime(ts);
    } // getResult
    
    /**
     * Set parameter.
     * @param setter setter
     * @param parameter Object
     * @throws SQLException on error
     */
    public void setParameter(ParameterSetter setter, Object parameter)
            throws SQLException {
        DateTime dt = (DateTime) parameter;
        setter.setTimestamp(new Timestamp(dt.getMillis()));
    } // setParameter
    
    /**
     * Get value.
     * @param s the object
     * @return value of the object
     */
    public Object valueOf(String s) {
        return s;
    } // valueOf
    
} // DateTimeTypeHandler
