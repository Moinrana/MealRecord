package com.emergingit.mealrecord;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DailyActivity extends AppCompatActivity {
    TextView tvTotalMealDaily, tvTotalAmount;
    RecyclerView recyclerView;
    AdapterClass adapter;
    LinearLayoutManager layoutManager;
    LinearLayout dailyMealContainer;
    ProgressBar progressBar;
    FloatingActionButton btnAddMeal, btnUsers, btnItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        toggleProgress(true);
    }

    private void init() {
        tvTotalMealDaily = findViewById(R.id.tvTotalMealsDaily);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        dailyMealContainer = findViewById(R.id.maincontainer);
        progressBar = findViewById(R.id.prgBarDaily);
        btnAddMeal = findViewById(R.id.fab_add);
        btnItem = findViewById(R.id.fab_item);
        btnUsers = findViewById(R.id.fab_user);
        initializeListeners();
        initializeRecycler();
        getDailyMeals();
    }

    private void initializeListeners() {
        btnUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity = new Intent(DailyActivity.this, UsersActivity.class);
                startActivity(activity);
            }
        });

        btnAddMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void toggleProgress(final boolean showProgress) {
        dailyMealContainer.setVisibility(showProgress ? View.GONE : View.VISIBLE);
        progressBar.setVisibility(showProgress ? View.VISIBLE : View.GONE);
    }

    private void getDailyMeals() {
        Call<List<Weeklydata>> call = RetrofitHelper.getApiCaller().getDailyMeals();
        call.enqueue(new Callback<List<Weeklydata>>() {
            @Override
            public void onResponse(Call<List<Weeklydata>> call, Response<List<Weeklydata>> response) {
                toggleProgress(false);
                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(DailyActivity.this, "Something went wrong please try again", Toast.LENGTH_SHORT).show();
                    return;
                }
                Weekly weekly =new Weekly();
                weekly.setWeeklyData(response.body());
                adapter.setWeeklydata(weekly.getWeeklyData());
                adapter.notifyDataSetChanged();
                if (weekly.getSumOfWeek() != null) {
                    tvTotalAmount.setText(String.valueOf(weekly.getSumOfWeek().getTotalAmount()));
                    tvTotalMealDaily.setText(String.valueOf(weekly.getSumOfWeek().getTotalMealCount()));
                }
            }

            @Override
            public void onFailure(Call<List<Weeklydata>> call, Throwable t) {
                toggleProgress(false);
                t.printStackTrace();
                Toast.makeText(DailyActivity.this, "Something went wrong please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeRecycler() {
        recyclerView = findViewById(R.id.recyclerMeals);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdapterClass(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}