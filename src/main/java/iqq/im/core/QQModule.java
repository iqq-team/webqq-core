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
 * File     : QQModule.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-7-31
 * License  : Apache License 2.0 
 */
package iqq.im.core;


/**
 *
 * QQ模块
 * 
 * 模块是QQ的功能单元水平的划分，一个模块负责某一个单独的相对独立的逻辑，如好友管理，分组管理，消息管理等
 *
 * @author solosky <solosky772@qq.com>
 *
 */
public interface QQModule extends QQLifeCycle{
	public enum Type{
		PROC,			//登陆和退出流程执行
		LOGIN,			//核心模块，处理登录和退出的逻辑
		USER,			//个人信息管理模块
		BUDDY,			//好友管理模块
		CATEGORY,		//分组管理模块
		GROUP,			//群管理模块
		DISCUZ,			//讨论组模块
		CHAT,			//聊天模块
		EMAIL			//邮件模块
	}
}
