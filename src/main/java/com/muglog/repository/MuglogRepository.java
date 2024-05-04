package com.muglog.repository;

import com.muglog.entity.Muglog;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MuglogRepository extends JpaRepository<Muglog, Long> {
    @EntityGraph(attributePaths = {"store", "writer"})
    List<Muglog> findAllByOrderByRegDateDesc();
}
