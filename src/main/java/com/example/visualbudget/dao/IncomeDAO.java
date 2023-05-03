package com.example.visualbudget.dao;

import com.example.visualbudget.model.Deduction;
import com.example.visualbudget.model.Income;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface IncomeDAO {
    //create income
    boolean createIncome(Income income);
    // get income
    Income getIncome(int incomeID) throws ChangeSetPersister.NotFoundException;
    // get all income streams for user
    List<Income> getAllIncomeStreamsForUser(int userID) throws ChangeSetPersister.NotFoundException;
    // update income
    void updateIncome(Income income);
    // delete income
    void deleteIncome(int incomeID);
}
