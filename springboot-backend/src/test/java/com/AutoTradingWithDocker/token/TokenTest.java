package com.AutoTradingWithDocker.token;

import com.AutoTradingWithDocker.config.TokenProp;
import com.AutoTradingWithDocker.model.TokenResult;
import com.AutoTradingWithDocker.utils.DomainUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootTest
@Slf4j
public class TokenTest {
	@Autowired
	private DomainUtil domainUtil;
	@Autowired
	private TokenProp tokenProp;

	@Test
	public TokenResult getAccessToken() {
		String totalUrl = domainUtil.baseUrl + domainUtil.getTokenUrl;
		String grantType = tokenProp.getGrantType();
		String appKey = tokenProp.getAppKey();
		String appSecret = tokenProp.getAppSecret();

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
}
