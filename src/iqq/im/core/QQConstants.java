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
 * File     : QQConstants.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-9-1
 * License  : Apache License 2.0 
 */
package iqq.im.core;

/**
 * 
 * 
 * @author solosky <solosky772@qq.com>
 * 
 */
public interface QQConstants {
	public static final String APPID = "1003903";
	public static final String REFFER = "http://d.web2.qq.com/proxy.html?v=20110331002&callback=1&id=2";
	public static final String USER_AGENT = "IQQ Client/0.1 dev";
	public static final String URL_CHECK_VERIFY = "http://check.ptlogin2.qq.com/check?uin={0}&appid=1003903&r={1}";
	public static final String URL_GET_CAPTCHA = "http://captcha.qq.com/getimage?aid=1003903&r={0}&uin={1}";
	public static final String REGXP_CHECK_VERIFY = "ptui_checkVC\\('(.*?)','(.*?)','(.*?)'\\)";
	public static final String REGXP_LOGIN = "ptuiCB\\('(\\d+)','(\\d+)','(.*?)','(\\d+)','(.*?)', '(.*?)'\\)";
	public static final String REGXP_JSON_SINGLE_RESULT = "\\{([\\s\\S]*)\\}";
	public static final String URL_UI_LOGIN = "http://ptlogin2.qq.com/login";
	public static final String URL_CHANNEL_LOGIN = "http://d.web2.qq.com/channel/login2";
	public static final String URL_GET_FRIEND_INFO = "http://s.web2.qq.com/api/get_friend_info2";
	public static final String URL_GET_STRANGER_INFO = "http://s.web2.qq.com/api/get_stranger_info2";
	public static final String URL_GET_IMAGE = "http://captcha.qq.com/getimage";
	public static final String URL_POLL_MSG = "http://d.web2.qq.com/channel/poll2";
	public static final String URL_GET_USER_CATEGORIES = "http://s.web2.qq.com/api/get_user_friends2";
	public static final String URL_GET_USER_FACE = "http://face10.qun.qq.com/cgi/svr/face/getface";
	public static final String URL_GET_GROUP_NAME_LIST = "http://s.web2.qq.com/api/get_group_name_list_mask2";
	public static final String URL_GET_USER_ACCOUNT = "http://s.web2.qq.com/api/get_friend_uin2";
	public static final String URL_GET_USER_SIGN = "http://s.web2.qq.com/api/get_single_long_nick2";
	public static final String URL_GET_ONLINE_BUDDY_LIST = "http://d.web2.qq.com/channel/get_online_buddies2";
	public static final String URL_SEND_BUDDY_MSG = "http://d.web2.qq.com/channel/send_buddy_msg2";
	public static final String URL_SEND_GROUP_MSG = "http://d.web2.qq.com/channel/send_qun_msg2";
	public static final String URL_SEND_DISCUZ_MSG = "http://d.web2.qq.com/channel/send_discu_msg2";
	public static final String URL_SEND_SESSION_MSG = "http://d.web2.qq.com/channel/send_sess_msg2";
	public static final String URL_UPLOAD_OFFLINE_PICTURE = "http://weboffline.ftn.qq.com/ftn_access/upload_offline_pic";
	public static final String URL_UPLOAD_CUSTOM_FACE = "http://up.web2.qq.com/cgi-bin/cface_upload";
	public static final String URL_CUSTOM_FACE_SIG = "http://d.web2.qq.com/channel/get_gface_sig2";
	public static final String URL_LOGOUT = "http://d.web2.qq.com/channel/logout2";
	public static final String URL_CHANGE_STATUS = "http://d.web2.qq.com/channel/change_status2";
	public static final String URL_GET_GROUP_INFO_EXT = "http://s.web2.qq.com/api/get_group_info_ext2";
	public static final String URL_GROUP_MESSAGE_FILTER = "http://cgi.web2.qq.com/keycgi/qqweb/uac/messagefilter.do";
	public static final String URL_GET_DISCUZ_LIST = "http://d.web2.qq.com/channel/get_discu_list_new2";
	public static final String URL_GET_DISCUZ_INFO = "http://d.web2.qq.com/channel/get_discu_info";
	public static final String URL_GET_RECENT_LIST = "http://d.web2.qq.com/channel/get_recent_list2";
	public static final String URL_SHAKE_WINDOW = "http://d.web2.qq.com/channel/shake2";
	public static final String URL_GET_OFFPIC = "http://d.web2.qq.com/channel/get_offpic2";
	public static final String URL_GET_CFACE2= "http://d.web2.qq.com/channel/get_cface2";
	public static final String URL_GET_GROUP_PIC = "http://web.qq.com/cgi-bin/get_group_pic";
	public static final String URL_GET_SESSION_MSG_SIG = "http://d.web2.qq.com/channel/get_c2cmsg_sig2";
	public static final String URL_SEND_INPUT_NOTIFY = "http://d.web2.qq.com/channel/input_notify2";
	public static final String URL_GET_USER_LEVEL = "http://s.web2.qq.com/api/get_qq_level2";
	public static final String URL_GET_GROUP_MEMBER_STATUS = "http://s.web2.qq.com/api/get_group_member_stat2";
	public static final String URL_SERACH_USER_BY_UIN = "http://s.web2.qq.com/api/search_qq_by_uin2";
	public static final String URL_ADD_NO_VERIFY2 = "http://s.web2.qq.com/api/add_no_verify2";
	public static final String URL_ADD_NEED_VERIFY2 = "http://s.web2.qq.com/api/add_need_verify2";
	public static final String URL_ADD_ANSWER_AND_ADD = "http://s.web2.qq.com/api/answer_and_add2";
	public static final int MAX_POLL_ERR_CNT = 10;
	public static final int MAX_RETRY_TIMES = 5;
}
