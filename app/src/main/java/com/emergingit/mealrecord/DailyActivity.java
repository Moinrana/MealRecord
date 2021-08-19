package com.emergingit.mealrecord;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    FloatingActionButton btnUsers, btnItem;
    Map<String, Integer> map = new HashMap<String, Integer>() {{
        put("One", 1);
        put("Two", 2);
        put("Three", 3);
        put("Four", 4);
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);
        init();
    }

//    @Override
//    protected void onRestart() {
//        super.onStart();
//        toggleProgress(true);
//        getDailyMeals();
//    }


    @Override
    protected void onRestart() {
        super.onRestart();
        toggleProgress(true);
        getDailyMeals();
    }

    private void init() {
        tvTotalMealDaily = findViewById(R.id.tvTotalMealsDaily);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        dailyMealContainer = findViewById(R.id.maincontainer);
        progressBar = findViewById(R.id.prgBarDaily);
        btnItem = findViewById(R.id.fab_item);
        btnUsers = findViewById(R.id.fab_user);
        initializeListeners();
        initializeRecycler();
    }

    private void initializeListeners() {
        btnUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity = new Intent(DailyActivity.this, UsersActivity.class);
                startActivity(activity);
            }
        });

        btnItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogBox();
            }
        });
    }

    private void showDialogBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DailyActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
        builder.setTitle("Number of Meals");
        Spinner spinner = (Spinner) mView.findViewById(R.id.spinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, map.keySet().toArray(new String[0]));
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int val = map.get(spinner.getSelectedItem().toString());
                addMeal(val);
            }
        });
        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setView(mView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void addMeal(int mealCount) {
        toggleProgress(true);
        Meal meal = new Meal();
        meal.setMealCount(mealCount);
        meal.setUserId(SharedPrefHelper.getIDFromSP(getApplicationContext()));
        Call<Meal> call = RetrofitHelper.getApiCaller().addMeal(meal);
        call.enqueue(new Callback<Meal>() {
            @Override
            public void onResponse(Call<Meal> call, Response<Meal> response) {
                toggleProgress(false);
                if (!response.isSuccessful() && response.body() == null) {
                    Toast.makeText(DailyActivity.this, "Something went wrong please try again", Toast.LENGTH_SHORT).show();
                    return;
                }
                Meal res = new Meal();
                res = response.body();
                if (res.isAdded() != true) {
                    Toast.makeText(DailyActivity.this, "Sorry. Meal couldn't be added successfully", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(DailyActivity.this, "Meal added successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Meal> call, Throwable t) {
                toggleProgress(false);
                Toast.makeText(DailyActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
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
                Weekly weekly = new Weekly();
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