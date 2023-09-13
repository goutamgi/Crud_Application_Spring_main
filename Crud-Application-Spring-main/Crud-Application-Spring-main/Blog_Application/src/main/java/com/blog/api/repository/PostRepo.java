package com.blog.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blog.api.entity.Category;
import com.blog.api.entity.PostEntity;
import com.blog.api.entity.User;

public interface PostRepo extends JpaRepository<PostEntity, Integer> {
	
	List<PostEntity>findByUser(User user);
	List<PostEntity>findByCategory(Category category);
//	@Query("select p from post p where p.title lik :key")
//	List<PostEntity>searchByTitle(@Param("key")String title);

}
