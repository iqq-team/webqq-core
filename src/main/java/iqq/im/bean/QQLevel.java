 /*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

 /**
 * Project  : WebQQCore
 * Package  : iqq.im.bean
 * File     : QQLevel.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2013-4-11
 * License  : Apache License 2.0 
 */
package iqq.im.bean;

import java.io.Serializable;

/**
 *
 * QQ等级
 *
 * @author solosky
 */
public class QQLevel implements Serializable{
	private static final long serialVersionUID = 1L;
	private int level;
	private int days;
	private int hours;
	private int remainDays;
	/**
	 * <p>Getter for the field <code>level</code>.</p>
	 *
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}
	/**
	 * <p>Setter for the field <code>level</code>.</p>
	 *
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	/**
	 * <p>Getter for the field <code>days</code>.</p>
	 *
	 * @return the days
	 */
	public int getDays() {
		return days;
	}
	/**
	 * <p>Setter for the field <code>days</code>.</p>
	 *
	 * @param days the days to set
	 */
	public void setDays(int days) {
		this.days = days;
	}
	/**
	 * <p>Getter for the field <code>hours</code>.</p>
	 *
	 * @return the hours
	 */
	public int getHours() {
		return hours;
	}
	/**
	 * <p>Setter for the field <code>hours</code>.</p>
	 *
	 * @param hours the hours to set
	 */
	public void setHours(int hours) {
		this.hours = hours;
	}
	/**
	 * <p>Getter for the field <code>remainDays</code>.</p>
	 *
	 * @return the remainDays
	 */
	public int getRemainDays() {
		return remainDays;
	}
	/**
	 * <p>Setter for the field <code>remainDays</code>.</p>
	 *
	 * @param remainDays the remainDays to set
	 */
	public void setRemainDays(int remainDays) {
		this.remainDays = remainDays;
	}
	
}
