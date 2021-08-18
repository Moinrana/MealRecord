package com.emergingit.mealrecord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.card.MaterialCardView;

public class HomeActivity extends AppCompatActivity {

    MaterialCardView cardWeeklyMeal, cardDailyMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }

    private void init() {
        cardWeeklyMeal = findViewById(R.id.cardWeeklyMeals);
        cardDailyMeal = findViewById(R.id.cardDailyMeal);
        initializeListeners();
    }

    private void initializeListeners() {
        cardDailyMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newActivity = new Intent(HomeActivity.this, DailyActivity.class);
                startActivity(newActivity);
            }
        });

        cardWeeklyMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newActivity = new Intent(HomeActivity.this, WeeklyActivity.class);
                startActivity(newActivity);
            }
        });
    }
}