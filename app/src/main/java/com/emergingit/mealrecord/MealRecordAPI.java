package com.emergingit.mealrecord;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MealRecordAPI {
    @GET("api/person")
    Call<List<Person>> getPersons();

    @GET("meals/weekly")
    Call<Weekly> getWeeklyData();

    @GET("users/all")
    Call<List<User>> getUsers();

}
