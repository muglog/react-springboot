package com.muglog.repository;

import com.muglog.entity.Muglog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MuglogRepository extends JpaRepository<Muglog, Long> {
}
