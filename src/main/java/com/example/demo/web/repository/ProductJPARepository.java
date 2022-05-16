package com.example.demo.web.repository;

import com.example.demo.web.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJPARepository extends JpaRepository<ProductEntity, Long> {

    boolean existsByProductCode(Long productCode);
}
