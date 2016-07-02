package iqq.im.action;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.bean.QQAccount;
import iqq.im.bean.QQAllow;
import iqq.im.bean.QQClientType;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.core.QQStore;
import iqq.im.event.QQActionEvent;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;
import iqq.im.util.DateUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

/**
 * Created by liaojiacan on 2016/6/25.
 */
public class GetSelfInfoAction extends AbstractHttpAction {
    /**
     * <p>Constructor for AbstractHttpAction.</p>
     *
     * @param context  a {@link QQContext} object.
     * @param listener a {@link QQActionListener} object.
     */
    public GetSelfInfoAction(QQContext context, QQActionListener listener) {
        super(context, listener);
    }
    /** {@inheritDoc} */
    @Override
    protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
        QQHttpRequest req = createHttpRequest("GET", QQConstants.URL_GET_SELF_INFO);
        req.addGetValue("t", System.currentTimeMillis() / 1000 + "");
        req.addHeader("Referer",QQConstants.REFERER_S);
        return req;
    }

	/* (non-Javadoc)
	 * @see iqq.im.action.AbstractHttpAction#onHttpStatusOK(iqq.im.http.QQHttpResponse)
	 */
    /** {@inheritDoc} */
    @Override
    protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
            JSONException {
        JSONObject json = new JSONObject(response.getResponseString());
        QQAccount account = getContext().getAccount();
        if (json.getInt("retcode") == 0) {
            JSONObject obj = json.getJSONObject("result");
            try {
                account.setBirthday(DateUtils.parse(obj.getJSONObject("birthday")));
            } catch (ParseException e) {
                account.setBirthday(null);
            }
            account.setOccupation(obj.getString("occupation"));
            account.setPhone(obj.getString("phone"));
            account.setAllow(QQAllow.values()[obj.getInt("allow")]);
            account.setCollege(obj.getString("college"));
            if (!obj.isNull("reg_time")) {
                account.setRegTime(obj.getInt("reg_time"));
            }
            account.setUin(obj.getLong("uin"));
            account.setConstel(obj.getInt("constel"));
            account.setBlood(obj.getInt("blood"));
            account.setHomepage(obj.getString("homepage"));
            if(!obj.isNull("stat")){
                account.setStat(obj.getInt("stat"));
            }
            account.setSign(obj.getString("lnick"));
            account.setVipLevel(obj.getInt("vip_info")); // VIP等级 0为非VIP
            account.setCountry(obj.getString("country"));
            account.setCity(obj.getString("city"));
            account.setPersonal(obj.getString("personal"));
            account.setNickname(obj.getString("nick"));
            account.setChineseZodiac(obj.getInt("shengxiao"));
            account.setEmail(obj.getString("email"));
            account.setProvince(obj.getString("province"));
            account.setGender(obj.getString("gender"));
            account.setMobile(obj.getString("mobile"));
            account.setVfwebqq(obj.getString("vfwebqq"));
            if (!obj.isNull("client_type")) {
                account.setClientType(QQClientType.valueOfRaw(obj.getInt("client_type")));
            }

        }

        notifyActionEvent(QQActionEvent.Type.EVT_OK, account);
    }

}
