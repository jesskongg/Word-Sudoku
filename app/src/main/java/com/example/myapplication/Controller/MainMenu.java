package com.example.myapplication.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class MainMenu extends AppCompatActivity {

    private Button startButton;
    private Button helpButton;
    private Button newGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        newGameButton = (Button) findViewById(R.id.new_game);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectLoadMode(1);
            }
        });

        startButton = (Button) findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectLoadMode(0);
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
    }



    // Button for user to proceed to choosing their language mode
    public void openSelectLoadMode(int newGame) {
        //THIS SHOULD BE CHANGE TO LOAD_NEW_WORDS FILE
        Intent goLoadWords = new Intent(this, load_new_words.class);
        goLoadWords.putExtra("newGame", newGame);
        startActivity(goLoadWords);
    }


    public void openInstructions() {
        Intent goOpenInstr = new Intent(this, instructions.class);
        startActivity(goOpenInstr);
    }
}
