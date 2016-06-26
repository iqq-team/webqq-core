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
 * Package  : iqq.im.core
 * File     : QQStore.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2013-2-24
 * License  : Apache License 2.0 
 */
package iqq.im.core;

import iqq.im.QQException;
import iqq.im.bean.*;
import iqq.im.bean.content.ContentItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 存储QQ相关的数据 如好友列表，分组列表，群列表，在线好友等
 *
 * @author solosky
 */
public class QQStore implements QQLifeCycle {
	private Map<Long, QQBuddy> buddyMap; // uin => QQBudy, 快速通过uin查找QQ好友
	private Map<Long, QQStranger> strangerMap; // uin => QQStranger, 快速通过uin查找临时会话用户
	private Map<Long, QQCategory> categoryMap; // index => QQCategory
	private Map<Long, QQDiscuz> discuzMap;		// did = > QQDiscuz
	private Map<Long, QQGroup> groupMap; // code => QQGroup, 快速通过群ID查找群
	private List<ContentItem> pictureItemList; // filename -> PicItem 上传图片列表

	/**
	 * <p>Constructor for QQStore.</p>
	 */
	public QQStore() {
		this.buddyMap = new HashMap<Long, QQBuddy>();
		this.strangerMap = new HashMap<Long, QQStranger>();
		this.categoryMap = new HashMap<Long, QQCategory>();
		this.groupMap = new HashMap<Long, QQGroup>();
		this.discuzMap = new HashMap<Long, QQDiscuz>();
		this.pictureItemList = new ArrayList<ContentItem>();
	}

	/** {@inheritDoc} */
	@Override
	public void init(QQContext context) throws QQException {
	}

	/** {@inheritDoc} */
	@Override
	public void destroy() throws QQException {
	}

	// add
	/**
	 * <p>addBuddy.</p>
	 *
	 * @param buddy a {@link iqq.im.bean.QQBuddy} object.
	 */
	public void addBuddy(QQBuddy buddy) {
		buddyMap.put(buddy.getUin(), buddy);
	}
	
	/**
	 * <p>addStranger.</p>
	 *
	 * @param stranger a {@link iqq.im.bean.QQStranger} object.
	 */
	public void addStranger(QQStranger stranger) {
		strangerMap.put(stranger.getUin(), stranger);
	}

	/**
	 * <p>addCategory.</p>
	 *
	 * @param category a {@link iqq.im.bean.QQCategory} object.
	 */
	public void addCategory(QQCategory category) {
		categoryMap.put(new Long(category.getIndex()), category);
	}

	/**
	 * <p>addGroup.</p>
	 *
	 * @param group a {@link iqq.im.bean.QQGroup} object.
	 */
	public void addGroup(QQGroup group) {
		groupMap.put(group.getCode(), group);
	}

	/**
	 * <p>addPicItem.</p>
	 *
	 * @param pictureItem a {@link iqq.im.bean.content.ContentItem} object.
	 */
	public void addPicItem(ContentItem pictureItem) {
		pictureItemList.add(pictureItem);
	}
	
	/**
	 * <p>addDiscuz.</p>
	 *
	 * @param discuz a {@link iqq.im.bean.QQDiscuz} object.
	 */
	public void addDiscuz(QQDiscuz discuz){
		discuzMap.put(discuz.getDid(), discuz);
	}

	// delete
	/**
	 * <p>deleteBuddy.</p>
	 *
	 * @param buddy a {@link iqq.im.bean.QQBuddy} object.
	 */
	public void deleteBuddy(QQBuddy buddy) {
		buddyMap.remove(buddy);
	}
	
	/**
	 * <p>deleteStranger.</p>
	 *
	 * @param stranger a {@link iqq.im.bean.QQStranger} object.
	 */
	public void deleteStranger(QQStranger stranger) {
		strangerMap.remove(stranger);
	}

	/**
	 * <p>deleteCategory.</p>
	 *
	 * @param category a {@link iqq.im.bean.QQCategory} object.
	 */
	public void deleteCategory(QQCategory category) {
		categoryMap.remove(category);
	}

	/**
	 * <p>deleteGroup.</p>
	 *
	 * @param group a {@link iqq.im.bean.QQGroup} object.
	 */
	public void deleteGroup(QQGroup group) {
		groupMap.remove(group.getGin());
	}

	/**
	 * <p>deletePicItem.</p>
	 *
	 * @param picItem a {@link iqq.im.bean.content.ContentItem} object.
	 */
	public void deletePicItem(ContentItem picItem) {
		pictureItemList.remove(picItem);
	}
	
