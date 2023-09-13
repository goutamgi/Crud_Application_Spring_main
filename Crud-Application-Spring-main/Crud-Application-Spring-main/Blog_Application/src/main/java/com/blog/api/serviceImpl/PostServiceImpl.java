package com.blog.api.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.api.entity.Category;
import com.blog.api.entity.PostEntity;
import com.blog.api.entity.User;
import com.blog.api.exception.ResourceNotFoundException;
import com.blog.api.payload.PostDTO;
import com.blog.api.payload.PostResponse;
import com.blog.api.repository.CategoryRepo;
import com.blog.api.repository.PostRepo;
import com.blog.api.repository.UserRepo;
import com.blog.api.service.PostService;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDTO createPost(PostDTO postDTO,Integer userId,Integer categoryId) {
		
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "User id", userId));
		
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "category id", categoryId));
		
		PostEntity postEntity=this.modelMapper.map(postDTO, PostEntity.class);
		postEntity.setImageName("defaut.png");
		postEntity.setAddedDate(new Date());
		
		postEntity.setUser(user);
		postEntity.setCategory(category);
		
		PostEntity newPostEntity=this.postRepo.save(postEntity);
		
		
		return this.modelMapper.map(newPostEntity, PostDTO.class);
	}

	@Override
	public PostDTO updatePost(PostDTO postDTO, Integer postId) {
		
		PostEntity postEntity=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("PostEntity", "postId", postId));
		postEntity.setTittle(postDTO.getTittle()); 
		postEntity.setContent(postDTO.getContent());
		postEntity.setImageName(postDTO.getImageName());
		
		PostEntity updatePostEntity=this.postRepo.save(postEntity);
		
		
		return this.modelMapper.map(updatePostEntity, PostDTO.class);
	}

	@Override
	public void deletePost(Integer postId) {
		PostEntity postEntity=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("PostEntity", "postId", postId));
		this.postRepo.delete(postEntity);
		
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		
		 Sort sort=(sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable p=PageRequest.of(pageNumber, pageSize, sort);
		Page<PostEntity>pagePost=this.postRepo.findAll(p);
		List<PostEntity>allposts=pagePost.getContent();
		List<PostDTO>postDTOs=allposts.stream().map((post)->this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postDTOs);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLasPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public PostDTO getPostById(Integer postId) {
		PostEntity postEntity=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("PostEntity", "post Id", postId));
		return this.modelMapper.map(postEntity, PostDTO.class);
	}

	@Override
	public List<PostDTO> getPostByCategory(Integer categoryId) {
		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "category id", categoryId));
		List<PostEntity>posts=this.postRepo.findByCategory(cat);
		List<PostDTO>postDTOs=posts.stream().map((post)->this.modelMapper.map(post,PostDTO.class))
		.collect(Collectors.toList());
		return postDTOs;
	}

	@Override
	public List<PostDTO> getPostByUser(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "user id", userId));
		List<PostEntity>posts=this.postRepo.findByUser(user);                      
		List<PostDTO>postDTOs=posts.stream().map((post)->this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
		return postDTOs;
	}

//	@Override
//	public List<PostDTO> searchPosts(String keyword) {
//		List<PostEntity> posts = this.postRepo.searchByTitle("%"+keyword+"%");
//		List<PostDTO> postDTOs = posts.stream().map((post)->this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
//		return null;
//	}

	
	
	

}
