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
 * Package  : iqq.im.bean
 * File     : QQAllow.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2013-4-15
 * License  : Apache License 2.0 
 */
package iqq.im.bean;

/**
 *
 * 对方设置的加好友策略
 *
 * @author solosky
 */
public enum QQAllow {
	/**允许所有人添加*/
	ALLOW_ALL, //0
	/**需要验证信息*/
	NEED_CONFIRM, //1
	/**拒绝任何人加好友*/
	REFUSE_ALL,	//2
	/**需要回答问题*/
	NEED_ANSWER, //3
	/**需要验证和回答问题*/
	NEED_ANSWER_AND_CONFIRM //4
}
