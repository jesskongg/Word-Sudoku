package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SelectLanguageMode extends AppCompatActivity {

    private Button enFrButton;
    private Button frEnButton;
    private Switch enableLCswitch;
   // private int mode_load_default;

    //String[] loaded_data;



    int LC_enabled = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language_mode);

        //switch used to enable listening comprehension mode
        enableLCswitch = findViewById(R.id.enableLC);
        enableLCswitch.setChecked(false); //by default, it is false. User must enable it
        enableLCswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    LC_enabled = 1; // L.C. MODE ON
                }
                else{
                    LC_enabled = 0; // L.C. MODE OFF
                }
            }
        });


        enFrButton = (Button) findViewById(R.id.eng_fr);
        enFrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(this, main)
                openMainActivity(1, LC_enabled);
            }
        });

        frEnButton = (Button) findViewById(R.id.fr_eng);
        frEnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity(2,LC_enabled);
            }
        });

    }

    public void openMainActivity(int lang, int LC_enabled) {


        Intent goMainActivity = new Intent();
        goMainActivity.putExtra("language", lang);
        goMainActivity.putExtra("modeLC", LC_enabled);
        //goMainActivity.putExtra("mode_load_old", mode_load_default);

        goMainActivity.setClass(this, MainActivity.class);
        startActivity(goMainActivity);

    }
}
