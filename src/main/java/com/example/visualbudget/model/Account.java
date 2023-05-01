package com.example.visualbudget.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account {
    @JsonProperty("user_id")
    int userID;
    @JsonProperty("account_type")
    String accountType;
    String name;
    BigDecimal balance;
    @JsonProperty("account_id")
    int accountID;
}
