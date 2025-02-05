package com.example.noteApp.dataLocal;

import android.content.Context;

import com.example.noteApp.model.Note;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataLocalManager {
    public static DataLocalManager instance;
    private static final String PREF_LIST_NOTE = "PREF_LIST_NOTE";
    private MySharedPreferences mySharedPreferences;
    public static void init(Context context){
        instance = new DataLocalManager();
        instance.mySharedPreferences = new MySharedPreferences(context);
    }

    public static DataLocalManager getInstance() {
        if (instance == null){
            instance = new DataLocalManager();
        }
        return instance;
    }

    public static void setListNotes (Set<String> notes){

        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_LIST_NOTE, notes);
    }

    public static Set<String> getListNotes(){

        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(PREF_LIST_NOTE);
    }
}
