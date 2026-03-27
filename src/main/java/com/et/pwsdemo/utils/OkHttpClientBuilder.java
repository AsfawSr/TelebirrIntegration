package com.et.pwsdemo.utils;

import com.et.pwsdemo.config.PWSConfig;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.concurrent.TimeUnit;

@Component
public class OkHttpClientBuilder {
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    private final PWSConfig pwsConfig;
    private OkHttpClient okHttpClient;

    public OkHttpClientBuilder(PWSConfig pwsConfig) {
        this.pwsConfig = pwsConfig;
    }

    public synchronized OkHttpClient createClient() {
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    // Keep strict TLS trust validation and hostname verification.
                    .retryOnConnectionFailure(Boolean.TRUE)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS);

            if (hasText(pwsConfig.getTlsTrustStorePath())) {
                try {
                    X509TrustManager trustManager = buildTrustManager();
                    SSLContext sslContext = SSLContext.getInstance("TLS");
                    sslContext.init(null, new TrustManager[]{trustManager}, null);
                    builder.sslSocketFactory(sslContext.getSocketFactory(), trustManager);
                } catch (Exception ex) {
                    throw new RuntimeException("Unable to load Telebirr TLS trust store", ex);
                }
            }

            okHttpClient = builder.build();
        }
        return okHttpClient;
    }

    private X509TrustManager buildTrustManager() throws GeneralSecurityException {
        String trustStoreType = hasText(pwsConfig.getTlsTrustStoreType())
                ? pwsConfig.getTlsTrustStoreType() : "JKS";
        KeyStore trustStore = KeyStore.getInstance(trustStoreType);
        char[] password = pwsConfig.getTlsTrustStorePassword() == null
                ? new char[]{} : pwsConfig.getTlsTrustStorePassword().toCharArray();

        try (InputStream inputStream = new FileInputStream(pwsConfig.getTlsTrustStorePath())) {
            trustStore.load(inputStream, password);
        } catch (Exception ex) {
            throw new GeneralSecurityException("Could not read trust store file", ex);
        }

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm()
        );
        trustManagerFactory.init(trustStore);

        for (TrustManager trustManager : trustManagerFactory.getTrustManagers()) {
            if (trustManager instanceof X509TrustManager) {
                return (X509TrustManager) trustManager;
            }
        }
        throw new GeneralSecurityException("No X509TrustManager found in configured trust store");
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
}

