package com.example.visualbudget.jdbc;

import com.example.visualbudget.dao.AccountDAO;
import com.example.visualbudget.model.Account;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class jdbcAccountDao implements AccountDAO {
    JdbcTemplate jdbcTemplate;

    public jdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account getAccount(int accountID) throws ChangeSetPersister.NotFoundException {
        String sql = "SELECT * FROM accounts WHERE account_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, accountID);
        if (result.next()) {
            return mapRowToAccount(result);
        }
        throw new ChangeSetPersister.NotFoundException();
    }

    @Override
    public boolean createAccount(Account account) {
        String sql = "INSERT INTO accounts (user_id, account_type, name, balance, account_id) VALUES (?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, account.getUserID(), account.getAccountType(), account.getName(), account.getBalance(), account.getAccountID());
        } catch (Exception e) {
            System.out.println("Error creating account: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<Account> getAllAccountsForUser(int userId) {
        String sql = "SELECT * FROM accounts WHERE user_id = ?";
        List<Account> listOfAccounts = new ArrayList<>();
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
        while (result.next()) {
            Account account = mapRowToAccount(result);
            listOfAccounts.add(account);
        }
        return listOfAccounts;
    }

    @Override
    public void updateAccountInfo(Account account) {
        String sql = "UPDATE accounts SET user_id = ?, account_type = ?, name = ?, balance = ? WHERE account_id = ?";
        jdbcTemplate.update(sql, account.getUserID(), account.getAccountType(), account.getName(), account.getBalance(), account.getAccountID());
    }

    @Override
    public void deleteAccount(int accountID) {
        String sql = "DELETE FROM accounts WHERE account_id = ?";
        jdbcTemplate.update(sql, accountID);
    }

    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setAccountID(rs.getInt("account_id"));
        account.setAccountType(rs.getString("account_type"));
        account.setName(rs.getString("name"));
        account.setBalance(rs.getBigDecimal("balance"));
        account.setUserID(rs.getInt("user_id"));

        return account;
    }
}
