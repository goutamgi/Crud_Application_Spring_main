package com.blog.api.service;

import com.blog.api.payload.CommentDTO;

public interface CommentService {
	
	CommentDTO createComment(CommentDTO commentDTO,Integer postId);
	
	void deleteComment(Integer commentId);

}
