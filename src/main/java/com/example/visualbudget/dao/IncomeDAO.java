package com.example.visualbudget.dao;

import com.example.visualbudget.model.Deduction;
import com.example.visualbudget.model.Income;

import java.util.List;

public interface IncomeDAO {
    //create income
    void createIncome(Income income);
    // get income
    Deduction getIncome(int incomeID);
    // get all income streams for user
    List<Income> getAllIncomeStreamsForUser(int userID);
    // update income
    void updateIncome(Income income);
    // delete income
    void deleteIncome(int incomeID);
}
