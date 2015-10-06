package iqq.im.http;

import iqq.im.core.QQConstants;
import org.apache.http.params.HttpConnectionParams;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * <p>QQSSLSocketFactory class.</p>
 */
public class QQSSLSocketFactory {
    static {
        System.out.println(">>>>in MySSLSocketFactory>>");
    }

    /**
     * <p>Constructor for QQSSLSocketFactory.</p>
     */
    public QQSSLSocketFactory() {
    }

    private SSLContext sslcontext = null;

    private SSLContext createSSLContext() {
        SSLContext sslcontext = null;
        try {
            sslcontext = SSLContext.getInstance("SSL");
            sslcontext.init(null,
                    new TrustManager[]{new TrustAnyTrustManager()},
                    new java.security.SecureRandom());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return sslcontext;
    }

    /**
     * <p>getSSLContext.</p>
     *
     * @return a {@link javax.net.ssl.SSLContext} object.
     */
    public SSLContext getSSLContext() {
        if (this.sslcontext == null) {
            this.sslcontext = createSSLContext();
        }
        return this.sslcontext;
    }

    /**
     * <p>createSocket.</p>
     *
     * @param socket    a {@link java.net.Socket} object.
     * @param host      a {@link java.lang.String} object.
     * @param port      a int.
     * @param autoClose a boolean.
     * @return a {@link java.net.Socket} object.
     * @throws java.io.IOException           if any.
     * @throws java.net.UnknownHostException if any.
     */
    public Socket createSocket(Socket socket, String host, int port,
                               boolean autoClose) throws IOException {
        return getSSLContext().getSocketFactory().createSocket(socket, host,
                port, autoClose);
    }

    /**
     * <p>createSocket.</p>
     *
     * @param host a {@link java.lang.String} object.
     * @param port a int.
     * @return a {@link java.net.Socket} object.
     * @throws java.io.IOException           if any.
     * @throws java.net.UnknownHostException if any.
     */
    public Socket createSocket(String host, int port) throws IOException {
        return getSSLContext().getSocketFactory().createSocket(host, port);
    }

    /**
     * <p>createSocket.</p>
     *
     * @param host       a {@link java.lang.String} object.
     * @param port       a int.
     * @param clientHost a {@link java.net.InetAddress} object.
     * @param clientPort a int.
     * @return a {@link java.net.Socket} object.
     * @throws java.io.IOException           if any.
     * @throws java.net.UnknownHostException if any.
     */
    public Socket createSocket(String host, int port, InetAddress clientHost, int clientPort) throws IOException {
        return getSSLContext().getSocketFactory().createSocket(host, port,
                clientHost, clientPort);
    }

    /**
     * <p>createSocket.</p>
     *
     * @param host         a {@link java.lang.String} object.
     * @param port         a int.
     * @param localAddress a {@link java.net.InetAddress} object.
     * @param localPort    a int.
     * @param params       a {@link org.apache.http.params.HttpConnectionParams} object.
     * @return a {@link java.net.Socket} object.
     * @throws java.io.IOException                          if any.
     * @throws java.net.UnknownHostException                if any.
     * @throws org.apache.http.conn.ConnectTimeoutException if any.
     */
    public Socket createSocket(String host, int port, InetAddress localAddress,
                               int localPort, HttpConnectionParams params) throws IOException {
        int timeout = QQConstants.HTTP_TIME_OUT;
        SocketFactory socketfactory = getSSLContext().getSocketFactory();
        if (timeout == 0) {
            return socketfactory.createSocket(host, port, localAddress, localPort);
        } else {
            Socket socket = socketfactory.createSocket();
            SocketAddress localAddr = new InetSocketAddress(localAddress, localPort);
            SocketAddress remoteAddr = new InetSocketAddress(host, port);
            socket.bind(localAddr);
            socket.connect(remoteAddr, timeout);
            socket.setSoTimeout(timeout);
            return socket;
        }
    }

    private static class TrustAnyTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }
}
