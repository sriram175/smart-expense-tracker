package com.expenseTracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expenseTracker.dto.AuthResponse;
import com.expenseTracker.dto.LoginRequest;
import com.expenseTracker.dto.RegisterRequest;
import com.expenseTracker.service.AuthenticationService;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private AuthenticationService authenticationService;
	
	public AuthController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest){
		AuthResponse authResponse =  authenticationService.register(registerRequest);
		return ResponseEntity.ok(authResponse);
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
		AuthResponse authResponse = authenticationService.login(loginRequest);
		return ResponseEntity.ok(authResponse);
	}
	
}
