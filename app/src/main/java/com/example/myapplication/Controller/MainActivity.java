package com.example.myapplication.Controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;

import com.example.myapplication.Model.boards_and_menu_data;
import com.example.myapplication.R;
import com.example.myapplication.Model.board_checker;

import java.util.Locale;

//import static com.bignerdranch.android.trial2.R.layout.cell_layout_after_click;
//position of arrayGrid starts from 0.
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    //text to speech var
    private TextToSpeech tFR;

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
    public int board[]=new int[81];
    private int solvable_board[]=new int[81];
    private int board_tracker[]=new int[81];
//    private ArrayList<String> solvable_board;

    //object for checker. input: solved number board and number of rows and columns
    //private board_checker checkBoard_object= new board_checker(solvable_board,9,9);

    private String wordListKeyboard[];
    private String wordListSudokuTable[];

    //save state variable
    private static final String KEY_WORD = "word";
    private static final String KEY_CELL = "selected_cell";
    private static final String KEY_BOARD = "solvable_board";
    private static final String KEY_COLOUR = "cell_colour";
    private int mWord[];
    private int mColour[];
    private int mCell[];
    private int mFilled[];


    private String hint_for_board[];
    private String listFrenchWords[]; // for L.C. mode

    //object which gives filled with words sudoku grid and menu depending on chosen language

    //default values
    //not loaded
    private String[] mMenu_list_English={"pink", "blue", "red", "green", "grey", "peach", "pear", "plum", "fig"};
    private String[] mMenu_list_French={"rose", "bleu", "rouge", "vert", "gris", "pêche", "poire", "prune", "figue"};


    //this is for file data communication
    //String[][] array_of_arrays = new String[20][2];
    String[]  english_data= new String[9];
    String[]   french_data =new String[9];
    //String[] values =new String[2];
    //String[] recieved_data=new String[20];


    private boards_and_menu_data data_object= new boards_and_menu_data();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "entered onCreate");

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        int load_mode_choose=0;
        load_mode_choose=pref.getInt("load_mode_chose", 0);


        Intent mode = getIntent();
        int langMode = mode.getIntExtra("language", 0);
        final int LC_enabled = mode.getIntExtra("modeLC", 0);
        //int load_or_keep=mode.getIntExtra("mode_load_old",100);

        if(load_mode_choose==100) {

            data_object.setMenu_list_English(mMenu_list_English);
            data_object.setMenu_list_French(mMenu_list_French);
        }

        if(load_mode_choose==200)
        {
            //it can be recieved as an intent
            int chapter_number=1;
           String recieved_string=pref.getString("chapter "+chapter_number,"no");
                //recieved_data[i]=pref.getString("chapter "+chapter_number+" line number is "+i, "no");
            set_data_recived_from_file(recieved_string);
        }

        setWordList(langMode, LC_enabled);
        board=data_object.getNumber_board();
        solvable_board=data_object.getSolvable_board();

        // text-to-speech (i.e. Listening Comprehension) -- setting up the speaking feature
        tFR = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != TextToSpeech.ERROR){
                    tFR.setLanguage(Locale.CANADA_FRENCH);
                }
            }
        });

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

