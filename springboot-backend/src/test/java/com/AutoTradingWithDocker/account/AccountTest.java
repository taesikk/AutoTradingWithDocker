package com.AutoTradingWithDocker.account;

import com.AutoTradingWithDocker.config.TokenProp;
import com.AutoTradingWithDocker.model.TokenResult;
import com.AutoTradingWithDocker.service.TokenService;
import com.AutoTradingWithDocker.utils.DomainUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootTest
@Slf4j
public class AccountTest {
	@Autowired
	private DomainUtil domainUtil;
	@Autowired
	private TokenProp tokenProp;
	@Autowired
	private TokenService tokenService;
	private String grantType;
	private String appKey;
	private String appSecret;
	@Test
	public void accountAmountTest() {
		this.grantType = tokenProp.getGrantType();
		this.appSecret = tokenProp.getAppSecret();
		this.appKey= tokenProp.getAppKey();
		String CANO = ""; // 종합계좌번호  계좌번호 체계(8-2)의 앞 8자리
		String ACNT_PRDT_CD = "01"; // 계좌상품코드  계좌번호 체계(8-2)의 뒤 2자리
		String AFHR_FLPR_YN = "N"; // 시간외단일가, 거래소여부
		// N : 기본값,
		// Y : 시간외단일가,
		// X : NXT 정규장 (프리마켓, 메인, 애프터마켓)
		// ※ NXT 선택 시 : NXT 거래종목만 시세 등 정보가 NXT 기준으로 변동됩니다. KRX 종목들은 그대로 유지
		String INQR_DVSN = "02"; // 조회구분 01 : 대출일별, 02 : 종목별
		String UNPR_DVSN = "01"; // 단가구분 01 기본값
		String FUND_STTL_ICLD_YN = "N"; // 펀드결제분포함여부 N : 포함하지 않음, Y : 포함
		String FNCG_AMT_AUTO_RDPT_YN = "N"; // 융자금액자동상환여부 N 기본값
		String PRCS_DVSN = "00"; // 처리구분  00 : 전일매매포함, 01 : 전일매매미포함

		String queryString = "?CANO=" + CANO + "&ACNT_PRDT_CD=" + ACNT_PRDT_CD + "&AFHR_FLPR_YN=" + AFHR_FLPR_YN + "&INQR_DVSN=" + INQR_DVSN + "&UNPR_DVSN=" + UNPR_DVSN + "&FUND_STTL_ICLD_YN=" + FUND_STTL_ICLD_YN
				+ "&FNCG_AMT_AUTO_RDPT_YN=" + FNCG_AMT_AUTO_RDPT_YN + "&PRCS_DVSN=" + PRCS_DVSN
				+ "&OFL_YN=&CTX_AREA_FK100=&CTX_AREA_NK100=";

		String totalUrl = domainUtil.baseUrl + domainUtil.getAccountAmount + queryString;

		TokenResult tokenResult = tokenService.getAccessToken();
		try {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(totalUrl))
					.headers("content-type", "application/json; charset=utf-8",
							"authorization", "Bearer " + tokenResult.getAcccessToken(),
							"appkey", this.appKey,
							"appsecret", this.appSecret,
							"tr_id", "TTTC8434R"
					)
					.GET()
					.build();

			log.info("[accountAmountTest] ========== Request : {}", request.toString());
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			log.info("[accountAmountTest] ========== Response : {}", response.toString());

			JsonObject responseJson = JsonParser.parseString(response.body()).getAsJsonObject();
			log.info("[accountAmountTest] ========== responseJson String : {}", responseJson.toString());
		} catch (Exception e) {

		}
	}
}
