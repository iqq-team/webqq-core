
package iqq.im.action;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.QQException.QQErrorCode;
import iqq.im.bean.QQGroup;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.core.QQSession;
import iqq.im.core.QQStore;
import iqq.im.event.QQActionEvent;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * <p>UpdateGroupMessageFilterAction class.</p>
 *
 * @author ZhiHui_Chen
 * @since date 2013-4-15
 */
public class UpdateGroupMessageFilterAction extends AbstractHttpAction {


	/**
	 * <p>Constructor for UpdateGroupMessageFilterAction.</p>
	 *
	 * @param context a {@link iqq.im.core.QQContext} object.
	 * @param listener a {@link iqq.im.QQActionListener} object.
	 */
	public UpdateGroupMessageFilterAction(QQContext context, QQActionListener listener) {
		super(context, listener);
	}

	/** {@inheritDoc} */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		// retype:1 app:EQQ
		// itemlist:{"groupmask":{"321105219":"1","1638195794":"0","cAll":0,"idx":1075,"port":37883}}
		// vfwebqq:8b26c442e239630f250e1e74d135fd85ab78c38e7b8da1c95a2d1d560bdebd2691443df19d87e70d
		QQStore store = getContext().getStore();
		QQSession session = getContext().getSession();
		QQHttpRequest req = createHttpRequest("POST", QQConstants.URL_GROUP_MESSAGE_FILTER);
		req.addPostValue("retype", "1");	// 群？？？
		req.addPostValue("app", "EQQ");
		
		JSONObject groupmask = new JSONObject();
		groupmask.put("cAll", 0);
		groupmask.put("idx", session.getIndex());
		groupmask.put("port", session.getPort());
		for(QQGroup g : store.getGroupList()) {
			if(g.getGin() > 0) {
				groupmask.put(g.getGin() + "", g.getMask() + "");
			}
		}
		JSONObject itemlist = new JSONObject();
		itemlist.put("groupmask", groupmask);
		req.addPostValue("itemlist", itemlist.toString());
		req.addPostValue("vfwebqq", getContext().getSession().getVfwebqq());
		
		System.out.println("UpdateGroupMessageFilterAction: " + itemlist.toString() + " - " + store.getGroupList().size());
		return req;
	}

	/** {@inheritDoc} */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		// {"result":null,"retcode":0}
		JSONObject json = new JSONObject(response.getResponseString());
		if(json.getInt("retcode") == 0){
			notifyActionEvent(QQActionEvent.Type.EVT_OK, getContext().getStore().getGroupList());
		} else {
			notifyActionEvent(QQActionEvent.Type.EVT_ERROR, QQErrorCode.UNEXPECTED_RESPONSE);
		}
	}

}
