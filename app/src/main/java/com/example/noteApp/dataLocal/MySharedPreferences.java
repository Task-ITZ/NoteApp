package com.example.noteApp.dataLocal;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.noteApp.model.Note;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MySharedPreferences {

    public static final String MY_SHAREs_PREFERENCES = "MY_SHAREs_PREFERENCES";
    private Context context;

    public MySharedPreferences(Context context) {
        this.context = context;
    }

    public void putStringValue(String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHAREs_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public String getStringValue(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHAREs_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
}
