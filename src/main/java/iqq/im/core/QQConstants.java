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
 * <p>QQConstants interface.</p>
 *
 * @author solosky
 */
public interface QQConstants {
    /**
     * Constant <code>APPID="1003903"</code>
     */
    public static final String APPID = "1003903";
    /**
     * Constant <code>JSVER="10114"</code>
     */
    public static final String JSVER = "10114";
    /**
     * Constant <code>REFFER="http://d1.web2.qq.com/proxy.html?v=20110"{trunked}</code>
     */
    public static final String REFFER = "http://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2";
    public static final String REFERER_S = "http://s.web2.qq.com/proxy.html?v=20130916001&callback=1&id=1";

    /**

     * Constant <code>ORIGIN="http://d1.web2.qq.com"{trunked}</code>
     */
    public static final String ORIGIN = "http://d1.web2.qq.com";
    public static final String ORIGIN_S = "http://s.web2.qq.com";
    /**
     * Constant <code>USER_AGENT="IQQ Client/0.1 dev"</code>
     */
//    public static final String USER_AGENT = "IQQ Client/1.2 dev";
    public static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36";
    /**
     * Constant <code>URL_CHECK_VERIFY="https://ssl.ptlogin2.qq.com/check?uin={"{trunked}</code>
     */
    public static final String URL_CHECK_VERIFY = "https://ssl.ptlogin2.qq.com/check?pt_tea=1&uin={0}&appid=" + APPID + "&js_ver=10114&js_type=0&login_sig={1}&u1=http%3A%2F%2Fweb2.qq.com%2Floginproxy.html&r={2}";
    /**
     * Constant <code>URL_GET_CAPTCHA="http://captcha.qq.com/getimage"</code>
     */
    public static final String URL_GET_CAPTCHA = "http://captcha.qq.com/getimage";
    /**
     * Constant <code>URL_GET_QRCODE="https://ssl.ptlogin2.qq.com/ptqrshow"</code>
     */
    public static final String URL_GET_QRCODE = "https://ssl.ptlogin2.qq.com/ptqrshow";
    /**
     * Constant <code>URL_CHECK_QRCODE="https://ssl.ptlogin2.qq.com/ptqrlogin"</code>
     */
    public static final String URL_CHECK_QRCODE = "https://ssl.ptlogin2.qq.com/ptqrlogin";
    /**
     * Constant <code>REGXP_CHECK_VERIFY="ptui_checkVC\\('(.*?)','(.*?)','(.*?)'("{trunked}</code>
     */
    public static final String REGXP_CHECK_VERIFY = "ptui_checkVC\\('(.*?)','(.*?)','(.*?)'(,\\s*'(.*?)')?\\)";
    /**
     * Constant <code>REGXP_LOGIN="ptuiCB\\('(\\d+)','(\\d+)','(.*?)','(\\"{trunked}</code>
     */
    public static final String REGXP_LOGIN = "ptuiCB\\('(\\d+)','(\\d+)','(.*?)','(\\d+)','(.*?)', '(.*?)'\\)";
    /**
     * Constant <code>REGXP_JSON_SINGLE_RESULT="\\{([\\s\\S]*)\\}"</code>
     */
    public static final String REGXP_JSON_SINGLE_RESULT = "\\{([\\s\\S]*)\\}";
    /**
     * Constant <code>URL_UI_LOGIN="https://ssl.ptlogin2.qq.com/login"</code>
     */
    public static final String URL_UI_LOGIN = "https://ssl.ptlogin2.qq.com/login";
    /**
     * Constant <code>URL_CHANNEL_LOGIN="http://d1.web2.qq.com/channel/login2"</code>
     */
    public static final String URL_CHANNEL_LOGIN = "http://d1.web2.qq.com/channel/login2";
    /**
     * Constant <code>URL_GET_FRIEND_INFO="http://s.web2.qq.com/api/get_friend_inf"{trunked}</code>
     */
    public static final String URL_GET_FRIEND_INFO = "http://s.web2.qq.com/api/get_friend_info2";
    /**
     * Constant <code>URL_GET_STRANGER_INFO="http://s.web2.qq.com/api/get_stranger_i"{trunked}</code>
     */
    public static final String URL_GET_STRANGER_INFO = "http://s.web2.qq.com/api/get_stranger_info2";
    /**
     * Constant <code>URL_GET_IMAGE="https://ssl.captcha.qq.com/getimage"</code>
     */
    public static final String URL_GET_IMAGE = "https://ssl.captcha.qq.com/getimage";
    /**
     * Constant <code>URL_POLL_MSG="http://d1.web2.qq.com/channel/poll2"</code>
     */
    public static final String URL_POLL_MSG = "http://d1.web2.qq.com/channel/poll2";
    /**
     * Constant <code>URL_GET_USER_CATEGORIES="http://s.web2.qq.com/api/get_user_frien"{trunked}</code>
     */
    public static final String URL_GET_USER_CATEGORIES = "http://s.web2.qq.com/api/get_user_friends2";
    /**
     * Constant <code>URL_GET_USER_FACE="http://face10.qun.qq.com/cgi/svr/face/g"{trunked}</code>
     */
    public static final String URL_GET_USER_FACE = "http://face1.qun.qq.com/cgi/svr/face/getface";
    /**
     * Constant <code>URL_GET_GROUP_NAME_LIST="http://s.web2.qq.com/api/get_group_name"{trunked}</code>
     */
    public static final String URL_GET_GROUP_NAME_LIST = "http://s.web2.qq.com/api/get_group_name_list_mask2";
    /**
     * Constant <code>URL_GET_USER_ACCOUNT="http://s.web2.qq.com/api/get_friend_uin"{trunked}</code>
     */
    public static final String URL_GET_USER_ACCOUNT = "http://s.web2.qq.com/api/get_friend_uin2";
    /**
     * Constant <code>URL_GET_USER_SIGN="http://s.web2.qq.com/api/get_single_lon"{trunked}</code>
     */
    public static final String URL_GET_USER_SIGN = "http://s.web2.qq.com/api/get_single_long_nick2";
    /**
     * Constant <code>URL_GET_ONLINE_BUDDY_LIST="http://d1.web2.qq.com/channel/get_online"{trunked}</code>
     */
    public static final String URL_GET_ONLINE_BUDDY_LIST = "http://d1.web2.qq.com/channel/get_online_buddies2";
    /**
     * Constant <code>URL_SEND_BUDDY_MSG="http://d1.web2.qq.com/channel/send_buddy"{trunked}</code>
     */
    public static final String URL_SEND_BUDDY_MSG = "http://d1.web2.qq.com/channel/send_buddy_msg2";
    /**
     * Constant <code>URL_SEND_GROUP_MSG="http://d1.web2.qq.com/channel/send_qun_m"{trunked}</code>
     */
    public static final String URL_SEND_GROUP_MSG = "http://d1.web2.qq.com/channel/send_qun_msg2";
    /**
     * Constant <code>URL_SEND_DISCUZ_MSG="http://d1.web2.qq.com/channel/send_discu"{trunked}</code>
     */
    public static final String URL_SEND_DISCUZ_MSG = "http://d1.web2.qq.com/channel/send_discu_msg2";
    /**
     * Constant <code>URL_SEND_SESSION_MSG="http://d1.web2.qq.com/channel/send_sess_"{trunked}</code>
     */
    public static final String URL_SEND_SESSION_MSG = "http://d1.web2.qq.com/channel/send_sess_msg2";
    /**
     * Constant <code>URL_UPLOAD_OFFLINE_PICTURE="http://weboffline.ftn.qq.com/ftn_access"{trunked}</code>
     */
    public static final String URL_UPLOAD_OFFLINE_PICTURE = "http://weboffline.ftn.qq.com/ftn_access/upload_offline_pic";
    /**
     * Constant <code>URL_UPLOAD_CUSTOM_FACE="http://up.web2.qq.com/cgi-bin/cface_upl"{trunked}</code>
     */
    public static final String URL_UPLOAD_CUSTOM_FACE = "http://up.web2.qq.com/cgi-bin/cface_upload";
    /**
     * Constant <code>URL_CUSTOM_FACE_SIG="http://d1.web2.qq.com/channel/get_gface_"{trunked}</code>
     */
    public static final String URL_CUSTOM_FACE_SIG = "http://d1.web2.qq.com/channel/get_gface_sig2";
    /**
     * Constant <code>URL_LOGOUT="http://d1.web2.qq.com/channel/logout2"</code>
     */
    public static final String URL_LOGOUT = "http://d1.web2.qq.com/channel/logout2";
    /**
     * Constant <code>URL_CHANGE_STATUS="http://d1.web2.qq.com/channel/change_sta"{trunked}</code>
     */
    public static final String URL_CHANGE_STATUS = "http://d1.web2.qq.com/channel/change_status2";
    /**
     * Constant <code>URL_GET_GROUP_INFO_EXT="http://s.web2.qq.com/api/get_group_info"{trunked}</code>
     */
    public static final String URL_GET_GROUP_INFO_EXT = "http://s.web2.qq.com/api/get_group_info_ext2";
    /**
     * Constant <code>URL_GROUP_MESSAGE_FILTER="http://cgi.web2.qq.com/keycgi/qqweb/uac"{trunked}</code>
     */
    public static final String URL_GROUP_MESSAGE_FILTER = "http://cgi.web2.qq.com/keycgi/qqweb/uac/messagefilter.do";
    /**
     * Constant <code>URL_GET_DISCUZ_LIST="http://s.web2.qq.com/api/get_discus_lis"{trunked}</code>
     */
    public static final String URL_GET_DISCUZ_LIST = "http://s.web2.qq.com/api/get_discus_list";
    /**
     * Constant <code>URL_GET_DISCUZ_INFO="http://d1.web2.qq.com/channel/get_discu_"{trunked}</code>
     */
    public static final String URL_GET_DISCUZ_INFO = "http://d1.web2.qq.com/channel/get_discu_info";
    /**
     * Constant <code>URL_GET_RECENT_LIST="http://d1.web2.qq.com/channel/get_recent"{trunked}</code>
     */
    public static final String URL_GET_RECENT_LIST = "http://d1.web2.qq.com/channel/get_recent_list2";
    /**
     * Constant <code>URL_SHAKE_WINDOW="http://d1.web2.qq.com/channel/shake2"</code>
     */
    public static final String URL_SHAKE_WINDOW = "http://d1.web2.qq.com/channel/shake2";
    /**
     * Constant <code>URL_GET_OFFPIC="http://d1.web2.qq.com/channel/get_offpic"{trunked}</code>
     */
    public static final String URL_GET_OFFPIC = "http://d1.web2.qq.com/channel/get_offpic2";
    /**
     * Constant <code>URL_GET_CFACE2="http://d1.web2.qq.com/channel/get_cface2"</code>
     */
    public static final String URL_GET_CFACE2 = "http://d1.web2.qq.com/channel/get_cface2";
    /**
     * Constant <code>URL_GET_GROUP_PIC="http://web.qq.com/cgi-bin/get_group_pic"</code>
     */
    public static final String URL_GET_GROUP_PIC = "http://web.qq.com/cgi-bin/get_group_pic";
    /**
     * Constant <code>URL_GET_SESSION_MSG_SIG="http://d1.web2.qq.com/channel/get_c2cmsg"{trunked}</code>
     */
    public static final String URL_GET_SESSION_MSG_SIG = "http://d1.web2.qq.com/channel/get_c2cmsg_sig2";
    /**
     * Constant <code>URL_SEND_INPUT_NOTIFY="http://d1.web2.qq.com/channel/input_noti"{trunked}</code>
     */
    public static final String URL_SEND_INPUT_NOTIFY = "http://d1.web2.qq.com/channel/input_notify2";
    /**
     * Constant <code>URL_GET_USER_LEVEL="http://s.web2.qq.com/api/get_qq_level2"</code>
     */
    public static final String URL_GET_USER_LEVEL = "http://s.web2.qq.com/api/get_qq_level2";
    /**
     * Constant <code>URL_GET_GROUP_MEMBER_STATUS="http://s.web2.qq.com/api/get_group_memb"{trunked}</code>
     */
    public static final String URL_GET_GROUP_MEMBER_STATUS = "http://s.web2.qq.com/api/get_group_member_stat2";
    /**
     * Constant <code>URL_SERACH_USER_BY_UIN="http://s.web2.qq.com/api/search_qq_by_u"{trunked}</code>
     */
    public static final String URL_SERACH_USER_BY_UIN = "http://s.web2.qq.com/api/search_qq_by_uin2";
    /**
     * Constant <code>URL_ADD_NO_VERIFY2="http://s.web2.qq.com/api/add_no_verify2"</code>
     */
    public static final String URL_ADD_NO_VERIFY2 = "http://s.web2.qq.com/api/add_no_verify2";
    /**
     * Constant <code>URL_ADD_NEED_VERIFY2="http://s.web2.qq.com/api/add_need_verif"{trunked}</code>
     */
    public static final String URL_ADD_NEED_VERIFY2 = "http://s.web2.qq.com/api/add_need_verify2";
    /**
     * Constant <code>URL_ADD_ANSWER_AND_ADD="http://s.web2.qq.com/api/answer_and_add"{trunked}</code>
     */
    public static final String URL_ADD_ANSWER_AND_ADD = "http://s.web2.qq.com/api/answer_and_add2";
    /**
     * Constant <code>URL_LOGIN_PAGE="https://ui.ptlogin2.qq.com/cgi-bin/logi"{trunked}</code>
     */
    public static final String URL_LOGIN_PAGE = "https://ui.ptlogin2.qq.com/cgi-bin/login?daid=164&target=self&style=5&mibao_css=m_webqq&appid=" + APPID + "&enable_qlogin=0&no_verifyimg=1&s_url=http%3A%2F%2Fweb2.qq.com%2Floginproxy.html&f_url=loginerroralert&strong_login=1&login_state=10&t=20140612002";
    /**
     * Constant <code>URL_SEARCH_GROUP_INFO="http://cgi.web2.qq.com/keycgi/qqweb/gro"{trunked}</code>
     */
    public static final String URL_SEARCH_GROUP_INFO = "http://cgi.web2.qq.com/keycgi/qqweb/group/search.do";
    /**
     * Constant <code>REGXP_LOGIN_SIG="var g_login_sig=encodeURIComponent\\(\""{trunked}</code>
     */
    public static final String REGXP_LOGIN_SIG = "var g_login_sig=encodeURIComponent\\(\"(.*?)\"\\);";

