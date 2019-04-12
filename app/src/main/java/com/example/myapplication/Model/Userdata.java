package com.example.myapplication.Model;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Userdata {
    private Context mContext;
    private SQLiteDatabase db;

    //variables for reading file data

    //number of lines is 0
    int line_counter=0;
    String file_string;


    public void createTable(){

    }

    public void saveData(int length, int[] number_board, int[] solvable_board, String[] words_table, String[] key_board, String[] hint_board, String[] list_French_words, Context context) {
//        if (board.length != 81) {
//            throw new IllegalArgumentException();
//        } else {
        mContext = context.getApplicationContext();

        db = new UserDataHelper(mContext).getWritableDatabase();
        db.delete("boardData", null, null);
        for (int i = 0; i < number_board.length; i++) {
            ContentValues values1 = new ContentValues();
            String index = Integer.toString(i);
            String number = Integer.toString(number_board[i]);
            String sNumber = Integer.toString(solvable_board[i]);
            values1.put("board_index", index);
            values1.put("number", number_board[i]);
            values1.put("sNumber", solvable_board[i]);
            values1.put("words", words_table[i]);
            db.insert("boardData", null, values1);


        }

        db.delete("wordsData", null, null);
        if (list_French_words == null) {
            for (int i = 0; i < key_board.length; i++) {
                ContentValues values2 = new ContentValues();
                String index = Integer.toString(i);
                values2.put("words_index", index);
                values2.put("key_words", key_board[i]);
                values2.put("hint_words", hint_board[i]);
                values2.put("French_words", " ");
                db.insert("wordsData", null, values2);
            }
        }
        else {
            for (int i = 0; i < key_board.length; i++) {
                ContentValues values2 = new ContentValues();
                String index = Integer.toString(i);
                values2.put("words_index", index);
                values2.put("key_words", key_board[i]);
                values2.put("hint_words", hint_board[i]);
                values2.put("French_words", list_French_words[i]);
                db.insert("wordsData", null, values2);
            }
        }

        db.delete("boardSize", null, null);
        ContentValues values = new ContentValues();
        String sLength = Integer.toString(length);
        values.put("length", sLength);
        db.insert("boardSize", null, values);

        //Should I do it? I cant find it in textBook.
//        db.close();
//
//
//        }
    }

    public void saveLC(int LCmode, Context context) {
        mContext = context.getApplicationContext();

        db = new UserDataHelper(mContext).getWritableDatabase();
        db.delete("LCmode", null, null);
        ContentValues values = new ContentValues();
        String mode = Integer.toString(LCmode);
        values.put("LC_mode", mode);
        db.insert("LCmode", null, values);

    }

    public void record_hint_times(String word, Context context) {
        mContext = context.getApplicationContext();
        String times;
        int num;

        db = new UserDataHelper(mContext).getWritableDatabase();
        ContentValues values = new ContentValues();
        Cursor cursor = db.query("hashMap", new String[]{"words", "times"}, "words=?", new String[]{word}, null, null, "words");
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            times = cursor.getString(cursor.getColumnIndex("times"));
            num = Integer.valueOf(times).intValue();
            num = num + 1;
            times = Integer.toString(num);
            values.put("times", times);
            db.update("hashMap", values, "words=?", new String[]{word});
        } else {
            num = 1;
            times = Integer.toString(num);
            values.put("words", word);
            values.put("times", times);
            db.insert("hashMap", null, values);
        }
        cursor.close();

    }

    public int getLC(Context context){
        int LC = 0;
        mContext = context.getApplicationContext();
        db = new UserDataHelper(mContext).getWritableDatabase();
        Cursor cursor = db.query("LCmode", new String[]{"LC_mode"}, null, null, null, null, "LC_mode");
        String number;
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                number = cursor.getString(cursor.getColumnIndex("LC_mode"));
                LC = Integer.valueOf(number).intValue();
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return LC;
    }

    public int[] getLenth(Context context){
        int[] size = new int[3];

        int i = 0;
        mContext = context.getApplicationContext();
        db = new UserDataHelper(mContext).getWritableDatabase();
        Cursor cursor = db.query("boardSize", new String[]{"length"}, null, null, null, null, "length");
        String number;
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                number = cursor.getString(cursor.getColumnIndex("length"));
                size[i] = Integer.valueOf(number).intValue();
                i++;
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        if(size[0] == 4){
            size[1] = 2;
            size[2] = 2;
        }
        else if(size[0] == 6){
            size[1] = 2;
            size[2] = 3;
        }
        else if(size[0] == 9){
            size[1] = 3;
            size[2] = 3;
        }
        else if(size[0] == 12){
            size[1] = 3;
            size[2] = 4;
        }

        return size;
    }


    public int[] getNumber_board(int gridLength, Context context){
        int capacity = gridLength * gridLength;
        int[] board = new int[capacity];

        int i = 0;
        mContext = context.getApplicationContext();
        db = new UserDataHelper(mContext).getWritableDatabase();
        Cursor cursor = db.query("boardData", new String[]{"board_index", "number"}, null, null, null, null, "board_index");
        String number;
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                number = cursor.getString(cursor.getColumnIndex("number"));
                board[i] = Integer.valueOf(number).intValue();
                i++;
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

//        int number_of_0 = 0;
//        for(int j = 0; j < 81; j++){
//            if(board[j] == 0){
//                number_of_0++;
//            }
//        }
//
//        if(number_of_0 == 81){
//            throw new SQLiteException();
//        }

        return board;
    }

    public int[] getSolvable_board(int gridLength, Context context){
        int capacity = gridLength * gridLength;
        int[] board = new int[capacity];

        int i = 0;
        mContext = context.getApplicationContext();
        db = new UserDataHelper(mContext).getWritableDatabase();
        Cursor cursor = db.query("boardData", new String[]{"board_index", "sNumber"}, null, null, null, null, "board_index");
        String number;
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                number = cursor.getString(cursor.getColumnIndex("sNumber"));
                board[i] = Integer.valueOf(number).intValue();
                i++;
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

//        int number_of_0 = 0;
//        for(int j = 0; j < 81; j++){
//            if(board[j] == 0){
//                number_of_0++;
//            }
//        }
//
//        if(number_of_0 == 81){
//            throw new SQLiteException();
//        }

        return board;
    }

    public String[] getWordsTable(int gridLength, Context context){
        int capacity = gridLength * gridLength;
        String[] words_table = new String[capacity];

        int i = 0;
        mContext = context.getApplicationContext();
        db = new UserDataHelper(mContext).getWritableDatabase();
        Cursor cursor = db.query("boardData", new String[]{"board_index", "words"}, null, null, null, null, "board_index");
        String number;
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                words_table[i] = cursor.getString(cursor.getColumnIndex("words"));
                i++;
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

//        int number_of_0 = 0;
//        for(int j = 0; j < 81; j++){
//            if(words_table[j] == 0){
//                number_of_0++;
//            }
//        }
//
//        if(number_of_0 == 81){
//            throw new SQLiteException();
//        }

        return words_table;
    }

    public String[] getKeyBoard(int gridLength, Context context){
        String[] key_words = new String[gridLength];

        int i = 0;
        mContext = context.getApplicationContext();
        db = new UserDataHelper(mContext).getWritableDatabase();
        Cursor cursor = db.query("wordsData", new String[]{"words_index", "key_words"}, null, null, null, null, "words_index");
//        String number;
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                key_words[i] = cursor.getString(cursor.getColumnIndex("key_words"));
                i++;
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return key_words;
    }

    public String[] getHintBoard(int gridLength, Context context){
        String[] hint_words = new String[gridLength];

        int i = 0;
        mContext = context.getApplicationContext();
        db = new UserDataHelper(mContext).getWritableDatabase();
        Cursor cursor = db.query("wordsData", new String[]{"words_index", "hint_words"}, null, null, null, null, "words_index");
//        String number;
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                hint_words[i] = cursor.getString(cursor.getColumnIndex("hint_words"));
                i++;
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return hint_words;
    }

    public String[] getListFrenchWords(int gridLength, Context context){
        String[] French_words = new String[gridLength];

        int i = 0;
        mContext = context.getApplicationContext();
        db = new UserDataHelper(mContext).getWritableDatabase();
        Cursor cursor = db.query("wordsData", new String[]{"words_index", "French_words"}, null, null, null, null, "words_index");
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                French_words[i] = cursor.getString(cursor.getColumnIndex("French_words"));
                i++;
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return French_words;
    }

    public HashMap<String, Integer> getHashMap(Context context){
        HashMap<String, Integer> map = new HashMap<>();

        mContext = context.getApplicationContext();
        db = new UserDataHelper(mContext).getWritableDatabase();
        Cursor cursor = db.query("hashMap", new String[]{"words", "times"}, null, null, null, null, "words");
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String word = cursor.getString(cursor.getColumnIndex("words"));
                String times = cursor.getString(cursor.getColumnIndex("times"));
                int num = Integer.valueOf(times).intValue();
                map.put(word, num);
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return map;
    }


    public void deleteHashMap(Context context){
        mContext = context.getApplicationContext();
        db = new UserDataHelper(mContext).getWritableDatabase();
        db.delete("hashMap", null, null);
    }


    //this function takes file path URI and then reads a file and puts that into shared ref
    public String read_and_pass_file_string_to_pref(BufferedReader reader, StringBuilder stringBuilder) throws IOException {
        String line;


        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(",");
            line_counter = line_counter + 1;
            file_string=stringBuilder.toString();

        }

        return file_string;
    }



    public int number_of_lines_inside_a_file()
    {
        return line_counter;
    }



    public void saveChapterWords(String name, String words, int pairs, Context context){
        mContext = context.getApplicationContext();

        db = new UserDataHelper(mContext).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("chapterName", name);
        values.put("words", words);
        String sPairs = Integer.toString(pairs);
        values.put("number_of_pairs", sPairs);
        db.insert("userWords", null, values);
    }

    public void deleteChapterWords(String name, Context context){
        mContext = context.getApplicationContext();

        db = new UserDataHelper(mContext).getWritableDatabase();
        db.delete("userWords", "chapterName=?", new String[]{name});
    }

    public String getChapterWords(String name, Context context){
        String words = null;

        mContext = context.getApplicationContext();
        db = new UserDataHelper(mContext).getWritableDatabase();
        Cursor cursor = db.query("userWords", new String[]{"chapterName", "words"}, null, null, null, null, "chapterName");
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String mName = cursor.getString(cursor.getColumnIndex("chapterName"));
                if(mName.equals(name)){
                    words = cursor.getString(cursor.getColumnIndex("words"));
                }
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return words;
    }

    public int getLineCounter(String name, Context context){
        int num = 0;

        mContext = context.getApplicationContext();
        db = new UserDataHelper(mContext).getWritableDatabase();
        Cursor cursor = db.query("userWords", new String[]{"chapterName", "number_of_pairs"}, null, null, null, null, "chapterName");
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String mName = cursor.getString(cursor.getColumnIndex("chapterName"));
                if(mName.equals(name)){
                    String sNum = cursor.getString(cursor.getColumnIndex("number_of_pairs"));
                    num = Integer.valueOf(sNum).intValue();
                }
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return num;
    }

    public ArrayList<String> getChapterList(Context context){
        ArrayList<String> list = new ArrayList<>();

        mContext = context.getApplicationContext();
        db = new UserDataHelper(mContext).getWritableDatabase();
        Cursor cursor = db.query("userWords", new String[]{"chapterName"}, null, null, null, null, "chapterName");
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String mName = cursor.getString(cursor.getColumnIndex("chapterName"));
                list.add(mName);
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return list;
    }




}
