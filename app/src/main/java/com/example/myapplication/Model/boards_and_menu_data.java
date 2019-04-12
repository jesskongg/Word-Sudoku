package com.example.myapplication.Model;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.Controller.MainActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import java.util.Collections;

public class boards_and_menu_data {
    private String[] mMenu_list_English;//={"pink", "blue", "red", "green", "grey", "peach", "pear", "plum", "fig"};
    private String[] mSudoku_grid_French;

    private String[] mMenu_list_French;//={"rose", "bleu", "rouge", "vert", "gris", "pêche", "poire", "prune", "figue"};
    private String[] mSudoku_grid_English;//=new String[81];
    private String[] mSudoku_grid_LCmode;


    private int number_board[];
    private int solvable_board[];

    private int number_of_columns;

    public void setMenu_list_English(String[] english_file_data)
    {

        if(english_file_data.length != number_of_columns){
            throw new IllegalArgumentException();
        }
        else {
            mMenu_list_English = new String[number_of_columns];
            mMenu_list_English = english_file_data;
        }
    }


    //board is based on
    public void setMenu_list_French(String[] french_file_data)
    {

        if(french_file_data.length != number_of_columns){
            throw new IllegalArgumentException();
        }
        else {
            mMenu_list_French = new String[number_of_columns];
            mMenu_list_French = french_file_data;
        }
    }



