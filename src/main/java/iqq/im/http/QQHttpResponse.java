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
 * File     : HttpResponse.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2010-10-1
 * License  : Apache License 2.0 
 */
package iqq.im.http;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * 
 * HTTP回复
 *
 * @author solosky <solosky772@qq.com>
 */
public class QQHttpResponse
{
	public static final int S_OK = 200;
	public static final int S_NOT_MODIFIED = 304;
	public static final int S_BAD_REQUEST = 400;
	public static final int S_NOT_AUTHORIZED = 401;
	public static final int S_FORBIDDEN = 403;
	public static final int S_NOT_FOUND = 404;
	public static final int S_NOT_ACCEPTABLE = 406;
	public static final int S_INTERNAL_SERVER_ERROR = 500;
	public static final int S_BAD_GATEWAY = 502;
	public static final int S_SERVICE_UNAVAILABLE = 503;
	/**
	 * 状态码
	 */
	private int responseCode;
	
	/**
	 * 状态消息
	 */
	private String responseMessage;
	
	/**
	 * 回复头
	 */
	private Map<String, List<String>> headerFields;
	
	/**
	 * 数据流
	 */
	private byte[] responseData;

	/**
	 * 构造函数
     * @param responseCode
     * @param responseMessage
     * @param headerFields
     * @param responseData
     */
    public QQHttpResponse(int responseCode, String responseMessage,
            Map<String, List<String>> headerFields, byte[] responseData)
    {
	    this.responseCode = responseCode;
	    this.responseMessage = responseMessage;
	    this.headerFields = headerFields;
	    this.responseData = responseData;
    }

    
	public QQHttpResponse() {
	}


	/**
     * @return the responseCode
     */
    public int getResponseCode()
    {
    	return responseCode;
    }

	/**
     * @return the responseMessage
     */
    public String getResponseMessage()
    {
    	return responseMessage;
    }

    /**
     * 返回所有的回复头的值
     * @return the headerFields
     */
    public Map<String, List<String>> getHeaders()
    {
    	return headerFields;
    }
    
    /**
     * 返回指定名字的回复头的值
     * 可能有多个返回值时，默认返回第一个值
     * @param name
     * @return
     */
    public String getHeader(String name)
    {
    	List<String> list = this.headerFields.get(name);
    	if(list!=null && list.size()>0) {
    		return list.get(0);
    	}else {
    		return null;
    	}
    }
    
    /**
     * 返回指定名字的所有的回复头的值的列表
     * @param name
     * @return
     */
    public List<String> getHeaders(String name)
    {
    	return this.headerFields.get(name);
    }
    
	/**
     * @return the inputStream
     */
    public InputStream getInputStream()
    {
    	return new ByteArrayInputStream(this.responseData);
    }
    
    
    /**
     * @return the responseData
     */
    public byte[] getResponseData()
    {
    	return responseData;
    }

	/**
     * 获取回复的字符串
     * @param charset	字符集编码
     * @return
     */
    public String getResponseString(String charset)
    {
    	try {
	        return new String(this.responseData, charset);
        } catch (UnsupportedEncodingException e) {
        	throw new RuntimeException(e);
        }
    }
    
    /**
     * 返回回复内容编码为utf8的字符串
     * @return
     */
    public String getResponseString()
    {
    	return this.getResponseString("utf8");
    }

	/* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
	    return "HttpResponse [responseCode=" + responseCode
	            + ", responseMessage=" + responseMessage
	            + ", getResponseString()=" + getResponseString() + "]";
    }
    
    public long getContentLength(){
    	String length = getHeader("Content-Length");
    	return length != null ? Long.parseLong(length) : 0;
    }
    public String getContentType(){
    	return getHeader("Content-Type");
    }

	public Map<String, List<String>> getHeaderFields() {
		return headerFields;
	}

	public void setHeaderFields(Map<String, List<String>> headerFields) {
		this.headerFields = headerFields;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public void setResponseData(byte[] responseData) {
		this.responseData = responseData;
	}
    
    
}
