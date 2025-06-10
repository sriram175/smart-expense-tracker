package com.expenseTracker.dto;

public class CategoryExpenseDto {

	public CategoryExpenseDto() {
	}
	public CategoryExpenseDto(String category, double amount) {
		this.category = category;
		this.amount = amount;
	}
	private String category;
	private double amount;
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
	
}
