package com.example.visualbudget.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.DayOfWeek;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cost {
@JsonProperty("cost_id")
int costID;
@JsonProperty("user_id")
int userID;
String description;
BigDecimal amount;
@JsonProperty("first_day_out")
int firstDayOut;
boolean reoccurs;
@JsonProperty("day_of_recurrence")
DayOfWeek dayOfRecurrence;

}
