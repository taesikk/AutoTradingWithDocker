package com.AutoTradingWithDocker.service.impl;

import com.AutoTradingWithDocker.config.TokenProp;
import com.AutoTradingWithDocker.model.TokenResult;
import com.AutoTradingWithDocker.service.TradeService;
import com.AutoTradingWithDocker.utils.DomainUtil;
import com.AutoTradingWithDocker.utils.HttpUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.el.parser.Token;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {
	private final DomainUtil domainUtil;
	private final HttpUtil httpUtil;
	private final TokenProp tokenProp;

	private String grantType;
	private String appKey;
	private String appSecret;
	private void setDefaultKey() {
		this.grantType = tokenProp.getGrantType();
		this.appSecret = tokenProp.getAppSecret();
		this.appKey= tokenProp.getAppKey();
	}

	/**
	 * 매수 가능 조회
	 *
	 * 종목의 현재 매수 가능량 조회
	 */
	@Override
	public void canBuyStock(TokenResult tokenResult) {
		this.setDefaultKey();
		String queryString = "";
		String totalUrl = domainUtil.baseUrl + domainUtil.canBuyStock + queryString;

		String CANO = "";
		String ACNT_PRDT_CD = "01";
		String PDNO = "";
		String ORD_UNPR = "";
		String ORD_DVSN = "01";
		String CMA_EVLU_AMT_ICLD_YN = "N";
		String OVRS_ICLD_YN = "N";

		HttpResponse<String> response = httpUtil.requestGETHttp(totalUrl, tokenResult, this.appKey, this.appSecret, "TTTC8908R", "canBuyStock");
//		try {
//			HttpClient client = HttpClient.newHttpClient();
//			HttpRequest request = HttpRequest.newBuilder()
//					.uri(URI.create(totalUrl))
//					.headers("content-type", "application/json; charset=utf-8",
//							"authorization", "Bearer " + tokenResult.getAcccessToken(),
//							"appkey", this.appKey,
//							"appsecret", this.appSecret,
//							"tr_id", "TTTC8908R"
//					)
//					.GET()
//					.build();
//
//			log.info("[getTradeAmount] ========== Request : {}", request.toString());
//			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//			log.info("[getTradeAmount] ========== Response : {}", response.toString());
//
//			JsonObject responseJson = JsonParser.parseString(response.body()).getAsJsonObject();
//			log.info("[getTradeAmount] ========== responseJson String : {}", responseJson.toString());
//		} catch (Exception e) {
//			log.info("[getTradeAmount CatchException] Invalid json data. {}", e.getMessage());
//		}
	}

	/**
	 * 매도 가능 조회
	 *
	 * 종목의 현재 매도 가능량 조회
 	 */
	@Override
	public void canSellStock(TokenResult tokenResult) {
		this.setDefaultKey();
		String CANO = ""; // 종합계좌번호
		String ACNT_PRDT_CD = ""; // 계좌상품코드
		String PDNO = ""; // 보유종목 코드

		String queryString = "?CANO=" + CANO + "&ACNT_PRDT_CD=" + ACNT_PRDT_CD + "&PDNO" + PDNO;
		String totalUrl = domainUtil.baseUrl + domainUtil.canSellStock + queryString;

		HttpResponse<String> response = httpUtil.requestGETHttp(totalUrl, tokenResult, this.appKey, this.appSecret, "TTTC8408R", "canSellStock");

//		try {
//			HttpClient client = HttpClient.newHttpClient();
//			HttpRequest request = HttpRequest.newBuilder()
//					.uri(URI.create(totalUrl))
//					.headers("content-type", "application/json; charset=utf-8",
//							"authorization", "Bearer " + tokenResult.getAcccessToken(),
//							"appkey", this.appKey,
//							"appsecret", this.appSecret,
//							"tr_id", "TTTC8408R"
//					)
//					.GET()
//					.build();
//
//			log.info("[canSellStock] ========== Request : {}", request.toString());
//			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//			log.info("[canSellStock] ========== Response : {}", response.toString());
//
//			JsonObject responseJson = JsonParser.parseString(response.body()).getAsJsonObject();
//			log.info("[canSellStock] ========== responseJson String : {}", responseJson.toString());
//		} catch (Exception e) {
//			log.info("[canSellStock CatchException] Invalid json data. {}", e.getMessage());
//		}

	}

	/**
	 * 주식주문(현금)
	 *
	 * 종목 거래
	 */
	@Override
	public void buyStock(TokenResult tokenResult) {
		String CANO = ""; // 종합계좌번호
		String ACNT_PRDT_CD = ""; // 계좌상품코드
		String PDNO = ""; // 보유종목 코드
		String ORD_DVSN = "01"; // 주문 구분 - 시장가로 고정
		String ORD_QTY = ""; // 주문수량
		String ORD_UNPR = ""; //주문단가 - 시장가 등 주문시, "0"으로 입력

		JsonObject body = new JsonObject();
		body.addProperty("CANO", CANO);
		body.addProperty("ACNT_PRDT_CD", ACNT_PRDT_CD);
		body.addProperty("PDNO", PDNO);
		body.addProperty("ORD_DVSN", ORD_DVSN);
		body.addProperty("ORD_QTY", ORD_QTY);
		body.addProperty("ORD_UNPR", ORD_UNPR);


		String queryString = "?CANO=" + CANO + "&ACNT_PRDT_CD=" + ACNT_PRDT_CD + "&PDNO=" + PDNO + "&ORD_DVSN=" + ORD_DVSN + "&ORD_QTY=" + ORD_QTY + "&ORD_UNPR=" + ORD_UNPR;
		String totalUrl = domainUtil.baseUrl + domainUtil.buyStock + queryString;

		HttpResponse<String> response = httpUtil.requestPOSTHttp(totalUrl, tokenResult, this.appKey, this.appSecret, "TTTC0011U", "buyStock", body.toString());
	}
}
