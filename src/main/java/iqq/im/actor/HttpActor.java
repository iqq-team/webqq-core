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
 * Package  : iqq.im.actor
 * File     : HttpActor.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-9-2
 * License  : Apache License 2.0 
 */
package iqq.im.actor;

import iqq.im.QQException;
import iqq.im.action.HttpAction;
import iqq.im.core.QQContext;
import iqq.im.core.QQService;
import iqq.im.event.QQActionEvent;
import iqq.im.http.QQHttpListener;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;
import iqq.im.service.HttpService;

import java.util.concurrent.Future;

/**
 * <p>HttpActor class.</p>
 *
 * @author solosky
 */
public class HttpActor implements QQActor {
	private Type type;
	private QQContext context;
	private HttpAction action;
	private QQHttpResponse response;
	private Throwable throwable;
	private long current;
	private long total;
	
	/** {@inheritDoc} */
	@Override
	public void execute() {
		try {
			switch (type) {
			case BUILD_REQUEST: {
				HttpService service = (HttpService) context.getSerivce(QQService.Type.HTTP);
				QQHttpRequest request = action.buildRequest();
				Future<QQHttpResponse> future = service.executeHttpRequest(request, new HttpAdaptor(context, action));
				action.setResponseFuture(future);
				}
				break;

			case CANCEL_REQUEST:
				action.cancelRequest();
				break;
				
			case ON_HTTP_ERROR:
				action.onHttpError(throwable);
				break;
				
			case ON_HTTP_FINISH:
				action.onHttpFinish(response);
				break;
				
			case ON_HTTP_HEADER:
				action.onHttpHeader(response);
				break;
				
			case ON_HTTP_READ:
				action.onHttpRead(current, total);
				break;
				
			case ON_HTTP_WRITE:
				action.onHttpWrite(current, total);
				break;

			}
		} catch (QQException e) {
			action.notifyActionEvent(QQActionEvent.Type.EVT_ERROR, e);
		}
	}
	
	
	public static enum Type{
		BUILD_REQUEST,
		CANCEL_REQUEST,
		ON_HTTP_ERROR,
		ON_HTTP_FINISH,
		ON_HTTP_HEADER,
		ON_HTTP_WRITE,
		ON_HTTP_READ
	}

	
	/**
	 * <p>Constructor for HttpActor.</p>
	 *
	 * @param type a {@link iqq.im.actor.HttpActor.Type} object.
	 * @param context a {@link iqq.im.core.QQContext} object.
	 * @param action a {@link iqq.im.action.HttpAction} object.
	 */
	public HttpActor(Type type, QQContext context, HttpAction action) {
		this.type = type;
		this.context = context;
		this.action = action;
	}


	/**
	 * <p>Constructor for HttpActor.</p>
	 *
	 * @param type a {@link iqq.im.actor.HttpActor.Type} object.
	 * @param context a {@link iqq.im.core.QQContext} object.
	 * @param action a {@link iqq.im.action.HttpAction} object.
	 * @param response a {@link iqq.im.http.QQHttpResponse} object.
	 */
	public HttpActor(Type type, QQContext context, HttpAction action, QQHttpResponse response) {
		this.type = type;
		this.context = context;
		this.action = action;
		this.response = response;
	}
	

	/**
	 * <p>Constructor for HttpActor.</p>
	 *
	 * @param type a {@link iqq.im.actor.HttpActor.Type} object.
	 * @param context a {@link iqq.im.core.QQContext} object.
	 * @param action a {@link iqq.im.action.HttpAction} object.
	 * @param throwable a {@link java.lang.Throwable} object.
	 */
	public HttpActor(Type type, QQContext context, HttpAction action, Throwable throwable) {
		this.type = type;
		this.context = context;
		this.action = action;
		this.throwable = throwable;
	}


	/**
	 * <p>Constructor for HttpActor.</p>
	 *
	 * @param type a {@link iqq.im.actor.HttpActor.Type} object.
	 * @param context a {@link iqq.im.core.QQContext} object.
	 * @param action a {@link iqq.im.action.HttpAction} object.
	 * @param current a long.
	 * @param total a long.
	 */
	public HttpActor(Type type, QQContext context, HttpAction action, long current, long total) {
		this.type = type;
		this.context = context;
		this.action = action;
		this.current = current;
		this.total = total;
	}


	public static class HttpAdaptor implements QQHttpListener{
		private QQContext context;
		private HttpAction action;
		
		public HttpAdaptor(QQContext context, HttpAction action) {
			this.context = context;
			this.action = action;
		}

		@Override
		public void onHttpFinish(QQHttpResponse response) {
			context.pushActor(new HttpActor(Type.ON_HTTP_FINISH, context, action, response));
		}

		@Override
		public void onHttpError(Throwable t) {
			context.pushActor(new HttpActor(Type.ON_HTTP_ERROR, context, action, t));
			
		}

		@Override
		public void onHttpHeader(QQHttpResponse response) {
			context.pushActor(new HttpActor(Type.ON_HTTP_HEADER, context, action, response));
			
		}

		@Override
		public void onHttpWrite(long current, long total) {
			context.pushActor(new HttpActor(Type.ON_HTTP_WRITE, context, action, current, total));
		}

		@Override
		public void onHttpRead(long current, long total) {
			context.pushActor(new HttpActor(Type.ON_HTTP_READ, context, action, current, total));
		}
	}


	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "HttpActor [type=" + type + ", action=" + action + "]";
	}
}
