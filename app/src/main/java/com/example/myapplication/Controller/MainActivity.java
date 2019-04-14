package com.example.myapplication.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.service.autofill.UserData;
import android.support.annotation.ColorInt;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;

import com.example.myapplication.Model.ChooseWords;
import com.example.myapplication.Model.Userdata;
import com.example.myapplication.Model.boards_and_menu_data;
import com.example.myapplication.R;
import com.example.myapplication.Model.board_checker;
import com.example.myapplication.SquareGrid;

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

    private String[] mMenu_list_English = {"pink", "blue", "red", "green", "grey", "peach", "pear", "plum", "fig", "egg", "black","white"};
    private String[] mMenu_list_French = {"rose", "bleu", "rouge", "vert", "gris", "pêche", "poire", "prune", "figue", "oeuf","noir","blanc"};

    //private boards_and_menu_data data_object = new boards_and_menu_data();

    private int load_mode_choose;
    private int gridLength;
    private int subLen;
    private int subWid;


    static final String MyPREFERENCES = "Sudoku_pref" ;
    static final String Length = "gridLength";
    static final String SubgridLength = "subgridLength";
    static final String SubgridWidth = "subgridWidth";
    SharedPreferences sharedpreferences_for_grid_var;

    static final String bonusFor9x9="Bonus9x9";
    static final String bonusFor4x4="Bonus4x4";
    static final String bonusFor6x6="Bonus6x6";
    static final String bonusFor12x12="Bonus12x12";
    static final String totalBonusKey="TotalBonus";


    int bonus4x4=0;
    int bonus6x6=0;
    int bonus9x9=0;
    int bonus12x12=0;
    int totalBonus=0;

    //0 means initially you dont have a point
    //1 means you alredy recived a point and across rotations you should not get that point


    public int haveBonus=0;


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putIntArray(KEY_CELLS, board_tracker);
        savedInstanceState.putStringArray(KEY_WORDS, wordListSudokuTable);
        savedInstanceState.putIntArray(KEY_SOLVABLEBOARD, solvable_board);
        savedInstanceState.putIntArray(KEY_BOARD, board);
        savedInstanceState.putInt("haveBonus", haveBonus);
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
//            setContentView(R.layout.activity_main);
//        }
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            setContentView(R.layout.activity_main);
//        }
//    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "entered onCreate");

        sharedpreferences_for_grid_var = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor_grid_var = sharedpreferences_for_grid_var.edit();
        gridLength=sharedpreferences_for_grid_var.getInt(Length, 9);
        subLen = sharedpreferences_for_grid_var.getInt(SubgridLength, 3);
        subWid = sharedpreferences_for_grid_var.getInt(SubgridWidth, 3);

        //if newGameFlag == 1, start a new game.
        Intent mode = getIntent();
        int newGameFlag = mode.getIntExtra("newGame", 1);
        if(newGameFlag == 0){
            try{
                Userdata data = new Userdata();
                int[] size = data.getLenth(MainActivity.this);
                gridLength = size[0];
                subLen = size[1];
                subWid = size[2];
            }
            catch (SQLiteException ex){
            }
        }

        final boards_and_menu_data data_object = new boards_and_menu_data(gridLength, subLen, subWid);


        //RECEIVE DATA FROM A FILE OR LOAD DEFAULT BOARD
        set_default_or_loaded(data_object);
        board = data_object.getNumber_board();
        solvable_board = data_object.getSolvable_board();

        set_listening_comprehension(data_object);

        if(newGameFlag == 0) {
            try {
                //Load data from SQL, if it failed, which means the app is used first time, and this step will be skipped.
                //NOTICE: just recover variables in MainActivity. Variables in boards_and_menu_data are not changed.
                continue_game();
            } catch (SQLiteException ex) {
            }
        }

        Userdata data = new Userdata();
        data.saveData(gridLength, board, solvable_board, wordListSudokuTable, wordListKeyboard, hint_for_board, listFrenchWords, MainActivity.this);
        data.saveLC(LC_enabled, MainActivity.this);

        //grid puzzle
        gridView = (GridView) findViewById(R.id.grid);
        gridView.setNumColumns(gridLength);
        textView = (TextView) findViewById(R.id.textView);

        //menu grid
        textMenu = findViewById(R.id.menu_cell);
        menuView = (GridView) findViewById(R.id.grid_menu);
        if (gridLength == 4 || gridLength == 6) {
            menuView.setNumColumns(2);
            menuView.setColumnWidth(1);
        }
        if (gridLength == 12) {
            menuView.setNumColumns(4);
        }



        //SAVE STATE WHEN DEVICE CONFIGURATION CHANGES (EX. ORIENTATION DUE TO ROTATION)
        if (savedInstanceState != null) {
            wordListSudokuTable = savedInstanceState.getStringArray(KEY_WORDS);
            solvable_board = savedInstanceState.getIntArray(KEY_SOLVABLEBOARD);
            board = savedInstanceState.getIntArray(KEY_BOARD);
            haveBonus=savedInstanceState.getInt("haveBonus");
        }





        //adapter for puzzle grid
        final ArrayAdapter<String> adapter;

        final DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        adapter = new ArrayAdapter<String>(this, R.layout.cell_layout, wordListSudokuTable){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);


                if (shaded_rectangle(gridLength,position)==true){
                    //dark grey
                    //view.setBackgroundResource(R.drawable.cell_shape_aftre_click_shaded);
                    view.setBackgroundResource(R.drawable.cell_shape_rectangles);
                }


                if (board[position]==0 && solvable_board[position]!=0)
                {
                    if (shaded_rectangle(gridLength, position)==true) {
                        //color into darl grey if it's clicked
                        view.setBackgroundResource(R.drawable.cell_shape_aftre_click_shaded);
                    }



                    else
                    {
                        //color into blue if ots clicked
                        view.setBackgroundResource(R.drawable.cell_shape_after_click);
                    }
                }

















