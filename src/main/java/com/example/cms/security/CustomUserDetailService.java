package com.example.cms.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.cms.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{
	private UserRepository userRepo;

	public CustomUserDetailService(UserRepository userRepo) {
		super();
		this.userRepo = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepo.findByEmail(username).map(user-> new CustomUserDetails(user))
				.orElseThrow(()-> new UsernameNotFoundException("Username does not exists"));
	}

}
