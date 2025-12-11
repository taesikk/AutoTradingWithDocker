package com.AutoTradingWithDocker.service.impl;

import com.AutoTradingWithDocker.config.TokenProp;
import com.AutoTradingWithDocker.model.TokenResult;
import com.AutoTradingWithDocker.service.TokenService;
import com.AutoTradingWithDocker.utils.DomainUtil;
import com.google.gson.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final DomainUtil domainUtil;
	private final TokenProp tokenProp;

	private String grantType;
	private String appKey;
	private String appSecret;
	@Override
    public TokenResult getAccessToken() {
		this.grantType = tokenProp.getGrantType();
		this.appSecret = tokenProp.getAppSecret();
		this.appKey= tokenProp.getAppKey();
		String totalUrl = domainUtil.baseUrl + domainUtil.getTokenUrl;
		// TODO 키값 RDB 전환 고려

		// json 데이터 생성
		JsonObject jsondata = new JsonObject();
		jsondata.addProperty("grant_type", grantType);
		jsondata.addProperty("appkey", appKey);
		jsondata.addProperty("appsecret", appSecret);


		JsonObject responseJson = new JsonObject();
		try {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(totalUrl))
					.POST(HttpRequest.BodyPublishers.ofString(jsondata.toString()))
					.build();

			log.info("[getAccessToken] ========== Request : {}", request.toString());
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			log.info("[getAccessToken] ========== Response : {}", response.toString());

			if (response.statusCode() != 200) {
				throw new RuntimeException("API info is not correct. " + response.body());
			}

			responseJson = JsonParser.parseString(response.body()).getAsJsonObject();
			log.info("[getAccessToken] ========== accessToken : {}", responseJson.get("access_token"));
			log.info("[getAccessToken] ========== expires_in : {}", responseJson.get("expires_in"));
			log.info("[getAccessToken] ========== access_token_token_expired : {}", responseJson.get("access_token_token_expired"));



		} catch (Exception e) {
			log.info("[getAccessToken CatchException] Invalid json data. {}", e.getMessage());
		}

		return TokenResult.builder()
				.acccessToken(responseJson.get("access_token").getAsString())
				.expiredSec(responseJson.get("expires_in").getAsString())
				.expiredDate(responseJson.get("access_token_token_expired").getAsString())
				.build();
	}

	@Override
	public void getAccountAmount(TokenResult tokenResult){
		this.grantType = tokenProp.getGrantType();
		this.appSecret = tokenProp.getAppSecret();
		this.appKey= tokenProp.getAppKey();
		String queryString = this.makeQueryString();
		String totalUrl = domainUtil.baseUrl + domainUtil.getAccountAmount + queryString;

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
			log.info("[accountAmountTest CatchException] Invalid json data. {}", e.getMessage());
		}
	}

	private String makeQueryString() {
		String CANO = ""; // 종합계좌번호  계좌번호 체계(8-2)의 앞 8자리
		String ACNT_PRDT_CD = "01"; // 계좌상품코드  계좌번호 체계(8-2)의 뒤 2자리
		String AFHR_FLPR_YN = "N"; // 시간외단일가, 거래소여부
		// N : 기본값,
		// Y : 시간외단일가,
		// X : NXT 정규장 (프리마켓, 메인, 애프터마켓)
		// ※ NXT 선택 시 : NXT 거래종목만 시세 등 정보가 NXT 기준으로 변동됩니다. KRX 종목들은 그대로 유지
		String INQR_DVSN = "01"; // 조회구분 01 : 대출일별, 02 : 종목별
		String UNPR_DVSN = "01"; // 단가구분 01 기본값
		String FUND_STTL_ICLD_YN = "N"; // 펀드결제분포함여부 N : 포함하지 않음, Y : 포함
		String FNCG_AMT_AUTO_RDPT_YN = "N"; // 융자금액자동상환여부 N 기본값
		String PRCS_DVSN = "00"; // 처리구분  00 : 전일매매포함, 01 : 전일매매미포함

		String queryString = "?CANO=" + CANO + "&ACNT_PRDT_CD=" + ACNT_PRDT_CD + "&AFHR_FLPR_YN=" + AFHR_FLPR_YN + "&INQR_DVSN=" + INQR_DVSN + "&UNPR_DVSN=" + UNPR_DVSN + "&FUND_STTL_ICLD_YN=" + FUND_STTL_ICLD_YN
				+ "&FNCG_AMT_AUTO_RDPT_YN=" + FNCG_AMT_AUTO_RDPT_YN + "&PRCS_DVSN=" + PRCS_DVSN
				+ "&OFL_YN=&CTX_AREA_FK100=&CTX_AREA_NK100=";

		return queryString;
	}
	public static StringBuilder print(HttpURLConnection conn) throws IOException {
		StringBuilder response = null;

		if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
				response = new StringBuilder();
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
					response.append("\n");
				}
				System.out.println("응답 코드 : " + String.valueOf(conn.getResponseCode()));
				System.out.println("응답 데이터 : " + response.toString());

			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			String result = new BufferedReader(new InputStreamReader(conn.getErrorStream()))
					.lines().collect(Collectors.joining("\n"));
			System.out.println("error : " + result);
		}

		return response;
	}
}
