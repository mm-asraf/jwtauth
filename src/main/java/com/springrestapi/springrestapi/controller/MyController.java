package com.springrestapi.springrestapi.controller;


import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


import com.springrestapi.springrestapi.entities.Users;
import com.springrestapi.springrestapi.exception.UserNotFoundException;
import com.springrestapi.springrestapi.helper.JwtUtil;
import com.springrestapi.springrestapi.payload.ApiResponse;
import com.springrestapi.springrestapi.payload.UserResponse;
//import com.springrestapi.springrestapi.exceptions.UserNotFoundException;
import com.springrestapi.springrestapi.services.UserService;

@RestController
public class MyController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	//get the users
	@GetMapping("/user")
	public List<Users> getUsers() {
		return this.userService.getUsers();
	}
	
//	@GetMapping("/users/{userId}")
//	public Users getUsers( @PathVariable String userId) {
//		
//		
//		
//		Users u = this.userService.getUser(Long.parseLong(userId));
//		System.out.println(u);
//		return u;
//	}
	
	
//	@GetMapping("/user/{userId}")
//	public  ResponseEntity<Users>  getUsers( @PathVariable String userId) {
//		
//		try {
//			Users u = this.userService.getUser(Long.parseLong(userId));
//			System.out.println(u);
//			return new ResponseEntity<Users>(u,HttpStatus.ACCEPTED);
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//		
//
//	}
	
	@GetMapping("/userDetail")
	public ResponseEntity<UserResponse> getSingleUser(@RequestHeader String Authorization) {
		
		String token = Authorization.substring(7);
		String userName = jwtUtil.getUsernameFromToken(token);
		Users resp = userService.getUser(userName).get();
		
		return new ResponseEntity<UserResponse>(new UserResponse(resp.getId(),resp.getFirstname(),resp.getLastname(),resp.getEmail(),resp.getMobile(),resp.getUsername()),HttpStatus.ACCEPTED);
	}

	
	@PostMapping("/register")
	public  ResponseEntity<ApiResponse> addUser(@Valid @RequestBody Users user) throws Exception,UserNotFoundException{
		    
		try {
			Users savedUser = userService.addUser(user);
			
			
			return new ResponseEntity<ApiResponse>(new ApiResponse("Signup successfully",true),HttpStatus.CREATED);
		} catch (UserNotFoundException e) {
		
			throw new UserNotFoundException("user already exists with these fields");
		}
	    
	    
	}
	
	@PostMapping("/authenticateUser")
	public Users authenticateUser(@RequestBody Users user) throws UserNotFoundException {
		
		try {
			return userService.authenticateUser(user);
			
		} catch (UserNotFoundException e) {
			throw new UserNotFoundException("username or password is not matched");
		}
		
	}
	
	
	
	@PutMapping("/user")
	public Users updateUser(@Valid @RequestBody Users user) {
		return this.userService.updateUser(user);
	}
	
	
	@DeleteMapping("/user/{userId}")
	public  ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId) {
		
		try {
			this.userService.deleteUser(Long.parseLong(userId));
			return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfully",true),HttpStatus.OK);
			
		}catch(UserNotFoundException e) {
			throw new UserNotFoundException(e.getMessage());
		}
	}

}
