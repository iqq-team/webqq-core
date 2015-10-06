package iqq.im.action;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.event.QQActionEvent;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;
import org.json.JSONException;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by Tony on 10/6/15.
 */
public class GetQRCodeAction extends AbstractHttpAction {

    /**
     * <p>Constructor for AbstractHttpAction.</p>
     *
     * @param context  a {@link QQContext} object.
     * @param listener a {@link QQActionListener} object.
     */
    public GetQRCodeAction(QQContext context, QQActionListener listener) {
        super(context, listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
        QQHttpRequest req = createHttpRequest("GET", QQConstants.URL_GET_QRCODE);
        req.addGetValue("appid", "501004106");
        req.addGetValue("e", "0");
        req.addGetValue("l", "M");
        req.addGetValue("s", "5");
        req.addGetValue("d", "72");
        req.addGetValue("v", "4");
        req.addGetValue("4", Math.random() + "");
        return req;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onHttpStatusOK(QQHttpResponse response) throws QQException, JSONException {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(response.getResponseData());
            notifyActionEvent(QQActionEvent.Type.EVT_OK, ImageIO.read(in));
        } catch (IOException e) {
            notifyActionEvent(QQActionEvent.Type.EVT_ERROR, new QQException(QQException.QQErrorCode.UNKNOWN_ERROR, e));
        }
    }
}
