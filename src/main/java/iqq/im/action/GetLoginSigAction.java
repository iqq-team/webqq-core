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
 * File     : GetLoginSigAction.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : Aug 4, 2013
 * License  : Apache License 2.0 
 */
package iqq.im.action;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.QQException.QQErrorCode;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.core.QQSession;
import iqq.im.event.QQActionEvent;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * 获取从登陆页面获取LoginSig
 * 2013-08-03 接口更新
 *
 * @author solosky
 */
public class GetLoginSigAction extends AbstractHttpAction {
	private static Logger LOG = LoggerFactory.getLogger(GetLoginSigAction.class);

	/**
	 * <p>Constructor for GetLoginSigAction.</p>
	 *
	 * @param context a {@link iqq.im.core.QQContext} object.
	 * @param listener a {@link iqq.im.QQActionListener} object.
	 */
	public GetLoginSigAction(QQContext context, QQActionListener listener) {
		super(context, listener);
	}

	/** {@inheritDoc} */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		Pattern pt = Pattern.compile(QQConstants.REGXP_LOGIN_SIG);
		System.out.println(response.getResponseString());
		Matcher mc = pt.matcher(response.getResponseString());
		if(mc.find()){
			QQSession session = getContext().getSession();
			session.setLoginSig(mc.group(1));
			LOG.info("loginSig = " + session.getLoginSig());
			notifyActionEvent(QQActionEvent.Type.EVT_OK, session.getLoginSig());
		}else{
			notifyActionEvent(QQActionEvent.Type.EVT_ERROR, new QQException(QQErrorCode.INVALID_RESPONSE, "Login Sig Not Found!!"));
		}
	}

	/** {@inheritDoc} */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		return createHttpRequest("GET", QQConstants.URL_LOGIN_PAGE);
	}
}
