package com.example.myapplication.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.ImageButton;

import com.example.myapplication.R;
import com.example.myapplication.View.SharedPref;

public class MainMenu extends AppCompatActivity {

    private Button startButton;
    private Button helpButton;
    private Button newGameButton;
    private ImageButton shareButton;

    //dark mode variables
    private Switch dayNight;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPref = new SharedPref(this);

        if(sharedPref.loadNightModeState()) {
            setTheme(R.style.DarkMode);
        }
        else setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        startButton = (Button) findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectLoadMode();
            }
        });

        helpButton = (Button) findViewById(R.id.help_button);
        helpButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                openInstructions();
            }
        });

        shareButton = (ImageButton) findViewById(R.id.share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareInfo();
            }
        });


        //dark mode switch
        dayNight = findViewById(R.id.dayNight);
        if (sharedPref.loadNightModeState()) {
            dayNight.setChecked(true);
        }

        dayNight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sharedPref.setNightModeState(true);
                    restartApp();
                }
                else {
                    sharedPref.setNightModeState(false);
                    restartApp();
                }
            }
        });
    }


    public void shareInfo(){
        Intent share;
        share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        //int score =0;
        share.putExtra(Intent.EXTRA_SUBJECT, "Wudoku");
        share.putExtra(Intent.EXTRA_TEXT, "I'm learning new languages with Wudoku!");//+ "My current score is " + score);
        startActivity(Intent.createChooser(share, "Share via"));
    }

    // Button for user to proceed to choosing their language mode
    public void openSelectLoadMode() {
        //THIS SHOULD BE CHANGE TO LOAD_NEW_WORDS FILE
        Intent goLoadWords = new Intent(this, load_new_words.class);
        startActivity(goLoadWords);
    }


    public void openInstructions() {
        Intent goOpenInstr = new Intent(this, instructions.class);
        startActivity(goOpenInstr);
    }

    //dark/light mode
    public void restartApp() {
        Intent i = new Intent(getApplicationContext(), MainMenu.class);
        startActivity(i);
        finish();
    }
}
