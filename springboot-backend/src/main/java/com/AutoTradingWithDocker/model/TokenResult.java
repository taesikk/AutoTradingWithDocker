package com.AutoTradingWithDocker.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TokenResult {
	private String acccessToken;
	private String expiredSec;
	private String expiredDate;
}
