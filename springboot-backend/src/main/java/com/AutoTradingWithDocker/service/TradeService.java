package com.AutoTradingWithDocker.service;

import com.AutoTradingWithDocker.model.TokenResult;
import com.AutoTradingWithDocker.model.TradeBody;

public interface TradeService {

	// 매수 가능 조회
	public String canBuyStock(TradeBody tradeBody) throws Exception;

	// 매도 가능 조회
	public String canSellStock(TradeBody tradeBody) throws Exception;

	// 주식주문(현금)
	public String buyStock(TradeBody tradeBody, String amount) throws Exception;
}
