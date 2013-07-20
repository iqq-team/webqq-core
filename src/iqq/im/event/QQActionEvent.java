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
 * Project  : WebQQCoreAsync
 * Package  : iqq.im.event
 * File     : ActionEvent.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-9-5
 * License  : Apache License 2.0 
 */
package iqq.im.event;

import iqq.im.QQException;



/**
 *
 *
 * @author solosky <solosky772@qq.com>
 *
 */
public class QQActionEvent extends QQEvent {
	private Type type;
	private Object target;
	private QQActionFuture future;
	
	public QQActionEvent(Type type, Object target, QQActionFuture future) {
		this.type = type;
		this.target = target;
		this.future = future;
	}
	
	
	public Type getType() {
		return type;
	}
	public Object getTarget() {
		return target;
	}
	public void cancelAction() throws QQException{
		future.cancel();
	}


	public static enum Type{
		EVT_OK,
		EVT_ERROR,
		EVT_WRITE,
		EVT_READ,
		EVT_CANCELED,
		EVT_RETRY,
	}


	@Override
	public String toString() {
		return "QQActionEvent [type=" + type + ", target=" + target + "]";
	}
}
