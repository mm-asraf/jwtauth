package com.springrestapi.springrestapi.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class UserResponse {
	
	private long id;
	private String firstname;
	private String lastname;
	private String email;
	private String mobile;
	private String username;
	
	
}
