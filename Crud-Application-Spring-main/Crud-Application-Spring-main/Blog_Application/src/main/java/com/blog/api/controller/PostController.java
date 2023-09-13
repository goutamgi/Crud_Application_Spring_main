package com.blog.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.api.config.AppConstants;
import com.blog.api.payload.ApiResponse;
import com.blog.api.payload.PostDTO;
import com.blog.api.payload.PostResponse;
import com.blog.api.service.FileService;
import com.blog.api.service.PostService;

@RestController
@RequestMapping("/api")
public class PostController {
	
	
	@Autowired
	private PostService postService;
	
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDTO>createPost(@RequestBody PostDTO postDTO,
			@PathVariable ("userId") Integer userId,@PathVariable ("categoryId")Integer categoryId)
	{
		
		PostDTO createPost=this.postService.createPost(postDTO, userId, categoryId);
		return new ResponseEntity<PostDTO>(createPost,HttpStatus.CREATED);
		
	}
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDTO>> getPostByUser(@PathVariable ("userId")Integer userId){
		
		List<PostDTO>posts=this.postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDTO>>(posts,HttpStatus.OK);
		
	}
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDTO>> getPostByCategory(@PathVariable ("categoryId")Integer categoryId){
		
		List<PostDTO>posts=this.postService.getPostByCategory(categoryId);
		return new ResponseEntity<List<PostDTO>>(posts,HttpStatus.OK);
		
	}
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse>getAllPost(
			@RequestParam(value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER,required=false)Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue=AppConstants.PAGE_SIZE,required=false)Integer pageSize,
			@RequestParam(value="sortBy",defaultValue=AppConstants.SORT_BY,required=false)String sortBy,
			@RequestParam(value="sortDir",defaultValue=AppConstants.SORT_DIR,required=false)String sortDir
			){
		
		
		PostResponse postResponse = this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDTO>getPostById(@PathVariable ("postId")Integer postId){
		
		PostDTO postDTO=this.postService.getPostById(postId);
		return new ResponseEntity<PostDTO>(postDTO,HttpStatus.OK);
				
		
	}
	
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable ("postId")Integer postId){
		
		this.postService.deletePost(postId);
		return new ApiResponse("Post is deleted Successfully !!",true);
		
	}
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDTO>updatePost(@RequestBody PostDTO postDTO, @PathVariable ("postId")Integer postId){
		
		PostDTO upadatePost=this.postService.updatePost(postDTO, postId);
		return new ResponseEntity<PostDTO>(upadatePost,HttpStatus.OK);
				
	}
	
	
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDTO>uploadPostImage(
			@RequestParam ("image") MultipartFile image,
			@PathVariable ("postId")Integer postId
			)throws IOException{
		
		PostDTO postDTO=this.postService.getPostById(postId);
		String fileName=this.fileService.uploadImage(path, image);
		postDTO.setImageName(fileName);
		PostDTO updatePost=this.postService.updatePost(postDTO, postId);
		return new ResponseEntity<PostDTO>(updatePost, HttpStatus.OK);
		
		
	}
	
	@GetMapping(value="/Post/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName")String imageName,
			HttpServletResponse response
			)throws IOException{
		
		InputStream resource=this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
	
	
	
//	@GetMapping("posts/search/{keyword}")
//	@Query("select p from Post p where lower(p.title) like concat('%', :keyword,'%')")
//	public ResponseEntity<List<PostDTO>>searchPostByTittle(@Param("keyword") String keyword){
//		
//		List<PostDTO> result = this.postService.searchPosts(keyword);
//	
//		return new ResponseEntity<List<PostDTO>>(result,HttpStatus.OK);
//	}
	
	
	
	
	
	
	
	
	
	

}
