package com.example.visualbudget.dao;

import com.example.visualbudget.model.Account;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface AccountDAO {
    //create account
    boolean createAccount(Account account);

    //get account
    Account getAccount(int accountID) throws ChangeSetPersister.NotFoundException;

    // get a list of accounts
    List<Account> getAllAccountsForUser(int userId);

    // update account info
    void updateAccountInfo(Account account);

    //delete account
    void deleteAccount(int countID);
}
