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
 * File     : LoginAction.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-9-2
 * License  : Apache License 2.0
 */
package iqq.im.action;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.QQException.QQErrorCode;
import iqq.im.bean.QQAccount;
import iqq.im.bean.QQStatus;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.core.QQService;
import iqq.im.core.QQSession;
import iqq.im.event.QQActionEvent;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;
import iqq.im.service.HttpService;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class ChannelLoginAction extends AbstractHttpAction {
    private QQStatus status;

    /**
     * <p>Constructor for ChannelLoginAction.</p>
     *
     * @param context  a {@link iqq.im.core.QQContext} object.
     * @param listener a {@link iqq.im.QQActionListener} object.
     * @param status   a {@link iqq.im.bean.QQStatus} object.
     */
    public ChannelLoginAction(QQContext context, QQActionListener listener, QQStatus status) {
        super(context, listener);
        this.status = status;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QQHttpRequest onBuildRequest() throws QQException, JSONException {
        HttpService httpService = (HttpService) getContext().getSerivce(QQService.Type.HTTP);
        QQSession session = getContext().getSession();
        if (session.getClientId() == 0) {
            session.setClientId(Math.abs(new Random().nextInt())); //random??
        }

        JSONObject json = new JSONObject();
        json.put("status", status.getValue());
        json.put("ptwebqq", httpService.getCookie("ptwebqq", QQConstants.URL_CHANNEL_LOGIN).getValue());
        json.put("passwd_sig", "");
        json.put("clientid", session.getClientId());
        json.put("psessionid", session.getSessionId());

        QQHttpRequest req = createHttpRequest("POST", QQConstants.URL_CHANNEL_LOGIN);
        req.addPostValue("r", json.toString());
        req.addPostValue("clientid", session.getClientId() + "");
        req.addPostValue("psessionid", session.getSessionId());

        req.addHeader("Referer", QQConstants.REFFER);
        return req;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onHttpStatusOK(QQHttpResponse response) throws QQException, JSONException {
        //{"retcode":0,"result":{"uin":236557647,"cip":1991953329,"index":1075,"port":51494,"status":"online","vfwebqq":"41778677efd86bae2ed575eea02349046a36f3f53298a34b97d75297ec1e67f6ee5226429daa6aa7","psessionid":"8368046764001d636f6e6e7365727665725f77656271714031302e3133332e342e31373200005b9200000549016e04004f95190e6d0000000a4052347371696a62724f6d0000002841778677efd86bae2ed575eea02349046a36f3f53298a34b97d75297ec1e67f6ee5226429daa6aa7","user_state":0,"f":0}}
        JSONObject json = new JSONObject(response.getResponseString());
        QQSession session = getContext().getSession();
        QQAccount account = getContext().getAccount();
        if (json.getInt("retcode") == 0) {
            JSONObject ret = json.getJSONObject("result");
            account.setUin(ret.getLong("uin"));
            account.setQQ(ret.getLong("uin"));
            session.setSessionId(ret.getString("psessionid"));
            session.setVfwebqq(ret.getString("vfwebqq"));
            account.setStatus(QQStatus.valueOfRaw(ret.getString("status")));
            session.setState(QQSession.State.ONLINE);
            session.setIndex(ret.getInt("index"));
            session.setPort(ret.getInt("port"));
            notifyActionEvent(QQActionEvent.Type.EVT_OK, null);
        } else {
            notifyActionEvent(QQActionEvent.Type.EVT_ERROR, new QQException(QQErrorCode.INVALID_RESPONSE));    //TODO ..
        }
    }

}
