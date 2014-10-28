package iqq.im.action;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.QQException.QQErrorCode;
import iqq.im.bean.QQUser;
import iqq.im.bean.content.OffPicItem;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.core.QQService;
import iqq.im.core.QQSession;
import iqq.im.event.QQActionEvent;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;
import iqq.im.service.HttpService;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 消息发送
 *
 * @author ChenZhiHui
 * @since 2013-2-23
 */
public class UploadOfflinePictureAction extends AbstractHttpAction {
	private File file;
	private QQUser user;

	/**
	 * <p>Constructor for UploadOfflinePictureAction.</p>
	 *
	 * @param context a {@link iqq.im.core.QQContext} object.
	 * @param listener a {@link iqq.im.QQActionListener} object.
	 * @param user a {@link iqq.im.bean.QQUser} object.
	 * @param file a {@link java.io.File} object.
	 */
	public UploadOfflinePictureAction(QQContext context,
			QQActionListener listener, QQUser user, File file) {
		super(context, listener);
		this.user = user;
		this.file = file;
	}

	/** {@inheritDoc} */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {

		HttpService httpService = (HttpService) getContext().getSerivce(
				QQService.Type.HTTP);
		QQSession session = getContext().getSession();

		QQHttpRequest req = createHttpRequest("POST",
				QQConstants.URL_UPLOAD_OFFLINE_PICTURE);
		req.addGetValue("time", System.currentTimeMillis() / 1000 + "");
		req.addPostFile("file", file);
		req.addPostValue("callback", "parent.EQQ.Model.ChatMsg.callbackSendPic");
		req.addPostValue("locallangid", "2052");
		req.addPostValue("clientversion", "1409");
		req.addPostValue("uin", getContext().getAccount().getUin() + ""); // 自己的账号
		req.addPostValue("skey", httpService.getCookie("skey",  QQConstants.URL_UPLOAD_OFFLINE_PICTURE).getValue());
		req.addPostValue("appid", "1002101");
		req.addPostValue("peeruin", user.getUin() + ""); // 图片对方UIN
		req.addPostValue("fileid", "1");
		req.addPostValue("vfwebqq", session.getVfwebqq());
		req.addPostValue("senderviplevel", getContext().getAccount().getLevel().getLevel() + "");
		req.addPostValue("reciverviplevel", user.getLevel().getLevel() + "");
		return req;
	}

	/** {@inheritDoc} */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		Pattern p = Pattern.compile(QQConstants.REGXP_JSON_SINGLE_RESULT);
		Matcher m = p.matcher(response.getResponseString());
		OffPicItem pic = new OffPicItem();
		JSONObject obj = null;
		
		if (!m.find()) {
			pic.setSuccess(false);
			notifyActionEvent(QQActionEvent.Type.EVT_ERROR, 
					new QQException(QQErrorCode.UNEXPECTED_RESPONSE, response.getResponseString() ));
		}
		
		String regResult = m.group().replaceAll("[\\r]?[\\n]", " ")
			.replaceAll("[\r]?[\n]", " ");
		obj = new JSONObject(regResult);
		
		int retcode = obj.getInt("retcode");
		if (retcode == 0) {
			pic.setSuccess(obj.getInt("progress") == 100 ? true : false);
			pic.setFileSize(obj.getInt("filesize"));
			pic.setFileName(obj.getString("filename"));
			pic.setFilePath(obj.getString("filepath"));
			notifyActionEvent(QQActionEvent.Type.EVT_OK, pic);
			return;
		}

		// 失败后返回路径
		pic.setFilePath(file.getPath());
		pic.setSuccess(false);
		notifyActionEvent(QQActionEvent.Type.EVT_ERROR, 
				new QQException(QQErrorCode.UNEXPECTED_RESPONSE, response.getResponseString() ));
		
	}

}
