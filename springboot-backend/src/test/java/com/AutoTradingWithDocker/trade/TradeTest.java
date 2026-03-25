package com.AutoTradingWithDocker.trade;

import com.AutoTradingWithDocker.config.TokenProp;
import com.AutoTradingWithDocker.model.TokenResult;
import com.AutoTradingWithDocker.service.TokenService;
import com.AutoTradingWithDocker.utils.DomainUtil;
import com.AutoTradingWithDocker.utils.HttpUtil;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.http.HttpResponse;

@Slf4j
@SpringBootTest
public class TradeTest {

	@Autowired
	private DomainUtil domainUtil;
	@Autowired
	private TokenProp tokenProp;
	@Autowired
	private TokenService tokenService;
	@Test
	public void canBuyStockTest() {
		String grantType = tokenProp.getGrantType();
		String appKey = tokenProp.getAppKey();
		String appSecret = tokenProp.getAppSecret();
		String url = domainUtil.baseUrl + domainUtil.canBuyStock;

		TokenResult tokenResult = null;//tokenService.getAccessToken(grantType, appKey, appSecret);
		log.info("[canBuyStockTest] ==== getAccessToken complete");

		String CANO = "";
		String ACNT_PRDT_CD = "01";
		String PDNO = "";
		String ORD_UNPR = "";
		String ORD_DVSN = "01";
		String CMA_EVLU_AMT_ICLD_YN = "N";
		String OVRS_ICLD_YN = "N";

		String queryString = "?CANO=" + CANO + "&ACNT_PRDT_CD=" + ACNT_PRDT_CD + "&PDNO=" + PDNO + "&ORD_UNPR=" + ORD_UNPR + "&ORD_DVSN=" + ORD_DVSN + "&CMA_EVLU_AMT_ICLD_YN=" + CMA_EVLU_AMT_ICLD_YN + "&OVRS_ICLD_YN=" + OVRS_ICLD_YN;

		//HttpResponse<String> response = HttpUtil.requestGETHttp(url + queryString, tokenResult, appKey, appSecret, "TTTC8908R", "canBuyStockTest");
		log.info("[canBuyStockTest] ==== canBuyStockTest complete");
		//log.info(response.body());
		// ord_psbl_cash - 예수금
		// nrcvb_buy_qty - 주문 가능량

	}

	@Test
	public void buyStockTest() {
		String grantType = tokenProp.getGrantType();
		String appKey = tokenProp.getAppKey();
		String appSecret = tokenProp.getAppSecret();
		String url = domainUtil.baseUrl + domainUtil.canBuyStock;

		TokenResult tokenResult = null; //tokenService.getAccessToken(grantType, appKey, appSecret);
		log.info("[buyStockTest] ==== getAccessToken complete");

		String CANO = ""; // 종합계좌번호
		String ACNT_PRDT_CD = "01"; // 계좌상품코드
		String PDNO = ""; // 보유종목 코드
		String ORD_DVSN = "01"; // 주문 구분 - 시장가로 고정
		String ORD_QTY = "1"; // 주문수량
		String ORD_UNPR = "0"; //주문단가 - 시장가 등 주문시, "0"으로 입력

		JsonObject body = new JsonObject();
		body.addProperty("CANO", CANO);
		body.addProperty("ACNT_PRDT_CD", ACNT_PRDT_CD);
		body.addProperty("PDNO", PDNO);
		body.addProperty("ORD_DVSN", ORD_DVSN);
		body.addProperty("ORD_QTY", ORD_QTY);
		body.addProperty("ORD_UNPR", ORD_UNPR);

		String queryString = "?CANO=" + CANO + "&ACNT_PRDT_CD=" + ACNT_PRDT_CD + "&PDNO=" + PDNO + "&ORD_DVSN=" + ORD_DVSN + "&ORD_QTY=" + ORD_QTY + "&ORD_UNPR=" + ORD_UNPR;

		//HttpResponse<String> response = HttpUtil.requestPOSTHttp(url, tokenResult, appKey, appSecret, "TTTC0011U", "buyStockTest", body.toString());
		log.info("[BuyStockTest] ==== BuyStockTest complete");
		//log.info(response.body());
	}
}
