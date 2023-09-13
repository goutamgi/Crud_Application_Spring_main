package com.blog.api.service;

import java.util.List;

import com.blog.api.payload.CategoryDTO;

public interface CategoryService {
	
	
	 CategoryDTO createCategoryDTO(CategoryDTO categoryDTO);
	 CategoryDTO updateCategoryDTO(CategoryDTO categoryDTO, Integer catagoryId);
	 void deleteCategoryDTO(Integer categoryId);
	 CategoryDTO getCategory(Integer categoryId);
	 List<CategoryDTO>getCategories(); 
	

}
