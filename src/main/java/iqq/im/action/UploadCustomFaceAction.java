package iqq.im.action;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.QQException.QQErrorCode;
import iqq.im.bean.content.CFaceItem;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.core.QQSession;
import iqq.im.event.QQActionEvent;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

/**
 * 消息发送
 *
 * @author ChenZhiHui
 * @since 2013-2-23
 */
public class UploadCustomFaceAction extends AbstractHttpAction {
	private static final Logger LOG = LoggerFactory.getLogger(UploadCustomFaceAction.class);
	private File file;

	/**
	 * <p>Constructor for UploadCustomFaceAction.</p>
	 *
	 * @param context a {@link iqq.im.core.QQContext} object.
	 * @param listener a {@link iqq.im.QQActionListener} object.
	 * @param file a {@link java.io.File} object.
	 */
	public UploadCustomFaceAction(QQContext context, QQActionListener listener, File file) {
		super(context, listener);
		this.file = file;
	}

	/** {@inheritDoc} */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {

		QQSession session = getContext().getSession();

		QQHttpRequest req = createHttpRequest("POST", QQConstants.URL_UPLOAD_CUSTOM_FACE);
		req.addGetValue("time", System.currentTimeMillis() / 1000 + "");
		req.addPostValue("from", "control");
		req.addPostValue("f", "EQQ.Model.ChatMsg.callbackSendPicGroup");
		req.addPostValue("vfwebqq", session.getVfwebqq());
		req.addPostValue("fileid", getContext().getStore().getPicItemListSize() + "");
		req.addPostFile("custom_face", file);

		return req;
	}

	/** {@inheritDoc} */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException, JSONException {
		// {'ret':0,'msg':'5F7E31F0001EF4310865F1FF4549B12B.jPg'}

		Pattern p = Pattern.compile(QQConstants.REGXP_JSON_SINGLE_RESULT);
		Matcher m = p.matcher(response.getResponseString());
		CFaceItem pic = new CFaceItem();
		JSONObject obj = null;
		if (m.find()) {
			try {
				String regResult = m.group().replaceAll("[\\r]?[\\n]", " ").replaceAll("[\r]?[\n]", " ");
				obj = new JSONObject(regResult);
				LOG.debug("cface result: " + obj.toString());

				int retcode = obj.getInt("ret");
				if (retcode == 0) {
					pic.setSuccess(true);
					pic.setFileName(obj.getString("msg"));
					notifyActionEvent(QQActionEvent.Type.EVT_OK, pic);

					getContext().getStore().addPicItem(pic);
					return;
				} else if (retcode == 4) {
					// {'ret':4,'msg':'D81AB7A7627ED673FDCD4DD24220C192.jPg
					// -6102 upload cface failed'}
					String prefix = "\"msg\":\"";
					String suffix = ".jPg";
					Pattern p4 = Pattern.compile(prefix + "([\\s\\S]*)" + suffix, Pattern.CASE_INSENSITIVE);
					Matcher m4 = p4.matcher(obj.toString());
					if (m4.find()) {
						String r = m4.group().replaceAll(prefix, "");
						LOG.debug("ret 4: " + r);
						pic.setSuccess(true);
						pic.setFileName(r);
						notifyActionEvent(QQActionEvent.Type.EVT_OK, pic);
						return;
					}
				} else {
					LOG.debug("ret: " + retcode);
				}
			} catch (Exception e) {
				LOG.warn(e.getMessage(), e);
			}
		}
		// 失败后返回路径
		pic.setFileName(file.getPath());
		pic.setSuccess(false);
		notifyActionEvent(QQActionEvent.Type.EVT_ERROR, new QQException(QQErrorCode.UNEXPECTED_RESPONSE));

		LOG.debug("CFace: " + response.getResponseString());
	}

}
