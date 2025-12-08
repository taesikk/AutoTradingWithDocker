package com.AutoTradingWithDocker.service.impl;

import com.AutoTradingWithDocker.config.TokenProp;
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
    public void getAccessToken() {
		String totalUrl = domainUtil.baseUrl + domainUtil.getTokenUrl;
		String grantType = tokenProp.getGrantType();
		String appKey = tokenProp.getAppKey();
		String appSecret = tokenProp.getAppSecret();

		// json 데이터 생성
		JsonObject jsondata = new JsonObject();
		jsondata.addProperty("granttype", grantType);
		jsondata.addProperty("appkey", appKey);
		jsondata.addProperty("appscret", appSecret);


		try {
//			URL url = new URL(totalUrl);
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(totalUrl))
					.POST(HttpRequest.BodyPublishers.ofString(jsondata.toString()))
					.build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			log.info("[getAccessToken] ========== Response : {}", response.toString());

		} catch (Exception e) {
			log.info("Invalid json data.");
		}
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
