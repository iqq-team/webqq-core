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
 * File     : HttpRequest.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2010-10-1
 * License  : Apache License 2.0 
 */
package iqq.im.http;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * HTTP请求
 *
 * @author solosky
 */
public class QQHttpRequest
{
	/**
	 * URL
	 */
	private String url;
	
	/**
	 * Method
	 */
	private String method;
	
	/**
	 * 超时时间
	 */
	private int timeout;
	
	/**
	 * 请求的头部
	 */
	private Map<String, String> headerMap;
	
	/**
	 * 请求的值集合
	 */
	private Map<String, String> postMap;

	/**
	 * body 请求
	 */
	private String postBody;
	
	/***
	 * Post的文件列表
	 */
	private Map<String, File> fileMap;
	
	/**
	 * Get方式的值集合
	 */
	private Map<String, String> getMap;
	
	/**
	 * 请求的数据流
	 */
	private InputStream inputStream;
	
	/**
	 * 保存的输出流
	 */
	private OutputStream outputStream;
	
	/**
	 * 编码
	 */
	private String charset;
	
	/**
	 * 连接超时
	 */
	private int connectTimeout;
	
	/***
	 * 读取超时
	 */
	private int readTimeout;
    /**
     * 默认的构造函数
     *
     * @param url			地址
     * @param method		方法
     */
    public QQHttpRequest(String url, String method)
    {
	    this.url = url;
	    this.method = method;
	    this.headerMap = new HashMap<String, String>();
	    this.postMap = new HashMap<String, String>();
	    this.getMap = new HashMap<String, String>();
	    this.fileMap = new HashMap<String, File>();
    }

    /**
     * 设置URL
     *
     * @param url the url to set
     */
    public void setUrl(String url)
    {
    	this.url = url;
    }

    /**
     * 设置请求的方法
     *
     * @param method the method to set
     */
    public void setMethod(String method)
    {
    	this.method = method;
    }

    /**
     * 设置超时时间
     *
     * @param timeout the timeout to set
     */
    public void setTimeout(int timeout)
    {
    	this.timeout = timeout;
    }
    
    /**
     * 添加请求头
     *
     * @param key a {@link java.lang.String} object.
     * @param value a {@link java.lang.String} object.
     */
    public void addHeader(String key, String value)
    {
    	this.headerMap.put(key, value);
    }
    
    
    /**
     * 以key=&gt;value的方式设置请求体，仅在方法为POST的方式下有用，默认为utf8编码
     *
     * @param keymap a {@link java.util.Map} object.
     */
    public void setBody(Map<String, String> keymap)
    {
    	this.postMap = keymap;
    }
    
    /**
     * 添加POST的值
     *
     * @param key a {@link java.lang.String} object.
     * @param value a {@link java.lang.String} object.
     */
    public void addPostValue(String key, String value) {
    	this.postMap.put(key, value);
    }
    
    /**
     * 添加POST文件
     *
     * @param key a {@link java.lang.String} object.
     * @param file a {@link java.io.File} object.
     */
    public void addPostFile(String key, File file){
    	this.fileMap.put(key, file);
    }
    
    /**
     * 添加POST的值
     *
     * @param key a {@link java.lang.String} object.
     * @param value a {@link java.lang.String} object.
     */
    public void addGetValue(String key, String value) {
    	this.getMap.put(key, value);
    }
    
    /**
     * 设置请求的数据流
     *
     * @param inputStream a {@link java.io.InputStream} object.
     */
    public void setBody(InputStream inputStream)
    {
    	this.inputStream = inputStream;
    }

    /**
     * <p>Getter for the field <code>headerMap</code>.</p>
     *
     * @return the headerMap
     */
    public Map<String, String> getHeaderMap()
    {
    	return headerMap;
    }

    /**
     * <p>Setter for the field <code>headerMap</code>.</p>
     *
     * @param headerMap the headerMap to set
     */
    public void setHeaderMap(Map<String, String> headerMap)
    {
    	this.headerMap = headerMap;
    }


