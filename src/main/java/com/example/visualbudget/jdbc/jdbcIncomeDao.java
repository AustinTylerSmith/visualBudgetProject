package com.example.visualbudget.jdbc;

import com.example.visualbudget.dao.IncomeDAO;
import com.example.visualbudget.model.Deduction;
import com.example.visualbudget.model.Income;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Component
public class jdbcIncomeDao implements IncomeDAO {

    JdbcTemplate jdbcTemplate;

    public jdbcIncomeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean createIncome(Income income) {
        String sql = "INSERT INTO income (company, amount, type, pay_day, pay_frequency,user_id) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, income.getCompany(), income.getAmount(), income.getType(), income.getPayDay(), income.getPayFrequency(), income.getUserID());
        } catch (Exception e) {
            System.out.println("ERROR creating income: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Income getIncome(int incomeID) throws ChangeSetPersister.NotFoundException {
        String sql = "SELECT * FROM income WHERE income_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, incomeID);
        if (result.next()) {
            return mapRowToIncome(result);
        }
        throw new ChangeSetPersister.NotFoundException();
    }

    @Override
    public List<Income> getAllIncomeStreamsForUser(int userID) throws ChangeSetPersister.NotFoundException {
        String sql = "SELECT * FROM income WHERE user_id = ?";
        List<Income> incomeList = new ArrayList<>();
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userID);
        while (result.next()) {
            Income income = mapRowToIncome(result);
            incomeList.add(income);
        }
        if (incomeList.size() < 1) {
            throw new ChangeSetPersister.NotFoundException();
        }
        return incomeList;
    }

    @Override
    public void updateIncome(Income income) {
        String sql = "UPDATE income SET company = ?, amount = ?, type = ?, pay_day = ?, pay_frequency = ?,user_id = ? WHERE income_id = ?";
        jdbcTemplate.update(sql, income.getCompany(), income.getAmount(), income.getType(), income.getPayDay(), income.getPayFrequency(), income.getUserID(), income.getIncomeID());

    }

    @Override
    public void deleteIncome(int incomeID) {
        String sql = "DELETE FROM income WHERE income_id = ?";
        jdbcTemplate.update(sql, incomeID);
    }

    private Income mapRowToIncome(SqlRowSet result) {

        Income income = new Income();
        income.setIncomeID(result.getInt("income_id"));
        income.setCompany(result.getString("company"));
        income.setType(result.getString("type"));
        income.setAmount(result.getBigDecimal("amount"));
        income.setPayDay(DayOfWeek.valueOf(result.getString("pay_day")));
        income.setPayFrequency(result.getInt("pay_frequency"));
        income.setUserID(result.getInt("user_id"));

        return income;

    }
}
