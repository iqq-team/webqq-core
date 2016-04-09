package iqq.im;

import iqq.im.util.QQEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// +----------------------------------------------------------------------
// | CreateTime: 15/10/22 
// +----------------------------------------------------------------------
// | Author:     xab(89415799@qq.com)
// +----------------------------------------------------------------------
// | CopyRight:  http://www.xueyong.net.cn
// +----------------------------------------------------------------------
public class XabHttpTest {
    private static final Logger LOG = LoggerFactory.getLogger(XabHttpTest.class);
    public static void main(String[] args) {
        LOG.info(QQEncryptor.hash("201286738","a9bd76faa9f0343d558a6689b6b6037d7b9853d603b8f7c4aed34eb9eeabfd94"));
//        XabHttpResult result = XabHttpRequest.getInstance().Get(QQConstants.URL_CONVERT_MSG);
//        LOG.info(result.getEOS());
    }
}
