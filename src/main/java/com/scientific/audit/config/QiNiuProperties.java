package com.scientific.audit.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("qiniu")
@Configuration
@Data
public class QiNiuProperties {
    private String accessKey;

    private String accessSecretKey;

    private String bucket;

    private String imageUrl;
}
