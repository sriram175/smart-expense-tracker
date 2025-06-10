package com.expenseTracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expenseTracker.dto.UserDto;
import com.expenseTracker.entity.User;
import com.expenseTracker.repository.UserRepository;
import com.expenseTracker.service.CustomUserDetailsService;
import com.expenseTracker.service.JwtService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private CustomUserDetailsService customUserDetailsService;
	private UserRepository userRepository;
	private JwtService jwtService;

	public UserController(CustomUserDetailsService customUserDetailsService, UserRepository userRepository,
			JwtService jwtService) {
		this.customUserDetailsService = customUserDetailsService;
		this.userRepository = userRepository;
		this.jwtService = jwtService;
	}

	@GetMapping("/me")
	public ResponseEntity<UserDto> getCurrentUser(Authentication authentication){
		String email = authentication.getName();
		User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found"));
		return ResponseEntity.ok(new UserDto(user));
	}
}
