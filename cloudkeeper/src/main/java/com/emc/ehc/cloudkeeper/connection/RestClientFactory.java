package com.emc.ehc.cloudkeeper.connection;
/**
* @author Nick Zhu E-mail: nick.zhu@emc.com
* @version build time：Aug 1, 2016 2:46:05 PM
* 
*/
import java.net.Socket;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestClientFactory {
    
    private final static Logger logger = LoggerFactory.getLogger(RestClientFactory.class);
    
    public static Client configureClient() {
        final TrustManager[] certs = new TrustManager[] { new X509ExtendedTrustManager() {

            public void checkClientTrusted(X509Certificate[] arg0, String arg1) {

            }

            public void checkServerTrusted(X509Certificate[] arg0, String arg1) {

            }

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(X509Certificate[] arg0, String arg1, Socket arg2)
                    throws CertificateException {

            }

            @Override
            public void checkClientTrusted(X509Certificate[] arg0, String arg1, SSLEngine arg2)
                    throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] arg0, String arg1, Socket arg2)
                    throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] arg0, String arg1, SSLEngine arg2)
                    throws CertificateException {

            }

        } };

        SSLContext ctx = null;

        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(null, certs, new SecureRandom());
        } catch (final java.security.GeneralSecurityException ex) {
            logger.error("Exception occurred when getting and initializing SSLContext instance", ex);
        }

        HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());

        final ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        Client client;

        try {
            client = clientBuilder.sslContext(ctx).hostnameVerifier((hostname, session) -> true)
                    .register(JacksonFeature.class).build();
        } catch (final Exception e) {
            logger.error("Exception occurred when setting https properties for ClientConig", e);
            client = ClientBuilder.newBuilder().build();
        }

        return client;
    }

    public static Client createClient(String userName, String password) {
        final Client client = configureClient();
        final HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(userName, password);
        client.register(feature);
        return client;
    }
}
