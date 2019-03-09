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
        list.addAll(rand.get_possible_values(testBoard, position));
        Collections.sort(list);

        int[] actuals = new int[3];
        int len = list.size();
        assertEquals(3, rand.get_possible_values(testBoard, position).size());

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
        rand.get_possible_values(testBoard, position);
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
        rand.get_possible_values(testBoard, position);
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
        rand.get_possible_values(testBoard, position);
    }

    //a function to check if the given board is a legal sudoku board
    private boolean isBoardLegal(int board[]){
        if(board.length != 81){
            return false;
        }

        int number_of_0 = 0;
        for(int i = 0; i < 81; i++){
            if(board[i] == 0){
                number_of_0++;
            }
        }

        if(number_of_0 == 0){
            return false;
        }

        HashSet<Integer> set = new HashSet<>();
        ArrayList<Integer> list = new ArrayList<>();
        int index;
        //check if rows legal
        boolean rows_legal = true;
        for(int row = 0; row < 9; row++){
            for(int column = 0; column < 9; column++){
                index = row*9 + column;
                //check if numbers are in 0 to 9
                if(board[index] < 0 || board[index] > 9){
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
        if(!rows_legal){
            return false;
        }

        //check if columns legal
        boolean columns_legal = true;
        for(int column = 0; column < 9; column++){
            for(int row = 0; row < 9; row++){
                index = row*9 + column;
                //check if numbers are in 0 to 9
                if(board[index] < 0 || board[index] > 9){
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
        if(!columns_legal){
            return false;
        }

        //check if groups legal
        boolean groups_legal = true;
        for(int group = 0; group < 9; group++) {
            for (int row = (group / 3) * 3; row < (group / 3) * 3 + 3; row++) {
                for (int column = (group % 3) * 3; column < (group % 3) * 3 + 3; column++) {
                    index = 9 * row + column;
                    //check if numbers are in 0 to 9
                    if(board[index] < 0 || board[index] > 9){
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
        if(!groups_legal){
            return false;
        }

        return true;
    }

    @Test
    public void generate() {
        //check if the generated board is a legal sudoku board
        for(int i = 0; i < 10; i++) {
            random_sudoku_generator rand = new random_sudoku_generator();
            int[] rand_board = rand.generate();

            boolean boardLegal = isBoardLegal(rand_board);
            assertTrue(boardLegal);
        }

        //check if every time it would generate different board
        random_sudoku_generator rand1 = new random_sudoku_generator();
        int[] rand_board1 = rand1.generate();

        random_sudoku_generator rand2 = new random_sudoku_generator();
        int[] rand_board2 = rand2.generate();

        boolean same = true;
        for(int i = 0; i < 81; i++){
            if(rand_board1[i] != rand_board2[i]){
                same = false;
            }
        }

        assertFalse(same);
    }
}