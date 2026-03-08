package com.AutoTradingWithDocker.controller.trade;

import com.AutoTradingWithDocker.model.CommonResponse;
import com.AutoTradingWithDocker.service.TradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/tradebot")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    /**
     *  매수 가능 조회
     *
     */
    @GetMapping(value = "/buyCheck", produces = "application/json; charset=UTF8")
    public ResponseEntity<CommonResponse> buyCheck(@RequestParam("accountFront") String accountFront,
                                                   @RequestParam("accountBack") String accountBack,
                                                   @RequestParam("code")String code) {


        return ResponseEntity.ok(CommonResponse.builder()
                .result("success")
                .data(null)
                .build());
    }
}
