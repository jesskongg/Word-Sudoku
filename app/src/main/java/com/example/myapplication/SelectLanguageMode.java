package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectLanguageMode extends AppCompatActivity {

    private Button enFrButton;
    private Button frEnButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language_mode);

        enFrButton = (Button) findViewById(R.id.eng_fr);
        enFrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity(1);
            }
        });

        frEnButton = (Button) findViewById(R.id.fr_eng);
        frEnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity(2);
            }
        });

        }

    public void openMainActivity(int value) {
        Intent goMainActivity = new Intent();
        goMainActivity.putExtra("language", value);
        goMainActivity.setClass(this, MainActivity.class);
        startActivity(goMainActivity);

    }
}
