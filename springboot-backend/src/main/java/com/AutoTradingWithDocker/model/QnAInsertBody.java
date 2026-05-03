package com.AutoTradingWithDocker.model;

import lombok.Getter;

@Getter
public class QnAInsertBody {
    private int idx;
    private String title;
    private String content;
    private String creator;
    private String comment;
}
