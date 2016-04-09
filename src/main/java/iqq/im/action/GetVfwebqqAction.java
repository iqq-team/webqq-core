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
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.core.QQService;
import iqq.im.core.QQSession;
import iqq.im.event.QQActionEvent;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;
import iqq.im.service.HttpService;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>CheckLoginSigAction class.</p>
 *
 * @author solosky
 */
public class GetVfwebqqAction extends AbstractHttpAction {
    private static final Logger LOG = LoggerFactory.getLogger(GetVfwebqqAction.class);

    /**
     * <p>Constructor for CheckLoginSigAction.</p>
     *
     * @param context a {@link QQContext} object.
     * @param listener a {@link QQActionListener} object.
     */
    public GetVfwebqqAction(QQContext context, QQActionListener listener) {
        super(context, listener);
    }

    /** {@inheritDoc} */
    @Override
    protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
            JSONException {

        JSONObject json = new JSONObject(response.getResponseString());
        QQSession session = getContext().getSession();
        if (json.getInt("retcode") == 0) {
            JSONObject ret = json.getJSONObject("result");
            session.setVfwebqq(ret.getString("vfwebqq"));
            notifyActionEvent(QQActionEvent.Type.EVT_OK, null);
        } else {
            notifyActionEvent(QQActionEvent.Type.EVT_ERROR, new QQException(QQException.QQErrorCode.UNKNOWN_ERROR));
        }

    }

    /** {@inheritDoc} */
    @Override
    protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
//		 http://s.web2.qq.com/api/getvfwebqq?ptwebqq=e084bc7a749db874109953417e728f274f032dcb767d04e24e9faf7db90cc293&clientid=53999199&psessionid=&t=1460020812176
        HttpService httpService = getContext().getSerivce(QQService.Type.HTTP);
        QQSession session = getContext().getSession();
        if (session.getClientId() == 0) {
            session.setClientId(53999199);
        }
        QQHttpRequest req = createHttpRequest("GET", QQConstants.URL_GET_VFWEBQQ);
        req.addGetValue("psessionid", StringUtils.defaultString(session.getSessionId(), ""));
        req.addGetValue("clientid", String.valueOf(session.getClientId()));
        req.addGetValue("ptwebqq", httpService.getCookie("ptwebqq", QQConstants.URL_CHANNEL_LOGIN).getValue());

        req.addGetValue("t", System.currentTimeMillis() / 1000 + "");

        req.addHeader("Referer", QQConstants.VREFFER);
        return req;
    }

}
