package com.AutoTradingWithDocker.service;

import com.AutoTradingWithDocker.entity.QnA;
import com.AutoTradingWithDocker.model.QnAInsertBody;
import com.AutoTradingWithDocker.model.QnASearchBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

public interface QnAService {
    Page<QnA> getQnAList(QnASearchBody body);
    QnA getQnA(int idx);
    void insertQnA(QnAInsertBody body);
    void insertComment(QnAInsertBody body) throws Exception;
}
