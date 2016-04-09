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
 * File     : GetLevelAction.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2013-4-11
 * License  : Apache License 2.0 
 */
package iqq.im.action;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.QQException.QQErrorCode;
import iqq.im.bean.QQLevel;
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

/**
 *
 * 获取用户等级
 *
 * @author solosky
 */
public class GetUserLevelAction extends AbstractHttpAction {
	private QQUser user;
	/**
	 * <p>Constructor for GetUserLevelAction.</p>
	 *
	 * @param context a {@link iqq.im.core.QQContext} object.
	 * @param listener a {@link iqq.im.QQActionListener} object.
	 * @param user a {@link iqq.im.bean.QQUser} object.
	 */
	public GetUserLevelAction(QQContext context, QQActionListener listener, QQUser user) {
		super(context, listener);
		this.user = user;
	}

	/** {@inheritDoc} */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		JSONObject json = new JSONObject(response.getResponseString());
		if (json.getInt("retcode") == 0) {
			JSONObject result = json.getJSONObject("result");
			QQLevel level = user.getLevel();
			level.setLevel(result.getInt("level"));
			level.setDays(result.getInt("days"));
			level.setHours(result.getInt("hours"));
			level.setRemainDays(result.getInt("remainDays"));
			notifyActionEvent(QQActionEvent.Type.EVT_OK, user);
		}else{
			notifyActionEvent(QQActionEvent.Type.EVT_ERROR, 
					new QQException(QQErrorCode.UNEXPECTED_RESPONSE, response.getResponseString()));
		}
	}

	/** {@inheritDoc} */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQHttpRequest req = createHttpRequest("GET", QQConstants.URL_GET_USER_LEVEL);
		QQSession session  = getContext().getSession();
		req.addGetValue("tuin", user.getUin() + "");
		req.addGetValue("t", DateUtils.nowTimestamp() + "");
		req.addGetValue("vfwebqq", session.getVfwebqq());
		req.addHeader("Referer", QQConstants.VREFFER);
		return req;
	}
}