    //好友添加请求
    public static final String URL_ACCEPET_BUDDY_ADD = "http://s.web2.qq.com/api/allow_and_add2";

    // Email
    /**
     * Constant <code>URL_EMAIL_POLL="http://wp.mail.qq.com/poll"</code>
     */
    public static final String URL_EMAIL_POLL = "http://wp.mail.qq.com/poll";
    /**
     * Constant <code>URL_PT4_AUTH="http://ptlogin2.qq.com/pt4_auth"</code>
     */
    public static final String URL_PT4_AUTH = "http://ptlogin2.qq.com/pt4_auth";
    /**
     * Constant <code>URL_GET_WP_KEY="http://mail.qq.com/cgi-bin/getwpkey"</code>
     */
    public static final String URL_GET_WP_KEY = "http://mail.qq.com/cgi-bin/getwpkey";
    /**
     * Constant <code>URL_LOGIN_EMAIL="http://mail.qq.com/cgi-bin/login?fun=pa"{trunked}</code>
     */
    public static final String URL_LOGIN_EMAIL = "http://mail.qq.com/cgi-bin/login?fun=passport&from=webqq";
    /**
     * Constant <code>URL_MARK_EMAIL="http://mail.qq.com/cgi-bin/mail_mgr"</code>
     */
    public static final String URL_MARK_EMAIL = "http://mail.qq.com/cgi-bin/mail_mgr";
    /**
     * Constant <code>REGXP_EMAIL_AUTH="ptui_auth_CB\\('(.*?)','(.*?)'\\)"</code>
     */
    public static final String REGXP_EMAIL_AUTH = "ptui_auth_CB\\('(.*?)','(.*?)'\\)";

    /**
     * Constant <code>MAX_POLL_ERR_CNT=10</code>
     */
    public static final int MAX_POLL_ERR_CNT = 20;

    /**
     * Constant <code>MAX_RETRY_TIMES=10</code>
     */
    public static final int MAX_RETRY_TIMES = 10;

    /**
     * Constant <code>HTTP_TIME_OUT=80000</code>
     */
    public static final int HTTP_TIME_OUT = 80000;

}
