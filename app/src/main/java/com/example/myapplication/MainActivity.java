package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

//import static com.bignerdranch.android.trial2.R.layout.cell_layout_after_click;
//position of arrayGrid starts from 0.

public class MainActivity extends AppCompatActivity {
      //puzzle variables
    private GridView gridView;
    private TextView textView;
    //menu variables
    private GridView menuView;
    private TextView textMenu;

    //variables for grid-menu communication
    private String received_text=" ";
    private int board_cell_clicked_position=-100;
    private int menu_cell_clicked_position=-100;

    private ImageButton backSelect;

    //number board variables
    private int board[];
     private int solvable_board[];

    //object for cheker. input: solved number board and number of rows and columns
    private board_checker checkBoard_object= new board_checker(solvable_board,9,9);

    private String wordListKeyboard[];
    private String wordListSudokuTable[];

    //object which gives filled with words sudoku grid and menu depending on chosen language
    private boards_and_menu_data data_object= new boards_and_menu_data();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //recive language intent and set wordListKeyboard and wordListSudokuTable
        Intent language = getIntent();
        int numberOfLanguage = language.getIntExtra("language", 0);
        setWordList(numberOfLanguage);
        board=data_object.getNumber_board();
        solvable_board=data_object.getNumber_board();

        //grid puzzle
        gridView=(GridView) findViewById(R.id.grid);
        textView=(TextView) findViewById(R.id.textView);

        //menu grid
        menuView=(GridView) findViewById(R.id.grid_menu);
        textMenu=(TextView) findViewById(R.id.menu_cell);

        //adapter for puzzle grid
        final ArrayAdapter adapter;
        adapter = new ArrayAdapter(this,R.layout.cell_layout,wordListSudokuTable );
        gridView.setAdapter(adapter);

        //adapter for menu
        final ArrayAdapter menu_adapter;
        menu_adapter= new ArrayAdapter(this, R.layout.cell_menu_layout, wordListKeyboard);
        menuView.setAdapter(menu_adapter);

        backSelect = (ImageButton) findViewById(R.id.back_select);
        backSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                        R.string.back_select_information,
                        Toast.LENGTH_SHORT).show();
                backSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goSelect();
                    }
                });
            }
        });


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l){
                String toast_fill_cell="Click on a menu!";
                //if a cell has no content then a user is asked to fill the cell
                if (board[position]==0)
                {
                    //boolean empty_cell_clicked=true;
                    board_cell_clicked_position=position;
                    Toast.makeText(getApplicationContext(), toast_fill_cell, Toast.LENGTH_SHORT).show();
                    TextView v = (TextView) view;
                    v.setTextColor(Color.BLUE);
                    v.setBackgroundResource(R.drawable.cell_shape_after_click);

                    if (menu_cell_clicked_position>=0) {
                        wordListSudokuTable[position] = received_text;
                        adapter.notifyDataSetChanged();
                        solvable_board[position] = menu_cell_clicked_position + 1;
                    }
                    //board[position]=wordListF[position+1];
                }
            }
        });

        menuView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //when you click
                //String word_from_menu;
                if (board_cell_clicked_position>=0) {
                    received_text = (String) menu_adapter.getItem(position);
                    //Toast.makeText(getApplicationContext(), received_text, Toast.LENGTH_SHORT).show();
                   menu_cell_clicked_position=position;
                }
                else //board_cell_clicked_position=-100
                {
                    //click on the board
                    String click_puzzle_cell="Choose empty cell first!";
                    Toast.makeText(getApplicationContext(), click_puzzle_cell, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button checkBoard= (Button) findViewById(R.id.checkBoard);
        checkBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isCorrect;

                isCorrect = checkBoard_object.checker();
                if(isCorrect == true) {
                    Toast.makeText(MainActivity.this,
                            R.string.boardTrue,
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,
                            R.string.boardFalse,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //OUTSIDE OF ON CREATE FUNCTION
    private void setWordList(int caseNumber){
        if(caseNumber == 1){
            wordListSudokuTable = data_object.generate_get_grid_English();
            wordListKeyboard = data_object.getMenu_list_French();
        }
        else{
            wordListSudokuTable = data_object.generate_get_grid_French();
            wordListKeyboard = data_object.getMenu_list_French();

        }
    }
    private void goSelect() {
        Intent goSelect = new Intent(this, SelectLanguageMode.class);
        startActivity(goSelect);
    }

}
