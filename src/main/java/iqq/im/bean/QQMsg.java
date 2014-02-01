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
 * Project  : WebQQCoreAsync
 * Package  : iqq.im.bean
 * File     : QQMember.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-2-17
 * License  : Apache License 2.0 
 */
package iqq.im.bean;

import iqq.im.QQException;
import iqq.im.QQException.QQErrorCode;
import iqq.im.bean.content.CFaceItem;
import iqq.im.bean.content.ContentItem;
import iqq.im.bean.content.FaceItem;
import iqq.im.bean.content.FontItem;
import iqq.im.bean.content.OffPicItem;
import iqq.im.bean.content.TextItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * 
 * QQ消息
 * 
 * @author solosky <solosky772@qq.com>
 * 
 */
public class QQMsg implements Serializable{
	private static final long serialVersionUID = 7531037633776708882L;

	public enum Type {
		BUDDY_MSG, 		//好友消息
		GROUP_MSG,		// 群消息
		DISCUZ_MSG,		//讨论组消息
		SESSION_MSG	//临时会话消息
	}

	private long id; // 消息ID
	private long id2;	//暂时不知什么含义
	private Type type; // 消息类型
	private QQUser to;	// 消息发送方
	private QQUser from; // 消息发送方
	private QQGroup group; // 所在群
	private QQDiscuz discuz;	//讨论组
	private Date date; // 发送时间
	private List<ContentItem> contentList; // 消息列表

	public QQMsg() {
		contentList = new ArrayList<ContentItem>();
	}

	public String packContentList() throws QQException {
		// ["font",{"size":10,"color":"808080","style":[0,0,0],"name":"\u65B0\u5B8B\u4F53"}]
		JSONArray json = new JSONArray();
		for (ContentItem contentItem : contentList) {
			json.put(contentItem.toJson());
		}
		return json.toString();
	}

	public void parseContentList(String text) throws QQException {
		try {
			JSONArray json = new JSONArray(text);
			for (int i = 0; i < json.length(); i++) {
				Object value = json.get(i);
				if(value instanceof JSONArray){
					JSONArray items = (JSONArray) value;
					ContentItem.Type type = ContentItem.Type.valueOfRaw(items.getString(0));
					switch (type) {
						case FACE:    addContentItem(new FaceItem(items.toString())); break;
						case FONT:    addContentItem(new FontItem(items.toString())); break;
						case CFACE:   addContentItem(new CFaceItem(items.toString())); break;
						case OFFPIC: addContentItem(new OffPicItem(items.toString())); break;
						default:
					}
				}else if( value instanceof String){
					addContentItem(new TextItem((String) value));
				}else{
					throw new QQException(QQErrorCode.UNKNOWN_ERROR, "unknown msg content type:" + value.toString());
				}
			}
		} catch (JSONException e) {
			throw new QQException(QQErrorCode.JSON_ERROR, e);
		}
	}

	public void addContentItem(ContentItem contentItem) {
		contentList.add(contentItem);
	}

	public void deleteContentItem(ContentItem contentItem) {
		contentList.remove(contentItem);
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public QQUser getFrom() {
		return from;
	}

	public void setFrom(QQUser from) {
		this.from = from;
	}

	public QQGroup getGroup() {
		return group;
	}

	public void setGroup(QQGroup group) {
		this.group = group;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the contentList
	 */
	public List<ContentItem> getContentList() {
		return contentList;
	}

	/**
	 * @param contentList
	 *            the contentList to set
	 */
	public void setContentList(List<ContentItem> contentList) {
		this.contentList = contentList;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the to
	 */
	public QQUser getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(QQUser to) {
		this.to = to;
	}

	/**
	 * @return the discuz
	 */
	public QQDiscuz getDiscuz() {
		return discuz;
	}

	/**
	 * @param discuz the discuz to set
	 */
	public void setDiscuz(QQDiscuz discuz) {
		this.discuz = discuz;
	}

	/**
	 * @return the id2
	 */
	public long getId2() {
		return id2;
	}

	/**
	 * @param id2 the id2 to set
	 */
	public void setId2(long id2) {
		this.id2 = id2;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		try {
			return packContentList();
		} catch (QQException e) {
			Logger.getLogger(QQMsg.class).warn(e.getMessage());
		}
		return null;
	}
	
	public String getText(){
		StringBuffer buffer = new StringBuffer();
		for(ContentItem item: contentList){
			switch(item.getType()){
			case FACE: 
				buffer.append( "[表情 " + ((FaceItem) item).getId() + "]"); break;
			case TEXT: 
				buffer.append( ((TextItem) item).getContent()); break;
			case CFACE: 
				buffer.append("[自定义表情]"); break;
			case OFFPIC: 
				buffer.append("[图片]"); break;
			case FONT:
				buffer.append(""); break;
			default:
				buffer.append(item.toString());
			}
		}
		
		return buffer.toString();
	}
	
}
