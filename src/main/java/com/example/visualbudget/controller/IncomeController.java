package com.example.visualbudget.controller;

import com.example.visualbudget.dao.IncomeDAO;
import com.example.visualbudget.model.Income;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200") // replace with actual client url
public class IncomeController {

    private IncomeDAO incomeDAO;

    @Autowired
    public IncomeController(IncomeDAO incomeDAO) {
        this.incomeDAO = incomeDAO;
    }

    @PostMapping("/createIncome")
    public ResponseEntity<Void> createIncome(@RequestBody Income income) {
        try {
            boolean created = incomeDAO.createIncome(income);
            if (created) {
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DataAccessException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/income/{incomeID}")
    public ResponseEntity<Income> getIncome(@PathVariable("incomeID") int incomeID) {
        try {
            Income income = incomeDAO.getIncome(incomeID);
            return new ResponseEntity<>(income, HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/income/user/{userID}")
    public ResponseEntity<List<Income>> getAllIncomeStreamsForUser(@PathVariable("userID") int userID) {
        try {
            List<Income> incomeList = incomeDAO.getAllIncomeStreamsForUser(userID);
            return new ResponseEntity<>(incomeList, HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateIncome")
    public ResponseEntity<Void> updateIncome(@RequestBody Income income) {
        try {
            incomeDAO.updateIncome(income);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/income/{incomeID}")
    public ResponseEntity<Void> deleteIncome(@PathVariable("incomeID") int incomeID) {
        try {
            incomeDAO.deleteIncome(incomeID);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

