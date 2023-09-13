package com.blog.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.payload.ApiResponse;
import com.blog.api.payload.UserDTO;
import com.blog.api.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<UserDTO>createUser(@Valid @RequestBody UserDTO userDTO){
		
		UserDTO createUserDTO=this.userService.create(userDTO);
		return new ResponseEntity<>(createUserDTO,HttpStatus.CREATED);
		
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDTO>updateUser(@RequestBody UserDTO userDTO, @PathVariable ("userId")Integer uid){
		
		UserDTO updateUser=this.userService.update(userDTO, uid);
		return ResponseEntity.ok(updateUser);
		
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse>deletEntity(@PathVariable ("userId")Integer uid){
		
		this.userService.deleteUser(uid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully",true),HttpStatus.OK);
		
	}
	@GetMapping("/")
	public ResponseEntity<List<UserDTO>>getAllUsers(){
		
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
	
	@GetMapping("/{userId}")
     public ResponseEntity<UserDTO> getSingleUsers(@PathVariable ("userId")Integer userId){
		
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}
	

}
