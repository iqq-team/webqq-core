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
 * Package  : iqq.im.protocol
 * File     : AbstractHttpAction.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-9-1
 * License  : Apache License 2.0 
 */
package iqq.im.action;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.QQException.QQErrorCode;
import iqq.im.actor.HttpActor;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.core.QQService;
import iqq.im.event.QQActionEvent;
import iqq.im.event.QQActionEventArgs;
import iqq.im.event.QQActionFuture;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;
import iqq.im.service.HttpService;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;
import org.json.JSONException;

/**
 *
 *
 * @author solosky <solosky772@qq.com>
 *
 */
public abstract class AbstractHttpAction implements HttpAction{
	private static final Logger LOG = Logger.getLogger(AbstractHttpAction.class);
	private QQContext context;
	private QQActionListener listener;
	private Future<QQHttpResponse> responseFuture;
	private QQActionFuture actionFuture;
	private int retryTimes;
	

	public AbstractHttpAction(QQContext context, QQActionListener listener) {
		this.context = context;
		this.listener = listener;
		this.retryTimes = 0;
	}

	@Override
	public void onHttpFinish(QQHttpResponse response) {
		try {
			LOG.debug(response.getContentType());
			String type = response.getContentType();
			if((type.startsWith("application/x-javascript") 
					|| type.startsWith("application/json") 
					|| type.indexOf("text") >= 0
					) && response.getContentLength() > 0){
				LOG.debug(response.getResponseString());
			}
			
			if(response.getResponseCode() == QQHttpResponse.S_OK){
				onHttpStatusOK(response);
			}else{
				onHttpStatusError(response);
			}
		} catch (QQException e) {
			notifyActionEvent(QQActionEvent.Type.EVT_ERROR, e);
		} catch (JSONException e) {
			notifyActionEvent(QQActionEvent.Type.EVT_ERROR, new QQException(QQErrorCode.JSON_ERROR, e));
		} catch (Throwable e){
			notifyActionEvent(QQActionEvent.Type.EVT_ERROR, new QQException(QQErrorCode.UNKNOWN_ERROR, e));
		}
	}


	@Override
	public void onHttpError(Throwable t) {
		if(!doRetryIt(getErrorCode(t), t)){
			notifyActionEvent(QQActionEvent.Type.EVT_ERROR, new QQException(getErrorCode(t), t));
		}
	}

	@Override
	public void onHttpWrite(long current, long total) {
		QQActionEventArgs.ProgressArgs progress = new QQActionEventArgs.ProgressArgs();
		progress.total = total;
		progress.current = current;
		notifyActionEvent(QQActionEvent.Type.EVT_WRITE, progress);
	}

	@Override
	public  void onHttpRead(long current, long total) {
		QQActionEventArgs.ProgressArgs progress = new QQActionEventArgs.ProgressArgs();
		progress.total = total;
		progress.current = current;
		notifyActionEvent(QQActionEvent.Type.EVT_READ, progress);
	}
	
	@Override
	public void onHttpHeader(QQHttpResponse response) {
	}

	@Override
	public void cancelRequest() {
		responseFuture.cancel(true);
		notifyActionEvent(QQActionEvent.Type.EVT_CANCELED, null);
	}

	protected QQContext getContext(){
		return this.context;
	}
	
	@Override
	public void setActionFuture(QQActionFuture future) {
		actionFuture = future;
	}
	

	@Override
	public void setResponseFuture(Future<QQHttpResponse> future) {
		responseFuture = future;
	}

	@Override
	public void notifyActionEvent(QQActionEvent.Type type, Object target) {
		if(listener != null){
			listener.onActionEvent(new QQActionEvent(type, target, actionFuture));
		}
		
	}

	@Override
	public QQHttpRequest buildRequest() throws QQException {
		try {
			return onBuildRequest();
		} catch (JSONException e) {
			throw new QQException(QQErrorCode.JSON_ERROR, e);
		} catch (Throwable e){
			throw new QQException(QQErrorCode.UNKNOWN_ERROR, e);
		}
	}

	protected QQHttpRequest createHttpRequest(String method, String url){
		HttpService httpService = (HttpService) getContext().getSerivce(QQService.Type.HTTP);
		return httpService.createHttpRequest(method, url);
	}
	
	protected void onHttpStatusError(QQHttpResponse response) throws QQException {
		if(!doRetryIt(QQErrorCode.ERROR_HTTP_STATUS, null)){
			throw new QQException(QQErrorCode.ERROR_HTTP_STATUS);
		}
	}
	
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException, JSONException{
		notifyActionEvent(QQActionEvent.Type.EVT_OK, null);
	}
	
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		return null;
	}

	@Override
	public QQActionListener getActionListener() {
		return listener;
	}

	@Override
	public void setActionListener(QQActionListener listener) {
		this.listener = listener;
	}

	@Override
	public boolean isCancelable() {
		return false;
	}
	
	private boolean doRetryIt(QQErrorCode code, Throwable t){
		if(actionFuture.isCanceled()){
			return true;
		}
	
		++retryTimes;
		if( retryTimes < QQConstants.MAX_RETRY_TIMES){
			notifyActionEvent(QQActionEvent.Type.EVT_RETRY, new QQException(code, t));
			try {
				// 等待几秒再重试
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				LOG.error("Sleep error...", e);
			}
			getContext().pushActor(new HttpActor(HttpActor.Type.BUILD_REQUEST, getContext(), this));
			return true;
		}
		
		return false;
	}
	
	private QQErrorCode getErrorCode(Throwable e){
		if(e instanceof SocketTimeoutException 
				|| e instanceof TimeoutException){
			return QQErrorCode.IO_TIMEOUT;
		}else if(e instanceof IOException){
			return QQErrorCode.IO_ERROR;
		}else {
			return QQErrorCode.UNKNOWN_ERROR;
		}
	}
}
