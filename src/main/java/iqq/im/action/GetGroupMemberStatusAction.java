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
 * Package  : iqq.im.action
 * File     : GetGroupMemberStatusAction.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2013-4-11
 * License  : Apache License 2.0 
 */
package iqq.im.action;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.QQException.QQErrorCode;
import iqq.im.bean.QQClientType;
import iqq.im.bean.QQGroup;
import iqq.im.bean.QQGroupMember;
import iqq.im.bean.QQStatus;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.event.QQActionEvent;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * 批量获取群成员在线状态
 *
 * @author solosky <solosky772@qq.com>
 *
 */
public class GetGroupMemberStatusAction extends AbstractHttpAction{
	private QQGroup group;
	
	public GetGroupMemberStatusAction(QQContext context,
			QQActionListener listener, QQGroup group) {
		super(context, listener);
		this.group = group;
	}
	
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		JSONObject json = new JSONObject(response.getResponseString());
		if(json.getInt("retcode") == 0){
			json = json.getJSONObject("result");
			
			// 消除所有成员状态，如果不在线的，webqq是不会返回的。
			for(QQGroupMember member: group.getMembers()){
				member.setStatus(QQStatus.OFFLINE);
				member.setClientType(QQClientType.UNKNOWN);
			}
			
			//result/stats
			JSONArray stats = json.getJSONArray("stats");
			for(int i=0; i<stats.length(); i++){
				// 下面重新设置最新状态
				JSONObject stat = stats.getJSONObject(i);
				QQGroupMember member = group.getMemberByUin(stat.getLong("uin"));
				if(member != null){
					member.setClientType(QQClientType.valueOfRaw(stat.getInt("client_type")));
					member.setStatus(QQStatus.valueOfRaw(stat.getInt("stat")));
				}
			}
			
			notifyActionEvent(QQActionEvent.Type.EVT_OK, group);
		}else{
			notifyActionEvent(QQActionEvent.Type.EVT_ERROR, QQErrorCode.UNEXPECTED_RESPONSE);
		}
	}

	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQHttpRequest req = createHttpRequest("GET", QQConstants.URL_GET_GROUP_INFO_EXT);
		req.addGetValue("gcode", group.getCode() + "");
		req.addGetValue("vfwebqq", getContext().getSession().getVfwebqq());
		req.addGetValue("t", System.currentTimeMillis()/1000 + "");
		return req;
	}
	

	
	
}