	/**
	 * <p>deleteDiscuz.</p>
	 *
	 * @param discuz a {@link iqq.im.bean.QQDiscuz} object.
	 */
	public void deleteDiscuz(QQDiscuz discuz){
		discuzMap.remove(discuz.getDid());
	}

	// get
	/**
	 * <p>getBuddyByUin.</p>
	 *
	 * @param uin a long.
	 * @return a {@link iqq.im.bean.QQBuddy} object.
	 */
	public QQBuddy getBuddyByUin(long uin) {
		return buddyMap.get(uin);
	}
	
	/**
	 * <p>getStrangerByUin.</p>
	 *
	 * @param uin a long.
	 * @return a {@link iqq.im.bean.QQStranger} object.
	 */
	public QQStranger getStrangerByUin(long uin) {
		return strangerMap.get(uin);
	}

	/**
	 * <p>getCategoryByIndex.</p>
	 *
	 * @param index a long.
	 * @return a {@link iqq.im.bean.QQCategory} object.
	 */
	public QQCategory getCategoryByIndex(long index) {
		return categoryMap.get(index);
	}

	/**
	 * <p>getGroupByCode.</p>
	 *
	 * @param code a long.
	 * @return a {@link iqq.im.bean.QQGroup} object.
	 */
	public QQGroup getGroupByCode(long code) {
		return groupMap.get(code);
	}
	
	/**
	 * <p>getGroupById.</p>
	 *
	 * @param id a long.
	 * @return a {@link iqq.im.bean.QQGroup} object.
	 */
	public QQGroup getGroupById(long id) {
		for(QQGroup g : getGroupList()) {
			if(g.getGid() == id) {
				return g;
			}
		}
		return null;
	}
	
	/**
	 * <p>getGroupByGin.</p>
	 *
	 * @param gin a long.
	 * @return a {@link iqq.im.bean.QQGroup} object.
	 */
	public QQGroup getGroupByGin(long gin) {
		for(QQGroup g : getGroupList()) {
			if(g.getGin() == gin) {
				return g;
			}
		}
		return null;
	}
	
	/**
	 * <p>getDiscuzByDid.</p>
	 *
	 * @param did a long.
	 * @return a {@link iqq.im.bean.QQDiscuz} object.
	 */
	public QQDiscuz getDiscuzByDid(long did){
		return discuzMap.get(did);
	}

	// get list
	/**
	 * <p>getBuddyList.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<QQBuddy> getBuddyList() {
		return new ArrayList<QQBuddy>(buddyMap.values());
	}
	
	/**
	 * <p>getStrangerList.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<QQStranger> getStrangerList() {
		return new ArrayList<QQStranger>(strangerMap.values());
	}

	/**
	 * <p>getCategoryList.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<QQCategory> getCategoryList() {
		return new ArrayList<QQCategory>(categoryMap.values());
	}

	/**
	 * <p>getGroupList.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<QQGroup> getGroupList() {
		return new ArrayList<QQGroup>(groupMap.values());
	}
	
	/**
	 * <p>getDiscuzList.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<QQDiscuz> getDiscuzList() {
		return new ArrayList<QQDiscuz>(discuzMap.values());
	}

	/**
	 * <p>getOnlineBuddyList.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<QQBuddy> getOnlineBuddyList() {
		List<QQBuddy> onlines = new ArrayList<QQBuddy>();
		for(QQBuddy buddy: buddyMap.values()){
			if(QQStatus.isGeneralOnline(buddy.getStatus())){
				onlines.add(buddy);
			}
		}
		return onlines;
	}

	/**
	 * <p>getPicItemList.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<ContentItem> getPicItemList() {
		return pictureItemList;
	}

	// get size
	/**
	 * <p>getPicItemListSize.</p>
	 *
	 * @return a int.
	 */
	public int getPicItemListSize() {
		return pictureItemList.isEmpty() ? 0 : pictureItemList.size();
	}
	
	// 查找临时会话用户 QQGroup/QQDiscuz/QQStranger
	/**
	 * <p>searchUserByUin.</p>
	 *
	 * @param uin a long.
	 * @return a {@link iqq.im.bean.QQUser} object.
	 */
	public QQUser searchUserByUin(long uin) {
		QQUser user = getBuddyByUin(uin);
		if(user == null) {
			user = getStrangerByUin(uin);
		}
		if(user == null) {
			for(QQGroup group : getGroupList()) {
				for(QQUser u : group.getMembers()) {
					if(u .getUin() == uin) {
						return u;
					}
				}
			}
			for(QQDiscuz discuz : getDiscuzList()) {
				for(QQUser u : discuz.getMembers()) {
					if(u .getUin() == uin) {
						return u;
					}
				}
			}
		}
		return user;
	}

}
