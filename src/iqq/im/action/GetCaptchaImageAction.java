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
 * File     : GetCaptchaImageAction.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-9-6
 * License  : Apache License 2.0 
 */
package iqq.im.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.QQException.QQErrorCode;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.event.QQActionEvent;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;

import org.json.JSONException;

/**
 *
 * 获取验证码图片
 *
 * @author solosky <solosky772@qq.com>
 *
 */
public class GetCaptchaImageAction extends AbstractHttpAction {
	private long uin;
	public GetCaptchaImageAction(QQContext context, QQActionListener listener, long uin) {
		super(context, listener);
		this.uin = uin;
	}
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(response.getResponseData());
			notifyActionEvent(QQActionEvent.Type.EVT_OK, ImageIO.read(in));
		} catch (IOException e) {
			notifyActionEvent(QQActionEvent.Type.EVT_ERROR, new QQException(QQErrorCode.UNKNOWN_ERROR, e));
		}
	}
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		//http://captcha.qq.com/getimage?aid=1003903&r=0.09874371578916907&uin=1070772010
		QQHttpRequest req = createHttpRequest("GET", QQConstants.URL_GET_IMAGE);
		req.addGetValue("aid", QQConstants.APPID);
		req.addGetValue("r", Math.random() + "");
		req.addGetValue("uin", uin + "");
		return req;
	}

	
}
