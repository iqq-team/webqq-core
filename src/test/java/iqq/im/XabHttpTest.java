package iqq.im;

import iqq.im.core.QQConstants;
import iqq.im.http.XabHttpRequest;
import iqq.im.http.XabHttpResult;

// +----------------------------------------------------------------------
// | CreateTime: 15/10/22 
// +----------------------------------------------------------------------
// | Author:     xab(89415799@qq.com)
// +----------------------------------------------------------------------
// | CopyRight:  http://www.xueyong.net.cn
// +----------------------------------------------------------------------
public class XabHttpTest {
    public static void main(String[] args) {
        XabHttpResult result = XabHttpRequest.getInstance().Get(QQConstants.URL_CONVERT_MSG);
        System.out.println(result.getEOS());
    }
}
