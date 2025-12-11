package com.AutoTradingWithDocker.utils;

import org.springframework.stereotype.Component;

@Component
public class DomainUtil {
	public final String baseUrl = "https://openapi.koreainvestment.com:9443";

	public final String getTokenUrl = "/oauth2/tokenP";
	public final String getAccountAmount = "/uapi/domestic-stock/v1/trading/inquire-balance";
}
