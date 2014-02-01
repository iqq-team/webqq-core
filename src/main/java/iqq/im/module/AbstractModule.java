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
 * Package  : iqq.im.module
 * File     : AbstractModule.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-8-10
 * License  : Apache License 2.0 
 */
package iqq.im.module;

import iqq.im.QQException;
import iqq.im.action.HttpAction;
import iqq.im.actor.HttpActor;
import iqq.im.core.QQContext;
import iqq.im.core.QQModule;
import iqq.im.event.QQActionFuture;
import iqq.im.event.future.HttpActionFuture;

/**
 *
 *
 * @author solosky <solosky772@qq.com>
 *
 */
public class AbstractModule implements QQModule {
	private QQContext context;

	@Override
	public void init(QQContext context) throws QQException{
		this.context = context;
	}

	@Override
	public void destroy() throws QQException{
	}
	
	
	protected QQContext getContext(){
		return this.context;
	}
	
	protected QQActionFuture pushHttpAction(HttpAction action){
		QQActionFuture future = new HttpActionFuture(action);	 	//替换掉原始的QQActionListener
		getContext().pushActor(new HttpActor(HttpActor.Type.BUILD_REQUEST, getContext(), action));
		return future;
	}
	
}
