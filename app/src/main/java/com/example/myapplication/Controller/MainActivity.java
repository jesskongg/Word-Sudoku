package com.example.myapplication.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.service.autofill.UserData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;

import com.example.myapplication.Model.ChooseWords;
import com.example.myapplication.Model.Userdata;
import com.example.myapplication.Model.boards_and_menu_data;
import com.example.myapplication.R;
import com.example.myapplication.Model.board_checker;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;


//import static com.bignerdranch.android.trial2.R.layout.cell_layout_after_click;
//position of arrayGrid starts from 0.
public class MainActivity extends AppCompatActivity {
    // SAVING STATE WHEN DEVICE CONFIGURATION CHANGES (DUE TO ROTATION OF DEVICE)
    private static final String TAG = "MainActivity";
    //text to speech var
    private TextToSpeech tFR;
    int langMode;
    int LC_enabled;

    //save state variables (device configuration changes)
    private static final String KEY_WORDS = "word";
    private static final String KEY_CELLS = "selected_cell";
    private static final String KEY_SOLVABLEBOARD = "solvable_board";
    private static final String KEY_BOARD = "board";
    private static final String KEY_COLOUR = "cell_colour";

    //puzzle variables
    private GridView gridView;
    private TextView textView;

    //menu variables
    private GridView menuView;
    private TextView textMenu;

    //variables for grid-menu communication
    private String received_text = " ";
    private int board_cell_clicked_position;
    private int menu_cell_clicked_position;
    private boolean menu_cell_clicked = false;
    private boolean grid_cell_clicked = false;

    private ImageButton backSelect;

    //number board variables

    public int board[];
    private int solvable_board[];
    private int board_tracker[];

    private String wordListKeyboard[];
    private String wordListSudokuTable[];

    private String hint_for_board[];
    private String listFrenchWords[]; // for L.C. mode

    private String[] mMenu_list_English = {"pink", "blue", "red", "green", "grey", "peach", "pear", "plum", "fig", "en1", "en2","en3"};
    private String[] mMenu_list_French = {"rose", "bleu", "rouge", "vert", "gris", "pêche", "poire", "prune", "figue", "fr1","fr2","fr3"};

    //private boards_and_menu_data data_object = new boards_and_menu_data();

    int load_mode_choose;

