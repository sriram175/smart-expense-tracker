package com.expenseTracker.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.expenseTracker.dto.AuthResponse;
import com.expenseTracker.dto.LoginRequest;
import com.expenseTracker.dto.RegisterRequest;
import com.expenseTracker.entity.User;
import com.expenseTracker.enums.Role;
import com.expenseTracker.repository.UserRepository;

@Service
public class AuthenticationService {

	private UserRepository userRepository;
	private JwtService jwtService;
	private PasswordEncoder passwordEncoder;
	private AuthenticationManager authenticationManager;

	public AuthenticationService(UserRepository userRepository, JwtService jwtService,
			PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
		this.userRepository = userRepository;
		this.jwtService = jwtService;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
	}

	public AuthResponse register(RegisterRequest registerRequest) {

		User user = new User();
		user.setName(registerRequest.getName());
		user.setEmail(registerRequest.getEmail());
		user.setRole(Role.USER);
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		userRepository.save(user);
		String token = jwtService.generateToken(registerRequest.getEmail());
		return new AuthResponse(token);
	}

	public AuthResponse login(LoginRequest loginRequest) {
		Authentication auth = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		String token = jwtService.generateToken(loginRequest.getEmail());
		return new AuthResponse(token);
	}
}
