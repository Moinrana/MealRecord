package com.emergingit.mealrecord;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meal {
    private String mealId;
    private String userId;
    private int mealCount;
    private int price;
    private Date date;
    private int ds;
    private boolean isAdded;
}
