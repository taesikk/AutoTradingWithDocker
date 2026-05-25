package com.AutoTradingWithDocker.repository;

import com.AutoTradingWithDocker.entity.QnA;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface QnARepository extends JpaRepository<QnA, Long>{
    QnA findById(int id);

    @Query("""
    SELECT q FROM QnA q
    WHERE (q.title LIKE %:keyword%)
      AND (:startDate = 0 OR q.createDate >= :startDate)
      AND (:endDate = 0 OR q.createDate <= :endDate)
""")
    Page<QnA> getQnASearch(
            @Param("keyword") String keyword,
            @Param("startDate") long startDate,
            @Param("endDate") long endDate,
            Pageable pageable
    );
}