    public boards_and_menu_data(int number_of_columns, int sublen, int subwid){

        this.number_of_columns=number_of_columns;

        mMenu_list_French=new String[number_of_columns];
        //mMenu_list_French=Menu_list_French;

        mMenu_list_English=new String[number_of_columns];
        //mMenu_list_English=Menu_list_English;
        //mMenu_list_English
        //Bundle english_bundle = getIntent().getExtras();
        //private String[] mMenu_list_English=english_bundle.getStringArray("EnglishArray");
        mSudoku_grid_English = new String[number_of_columns*number_of_columns];
        mSudoku_grid_French = new String[number_of_columns*number_of_columns];
        mSudoku_grid_LCmode = new String[number_of_columns*number_of_columns];

        random_sudoku_generator generator = new random_sudoku_generator();
        int[] rand_board = new int[number_of_columns];
        number_board = new int[number_of_columns*number_of_columns];
        solvable_board = new int[number_of_columns*number_of_columns];

        rand_board = generator.generate(number_of_columns, sublen, subwid);
        for(int i = 0; i < number_of_columns*number_of_columns; i++){
            number_board[i] = rand_board[i];
            solvable_board[i] = rand_board[i];
        }
//        number_board= new int[]{0, 9, 2, 0, 0, 0, 0, 0, 4,
//                5, 0, 0, 0, 0, 1, 0, 3, 0,
//                0, 0, 7, 8, 4, 6, 0, 0, 0,
//                0, 0, 5, 0, 0, 0, 0, 1, 8,
//                1, 8, 3, 2, 0, 0, 0, 4, 0,
//                9, 7, 0, 0, 8, 3, 6, 0, 2,
//                0, 3, 0, 6, 9, 4, 5, 7, 1,
//                4, 6, 0, 0, 0, 2, 9, 8, 0,
//                7, 5, 9, 3, 1, 0, 0, 2, 6};
//
//        solvable_board=new int[]{0, 9, 2, 0, 0, 0, 0, 0, 4,
//                5, 0, 0, 0, 0, 1, 0, 3, 0,
//                0, 0, 7, 8, 4, 6, 0, 0, 0,
//                0, 0, 5, 0, 0, 0, 0, 1, 8,
//                1, 8, 3, 2, 0, 0, 0, 4, 0,
//                9, 7, 0, 0, 8, 3, 6, 0, 2,
//                0, 3, 0, 6, 9, 4, 5, 7, 1,
//                4, 6, 0, 0, 0, 2, 9, 8, 0,
//                7, 5, 9, 3, 1, 0, 0, 2, 6};


        //DUMB, TEMPORARLY, CODE SHOULD BE CHANGED WITH RANDOM SUDOKU GENERATOR

//        int[] example_number_board= new int[]{0, 2, 2, 0, 0, 0, 0, 0, 4,
//                2, 0, 0, 0, 0, 1, 0, 3, 0,
//                0, 0, 2, 2, 2, 2, 0, 0, 0,
//                0, 0, 2, 0, 0, 0, 0, 1, 2,
//                1, 2, 3, 2, 0, 0, 0, 2, 0,
//                9, 7, 0, 0, 8, 3, 6, 0, 2,
//                0, 3, 0, 6, 9, 4, 5, 7, 1,
//                4, 6, 0, 0, 0, 2, 9, 8, 0,
//                7, 5, 9, 3, 1, 0, 0, 2, 6,
//                7, 5, 9, 3, 1, 0, 0, 2, 6,
//                7, 5, 9, 3, 1, 0, 0, 2, 6,
//                7, 5, 9, 3, 1, 0, 0, 2, 6,
//                7, 5, 9, 3, 1, 0, 0, 2, 6,
//                7, 5, 9, 3, 1, 0, 0, 2, 6,
//                7, 5, 9, 3, 1, 0, 0, 2, 6,
//                11, 11, 7, 7, 7, 0, 0, 9, 11};
//
//        int[] example_solvable_board=new int[]{0, 2, 2, 0, 0, 0, 0, 0, 4,
//                2, 0, 0, 0, 0, 1, 0, 3, 0,
//                0, 0, 2, 2, 2, 2, 0, 0, 0,
//                0, 0, 2, 0, 0, 0, 0, 1, 2,
//                1, 2, 3, 2, 0, 0, 0, 2, 0,
//                9, 7, 0, 0, 8, 3, 6, 0, 2,
//                0, 3, 0, 6, 9, 4, 5, 7, 1,
//                4, 6, 0, 0, 0, 2, 9, 8, 0,
//                7, 5, 9, 3, 1, 0, 0, 2, 6,
//                7, 5, 9, 3, 1, 0, 0, 2, 6,
//                7, 5, 9, 3, 1, 0, 0, 2, 6,
//                7, 5, 9, 3, 1, 0, 0, 2, 6,
//                7, 5, 9, 3, 1, 0, 0, 2, 6,
//                7, 5, 9, 3, 1, 0, 0, 2, 6,
//                7, 5, 9, 3, 1, 0, 0, 2, 6,
//                7, 5, 9, 3, 1, 0, 0, 2, 6,
//                11, 11, 7, 7, 7, 0, 0, 9, 11};



//        for (int i=0; i<number_of_columns*number_of_columns; i++){
//
//            number_board[i]=example_number_board[i];
//            solvable_board[i]=example_solvable_board[i];
//
//        }


    }

    public String[] getMenu_list_English()
    {
        return mMenu_list_English;
    }

    public String[] getMenu_list_French()
    {
        return mMenu_list_French;
    }

    public String[] generate_get_grid_French()
    {

        for (int i=0; i<number_of_columns*number_of_columns; i++) {
            if (number_board[i] == 0) {
                mSudoku_grid_French[i] = " ";
            } else {

                String cell=mMenu_list_French[(number_board[i]-1)];
                mSudoku_grid_French[i] = cell;
            }

        }
        return mSudoku_grid_French;
    }



    public String[] generate_get_grid_English()
    {
        for(int i=0; i<number_of_columns*number_of_columns; i++)
        {
            if (number_board[i]==0)
            {
                mSudoku_grid_English[i]=" ";
            }

            else
            {
                String cell=mMenu_list_English[(number_board[i]-1)];
                mSudoku_grid_English[i]=cell;
            }

        }
        return mSudoku_grid_English;
    }

