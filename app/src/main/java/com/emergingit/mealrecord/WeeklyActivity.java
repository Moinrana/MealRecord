package com.emergingit.mealrecord;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeeklyActivity extends AppCompatActivity {
    LinearLayout weeklyContainer;
    ProgressBar progressBar;
    TextView tvTotalMeals, tvTotalAmount;
    RecyclerView recyclerView;
    AdapterClass adapter;
    LinearLayoutManager layoutManager;
    Weekly weekly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        toggleProgress(true);
    }

    private void init() {
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        tvTotalMeals = findViewById(R.id.tvTotalMeals);
        progressBar = findViewById(R.id.prgBar);
        weeklyContainer = findViewById(R.id.secondaryContainer);
        putDataOnRecycleView();
        getWeeklyMeals();
    }

    private void toggleProgress(final boolean showProgress) {
        weeklyContainer.setVisibility(showProgress ? View.GONE : View.VISIBLE);
        progressBar.setVisibility(showProgress ? View.VISIBLE : View.GONE);
    }

    private void getWeeklyMeals() {
        callTheAPI();
    }

    private void callTheAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://mealrecord.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MealRecordAPI mealRecordAPI = retrofit.create(MealRecordAPI.class);

        Call<Weekly> call = mealRecordAPI.getWeeklyData();
        call.enqueue(new Callback<Weekly>() {
            @Override
            public void onResponse(Call<Weekly> call, Response<Weekly> response) {
                toggleProgress(false);
                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(WeeklyActivity.this, "Something went wrong please try again", Toast.LENGTH_SHORT).show();
                    return;
                }
                weekly = response.body();
                adapter.setWeeklydata(weekly.getWeeklyData());
                adapter.notifyDataSetChanged();
                tvTotalAmount.setText("Total Amount: " + ((weekly.getSumOfWeek() != null) ? String.valueOf(weekly.getSumOfWeek().getTotalAmount()) : '0'));
                tvTotalMeals.setText("Total Meals: " + ((weekly.getSumOfWeek() != null) ? String.valueOf(weekly.getSumOfWeek().getTotalMealCount()) : '0'));
            }

            @Override
            public void onFailure(Call<Weekly> call, Throwable t) {
                toggleProgress(false);
                Toast.makeText(WeeklyActivity.this, "Error:" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void putDataOnRecycleView() {
        recyclerView = findViewById(R.id.reclmealViewes);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdapterClass(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}