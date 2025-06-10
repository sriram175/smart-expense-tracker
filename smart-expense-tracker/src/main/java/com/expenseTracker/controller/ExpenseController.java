package com.expenseTracker.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.expenseTracker.dto.CategoryExpenseDto;
import com.expenseTracker.dto.ExpenseDto;
import com.expenseTracker.dto.ExpenseResponseDto;
import com.expenseTracker.entity.Expense;
import com.expenseTracker.entity.User;
import com.expenseTracker.repository.ExpenseRepository;
import com.expenseTracker.repository.UserRepository;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

	public ExpenseController(UserRepository userRepository,ExpenseRepository expenseRepository) {
		this.userRepository = userRepository;
		this.expenseRepository = expenseRepository;
	}
	private UserRepository userRepository;
	private ExpenseRepository expenseRepository;
	@PostMapping
	public ResponseEntity<String> addExpense(@RequestBody ExpenseDto expenseDto,Authentication authentication){
		String email = authentication.getName();
		User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		Expense expense =  new Expense();
		expense.setCategory(expenseDto.getCategory());
		expense.setAmount(expenseDto.getAmount());
		expense.setDate(LocalDate.now());
		expense.setNote(expenseDto.getNote());
		expense.setUser(user);
		System.out.println("ExpenseDTO category: "+expenseDto.getCategory());

		System.out.println("Expense category: "+expense.getCategory());
		expenseRepository.save(expense);
		return ResponseEntity.ok("Expenses has been added");
	}
	
	@GetMapping
	public ResponseEntity<List<ExpenseResponseDto>> getUserExpenses(Authentication authentication){
		
		String email = authentication.getName();
		User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		List<Expense> expenses = expenseRepository.findByUser(user);
		List<ExpenseResponseDto> expensesDto = expenses.stream().map(e->{
			ExpenseResponseDto expenseResponseDto = new ExpenseResponseDto(e);
			return expenseResponseDto;
		}).collect(Collectors.toList());
		return ResponseEntity.ok(expensesDto);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateExpense(@PathVariable Long id , @RequestBody ExpenseDto expenseDto, Authentication authentication){
		String email = authentication.getName();
		User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		Expense expense = expenseRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Invalid Expense id"));
		if(!expense.getUser().getId().equals(user.getId())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
		}
		expense.setCategory(expenseDto.getCategory());
		expense.setAmount(expenseDto.getAmount());
		expense.setDate(expenseDto.getLocalDate());
		expense.setNote(expenseDto.getNote());
		expenseRepository.save(expense);
		return ResponseEntity.ok("Expense has been updated");
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteExpense(@PathVariable Long id,Authentication authentication){
		String email = authentication.getName();
		User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		Expense expense = expenseRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Invalid Expense id"));
		if(!user.getId().equals(expense.getUser().getId())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
		}
		expenseRepository.deleteById(id);
		return ResponseEntity.ok("Expense has been deleted");
	}
	@GetMapping("/category")
	public ResponseEntity<List<ExpenseResponseDto>> getExpenseByFood(@RequestParam String category , Authentication authentication){
		String email = authentication.getName();
		User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		List<Expense> expenses = expenseRepository.findByUserAndCategory(user,category);
		List<ExpenseResponseDto> expensesDto = expenses.stream().map(e->{
			ExpenseResponseDto expenseResponseDto = new ExpenseResponseDto(e);
			return expenseResponseDto;
		}).collect(Collectors.toList());
		return ResponseEntity.ok(expensesDto);	
	}
	@GetMapping("/date")
	public ResponseEntity<List<ExpenseResponseDto>> getByDateInterval(@RequestParam LocalDate start, LocalDate end,Authentication authentication){
		String email = authentication.getName();
		User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		List<Expense> expenses = expenseRepository.findByUserAndDateBetween(user, start, end);
		List<ExpenseResponseDto> expsenseDto = expenses.stream().map(e->{
			ExpenseResponseDto expenseResponseDto = new ExpenseResponseDto(e);
			return expenseResponseDto;
		}).collect(Collectors.toList());
		return ResponseEntity.ok(expsenseDto);
	}
	
	@GetMapping("/analytics")
	public ResponseEntity<Map<String, Object>> getAmountSpentInMonth(Authentication authentication){
		String email = authentication.getName();
		User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		LocalDate date = LocalDate.now();
		int month = date.getMonthValue();
		int year = date.getYear();
		Double totalAmount = expenseRepository.getTotalSpentInMonth(user, month, year);
		if(totalAmount == null) {
			totalAmount = 0.0;
		}
		Map<String,Object> map= new HashMap<>();
		map.put("Month", month);
		map.put("Year", year);
		map.put("Amount",totalAmount);
		return ResponseEntity.ok(map);
	}
	
	@GetMapping("/analytics/category-wise")
	public ResponseEntity<List<CategoryExpenseDto>> getCategoryWiseExpenses(Authentication authentication){
		String email = authentication.getName();
		User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		List<CategoryExpenseDto> expenses = expenseRepository.getCategoryExpenseDto(user);
		return ResponseEntity.ok(expenses);
	}
	
	@GetMapping("/analytics/page")
	public Page<ExpenseResponseDto> getExpensesByPage(@RequestParam(defaultValue = "0") int page, @RequestParam (defaultValue = "5") int size, Authentication authentication){
		String email = authentication.getName();
		User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		
		Pageable pageable = PageRequest.of(page, size,Sort.by("date").descending());
		Page<Expense> expensePage = expenseRepository.findByUser(user, pageable);
		Page<ExpenseResponseDto> expensePageDto = expensePage.map(e ->{
			ExpenseResponseDto expenseResponseDto = new ExpenseResponseDto(e);
			return expenseResponseDto;
		});
		return expensePageDto;
	}
}
