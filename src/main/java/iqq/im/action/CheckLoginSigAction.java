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
 * File     : CheckSigAction.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : Aug 4, 2013
 * License  : Apache License 2.0 
 */
package iqq.im.action;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.core.QQContext;
import iqq.im.event.QQActionEvent;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;

import org.apache.log4j.Logger;
import org.json.JSONException;

/**
 *
 *
 * @author solosky <solosky772@qq.com>
 *
 */
public class CheckLoginSigAction extends AbstractHttpAction{
	private static final Logger LOG = Logger.getLogger(CheckLoginSigAction.class);
	private String checkSigUrl;
	public CheckLoginSigAction(QQContext context, QQActionListener listener, String checkSigUrl) {
		super(context, listener);
		this.checkSigUrl = checkSigUrl;
	}
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		LOG.info("checkSig result:" + response.getResponseString());
		LOG.info("Location:" + response.getHeader("Location"));
		notifyActionEvent(QQActionEvent.Type.EVT_OK, null);
	}
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		return createHttpRequest("GET", checkSigUrl);
	}

}
