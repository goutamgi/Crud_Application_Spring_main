package com.blog.api.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.api.entity.Comment;
import com.blog.api.entity.PostEntity;
import com.blog.api.exception.ResourceNotFoundException;
import com.blog.api.payload.CommentDTO;
import com.blog.api.repository.CommentRepo;
import com.blog.api.repository.PostRepo;
import com.blog.api.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDTO createComment(CommentDTO commentDTO, Integer postId) {
		
		PostEntity postEntity=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("PostEntity", "post id", postId));
		
		Comment comment = this.modelMapper.map(commentDTO, Comment.class);
		
		comment.setPostEntity(postEntity);
		
		Comment savedComment = this.commentRepo.save(comment);
		
		return this.modelMapper.map(savedComment, CommentDTO.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		
		Comment com=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "commentId", commentId));
		
		this.commentRepo.delete(com);
		
	}

}
