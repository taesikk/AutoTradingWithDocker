package com.AutoTradingWithDocker.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "kis")
public class TokenProp {
	private String grantType;
	private String appKey;
	private String appSecret;
}
