package com.bhw.pay.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.cert.X509Certificate;

public class BaseHttpSSLSocketFactory extends SSLSocketFactory {

    private static Logger logger = LoggerFactory.getLogger(BaseHttpSSLSocketFactory.class);

    private SSLContext getSSLContext() {
        return createEasySSLContext();
    }

    @Override
    public Socket createSocket(InetAddress arg0, int arg1, InetAddress arg2,
                               int arg3) throws IOException {
        return getSSLContext().getSocketFactory().createSocket(arg0, arg1,
                arg2, arg3);
    }

    @Override
    public Socket createSocket(String arg0, int arg1, InetAddress arg2, int arg3)
            throws IOException, UnknownHostException {
        return getSSLContext().getSocketFactory().createSocket(arg0, arg1,
                arg2, arg3);
    }

    @Override
    public Socket createSocket(InetAddress arg0, int arg1) throws IOException {
        return getSSLContext().getSocketFactory().createSocket(arg0, arg1);
    }

    @Override
    public Socket createSocket(String arg0, int arg1) throws IOException,
            UnknownHostException {
        return getSSLContext().getSocketFactory().createSocket(arg0, arg1);
    }

    @Override
    public String[] getSupportedCipherSuites() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String[] getDefaultCipherSuites() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Socket createSocket(Socket arg0, String arg1, int arg2, boolean arg3)
            throws IOException {
        return getSSLContext().getSocketFactory().createSocket(arg0, arg1,
                arg2, arg3);
    }

    private SSLContext createEasySSLContext() {
        try {
            SSLContext context = SSLContext.getInstance("SSL");
            context.init(null,
                    new TrustManager[] { MyX509TrustManager.manger }, null);
            return context;
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            return null;
        }
    }

    public static class MyX509TrustManager implements X509TrustManager {

        static MyX509TrustManager manger = new MyX509TrustManager();

        public MyX509TrustManager() {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }
    }

    /**
     * ???????????????????????????????????????HTTPS????????????????????? PS:HTTPS hostname wrong: should be <localhost>
     */
    public static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            //????????????true
            return true;
        }
    }
}
