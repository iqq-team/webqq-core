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
 * Package  : iqq.im.vo
 * File     : QQCategory.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-7-31
 * License  : Apache License 2.0 
 */
package iqq.im.bean;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * QQ分组
 *
 * @author solosky
 */
public class QQCategory implements Serializable {
	private static final long serialVersionUID = 1L;
	private int index;
	private int sort;
	private String name;

	private List<QQBuddy> buddyList = new LinkedList<QQBuddy>();

	/**
	 * <p>getQQBuddyByUin.</p>
	 *
	 * @param uin a int.
	 * @return a {@link iqq.im.bean.QQBuddy} object.
	 */
	public QQBuddy getQQBuddyByUin(int uin) {
		if (!buddyList.isEmpty() && uin != 0) {
			for (QQBuddy b : buddyList) {
				if (b.getUin() == uin) {
					return b;
				}
			}
		}
		return null;
	}

	/**
	 * <p>Getter for the field <code>buddyList</code>.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<QQBuddy> getBuddyList() {
		return buddyList;
	}

	/**
	 * <p>Setter for the field <code>buddyList</code>.</p>
	 *
	 * @param buddyList a {@link java.util.List} object.
	 */
	public void setBuddyList(List<QQBuddy> buddyList) {
		this.buddyList = buddyList;
	}

	/**
	 * <p>getOnlineCount.</p>
	 *
	 * @return a int.
	 */
	public int getOnlineCount() {
		int count = 0;
		for(QQBuddy buddy: buddyList){
			QQStatus stat = buddy.getStatus();
			if(QQStatus.isGeneralOnline(stat)){
				count++;
			}
		}
		return count;
	}
	
	/**
	 * <p>getBuddyCount.</p>
	 *
	 * @return a int.
	 */
	public int getBuddyCount(){
		return buddyList!=null ? buddyList.size() : 0;
	}

	/**
	 * <p>Getter for the field <code>index</code>.</p>
	 *
	 * @return a int.
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * <p>Setter for the field <code>index</code>.</p>
	 *
	 * @param index a int.
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * <p>Getter for the field <code>name</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getName() {
		return name;
	}

	/**
	 * <p>Setter for the field <code>name</code>.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * <p>Getter for the field <code>sort</code>.</p>
	 *
	 * @return a int.
	 */
	public int getSort() {
		return sort;
	}

	/**
	 * <p>Setter for the field <code>sort</code>.</p>
	 *
	 * @param sort a int.
	 */
	public void setSort(int sort) {
		this.sort = sort;
	}
}
