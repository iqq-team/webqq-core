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
 * File     : GetRecentListAction.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2013-4-5
 * License  : Apache License 2.0 
 */
package iqq.im.action;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.QQException.QQErrorCode;
import iqq.im.bean.QQBuddy;
import iqq.im.bean.QQDiscuz;
import iqq.im.bean.QQGroup;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.core.QQSession;
import iqq.im.core.QQStore;
import iqq.im.event.QQActionEvent;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * 获取最近联系人列表
 *
 * @author solosky <solosky772@qq.com>
 *
 */
public class GetRecentListAction extends AbstractHttpAction{

	public GetRecentListAction(QQContext context, QQActionListener listener) {
		super(context, listener);
	}
	
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQSession session = getContext().getSession();

		JSONObject json = new JSONObject();
		json.put("vfwebqq", session.getVfwebqq());
		json.put("clientid", session.getClientId()); 
		json.put("psessionid", session.getSessionId());
		
		QQHttpRequest req = createHttpRequest("POST", QQConstants.URL_GET_RECENT_LIST);
		req.addPostValue("r", json.toString());
		req.addPostValue("clientid", session.getClientId()+"");
		req.addPostValue("psessionid", session.getSessionId());

		return req;
	}

	/* (non-Javadoc)
	 * @see iqq.im.action.AbstractHttpAction#onHttpStatusOK(iqq.im.http.QQHttpResponse)
	 */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		JSONObject json = new JSONObject(response.getResponseString());
		List<Object> recents = new ArrayList<Object>();
		QQStore store = getContext().getStore();
        if (json.getInt("retcode") == 0) {
            JSONArray result = json.getJSONArray("result");
            for(int i=0; i<result.length(); i++){
            	 JSONObject rejson = result.getJSONObject(i);
            	 switch(rejson.getInt("type")){
            	 case 0:{	//好友
            		 QQBuddy buddy = store.getBuddyByUin(rejson.getLong("uin"));
            		 if(buddy != null){
            			 recents.add(buddy);
            		 }
            	 } break;
            	 
            	 case 1: {	//群
            		 QQGroup group = store.getGroupByCode(rejson.getLong("uin"));
            		 if(group != null){
            			 recents.add(group);
            		 }
            	 } break;
            	 
            	 case 2: {	//讨论组
            		 QQDiscuz discuz = store.getDiscuzByDid(rejson.getLong("uin"));
            		 if(discuz != null){
            			 recents.add(discuz);
            		 }
            	 }
            	 }
            }
            notifyActionEvent(QQActionEvent.Type.EVT_OK, recents);
        }else{
        	notifyActionEvent(QQActionEvent.Type.EVT_ERROR, new QQException(QQErrorCode.UNEXPECTED_RESPONSE));
        }
	}
	
	

}
