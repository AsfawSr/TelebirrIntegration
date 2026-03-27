package com.et.pwsdemo.controller;

import com.et.pwsdemo.config.PWSConfig;
import com.et.pwsdemo.entity.response.ApplyH5TokenResponse;
import com.et.pwsdemo.service.ApplyFabricTokenService;
import com.et.pwsdemo.service.TelebirrMockService;
import com.et.pwsdemo.utils.OkHttpClientBuilder;
import com.et.pwsdemo.utils.ToolUtils;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.et.pwsdemo.utils.OkHttpClientBuilder.JSON;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@Controller
public class ApplyH5Token {

    @Autowired
    ApplyFabricTokenService applyFabricTokenService;

    @Autowired
    PWSConfig pwsConfig;

    @Autowired
    OkHttpClientBuilder okHttpClientBuilder;

    @Autowired
    TelebirrMockService telebirrMockService;

    /**
     * apply a H5 Token，
     * this token just for test, the real token need get from Super App use javascript interface.
     */
    @ResponseBody
    @RequestMapping("/apply/h5token")
    public ApplyH5TokenResponse applyH5Token() {
        if (pwsConfig.isMockEnabled()) {
            return telebirrMockService.mockApplyH5TokenResponse();
        }

        String fabricToken = applyFabricTokenService.applyFabricToken();
        Map<String, Object> params = createRequestObject();
        RequestBody body = RequestBody.create(new Gson().toJson(params), JSON);
        Request request = new Request.Builder()
                .url(pwsConfig.getBaseUrl() + "/payment/v1/auth/applyH5Token")
                .addHeader("X-APP-Key", pwsConfig.getFabricAppId())
                .addHeader("Authorization", fabricToken)
                .post(body)
                .build();
        try {
            OkHttpClient client = okHttpClientBuilder.createClient();
            Response response = client.newCall(request).execute();
            return new Gson().fromJson(response.body().string(), ApplyH5TokenResponse.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Map<String, Object> createRequestObject() {
        Map<String, Object> req = new HashMap<>();
        req.put("timestamp", ToolUtils.createTimeStamp());
        req.put("nonce_str", ToolUtils.createNonceStr());
        req.put("method", "payment.applyh5token");
        req.put("version", "1.0");
        req.put("app_code", pwsConfig.getMerchantCode());
        Map<String, Object> biz = new HashMap<>();
        req.put("biz_content", biz);

        // fill business object. more detail info, please query api description
        biz.put("appid", pwsConfig.getMerchantAppId());
        biz.put("auth_identifier", pwsConfig.getTestPhoneNumber());
        biz.put("auth_identifier_type", "01");
        biz.put("auth_type", "1000");
        biz.put("auth_merch_code", pwsConfig.getMerchantCode());
        biz.put("trade_type", "InApp");
        biz.put("resource_type", "OpenId");
        biz.put("auth_limit", "5");

        // sign type and sign string
        req.put("sign_type", "SHA256WithRSA");
        req.put("sign", ToolUtils.signRequestBody(req, pwsConfig.getMerchantPrivateKey()));
        return req;
    }
}
