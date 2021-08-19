package com.emergingit.mealrecord;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
    EditText etName, etEmail, etPass, etRepass;
    Button btnRegister;
    String username, email, password, repass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();
    }

    private void init() {
        etName = findViewById(R.id.etUserName);
        etPass = findViewById(R.id.etConfirmPass);
        etRepass = findViewById(R.id.etReconfirmPass);
        etEmail = findViewById(R.id.etREmail);
        btnRegister = findViewById(R.id.btnSignUp);
        initializeListeners();
    }

    private void initializeListeners() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();

            }
        });
    }

    private void registerUser() {
        if (!validateFields()) {
            return;
        }
        repass = etRepass.getText().toString();
        if (!password.equals(repass)) {
            Toast.makeText(RegistrationActivity.this, "Password didn't match", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = new User();
        user.setEmail(email);
        user.setUname(username);
        user.setPassword(password);
        Call<RegisteredUser> call = RetrofitHelper.getApiCaller().registerUser(user);
        call.enqueue(new Callback<RegisteredUser>() {
            @Override
            public void onResponse(Call<RegisteredUser> call, Response<RegisteredUser> response) {
                if (response.isSuccessful() == false || response.body() == null) {
                    Toast.makeText(RegistrationActivity.this, "Something went wrong. Please try again", Toast.LENGTH_SHORT).show();
                    return;
                }
                RegisteredUser ru = response.body();
                if (ru.response == false) {
                    Toast.makeText(RegistrationActivity.this, "Sorry! user not registered", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(RegistrationActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<RegisteredUser> call, Throwable t) {

            }
        });
    }

    private boolean validateFields() {
        username = etName.getText().toString();
        password = etPass.getText().toString();
        email = etEmail.getText().toString();
        if (username.equals("") || password.equals("") || email.equals("")) {
            Toast.makeText(RegistrationActivity.this, "Please fill up all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}