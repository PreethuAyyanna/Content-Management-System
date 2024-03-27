package com.example.cms.serviceImpl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cms.exception.UserAlreadyExistByEmailException;
import com.example.cms.model.User;
import com.example.cms.repository.UserRepository;
import com.example.cms.requestdto.UserRequest;
import com.example.cms.responsedto.UserResponse;
import com.example.cms.service.UserService;
import com.example.cms.utility.ResponseStructure;

@Service
public class UserServiceImpl implements UserService {
	private UserRepository userRepo;
	private ResponseStructure<UserResponse> responseStructure;
	private PasswordEncoder passwordEncoder;
	
	
	public UserServiceImpl(UserRepository userRepo, ResponseStructure<UserResponse> responseStructure,PasswordEncoder passwordEncoder) {
		super();
		this.userRepo = userRepo;
		this.responseStructure = responseStructure;
		this.passwordEncoder = passwordEncoder;
	}


	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> registerUser(UserRequest userRequest) {
		if(userRepo.existsByEmail(userRequest.getEmail()))
			throw new UserAlreadyExistByEmailException("Failed to register user");
		User uniqueUser=userRepo.save(mapToUserEntity(userRequest,new User()));
		return ResponseEntity.ok(responseStructure.setStatus(HttpStatus.OK.value())
				.setMessage("User registered successfully")
				.setData(mapToUserResponse(uniqueUser)));
		
	}


	private UserResponse mapToUserResponse(User user) {
		UserResponse userResponse=new UserResponse();
		userResponse.setUserId(user.getUserId());
		userResponse.setUserName(user.getUsername());
		userResponse.setEmail(user.getEmail());
		userResponse.setCreatedAt(user.getCreateAt());
		userResponse.setLastModifiedAt(user.getLastModifiedAt());
		return userResponse;
	}


	private User mapToUserEntity(UserRequest userRequest, User user) {
		user.setUsername(userRequest.getUserName());
		user.setEmail(userRequest.getEmail());
		user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		return user;
		}

}
