package com.AutoTradingWithDocker.controller.trade;

import com.AutoTradingWithDocker.model.CommonResponse;
import com.AutoTradingWithDocker.model.TradeBody;
import com.AutoTradingWithDocker.service.TokenService;
import com.AutoTradingWithDocker.service.TradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/tradebot")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;
    private final TokenService tokenService;

    /**
     *  매수 가능 조회
     *
     */
    @PostMapping(value = "/buyCheck", produces = "application/json; charset=UTF8")
    public ResponseEntity<CommonResponse> buyCheck(@RequestBody TradeBody body) throws Exception {

        if (body.getAppkey().isEmpty() || body.getAppsecret().isEmpty() || body.getAccessToken().isEmpty()) {
            throw new Exception("Invalid token info.");
        }
        if (body.getAccountFront().isEmpty() || body.getAccountBack().isEmpty() || body.getCode().isEmpty()) {
            throw new Exception("Invalid account info.");
        }

        // 매수 가능 조회
        String result = tradeService.canBuyStock(body);


        return ResponseEntity.ok(CommonResponse.builder()
                .result("success")
                .data(result)
                .build());
    }

    /**
     * 매도 가능 조회
     *
     */
    @PostMapping(value = "/sellCheck", produces = "application/json; charset=UTF8")
    public ResponseEntity<CommonResponse> sellCheck(@RequestBody TradeBody body) throws Exception{

        if (body.getAppkey().isEmpty() || body.getAppsecret().isEmpty() || body.getAccessToken().isEmpty()) {
            throw new Exception("Invalid token info.");
        }
        if (body.getAccountFront().isEmpty() || body.getAccountBack().isEmpty() || body.getCode().isEmpty()) {
            throw new Exception("Invalid account info.");
        }

        String result = tradeService.canSellStock(body);

        return ResponseEntity.ok(CommonResponse.builder()
                .result("success")
                .data(result)
                .build());
    }


    /**
     * 매수하기
     */
    @PostMapping(value = "/buyStock/{amount}", produces = "application/json; charset=UTF8")
    public ResponseEntity<CommonResponse> buyStock(@PathVariable("amount") String amount,
                                                   @RequestBody TradeBody body) throws Exception {

        if (body.getAppkey().isEmpty() || body.getAppsecret().isEmpty() || body.getAccessToken().isEmpty()) {
            throw new Exception("Invalid token info.");
        }
        if (body.getAccountFront().isEmpty() || body.getAccountBack().isEmpty() || body.getCode().isEmpty()) {
            throw new Exception("Invalid account info.");
        }
        if (amount == null || amount.isEmpty()) {
            throw new Exception("Not exist amount.");
        }
        String result = tradeService.buyStock(body, amount);

        return ResponseEntity.ok(CommonResponse.builder()
                .result("success")
                .data(result)
                .build());
    }


    /**
     *
     * 매도하기
     */
    @GetMapping(value = "/sellStock", produces = "application/json; charset=UTF8")
    public ResponseEntity<CommonResponse> sellStock(@RequestParam("accountFront") String accountFront,
                                                   @RequestParam("accountBack") String accountBack,
                                                   @RequestParam("code")String code) {


        return ResponseEntity.ok(CommonResponse.builder()
                .result("success")
                .data(null)
                .build());
    }
}
