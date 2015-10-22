// +----------------------------------------------------------------------
// | FileName:   XabHttpConfig.java
// +----------------------------------------------------------------------
// | CreateTime: 2013-7-5 下午2:10:43
// +----------------------------------------------------------------------
// | Copyright:  http://www.xueyong.net.cn
// +----------------------------------------------------------------------
// | Author:     xab(admin@xueyong.net.cn)
// +----------------------------------------------------------------------
package iqq.im.http;

import org.apache.http.protocol.HTTP;

/**
 * @author xab
 * @description
 * 
 */
public class XabHttpConfig {

	public static boolean ISPROXY = false;
	public static String PROXY_IP = "127.0.0.1";
	public static int PROXY_PORT = 80;
	public static String PROXY_USERNAME = "";
	public static String PROXY_PASSWORD = "";

	public static String ENCODING = HTTP.UTF_8;
}
