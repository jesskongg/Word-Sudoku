package com.example.myapplication.View;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    private SharedPreferences sharedPreferences;

    // constructor to receive application context
    public SharedPref(Context context) {
        sharedPreferences = context.getSharedPreferences("filename", Context.MODE_PRIVATE);
    }

    // method to save night mode state: either True or False
    public void setNightModeState(Boolean state) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("NightMode",state);
        editor.apply();
    }

    // load night mode state
    public Boolean loadNightModeState (){
        Boolean state = sharedPreferences.getBoolean("NightMode", false);
        return state;
    }

}
