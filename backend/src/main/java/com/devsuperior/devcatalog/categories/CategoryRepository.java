package com.devsuperior.devcatalog.categories;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import com.devsuperior.devcatalog.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepositoryImplementation<Category, Long>{

	
	
}
