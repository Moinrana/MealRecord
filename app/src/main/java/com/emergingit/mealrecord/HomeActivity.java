package com.emergingit.mealrecord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

import org.w3c.dom.Text;

public class HomeActivity extends AppCompatActivity {

    MaterialCardView cardWeeklyMeal, cardDailyMeal;
    TextView tvUname, tvLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }

    private void init() {
        tvUname = findViewById(R.id.tvUnameHome);
        cardWeeklyMeal = findViewById(R.id.cardWeeklyMeals);
        cardDailyMeal = findViewById(R.id.cardDailyMeal);
        tvLogout = findViewById(R.id.tvlogoutHome);
        tvUname.setText(SharedPrefHelper.getNameFromSP(HomeActivity.this.getApplicationContext()));
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

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // refresh sharedPreference
                SharedPrefHelper.storeStringIn(getApplicationContext(), "", "");
                Intent activity = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(activity);
                finish();
            }
        });
    }
}