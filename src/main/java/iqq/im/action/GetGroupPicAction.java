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
import iqq.im.util.DateUtils;

import java.io.OutputStream;

import org.json.JSONException;

/**
 *
 * 获取群聊天图片
 *
 * @author solosky
 */
public class GetGroupPicAction extends AbstractHttpAction{
	private CFaceItem cface;
	private QQMsg msg;
	private OutputStream picOut;
	
	/**
	 * <p>Constructor for GetGroupPicAction.</p>
	 *
	 * @param context a {@link iqq.im.core.QQContext} object.
	 * @param listener a {@link iqq.im.QQActionListener} object.
	 * @param cface a {@link iqq.im.bean.content.CFaceItem} object.
	 * @param msg a {@link iqq.im.bean.QQMsg} object.
	 * @param picOut a {@link java.io.OutputStream} object.
	 */
	public GetGroupPicAction(QQContext context, QQActionListener listener,
										CFaceItem cface, QQMsg msg, OutputStream picOut) {
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
		QQHttpRequest req = createHttpRequest("GET", QQConstants.URL_GET_GROUP_PIC);
		
//		fid	3648788200
//		gid	2890126166
//		pic	{F2B04C26-9087-437D-4FD9-6A0ED84155FD}.jpg
//		rip	123.138.154.167
//		rport	8000
//		t	1365343106
//		type	0
//		uin	3559750777
//		vfwebqq	70b5f77bfb1db1367a2ec483ece317ea9ef119b9b59e542b2e8586f7ede6030ff56f7ba8798ba34b
//		"cface",
//        {
//            "name": "{F2B04C26-9087-437D-4FD9-6A0ED84155FD}.jpg",
//            "file_id": 3648788200,
//            "key": "pcm4N6IKmQ852Pus",
//            "server": "123.138.154.167:8000"
//        }
		
		QQSession session  = getContext().getSession();
		req.addGetValue("fid", cface.getFileId() + "");
		req.addGetValue("gid", (msg.getGroup() != null ? 
						msg.getGroup().getCode(): msg.getDiscuz().getDid()) + "");
		req.addGetValue("pic", cface.getFileName());
		String[] parts = cface.getServer().split(":");
		req.addGetValue("rip", parts[0]);
		req.addGetValue("rport", parts[1]);
		req.addGetValue("t", DateUtils.nowTimestamp() + "");
		req.addGetValue("type", msg.getGroup() != null ? "0" : "1");
		req.addGetValue("uin", msg.getFrom().getUin() + "");
		req.addGetValue("vfwebqq", session.getVfwebqq());
		
		req.setOutputStream(picOut);
		return req;
	}
	
	

}
