package com.et.pwsdemo.service;

import com.et.pwsdemo.config.PWSConfig;
import com.et.pwsdemo.entity.response.FabricTokenResponse;
import com.et.pwsdemo.utils.OkHttpClientBuilder;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import static com.et.pwsdemo.utils.OkHttpClientBuilder.JSON;

import java.util.HashMap;
import java.util.Map;

@Service
public class ApplyFabricTokenService {
    private final PWSConfig pwsConfig;
    private final OkHttpClientBuilder okHttpClientBuilder;
    private final TelebirrMockService telebirrMockService;

    public ApplyFabricTokenService(PWSConfig pwsConfig,
                                   OkHttpClientBuilder okHttpClientBuilder,
                                   TelebirrMockService telebirrMockService) {
        this.pwsConfig = pwsConfig;
        this.okHttpClientBuilder = okHttpClientBuilder;
        this.telebirrMockService = telebirrMockService;
    }

    /**
     * apply a fabric token to request other interface
     */
    public String applyFabricToken() {
        if (pwsConfig.isMockEnabled()) {
            return telebirrMockService.mockFabricToken();
        }

        Map<String, String> params = new HashMap<>();
        params.put("appSecret", pwsConfig.getAppSecret());
        RequestBody body = RequestBody.create(new Gson().toJson(params), JSON);
        Request request = new Request.Builder()
                .url(pwsConfig.getBaseUrl() + "/payment/v1/token")
                .addHeader("X-APP-Key", pwsConfig.getFabricAppId())
                .post(body)
                .build();
        try {
            OkHttpClient client = okHttpClientBuilder.createClient();
            Response response = client.newCall(request).execute();
            FabricTokenResponse res = new Gson().fromJson(response.body().string(), FabricTokenResponse.class);
            return res.getToken();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }
}
