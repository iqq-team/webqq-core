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
 * File     : HttpListener.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-9-1
 * License  : Apache License 2.0 
 */
package iqq.im.http;


/**
 * <p>QQHttpListener interface.</p>
 *
 * @author solosky
 */
public interface QQHttpListener {
	/**
	 * <p>onHttpFinish.</p>
	 *
	 * @param response a {@link iqq.im.http.QQHttpResponse} object.
	 */
	public void onHttpFinish(QQHttpResponse response);
	/**
	 * <p>onHttpError.</p>
	 *
	 * @param t a {@link java.lang.Throwable} object.
	 */
	public void onHttpError(Throwable t);
	/**
	 * <p>onHttpHeader.</p>
	 *
	 * @param response a {@link iqq.im.http.QQHttpResponse} object.
	 */
	public void onHttpHeader(QQHttpResponse response);
	/**
	 * <p>onHttpWrite.</p>
	 *
	 * @param current a long.
	 * @param total a long.
	 */
	public void onHttpWrite(long current, long total);
	/**
	 * <p>onHttpRead.</p>
	 *
	 * @param current a long.
	 * @param total a long.
	 */
	public void onHttpRead(long current, long total);
}
