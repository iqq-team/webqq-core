// +----------------------------------------------------------------------
// | FileName:   XabHttpRequest.java
// +----------------------------------------------------------------------
// | CreateTime: 2013-7-5 下午2:11:22
// +----------------------------------------------------------------------
// | Copyright:  http://www.xueyong.net.cn
// +----------------------------------------------------------------------
// | Author:     xab(admin@xueyong.net.cn)
// +----------------------------------------------------------------------
package iqq.im.http;

import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xab
 * @description
 * 
 */
public class XabHttpRequest {
	public static XabHttpRequest xhr;
	private DefaultHttpClient client;

	/**************************************************
	 * Xab - 2013-7-17 下午3:52:29
	 * Description
	 *  - 
	 * 构造函数
	 **************************************************/
	public XabHttpRequest() {
		
	}
	public static XabHttpRequest getInstance(){
		if(xhr==null){
			xhr=new XabHttpRequest();
		}
		return xhr;
	}
	public XabHttpResult Get(String url, Map<String, String> headers,
			Map<String, String> params, String encoding, boolean duan) {
		XabHttpResult result = new XabHttpResult();
		HttpGet hp = null;
		HttpResponse response = null;
		try {
			// 实例化一个Httpclient
			client = this.createHttpClient();
			HttpParams httpparams = client.getParams();

			// 如果有参数的就拼装起
			client.setParams(httpparams);
			url = url + (null == params ? "" : assemblyParameter(params));
			// 这是实例化一个get请求
			hp = new HttpGet(url);
			if (null != headers) {
				headers.put("Accept-Language", "zh-cn");
				headers.put("Cache-Control", "no-cache");
				headers.put("Accept", "*/*");
				headers.put("Accept-Encoding", "gzip");
				headers.put("Content-Encoding", "gzip");
				headers.put("Accept-Wncoding", "gzip, deflate");
				hp.setHeaders(assemblyHeader(headers));
			}
			if (XabHttpConfig.ISPROXY) {
				HttpHost proxy = new HttpHost(XabHttpConfig.PROXY_IP,
						XabHttpConfig.PROXY_PORT);
				httpparams.setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
				client.setParams(httpparams);
				AuthScope authscope = new AuthScope(XabHttpConfig.PROXY_IP,
						XabHttpConfig.PROXY_PORT);
				Credentials credentials = new UsernamePasswordCredentials(
						XabHttpConfig.PROXY_USERNAME,
						XabHttpConfig.PROXY_PASSWORD);
				client.getCredentialsProvider().setCredentials(authscope,
						credentials);
			}
			// 执行请求后返回一个HttpResponse
			client.addResponseInterceptor(new HttpResponseInterceptor() {
				public void process(final HttpResponse response,
						final HttpContext context) throws HttpException,
						IOException {
					HttpEntity entity = response.getEntity();
					Header ceheader = entity.getContentEncoding();
					if (ceheader != null) {
						for (HeaderElement element : ceheader.getElements()) {
							if (element.getName().equalsIgnoreCase("gzip")) {
								response.setEntity(new GzipDecompressingEntity(
										response.getEntity()));
							}
						}
					}
				}
			});
			response = client.execute(hp);
			if (duan)
				hp.abort();
			HttpEntity entity = response.getEntity();
			result.setStatusCode(response.getStatusLine().getStatusCode());
			result.setHeaders(response.getAllHeaders());
			result.setCookie(assemblyCookie(client.getCookieStore()
					.getCookies()));
			result.setHttpEntity(entity);
			result.setEOS(XabHttpResult.E2S(entity));
		} catch (Exception e) {
			result.setException(e);
		}
		return result;
	}

