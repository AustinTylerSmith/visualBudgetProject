package com.example.visualbudget.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Deduction {
    @JsonProperty("deduct_id")
    int deductID;
    BigDecimal amount;
    @JsonProperty("receiving_account_id")
    int receivingAccountID;
    @JsonProperty("sending_account_id")
    int sendingAccountID;
    @JsonProperty("user_id")
    int userID;
}
