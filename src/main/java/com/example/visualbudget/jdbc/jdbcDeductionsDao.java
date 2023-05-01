package com.example.visualbudget.jdbc;

import com.example.visualbudget.dao.DeductionsDAO;
import com.example.visualbudget.model.Cost;
import com.example.visualbudget.model.Deduction;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.time.DayOfWeek.valueOf;

@Component
public class jdbcDeductionsDao implements DeductionsDAO {
    JdbcTemplate jdbcTemplate;

    public jdbcDeductionsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean createDeduction(Deduction deduction) {
        String sql = "INSERT INTO deductions (amount, receiving_account_id, sending_account_id, user_id) VALUES (?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, deduction.getAmount(), deduction.getReceivingAccountID(), deduction.getSendingAccountID(), deduction.getUserID());
        } catch (Exception e) {
            System.out.println("Error creating deduction: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Deduction getDeduction(int deductID) throws ChangeSetPersister.NotFoundException {
        String sql = "SELECT * FROM deductions WHERE deduct_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, deductID);
        if(result.next()) {
            return mapRowToDeduction(result);
        }
        throw new ChangeSetPersister.NotFoundException();
    }

    @Override
    public List<Deduction> getAllDeductionsForUser(int userID) throws ChangeSetPersister.NotFoundException {
        String sql = "SELECT * FROM deductions WHERE user_id = ?";
        List<Deduction> deductions = new ArrayList<>();
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userID);
        while (result.next()) {
            Deduction deduction = mapRowToDeduction(result);
            deductions.add(deduction);
        }
        if(deductions.size() < 1) {
            throw new ChangeSetPersister.NotFoundException();
        }
        return deductions;
    }

    @Override
    public void updateDeduction(Deduction deduction) {
        String sql = "UPDATE deductions SET amount = ?, receiving_account_id = ?, sending_account_id = ? WHERE deduct_id = ?";
        jdbcTemplate.update(sql, deduction.getAmount(), deduction.getReceivingAccountID(), deduction.getSendingAccountID(), deduction.getDeductID());

    }

    @Override
    public void deleteDeduction(int deductID) {
    String sql = "DELETE FROM deductions WHERE deduct_id = ?";
    jdbcTemplate.update(sql, deductID);
    }

    private Deduction mapRowToDeduction(SqlRowSet result) {
        Deduction deduction = new Deduction();
       deduction.setDeductID(result.getInt("deduct_id"));
       deduction.setAmount(result.getBigDecimal("amount"));
       deduction.setReceivingAccountID(result.getInt("receiving_account_id"));
       deduction.setSendingAccountID(result.getInt("sending_account_id"));
       deduction.setUserID(result.getInt("user_id"));

       return deduction;
}
}
