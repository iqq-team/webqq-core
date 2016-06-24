package iqq.im.action;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.core.QQService;
import iqq.im.core.QQSession;
import iqq.im.event.QQActionEvent;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;
import iqq.im.service.HttpService;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Tony on 4/10/16.
 */
public class GetVFWebqq extends AbstractHttpAction {
    private Logger logger = LoggerFactory.getLogger(GetVFWebqq.class);

    String url = "http://s.web2.qq.com/api/getvfwebqq";

    /**
     * <p>Constructor for AbstractHttpAction.</p>
     *
     * @param context  a {@link QQContext} object.
     * @param listener a {@link QQActionListener} object.
     */
    public GetVFWebqq(QQContext context, QQActionListener listener) {
        super(context, listener);
    }

    @Override
    public QQHttpRequest buildRequest() throws QQException {
        HttpService httpService = (HttpService) getContext().getSerivce(QQService.Type.HTTP);
        QQSession session = getContext().getSession();

        QQHttpRequest request = createHttpRequest("GET", url);
        request.addGetValue("ptwebqq", httpService.getCookie("ptwebqq", QQConstants.URL_CHANNEL_LOGIN).getValue());
        request.addGetValue("clientid", session.getClientId() + "");
        request.addGetValue("psessionid", "");
        request.addGetValue("t", System.currentTimeMillis() + "");
        return request;
    }

    @Override
    protected void onHttpStatusOK(QQHttpResponse response) throws QQException, JSONException {
        logger.info(response.getResponseString());
        QQSession session = getContext().getSession();

        JSONObject json = new JSONObject(response.getResponseString());
        if (json.getInt("retcode") == 0) {
            JSONObject ret = json.getJSONObject("result");
            session.setVfwebqq(ret.getString("vfwebqq"));
            System.out.println("vfw:"+session.getVfwebqq());
            notifyActionEvent(QQActionEvent.Type.EVT_OK, null);
        } else {
            notifyActionEvent(QQActionEvent.Type.EVT_ERROR, new QQException(QQException.QQErrorCode.INVALID_RESPONSE));    //TODO ..
        }
    }
}
