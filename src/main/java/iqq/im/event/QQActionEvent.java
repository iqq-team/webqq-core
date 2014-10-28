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
 * <p>QQActionEvent class.</p>
 *
 * @author solosky
 */
public class QQActionEvent extends QQEvent {
	private Type type;
	private Object target;
	private QQActionFuture future;
	
	/**
	 * <p>Constructor for QQActionEvent.</p>
	 *
	 * @param type a {@link iqq.im.event.QQActionEvent.Type} object.
	 * @param target a {@link java.lang.Object} object.
	 * @param future a {@link iqq.im.event.QQActionFuture} object.
	 */
	public QQActionEvent(Type type, Object target, QQActionFuture future) {
		this.type = type;
		this.target = target;
		this.future = future;
	}
	
	
	/**
	 * <p>Getter for the field <code>type</code>.</p>
	 *
	 * @return a {@link iqq.im.event.QQActionEvent.Type} object.
	 */
	public Type getType() {
		return type;
	}
	/**
	 * <p>Getter for the field <code>target</code>.</p>
	 *
	 * @return a {@link java.lang.Object} object.
	 */
	public Object getTarget() {
		return target;
	}
	/**
	 * <p>cancelAction.</p>
	 *
	 * @throws iqq.im.QQException if any.
	 */
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


	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "QQActionEvent [type=" + type + ", target=" + target + "]";
	}
}
