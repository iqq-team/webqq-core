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
 * File     : NotifyEvent.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-9-5
 * License  : Apache License 2.0 
 */
package iqq.im.event;


/**
 *
 *
 * @author solosky <solosky772@qq.com>
 *
 */
public class QQNotifyEvent extends QQEvent{
	private Type type;
	private Object target;
	
	public QQNotifyEvent(Type type, Object target) {
		this.type = type;
		this.target = target;
	}
	
	public Type getType() {
		return type;
	}
	public Object getTarget() {
		return target;
	}

	public static enum Type{
		/**网络连接出错，客户端已经掉线*/
		NET_ERROR,
		/**未知错误，如retcode多次出现未知值*/
		UNKNOWN_ERROR,
		/**服务器需要再次认证，否则就离线掉线*/
		NEED_REAUTH,
		/**客户端被踢下线，可能是其他地方登陆*/
		KICK_OFFLINE,
		/**对方正在输入*/
		BUDDY_INPUT,
		/**窗口震动*/
		SHAKE_WINDOW,
		/**聊天消息，包括好友，群，临时会话，讨论组消息*/
		CHAT_MSG,
		/**好友通知，如其他人请求添加好友，添加其他用户请求通过或者拒绝*/
		BUDDY_NOTIFY,
		/**群通知，如管理员通过或拒绝了添加群请求，群成员退出等*/
		GROUP_NOTIFY,
		/**文件传输通知，如对方请求发送文件，对方已同意接受文件等*/
		FILE_NOTIFY,
		/**视频通知，如对方请求和你视频，对方同意视频等。。*/
		AV_NOTIFY,
		/**系统广播*/
		SYSTEM_NOTIFY,
		/**好友状态改变*/
		BUDDY_STATUS_CHANGE,
		/**验证请求，需要用户输入验证码以继续*/
		CAPACHA_VERIFY,
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "QQNotifyEvent [type=" + type + ", target=" + target + "]";
	}
}
