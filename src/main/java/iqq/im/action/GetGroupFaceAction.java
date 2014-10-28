package iqq.im.action;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.QQException.QQErrorCode;
import iqq.im.bean.QQGroup;
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
 *
 * @author ChenZhiHui
 * @since 2013-2-23
 */
public class GetGroupFaceAction extends AbstractHttpAction {

	private QQGroup group;

	/**
	 * <p>Constructor for GetGroupFaceAction.</p>
	 *
	 * @param context a {@link iqq.im.core.QQContext} object.
	 * @param listener a {@link iqq.im.QQActionListener} object.
	 * @param group a {@link iqq.im.bean.QQGroup} object.
	 */
	public GetGroupFaceAction(QQContext context, QQActionListener listener,
			QQGroup group) {
		super(context, listener);
		this.group = group;
	}

	/** {@inheritDoc} */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQSession session = getContext().getSession();
		QQHttpRequest req = createHttpRequest("GET",
				QQConstants.URL_GET_USER_FACE);
		req.addGetValue("uin", group.getCode() + "");
		req.addGetValue("vfwebqq", session.getVfwebqq());
		req.addGetValue("t", System.currentTimeMillis() / 1000 + "");
		req.addGetValue("cache", "0");
		req.addGetValue("type", "4");
		req.addGetValue("fid", "0");
		return req;
	}

	/** {@inheritDoc} */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		ByteArrayInputStream in = new ByteArrayInputStream(
				response.getResponseData()); // 输入流
		BufferedImage image = null;
		try {
			image = ImageIO.read(in);
			group.setFace(image);
		} catch (IOException e) {
			new QQException(QQErrorCode.IO_ERROR, e);
		}
		notifyActionEvent(QQActionEvent.Type.EVT_OK, group);
	}

}
