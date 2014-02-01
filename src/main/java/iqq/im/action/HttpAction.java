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
 * Package  : iqq.im.protocol
 * File     : QQHttpAction.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-9-2
 * License  : Apache License 2.0 
 */
package iqq.im.action;

import java.util.concurrent.Future;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.event.QQActionEvent;
import iqq.im.event.QQActionFuture;
import iqq.im.http.QQHttpListener;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;

/**
 *
 *
 * @author solosky <solosky772@qq.com>
 *
 */
public interface HttpAction extends QQHttpListener {
	public QQHttpRequest buildRequest() throws QQException;
	public void cancelRequest() throws QQException;
	public boolean isCancelable();
	public void notifyActionEvent(QQActionEvent.Type type, Object target);
	public QQActionListener getActionListener();
	public void setActionListener(QQActionListener listener);
	public void setActionFuture(QQActionFuture future);
	public void setResponseFuture(Future<QQHttpResponse> future);
}
