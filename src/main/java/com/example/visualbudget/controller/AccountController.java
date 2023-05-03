package com.example.visualbudget.controller;

import com.example.visualbudget.dao.AccountDAO;
import com.example.visualbudget.model.Account;
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
    public ResponseEntity<Account> getAccountByID(@PathVariable("ID") int accountID) {
        try {
            Account account = accountDAO.getAccount(accountID);
            return new ResponseEntity<>(account, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("ERROR retrieving account " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/allAccounts/{ID}")
    public ResponseEntity<List<Account>> getAllAccountsForUser(@PathVariable("ID") int userID) {
        try {
            List<Account> accountList = accountDAO.getAllAccountsForUser(userID);
            return  new ResponseEntity<>(accountList, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("ERROR retrieving user Accounts: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateAccount")
    public ResponseEntity<Void> updateAccountInfo(@RequestBody Account account) {
        try {
            accountDAO.updateAccountInfo(account);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            System.out.println("ERROR updating account " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteAccount/{ID}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("ID") int accountID) {
        try {
            accountDAO.deleteAccount(accountID);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            System.out.println("ERROR Deleting account: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
