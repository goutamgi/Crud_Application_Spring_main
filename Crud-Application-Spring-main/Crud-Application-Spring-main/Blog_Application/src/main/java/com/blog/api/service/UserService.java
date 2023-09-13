package com.blog.api.service;

import java.util.List;

import com.blog.api.payload.UserDTO;

public interface UserService {
	
	
	UserDTO create(UserDTO userDTO);
	
	UserDTO update(UserDTO userDTO,Integer userId);
	
	UserDTO getUserById(Integer userId);
	
	List<UserDTO>getAllUsers();
	
	void deleteUser(Integer userId);

}
