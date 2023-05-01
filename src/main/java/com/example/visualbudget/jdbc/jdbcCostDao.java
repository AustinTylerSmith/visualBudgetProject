package com.example.visualbudget.jdbc;

import com.example.visualbudget.dao.CostDAO;
import com.example.visualbudget.model.Cost;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.time.DayOfWeek.valueOf;

@Component
public class jdbcCostDao implements CostDAO {
    JdbcTemplate jdbcTemplate;

    public jdbcCostDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public boolean createCost(Cost cost) {
        String sql = "INSERT INTO costs (user_id, description, amount, first_day_out, reoccurs, day_of_recurrence, cost_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, cost.getUserID(), cost.getDescription(), cost.getAmount(), cost.getFirstDayOut(), cost.isReoccurs(), cost.getDayOfRecurrence().toString(), cost.getCostID());
        } catch (Exception e) {
            System.out.println("Creating cost FAILED: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Cost getCost(int costId) throws ChangeSetPersister.NotFoundException {
        String sql = "SELECT * FROM costs WHERE cost_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, costId);
        if (result.next()) {
            return mapRowToCost(result);
        }
        throw new ChangeSetPersister.NotFoundException();
    }

    @Override
    public List<Cost> getAllCostsForUser(int userID) throws ChangeSetPersister.NotFoundException {
        String sql = "SELECT * FROM costs WHERE user_id = ?";
        List<Cost> listOfCosts = new ArrayList<>();
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userID);
        while (result.next()) {
            Cost cost = mapRowToCost(result);
            listOfCosts.add(cost);
        }
        return listOfCosts;
    }

    @Override
    public void updateCostInfo(Cost cost) {
        String sql = "UPDATE costs SET user_id = ?, description = ?, amount = ?, first_day_out = ?, reoccurs = ?, day_of_recurrence = ? WHERE cost_id = ?";
        jdbcTemplate.update(sql, cost.getUserID(), cost.getDescription(), cost.getAmount(), cost.getFirstDayOut(),cost.isReoccurs(), cost.getDayOfRecurrence(), cost.getCostID());
    }

    @Override
    public void deleteCost(int costID) {
        String sql = "DELETE FROM costs WHERE cost_id = ?";
        jdbcTemplate.update(sql, costID);
    }

    private Cost mapRowToCost(SqlRowSet result) {
        Cost cost = new Cost();
        cost.setCostID(result.getInt("cost_id"));
        cost.setUserID(result.getInt("user_id"));
        cost.setAmount(result.getBigDecimal("amount"));
        cost.setDescription(result.getString("description"));
        cost.setReoccurs(result.getBoolean("reoccurs"));
        String dayOfRecurrence = result.getString("day_of_recurrence");
        if(dayOfRecurrence != null) {
            cost.setDayOfRecurrence(valueOf(dayOfRecurrence));
        }
        cost.setFirstDayOut(result.getInt("first_day_out"));
        return cost;
    }
}
