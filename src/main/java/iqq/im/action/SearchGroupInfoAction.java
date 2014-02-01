package iqq.im.action;

import java.awt.image.BufferedImage;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.bean.QQGroupSearchInfo;
import iqq.im.bean.QQGroupSearchList;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.core.QQSession;
import iqq.im.event.QQActionEvent;
import iqq.im.event.QQNotifyEvent;
import iqq.im.event.QQNotifyEventArgs;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 查找群,并获取相应信息;
 * @author 元谷
 * @create-time 2013-8-13
 */

public class SearchGroupInfoAction extends AbstractHttpAction {

	private QQGroupSearchList buddy;
	public SearchGroupInfoAction(QQContext context, QQActionListener listener, QQGroupSearchList buddy) {
		super(context, listener);
		this.buddy = buddy;
	}
	
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQSession session = getContext().getSession();
		QQHttpRequest req = createHttpRequest("GET",
				QQConstants.URL_SEARCH_GROUP_INFO);
		
		//我不知道以下4个参数干啥？但是一致！	
		req.addGetValue("c1", "0");
		req.addGetValue("c2", "0");
		req.addGetValue("c3", "0");
		req.addGetValue("st", "0");
		
		
		req.addGetValue("pg", buddy.getCurrentPage() + "");
		req.addGetValue("perpage", buddy.getPageSize() + "");
		req.addGetValue("all", buddy.getKeyStr());
		
		
		req.addGetValue("vfwebqq", session.getVfwebqq());
		req.addGetValue("t", System.currentTimeMillis() / 1000 + "");
		req.addGetValue("type", 1 + "");
		req.addGetValue("vfcode", "");		
		
		return req;		
	}
	
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		JSONObject json = new JSONObject(response.getResponseString());
		
		if (json.getInt("retcode") == 0) {
			JSONArray result = json.getJSONArray("result");
			for(int index = 0; index < result.length(); index++){   //结果获取;
				QQGroupSearchInfo info = new QQGroupSearchInfo();
				JSONObject ret = result.getJSONObject(index);
				info.setGroupId(ret.getLong("GE"));  //真实的QQ号
				info.setOwerId(ret.getLong("QQ"));
				info.setGroupName(ret.getString("TI"));
				info.setCreateTimeStamp(ret.getLong("RQ"));  //QQ群创建时间,时间戳形式;
				info.setAliseGroupId(ret.getLong("GEX"));
				
			}
			
			
		}
		if(json.getInt("retcode") == 100110) //需要验证码
		{
			this.buddy.setNeedVfcode(true);
			
		}
		notifyActionEvent(QQActionEvent.Type.EVT_OK, buddy);
	}

}
