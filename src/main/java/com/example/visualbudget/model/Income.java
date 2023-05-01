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

    @JsonProperty("id")
    int ID;
    String company;
    BigDecimal amount;
    String type;
    DayOfWeek payDay;
    int payFrequency;

}
