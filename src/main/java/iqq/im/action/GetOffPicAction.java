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
 * File     : GetOffPicAction.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2013-4-8
 * License  : Apache License 2.0 
 */
package iqq.im.action;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.bean.QQMsg;
import iqq.im.bean.content.OffPicItem;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.core.QQSession;
import iqq.im.event.QQActionEvent;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;

import java.io.OutputStream;

import org.json.JSONException;

/**
 *
 * 获取聊天图片
 *
 * @author solosky <solosky772@qq.com>
 *
 */
public class GetOffPicAction extends AbstractHttpAction{
	private OffPicItem offpic;
	private QQMsg msg;
	private OutputStream picOut;
	
	public GetOffPicAction(QQContext context, QQActionListener listener, OffPicItem offpic, QQMsg msg, OutputStream picOut) {
		super(context, listener);
		this.offpic = offpic;
		this.msg = msg;
		this.picOut = picOut;
	}

	/* (non-Javadoc)
	 * @see iqq.im.action.AbstractHttpAction#onHttpStatusOK(iqq.im.http.QQHttpResponse)
	 */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		notifyActionEvent(QQActionEvent.Type.EVT_OK, offpic);
	}

	/* (non-Javadoc)
	 * @see iqq.im.action.AbstractHttpAction#onBuildRequest()
	 */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQHttpRequest req = createHttpRequest("GET", QQConstants.URL_GET_OFFPIC);
		QQSession session  = getContext().getSession();
		req.addGetValue("clientid", session.getClientId() + "");
		req.addGetValue("f_uin", msg.getFrom().getUin() + "");
		req.addGetValue("file_path", offpic.getFilePath());
		req.addGetValue("psessionid", session.getSessionId());
		req.setOutputStream(picOut);
		return req;
	}
	
	

}
