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
import iqq.im.bean.content.CFaceItem;
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
 * @author solosky
 */
public class GetUserPicAction extends AbstractHttpAction{
	private CFaceItem cface;
	private QQMsg msg;
	private OutputStream picOut;
	
	/**
	 * <p>Constructor for GetUserPicAction.</p>
	 *
	 * @param context a {@link iqq.im.core.QQContext} object.
	 * @param listener a {@link iqq.im.QQActionListener} object.
	 * @param cface a {@link iqq.im.bean.content.CFaceItem} object.
	 * @param msg a {@link iqq.im.bean.QQMsg} object.
	 * @param picOut a {@link java.io.OutputStream} object.
	 */
	public GetUserPicAction(QQContext context, QQActionListener listener, CFaceItem cface, QQMsg msg, OutputStream picOut) {
		super(context, listener);
		this.cface = cface;
		this.msg = msg;
		this.picOut = picOut;
	}

	/* (non-Javadoc)
	 * @see iqq.im.action.AbstractHttpAction#onHttpStatusOK(iqq.im.http.QQHttpResponse)
	 */
	/** {@inheritDoc} */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		notifyActionEvent(QQActionEvent.Type.EVT_OK, cface);
	}

	/* (non-Javadoc)
	 * @see iqq.im.action.AbstractHttpAction#onBuildRequest()
	 */
	/** {@inheritDoc} */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQHttpRequest req = createHttpRequest("GET", QQConstants.URL_GET_CFACE2);
		
//		clientid=12202920
//		count=5
//		guid=4D72EF8CF64D53DECB31ABC2B601AB23.jpg
//		lcid=16059	//msg_id
//		psessionid=8368046764001e636f6e6e7365727665725f77656271714031302e3133332e34312e32303200002a5400000a2c026e04004f95190e6d0000000a40345a4e79386b71416e6d000000280adff44c88196358dadc9fa075334fd6293f7e6a0020a86cad689c240384e54cbb329be8dd5f0c3f
//		time=1
//		to=3559750777 //from_uin
		
		QQSession session  = getContext().getSession();
		req.addGetValue("clientid", session.getClientId() + "");
		req.addGetValue("to", msg.getFrom().getUin() + "");
		req.addGetValue("guid", cface.getFileName());
		req.addGetValue("psessionid", session.getSessionId());
		req.addGetValue("count", "5");
		req.addGetValue("lcid", msg.getId() + "");
		req.addGetValue("time", "1");
		req.setOutputStream(picOut);
		return req;
	}
	
	

}
