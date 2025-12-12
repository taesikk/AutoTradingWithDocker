package com.AutoTradingWithDocker.service;

import com.AutoTradingWithDocker.model.TokenResult;

public interface AccountService {
	public void getAccountAmount(TokenResult tokenResult);
	public void getTradeAmount(TokenResult tokenResult);
}
