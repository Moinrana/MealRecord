package com.emergingit.mealrecord;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MealRecordAPI {
    @GET("api/person")
    Call<List<Person>> getPersons();

    @GET("meals/weekly")
    Call<Weekly> getWeeklyData();

    @POST("meals/daily")
    Call<List<Weeklydata>> getDailyMeals();

    @GET("users/all")
    Call<List<User>> getUsers();

    @POST("meals/add")
    Call<Meal> addMeal(@Body Meal meal);

    @POST("users/signup")
    Call<RegisteredUser> registerUser(@Body User user);
}
