package com.example.visualbudget.controller;

import com.example.visualbudget.dao.AccountDAO;
import com.example.visualbudget.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
public class AccountController {
    private final AccountDAO accountDAO;

    @Autowired
    public AccountController(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/createAccount")
    public void createAccount(@RequestBody Account account) {
        if(!accountDAO.createAccount(account)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Creating account Failed");
        }
    }

    @GetMapping("/myAccount/{ID}")
    public Account getAccountByID(@PathVariable("ID") int accountID) {
        try {
            return accountDAO.getAccount(accountID);
        } catch (Exception e) {
            System.out.println("ERROR retrieving account " + e.getMessage() );
            return null;
        }
    }

    @GetMapping("/allAccounts/{ID}")
    public List<Account> getAllAccountsForUser(@PathVariable("ID") int userID) {
        try {
            return accountDAO.getAllAccountsForUser(userID);
        } catch (Exception e) {
            System.out.println("ERROR retrieving user Accounts: " + e.getMessage());
        }
        return null;
    }

    @PutMapping("/updateAccount")
    public void updateAccountInfo(@RequestBody Account account) {
        try {
            accountDAO.updateAccountInfo(account);
        } catch (Exception e) {
            System.out.println("ERROR updating account " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteAccount/{ID}")
    public void deleteAccount(@PathVariable("ID") int accountID) {
        try {
            accountDAO.deleteAccount(accountID);
        } catch (Exception e) {
            System.out.println("ERROR Deleting account: " + e.getMessage());
        }
    }
}
