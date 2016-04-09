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
 * Package  : iqq.im.module
 * File     : ProcModule.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-9-2
 * License  : Apache License 2.0
 */
package iqq.im.module;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.QQException.QQErrorCode;
import iqq.im.bean.QQAccount;
import iqq.im.bean.QQStatus;
import iqq.im.core.QQModule;
import iqq.im.core.QQSession;
import iqq.im.event.*;
import iqq.im.event.future.ProcActionFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * 处理整体登陆逻辑
 *
 * @author solosky
 */
public class ProcModule extends AbstractModule {
    private static final Logger LOG = LoggerFactory.getLogger(ProcModule.class);

    /**
     * <p>check qrcode</p>
     *
     * @param listener
     * @return
     */
    public QQActionFuture checkQRCode(final QQActionListener listener) {
        final ProcActionFuture future = new ProcActionFuture(listener, true);
        LoginModule login = getContext().getModule(QQModule.Type.LOGIN);
        login.checkQRCode(new QQActionListener() {
            @Override
            public void onActionEvent(QQActionEvent event) {
                if (event.getType() == QQActionEvent.Type.EVT_OK) {
                    doCheckLoginSig((String) event.getTarget(), future);
                } else if (event.getType() == QQActionEvent.Type.EVT_ERROR) {
                    future.notifyActionEvent(QQActionEvent.Type.EVT_ERROR, event.getTarget());
                }
            }
        });
        return future;
    }
    private void getVfwebqq(final ProcActionFuture future) {
        LoginModule login = getContext().getModule(QQModule.Type.LOGIN);
        login.getVfwebqq(new QQActionListener() {
            @Override
            public void onActionEvent(QQActionEvent event) {
                if (event.getType() == QQActionEvent.Type.EVT_OK) {
                    doChannelLogin(future);
                } else if (event.getType() == QQActionEvent.Type.EVT_ERROR) {
                    future.notifyActionEvent(
                            QQActionEvent.Type.EVT_ERROR,
                            event.getTarget());
                }

            }
        });
    }

