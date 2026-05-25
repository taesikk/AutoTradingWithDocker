package com.AutoTradingWithDocker.service.impl;

import com.AutoTradingWithDocker.entity.QnA;
import com.AutoTradingWithDocker.model.QnAInsertBody;
import com.AutoTradingWithDocker.model.QnASearchBody;
import com.AutoTradingWithDocker.repository.QnARepository;
import com.AutoTradingWithDocker.service.QnAService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class QnAServiceImpl implements QnAService {

    private final QnARepository qnARepository;

    @Override
    public Page<QnA> getQnAList(QnASearchBody body) {
        int page = body.getSkip() / body.getLimit();
        int size = body.getLimit();

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, body.getSort())
        );

        return qnARepository.getQnASearch(body.getKeyword(), body.getStartDate(), body.getEndDate(), pageable);
    }

    @Override
    public QnA getQnA(int idx) {
        return qnARepository.findById(idx);
    }

    @Override
    public void insertQnA(QnAInsertBody body) {
        long now = System.currentTimeMillis();
        QnA qna = QnA.builder()
                .title(body.getTitle())
                .content(body.getContent())
                .comment("")
                .creator(body.getCreator())
                .createDate(now)
                .updateDate(now).build();
        qnARepository.save(qna);
    }

    @Transactional
    public void insertComment(QnAInsertBody body) throws Exception {
        QnA qna = qnARepository.findById(body.getIdx());

        if (qna == null) {
            throw new Exception("Not exist post.");
        }

        qna.setComment(body.getComment());
        qnARepository.save(qna);
    }
}
