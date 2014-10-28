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
 * File     : QQNotifyHandlerProxy.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2013-3-16
 * License  : Apache License 2.0 
 */
package iqq.im.event;

import iqq.im.QQNotifyListener;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * 使用这个类简化事件的注册，分发
 * 只需在被代理的类使用@IMEventHandler注解需要处理的事件类型即可
 *
 * @author solosky
 */
public class QQNotifyHandlerProxy implements QQNotifyListener{
	private static final Logger LOG = LoggerFactory.getLogger(QQNotifyHandlerProxy.class);
	private Object proxyObject;
	private Map<QQNotifyEvent.Type, Method> methodMap;
	/**
	 * <p>Constructor for QQNotifyHandlerProxy.</p>
	 *
	 * @param proxyObject a {@link java.lang.Object} object.
	 */
	public QQNotifyHandlerProxy(Object proxyObject){
		this.proxyObject = proxyObject;
		this.methodMap = new HashMap<QQNotifyEvent.Type, Method>();
		 for (Method m : proxyObject.getClass().getDeclaredMethods()) {
			 if(m.isAnnotationPresent(QQNotifyHandler.class)){
				 QQNotifyHandler handler = m.getAnnotation(QQNotifyHandler.class);
				 this.methodMap.put(handler.value(), m);
				 if(!m.isAccessible()){
					 m.setAccessible(true);
				 }
			 }
		 }
	}
	
	/** {@inheritDoc} */
	@Override
	public void onNotifyEvent(QQNotifyEvent event) {
		Method m =  methodMap.get(event.getType());
		if(m != null){
			try {
				m.invoke(proxyObject, event);
			} catch (Throwable e) {
				LOG.warn("invoke QQNotifyHandler Error!!", e);
			}
		}else{
			LOG.warn("Not found QQNotifyHandler for QQNotifyEvent = " + event);
		}
	}

}
