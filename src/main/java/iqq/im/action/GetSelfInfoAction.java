
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

/**
 * 获取群列表名称
 *
 * @author ChenZhiHui
 * @since 2013-2-21
 */
public class GetSelfInfoAction extends AbstractHttpAction {
    private static final Logger LOG = LoggerFactory.getLogger(GetSelfInfoAction.class);

    /**
     * <p>Constructor for GetGroupListAction.</p>
     *
     * @param context  a {@link QQContext} object.
     * @param listener a {@link QQActionListener} object.
     */
    public GetSelfInfoAction(QQContext context, QQActionListener listener) {
        super(context, listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QQHttpRequest onBuildRequest() throws QQException, JSONException {
        QQHttpRequest req = createHttpRequest("GET",
                QQConstants.URL_GET_SELF_INFO);
        req.addHeader("Referer", QQConstants.VREFFER);

        return req;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
            JSONException {
        notifyActionEvent(QQActionEvent.Type.EVT_OK, response.getResponseString());
    }

}
