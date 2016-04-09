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
 * Package  : net.solosky.litefetion.util
 * File     : StringHelper.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2010-10-3
 * License  : Apache License 2.0 
 */
package iqq.im.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * 字符串工具类
 *
 * @author solosky
 */
public class StringHelper {

	/**
	 * 转义HTML中特殊的字符
	 *
	 * @param html HTML 内容
	 * @return 结果字符串
	 */
	public static String qouteHtmlSpecialChars(String html)
	{
		if(html==null)	return null;
		String[] specialChars = { "&", "\"", "'", "<", ">"};
		String[] qouteChars = {"&amp;", "&quot;", "&apos;", "&lt;", "&gt;"};
		for(int i=0; i<specialChars.length; i++){
			html = html.replace(specialChars[i], qouteChars[i]);
		}
		return html;
	}
	
	/**
	 * 反转义HTML中特殊的字符
	 *
	 * @param html HTML 内容
	 * @return 结果字符串
	 */
	public static String unqouteHtmlSpecialChars(String html)
	{
		if(html==null)	return null;
		String[] specialChars = { "&", "\"", "'", "<", ">", " "};
		String[] qouteChars = {"&amp;", "&quot;", "&apos;", "&lt;", "&gt;", "&nbsp;"};
		for(int i=0; i<specialChars.length; i++){
			html = html.replace(qouteChars[i], specialChars[i]);
		}
		return html;
	}
	
	
	/**
	 * 去掉HTML标签
	 *
	 * @param html HTML 内容
	 * @return 去除HTML标签后的结果
	 */
	public static String stripHtmlSpecialChars(String html)
	{
		if(html==null)	return null;
		 html=html.replaceAll("</?[^>]+>",""); 
		 html=html.replace("&nbsp;"," "); 
		 
		 return html;
	}
	
	
	/**
	 * 以一种简单的方式格式化字符串
	 * 如
	 * <pre>
	 * String s = StringHelper.format("{0} is {1}", "apple", "fruit");
	 * LOG.info(s);
	 * //输出  apple is fruit.
	 * </pre>
	 *
	 * @param pattern 需要进行格式化的字符串
	 * @param args 用于格式化的参数
	 * @return 结果字符串
	 */
	public static String format(String pattern, Object ...args)
	{
		for(int i=0; i<args.length; i++) {
			pattern = pattern.replace("{"+i+"}", args[i].toString());
		}
		return pattern;
	}
	
	/**
	 * 编码URL
	 *
	 * @param url 需要进行编码的URL
	 * @return 编码后的URL
	 */
	public static String urlEncode(String url)
	{
		try {
	        return URLEncoder.encode(url, "utf8");
        } catch (UnsupportedEncodingException e) {
        	return url;
        }
	}
}
