package com.muglog.repository;

import com.muglog.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StoreRepository extends JpaRepository<Store, Long> {
    Store findByStoreNmAndKatechXAndKatechY(String storeNm, Integer katechX, Integer katechY);
}
