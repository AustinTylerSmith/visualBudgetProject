package com.example.visualbudget.controller;

import com.example.visualbudget.dao.CostDAO;
import com.example.visualbudget.model.Cost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
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

import java.util.InvalidPropertiesFormatException;
import java.util.List;

@RestController
@CrossOrigin
public class CostController {
    private final CostDAO costDAO;

    @Autowired
    public CostController(CostDAO costDAO) {
        this.costDAO = costDAO;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/createCost")
    public void createCost(@RequestBody Cost cost) throws InvalidPropertiesFormatException {
        if (!costDAO.createCost(cost)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Creating Cost Failed");
        }
    }

    @GetMapping("/fetchCost/{ID}")
    public ResponseEntity<Cost> getCostByID(@PathVariable("ID") int costID) {
        try {
            Cost cost = costDAO.getCost(costID);
            return new ResponseEntity<>(cost, HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException e) {
            System.out.println("ERROR fetching cost: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/fetchAllCosts/{ID}")
    public ResponseEntity<List<Cost>> getAllCostsForUser(@PathVariable("ID") int userID) {
        try {
            List<Cost> costList = costDAO.getAllCostsForUser(userID);
            return new ResponseEntity<>(costList, HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException e) {
            System.out.println("ERROR fetching costs for user; " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateCostInfo")
    public ResponseEntity<Void> updateCostInfo(@RequestBody Cost cost) {
        try {
            costDAO.updateCostInfo(cost);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            System.out.println("ERROR updating cost: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteCost/{ID}")
    public ResponseEntity<Void> deleteCost(@PathVariable("ID") int costID) {
        try {
            costDAO.deleteCost(costID);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            System.out.println("ERROR deleting cost: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
