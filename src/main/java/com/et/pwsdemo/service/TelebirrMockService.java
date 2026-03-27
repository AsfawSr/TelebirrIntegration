package com.et.pwsdemo.service;

import com.et.pwsdemo.config.PWSConfig;
import com.et.pwsdemo.entity.response.ApplyH5TokenResponse;
import com.et.pwsdemo.entity.response.AuthTokenResponse;
import com.et.pwsdemo.utils.ToolUtils;
import org.springframework.stereotype.Service;

@Service
public class TelebirrMockService {

    private final PWSConfig pwsConfig;

    public TelebirrMockService(PWSConfig pwsConfig) {
        this.pwsConfig = pwsConfig;
    }

    public String mockFabricToken() {
        return "mock-fabric-token-" + ToolUtils.createNonceStr();
    }

    public ApplyH5TokenResponse mockApplyH5TokenResponse() {
        ApplyH5TokenResponse response = new ApplyH5TokenResponse();
        response.setResult("SUCCESS");
        response.setCode("200");
        response.setMsg("Mock mode enabled");
        response.setNonce_str(ToolUtils.createNonceStr());
        response.setSign("MOCK_SIGNATURE");
        response.setSign_type("SHA256WithRSA");

        ApplyH5TokenResponse.BizContent biz = response.new BizContent();
        biz.setAccess_token("mock-h5-token-" + ToolUtils.createNonceStr());
        biz.setAppid(pwsConfig.getMerchantAppId());
        biz.setMerch_entry_url("https://mock.telebirr.local/entry");
        biz.setRegistered_time(ToolUtils.createTimeStamp());
        biz.setStatus("ACTIVE");
        response.setBiz_content(biz);
        return response;
    }

    public AuthTokenResponse mockAuthTokenResponse() {
        AuthTokenResponse response = new AuthTokenResponse();
        response.setResult("SUCCESS");
        response.setCode("200");
        response.setMsg("Mock mode enabled");
        response.setNonce_str(ToolUtils.createNonceStr());
        response.setSign("MOCK_SIGNATURE");
        response.setSign_type("SHA256WithRSA");

        AuthTokenResponse.BizContent biz = response.new BizContent();
        biz.setOpen_id("mock-open-id-" + ToolUtils.createNonceStr());
        biz.setIdentityId("mock-identity-id");
        biz.setIdentityType("OpenId");
        biz.setWalletIdentityId("mock-wallet-identity-id");
        response.setBiz_content(biz);
        return response;
    }

    public String mockCreateOrderRawRequest() {
        String nonce = ToolUtils.createNonceStr();
        String prepayId = "mock-prepay-" + nonce;
        String timestamp = ToolUtils.createTimeStamp();
        return "appid=" + pwsConfig.getMerchantAppId()
                + "&merch_code=" + pwsConfig.getMerchantCode()
                + "&nonce_str=" + nonce
                + "&prepay_id=" + prepayId
                + "&timestamp=" + timestamp
                + "&sign=MOCK_SIGNATURE&sign_type=SHA256WithRSA";
    }
}

