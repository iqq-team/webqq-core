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
 * File     : QQStatus.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2013-3-24
 * License  : Apache License 2.0 
 */
package iqq.im.bean;


/**
 *
 * QQ状态枚举
 *
 * @author solosky
 */
public enum QQStatus {
	
	/**
	 *  10 : "online",
                20 : "offline",
                30 : "away",
                40 : "hidden",
                50 : "busy",
                60 : "callme",
                70 : "silent"
	 */
	ONLINE("online", 10),
	OFFLINE("offline", 20),
	AWAY("away", 30),
	HIDDEN("hidden", 40),
	BUSY("busy", 50),
	CALLME("callme", 60),
	SLIENT("silent", 70);
	
	private String value;
	private int status;
	QQStatus(String value, int status){
		this.value = value; 
		this.status = status;
	}
	/**
	 * <p>Getter for the field <code>value</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getValue(){
		return value;
	}
	/**
	 * <p>Getter for the field <code>status</code>.</p>
	 *
	 * @return a int.
	 */
	public int getStatus(){
		return status;
	}
	
	/**
	 * <p>valueOfRaw.</p>
	 *
	 * @param txt a {@link java.lang.String} object.
	 * @return a {@link iqq.im.bean.QQStatus} object.
	 */
	public static QQStatus valueOfRaw(String txt){
		for(QQStatus s: QQStatus.values()){
			if(s.value.equals(txt)){
				return s;
			}
		}
		throw new IllegalArgumentException("unknown QQStatus enum: " + txt);
	}
	
	
	/**
	 * <p>valueOfRaw.</p>
	 *
	 * @param status a int.
	 * @return a {@link iqq.im.bean.QQStatus} object.
	 */
	public static QQStatus valueOfRaw(int status){
		for(QQStatus s: QQStatus.values()){
			if(s.status == status){
				return s;
			}
		}
		throw new IllegalArgumentException("unknown QQStatus enum: " + status);
	}
	
	/**
	 * <p>isGeneralOnline.</p>
	 *
	 * @param stat a {@link iqq.im.bean.QQStatus} object.
	 * @return a boolean.
	 */
	public static boolean isGeneralOnline(QQStatus stat){
		return (stat == QQStatus.ONLINE ||
			stat == QQStatus.CALLME ||
			stat == QQStatus.AWAY ||
			stat == QQStatus.SLIENT ||
			stat == QQStatus.BUSY ||
			stat == QQStatus.HIDDEN);
	}
}
