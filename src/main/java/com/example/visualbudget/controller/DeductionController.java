package com.example.visualbudget.controller;

import com.example.visualbudget.dao.DeductionsDAO;
import com.example.visualbudget.model.Deduction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin
public class DeductionController {

    private final DeductionsDAO deductionsDAO;

    @Autowired
    public DeductionController(DeductionsDAO deductionsDAO) {
        this.deductionsDAO = deductionsDAO;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/createDeduction")
    public void createDeduction(@RequestBody Deduction deduction) {
        if(!deductionsDAO.createDeduction(deduction)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Creating Deduction Failed");
        }
    }

    @GetMapping("/myDeduction/{ID}")
    public ResponseEntity<Deduction> getDeductionByID(@PathVariable("ID") int deductionID) {
        try {
            Deduction deduction = deductionsDAO.getDeduction(deductionID);
            return new ResponseEntity<>(deduction, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("ERROR retrieving deduction " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/allDeductions/{ID}")
    public ResponseEntity<List<Deduction>> getAllDeductionsForUser(@PathVariable("ID") int userID) {
        try {
            List<Deduction> deductionList = deductionsDAO.getAllDeductionsForUser(userID);
            return new ResponseEntity<>(deductionList, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("ERROR retrieving deductions for user: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateDeduction")
    public ResponseEntity<Void> updateDeductionInfo(@RequestBody Deduction deduction) {
        try {
            deductionsDAO.updateDeduction(deduction);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error updating deduction: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteDeduction/{ID}")
    public ResponseEntity<Void> deleteDeduction(@PathVariable("ID") int deductionID) {
        try {
            deductionsDAO.deleteDeduction(deductionID);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("ERROR Deleting Deduction: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
