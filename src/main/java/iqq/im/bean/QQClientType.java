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
 * File     : QQClientType.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2013-3-24
 * License  : Apache License 2.0 
 */
package iqq.im.bean;

/**
 * 
 * QQ客户端类型
 * 可能有多个值对应同一种客户端的情况，这些值是否能进一步区分，还需做测试
 *
 * @author solosky <solosky772@qq.com>
 *
 */
public enum QQClientType {
	/**PC版QQ: [1,2,3,4,5,6,10,0x1E4]*/
	PC,
	/**WEBQQ: [41]*/
	WEBQQ,
	/**手机QQ: [21,22,23,24] */
	MOBILE,
	/**平板QQ: [42] (Android还是IOS不知道是否可以区分)*/
	PAD,
	/**其他值，待测试*/
	UNKNOWN;
	
	
	public static QQClientType valueOfRaw(int i){
		switch(i){
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 10:
		case 0x1E4:
			return PC;
		case 41:
			return WEBQQ;
		case 21:
		case 22:
		case 23:
		case 24:
			return MOBILE;
		case 42:
			return PAD;
		default:
			return UNKNOWN;	
		}
	}
}
