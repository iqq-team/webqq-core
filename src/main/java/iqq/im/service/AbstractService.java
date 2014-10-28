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
 * Package  : iqq.im.service
 * File     : AbstractService.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-8-10
 * License  : Apache License 2.0 
 */
package iqq.im.service;

import iqq.im.QQException;
import iqq.im.core.QQContext;
import iqq.im.core.QQService;

/**
 *
 * 抽象的服务类，实现了部分接口，方便子类实现
 *
 * @author solosky
 */
public abstract class AbstractService implements QQService{
	private QQContext context;

	/** {@inheritDoc} */
	@Override
	public void init(QQContext context) throws QQException {
		this.context = context;
	}

	/** {@inheritDoc} */
	@Override
	public void destroy() throws QQException{
	}
	
	
	/**
	 * <p>Getter for the field <code>context</code>.</p>
	 *
	 * @return a {@link iqq.im.core.QQContext} object.
	 */
	protected QQContext getContext(){
		return this.context;
	}
	
	

}
