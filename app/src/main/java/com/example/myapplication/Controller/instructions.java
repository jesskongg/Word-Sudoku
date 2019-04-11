package com.example.myapplication.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.myapplication.Controller.MainMenu;
import com.example.myapplication.R;
import com.example.myapplication.View.SharedPref;


public class instructions extends AppCompatActivity {
    private ImageButton exitbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final SharedPref sharedPref;

        sharedPref = new SharedPref(this);

        if(sharedPref.loadNightModeState()) {
            setTheme(R.style.DarkMode);
        }
        else setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        exitbutton=findViewById(R.id.imageButton);
        exitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBacktoMainMenu();
            }
        });
    }
    // Button for user to proceed to choosing their language mode
    public void goBacktoMainMenu() {
        Intent goMainMenu = new Intent(this, MainMenu.class);
        startActivity(goMainMenu);
    }
}