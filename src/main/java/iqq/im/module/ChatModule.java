package iqq.im.module;

import iqq.im.QQActionListener;
import iqq.im.action.*;
import iqq.im.bean.QQMsg;
import iqq.im.bean.QQStranger;
import iqq.im.bean.QQUser;
import iqq.im.bean.content.CFaceItem;
import iqq.im.bean.content.OffPicItem;
import iqq.im.core.QQModule;
import iqq.im.event.QQActionEvent;
import iqq.im.event.QQActionFuture;
import iqq.im.event.future.ProcActionFuture;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.OutputStream;

/**
 * 消息处理
 *
 * @author ChenZhiHui
 * @since 2013-2-25
 */
public class ChatModule extends AbstractModule {

    private QQActionFuture doSendMsg(QQMsg msg, QQActionListener listener) {
        return pushHttpAction(new SendMsgAction(getContext(), listener, msg));
    }

    /**
     * <p>sendMsg.</p>
     *
     * @param msg      a {@link iqq.im.bean.QQMsg} object.
     * @param listener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture sendMsg(final QQMsg msg, QQActionListener listener) {
        if (msg.getType() == QQMsg.Type.SESSION_MSG) {
            final ProcActionFuture future = new ProcActionFuture(listener, true);
            QQStranger stranger = (QQStranger) msg.getTo();
//            if (StringUtils.isEmpty(stranger.getGroupSig())) {
            getSessionMsgSig(stranger, new QQActionListener() {
                @Override
                public void onActionEvent(QQActionEvent event) {
                    if (future.isCanceled()) {
                        return;
                    }
                    if (event.getType() == QQActionEvent.Type.EVT_OK) {
                        doSendMsg(msg, future);
                    } else if (event.getType() == QQActionEvent.Type.EVT_ERROR) {
                        future.notifyActionEvent(event.getType(), event.getTarget());
                    }
                }
            });
//            }
            return future;
        } else if (msg.getType() == QQMsg.Type.GROUP_MSG || msg.getType() == QQMsg.Type.DISCUZ_MSG) {
            final ProcActionFuture future = new ProcActionFuture(listener, true);
            if (msg.getType() == QQMsg.Type.GROUP_MSG) {
                if (msg.getGroup().getGin() == 0) {
                    GroupModule groupModule = getContext().getModule(QQModule.Type.GROUP);
                    groupModule.getGroupInfo(msg.getGroup(), new QQActionListener() {
                        @Override
                        public void onActionEvent(QQActionEvent event) {
                            if (future.isCanceled()) {
                                return;
                            }
                            if (event.getType() == QQActionEvent.Type.EVT_OK) {
                                sendMsg(msg, future);
                            } else if (event.getType() == QQActionEvent.Type.EVT_ERROR) {
                                future.notifyActionEvent(event.getType(), event.getTarget());
                            }
                        }
                    });
                    return future;
                }
            }
            if (StringUtils.isEmpty(getContext().getSession().getCfaceKey())) {
                getCFaceSig(new QQActionListener() {

                    @Override
                    public void onActionEvent(QQActionEvent event) {
                        if (future.isCanceled()) {
                            return;
                        }
                        if (event.getType() == QQActionEvent.Type.EVT_OK) {
                            sendMsg(msg, future);
                        } else if (event.getType() == QQActionEvent.Type.EVT_ERROR) {
                            future.notifyActionEvent(event.getType(), event.getTarget());
                        }
                    }
                });
                return future;
            }
        }

        return doSendMsg(msg, listener);
    }

    /**
     * <p>getSessionMsgSig.</p>
     *
     * @param user     a {@link iqq.im.bean.QQStranger} object.
     * @param listener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture getSessionMsgSig(QQStranger user, QQActionListener listener) {
        return pushHttpAction(new GetSessionMsgSigAction(getContext(), listener, user));
    }

    /**
     * <p>uploadOffPic.</p>
     *
     * @param user     a {@link iqq.im.bean.QQUser} object.
     * @param file     a {@link java.io.File} object.
     * @param listener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture uploadOffPic(QQUser user, File file, QQActionListener listener) {
        return pushHttpAction(new UploadOfflinePictureAction(getContext(), listener, user, file));
    }

    /**
     * <p>uploadCFace.</p>
     *
     * @param file     a {@link java.io.File} object.
     * @param listener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture uploadCFace(File file, QQActionListener listener) {
        return pushHttpAction(new UploadCustomFaceAction(getContext(),
                listener, file));
    }

    /**
     * <p>getCFaceSig.</p>
     *
     * @param listener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture getCFaceSig(QQActionListener listener) {
        return pushHttpAction(new GetCustomFaceSigAction(getContext(), listener));
    }

    /**
     * <p>sendShake.</p>
     *
     * @param user     a {@link iqq.im.bean.QQUser} object.
     * @param listener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture sendShake(QQUser user, QQActionListener listener) {
        return pushHttpAction(new ShakeWindowAction(getContext(), listener, user));
    }

    /**
     * <p>getOffPic.</p>
     *
     * @param offpic   a {@link iqq.im.bean.content.OffPicItem} object.
     * @param msg      a {@link iqq.im.bean.QQMsg} object.
     * @param picout   a {@link java.io.OutputStream} object.
     * @param listener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture getOffPic(OffPicItem offpic, QQMsg msg,
                                    OutputStream picout, QQActionListener listener) {
        return pushHttpAction(new GetOffPicAction(getContext(), listener, offpic, msg, picout));
    }

    /**
     * <p>getUserPic.</p>
     *
     * @param cface    a {@link iqq.im.bean.content.CFaceItem} object.
     * @param msg      a {@link iqq.im.bean.QQMsg} object.
     * @param picout   a {@link java.io.OutputStream} object.
     * @param listener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture getUserPic(CFaceItem cface, QQMsg msg,
                                     OutputStream picout, QQActionListener listener) {
        return pushHttpAction(new GetUserPicAction(getContext(), listener, cface, msg, picout));
    }

    /**
     * <p>getGroupPic.</p>
     *
     * @param cface    a {@link iqq.im.bean.content.CFaceItem} object.
     * @param msg      a {@link iqq.im.bean.QQMsg} object.
     * @param picout   a {@link java.io.OutputStream} object.
     * @param listener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture getGroupPic(CFaceItem cface, QQMsg msg,
                                      OutputStream picout, QQActionListener listener) {
        return pushHttpAction(new GetGroupPicAction(getContext(), listener, cface, msg, picout));
    }

    /**
     * <p>sendInputNotify.</p>
     *
     * @param user     a {@link iqq.im.bean.QQUser} object.
     * @param listener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture sendInputNotify(QQUser user, QQActionListener listener) {
        return pushHttpAction(new SendInputNotifyAction(getContext(), listener, user));
    }
}
