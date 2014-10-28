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
 * Package  : iqq.im.event.future
 * File     : ProcActionFuture.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2013-2-24
 * License  : Apache License 2.0 
 */
package iqq.im.event.future;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.event.QQActionEvent;

/**
 *
 * 多个过程操作的异步等待对象
 * 需手动处理
 *
 * @author solosky
 */
public class ProcActionFuture extends AbstractActionFuture {

	/**
	 * <p>Constructor for ProcActionFuture.</p>
	 *
	 * @param proxyListener a {@link iqq.im.QQActionListener} object.
	 * @param Cancelable a boolean.
	 */
	public ProcActionFuture(QQActionListener proxyListener, boolean Cancelable) {
		super(proxyListener);
		setCanceled(false);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isCancelable() {
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public void cancel() throws QQException {
		setCanceled(true);
		notifyActionEvent(QQActionEvent.Type.EVT_CANCELED, null);
	}

}
