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
 * @author solosky <solosky772@qq.com>
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
     * @param url the url to set
     */
    public void setUrl(String url)
    {
    	this.url = url;
    }

	/**
	 * 设置请求的方法
     * @param method the method to set
     */
    public void setMethod(String method)
    {
    	this.method = method;
    }

	/**
	 * 设置超时时间
     * @param timeout the timeout to set
     */
    public void setTimeout(int timeout)
    {
    	this.timeout = timeout;
    }
    
    /**
     * 添加请求头
     * @param key
     * @param value
     */
    public void addHeader(String key, String value)
    {
    	this.headerMap.put(key, value);
    }
    
    
    /**
     * 以key=>value的方式设置请求体，仅在方法为POST的方式下有用，默认为utf8编码
     * @param keymap
     */
    public void setBody(Map<String, String> keymap)
    {
    	this.postMap = keymap;
    }
    
    /**
     * 添加POST的值
     * @param key
     * @param value
     */
    public void addPostValue(String key, String value) {
    	this.postMap.put(key, value);
    }
    
    /**
     * 添加POST文件
     * @param key
     * @param file
     */
    public void addPostFile(String key, File file){
    	this.fileMap.put(key, file);
    }
    
    /**
     * 添加POST的值
     * @param key
     * @param value
     */
    public void addGetValue(String key, String value) {
    	this.getMap.put(key, value);
    }
    
    /**
     * 设置请求的数据流
     * @param outStream
     */
    public void setBody(InputStream inputStream)
    {
    	this.inputStream = inputStream;
    }

	/**
     * @return the headerMap
     */
    public Map<String, String> getHeaderMap()
    {
    	return headerMap;
    }

	/**
     * @param headerMap the headerMap to set
     */
    public void setHeaderMap(Map<String, String> headerMap)
    {
    	this.headerMap = headerMap;
    }


	/**
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
     * @param inputStream the inputStream to set
     */
    public void setInputStream(InputStream inputStream)
    {
    	this.inputStream = inputStream;
    }

	/**
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
     * @return the method
     */
    public String getMethod()
    {
    	return method;
    }

	/**
     * @return the timeout
     */
    public int getTimeout()
    {
    	return timeout;
    }

	public OutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public Map<String, String> getPostMap() {
		return postMap;
	}

	public Map<String, File> getFileMap() {
		return fileMap;
	}

	public String getCharset() {
		return charset==null ? "utf-8" : charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * @return the connectTimeout
	 */
	public int getConnectTimeout() {
		return connectTimeout;
	}

	/**
	 * @param connectTimeout the connectTimeout to set
	 */
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	/**
	 * @return the readTimeout
	 */
	public int getReadTimeout() {
		return readTimeout;
	}

	/**
	 * @param readTimeout the readTimeout to set
	 */
	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}
	
}
