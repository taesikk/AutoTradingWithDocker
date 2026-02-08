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

	@Override
    public TokenResult getAccessToken(String grantType, String appKey, String appSecret) {
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
				.accessToken(responseJson.get("access_token").getAsString())
				.expiredSec(responseJson.get("expires_in").getAsString())
				.expiredDate(responseJson.get("access_token_token_expired").getAsString())
				.build();
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