    public static final String MyPREFERENCES = "Sudoku_pref" ;
    public static final String Length = "gridLength";
    public static final String SubgridLength = "suggridLength";
    public static final String SubgridWidth = "subgridWidth";
    SharedPreferences sharedpreferences_for_grid_var;



    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putIntArray(KEY_CELLS, board_tracker);
        savedInstanceState.putStringArray(KEY_WORDS, wordListSudokuTable);
        savedInstanceState.putIntArray(KEY_SOLVABLEBOARD, solvable_board);
        savedInstanceState.putIntArray(KEY_BOARD, board);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "entered onCreate");

        sharedpreferences_for_grid_var = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor_grid_var = sharedpreferences_for_grid_var.edit();
        final int gridLength=sharedpreferences_for_grid_var.getInt(Length, 100);

        boards_and_menu_data data_object = new boards_and_menu_data(gridLength);

        Intent mode = getIntent();
        final int langMode = mode.getIntExtra("language", 0);
        final int LC_enabled = mode.getIntExtra("modeLC", 0);
        //int load_or_keep=mode.getIntExtra("mode_load_old",100);

        //RECEIVE DATA FROM A FILE OR LOAD DEFAULT BOARD
        set_default_or_loaded(data_object);

        //if newGameFlag == 1, start a new game.
        int newGameFlag = mode.getIntExtra("newGame", 1);
        if(newGameFlag == 0) {
            Userdata data = new Userdata();
            try {
                //Load data from SQL, if it failed, which means the app is used first time, and this step will be skipped.
                board = data.getNumber_board(MainActivity.this);
                solvable_board = data.getSolvable_board(MainActivity.this);
                wordListSudokuTable = data.getWordsTable(MainActivity.this);
                wordListKeyboard = data.getKeyBoard(MainActivity.this);
                hint_for_board = data.getKeyBoard(MainActivity.this);
                listFrenchWords = data.getListFrenchWords(MainActivity.this);
//                data_object.setNumber_board(data.getNumber_board(MainActivity.this));
//                data_object.setSolvable_board(data.getSolvable_board(MainActivity.this));
//                data_object.setMenu_list_English(data.getEnglish(MainActivity.this));
//                data_object.setMenu_list_French(data.getFrench(MainActivity.this));
//                langMode = data.getLangMode(MainActivity.this);
//                LC_enabled = data.getLC(MainActivity.this);
            } catch (SQLiteException ex) {
                //same as new game
                board = data_object.getNumber_board();
                solvable_board = data_object.getSolvable_board();
                setWordList(langMode, LC_enabled);
            }
        }
        else{
            board = data_object.getNumber_board();
            solvable_board = data_object.getSolvable_board();
            setWordList(langMode, LC_enabled);
        }



        set_listening_comprehension(data_object);

        //grid puzzle
        gridView = (GridView) findViewById(R.id.grid);
        gridView.setNumColumns(gridLength);
        textView = (TextView) findViewById(R.id.textView);

        //menu grid
        menuView = (GridView) findViewById(R.id.grid_menu);
        textMenu = (TextView) findViewById(R.id.menu_cell);

        //SAVE STATE WHEN DEVICE CONFIGURATION CHANGES (EX. ORIENTATION DUE TO ROTATION)
        if (savedInstanceState != null) {
            wordListSudokuTable = savedInstanceState.getStringArray(KEY_WORDS);
            solvable_board = savedInstanceState.getIntArray(KEY_SOLVABLEBOARD);
            board = savedInstanceState.getIntArray(KEY_BOARD);
        }

        //adapter for puzzle grid
        final ArrayAdapter adapter;

        final DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        adapter = new ArrayAdapter(this, R.layout.cell_layout, wordListSudokuTable){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                if (board[position]==0 && solvable_board[position]!=0)
                {
                    view.setBackgroundResource(R.drawable.cell_shape_after_click);
                }

                int width = (displayMetrics.widthPixels)/gridLength;

                //hieght is a bit less than width to allow for space for menu and buttons
                int height=width-18;
                //=(displayMetrics.heightPixels)/(columns_number*2-4);
                view.setLayoutParams(new GridView.LayoutParams(width, height));
                return view;
            }
        };


        gridView.setAdapter(adapter);
        //adapter for menu
        final ArrayAdapter menu_adapter;
        menu_adapter = new ArrayAdapter(this, R.layout.cell_menu_layout, wordListKeyboard);
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
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String toast_fill_cell = "Click on a menu to fill empty cell!";
                //if a cell has no content then a user is asked to fill the cell
                if (board[position] == 0) {
                    //stores position of green clicked cells
                    board_cell_clicked_position = position;
                    board_tracker[board_cell_clicked_position] = position;
                    grid_cell_clicked = true;
                    Toast.makeText(getApplicationContext(), toast_fill_cell, Toast.LENGTH_SHORT).show();
                    view.setBackgroundResource(R.drawable.cell_shape_after_click);

                } else {
                    final int current_position = position;
                    Toast.makeText(getApplicationContext(), "Tap again for a hint!", Toast.LENGTH_SHORT).show();
                    if (LC_enabled == 1) {
                        //IF listening comprehension mode IS ENABLED, execute TEXT TO SPEECH when we TAP a pre-filled cell (with a number) for the FIRST TIME.
                        String speakFrenchWord = listFrenchWords[board[current_position] - 1];
                        tFR.speak(speakFrenchWord, TextToSpeech.QUEUE_FLUSH, null);
                    }

                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View V) {
                            //HINT feature -- toast with word from menu @ bottom which corresponds to selected cell
                            String hint_text = hint_for_board[board[current_position] - 1];
                            Toast.makeText(getApplicationContext(), hint_text, Toast.LENGTH_SHORT).show();

                            Userdata data = new Userdata();
                            String frenchWord = data_object.getMenu_list_French()[board[current_position]-1];
                            data.record_hint_times(frenchWord, MainActivity.this);

                            //any subsequent clicks on a pre-filled cell (with #) will still pronounce word in French!
                            if (LC_enabled == 1) {
                                //IF listening comprehension mode is enabled, execute TEXT TO SPEECH for all subsequent TAPS to pre-filled cells
                                String speakFrenchWord = listFrenchWords[board[current_position] - 1];
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
                if (grid_cell_clicked == true) {
                    menu_cell_clicked_position = position;
                    received_text = (String) menu_adapter.getItem(position);
                    wordListSudokuTable[board_cell_clicked_position] = received_text;
                    adapter.notifyDataSetChanged();
                    //array stores all user word inputs in the form of numbers
                    solvable_board[board_cell_clicked_position] = menu_cell_clicked_position + 1;

                    Userdata data = new Userdata();
                    data.saveData(board, solvable_board, wordListSudokuTable, wordListKeyboard, listFrenchWords, MainActivity.this);

                } else //board_cell_clicked_position=-100
                {
                    String click_puzzle_cell = "Choose empty cell first!";
                    Toast.makeText(getApplicationContext(), click_puzzle_cell, Toast.LENGTH_SHORT).show();
                }
            }
        });

        final Button checkBoard = (Button) findViewById(R.id.checkBoard);
        checkBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                board_checker checkBoard_object = new board_checker(solvable_board);
                boolean isCorrect;
                isCorrect = checkBoard_object.checker();

                if (isCorrect == true) {
                    Toast.makeText(MainActivity.this,
                            R.string.boardTrue,
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this,
                            R.string.boardFalse,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //OUTSIDE OF ON CREATE FUNCTION

    //text to speech related:
    /*this deals with the case where system is about to resume previous activity
    -- need to ensure things are not consuming resources (ex. CPU)*/
    public void onPause() {
        //shut down text to speech
        if (tFR != null) {
            tFR.stop(); // stops the speech
            tFR.shutdown(); // releases resources being used by TextToSpeech engine
        }
        super.onPause();
    }


    public void set_listening_comprehension(boards_and_menu_data sudoku_object)
    {
        //listening comprehension
        Intent mode = getIntent();
        langMode = mode.getIntExtra("language", 0);
        LC_enabled = mode.getIntExtra("modeLC", 0);
        //int load_or_keep=mode.getIntExtra("mode_load_old",100);

        setWordList(langMode, LC_enabled, sudoku_object);

        // text-to-speech (i.e. Listening Comprehension) -- setting up the speaking feature
        tFR = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    tFR.setLanguage(Locale.CANADA_FRENCH);
                }
            }
        });
    }
    // SET-UP GRIDS based on the Language Mode selected as well as Listening Comprehension mode (enabled or disabled)
    private void setWordList(int caseNumber, int LC_enabled, boards_and_menu_data sudoku_object) {
        // CASE NUMBER =1 --> LANGUAGE MODE = ENGLISH TO FRENCH
        if (caseNumber == 1) {
            if (LC_enabled == 0) { //L.C. mode OFF
                wordListSudokuTable = sudoku_object.generate_get_grid_English();
                wordListKeyboard = sudoku_object.getMenu_list_French();
                hint_for_board = sudoku_object.getMenu_list_French();
            } else { //L.C. MODE ON -- GRID WITH NUMBERS
                wordListSudokuTable = sudoku_object.generate_LCmodeGrid();
                wordListKeyboard = sudoku_object.getMenu_list_French();
                hint_for_board = sudoku_object.getMenu_list_French();
                listFrenchWords = sudoku_object.getMenu_list_French();
            }
        }
        // CASE NUMBER =2 --> LANGUAGE MODE = FRENCH TO ENGLISH
        else {
            if (LC_enabled == 0) { //L.C. mode OFF
                wordListSudokuTable = sudoku_object.generate_get_grid_French();
                wordListKeyboard = sudoku_object.getMenu_list_English();
                hint_for_board = sudoku_object.getMenu_list_English();
            } else {//L.C. MODE ON -- GRID WITH NUMBERS
                wordListSudokuTable = sudoku_object.generate_LCmodeGrid();
                wordListKeyboard = sudoku_object.getMenu_list_English();
                hint_for_board = sudoku_object.getMenu_list_English();
                listFrenchWords = sudoku_object.getMenu_list_French();
            }

        }
    }

    private void goSelect() {
        Intent goSelect = new Intent(this, SelectLanguageMode.class);
        startActivity(goSelect);
    }

    public void set_default_or_loaded(boards_and_menu_data sudoku_object)
    {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        load_mode_choose = pref.getInt("load_mode_chose", 0);


        if (load_mode_choose == 100) {

            String[] mMenu_list_English_cut=new String[sudoku_object.getNumber_of_columns()];
            mMenu_list_English_cut=Arrays.copyOfRange(mMenu_list_English, 0, sudoku_object.getNumber_of_columns());

            String[] mMenu_list_French_cut= new String[sudoku_object.getNumber_of_columns()];
            mMenu_list_French_cut=Arrays.copyOfRange(mMenu_list_French, 0, sudoku_object.getNumber_of_columns() );

            sudoku_object.setMenu_list_English(mMenu_list_English_cut);
            sudoku_object.setMenu_list_French(mMenu_list_French_cut);
        }

        if (load_mode_choose == 200) {
            //it can be recieved as an intent
            //it can be recieved as an intent
            int chapter_number = 1;
            String recieved_string=null;
            recieved_string = pref.getString("chapter " + chapter_number, "no");
            //recieved_data[i]=pref.getString("chapter "+chapter_number+" line number is "+i, "no");
            set_data_recived_from_file(recieved_string, sudoku_object);
        }
    }




    public void set_data_recived_from_file(String data_string, boards_and_menu_data sudoku_object) {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        int size_of_each_array=pref.getInt("line_counter",0);
        //int size_of_each_array=16;

        String[] array = data_string.split(",");
        int array_size = array.length;

        String[] english_data=new String[array_size];
        String[] french_data=new String[array_size];

        //while loop is to split one big array of data into two arrays with french and english words separately.
        int k = 0;
        int m = 0;
        int h = 1;
        while (h <= array_size) {
            english_data[k] = array[m];
            french_data[k] = array[h];
            m = m + 2;
            k = k + 1;
            h = h + 2;
        }

        String[] english_long_array=new String[size_of_each_array];
        String[] french_long_array=new String[size_of_each_array];

        english_long_array=Arrays.copyOfRange(english_data, 0, size_of_each_array);
        //here I chnaged variable from french data to english data
        french_long_array=Arrays.copyOfRange(french_data, 0, size_of_each_array);


        //convert arrays into lists
        List<String> english_list = Arrays.asList(english_long_array);
        List<String> french_list=Arrays.asList(french_long_array);

        //shuffle lists
//        Collections.shuffle(english_list);
//        Collections.shuffle(french_list);

        //convert arrays back to lists
        String[] converted_english=new String[size_of_each_array];
        String[] converted_french=new String[size_of_each_array];

        //converted_english= list.toArray(english_list);
        for( int i=0; i<size_of_each_array; i++)
        {
            converted_english[i]=english_list.get(i);
            converted_french[i]=french_list.get(i);
        }

        String[] english_data_clean=new String[sudoku_object.getNumber_of_columns()];
        String[] french_data_clean=new String[sudoku_object.getNumber_of_columns()];

        //for tracking words which are difficult to recognize
        //choosing words according to hint times.

        Userdata data = new Userdata();
        HashMap<String, Integer> map = data.getHashMap(MainActivity.this);
        ChooseWords chooseWords = new ChooseWords();
        french_data_clean = chooseWords.chooseFrench(map, converted_french);
        english_data_clean = chooseWords.chooseEnglish(french_data_clean, converted_english, converted_french);

        //now take the first 9 elements of arrays

//        english_data_clean=Arrays.copyOfRange(converted_english, 0, 9);
//        french_data_clean=Arrays.copyOfRange(converted_french, 0, 9);


        //now paste clean arrays of size 9 into the menu
        sudoku_object.setMenu_list_French(english_data_clean);
        sudoku_object.setMenu_list_English(french_data_clean);
    }
}