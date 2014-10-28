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
 * Package  : iqq.im.http
 * File     : QQCookieJar.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2013-2-27
 * License  : Apache License 2.0 
 */
package iqq.im.http;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 保存和读取cookie
 *
 * @author solosky
 */
public class QQHttpCookieJar {
	private static final Logger LOG = LoggerFactory.getLogger(QQHttpCookieJar.class);
	private List<QQHttpCookie> cookieContainer;
	
	/**
	 * <p>Constructor for QQHttpCookieJar.</p>
	 */
	public QQHttpCookieJar(){
		this.cookieContainer = new ArrayList<QQHttpCookie>();
	}
	
	/**
	 * <p>getCookie.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param url a {@link java.lang.String} object.
	 * @return a {@link iqq.im.http.QQHttpCookie} object.
	 */
	public QQHttpCookie getCookie(String name, String url){
		Iterator<QQHttpCookie> it = cookieContainer.iterator();
		while(it.hasNext()) {
			QQHttpCookie cookie = it.next();
			if(cookie.getName().equals(name)) {
				return cookie;
			}
		}
		return null;
	}
	
	/**
	 * <p>updateCookies.</p>
	 *
	 * @param tmpCookies a {@link java.util.List} object.
	 */
	public void updateCookies(List<String> tmpCookies){
		List<String> newCookies = new ArrayList<String>();
		if(tmpCookies!=null){
			newCookies.addAll(tmpCookies);
		}
		
		if(newCookies.size()>0) {
    		Iterator<String> nit = newCookies.iterator();
    		while(nit.hasNext()) {
    			QQHttpCookie cookie = new QQHttpCookie(nit.next());
    			QQHttpCookie oldCookie = this.getCookie(cookie.getName(), null);
    			//如果有之前相同名字的Cookie,删除之前的cookie
    			if(oldCookie!=null) {
    					cookieContainer.remove(oldCookie);
            			//如果新cookie的值不为空，就添加到新的cookie到列表中
            			if(cookie.getValue()!=null && cookie.getValue().length()>0) {
            				cookieContainer.add(cookie);
            				LOG.debug("[Update Cookie] "+cookie);
            			}
    			}else {
    				cookieContainer.add(cookie);
    				LOG.debug("[New Cookie] "+cookie);
    			}
    		}
		}
	}
	
	/**
	 * <p>getCookieHeader.</p>
	 *
	 * @param url a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public String getCookieHeader(String url){
		URL u = null;
		try {
			u = new URL(url);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
		
		Iterator<QQHttpCookie> cit = cookieContainer.iterator();
		StringBuffer buffer = new StringBuffer();
		while(cit.hasNext()) {
			QQHttpCookie cookie = cit.next();
			if(cookie.getExpired()!=null && cookie.getExpired().before(new Date())) {
				cit.remove();	//已经过期，删除
				LOG.debug("[Removed Cookie] "+cookie);
			}else if( /*url.getHost().endsWith(cookie.getDomain()) && */
					u.getPath().startsWith(cookie.getPath())) {
				buffer.append(cookie.getName());
				buffer.append("=");
				buffer.append(cookie.getValue());
				buffer.append("; ");
			}else {}
		}
		return buffer.toString();
	}
}
