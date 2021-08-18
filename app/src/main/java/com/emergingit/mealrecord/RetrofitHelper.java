package com.emergingit.mealrecord;

import lombok.AllArgsConstructor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@AllArgsConstructor

public class RetrofitHelper {
    private static final String baseURL = "https://mealrecord.herokuapp.com/";

    public static MealRecordAPI getApiCaller() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MealRecordAPI mealRecordAPI = retrofit.create(MealRecordAPI.class);
        return mealRecordAPI;
    }
}
