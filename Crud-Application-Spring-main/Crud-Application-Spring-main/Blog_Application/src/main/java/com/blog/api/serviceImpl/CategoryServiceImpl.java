package com.blog.api.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.api.entity.Category;
import com.blog.api.exception.ResourceNotFoundException;
import com.blog.api.payload.CategoryDTO;
import com.blog.api.repository.CategoryRepo;
import com.blog.api.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	

	@Override
	public CategoryDTO createCategoryDTO(CategoryDTO categoryDTO) {
		Category cat=this.modelMapper.map(categoryDTO,Category.class);
		Category addedCat=this.categoryRepo.save(cat);
		return this.modelMapper.map(addedCat, CategoryDTO.class);
	}

	@Override
	public CategoryDTO updateCategoryDTO(CategoryDTO categoryDTO, Integer catagoryId) {
		Category cat=this.categoryRepo.findById(catagoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",catagoryId));
		
		cat.setCategoryTitle(categoryDTO.getCategoryTitle());
		cat.setCategoryDescription(categoryDTO.getCategoryDescription());
		Category updatedcat=this.categoryRepo.save(cat);
		
		return this.modelMapper.map(updatedcat, CategoryDTO.class);
	}

	@Override
	public void deleteCategoryDTO(Integer categoryId) {
		
		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		this.categoryRepo.delete(cat);
	}

	@Override
	public CategoryDTO getCategory(Integer categoryId) {
		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		return this.modelMapper.map(cat, CategoryDTO.class);
	}

	@Override
	public List<CategoryDTO> getCategories() {
		List<Category>categories=this.categoryRepo.findAll();
		
		List<CategoryDTO>categoryDTOs=categories.stream().map((cat)->this.modelMapper.map(cat, CategoryDTO.class)).collect(Collectors.toList());
		
		return categoryDTOs;
	}

}
