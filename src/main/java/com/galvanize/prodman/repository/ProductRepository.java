package com.galvanize.prodman.repository;

import com.galvanize.prodman.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;


public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Modifying
    @Transactional
    @Query("update Product p set p.views = p.views + :count where p.id = :id")
    void incrementViewsByCount(@Param(value = "id") Integer id, @Param(value = "count") Integer count);
}
