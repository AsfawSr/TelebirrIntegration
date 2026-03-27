package com.et.pwsdemo.utils;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class OkHttpClientBuilder {
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    private static OkHttpClient okHttpClient;

    public static OkHttpClient createClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    // Keep default TLS trust validation and hostname verification.
                    .retryOnConnectionFailure(Boolean.TRUE)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();
        }
        return okHttpClient;
    }
}