    /**
     * <p>Getter for the field <code>inputStream</code>.</p>
     *
     * @return the inputStream
     */
    public InputStream getInputStream()
    {
    	if(this.inputStream!=null) {
    		return this.inputStream;
    	}else if(this.postMap.size()>0) {
    		addHeader("Content-Type", "application/x-www-form-urlencoded");
    		StringBuffer buffer = new StringBuffer();
        	Iterator<String> it = this.postMap.keySet().iterator();
        	String charset = "utf8";
        	while(it.hasNext()) {
        		String key = it.next();
        		String value = this.postMap.get(key);
        		try {
    	            key = URLEncoder.encode(key, charset);
    	            value = URLEncoder.encode(value==null?"":value, charset);
    	            buffer.append(key);
    	            buffer.append("=");
    	            buffer.append(value);
    	            buffer.append("&");
                } catch (Exception e) {
                	throw new RuntimeException(e);
                }
        	}
        	try {
	            return new ByteArrayInputStream(buffer.toString().getBytes(charset));
            } catch (UnsupportedEncodingException e) {
            	throw new RuntimeException(e);
            }
    	}else {
    		return null;
    	}
    }

    /**
     * <p>Setter for the field <code>inputStream</code>.</p>
     *
     * @param inputStream the inputStream to set
     */
    public void setInputStream(InputStream inputStream)
    {
    	this.inputStream = inputStream;
    }

    /**
     * <p>Getter for the field <code>url</code>.</p>
     *
     * @return the url
     */
    public String getUrl()
    {
    	if(this.getMap.size()>0){
    		StringBuffer buffer = new StringBuffer(url);
    		buffer.append("?");
    		Iterator<String> it = this.getMap.keySet().iterator();
        	String charset = "utf8";
        	while(it.hasNext()) {
        		String key = it.next();
        		String value = this.getMap.get(key);
        		try {
    	            key = URLEncoder.encode(key, charset);
    	            value = URLEncoder.encode(value==null?"":value, charset);
    	            buffer.append(key);
    	            buffer.append("=");
    	            buffer.append(value);
    	            if(it.hasNext())	  
    	            	buffer.append("&");
                } catch (Exception e) {
                	throw new RuntimeException(e);
                }
        	}
    		return buffer.toString();
    	}else{
    		return url;
    	}
    }

    /**
     * <p>Getter for the field <code>method</code>.</p>
     *
     * @return the method
     */
    public String getMethod()
    {
    	return method;
    }

    /**
     * <p>Getter for the field <code>timeout</code>.</p>
     *
     * @return the timeout
     */
    public int getTimeout()
    {
    	return timeout;
    }

	/**
	 * <p>Getter for the field <code>outputStream</code>.</p>
	 *
	 * @return a {@link java.io.OutputStream} object.
	 */
	public OutputStream getOutputStream() {
		return outputStream;
	}

	/**
	 * <p>Setter for the field <code>outputStream</code>.</p>
	 *
	 * @param outputStream a {@link java.io.OutputStream} object.
	 */
	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	/**
	 * <p>Getter for the field <code>postMap</code>.</p>
	 *
	 * @return a {@link java.util.Map} object.
	 */
	public Map<String, String> getPostMap() {
		return postMap;
	}

	/**
	 * <p>Getter for the field <code>fileMap</code>.</p>
	 *
	 * @return a {@link java.util.Map} object.
	 */
	public Map<String, File> getFileMap() {
		return fileMap;
	}

	/**
	 * <p>Getter for the field <code>charset</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getCharset() {
		return charset==null ? "utf-8" : charset;
	}

	/**
	 * <p>Setter for the field <code>charset</code>.</p>
	 *
	 * @param charset a {@link java.lang.String} object.
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * <p>Getter for the field <code>connectTimeout</code>.</p>
	 *
	 * @return the connectTimeout
	 */
	public int getConnectTimeout() {
		return connectTimeout;
	}

	/**
	 * <p>Setter for the field <code>connectTimeout</code>.</p>
	 *
	 * @param connectTimeout the connectTimeout to set
	 */
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	/**
	 * <p>Getter for the field <code>readTimeout</code>.</p>
	 *
	 * @return the readTimeout
	 */
	public int getReadTimeout() {
		return readTimeout;
	}

	/**
	 * <p>Setter for the field <code>readTimeout</code>.</p>
	 *
	 * @param readTimeout the readTimeout to set
	 */
	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public String getPostBody() {
		return postBody;
	}

	public void setPostBody(String postBody) {
		this.postBody = postBody;
	}
}
