package com.muglog.repository;

import com.muglog.entity.MenuReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuReviewRepository extends JpaRepository<MenuReview, Long> {

    public List<MenuReview> findAllByMuglogSeqOrderByThread(Long muglogSeq);

}
