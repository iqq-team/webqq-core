package iqq.im.action;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.event.QQActionEvent;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Tony on 10/6/15.
 */
public class CheckQRCodeAction extends AbstractHttpAction {
    private static final Logger LOG = LoggerFactory.getLogger(CheckQRCodeAction.class);

    /**
     * <p>Constructor for AbstractHttpAction.</p>
     *
     * @param context  a {@link QQContext} object.
     * @param listener a {@link QQActionListener} object.
     */
    public CheckQRCodeAction(QQContext context, QQActionListener listener) {
        super(context, listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
        QQHttpRequest req = createHttpRequest("GET", QQConstants.URL_CHECK_QRCODE);
        req.addGetValue("webqq_type", "10");
        req.addGetValue("remember_uin", "1");
        req.addGetValue("login2qq", "1");
        req.addGetValue("aid", "501004106");
        req.addGetValue("u1", "http://w.qq.com/proxy.html?login2qq=1&webqq_type=10");
        req.addGetValue("ptredirect", "0");
        req.addGetValue("ptlang", "2052");
        req.addGetValue("daid", "164");
        req.addGetValue("from_ui", "1");
        req.addGetValue("pttype", "1");
        req.addGetValue("dumy", "");
        req.addGetValue("fp", "loginerroralert");
        req.addGetValue("action", "0-0-4434");
        req.addGetValue("mibao_css", "m_webqq");
        req.addGetValue("t", "1");
        req.addGetValue("g", "1");
        req.addGetValue("js_type", "0");
        req.addGetValue("js_ver", "10135");
        req.addGetValue("login_sig", "");
        req.addGetValue("pt_randsalt", "0");
        return req;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onHttpStatusOK(QQHttpResponse response) throws QQException {
        Pattern pt = Pattern.compile(QQConstants.REGXP_LOGIN);
        Matcher mc = pt.matcher(response.getResponseString());
        LOG.info("WebLogin: " + response.getResponseString());
        if (mc.find()) {
            int ret = Integer.parseInt(mc.group(1));
            switch(ret){
                case 0: notifyActionEvent(QQActionEvent.Type.EVT_OK, mc.group(3)); break;
                case 66: throw new QQException(QQException.QQErrorCode.QRCODE_OK, mc.group(5));
                case 67: throw new QQException(QQException.QQErrorCode.QRCODE_AUTH, mc.group(5));
                default: throw new QQException(QQException.QQErrorCode.INVALID_USER, mc.group(5));
            }
        } else {
            throw new QQException(QQException.QQErrorCode.UNEXPECTED_RESPONSE);
        }
    }

}
