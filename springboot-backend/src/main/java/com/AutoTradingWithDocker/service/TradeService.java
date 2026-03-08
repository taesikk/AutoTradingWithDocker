package com.AutoTradingWithDocker.service;

import com.AutoTradingWithDocker.model.TokenResult;

public interface TradeService {

	// 매수 가능 조회
	public void canBuyStock(TokenResult tokenResult, String accountF, String accountB, String code);

	// 매도 가능 조회
	public void canSellStock(TokenResult tokenResult, String accountF, String accountB, String code);

	// 주식주문(현금)
	public void buyStock(TokenResult tokenResult);
}
