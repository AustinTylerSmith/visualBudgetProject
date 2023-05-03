package com.example.visualbudget.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.DayOfWeek;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Income {

    @JsonProperty("income_id")
    int incomeID;
    String company;
    BigDecimal amount;
    String type;
    @JsonProperty("pay_day")
    DayOfWeek payDay;
    @JsonProperty("pay_frequency")
    int payFrequency;
    @JsonProperty("user_id")
    int userID;

}
