package com.example.myapplication.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDataHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "newUserData.db";
    public UserDataHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table boardData(board_index String primary key, number String, sNumber String, words String)");
        db.execSQL("create table wordsData(words_index String primary key, key_words String, hint_words String, French_words String)");
        db.execSQL("create table hashMap(words String primary key, times String)");
        db.execSQL("create table boardSize(length String primary key)");
        db.execSQL("create table LCmode(LC_mode String primary key)");
        db.execSQL("create table userWords(chapterName String primary key, words String, number_of_pairs String)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists EventsBase");
        onCreate(db);
    }
}
