package com.emergingit.mealrecord;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeeklyActivity extends AppCompatActivity {
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

    private void init() {
        putDatasOnRecycleView();
        getWeeklyMeals();
    }

    private void getWeeklyMeals() {
        callTheAPI();
    }

    private void callTheAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://mealrecord.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MealRecordAPI mealRecordAPI = retrofit.create(MealRecordAPI.class);

        Call<Weekly> call = mealRecordAPI.getWeeklyData();
        call.enqueue(new Callback<Weekly>() {
            @Override
            public void onResponse(Call<Weekly> call, Response<Weekly> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(WeeklyActivity.this, "Something went wrong please try again", Toast.LENGTH_SHORT).show();
                    return;
                }
                weekly = response.body();
                adapter.setWeeklydata(weekly.getWeeklydata());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Weekly> call, Throwable t) {
                Toast.makeText(WeeklyActivity.this, "Error:" + t, Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void putDatasOnRecycleView() {
        recyclerView = findViewById(R.id.reclmealViewes);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdapterClass(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}