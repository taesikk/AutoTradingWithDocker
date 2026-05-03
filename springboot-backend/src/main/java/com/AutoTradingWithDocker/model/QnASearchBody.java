package com.AutoTradingWithDocker.model;

import lombok.Getter;

@Getter
public class QnASearchBody {
    private String keyword;
    private String sort;
    private int skip;
    private int limit;
    private long startDate;
    private long endDate;
}
