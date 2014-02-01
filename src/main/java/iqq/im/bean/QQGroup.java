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
 * File     : QQGroup.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-7-31
 * License  : Apache License 2.0 
 */
package iqq.im.bean;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * QQ群
 * 
 * @author solosky <solosky772@qq.com>
 * 
 */
public class QQGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	private long gid;	//真实的群号
	private long gin;	//变换后的群号
	private long code;
	private int clazz;
	private int flag;
	private int level;
	private int mask;
	private String name;
	private String memo;
	private String fingermemo;
	private Date createTime;
	private transient BufferedImage face; // 头像

	private List<QQGroupMember> members = new ArrayList<QQGroupMember>();


	/**
	 * @return the code
	 */
	public long getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(long code) {
		this.code = code;
	}

	/**
	 * @return the clazz
	 */
	public int getClazz() {
		return clazz;
	}

	/**
	 * @param clazz
	 *            the clazz to set
	 */
	public void setClazz(int clazz) {
		this.clazz = clazz;
	}

	/**
	 * @return the flag
	 */
	public int getFlag() {
		return flag;
	}

	/**
	 * @param flag
	 *            the flag to set
	 */
	public void setFlag(int flag) {
		this.flag = flag;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the fingermemo
	 */
	public String getFingermemo() {
		return fingermemo;
	}

	/**
	 * @param fingermemo
	 *            the fingermemo to set
	 */
	public void setFingermemo(String fingermemo) {
		this.fingermemo = fingermemo;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the face
	 */
	public BufferedImage getFace() {
		return face;
	}

	/**
	 * @param face
	 *            the face to set
	 */
	public void setFace(BufferedImage face) {
		this.face = face;
	}

	/**
	 * @return the members
	 */
	public List<QQGroupMember> getMembers() {
		return members;
	}

	/**
	 * @param members
	 *            the members to set
	 */
	public void setMembers(List<QQGroupMember> members) {
		this.members = members;
	}
	
	/**
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * @param memo the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public QQGroupMember getMemberByUin(long uin){
		for(QQGroupMember mem: members){
			if(mem.getUin() == uin){
				return mem;
			}
		}
		return null;
	}

	public int getMask() {
		return mask;
	}

	public void setMask(int mask) {
		this.mask = mask;
	}

	@Override
	public int hashCode() {
		return (int) this.getCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}

		if (obj instanceof QQGroup) {
			QQGroup g = (QQGroup) obj;
			if (g.getCode() == this.getCode()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return the gid
	 */
	public long getGid() {
		return gid;
	}

	/**
	 * @param gid the gid to set
	 */
	public void setGid(long gid) {
		this.gid = gid;
	}
	

	/**
	 * @return the gin
	 */
	public long getGin() {
		return gin;
	}

	/**
	 * @param gin the gin to set
	 */
	public void setGin(long gin) {
		this.gin = gin;
	}
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "QQGroup [gid=" + gid + ", code=" + code + ", name=" + name + "]";
	}


}
