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
import iqq.im.bean.QQBuddy;
import iqq.im.bean.QQCategory;
import iqq.im.bean.QQDiscuz;
import iqq.im.bean.QQGroup;
import iqq.im.bean.QQStatus;
import iqq.im.bean.QQStranger;
import iqq.im.bean.QQUser;
import iqq.im.bean.content.ContentItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 存储QQ相关的数据 如好友列表，分组列表，群列表，在线好友等
 * 
 * @author solosky <solosky772@qq.com>
 * 
 */
public class QQStore implements QQLifeCycle {
	private Map<Long, QQBuddy> buddyMap; // uin => QQBudy, 快速通过uin查找QQ好友
	private Map<Long, QQStranger> strangerMap; // uin => QQStranger, 快速通过uin查找临时会话用户
	private Map<Long, QQCategory> categoryMap; // index => QQCategory
	private Map<Long, QQDiscuz> discuzMap;		// did = > QQDiscuz
	private Map<Long, QQGroup> groupMap; // code => QQGroup, 快速通过群ID查找群
	private List<ContentItem> pictureItemList; // filename -> PicItem 上传图片列表

	public QQStore() {
		this.buddyMap = new HashMap<Long, QQBuddy>();
		this.strangerMap = new HashMap<Long, QQStranger>();
		this.categoryMap = new HashMap<Long, QQCategory>();
		this.groupMap = new HashMap<Long, QQGroup>();
		this.discuzMap = new HashMap<Long, QQDiscuz>();
		this.pictureItemList = new ArrayList<ContentItem>();
	}

	@Override
	public void init(QQContext context) throws QQException {
	}

	@Override
	public void destroy() throws QQException {
	}

	// add
	public void addBuddy(QQBuddy buddy) {
		buddyMap.put(buddy.getUin(), buddy);
	}
	
	public void addStranger(QQStranger stranger) {
		strangerMap.put(stranger.getUin(), stranger);
	}

	public void addCategory(QQCategory category) {
		categoryMap.put(new Long(category.getIndex()), category);
	}

	public void addGroup(QQGroup group) {
		groupMap.put(group.getCode(), group);
	}

	public void addPicItem(ContentItem pictureItem) {
		pictureItemList.add(pictureItem);
	}
	
	public void addDiscuz(QQDiscuz discuz){
		discuzMap.put(discuz.getDid(), discuz);
	}

	// delete
	public void deleteBuddy(QQBuddy buddy) {
		buddyMap.remove(buddy);
	}
	
	public void deleteStranger(QQStranger stranger) {
		strangerMap.remove(stranger);
	}

	public void deleteCategory(QQCategory category) {
		categoryMap.remove(category);
	}

	public void deleteGroup(QQGroup group) {
		groupMap.remove(group.getGin());
	}

	public void deletePicItem(ContentItem picItem) {
		pictureItemList.remove(picItem);
	}
	
	public void deleteDiscuz(QQDiscuz discuz){
		discuzMap.remove(discuz.getDid());
	}

	// get
	public QQBuddy getBuddyByUin(long uin) {
		return buddyMap.get(uin);
	}
	
	public QQStranger getStrangerByUin(long uin) {
		return strangerMap.get(uin);
	}

	public QQCategory getCategoryByIndex(long index) {
		return categoryMap.get(index);
	}

	public QQGroup getGroupByCode(long code) {
		return groupMap.get(code);
	}
	
	public QQGroup getGroupById(long id) {
		for(QQGroup g : getGroupList()) {
			if(g.getGid() == id) {
				return g;
			}
		}
		return null;
	}
	
	public QQGroup getGroupByGin(long gin) {
		for(QQGroup g : getGroupList()) {
			if(g.getGin() == gin) {
				return g;
			}
		}
		return null;
	}
	
	public QQDiscuz getDiscuzByDid(long did){
		return discuzMap.get(did);
	}

	// get list
	public List<QQBuddy> getBuddyList() {
		return new ArrayList<QQBuddy>(buddyMap.values());
	}
	
	public List<QQStranger> getStrangerList() {
		return new ArrayList<QQStranger>(strangerMap.values());
	}

	public List<QQCategory> getCategoryList() {
		return new ArrayList<QQCategory>(categoryMap.values());
	}

	public List<QQGroup> getGroupList() {
		return new ArrayList<QQGroup>(groupMap.values());
	}
	
	public List<QQDiscuz> getDiscuzList() {
		return new ArrayList<QQDiscuz>(discuzMap.values());
	}

	public List<QQBuddy> getOnlineBuddyList() {
		List<QQBuddy> onlines = new ArrayList<QQBuddy>();
		for(QQBuddy buddy: buddyMap.values()){
			if(QQStatus.isGeneralOnline(buddy.getStatus())){
				onlines.add(buddy);
			}
		}
		return onlines;
	}

	public List<ContentItem> getPicItemList() {
		return pictureItemList;
	}

	// get size
	public int getPicItemListSize() {
		return pictureItemList.isEmpty() ? 0 : pictureItemList.size();
	}
	
	// 查找临时会话用户 QQGroup/QQDiscuz/QQStranger
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
