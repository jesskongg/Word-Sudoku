package com.example.myapplication.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.myapplication.R;

public class MainMenu extends AppCompatActivity {

    private Button startButton;
    private Button helpButton;
    private Button newGameButton;
    private ImageButton shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
}
