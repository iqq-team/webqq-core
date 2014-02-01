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
 * File     : GetDiscuzInfoAction.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2013-4-4
 * License  : Apache License 2.0 
 */
package iqq.im.action;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.QQException.QQErrorCode;
import iqq.im.bean.QQClientType;
import iqq.im.bean.QQDiscuz;
import iqq.im.bean.QQDiscuzMember;
import iqq.im.bean.QQStatus;
import iqq.im.bean.QQUser;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.core.QQSession;
import iqq.im.core.QQStore;
import iqq.im.event.QQActionEvent;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;
import iqq.im.util.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * 获取讨论组信息，讨论组成员
 *
 * @author solosky <solosky772@qq.com>
 *
 */
public class GetDiscuzInfoAction extends AbstractHttpAction{
	private QQDiscuz discuz;
	public GetDiscuzInfoAction(QQContext context, QQActionListener listener, QQDiscuz discuz) {
		super(context, listener);
		this.discuz = discuz;
	}
	
	/* (non-Javadoc)
	 * @see iqq.im.action.AbstractHttpAction#onBuildRequest()
	 */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQSession session = getContext().getSession();
		QQHttpRequest req = createHttpRequest("GET", QQConstants.URL_GET_DISCUZ_INFO);
		req.addGetValue("clientid", session.getClientId() + "");
		req.addGetValue("psessionid", session.getSessionId());
		req.addGetValue("vfwebqq", session.getVfwebqq());
		req.addGetValue("t", DateUtils.nowTimestamp() + "");
		req.addGetValue("did", discuz.getDid() + "");
		return req;
	}
	
	/* (non-Javadoc)
	 * @see iqq.im.action.AbstractHttpAction#onHttpStatusOK(iqq.im.http.QQHttpResponse)
	 */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		JSONObject json = new JSONObject(response.getResponseString());
		QQStore store = getContext().getStore();
        if (json.getInt("retcode") == 0) {
            JSONObject result = json.getJSONObject("result");
            
            //result/info
            JSONObject info = result.getJSONObject("info");
            discuz.setName(info.getString("discu_name"));
            discuz.setOwner(info.getLong("discu_owner"));
            
            //result/mem_list
            JSONArray memlist = result.getJSONArray("mem_info");
            for(int i=0; i<memlist.length(); i++){
            	JSONObject memjson = memlist.getJSONObject(i);
            	QQDiscuzMember member = discuz.getMemberByUin(memjson.getLong("uin"));
            	if(member == null) {
            		member = new QQDiscuzMember();
            		discuz.addMemeber(member);
            	}
            	member.setUin(memjson.getLong("uin"));
            	member.setQQ(memjson.getLong("uin"));	//这里有用户真实的QQ号
            	member.setNickname(memjson.getString("nick"));
            	member.setDiscuz(discuz);
            }
            
            // 消除所有成员状态，如果不在线的，webqq是不会返回的。
        	discuz.clearStatus();
            //result/mem_status
            JSONArray statlist = result.getJSONArray("mem_status");
            for(int i=0; i<statlist.length(); i++){
				// 下面重新设置最新状态
            	JSONObject statjson = statlist.getJSONObject(i);
            	QQUser member = discuz.getMemberByUin(statjson.getLong("uin"));
            	if(statjson.has("client_type") && member != null) {
                	member.setClientType(QQClientType.valueOfRaw(statjson.getInt("client_type")));
    				member.setStatus(QQStatus.valueOfRaw(statjson.getString("status")));
            	}
            }
            
            //result/mem_info
            JSONArray infolist = result.getJSONArray("mem_info");
            for(int i=0; i<infolist.length(); i++){
            	JSONObject infojson = infolist.getJSONObject(i);
            	QQUser member = discuz.getMemberByUin(infojson.getLong("uin"));
            	member.setNickname(infojson.getString("nick"));
            }
            
            notifyActionEvent(QQActionEvent.Type.EVT_OK, store.getDiscuzList());
        }else{
        	notifyActionEvent(QQActionEvent.Type.EVT_ERROR, new QQException(QQErrorCode.UNEXPECTED_RESPONSE));
        }
	}
	

}
