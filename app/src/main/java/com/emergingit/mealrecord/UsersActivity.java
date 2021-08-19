package com.emergingit.mealrecord;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsersActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    LinearLayout container;
    LinearLayoutManager layoutManager;
    AdapterClassUsers adapter;
    UsersModel usersModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        toggleProgress(true);
    }

    private void init() {
        container = findViewById(R.id.layoutContainer);
        progressBar = findViewById(R.id.prgBarUsers);
        recyclerView = findViewById(R.id.recyclerUsers);
        initializeRecycler();
        getUsers();
    }

    private void toggleProgress(final boolean showProgress) {
        container.setVisibility(showProgress ? View.GONE : View.VISIBLE);
        progressBar.setVisibility(showProgress ? View.VISIBLE : View.GONE);
    }

    private void getUsers() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://mealrecord.herokuapp.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        MealRecordAPI mealRecordAPI = retrofit.create(MealRecordAPI.class);

        Call<List<User>> call = RetrofitHelper.getApiCaller().getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                toggleProgress(false);
                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(UsersActivity.this, "Something went wrong please try again", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<User> users;
                users = response.body();
                adapter.setUsersList(users);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                toggleProgress(false);
                Toast.makeText(UsersActivity.this, "Error:" + t, Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }

        });
    }

    private void initializeRecycler() {
        recyclerView = findViewById(R.id.recyclerUsers);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdapterClassUsers(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}