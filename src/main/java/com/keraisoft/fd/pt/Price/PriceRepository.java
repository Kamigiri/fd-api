package com.keraisoft.fd.pt.Price;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PriceRepository extends JpaRepository<Price , Long> {


    @Query(value = "SELECT p.* from price p where products_id = ?1", nativeQuery = true)
    List<Price> findByProductId(Long productId);
}
