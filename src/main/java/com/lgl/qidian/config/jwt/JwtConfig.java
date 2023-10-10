package com.lgl.qidian.config.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @auther 刘广林
 */
@Configuration
@ConfigurationProperties("jwt")
public class JwtConfig {
    private String secret;

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getSecret() {
        return secret;
    }
}
