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
 * File     : CoreModule.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-7-31
 * License  : Apache License 2.0 
 */
package iqq.im.module;

import iqq.im.QQActionListener;
import iqq.im.action.ChannelLoginAction;
import iqq.im.action.CheckVerifyAction;
import iqq.im.action.GetCaptchaImageAction;
import iqq.im.action.PollMsgAction;
import iqq.im.action.WebLoginAction;
import iqq.im.action.WebLogoutAction;
import iqq.im.bean.QQStatus;
import iqq.im.event.QQActionFuture;

/**
 *
 * 登录模块，处理登录和退出
 *
 * @author solosky <solosky772@qq.com>
 *
 */
public class LoginModule extends AbstractModule {
	
	public QQActionFuture checkVerify(String qqAccount, QQActionListener listener){
		return pushHttpAction(new CheckVerifyAction(getContext(), listener, qqAccount));
	}
	
	public QQActionFuture webLogin(String username, String password, long uin, String verifyCode, QQActionListener listener){
		return pushHttpAction(new WebLoginAction(getContext(), listener, username, password, uin, verifyCode));
	}
	
	public QQActionFuture channelLogin(QQStatus status, QQActionListener listener){
		return pushHttpAction(new ChannelLoginAction(getContext(), listener, status));
	}
	
	public QQActionFuture getCaptcha(long uin, QQActionListener listener){
		return pushHttpAction(new GetCaptchaImageAction(getContext(), listener, uin));
	}
	
	public QQActionFuture pollMsg(QQActionListener listener){
		return pushHttpAction(new PollMsgAction(getContext(), listener));
	}
	
	public QQActionFuture logout(QQActionListener listener){
		return pushHttpAction(new WebLogoutAction(getContext(), listener));
	}
}
