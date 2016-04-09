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
 * Package  : iqq.im.action
 * File     : GetFriendInfoAction.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-9-5
 * License  : Apache License 2.0 
 */
package iqq.im.action;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.bean.QQAllow;
import iqq.im.bean.QQClientType;
import iqq.im.bean.QQUser;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.core.QQSession;
import iqq.im.event.QQActionEvent;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;
import iqq.im.util.DateUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

/**
 *
 * 获取好友信息的请求
 *
 * @author solosky
 */
public class GetFriendInfoAction extends AbstractHttpAction{
	private QQUser buddy;
	/**
	 * <p>Constructor for GetFriendInfoAction.</p>
	 *
	 * @param context a {@link iqq.im.core.QQContext} object.
	 * @param listener a {@link iqq.im.QQActionListener} object.
	 * @param buddy a {@link iqq.im.bean.QQUser} object.
	 */
	public GetFriendInfoAction(QQContext context, QQActionListener listener, QQUser buddy) {
		super(context, listener);
		this.buddy = buddy;
	}
	/** {@inheritDoc} */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQSession session = getContext().getSession();
		/*
		tuin	236557647
		verifysession	
		code	
		vfwebqq	efa425e6afa21b3ca3ab8db97b65afa0535feb4af47a38cadcf1a4b1650169b4b4eee9955f843990
		t	1346856270187*/
		
		
		
		QQHttpRequest req = createHttpRequest("GET", QQConstants.URL_GET_FRIEND_INFO);
		req.addGetValue("tuin", buddy.getUin() + "");
		req.addGetValue("verifysession", "");	//难道有验证码？？？
		req.addGetValue("code", "");
		req.addGetValue("vfwebqq", session.getVfwebqq()); 
		req.addGetValue("t", System.currentTimeMillis()/1000+"");

		req.addHeader("Referer", QQConstants.VREFFER);
		return req;
	}
	
	
	/** {@inheritDoc} */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		JSONObject json = new JSONObject(response.getResponseString());
        if (json.getInt("retcode") == 0) {
            JSONObject obj = json.getJSONObject("result");
            try {
				buddy.setBirthday(DateUtils.parse(obj.getJSONObject("birthday")));
			} catch (ParseException e) {
				buddy.setBirthday(null);
			}
            buddy.setOccupation(obj.getString("occupation"));
            buddy.setPhone(obj.getString("phone"));
            buddy.setAllow(QQAllow.values()[obj.getInt("allow")]);
            buddy.setCollege(obj.getString("college"));
            if (!obj.isNull("reg_time")) {
                buddy.setRegTime(obj.getInt("reg_time"));
            }
            buddy.setUin(obj.getLong("uin"));
            buddy.setConstel(obj.getInt("constel"));
            buddy.setBlood(obj.getInt("blood"));
            buddy.setHomepage(obj.getString("homepage"));
            buddy.setStat(obj.getInt("stat"));
            buddy.setVipLevel(obj.getInt("vip_info")); // VIP等级 0为非VIP
            buddy.setCountry(obj.getString("country"));
            buddy.setCity(obj.getString("city"));
            buddy.setPersonal(obj.getString("personal"));
            buddy.setNickname(obj.getString("nick"));
            buddy.setChineseZodiac(obj.getInt("shengxiao"));
            buddy.setEmail(obj.getString("email"));
            buddy.setProvince(obj.getString("province"));
            buddy.setGender(obj.getString("gender"));
            buddy.setMobile(obj.getString("mobile"));
            if (!obj.isNull("client_type")) {
                buddy.setClientType(QQClientType.valueOfRaw(obj.getInt("client_type")));
            }
        }
        
        notifyActionEvent(QQActionEvent.Type.EVT_OK, buddy);
	}
}
