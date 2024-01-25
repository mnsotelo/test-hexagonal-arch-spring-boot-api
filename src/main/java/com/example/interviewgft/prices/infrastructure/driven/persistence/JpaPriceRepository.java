package com.example.interviewgft.prices.infrastructure.driven.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface JpaPriceRepository extends JpaRepository<PriceEntity, Long> {

    @Query("""
            SELECT p FROM PriceEntity p WHERE p.startDate <= :date AND p.endDate >= :date
            AND p.productId = :productId
            AND p.brandId = :brandId""")
    List<PriceEntity> findByProductIdAndBrandIdAndDate(@Param("productId") int productId,
                                                       @Param("brandId") Integer brandId,
                                                       @Param("date") LocalDateTime date);
}