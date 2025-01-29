package com.expensetracker.PersonalExpenceTrackerAPI.jpa;

import com.expensetracker.PersonalExpenceTrackerAPI.DAO.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense,Integer> {
}
