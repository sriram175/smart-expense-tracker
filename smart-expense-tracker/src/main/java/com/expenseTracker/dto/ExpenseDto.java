package com.expenseTracker.dto;

import java.time.LocalDate;

public class ExpenseDto {

	private String Category;
	private String note;
	private double amount;
	private LocalDate localDate;

	public String getCategory() {
		return Category;
	}

	public ExpenseDto() {
	}

	public ExpenseDto(String category, String note, double amount, LocalDate localDate) {
		this.Category = category;
		this.note = note;
		this.amount = amount;
		this.localDate = localDate;
	}

	public void setCategory(String category) {
		Category = category;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDate getLocalDate() {
		return localDate;
	}

	public void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
	}

}
