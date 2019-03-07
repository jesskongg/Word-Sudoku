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
    private int board_cell_clicked_position;
    private int menu_cell_clicked_position;
    private boolean menu_cell_clicked=false;
    private boolean grid_cell_clicked=false;

    private ImageButton backSelect;

    //number board variables
    private int board[]=new int[81];
    private int solvable_board[]=new int[81];

    //object for checker. input: solved number board and number of rows and columns
    //private board_checker checkBoard_object= new board_checker(solvable_board,9,9);

    private String wordListKeyboard[];
    private String wordListSudokuTable[];

    private String hint_for_board[];

    //object which gives filled with words sudoku grid and menu depending on chosen language
    private boards_and_menu_data data_object= new boards_and_menu_data();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //receive mode intent and set wordListKeyboard and wordListSudokuTable
        Intent mode = getIntent();
        int langMode = mode.getIntExtra("language", 0);
        int LC_enabled = mode.getIntExtra("modeLC", 0);
        setWordList(langMode, LC_enabled);
        board=data_object.getNumber_board();
        solvable_board=data_object.getSolvable_board();

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
                String toast_fill_cell="Click on a menu to fill empty cell!";
                //if a cell has no content then a user is asked to fill the cell
                if (board[position]==0)
                {
                    board_cell_clicked_position=position;
                    grid_cell_clicked=true;
                    Toast.makeText(getApplicationContext(), toast_fill_cell, Toast.LENGTH_SHORT).show();
                    TextView v = (TextView) view;
                    v.setBackgroundResource(R.drawable.cell_shape_after_click);

                }

                else
                {
                    final int current_position=position;
                    Toast.makeText(getApplicationContext(), "Tap again for a hint!", Toast.LENGTH_SHORT).show();

                    view.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View V){


                            String hint_text=hint_for_board[board[current_position]-1];
                            Toast.makeText(getApplicationContext(),hint_text, Toast.LENGTH_SHORT).show();

                        }

                    });



                }
            }
        });




        menuView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //when you click
                //String word_from_menu;
                if (grid_cell_clicked==true) {
                    menu_cell_clicked_position=position;
                    received_text = (String) menu_adapter.getItem(position);
                    wordListSudokuTable[board_cell_clicked_position]=received_text;

                    adapter.notifyDataSetChanged();
                    solvable_board[board_cell_clicked_position]=menu_cell_clicked_position+1;



                    //bebug here!!
                    //String solvable_board_toast= String.valueOf(solvable_board[board_cell_clicked_position]);
                    //String pos =String.valueOf(board_cell_clicked_position);
                    //String finaly="value is "+solvable_board_toast+" and position is "+pos;
                    //Toast.makeText(getApplicationContext(), finaly, Toast.LENGTH_SHORT).show();

                }
                else //board_cell_clicked_position=-100
                {
                    String click_puzzle_cell="Choose empty cell first!";
                    Toast.makeText(getApplicationContext(), click_puzzle_cell, Toast.LENGTH_SHORT).show();
                }
            }
        });


        final Button checkBoard= (Button) findViewById(R.id.checkBoard);
        checkBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                board_checker checkBoard_object= new board_checker(solvable_board);

                boolean isCorrect;

                isCorrect = checkBoard_object.checker();


                //int first_cell_of_board=checkBoard_object.getSolvedBoard();
                //String cell=String.valueOf(first_cell_of_board);
                //Toast.makeText(MainActivity.this, cell, Toast.LENGTH_SHORT).show();

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


                //String solvable_board_toast= String.valueOf(solvable_board);

                //Toast.makeText(getApplicationContext(), solvable_board_toast, Toast.LENGTH_SHORT).show();


            }
        });

    }

    //OUTSIDE OF ON CREATE FUNCTION
    private void setWordList(int caseNumber, int LC_enabled){
        // CASE NUMBER =1 --> LANGUAGE MODE = ENGLISH TO FRENCH
        if(caseNumber == 1){
            if(LC_enabled==0) { //L.C. mode OFF
                wordListSudokuTable = data_object.generate_get_grid_English();
                wordListKeyboard = data_object.getMenu_list_French();
                hint_for_board=data_object.getMenu_list_French();
            }
            else{ //L.C. MODE ON -- GRID WITH NUMBERS
                wordListSudokuTable = data_object.generate_LCmodeGrid();
                wordListKeyboard = data_object.getMenu_list_French();
                hint_for_board=data_object.getMenu_list_French();
            }
        }
        // CASE NUMBER =2 --> LANGUAGE MODE = FRENCH TO ENGLISH
        else{
            if(LC_enabled==0) { //L.C. mode OFF
                wordListSudokuTable = data_object.generate_get_grid_French();
                wordListKeyboard = data_object.getMenu_list_English();
                hint_for_board=data_object.getMenu_list_English();
            }
            else{//L.C. MODE ON -- GRID WITH NUMBERS
                wordListSudokuTable = data_object.generate_LCmodeGrid();
                wordListKeyboard = data_object.getMenu_list_English();
                hint_for_board=data_object.getMenu_list_English();
            }

        }
    }
    private void goSelect() {
        Intent goSelect = new Intent(this, SelectLanguageMode.class);
        startActivity(goSelect);
    }

}
