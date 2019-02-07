package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mButton = (Button) findViewById(R.id.start_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectLanguageMode();
            }
        });
    }

    // Button for user to proceed to choosing their language mode
    public void openSelectLanguageMode() {
        Intent goSetLang = new Intent(this, SelectLanguageMode.class);
        startActivity(goSetLang);
    }
}
