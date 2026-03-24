package com.AutoTradingWithDocker.model;

import lombok.Getter;

@Getter
public class TradeBody {
	private String accessToken;
	private String appkey;
	private String appsecret;
	private String accountFront;
	private String accountBack;
	private String code;
}
