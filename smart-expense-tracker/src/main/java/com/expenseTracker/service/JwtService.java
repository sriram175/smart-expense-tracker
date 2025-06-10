package com.expenseTracker.service;

import java.security.Key;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Service
public class JwtService {

	private String SECRET_KEY="fdkjnajvawpifaf;anskjvnaewkdsvjsnpoda bsafnm bsifcnkbvpoajc kjsncinkjsncdl";
	
	private Key key;
	
	@PostConstruct
	public void init() {
		key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	}
	
	public String generateToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10))
				.signWith(key,SignatureAlgorithm.HS256)
				.compact();
				
	}
	
	public String extractUserName(String token) {
		return Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes()).build().parseClaimsJws(token).getBody().getSubject();
	}
	
	public boolean validateToken(UserDetails userDetails, String token) {
		return userDetails.getUsername().equals(extractUserName(token)) && !isTokenExpired(token);
	}
	
	public boolean isTokenExpired(String token) {
		Date expiration = Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes()).build().parseClaimsJws(token).getBody().getExpiration();
		return expiration.before(new Date());
	}
	
}
