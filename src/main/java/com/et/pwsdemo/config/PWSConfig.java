package com.et.pwsdemo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Telebirr configuration loaded from application properties/environment.
 */
@Component
@ConfigurationProperties(prefix = "telebirr")
public class PWSConfig {

    private String fabricAppId;
    private String appSecret;
    private String merchantAppId;
    private String merchantCode;
    private String merchantPrivateKey;
    private String testPhoneNumber;
    private String baseUrl;
    private String notifyUrl;
    private String tlsTrustStorePath;
    private String tlsTrustStorePassword;
    private String tlsTrustStoreType;
    private boolean mockEnabled;

    public String getFabricAppId() {
        return fabricAppId;
    }

    public void setFabricAppId(String fabricAppId) {
        this.fabricAppId = fabricAppId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getMerchantAppId() {
        return merchantAppId;
    }

    public void setMerchantAppId(String merchantAppId) {
        this.merchantAppId = merchantAppId;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getMerchantPrivateKey() {
        return merchantPrivateKey;
    }

    public void setMerchantPrivateKey(String merchantPrivateKey) {
        this.merchantPrivateKey = merchantPrivateKey;
    }

    public String getTestPhoneNumber() {
        return testPhoneNumber;
    }

    public void setTestPhoneNumber(String testPhoneNumber) {
        this.testPhoneNumber = testPhoneNumber;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getTlsTrustStorePath() {
        return tlsTrustStorePath;
    }

    public void setTlsTrustStorePath(String tlsTrustStorePath) {
        this.tlsTrustStorePath = tlsTrustStorePath;
    }

    public String getTlsTrustStorePassword() {
        return tlsTrustStorePassword;
    }

    public void setTlsTrustStorePassword(String tlsTrustStorePassword) {
        this.tlsTrustStorePassword = tlsTrustStorePassword;
    }

    public String getTlsTrustStoreType() {
        return tlsTrustStoreType;
    }

    public void setTlsTrustStoreType(String tlsTrustStoreType) {
        this.tlsTrustStoreType = tlsTrustStoreType;
    }

    public boolean isMockEnabled() {
        return mockEnabled;
    }

    public void setMockEnabled(boolean mockEnabled) {
        this.mockEnabled = mockEnabled;
    }
}