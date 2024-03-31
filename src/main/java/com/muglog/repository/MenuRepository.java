package com.muglog.repository;

import com.muglog.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    Optional<Menu> findTopByMenuNmContainingIgnoreCase(String menuNm);
}
