package com.example.myapplication.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.myapplication.R;




public class MainMenu extends AppCompatActivity {

    SharedPreferences sharedpreferences_for_grid_var;
    static final String MyPREFERENCES = "Sudoku_pref" ;

    static final String totalBonusKey="TotalBonus";
    static final String bonusFor9x9="Bonus9x9";
    static final String bonusFor4x4="Bonus4x4";
    static final String bonusFor6x6="Bonus6x6";
    static final String bonusFor12x12="Bonus12x12";



    int myBonus;
    String myBonusString;
    String bonus4x4;
    String bonus6x6;
    String bonus9x9;
    String bonus12x12;


    private Button startButton;
    private Button helpButton;
    private Button newGameButton;
    private ImageButton shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        sharedpreferences_for_grid_var = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor_grid_var = sharedpreferences_for_grid_var.edit();


        myBonus=sharedpreferences_for_grid_var.getInt(totalBonusKey,0);
        myBonusString=Integer.toString(myBonus);
        bonus4x4=Integer.toString(sharedpreferences_for_grid_var.getInt(bonusFor4x4, 0));
        bonus6x6=Integer.toString(sharedpreferences_for_grid_var.getInt(bonusFor6x6, 0));
        bonus9x9=Integer.toString(sharedpreferences_for_grid_var.getInt(bonusFor9x9, 0));
        bonus12x12=Integer.toString(sharedpreferences_for_grid_var.getInt(bonusFor12x12, 0));




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
        share.putExtra(Intent.EXTRA_TEXT, "I'm learning new languages with Wudoku! "
                +"\n"+"My current score is "+myBonusString+"!"+"\n"+
        "4X4 puzlle bonus: "+bonus4x4+"\n"+
        "6x6 puzzle bonus: "+bonus6x6+"\n"+
        "9x9 puzzle bonus: "+bonus9x9+"\n"+
        "12x12 puzzle bonus: "+bonus12x12);


        //share.putExtra(Intent.EXTRA_SCORE, "here");
        //share.


        //share.putExtra(Intent.EXTRA_BONUS,"My cuurent score is ");

    //+ "My current score is " + score);
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
