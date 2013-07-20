package iqq.im.action;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.QQException.QQErrorCode;
import iqq.im.bean.QQUser;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.core.QQSession;
import iqq.im.event.QQActionEvent;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.json.JSONException;

/**
 * 获取用户头像
 * @author ChenZhiHui
 * @create-time 2013-2-23
 */
public class GetFriendFaceAction extends AbstractHttpAction {

	private QQUser user;

	public GetFriendFaceAction(QQContext context, QQActionListener listener,
			QQUser user) {
		super(context, listener);
		this.user = user;
	}

	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQSession session = getContext().getSession();
		QQHttpRequest req = createHttpRequest("GET", QQConstants.URL_GET_USER_FACE);
		req.addGetValue("uin", user.getUin() + "");
		req.addGetValue("vfwebqq", session.getVfwebqq());
		req.addGetValue("t", System.currentTimeMillis() / 1000 + "");
		req.addGetValue("cache", 0 + ""); // ??
		req.addGetValue("type", 1 + ""); // ??
		req.addGetValue("fid", 0 + ""); // ??

		req.addHeader("Referer", QQConstants.REFFER);
		return req;
	}

	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		ByteArrayInputStream in = new ByteArrayInputStream(
				response.getResponseData()); // 输入流
		BufferedImage image = null;
		try {
			image = ImageIO.read(in);
			user.setFace(image);
		} catch (IOException e) {
			new QQException(QQErrorCode.IO_ERROR, e);
		}
		notifyActionEvent(QQActionEvent.Type.EVT_OK, user);
	}

}
