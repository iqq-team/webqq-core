// +----------------------------------------------------------------------
// | FileName:   XabHttpResult.java
// +----------------------------------------------------------------------
// | CreateTime: 2013-7-5 下午2:11:45
// +----------------------------------------------------------------------
// | Copyright:  http://www.xueyong.net.cn
// +----------------------------------------------------------------------
// | Author:     xab(admin@xueyong.net.cn)
// +----------------------------------------------------------------------
package iqq.im.http;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;

/**
 * @author xab
 * @处理保存HTTP请求返回结果
 * 
 */
public class XabHttpResult {
	private String cookie;
	private int statusCode;
	private HashMap<String, Header> headerAll;
	private HttpEntity httpEntity;
	private Exception e;
	private String EOS;

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public HashMap<String, Header> getHeaderAll() {
		return headerAll;
	}

	public void setHeaderAll(HashMap<String, Header> headerAll) {
		this.headerAll = headerAll;
	}

	public HttpEntity getHttpEntity() {
		return httpEntity;
	}

	public void setHttpEntity(HttpEntity httpEntity) {
		this.httpEntity = httpEntity;
	}

	public Exception getException() {
		return e;
	}

	public void setException(Exception e) {
		this.e = e;
	}

	public void setHeaders(Header[] headers) {
		headerAll = new HashMap<String, Header>();
		for (Header header : headers) {
			headerAll.put(header.getName(), header);
		}
	}

	public String getEOS() {
		return EOS;
	}

	public void setEOS(String eOS) {
		EOS = eOS;
	}

	public static String E2S(HttpEntity httpEntity, String encoding) {
		String str = "";
		try {
			str = EntityUtils.toString(httpEntity, encoding);
		} catch (Exception e) {
			str = "Entity2Str Error";
		}
		return str;
	}

	public static String E2S(HttpEntity httpEntity) {
		return E2S(httpEntity, XabHttpConfig.ENCODING);
	}
}
