package com.AutoTradingWithDocker.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "qna")
public class QnA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // idx
    @Column
    private String title; // 제목
    @Column
    private String content; // 내용
    @Column
    private String comment; // 댓글
    @Column
    private String creator; // 작성자
    @Column
    private long createDate;  // 작성일
    @Column
    private long updateDate;  // 수정
}