//              For Final Iteration
//                TextView grid_text = (TextView) view;
//                if (gridLength == 4 || gridLength == 6) {
//                    grid_text.setTextSize(30);
//                }
//                if (gridLength == 9) {
//                    grid_text.setTextSize(20);
//                }
//                if (gridLength == 12) {
//                    grid_text.setTextSize(14);
//                }

                int width = (displayMetrics.widthPixels);
                int height = (displayMetrics.heightPixels);

                //variables for portrait mode cell dimensions
                int gridWidth = width/(gridLength);
                int gridHeight = gridWidth;

                //variables for landscape mode cell dimensions
                int gridHeightLand = height/(gridLength+(gridLength/4));
                int gridWidthLand = gridHeightLand+20;

                //adjusting grid height and width to accommodate 4x4 and 6x6 grids
                if (gridLength == 4 || gridLength == 6) {
                    gridWidth = width/(gridLength);
                    gridHeight = gridWidth-(gridLength*5);

                    gridHeightLand = height/(gridLength+(gridLength/3));
                    gridWidthLand = gridHeightLand+29;
                }

//                if (gridLength == 6) {
//                    gridWidth = width/(gridLength);
//                    gridHeight = gridWidth-(gridLength*3);
//
//                    gridHeightLand = height/(gridLength+(gridLength/3));
//                    gridWidthLand = gridHeightLand+29;
//                }

                int orientation = getResources().getConfiguration().orientation;
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    // In landscape
                    view.setLayoutParams(new GridView.LayoutParams(gridWidthLand, gridHeightLand));
