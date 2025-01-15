package com.goods.product.task1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import jakarta.persistence.LockModeType;

public interface PriceRepository extends JpaRepository<Price, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Price p WHERE p.id = :id")
    Price findPriceForUpdate(@Param("id") Long id);

    // Пример для оптимистичного лока:
    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT p FROM Price p WHERE p.id = :id")
    Price findPriceWithOptimisticLock(@Param("id") Long id);
}
