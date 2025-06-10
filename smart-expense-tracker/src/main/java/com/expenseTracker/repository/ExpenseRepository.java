package com.expenseTracker.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.expenseTracker.dto.CategoryExpenseDto;
import com.expenseTracker.entity.Expense;
import com.expenseTracker.entity.User;

public interface ExpenseRepository extends JpaRepository<Expense, Long>{

	List<Expense> findByUser(User user);

	List<Expense> findByUserAndCategory(User user, String food);
	
	List<Expense> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
	
	@Query("SELECT SUM(e.amount) FROM Expense e where e.user = :user AND MONTH(e.date) = :month AND YEAR(e.date) = :year")
	Double getTotalSpentInMonth(@Param("user") User user, @Param("month") int month,@Param("year") int year);
	
	@Query("SELECT new com.expenseTracker.dto.CategoryExpenseDto(e.category,SUM(e.amount)) FROM Expense e where e.user = :user GROUP BY e.category")
	List<CategoryExpenseDto> getCategoryExpenseDto(@Param("user") User user);
	
	Page<Expense> findByUser(User user,Pageable pageable);

}
