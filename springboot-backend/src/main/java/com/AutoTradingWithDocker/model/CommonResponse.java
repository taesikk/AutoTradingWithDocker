package com.AutoTradingWithDocker.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommonResponse {
    private String result;
    private String message;
    private Object data;
}