	// 发送get请求获取验证码
	public XabHttpResult GetVerifyCode(String url, Map<String, String> headers,
			Map<String, String> params, String fileUrl) {
		XabHttpResult result = new XabHttpResult();
		HttpGet hp = null;
		HttpResponse response = null;
		HttpEntity entity = null;
		try {
			// 实例化一个Httpclient
			client = this.createHttpClient();
			HttpParams httpparams = client.getParams();

			// 如果有参数的就拼装起
			client.setParams(httpparams);
			url = url + (null == params ? "" : assemblyParameter(params));
			// 这是实例化一个get请求
			hp = new HttpGet(url);
			if (null != headers) {
				headers.put("Accept-Language", "zh-cn");
				headers.put("Cache-Control", "no-cache");
				headers.put("Accept", "*/*");
				headers.put("Accept-Encoding", "gzip");
				headers.put("Accept-Wncoding", "gzip, deflate");
				hp.setHeaders(assemblyHeader(headers));
			}
			if (XabHttpConfig.ISPROXY) {
				HttpHost proxy = new HttpHost(XabHttpConfig.PROXY_IP,
						XabHttpConfig.PROXY_PORT);
				httpparams.setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
				client.setParams(httpparams);
				AuthScope authscope = new AuthScope(XabHttpConfig.PROXY_IP,
						XabHttpConfig.PROXY_PORT);
				Credentials credentials = new UsernamePasswordCredentials(
						XabHttpConfig.PROXY_USERNAME,
						XabHttpConfig.PROXY_PASSWORD);
				client.getCredentialsProvider().setCredentials(authscope,
						credentials);
			}
			// 执行请求后返回一个HttpResponse
			client.addResponseInterceptor(new HttpResponseInterceptor() {
				public void process(final HttpResponse response,
						final HttpContext context) throws HttpException,
						IOException {
					HttpEntity entity = response.getEntity();
					Header ceheader = entity.getContentEncoding();
					if (ceheader != null) {
						for (HeaderElement element : ceheader.getElements()) {
							if (element.getName().equalsIgnoreCase("gzip")) {
								response.setEntity(new GzipDecompressingEntity(
										response.getEntity()));
							}
						}
					}
				}
			});
			response = client.execute(hp);
			hp.abort();
			entity = response.getEntity();
			result.setStatusCode(response.getStatusLine().getStatusCode());
			result.setHeaders(response.getAllHeaders());
			result.setCookie(assemblyCookie(client.getCookieStore()
					.getCookies()));
			result.setHttpEntity(entity);
			InputStream in = entity.getContent();
			int temp = 0;
			File file = new File(fileUrl);
			FileOutputStream out = new FileOutputStream(file);
			while ((temp = in.read()) != -1) {
				out.write(temp);
			}
			in.close();
			out.close();
		} catch (Exception e) {
			result.setException(e);
		}

		return result;
	}

	public XabHttpResult Get(String url, Map<String, String> headers,
			Map<String, String> params, String encoding) {
		return Get(url, headers, params, encoding, false);
	}

	public XabHttpResult Get(String url, Map<String, String> headers,
			Map<String, String> params) {
		return Get(url, headers, params, XabHttpConfig.ENCODING, false);
	}

	public XabHttpResult Get(String url) {
		return Get(url, null, null, XabHttpConfig.ENCODING, false);
	}

	// 这是模拟post请求
	public XabHttpResult Post(String url, Map<String, String> headers,
			Map<String, String> params, String encoding) {
		// 封装返回的参数
		XabHttpResult result = new XabHttpResult();
		HttpResponse response = null;

		try {
			client = this.createHttpClient();
			HttpParams httpparams = client.getParams();
			// 设置代理服务器。
			if (XabHttpConfig.ISPROXY) {
				HttpHost proxy = new HttpHost(XabHttpConfig.PROXY_IP,
						XabHttpConfig.PROXY_PORT);
				httpparams.setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
				client.setParams(httpparams);
				AuthScope authscope = new AuthScope(XabHttpConfig.PROXY_IP,
						XabHttpConfig.PROXY_PORT);
				Credentials credentials = new UsernamePasswordCredentials(
						XabHttpConfig.PROXY_USERNAME,
						XabHttpConfig.PROXY_PASSWORD);
				client.getCredentialsProvider().setCredentials(authscope,
						credentials);
			}
			HttpPost post = new HttpPost(url);
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			for (String temp : params.keySet()) {
				list.add(new BasicNameValuePair(temp, params.get(temp)));
			}
			post.setEntity(new UrlEncodedFormEntity(list, encoding));
			// 设置头部
			if (null != headers) {
				headers.put("Accept-Language", "zh-cn");
				headers.put("Cache-Control", "no-cache");
				headers.put("Accept", "*/*");
				headers.put("Accept-Wncoding", "gzip, deflate");
				headers.put("Accept-Encoding", "gzip");
				post.setHeaders(assemblyHeader(headers));
			}
			client.addResponseInterceptor(new HttpResponseInterceptor() {
				public void process(final HttpResponse response,
						final HttpContext context) throws HttpException,
						IOException {
					HttpEntity entity = response.getEntity();
					Header ceheader = entity.getContentEncoding();
					if (ceheader != null) {
						for (HeaderElement element : ceheader.getElements()) {
							if (element.getName().equalsIgnoreCase("gzip")) {
								response.setEntity(new GzipDecompressingEntity(
										response.getEntity()));
							}
						}
					}
				}
			});

			response = client.execute(post);
			HttpEntity entity = response.getEntity();

			// 设置返回状态代码
			result.setStatusCode(response.getStatusLine().getStatusCode());

			result.setHeaders(response.getAllHeaders());
			// 设置返回的cookie信息
			result.setCookie(assemblyCookie(client.getCookieStore()
					.getCookies()));
			// 设置返回到信息
			result.setHttpEntity(entity);
			result.setEOS(XabHttpResult.E2S(entity));
		} catch (IOException e) {
			result.setException(e);
		}
		return result;
	}

