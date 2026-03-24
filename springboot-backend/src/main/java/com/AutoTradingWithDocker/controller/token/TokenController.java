package com.AutoTradingWithDocker.controller.token;

import com.AutoTradingWithDocker.model.CommonResponse;
import com.AutoTradingWithDocker.model.TokenBody;
import com.AutoTradingWithDocker.model.TokenResult;
import com.AutoTradingWithDocker.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/tradebot")
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;

    /**
     * access token 발급
     *
     * */
    @PostMapping(value = "/token", produces = "application/json; charset=UTF8", consumes = "application/json")
    public ResponseEntity<CommonResponse> getToken(@RequestBody TokenBody body) throws Exception {
        if (body.getAppkey().isEmpty() || body.getGrantType().isEmpty() || body.getAppsecret().isEmpty()) {
            throw new Exception("Invalid json format !!! ");
        }

        TokenResult tokenResult = tokenService.getAccessToken(body.getGrantType(), body.getAppkey(), body.getAppsecret());

        return ResponseEntity.ok(CommonResponse.builder()
                .result("success")
                .data(tokenResult)
                .build());
    }
}
