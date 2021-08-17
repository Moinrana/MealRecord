package com.emergingit.mealrecord;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Weeklydata {
    private int totalMeal;
    private int totalPrice;
    private List<User> user;

}