	// 这是模拟上传文件
	public XabHttpResult Upload(String url, String fileurl,
			Map<String, String> headers, Map<String, String> params,
			String encoding) {
		// 封装返回的参数
		XabHttpResult result = new XabHttpResult();
		HttpResponse response = null;

		try {
			client = this.createHttpClient();
			HttpParams httpparams = client.getParams();
			// 设置代理服务器。
			if (XabHttpConfig.ISPROXY) {
				HttpHost proxy = new HttpHost(XabHttpConfig.PROXY_IP,
						XabHttpConfig.PROXY_PORT);
				httpparams.setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
				client.setParams(httpparams);
				AuthScope authscope = new AuthScope(XabHttpConfig.PROXY_IP,
						XabHttpConfig.PROXY_PORT);
				Credentials credentials = new UsernamePasswordCredentials(
						XabHttpConfig.PROXY_USERNAME,
						XabHttpConfig.PROXY_PASSWORD);
				client.getCredentialsProvider().setCredentials(authscope,
						credentials);
			}
			HttpPost post = new HttpPost(url);
			File file = new File(fileurl);
			MultipartEntity mpEntity = new MultipartEntity();
			ContentBody cbFile = new FileBody(file, "image/jpeg",
					XabHttpConfig.ENCODING);
			mpEntity.addPart("file", cbFile);
			if (null != params) {
				for (String temp : params.keySet()) {
					ContentBody cb = new StringBody(params.get(temp),
							Charset.forName("UTF-8"));
					mpEntity.addPart(temp, cb);
					cb = null;
				}
			}
			post.setEntity(mpEntity);
			// 设置头部
			if (null != headers) {
				headers.put("Accept-Language", "zh-cn");
				headers.put("Cache-Control", "no-cache");
				headers.put("Accept", "*/*");
				headers.put("Accept-Wncoding", "gzip, deflate");
				headers.put("Accept-Encoding", "gzip");
				post.setHeaders(assemblyHeader(headers));
			}
			client.addResponseInterceptor(new HttpResponseInterceptor() {
				public void process(final HttpResponse response,
						final HttpContext context) throws HttpException,
						IOException {
					HttpEntity entity = response.getEntity();
					Header ceheader = entity.getContentEncoding();
					if (ceheader != null) {
						for (HeaderElement element : ceheader.getElements()) {
							if (element.getName().equalsIgnoreCase("gzip")) {
								response.setEntity(new GzipDecompressingEntity(
										response.getEntity()));
							}
						}
					}
				}
			});

			response = client.execute(post);
			HttpEntity entity = response.getEntity();

			// 设置返回状态代码
			result.setStatusCode(response.getStatusLine().getStatusCode());

			result.setHeaders(response.getAllHeaders());
			// 设置返回的cookie信息
			result.setCookie(assemblyCookie(client.getCookieStore()
					.getCookies()));
			// 设置返回到信息
			result.setHttpEntity(entity);
			result.setEOS(XabHttpResult.E2S(entity));
		} catch (IOException e) {
			result.setException(e);
		}
		return result;
	}

	// 这是组装头部
	public static Header[] assemblyHeader(Map<String, String> headers) {
		Header[] allHeader = new BasicHeader[headers.size()];
		int i = 0;
		for (String str : headers.keySet()) {
			allHeader[i] = new BasicHeader(str, headers.get(str));
			i++;
		}
		return allHeader;
	}

	// 这是组装cookie
	public static String assemblyCookie(List<Cookie> cookies) {
		StringBuffer sbu = new StringBuffer();
		for (Cookie cookie : cookies) {
			sbu.append(cookie.getName()).append("=").append(cookie.getValue())
					.append(";");
		}
		if (sbu.length() > 0)
			sbu.deleteCharAt(sbu.length() - 1);
		return sbu.toString();
	}

	// 这是组装参数
	public static String assemblyParameter(Map<String, String> parameters) {
		String para = "?";
		for (String str : parameters.keySet()) {
			para += str + "=" + parameters.get(str) + "&";
		}
		return para.substring(0, para.length() - 1);
	}

	// 创建HttpClient实例
	private DefaultHttpClient createHttpClient() {
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params,
				HTTP.DEFAULT_CONTENT_CHARSET);
		HttpProtocolParams.setUseExpectContinue(params, true);
		// 从连接池中取连接的超时时间
		ConnManagerParams.setTimeout(params, 5000);
		// 连接超时
		HttpConnectionParams.setConnectionTimeout(params, 1200000);
		// 请求超时
		HttpConnectionParams.setSoTimeout(params, 1200000);
		SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schReg.register(new Scheme("https",
				SSLSocketFactory.getSocketFactory(), 443));

		ClientConnectionManager connMgr = new ThreadSafeClientConnManager(
				params, schReg);

		return new DefaultHttpClient(connMgr, params);
	}

	// 关闭连接管理器并释放资源
	public void closeHttpClient() {
		if (client != null && client.getConnectionManager() != null) {
			client.getConnectionManager().shutdown();
		}
	}
}
