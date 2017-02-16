package iqq.im.action;

import iqq.im.QQException;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.QQActionListener;
import iqq.im.event.QQActionEvent;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by tony on 16/02/2017.
 */
public class InitLogin extends AbstractHttpAction {
    private static final Logger LOG = LoggerFactory.getLogger(InitLogin.class);

    public InitLogin(QQContext context, QQActionListener listener) {
        super(context, listener);
    }

    @Override
    public QQHttpRequest onBuildRequest() throws QQException, JSONException {
        QQHttpRequest req = createHttpRequest("GET", QQConstants.URL_LOGIN_INIT);
        req.addHeader("referer", "http://w.qq.com/");
        return req;
    }

    @Override
    protected void onHttpStatusOK(QQHttpResponse response) throws QQException, JSONException {
        LOG.info("init login: " + response.getHeaders());
        notifyActionEvent(QQActionEvent.Type.EVT_OK, null);
    }
}