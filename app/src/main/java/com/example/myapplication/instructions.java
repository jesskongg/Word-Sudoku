package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class instructions extends AppCompatActivity {
    private ImageButton exitbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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