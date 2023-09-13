package com.blog.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.entity.Category;
import com.blog.api.payload.ApiResponse;
import com.blog.api.payload.CategoryDTO;
import com.blog.api.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	
	@PostMapping("/")
	public ResponseEntity<CategoryDTO>createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
		
		CategoryDTO createCategory=this.categoryService.createCategoryDTO(categoryDTO);
		return new ResponseEntity<CategoryDTO>(createCategory,HttpStatus.CREATED);
	}
	
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO>updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable ("categoryId")Integer categoryId){
		CategoryDTO updateCategory=this.categoryService.updateCategoryDTO(categoryDTO, categoryId);
		return new ResponseEntity<CategoryDTO>(updateCategory,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse>deleteCategory(@PathVariable ("categoryId")Integer categoryId){
		
		this.categoryService.deleteCategoryDTO(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category is deleted SuccessFul !!",true),HttpStatus.OK);
	}
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO>getCategory(@PathVariable ("categoryId")Integer categoryId){
		
		CategoryDTO getCategory=this.categoryService.getCategory(categoryId);
		
		return new ResponseEntity<CategoryDTO>(getCategory, HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDTO>>getCategories(){
		
		List<CategoryDTO>getCategories=this.categoryService.getCategories();
		return ResponseEntity.ok(getCategories);
	}
	
	
	
	
	
	

}
