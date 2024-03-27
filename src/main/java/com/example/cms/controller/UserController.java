package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.model.User;
import com.example.cms.requestdto.UserRequest;
import com.example.cms.responsedto.UserResponse;
import com.example.cms.service.UserService;
import com.example.cms.utility.ResponseStructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
public class UserController {
	private UserService userService;
	
	
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@Operation(description = "The endpoint is used to add a new user to the database",responses= {
			@ApiResponse(responseCode = "200",description = "User registered  successFully"),
			@ApiResponse(responseCode = "400",description = "Invalid inputs")
	})
	@PostMapping("/users/register")
	public ResponseEntity<ResponseStructure<UserResponse>> registerUser(@RequestBody @Valid UserRequest userRequest)
	{
		return userService.registerUser(userRequest);
	}
	
	@GetMapping("/test")
	public String test()
	{
		return "Hello from cms";
	}

}