    /**
     * <p>login.</p>
     *
     * @param listener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture login(QQActionListener listener) {
        ProcActionFuture future = new ProcActionFuture(listener, true);
        doGetLoginSig(future);
        return future;
    }

    /**
     * <p>loginWithVerify.</p>
     *
     * @param verifyCode a {@link java.lang.String} object.
     * @param future     a {@link iqq.im.event.future.ProcActionFuture} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture loginWithVerify(String verifyCode, ProcActionFuture future) {
        doWebLogin(verifyCode, future);
        return future;
    }


    private void doGetLoginSig(final ProcActionFuture future) {
        LoginModule login = (LoginModule) getContext().getModule(QQModule.Type.LOGIN);
        login.getLoginSig(new QQActionListener() {
            @Override
            public void onActionEvent(QQActionEvent event) {
                if (event.getType() == QQActionEvent.Type.EVT_OK) {
                    doCheckVerify(future);
                } else if (event.getType() == QQActionEvent.Type.EVT_ERROR) {
                    future.notifyActionEvent(
                            QQActionEvent.Type.EVT_ERROR,
                            (QQException) event.getTarget());
                }
            }
        });
    }

    private void doGetVerify(final String reason, final ProcActionFuture future) {
        if (future.isCanceled()) {
            return;
        }
        QQAccount account = getContext().getAccount();
        LoginModule login = (LoginModule) getContext().getModule(QQModule.Type.LOGIN);
        login.getCaptcha(account.getUin(), new QQActionListener() {
            public void onActionEvent(QQActionEvent event) {
                if (event.getType() == QQActionEvent.Type.EVT_OK) {
                    QQNotifyEventArgs.ImageVerify verify = new QQNotifyEventArgs.ImageVerify();

                    verify.type = QQNotifyEventArgs.ImageVerify.VerifyType.LOGIN;
                    verify.image = (BufferedImage) event.getTarget();
                    verify.reason = reason;
                    verify.future = future;

                    getContext().fireNotify(new QQNotifyEvent(QQNotifyEvent.Type.CAPACHA_VERIFY, verify));
                } else if (event.getType() == QQActionEvent.Type.EVT_ERROR) {
                    future.notifyActionEvent(
                            QQActionEvent.Type.EVT_ERROR,
                            (QQException) event.getTarget());
                }
            }
        });


    }

    private void doCheckVerify(final ProcActionFuture future) {
        if (future.isCanceled()) {
            return;
        }

        LoginModule login = getContext().getModule(QQModule.Type.LOGIN);
        final QQAccount account = getContext().getAccount();
        login.checkVerify(account.getUsername(), new QQActionListener() {
            public void onActionEvent(QQActionEvent event) {
                if (event.getType() == QQActionEvent.Type.EVT_OK) {
                    QQActionEventArgs.CheckVerifyArgs args =
                            (QQActionEventArgs.CheckVerifyArgs) (event.getTarget());
                    account.setUin(args.uin);
                    if (args.result == 0) {
                        doWebLogin(args.code, future);
                    } else {
                        doGetVerify("为了保证您账号的安全，请输入验证码中字符继续登录。", future);
                    }
                } else if (event.getType() == QQActionEvent.Type.EVT_ERROR) {
                    future.notifyActionEvent(
                            QQActionEvent.Type.EVT_ERROR,
                            (QQException) event.getTarget());
                }

            }
        });
    }

    private void doWebLogin(String verifyCode, final ProcActionFuture future) {
        LoginModule login = getContext().getModule(QQModule.Type.LOGIN);
        QQAccount account = getContext().getAccount();
        login.webLogin(account.getUsername(), account.getPassword(),
                account.getUin(), verifyCode, new QQActionListener() {
                    public void onActionEvent(QQActionEvent event) {
                        if (event.getType() == QQActionEvent.Type.EVT_OK) {
                            doCheckLoginSig((String) event.getTarget(), future);
                        } else if (event.getType() == QQActionEvent.Type.EVT_ERROR) {
                            QQException ex = (QQException) (event.getTarget());
                            if (ex.getError() == QQErrorCode.WRONG_CAPTCHA) {
                                doGetVerify(ex.getMessage(), future);
                            } else {
                                future.notifyActionEvent(
                                        QQActionEvent.Type.EVT_ERROR,
                                        event.getTarget());
                            }
                        }
                    }
                });
    }

    private void doCheckLoginSig(String checkSigUrl, final ProcActionFuture future) {
        LoginModule login = getContext().getModule(QQModule.Type.LOGIN);
        login.checkLoginSig(checkSigUrl, new QQActionListener() {
            @Override
            public void onActionEvent(QQActionEvent event) {
                if (event.getType() == QQActionEvent.Type.EVT_OK) {
                    getVfwebqq(future);
                } else if (event.getType() == QQActionEvent.Type.EVT_ERROR) {
                    future.notifyActionEvent(
                            QQActionEvent.Type.EVT_ERROR,
                            event.getTarget());
                }

            }
        });
    }

    private void doChannelLogin(final ProcActionFuture future) {
        LoginModule login = getContext().getModule(QQModule.Type.LOGIN);
        login.channelLogin(getContext().getAccount().getStatus(), new QQActionListener() {
            public void onActionEvent(QQActionEvent event) {
                if (event.getType() == QQActionEvent.Type.EVT_OK) {
                    LOG.debug(event.toString());
                    future.notifyActionEvent(QQActionEvent.Type.EVT_OK, null);
                } else if (event.getType() == QQActionEvent.Type.EVT_ERROR) {
                    future.notifyActionEvent(QQActionEvent.Type.EVT_ERROR,
                            event.getTarget());
                }
            }
        });
    }

    /**
     * <p>relogin.</p>
     *
     * @param status   a {@link iqq.im.bean.QQStatus} object.
     * @param listener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture relogin(QQStatus status, final QQActionListener listener) {
        getContext().getAccount().setStatus(status);
        getContext().getSession().setState(QQSession.State.LOGINING);
        LoginModule login = getContext().getModule(QQModule.Type.LOGIN);
        LOG.info("iqq client relogin...");
        QQActionFuture future = login.channelLogin(status, new QQActionListener() {

            @Override
            public void onActionEvent(QQActionEvent event) {
                if (event.getType() == QQActionEvent.Type.EVT_ERROR) {
                    LOG.info("iqq client reloginChannel fail!!! use relogin.");
                    login(listener);
                } else {
                    listener.onActionEvent(event);
                }
            }
        });
        return future;
    }

    /**
     * <p>relogin.</p>
     */
    public void relogin() {
        QQSession session = getContext().getSession();
        if (session.getState() == QQSession.State.LOGINING) return;
        // 登录失效，重新登录
        relogin(getContext().getAccount().getStatus(), new QQActionListener() {

            @Override
            public void onActionEvent(QQActionEvent event) {
                if (event.getType() == QQActionEvent.Type.EVT_OK) {
                    // 重新登录成功重新POLL
                    getContext().fireNotify(new QQNotifyEvent(QQNotifyEvent.Type.RELOGIN_SUCCESS, null));
                } else if (event.getType() == QQActionEvent.Type.EVT_ERROR) {
                    getContext().fireNotify(new QQNotifyEvent(QQNotifyEvent.Type.UNKNOWN_ERROR, null));
                }
            }

        });
    }

