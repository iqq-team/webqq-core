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
 * Package  : iqq.im.core
 * File     : QQSession.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-9-5
 * License  : Apache License 2.0 
 */
package iqq.im.core;


/**
 *
 *
 * QQSession保存了每次登陆时候的状态信息
 *
 * @author solosky <solosky772@qq.com>
 *
 */
public class QQSession {
	private long   clientId;
	private String sessionId;
	private String vfwebqq;
	private String ptwebqq;
	private String loginSig;
	private String cfaceKey;	// 上传群图片时需要
	private String cfaceSig;	// 上传群图片时需要
	private String emailAuthKey;// 邮箱登录认证
	private int index;			// 禁用群时需要
	private int port;			// 禁用群时需要
	private int pollErrorCnt;
	private volatile State  state;
	
	public enum State{ 
		OFFLINE,
		ONLINE,
		KICKED,
		LOGINING,
		ERROR
	}

	public long getClientId() {
		return clientId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getVfwebqq() {
		return vfwebqq;
	}

	public void setVfwebqq(String vfwebqq) {
		this.vfwebqq = vfwebqq;
	}

	public int getPollErrorCnt() {
		return pollErrorCnt;
	}

	public void setPollErrorCnt(int pollErrorCnt) {
		this.pollErrorCnt = pollErrorCnt;
	}

	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}

	/**
	 * @return the cfaceKey
	 */
	public String getCfaceKey() {
		return cfaceKey;
	}

	/**
	 * @param cfaceKey the cfaceKey to set
	 */
	public void setCfaceKey(String cfaceKey) {
		this.cfaceKey = cfaceKey;
	}

	/**
	 * @return the cfaceSig
	 */
	public String getCfaceSig() {
		return cfaceSig;
	}

	/**
	 * @param cfaceSig the cfaceSig to set
	 */
	public void setCfaceSig(String cfaceSig) {
		this.cfaceSig = cfaceSig;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPtwebqq() {
		return ptwebqq;
	}

	public void setPtwebqq(String ptwebqq) {
		this.ptwebqq = ptwebqq;
	}

	public String getLoginSig() {
		return loginSig;
	}

	public void setLoginSig(String loginSig) {
		this.loginSig = loginSig;
	}

	public String getEmailAuthKey() {
		return emailAuthKey;
	}

	public void setEmailAuthKey(String emailAuthKey) {
		this.emailAuthKey = emailAuthKey;
	}
}
