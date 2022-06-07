package com.devsuperior.devcatalog.repositories;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import com.devsuperior.devcatalog.entities.Product;

public interface ProductRepository extends JpaRepositoryImplementation<Product, Long> {

}