    /**
     * <p>doPollMsg.</p>
     */
    public void doPollMsg() {
        final LoginModule login = getContext().getModule(QQModule.Type.LOGIN);
        login.pollMsg(new QQActionListener() {
            public void onActionEvent(QQActionEvent event) {
                // 回调通知事件函数
                if (event.getType() == QQActionEvent.Type.EVT_OK) {
                    List<QQNotifyEvent> events = (List<QQNotifyEvent>) event.getTarget();
                    for (QQNotifyEvent evt : events) {
                        getContext().fireNotify(evt);
                    }
                    // 准备提交下次poll请求
                    QQSession session = getContext().getSession();
                    if (session.getState() == QQSession.State.ONLINE) {
                        doPollMsg();
                    } else if (session.getState() != QQSession.State.KICKED) {
                        relogin();
                    }
                } else if (event.getType() == QQActionEvent.Type.EVT_ERROR) {
                    QQSession session = getContext().getSession();
                    QQAccount account = getContext().getAccount();
                    session.setState(QQSession.State.OFFLINE);
                    account.setStatus(QQStatus.OFFLINE);
                    //因为自带了错误重试机制，如果出现了错误回调，表明已经超时多次均失败，这里直接返回网络错误的异常
                    QQException ex = (QQException) event.getTarget();
                    QQErrorCode code = ex.getError();
                    if (code == QQErrorCode.INVALID_LOGIN_AUTH) {
                        relogin();
                    } else if (code == QQErrorCode.IO_ERROR || code == QQErrorCode.IO_TIMEOUT) {
                        //粗线了IO异常，直接报网络错误
                        getContext().fireNotify(new QQNotifyEvent(QQNotifyEvent.Type.NET_ERROR, ex));
                    } else {
                        LOG.warn("poll msg unexpected error, ignore it ...", ex);
                        relogin();
                        doPollMsg();
                    }
                } else if (event.getType() == QQActionEvent.Type.EVT_RETRY) {
                    LOG.error("poll msg error, retrying....", ((QQException) event.getTarget()).getMessage());
                }
            }
        });
    }

    /**
     * <p>doLogout.</p>
     *
     * @param listener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture doLogout(QQActionListener listener) {
        LoginModule login = (LoginModule) getContext().getModule(QQModule.Type.LOGIN);
        return login.logout(listener);
    }
}
