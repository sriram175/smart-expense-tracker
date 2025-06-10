package com.expenseTracker.dto;

import com.expenseTracker.entity.User;
import com.expenseTracker.enums.Role;

public class UserDto {

	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserDto(User user) {
		
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.role = user.getRole();
	}
	private Long id;
	private String name;
	private String email;
	private Role role;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
}
