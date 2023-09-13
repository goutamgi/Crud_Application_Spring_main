package com.blog.api.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
	
	
	private Integer userId;
	@NotEmpty
	@Size(min=4,message="User must must be min of 4 characters !!")
	private String name;
	@Email(message="Email address is not valid !!")
	private String email;
	@NotEmpty
	@Size(min=3,max=10,message="Password must be minimum 3 characters and maximum 10 characters !!")
	private String password;
	@NotEmpty
	private String about;

}
