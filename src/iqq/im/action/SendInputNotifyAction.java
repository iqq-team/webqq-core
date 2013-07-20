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
 * File     : SendInputNotifyAction.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2013-4-10
 * License  : Apache License 2.0 
 */
package iqq.im.action;

import org.json.JSONException;
import org.json.JSONObject;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.QQException.QQErrorCode;
import iqq.im.bean.QQUser;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.core.QQSession;
import iqq.im.event.QQActionEvent;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;
import iqq.im.util.DateUtils;

/**
 *
 * 发送正在输入状态通知
 *
 * @author solosky <solosky772@qq.com>
 *
 */
public class SendInputNotifyAction extends AbstractHttpAction{
	private QQUser user;
	public SendInputNotifyAction(QQContext context, QQActionListener listener, QQUser user) {
		super(context, listener);
		this.user = user;
	}
	/* (non-Javadoc)
	 * @see iqq.im.action.AbstractHttpAction#onHttpStatusOK(iqq.im.http.QQHttpResponse)
	 */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		JSONObject json = new JSONObject(response.getResponseString());
		if (json.getInt("retcode") == 0) {
			notifyActionEvent(QQActionEvent.Type.EVT_OK, user);
		}else{
			notifyActionEvent(QQActionEvent.Type.EVT_ERROR, 
					new QQException(QQErrorCode.UNEXPECTED_RESPONSE, response.getResponseString()));
		}
		
		
	}
	/* (non-Javadoc)
	 * @see iqq.im.action.AbstractHttpAction#onBuildRequest()
	 */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQHttpRequest req = createHttpRequest("GET", QQConstants.URL_SEND_INPUT_NOTIFY);
		QQSession session  = getContext().getSession();
		req.addGetValue("clientid", session.getClientId() + "");
		req.addGetValue("to_uin", user.getUin() + "");
		req.addGetValue("t", DateUtils.nowTimestamp() + "");
		req.addGetValue("psessionid", session.getSessionId());
		return req;
	}
	
	

}
