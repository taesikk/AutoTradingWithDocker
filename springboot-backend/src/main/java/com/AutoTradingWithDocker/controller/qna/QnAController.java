package com.AutoTradingWithDocker.controller.qna;

import com.AutoTradingWithDocker.entity.QnA;
import com.AutoTradingWithDocker.model.CommonResponse;
import com.AutoTradingWithDocker.model.QnAInsertBody;
import com.AutoTradingWithDocker.model.QnASearchBody;
import com.AutoTradingWithDocker.service.QnAService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/qna")
@RequiredArgsConstructor
public class QnAController {
    private final QnAService qnAService;

    /**
     *  qna 게시판 목록 조회
     */
    @GetMapping(value = "/list", produces = "application/json; charset=UTF8")
    public ResponseEntity<CommonResponse> getQnAList(@RequestBody QnASearchBody body) throws Exception {
        Page<QnA> result = qnAService.getQnAList(body);

//        page.getTotalElements();  // 전체 데이터 개수
//        page.getTotalPages();     // 전체 페이지 수
//        page.getNumber();         // 현재 페이지 (0부터 시작)
//        page.getSize();           // 페이지 크기
//        page.isFirst();
//        page.isLast();

        return ResponseEntity.ok(CommonResponse.builder()
                .result("success")
                .data(result.getContent())
                .build());

    }

    /**
     * qna 게시글 조회
     */
    @GetMapping(value = "/content/{idx}", produces = "application/json; charset=UTF8")
    public ResponseEntity<CommonResponse> getQnA(@PathVariable(name = "idx") int idx) {
        QnA result = qnAService.getQnA(idx);

        return ResponseEntity.ok(CommonResponse.builder()
                .result("success")
                .data(result)
                .build());
    }
    /**
     * qna 게시글 작성
     */
    @PostMapping(value = "/write", produces = "application/json; charset=UTF8")
    public ResponseEntity<CommonResponse> insertQna(@RequestBody QnAInsertBody body) {
        qnAService.insertQnA(body);

        return ResponseEntity.ok(CommonResponse.builder()
                .result("success")
                .data(null)
                .build());
    }

    /**
     *  qna 게시글 댓글 등록
     */
    @PostMapping(value = "/comment", produces = "application/json; charset=UTF8")
    public ResponseEntity<CommonResponse> insertComment(@RequestBody QnAInsertBody body) throws Exception {
        qnAService.insertComment(body);

        return ResponseEntity.ok(CommonResponse.builder()
                .result("success")
                .data(null)
                .build());
    }
}
