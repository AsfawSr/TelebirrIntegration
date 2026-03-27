package com.et.pwsdemo.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("prod")
public class TelebirrProdConfigValidator {

    private final PWSConfig pwsConfig;

    public TelebirrProdConfigValidator(PWSConfig pwsConfig) {
        this.pwsConfig = pwsConfig;
    }

    @PostConstruct
    public void validate() {
        List<String> missing = new ArrayList<>();
        require(missing, pwsConfig.getFabricAppId(), "TELEBIRR_FABRIC_APP_ID");
        require(missing, pwsConfig.getAppSecret(), "TELEBIRR_APP_SECRET");
        require(missing, pwsConfig.getMerchantAppId(), "TELEBIRR_MERCHANT_APP_ID");
        require(missing, pwsConfig.getMerchantCode(), "TELEBIRR_MERCHANT_CODE");
        require(missing, pwsConfig.getMerchantPrivateKey(), "TELEBIRR_MERCHANT_PRIVATE_KEY");
        require(missing, pwsConfig.getBaseUrl(), "TELEBIRR_BASE_URL");
        require(missing, pwsConfig.getNotifyUrl(), "TELEBIRR_NOTIFY_URL");

        if (!missing.isEmpty()) {
            throw new IllegalStateException(
                    "Missing required Telebirr production configuration. Set environment variable(s): "
                            + String.join(", ", missing)
            );
        }
    }

    private void require(List<String> missing, String value, String envVarName) {
        if (value == null || value.trim().isEmpty()) {
            missing.add(envVarName);
        }
    }
}

