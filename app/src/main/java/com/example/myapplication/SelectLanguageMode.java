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
                openMainActivity();
            }
        });

        frEnButton = (Button) findViewById(R.id.fr_eng);
        frEnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        }

    public void openMainActivity() {
        Intent goMainActivity = new Intent(this, MainActivity.class);
        startActivity(goMainActivity);

    }
}
