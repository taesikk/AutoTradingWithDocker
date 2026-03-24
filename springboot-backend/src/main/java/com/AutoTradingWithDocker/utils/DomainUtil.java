package com.AutoTradingWithDocker.utils;

import org.springframework.stereotype.Component;

@Component
public class DomainUtil {
	public final String baseUrl = "https://openapi.koreainvestment.com:9443";

	public final String getTokenUrl = "/oauth2/tokenP";
	public final String getAccountAmount = "/uapi/domestic-stock/v1/trading/inquire-balance";
	public final String getTradeAmount = "/uapi/domestic-stock/v1/trading/inquire-psbl-order";
	public final String canBuyStock = "/uapi/domestic-stock/v1/trading/inquire-psbl-order";
	public final String canSellStock = "/uapi/domestic-stock/v1/trading/inquire-psbl-sell";
	public final String buyStock = "/uapi/domestic-stock/v1/trading/order-cash";
}
