package com.emergingit.mealrecord;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Weekly {
    private List<Weeklydata> weeklyData;
    private SumOfWeek sumOfWeek;
}
