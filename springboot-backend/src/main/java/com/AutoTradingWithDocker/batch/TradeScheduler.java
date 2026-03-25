package com.AutoTradingWithDocker.batch;

import com.AutoTradingWithDocker.model.TokenResult;
import com.AutoTradingWithDocker.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TradeScheduler {

	private final TokenService tokenService;

	// 매월 1일 오전 10시에 실행(S&P500 적금식 매수)
	@Scheduled(cron = "0 0 10 1 * *")
	public void autoTradeSP500() {
		log.info("[AutoTrade] ========== Start auto trade S&P500");

//		TokenResult token = tokenService.getAccessToken();
//
//		if (token.getAcccessToken() == null || token.getAcccessToken().equals("")) {
//			throw new RuntimeException("Not exist access token.");
//		}
	}
}