//                    if (gridLength == 4){
//                        view.setLayoutParams(new GridView.LayoutParams(gridWidthLand, gridHeightLand-20));
//                    }
                } else {
                    // In portrait
                    view.setLayoutParams(new GridView.LayoutParams(gridWidth,gridHeight));
                }

                return view;
            }
        };

        gridView.setAdapter(adapter);

        //adapter for menu grid
        final ArrayAdapter<String> menu_adapter;
        menu_adapter = new ArrayAdapter<String>(this, R.layout.cell_menu_layout, wordListKeyboard) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View menuView = super.getView(position, convertView, parent);

                int width = (displayMetrics.widthPixels);
                int height = (displayMetrics.heightPixels);

                //variables for portrait mode cell dimensions
                int gridWidth = width/(gridLength/2);
                int gridHeight = width/(gridLength*2);

                //variables for landscape mode cell dimensions
                int gridHeightLand = height/(gridLength+(gridLength/6));
                int gridWidthLand = gridHeightLand+(gridHeightLand/2);


                if (gridLength == 4) {
                    gridWidth = width / (gridLength);
                    gridHeight = width / (gridLength * 2);
                    gridHeightLand = height/(gridLength);
                    gridWidthLand = gridHeightLand;
                }

                if (gridLength == 12) {
                    gridWidth = width / (gridLength/2);
                    gridHeight = width / (gridLength);
                    gridHeightLand = height/(gridLength);
                    gridWidthLand = gridHeightLand + (gridLength*2);
                }

                int orientation = getResources().getConfiguration().orientation;
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    // In landscape
                    menuView.setLayoutParams(new GridView.LayoutParams(gridWidthLand, gridHeightLand));
                } else {
                    // In portrait
                    menuView.setLayoutParams(new GridView.LayoutParams(gridWidth,gridHeight));
                }

                return menuView;
            }
        };

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
                    //board_tracker[board_cell_clicked_position] = position;
                    grid_cell_clicked = true;
                    Toast.makeText(getApplicationContext(), toast_fill_cell, Toast.LENGTH_SHORT).show();

                    if (shaded_rectangle(gridLength, position)==true) {
                        view.setBackgroundResource(R.drawable.cell_shape_aftre_click_shaded);
                    }

                    else
                    {
                        view.setBackgroundResource(R.drawable.cell_shape_after_click);
                    }



                    //view.setBackgroundResource(R.drawable.cell_shape_after_click);

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
                            String frenchWord = listFrenchWords[board[current_position]-1];
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
                    menu_cell_clicked_position = data_object.getPosition(hint_for_board, received_text);
                    solvable_board[board_cell_clicked_position] = menu_cell_clicked_position + 1;

                    Userdata data = new Userdata();
                    data.saveData(gridLength, board, solvable_board, wordListSudokuTable, wordListKeyboard, hint_for_board, listFrenchWords, MainActivity.this);

                } else //board_cell_clicked_position=-100
                {
                    String click_puzzle_cell = "Choose empty cell first!";
                    Toast.makeText(getApplicationContext(), click_puzzle_cell, Toast.LENGTH_SHORT).show();
                }
            }
        });
        //int my_bonus=haveBonus;
        //boolean tablet;
        Button checkBoard = (Button) findViewById(R.id.checkBoard);
        checkBoard.setOnClickListener(new View.OnClickListener() {
            //int have_bonus=0;
            @Override
            public void onClick(View v) {
                board_checker checkBoard_object = new board_checker(solvable_board);
                boolean isCorrect;
                isCorrect = checkBoard_object.checker(gridLength, subLen, subWid);

               // boolean got_bonus=false;
                if (isCorrect == true) {
                    Toast.makeText(MainActivity.this,
                            R.string.boardTrue,
                            Toast.LENGTH_SHORT).show();
                    //boolean is_correct=true;
                    Button closePopup;

                    final PopupWindow popupWindow;
                    ConstraintLayout current_layout;

                    DisplayMetrics metrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(metrics);
                    int width = metrics.widthPixels;
                    int height = metrics.heightPixels;
                    boolean tablet;

                    tablet=isTablet();

                    //PICK PROPER LAYOUT
                    current_layout = findViewById(R.id.mainLayout);
                    int orientation = getResources().getConfiguration().orientation;
                    if (orientation == Configuration.ORIENTATION_LANDSCAPE && tablet==false) {
                        // In landscape
                        current_layout = findViewById(R.id.mainLayout_land);
//                    if (gridLength == 4){
//                        view.setLayoutParams(new GridView.LayoutParams(gridWidthLand, gridHeightLand-20));
//                    }
                    }

                    if (orientation==Configuration.ORIENTATION_PORTRAIT && tablet==true)
                    {
                        current_layout=findViewById(R.id.mainLayout_tablet);
                    }

                    if (orientation==Configuration.ORIENTATION_LANDSCAPE && tablet==true)
                    {
                        current_layout=findViewById(R.id.mainLayout_tablet_land);
                    }


                    if (orientation == Configuration.ORIENTATION_PORTRAIT && tablet==false)
                    {
                        // In portrait
                        current_layout = findViewById(R.id.mainLayout);
                    }
                    //current_layout = findViewById(R.id.mainLayout_land);
                    LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    //we now have a custom view
                    View customView = layoutInflater.inflate(R.layout.bonus_popup_layout,null);
                    //we add a button to it
                    closePopup = (Button) customView.findViewById(R.id.close);
                    //instantiate popup window
                    popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    //chnage pop-up window size
                       //use screen size paramenters to set these parameters.
                    //int width = (displayMetrics.widthPixels);
                    //int height = (displayMetrics.heightPixels);
                    //25 is hard coded but as I understand it doesnt add much to the pop up size
                    //int popupWidth=width/2+width/5;
                    //int popupHeight=width/2+width/20;
                    //popupWindow.setWidth(popupWidth);
                    //popupWindow.setHeight(popupHeight);
                    //String showbonus=Integer.toString(sharedpreferences_for_grid_var.getInt(bonusFor4x4, 0))
                    //((TextView)popupWindow.getContentView().findViewById(R.id.textView3)).setText("Total number of bonuses is "+Integer.toString(sharedpreferences_for_grid_var.getInt(bonusFor4x4, 0)));
                    //location of pop up is center
                    //here it gives me an arror across rotations
                    popupWindow.showAtLocation(current_layout, Gravity.CENTER, 0, 0);
                    //how to determine popup width and hieght????
                    //close the popup window on button click
                    closePopup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });

                    //some variable here across rotations
                    if (gridLength==4 && haveBonus==0 ) {
                        bonus4x4=sharedpreferences_for_grid_var.getInt(bonusFor4x4, 0);
                        bonus4x4=bonus4x4+4;
                        editor_grid_var.putInt(bonusFor4x4, bonus4x4);
                        editor_grid_var.commit();
                        //sharedpreferences_for_grid_var.getInt(bonusFor4x4, 0);
                        haveBonus=haveBonus+1;
                        totalBonus=sharedpreferences_for_grid_var.getInt(totalBonusKey,0);
                        totalBonus=totalBonus+4;
                        editor_grid_var.putInt(totalBonusKey, totalBonus);
                        editor_grid_var.commit();
                    }

                    if (gridLength==4 && haveBonus!=0) {
                        ((TextView) popupWindow.getContentView().findViewById(R.id.textView2)).setText("You earned 4 points!");
                        ((TextView) popupWindow.getContentView().findViewById(R.id.textView3)).setText("Total bonus points: " + Integer.toString(sharedpreferences_for_grid_var.getInt(totalBonusKey, 0)));
                    }

                    //haveBonus=0;

                    if (gridLength==6 && haveBonus==0) {
                        bonus6x6=sharedpreferences_for_grid_var.getInt(bonusFor6x6, 0);
                        bonus6x6=bonus6x6+6;
                        editor_grid_var.putInt(bonusFor6x6, bonus6x6);
                        editor_grid_var.commit();
                        haveBonus=haveBonus+1;

                        totalBonus=sharedpreferences_for_grid_var.getInt(totalBonusKey,0);
                        totalBonus=totalBonus+6;
                        editor_grid_var.putInt(totalBonusKey, totalBonus);
                        editor_grid_var.commit();
                    }


                    if (gridLength==6 && haveBonus!=0) {
                        ((TextView) popupWindow.getContentView().findViewById(R.id.textView2)).setText("You earned 6 points!");
                        ((TextView) popupWindow.getContentView().findViewById(R.id.textView3)).setText("Total bonus points: " + Integer.toString(sharedpreferences_for_grid_var.getInt(totalBonusKey, 0)));
                    }



                    if (gridLength==9 && haveBonus==0) {
                        bonus9x9=sharedpreferences_for_grid_var.getInt(bonusFor9x9, 0);
                        bonus9x9=bonus9x9+9;
                        editor_grid_var.putInt(bonusFor9x9, bonus9x9);
                        editor_grid_var.commit();

                        haveBonus=haveBonus+1;

                        totalBonus=sharedpreferences_for_grid_var.getInt(totalBonusKey,0);
                        totalBonus=totalBonus+9;
                        editor_grid_var.putInt(totalBonusKey, totalBonus);
                        editor_grid_var.commit();
                    }



                    if (gridLength==9 && haveBonus!=0) {
                        ((TextView) popupWindow.getContentView().findViewById(R.id.textView2)).setText("You earned 9 points!");
                        ((TextView) popupWindow.getContentView().findViewById(R.id.textView3)).setText("Total bonus points: " + Integer.toString(sharedpreferences_for_grid_var.getInt(totalBonusKey, 0)));
                    }

                    if (gridLength==12 && haveBonus==0) {
                        bonus12x12=sharedpreferences_for_grid_var.getInt(bonusFor12x12, 0);
                        bonus12x12=bonus12x12+12;
                        editor_grid_var.putInt(bonusFor12x12, bonus12x12);
                        editor_grid_var.commit();

                        haveBonus=haveBonus+1;

                        totalBonus=sharedpreferences_for_grid_var.getInt(totalBonusKey,0);
                        totalBonus=totalBonus+12;
                        editor_grid_var.putInt(totalBonusKey, totalBonus);
                        editor_grid_var.commit();
                    }

                    if (gridLength==12 && haveBonus!=0) {
                        ((TextView) popupWindow.getContentView().findViewById(R.id.textView2)).setText("You earned 12 points!");
                        ((TextView) popupWindow.getContentView().findViewById(R.id.textView3)).setText("Total bonus points: " + Integer.toString(sharedpreferences_for_grid_var.getInt(totalBonusKey, 0)));
                    }

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

    public void continue_game(){
        Userdata data = new Userdata();
        int size[] = data.getLenth(MainActivity.this);
        board = data.getNumber_board(size[0], MainActivity.this);
        solvable_board = data.getSolvable_board(size[0], MainActivity.this);
        wordListSudokuTable = data.getWordsTable(size[0], MainActivity.this);
        wordListKeyboard = data.getKeyBoard(size[0], MainActivity.this);
        hint_for_board = data.getHintBoard(size[0], MainActivity.this);
        listFrenchWords = data.getListFrenchWords(size[0], MainActivity.this);
        LC_enabled = data.getLC(MainActivity.this);
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
                wordListKeyboard = sudoku_object.mShuffle(wordListKeyboard);
                hint_for_board = sudoku_object.getMenu_list_French();
                listFrenchWords = sudoku_object.getMenu_list_French();
            } else { //L.C. MODE ON -- GRID WITH NUMBERS
                wordListSudokuTable = sudoku_object.generate_LCmodeGrid();
                wordListKeyboard = sudoku_object.getMenu_list_French();
                wordListKeyboard = sudoku_object.mShuffle(wordListKeyboard);
                hint_for_board = sudoku_object.getMenu_list_French();
                listFrenchWords = sudoku_object.getMenu_list_French();
            }
        }
        // CASE NUMBER =2 --> LANGUAGE MODE = FRENCH TO ENGLISH
        else {
            if (LC_enabled == 0) { //L.C. mode OFF
                wordListSudokuTable = sudoku_object.generate_get_grid_French();
                wordListKeyboard = sudoku_object.getMenu_list_English();
                wordListKeyboard = sudoku_object.mShuffle(wordListKeyboard);
                hint_for_board = sudoku_object.getMenu_list_English();
                listFrenchWords = sudoku_object.getMenu_list_French();
            } else {//L.C. MODE ON -- GRID WITH NUMBERS
                wordListSudokuTable = sudoku_object.generate_LCmodeGrid();
                wordListKeyboard = sudoku_object.getMenu_list_English();
                wordListKeyboard = sudoku_object.mShuffle(wordListKeyboard);
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
            recieved_string = pref.getString("chapter ", "no");
            //recieved_data[i]=pref.getString("chapter "+chapter_number+" line number is "+i, "no");

            int line_counter=pref.getInt("line_counter",0);
            sudoku_object.set_data_recived_from_file(recieved_string, line_counter, MainActivity.this);
        }
    }


    public boolean isTablet()
    {
        try {
            // Compute screen size
            Context context = MainActivity.this;
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            float screenWidth  = dm.widthPixels / dm.xdpi;
            float screenHeight = dm.heightPixels / dm.ydpi;
            double size = Math.sqrt(Math.pow(screenWidth, 2) +
                    Math.pow(screenHeight, 2));
            // Tablet devices have a screen size greater than 6 inches
            return size >= 6;
        } catch(Throwable t) {
            //Log.e("Failed to compute screen size", t.toString());
            return false;
        }
    }


    public boolean shaded_rectangle(int grid_size, int position) {


        int index;
       // int step_size=0;
        if (gridLength==9 || gridLength==12) {

             for(int group = 0; group < grid_size; group=group+2) {
                 for (int row = (group / subLen) * subLen; row < (group / subLen) * subLen + subLen; row++) {
                     for (int column = (group % subLen) * subWid; column < (group % subLen) * subWid + subWid; column++) {
                         index = grid_size * row + column;
                         if (position == index) {
                             return true;
                         }
                     }
                 }
             }
         }

         if (gridLength==4)
         {


            for (int group=0 ; group<5; group=group+3)
             for (int row = (group / subLen) * subLen; row < (group / subLen) * subLen + subLen; row++) {
                 for (int column = (group % subLen) * subWid; column < (group % subLen) * subWid + subWid; column++) {
                     index = grid_size * row + column;
                     if (position == index) {
                         return true;
                     }
                 }
             }

         }


         if (gridLength==6)
         {
             if ((position>=0 && position <=2) || (position>=6 && position <=8 || (position>=12 && position <=14)) ||
                     (position >=21 && position <=23) || (position>=27 && position<=29) || (position>=33))
             {
                 return true;
             }

         }

        return false;

    }


}