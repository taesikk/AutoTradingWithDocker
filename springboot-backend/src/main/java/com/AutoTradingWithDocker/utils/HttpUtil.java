package com.AutoTradingWithDocker.utils;

import com.AutoTradingWithDocker.model.TokenResult;
import com.AutoTradingWithDocker.model.TradeBody;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@Component
public class HttpUtil {
	public static HttpResponse<String> requestGETHttp (String url, TradeBody tradeBody, String trId, String method) {
		try {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(url))
					.headers("content-type", "application/json; charset=utf-8",
							"authorization", "Bearer " + tradeBody.getAccessToken(),
							"appkey", tradeBody.getAppkey(),
							"appsecret", tradeBody.getAppsecret(),
							"tr_id", trId,
							"custtype", "P" // 개인
					)
					.GET()
					.build();

			log.info("[" + method + "] ========== Request : {}", request.toString());
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			log.info("[" + method + "] ========== Response : {}", response.toString());

			JsonObject responseJson = JsonParser.parseString(response.body()).getAsJsonObject();
			log.info("[" + method + "] ========== responseJson String : {}", responseJson.toString());

			return response;
		} catch (Exception e) {
			log.info("[" + method + "CatchException] Invalid json data. {}", e.getMessage());
		}
		return null;
	}

	public static HttpResponse<String> requestPOSTHttp (String url, TokenResult tokenResult, String appkey, String appsecret, String trId, String method, String body) {
		try {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(url))
					.headers("content-type", "application/json; charset=utf-8",
							"authorization", "Bearer " + tokenResult.getAccessToken(),
							"appkey", appkey,
							"appsecret", appsecret,
							"tr_id", trId
					)
					.POST(HttpRequest.BodyPublishers.ofString(body))
					.build();

			log.info("[" + method + "] ========== Request : {}", request.toString());
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			log.info("[" + method + "] ========== Response : {}", response.toString());

			JsonObject responseJson = JsonParser.parseString(response.body()).getAsJsonObject();
			log.info("[" + method + "] ========== responseJson String : {}", responseJson.toString());

			return response;
		} catch (Exception e) {
			log.info("[" + method + "CatchException] Invalid json data. {}", e.getMessage());
		}
		return null;
	}
}
