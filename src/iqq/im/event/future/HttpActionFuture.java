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
 * Package  : iqq.im.event
 * File     : QQActionFuture.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2013-2-23
 * License  : Apache License 2.0 
 */
package iqq.im.event.future;

import iqq.im.QQException;
import iqq.im.action.HttpAction;

/**
 *
 * 用于异步等待操作完成
 * 
 * 提交任何一个操作都会返回这个对象，可以在这个对象上:
 * 1. 同步等待操作完成
 * 3. 取消请求
 * 
 * 注意: 千万不要在回调函数里面等待操作完成，否则会把整个客户端挂起
 * 
 * @author solosky <solosky772@qq.com>
 *
 */
public class HttpActionFuture extends AbstractActionFuture{
	private HttpAction httpAction;
	public HttpActionFuture(HttpAction action) {
		super(action.getActionListener());
		this.httpAction = action;
		this.httpAction.setActionListener(this);
		this.httpAction.setActionFuture(this);
	}
	
	/* (non-Javadoc)
	 * @see iqq.im.event.QQActionFuture#isCancelable()
	 */
	@Override
	public boolean isCancelable(){
		return httpAction.isCancelable();
	}
	
	/* (non-Javadoc)
	 * @see iqq.im.event.QQActionFuture#cancel()
	 */
	@Override
	public void cancel() throws QQException{
		httpAction.cancelRequest();
	}
}
