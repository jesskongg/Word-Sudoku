package com.example.myapplication.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class Userdata {
    private Context mContext;
    private SQLiteDatabase db;

    public void createTable(){

    }

    public void saveData(int[] number_board, int[] solvable_board, Context context) {
//        if (board.length != 81) {
//            throw new IllegalArgumentException();
//        } else {
        mContext = context.getApplicationContext();

        db = new UserDataHelper(mContext).getWritableDatabase();
        db.delete("userData", null, null);
        for (int i = 0; i < 81; i++) {
            ContentValues values = new ContentValues();
            String index = Integer.toString(i);
            String number = Integer.toString(number_board[i]);
            String sNumber = Integer.toString(solvable_board[i]);
            values.put("board_index", index);
            values.put("number", number_board[i]);
            values.put("sNumber", solvable_board[i]);
            db.insert("userData", null, values);
        }

        //Should I do it? I cant find it in textBook.
//        db.close();
//
//
//        }
    }

    public int[] getNumber_board(Context context){
        int[] board = new int[81];

        int i = 0;
        mContext = context.getApplicationContext();
        db = new UserDataHelper(mContext).getWritableDatabase();
        Cursor cursor = db.query("userData", new String[]{"board_index", "number"}, null, null, null, null, "board_index");
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

        int number_of_0 = 0;
        for(int j = 0; j < 81; j++){
            if(board[j] == 0){
                number_of_0++;
            }
        }

        if(number_of_0 == 81){
            throw new SQLiteException();
        }

        return board;
    }

    public int[] getSolvable_board(Context context){
        int[] board = new int[81];

        int i = 0;
        mContext = context.getApplicationContext();
        db = new UserDataHelper(mContext).getWritableDatabase();
        Cursor cursor = db.query("userData", new String[]{"board_index", "sNumber"}, null, null, null, null, "board_index");
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

        int number_of_0 = 0;
        for(int j = 0; j < 81; j++){
            if(board[j] == 0){
                number_of_0++;
            }
        }

        if(number_of_0 == 81){
            throw new SQLiteException();
        }

        return board;
    }

}
