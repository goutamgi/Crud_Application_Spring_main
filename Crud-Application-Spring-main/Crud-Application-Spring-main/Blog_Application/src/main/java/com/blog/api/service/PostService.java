package com.blog.api.service;

import java.util.List;

import com.blog.api.payload.PostDTO;
import com.blog.api.payload.PostResponse;

public interface PostService {
	
	PostDTO createPost(PostDTO postDTO,Integer userId,Integer categoryId);
	
	PostDTO updatePost(PostDTO postDTO,Integer postId);
	
	void deletePost(Integer postId);
	
	PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	PostDTO getPostById(Integer postId);
	
	List<PostDTO>getPostByCategory(Integer categoryId);
	
	List<PostDTO>getPostByUser(Integer userId);
	
//	List<PostDTO>searchPosts(String keyword);
	
	
	

}
