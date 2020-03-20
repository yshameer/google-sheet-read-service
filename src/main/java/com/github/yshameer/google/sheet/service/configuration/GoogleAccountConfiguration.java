package com.github.yshameer.google.sheet.service.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties(prefix = "google.account.secret")
@Getter
@Setter
@ToString
@Validated
public class GoogleAccountConfiguration {

    private String type;

    private String projectId;

    private String privateKeyId;

    private String privateKey;

    private String clientEmail;

    private String clientId;

    private String authUri;

    private String tokenUri;

    private String authProviderX509CertUrl;

    private String clientX509CertUrl;
}
