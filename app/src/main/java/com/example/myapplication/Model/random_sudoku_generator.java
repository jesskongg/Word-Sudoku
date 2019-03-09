package com.example.myapplication.Model;

import android.support.v4.app.INotificationSideChannel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;
import java.util.Random;

public class random_sudoku_generator{

    public static ArrayList<Integer> get_possible_values(int[] board, int position){
        if(board.length != 81 || position < 0 || position > 80){
            throw new IllegalArgumentException();
        }

        HashSet<Integer> set = new HashSet<>();

        //check the row
        int row = position/9;
        for(int i = 0; i < 9; i++){
            if(board[row*9 + i] != 0){
                set.add(board[row*9 + i]);
            }
        }

        //check the column
        int col = position%9;
        for(int i = 0; i < 9; i++){
            if(board[col + i*9] != 0){
                set.add(board[col + i*9]);
            }
        }

        //check the 3*3 grid
        int groupIdRow = (position/9)/3;
        int groupIdCol = (position%9)/3;
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++){
                if(board[groupIdRow*27 + groupIdCol*3 + 9*i + j] != 0) {
                    set.add(board[groupIdRow*27 + groupIdCol*3 + 9*i + j]);
                }
            }
        }

        ArrayList<Integer> list_impossible = new ArrayList<Integer>();
        list_impossible.addAll(set);

        ArrayList<Integer> list_possible = new ArrayList<>();
        for(int i = 1; i < 10; i++){
            list_possible.add(i);
        }

        int size = list_impossible.size();
        for(int i = 0; i < size; i++){
            list_possible.remove(list_impossible.get(i));
        }

        return list_possible;
    }

    public static int[] generate(){
        //initialize the empty board
        int[] board = new int[81];
        for(int i = 0; i < 81; i++){
            board[i] = 0;
        }

        ArrayList<Integer> randomList = new ArrayList<>();
        for(int i = 1; i < 10; i++){
            randomList.add(i);
        }

        // fill in left-top 3*3 grid with random numbers
        Collections.shuffle(randomList);
        int k = 0;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                board[9*i + j] = randomList.get(k);
                k++;
            }
        }

        //fill in middle 3*3 grid with random numbers
        Collections.shuffle(randomList);
        k = 0;
        for(int i = 3; i < 6; i++){
            for(int j = 3; j < 6; j++){
                board[9*i + j] = randomList.get(k);
                k++;
            }
        }

        //fill in right-bottom 3*3 grid with random numbers
        Collections.shuffle(randomList);
        k = 0;
        for(int i = 6; i < 9; i++){
            for(int j = 6; j < 9; j++){
                board[9*i + j] = randomList.get(k);
                k++;
            }
        }

        //give each empty cell an index
        ArrayList<Integer> position_index = new ArrayList<>();
        for(int i = 0; i < 81; i++){
            if(board[i] == 0){
                position_index.add(i);
            }
        }

        //initialize a two dimensional ArrayList to hole the possible values for each position
        ArrayList<ArrayList<Integer>> possible_values = new ArrayList<>();

        int i = 0;
        int position;
        Random r = new Random();
        while(i < 54){
            position = position_index.get(i);

            if(possible_values.size() <= i){
                possible_values.add(get_possible_values(board, position));
            }

            if(possible_values.get(i).size() == 0){
                possible_values.remove(i);
                i--;
                board[position] = 0;
            }
            else{
                int j = r.nextInt(possible_values.get(i).size());
                board[position] = possible_values.get(i).get(j);
                possible_values.get(i).remove(j);
                i++;
            }
        }

        //randomly choose balnk cells
        int number_of_0 = 0;
        while(number_of_0 == 0){
            for(int a = 0; a < 50; a++){
                int rand = r.nextInt(81);
                board[rand] = 0;
            }

            for(int a = 0; a < 81; a++){
                if(board[a] == 0){
                    number_of_0++;
                }
            }
        }


        return board;
    }

    // public static void main(String[] args){
    // 	int[] board = new int[81];
    // 	board = generate();

    // 	for(int i = 0; i < 81; i++){
    // 		System.out.print(board[i]);
    // 		System.out.print(" ");
    // 		if(i % 9 == 8){
    // 			System.out.println(" ");
    // 		}
    // 	}
    // }
}