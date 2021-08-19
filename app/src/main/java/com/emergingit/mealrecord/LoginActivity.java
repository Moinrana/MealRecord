package com.emergingit.mealrecord;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText etUsername, etPassword;
    TextView tvSignUp;
    Button btnSignin;
    LinearLayout container;
    ProgressBar progressBar;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (SharedPrefHelper.getIDFromSP(getApplicationContext()) != "") {
            Intent activity = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(activity);
            return;
        }
        init();
    }

    private void init() {
        container = findViewById(R.id.containerLogin);
        progressBar = findViewById(R.id.prgBarLogin);
        etUsername = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        tvSignUp = findViewById(R.id.tvLink);
        btnSignin = findViewById(R.id.btnLogin);
        initializeListeners();
    }


    private void toggleProgress(final boolean showProgress) {
        container.setVisibility(showProgress ? View.GONE : View.VISIBLE);
        progressBar.setVisibility(showProgress ? View.VISIBLE : View.GONE);
    }

    private void initializeListeners() {
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(activity);
            }
        });
    }

    private boolean validateFields() {
        email = etUsername.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        if (email.length() == 0 || password.length() == 0) {
            Toast.makeText(LoginActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void signIn() {
        if (!validateFields()) {
            return;
        }
        toggleProgress(true);
        Call<List<User>> call = RetrofitHelper.getApiCaller().getUsers();
        call.enqueue(new Callback<List<User>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                toggleProgress(false);
                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(LoginActivity.this, "Something went wrong please try again", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<User> users;
                users = response.body();
                boolean userFound = false;
                for (User user : users) {
                    if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                        Toast.makeText(LoginActivity.this, "Welcome to MealRecord.", Toast.LENGTH_SHORT).show();
                        SharedPrefHelper.storeStringIn(LoginActivity.this.getApplicationContext(), user.getUserId(), user.getUname());
                        userFound = true;
                        break;
                    }
                }
                if (userFound == true) {
                    Intent activity = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(activity);
                } else {
                    Toast.makeText(LoginActivity.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                toggleProgress(false);
                Toast.makeText(LoginActivity.this, "Error:" + t, Toast.LENGTH_SHORT).show();
            }

        });
    }
}