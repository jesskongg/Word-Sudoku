package com.example.myapplication.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.myapplication.Controller.MainActivity;
import com.example.myapplication.R;

public class SelectLanguageMode extends AppCompatActivity {

    private Button enFrButton;
    private Button frEnButton;
    private Switch enableLCswitch;
    private Spinner selectGridSize;
   // private int mode_load_default;

    //String[] loaded_data;
    //variable set up
    int LC_enabled = 0;
    //variables for grid size selection
    int gridLength = 9;
    int subgridLength = 3;
    int subgridWidth = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language_mode);

        //drop-down menu for selection of GRID SIZE
        selectGridSize = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gridSize, android.R.layout.simple_spinner_item);
        //layout for drop-down
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectGridSize.setAdapter(adapter);
        selectGridSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        //when first item (9x9 grid) from drop-down menu selected
                        gridLength = 9;
                        subgridLength = 3;
                        subgridWidth = 3;
                        break;
                    case 1:
                        //when second item (4x4 grid) from drop-down menu selected
                        gridLength = 4;
                        subgridLength = 2;
                        subgridWidth = 2;
                        break;
                    case 2:
                        //when third item (6x6 grid) from drop-down menu selected
                        gridLength = 6;
                        subgridLength = 2;
                        subgridWidth = 3;
                        break;
                    case 3:
                        //when fourth item (12x12 grid) from drop-down menu selected
                        gridLength = 12;
                        subgridLength = 3;
                        subgridWidth = 4;
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //set to default (9x9 grid)


            }
        });


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