    // GRID WITH NUMBERS FOR LISTENING COMPREHENSION MODE
    public String[] generate_LCmodeGrid() {
        for (int i = 0; i < number_of_columns*number_of_columns; i++) {
            if (number_board[i] == 0) {
                mSudoku_grid_LCmode[i] = " ";
            } else {
                String cell = Integer.toString(number_board[i]);
                mSudoku_grid_LCmode[i] = cell;
            }
        }
        return mSudoku_grid_LCmode;
    }



    public int[] getNumber_board()
    {
        return number_board;
    }


    public int[] getSolvable_board(){
        return solvable_board;
    }

    public int getNumber_of_columns()
    {
        return  number_of_columns;
    }

    public void set_data_recived_from_file(String data_string, int size_of_each_array, Context context) {

//        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
//        SharedPreferences.Editor editor = pref.edit();
//
//        int size_of_each_array=pref.getInt("line_counter",0);
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

        english_long_array= Arrays.copyOfRange(english_data, 0, size_of_each_array);
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

        String[] english_data_clean=new String[getNumber_of_columns()];
        String[] french_data_clean=new String[getNumber_of_columns()];

        //for tracking words which are difficult to recognize
        //choosing words according to hint times.

        Userdata data = new Userdata();
        HashMap<String, Integer> map = data.getHashMap(context);
        ChooseWords chooseWords = new ChooseWords();
        french_data_clean = chooseWords.chooseFrench(getNumber_of_columns(), map, converted_french);
        english_data_clean = chooseWords.chooseEnglish(getNumber_of_columns(), french_data_clean, converted_english, converted_french);

        //now take the first 9 elements of arrays

//        english_data_clean=Arrays.copyOfRange(converted_english, 0, 9);
//        french_data_clean=Arrays.copyOfRange(converted_french, 0, 9);


        //now paste clean arrays of size 9 into the menu
        setMenu_list_French(french_data_clean);
        setMenu_list_English(english_data_clean);
    }

    public int getPosition(String[] wordList, String word){
        for(int i = 0; i < wordList.length; i++){
            if(word.equals(wordList[i])){
                return i;
            }
        }

        return 0;
    }

    public String[] mShuffle(String[] wordList){
        String[] newWordList = new String[wordList.length];
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0; i < wordList.length; i++){
            list.add(wordList[i]);
        }
        Collections.shuffle(list);
        for(int i = 0; i < wordList.length; i++){
            newWordList[i] = list.get(i);
        }
        return newWordList;
    }

    public boolean checker(int[] board, int length, int subLen, int subWid)
    {
        if((length != 4 || subLen != 2 || subWid != 2) && (length != 6 || subLen != 2 || subWid != 3) && (length != 9 || subLen != 3 || subWid != 3) && (length != 12 || subLen != 3 || subWid != 4)){
            throw new IllegalArgumentException();
        }

        int sum = 0;
        for(int i = 1; i < length + 1; i++){
            sum += i;
        }

        int index;
        //check rows
        int row_sum = 0;
        for(int row = 0; row < length; row++) {
            for(int column = 0; column < length; column++) {
                index = length*row + column;
                row_sum += board[index];
            }
            if(row_sum != sum) {
                return false;
            }
            //reset row_sum
            row_sum = 0;
        }

        //check columns
        int column_sum = 0;
        for(int column = 0; column < length; column++) {
            for(int row = 0; row <length; row++) {
                index = length*row + column;
                column_sum += board[index];
            }

            if(column_sum != sum) {
                return false;
            }

            //reset column_sum
            column_sum = 0;
        }
//
//        //check groups
        int group_sum = 0;
        for(int group = 0; group < length; group++) {
            for (int row = (group / subLen) * subLen; row < (group / subLen) * subLen + subLen; row++) {
                for (int column = (group % subLen) * subWid; column < (group % subLen) * subWid + subWid; column++) {
                    index = length * row + column;
                    group_sum += board[index];
                }
            }

            if (group_sum != sum) {
                return false;
            }
            //reset group_sum
            group_sum = 0;
        }
        return true;

    }
}



