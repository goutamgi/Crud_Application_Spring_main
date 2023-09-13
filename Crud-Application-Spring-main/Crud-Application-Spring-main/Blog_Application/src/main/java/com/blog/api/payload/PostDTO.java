package com.blog.api.payload;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.blog.api.entity.Comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class PostDTO {
	
	private Integer postId;
	
	private String tittle;
	
	private String content;
	

	private String imageName;
	
	private Date addedDate;
	
	private CategoryDTO category;
	
	
	private UserDTO user;
	
	private Set<CommentDTO> comments=new HashSet<>();
	
	
	

}
