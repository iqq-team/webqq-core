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
 * Package  : iqq.im.core
 * File     : QQEventDispatcher.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-8-2
 * License  : Apache License 2.0 
 */
package iqq.im.actor;

import iqq.im.QQException;
import iqq.im.core.QQContext;

import javax.swing.SwingUtilities;

/**
 *
 * 使用Swing的线程模型Actor分发器，可以结合SWing来使用，
 * 好处就是所有的回调函数都是在GUI线程里面运行的，可以直接在回调函数里面写GUI的代码
 *
 * @author solosky
 */
public class SwingActorDispatcher implements QQActorDispatcher {
	/* (non-Javadoc)
	 * @see iqq.im.actor.QQActorDispatcher#pushActor(iqq.im.actor.QQActor)
	 */
	/** {@inheritDoc} */
	@Override
	public void pushActor(final QQActor actor){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				actor.execute();
			}
		});
	}

	/** {@inheritDoc} */
	@Override
	public void init(QQContext context) throws QQException {
		//do nothing
	}

	/** {@inheritDoc} */
	@Override
	public void destroy() throws QQException {
		//do nothing
	}
}
