package com.example.myapplication;

import com.example.myapplication.Model.random_sudoku_generator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.*;

public class random_sudoku_generatorTest {

    @Test
    public void get_possible_values() {
        int[] testBoard = new int[]{0, 9, 2, 0, 0, 0, 0, 0, 4,
                                    5, 0, 0, 0, 0, 1, 0, 3, 0,
                                    0, 0, 7, 8, 4, 6, 0, 0, 0,
                                    0, 0, 5, 0, 0, 0, 0, 1, 8,
                                    1, 8, 3, 2, 0, 0, 0, 4, 0,
                                    9, 7, 0, 0, 8, 3, 6, 0, 2,
                                    0, 3, 0, 6, 9, 4, 5, 7, 1,
                                    4, 6, 0, 0, 0, 2, 9, 8, 0,
                                    7, 5, 9, 3, 1, 0, 0, 2, 6};
        int position = 0;

        random_sudoku_generator rand = new random_sudoku_generator();

        int[] expecteds = new int[]{3, 6, 8};
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.addAll(rand.get_possible_values(testBoard, position, 9, 3, 3));
        Collections.sort(list);

        int[] actuals = new int[3];
        int len = list.size();
        assertEquals(3, rand.get_possible_values(testBoard, position, 9 ,3 ,3).size());

        for(int i = 0; i < len; i++){
            actuals[i] = list.get(i);
        }

        assertArrayEquals(expecteds, actuals);
    }

    @Test(expected = IllegalArgumentException.class)
    public void get_possible_valuesThrowCase1(){
        int[] testBoard = new int[]{1, 2, 3, 4, 5};
        int position = 1;
        random_sudoku_generator rand = new random_sudoku_generator();
        rand.get_possible_values(testBoard, position, 9 , 3, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void get_possible_valuesThrowCase2(){
        int[] testBoard = new int[]{0, 9, 2, 0, 0, 0, 0, 0, 4,
                5, 0, 0, 0, 0, 1, 0, 3, 0,
                0, 0, 7, 8, 4, 6, 0, 0, 0,
                0, 0, 5, 0, 0, 0, 0, 1, 8,
                1, 8, 3, 2, 0, 0, 0, 4, 0,
                9, 7, 0, 0, 8, 3, 6, 0, 2,
                0, 3, 0, 6, 9, 4, 5, 7, 1,
                4, 6, 0, 0, 0, 2, 9, 8, 0,
                7, 5, 9, 3, 1, 0, 0, 2, 6};
        int position = -1;
        random_sudoku_generator rand = new random_sudoku_generator();
        rand.get_possible_values(testBoard, position, 9 , 3, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void get_possible_valuesThrowCase3(){
        int[] testBoard = new int[]{0, 9, 2, 0, 0, 0, 0, 0, 4,
                5, 0, 0, 0, 0, 1, 0, 3, 0,
                0, 0, 7, 8, 4, 6, 0, 0, 0,
                0, 0, 5, 0, 0, 0, 0, 1, 8,
                1, 8, 3, 2, 0, 0, 0, 4, 0,
                9, 7, 0, 0, 8, 3, 6, 0, 2,
                0, 3, 0, 6, 9, 4, 5, 7, 1,
                4, 6, 0, 0, 0, 2, 9, 8, 0,
                7, 5, 9, 3, 1, 0, 0, 2, 6};
        int position = 100;
        random_sudoku_generator rand = new random_sudoku_generator();
        rand.get_possible_values(testBoard, position, 9, 3, 3);
    }

    //a function to check if the given board is a legal sudoku board
    private boolean isBoardLegal(int board[], int length, int subLen, int subWid){
        if(board.length != length*length){
            return false;
        }

        int number_of_0 = 0;
        for(int i = 0; i < length*length; i++){
            if(board[i] == 0){
                number_of_0++;
            }
        }

        if(number_of_0 == 0){
            return false;
        }

        HashSet<Integer> set = new HashSet<Integer>();
        ArrayList<Integer> list = new ArrayList<Integer>();
        int index;
        //check if rows legal
        boolean rows_legal = true;
        for(int row = 0; row < length; row++){
            for(int column = 0; column < length; column++){
                index = row*length + column;
                //check if numbers are in 0 to length
                if(board[index] < 0 || board[index] > length){
                    rows_legal = false;
                }

                //check if there are same numbers
                if(board[index] != 0) {
                    list.add(board[index]);
                    set.add(board[index]);
                }
            }

            //check if there are same numbers
            if(list.size() != set.size()){
                rows_legal = false;
            }

            //clear set and list
            list.clear();
            set.clear();
        }
        if(rows_legal == false){
            return false;
        }

        //check if columns legal
        boolean columns_legal = true;
        for(int column = 0; column < length; column++){
            for(int row = 0; row < length; row++){
                index = row*length + column;
                //check if numbers are in 0 to length
                if(board[index] < 0 || board[index] > length){
                    columns_legal = false;
                }

                //check if there are same numbers

                if(board[index] != 0) {
                    set.add(board[index]);
                    list.add(board[index]);
                }
            }

            //check if there are same numbers
            if(list.size() != set.size()){
                columns_legal = false;
            }

            //clear set and list
            list.clear();
            set.clear();
        }
        if(columns_legal == false){
            return false;
        }

        //check if groups legal
        boolean groups_legal = true;
        for(int group = 0; group < length; group++) {
            for (int row = (group / subLen) * subLen; row < (group / subLen) * subLen + subLen; row++) {
                for (int column = (group % subLen) * subWid; column < (group % subLen) * subWid + subWid; column++) {
                    index = length * row + column;
                    //check if numbers are in 0 to length
                    if(board[index] < 0 || board[index] > length){
                        groups_legal = false;
                    }

                    //check if there are same numbers

                    if(board[index] != 0) {
                        set.add(board[index]);
                        list.add(board[index]);
                    }
                }
            }

            //check if there are same numbers
            if(list.size() != set.size()){
                groups_legal = false;
            }

            //clear set and list
            list.clear();
            set.clear();
        }
        if(groups_legal == false){
            return false;
        }

        return true;
    }


    @Test
    public void generate() {
        int len[] = new int[]{4, 6, 9, 12};
        int subLen[] = new int[]{2, 2, 3, 3};
        int subWid[] = new int[]{2, 3, 3, 4};

        for(int j = 0; j < 4; j++) {
            //check if the generated board is a legal sudoku board
            for (int i = 0; i < 10; i++) {
                random_sudoku_generator rand = new random_sudoku_generator();
                int[] rand_board = rand.generate(len[j], subLen[j], subWid[j]);

                boolean boardLegal = isBoardLegal(rand_board,len[j], subLen[j], subWid[j]);
                assertTrue(boardLegal);
            }

            //check if every time it would generate different board
            random_sudoku_generator rand1 = new random_sudoku_generator();
            int[] rand_board1 = rand1.generate(len[j], subLen[j], subWid[j]);

            random_sudoku_generator rand2 = new random_sudoku_generator();
            int[] rand_board2 = rand2.generate(len[j], subLen[j], subWid[j]);

            boolean same = true;
            for (int i = 0; i < len[j]*len[j]; i++) {
                if (rand_board1[i] != rand_board2[i]) {
                    same = false;
                }
            }


            assertFalse(same);
        }
    }
}