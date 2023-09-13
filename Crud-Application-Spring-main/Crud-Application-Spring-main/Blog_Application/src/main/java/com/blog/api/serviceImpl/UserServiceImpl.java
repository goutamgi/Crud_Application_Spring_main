package com.blog.api.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.api.entity.User;
import com.blog.api.exception.ResourceNotFoundException;
import com.blog.api.payload.UserDTO;
import com.blog.api.repository.UserRepo;
import com.blog.api.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	
	

	@Override
	public UserDTO create(UserDTO userDTO) {
		User user=this.modelMapper.map(userDTO, User.class);
//		User user=this.dtoToUser(userDTO);
		User savedUser=this.userRepo.save(user);	
		return this.modelMapper.map(savedUser, UserDTO.class);
	}

	@Override
	public UserDTO update(UserDTO userDTO, Integer userId) {
		
	 User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		
		user.setName(userDTO.getName());
		user.setAbout(userDTO.getAbout());
		user.setPassword(userDTO.getPassword());
		user.setEmail(userDTO.getEmail());
	 
		User updatedUser=this.userRepo.save(user);
		UserDTO userDTO1= this.modelMapper.map(updatedUser, UserDTO.class);
		
		return userDTO1;
	}

	@Override
	public UserDTO getUserById(Integer userId) {
		 User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));

		return this.modelMapper.map(user, UserDTO.class);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User>users=this.userRepo.findAll();
		
		List<UserDTO>userDTOs=users.stream().map(user->this.modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
		return userDTOs;
	}

	@Override
	public void deleteUser(Integer userId) {
		 User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		this.userRepo.delete(user);
	}
	
//	private User dtoToUser(UserDTO userDTO) {
//		User user=this.modelMapper.map(userDTO, User.class);
//		
//		
//		
////		user.setId(userDTO.getId());
////		user.setName(userDTO.getName());
////		user.setAbout(userDTO.getAbout());
////		user.setPassword(userDTO.getPassword());
////		user.setEmail(userDTO.getEmail());
//		return user;
//	}
	
//	public UserDTO userToDto(User user) {
//		UserDTO userDTO=this.modelMapper.map(user, UserDTO.class);
//		
//		return userDTO;
//		
//	}
	
	

}
