package com.expenseTracker.dto;

import java.time.LocalDate;

import com.expenseTracker.entity.Expense;

public class ExpenseResponseDto {

	public ExpenseResponseDto() {
	}
	public ExpenseResponseDto(Expense expense) {
		this.id = expense.getId();
		this.category = expense.getCategory();
		this.amount = expense.getAmount();
		this.note = expense.getNote();
		this.date = expense.getDate();
		this.email = expense.getUser().getEmail();
	}
	private Long id;
	private String category;
	private double amount;
	private String note;
	private LocalDate date;
	private String email;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
