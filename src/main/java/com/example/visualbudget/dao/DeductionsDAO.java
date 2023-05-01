package com.example.visualbudget.dao;

import com.example.visualbudget.model.Deduction;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface DeductionsDAO {
    //createDeduction
    boolean createDeduction(Deduction deduction);
    //getDeduction
    Deduction getDeduction(int deductID) throws ChangeSetPersister.NotFoundException;
    //getAllDeductionsForUser
    List<Deduction> getAllDeductionsForUser(int userID) throws ChangeSetPersister.NotFoundException;
    //updateDeduction
    void updateDeduction(Deduction deduction);
    //deleteDeduction
    void deleteDeduction(int deductID);
}
