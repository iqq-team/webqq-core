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
 * Project  : LiteFetion
 * Package  : net.solosky.litefetion.http
 * File     : Cookie.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2010-10-1
 * License  : Apache License 2.0 
 */
package iqq.im.http;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * Cookie
 *
 * @author solosky <solosky772@qq.com>
 */
public class QQHttpCookie
{
	/**
	 * 名字
	 */
	private String name;
	
	/**
	 * 值
	 */
	private String value;
	
	/**
	 * 所属的域
	 */
	private String domain;
	
	/**
	 * 所属的路径
	 */
	private String path;
	
	/**
	 * 过期时间
	 */
	private Date expired;
	
	

	/**
     * @param name
     * @param value
     * @param domain
     * @param path
     * @param expired
     */
    public QQHttpCookie(String name, String value, String domain, String path,
            Date expired)
    {
	    this.name = name;
	    this.value = value;
	    this.domain = domain;
	    this.path = path;
	    this.expired = expired;
    }
    
    
    /**
     * 通过一个原始的cookie字符串解析cookie
     * @param cookie
     */
    public QQHttpCookie(String cookie)
    {
    	/*
    	这里只解析name, value, domain, path, Max-Age(expired)
    	The syntax for the Set-Cookie response header is

    	   set-cookie      =       "Set-Cookie:" cookies
    	   cookies         =       1#cookie
    	   cookie          =       NAME "=" VALUE *(";" cookie-av)
    	   NAME            =       attr
    	   VALUE           =       value
    	   cookie-av       =       "Comment" "=" value
    	                   |       "Domain" "=" value
    	                   |       "Max-Age" "=" value
    	                   |       "Path" "=" value
    	                   |       "Secure"
    	                   |       "Version" "=" 1*DIGIT
		*/
    	String[] parts = cookie.split(";");
    	if(parts.length<2)	throw new IllegalArgumentException("Invalid cookie string:"+cookie);
    	
    	for(int i=0; i<parts.length; i++) {
    		String[] pairs = parts[i].split("=");
    		String key = pairs[0].trim();
    		String val = pairs.length>1 ? pairs[1].trim() : "";
    		if(i==0) {	//解析name和value
    			this.name = key;
    			this.value = val;
    		}else {
    			key = key.toLowerCase();
    			if(key.equals("domain")) {
    				this.domain = val;
    			}else if(key.equals("max-age")) {
    				try {
	                    this.expired = SimpleDateFormat.getInstance().parse(val);
                    } catch (ParseException e) {
                    	throw new IllegalArgumentException("parse exipred failed.", e);
                    }
    			}else if(key.equals("path")) {
    				this.path = val;
    			}
    		}
    	}

    }

	/**
     * @return the name
     */
    public String getName()
    {
    	return name;
    }

	/**
     * @param name the name to set
     */
    public void setName(String name)
    {
    	this.name = name;
    }

	/**
     * @return the value
     */
    public String getValue()
    {
    	return value;
    }

	/**
     * @param value the value to set
     */
    public void setValue(String value)
    {
    	this.value = value;
    }

	/**
     * @return the domain
     */
    public String getDomain()
    {
    	return domain;
    }

	/**
     * @param domain the domain to set
     */
    public void setDomain(String domain)
    {
    	this.domain = domain;
    }

	/**
     * @return the path
     */
    public String getPath()
    {
    	return path;
    }

	/**
     * @param path the path to set
     */
    public void setPath(String path)
    {
    	this.path = path;
    }

	/**
     * @return the expired
     */
    public Date getExpired()
    {
    	return expired;
    }

	/**
     * @param expired the expired to set
     */
    public void setExpired(Date expired)
    {
    	this.expired = expired;
    }


	/* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
	    return "Cookie [name=" + name + ", value=" + value + ", domain="
	            + domain + ", path=" + path + ", expired=" + expired + "]";
    }
}
