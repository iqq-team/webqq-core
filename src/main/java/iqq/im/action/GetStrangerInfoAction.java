
package iqq.im.action;

import java.text.ParseException;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.bean.QQAllow;
import iqq.im.bean.QQClientType;
import iqq.im.bean.QQUser;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.core.QQSession;
import iqq.im.event.QQActionEvent;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;
import iqq.im.util.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * <p>GetStrangerInfoAction class.</p>
 *
 * @author ZhiHui_Chen
 * @since date 2013-4-21
 */
public class GetStrangerInfoAction extends AbstractHttpAction {

	private QQUser user;
	

	/**
	 * <p>Constructor for GetStrangerInfoAction.</p>
	 *
	 * @param context a {@link iqq.im.core.QQContext} object.
	 * @param listener a {@link iqq.im.QQActionListener} object.
	 * @param user a {@link iqq.im.bean.QQUser} object.
	 */
	public GetStrangerInfoAction(QQContext context, QQActionListener listener,
			QQUser user) {
		super(context, listener);
		
		this.user = user;
	}
	
	/** {@inheritDoc} */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQSession session = getContext().getSession();
		QQHttpRequest req = createHttpRequest("GET", QQConstants.URL_GET_STRANGER_INFO);
		req.addGetValue("tuin", user.getUin() + "");
		req.addGetValue("verifysession", "");	// ?
		req.addGetValue("gid", "0");
		req.addGetValue("code", "");
		req.addGetValue("vfwebqq", session.getVfwebqq());
		req.addGetValue("t", System.currentTimeMillis()/1000 + "");
		return req;
	}
	
	/** {@inheritDoc} */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		/*
		 * {"retcode":0,"result":
		 * {"face":0,"birthday":{"month":0,"year":0,"day":0},
		 * "phone":"","occupation":"","allow":1,"college":"","uin":2842465527,"blood":0,
		 * "constel":0,"homepage":"","stat":10,"country":"","city":"","personal":"","nick":"平凡",
		 * "shengxiao":0,"email":"","token":"d04e802bda6ff115e31c3792199f15aa74f92eb435e75d93",
		 * "client_type":1,"province":"","gender":"male","mobile":"-"}}
		 */
		try{
		JSONObject json = new JSONObject(response.getResponseString());
        if (json.getInt("retcode") == 0) {
            JSONObject obj = json.getJSONObject("result");
            try {
				user.setBirthday(DateUtils.parse(obj.getJSONObject("birthday")));
			} catch (ParseException e) {
				user.setBirthday(null);
			}
            user.setOccupation(obj.getString("occupation"));
            user.setPhone(obj.getString("phone"));
            user.setAllow(QQAllow.values()[obj.getInt("allow")]);
            user.setCollege(obj.getString("college"));
            if (obj.has("reg_time")) {
                user.setRegTime(obj.getInt("reg_time"));
            }
            user.setUin(obj.getLong("uin"));
            user.setConstel(obj.getInt("constel"));
            user.setBlood(obj.getInt("blood"));
            user.setHomepage(obj.getString("homepage"));
            user.setStat(obj.getInt("stat"));
            if(obj.has("vip_info")) {
            	user.setVipLevel(obj.getInt("vip_info")); // VIP等级 0为非VIP
            }
            user.setCountry(obj.getString("country"));
            user.setCity(obj.getString("city"));
            user.setPersonal(obj.getString("personal"));
            user.setNickname(obj.getString("nick"));
            user.setChineseZodiac(obj.getInt("shengxiao"));
            user.setEmail("email");
            user.setProvince(obj.getString("province"));
            user.setGender(obj.getString("gender"));
            user.setMobile(obj.getString("mobile"));
            if (obj.has("client_type")) {
                user.setClientType(QQClientType.valueOfRaw(obj.getInt("client_type")));
            }
        }
		} catch(Exception e) {
			e.printStackTrace();
		}
        
        notifyActionEvent(QQActionEvent.Type.EVT_OK, user);
	}

}
