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
 * File     : NotifyEventArgs.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-9-5
 * License  : Apache License 2.0 
 */
package iqq.im.event;

import java.awt.image.BufferedImage;

/**
 *
 * 这里定义了一些事件Target对象
 * 有些事件可能需要附带多个数据，如果没有合适的对象表示Target属性，可以在此定义
 * 所有的成员建议都公开，减少无用的getter和setter
 *
 * @author solosky <solosky772@qq.com>
 *
 */
public class QQNotifyEventArgs {
	
	/**
	 * 需要用户识别验证码通知
	 * 登录，加好友，获取QQ号可能都需要验证码
	 * @author solosky <solosky772@qq.com>
	 */
	public static class ImageVerify{
		public  enum VerifyType {LOGIN, ADD_FRIEND, GET_UIN};
		/**验证的类型，登陆，添加好友，获取qq号可能会出现验证码*/
		public VerifyType type;			
		/**验证码图片对象**/
		public BufferedImage image;
		/**需要验证的原因*/
		public String reason;	
		/**future对象，在验证流程内部使用*/
		public QQActionFuture future;
		/**每一个验证码对应HTTP中cookie中名字为verifysession的值*/
		public String vsession;
		/**验证码字符*/
		public String vcode;
	}
	
	/**
	 * 登录进度通知
	 * @author solosky <solosky772@qq.com>
	 */
	public enum LoginProgress{
		CHECK_VERIFY,
		UI_LOGIN,
		UI_LOGIN_VERIFY,
		CHANNEL_LOGIN,
	}
}
