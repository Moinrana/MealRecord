package com.emergingit.mealrecord;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefHelper {
    private static final String keyForName = "loginName";
    private static final String keyForID = "loginID";
    private static final String spName = "SP_FOR_LOGIN_INFO";

    public static void storeStringIn(Context context, String id, String name) {
        SharedPreferences sharedPref =   context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(keyForName, name);
        editor.putString(keyForID, id);
        editor.commit();
    }


    public static String getNameFromSP(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return sharedPref.getString(keyForName, "No Name");
    }

    public static String getIDFromSP(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return sharedPref.getString(keyForID, "");
    }
}
