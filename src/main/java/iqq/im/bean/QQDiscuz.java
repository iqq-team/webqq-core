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
 * File     : QQDiscuz.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2013-4-4
 * License  : Apache License 2.0 
 */
package iqq.im.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * QQ讨论组
 *
 * @author solosky <solosky772@qq.com>
 *
 */
public class QQDiscuz implements Serializable {
	private static final long serialVersionUID = -2467563422772879814L;
	private long did;	//讨论组ID，每次登陆都固定，视为没有变换
	private String name;	//讨论组的名字
	private long owner;		//创建者的UIN
	private List<QQDiscuzMember> members = new ArrayList<QQDiscuzMember>();	//讨论组成员
	
	/**
	 * @return the did
	 */
	public long getDid() {
		return did;
	}
	/**
	 * @param did the did to set
	 */
	public void setDid(long did) {
		this.did = did;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the owner
	 */
	public long getOwner() {
		return owner;
	}
	/**
	 * @param owner the owner to set
	 */
	public void setOwner(long owner) {
		this.owner = owner;
	}
	/**
	 * @return the memebers
	 */
	public List<QQDiscuzMember> getMembers() {
		return members;
	}
	/**
	 * @param memebers the memebers to set
	 */
	public void setMembers(List<QQDiscuzMember> members) {
		this.members = members;
	}
	
	public QQDiscuzMember getMemberByUin(long uin){
		for(QQDiscuzMember mem: members){
			if(mem.getUin() == uin){
				return mem;
			}
		}
		return null;
	}
	
	public void clearStatus(){
		for(QQDiscuzMember mem: members){
			mem.setStatus(QQStatus.OFFLINE);
		}
	}
	
	public void addMemeber(QQDiscuzMember user){
		this.members.add(user);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (did ^ (did >>> 32));
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QQDiscuz other = (QQDiscuz) obj;
		if (did != other.did)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "QQDiscuz [did=" + did + ", name=" + name + ", owner=" + owner + "]";
	}
}
