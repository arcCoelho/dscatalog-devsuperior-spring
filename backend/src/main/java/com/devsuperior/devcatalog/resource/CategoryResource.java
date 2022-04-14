package com.devsuperior.devcatalog.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.devcatalog.entities.Category;

@RestController
@RequestMapping(value="categories")
public class CategoryResource {

	@GetMapping
	public ResponseEntity< List<Category> > findAll(){
		List<Category> lista = new ArrayList<>();
		lista.add(new Category(1L, "comida"));
		lista.add(new Category(2L, "games"));
		lista.add(new Category(3L, "bebidas"));
		
		return ResponseEntity.ok().body(lista);
	}
	
}
