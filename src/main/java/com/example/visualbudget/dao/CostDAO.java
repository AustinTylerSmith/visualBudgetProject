package com.example.visualbudget.dao;

import com.example.visualbudget.model.Cost;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface CostDAO {
    //create
    boolean createCost(Cost cost);
    //get single cost
    Cost getCost(int costId) throws ChangeSetPersister.NotFoundException;
    //get list of costs associated with user
    List<Cost> getAllCostsForUser(int id) throws ChangeSetPersister.NotFoundException;
    //update
    void updateCostInfo(Cost cost);
    //delete
    void deleteCost(int id);
}