////        saving state for rotation
//        wordAdapter = new WordAdapter();    // Create empty adapter
//        if (savedInstanceState != null) {
//            ArrayList<String> items = savedInstanceState.getInt(KEY_WORD);
//            wordAdapter.setItems(items);
//        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l){
                String toast_fill_cell="Click on a menu to fill empty cell!";
                //if a cell has no content then a user is asked to fill the cell
                if (board[position]==0)
                {
                    //stores position of green clicked cells
                    board_cell_clicked_position=position;

                    board_tracker[board_cell_clicked_position] = position;

                    grid_cell_clicked=true;
                    Toast.makeText(getApplicationContext(), toast_fill_cell, Toast.LENGTH_SHORT).show();
                    TextView v = (TextView) view;
                    v.setBackgroundResource(R.drawable.cell_shape_after_click);

                }

                else
                {
                    final int current_position=position;
                    Toast.makeText(getApplicationContext(), "Tap again for a hint!", Toast.LENGTH_SHORT).show();
                    if(LC_enabled ==1) {
                        //IF listening comprehension mode IS ENABLED, execute TEXT TO SPEECH when we TAP a pre-filled cell (with a number) for the FIRST TIME.
                        String speakFrenchWord = listFrenchWords[board[current_position]-1];
                        tFR.speak(speakFrenchWord, TextToSpeech.QUEUE_FLUSH, null);
                    }

                    view.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View V){
                            //HINT feature -- toast with word from menu @ bottom which corresponds to selected cell
                            String hint_text=hint_for_board[board[current_position]-1];
                            Toast.makeText(getApplicationContext(),hint_text, Toast.LENGTH_SHORT).show();
                            //any subsequent clicks on a pre-filled cell (with #) will still pronounce word in French!
                            if(LC_enabled ==1) {
                                //IF listening comprehension mode is enabled, execute TEXT TO SPEECH for all subsequent TAPS to pre-filled cells
                                String speakFrenchWord = listFrenchWords[board[current_position]-1];
                                tFR.speak(speakFrenchWord, TextToSpeech.QUEUE_FLUSH, null);
                            }
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

                    //array stores all user word inputs in the form of numbers
                    solvable_board[board_cell_clicked_position]=menu_cell_clicked_position+1;

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

        if (savedInstanceState != null) {
            mCell = savedInstanceState.getIntArray(KEY_CELL);   //clicked cells by user
            mWord = savedInstanceState.getIntArray(KEY_WORD);   //words on board including newly added by user
            mFilled = savedInstanceState.getIntArray(KEY_BOARD);    //board values including newly added by user
        }

    }

    //OUTSIDE OF ON CREATE FUNCTION

    //text to speech related:
    /*this deals with the case where system is about to resume previous activity
    -- need to ensure things are not consuming resources (ex. CPU)*/
    public void onPause(){
        //shut down text to speech
        if(tFR != null){
            tFR.stop(); // stops the speech
            tFR.shutdown(); // releases resources being used by TextToSpeech engine
        }
        super.onPause();
    }


    // SET-UP GRIDS based on the Language Mode selected as well as Listening Comprehension mode (enabled or disabled)
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
                listFrenchWords = data_object.getMenu_list_French();
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
                listFrenchWords = data_object.getMenu_list_French();
            }

        }
    }
    private void goSelect() {
        Intent goSelect = new Intent(this, SelectLanguageMode.class);
        startActivity(goSelect);
    }

    public void set_data_recived_from_file(String data_string)
    {
        //for (int i=0; i<9; i++)
        //{
            //values = data_array[i].split(",");
            //array_of_arrays[i]=values;
           // english_data[i]=array_of_arrays[i][0];
         //   french_data[i]=array_of_arrays[i][1];
       // }

        String[] array=data_string.split(",");
        int array_size=array.length;

        int k=0;
        int m=0;
        int h=1;
        while(h<=array_size)
        {
            english_data[k]=array[m];
            french_data[k]=array[h];
            m=m+2;
            k=k+1;
            h=h+2;
        }
        data_object.setMenu_list_French(english_data);
        data_object.setMenu_list_English(french_data);
    }


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putIntArray(KEY_CELL, board_tracker);
        savedInstanceState.putStringArray(KEY_WORD, wordListSudokuTable);
        savedInstanceState.putIntArray(KEY_BOARD, solvable_board);

//        for (int i =0; i < wordListSudokuTable.length; i++ ) {
//            savedInstanceState.putIntArray(KEY_CELL, board_tracker);
//            Log.i(TAG, "board_cell_clicked" + " " + board_tracker[i]);
//        }
//
//        for (int i =0; i < wordListSudokuTable.length; i++ ) {
//            savedInstanceState.putIntArray(KEY_BOARD, solvable_board);
//            Log.i(TAG, "solvable_board" + " " + solvable_board[i]);
//        }
//
//        for (int i =0; i < wordListSudokuTable.length; i++ ) {
//            savedInstanceState.putStringArray(KEY_WORD, wordListSudokuTable);
//            Log.i(TAG, "word_list_Sudoku_table" + " " + wordListSudokuTable[i]);
//        }

//        savedInstanceState.putInt(KEY_WORD, menu_cell_clicked_position);
//        Log.i(TAG, "menu_cell_clicked_pos" + " " + menu_cell_clicked_position);
//        savedInstanceState.putBoolean("test", menu_cell_clicked);
//        Log.i(TAG, "menu_cell_clicked" + " " + menu_cell_clicked);
    }

}
