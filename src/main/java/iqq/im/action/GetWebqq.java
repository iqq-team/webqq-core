package iqq.im.action;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.core.QQContext;
import iqq.im.http.QQHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Tony on 4/10/16.
 */
public class GetWebqq extends AbstractHttpAction {
    private Logger logger = LoggerFactory.getLogger(GetWebqq.class);

    String url = "http://wspeed.qq.com/w.cgi";

    /**
     * <p>Constructor for AbstractHttpAction.</p>
     *
     * @param context  a {@link QQContext} object.
     * @param listener a {@link QQActionListener} object.
     */
    public GetWebqq(QQContext context, QQActionListener listener) {
        super(context, listener);
    }

    @Override
    public QQHttpRequest buildRequest() throws QQException {
        QQHttpRequest request = createHttpRequest("GET", url);
        request.addGetValue("appid", "1000164");
        request.addGetValue("touin", "null");
        request.addGetValue("releaseversion", "SMARTQQ");
        request.addGetValue("frequency", "1");
        request.addGetValue("commandid", "http://s.web2.qq.com/api/getvfwebqq");
        request.addGetValue("resultcode", "0");
        request.addGetValue("tmcost", "160");
        return request;
    }

}
