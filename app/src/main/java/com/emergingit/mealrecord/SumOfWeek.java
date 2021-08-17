package com.emergingit.mealrecord;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SumOfWeek {
    private int totalAmount;
    private int totalMealCount;
}
