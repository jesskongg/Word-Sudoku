package com.example.myapplication;

import com.example.myapplication.Model.boards_and_menu_data;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.*;

public class boards_and_menu_dataTest {

    @Test
    public void getMenu_list_English() {
        boards_and_menu_data myData = new boards_and_menu_data(4, 2, 2);
        String[] s = new String[4];
        assertArrayEquals(s, myData.getMenu_list_English());
    }

    @Test
    public void getMenu_list_French() {
        boards_and_menu_data myData = new boards_and_menu_data(6, 2, 3);
        String[] s = new String[6];
        assertArrayEquals(s, myData.getMenu_list_French());
    }

    @Test
    public void setMenu_list_English(){
        boards_and_menu_data myData = new boards_and_menu_data(9, 3, 3);
        String[] s = {"pink", "blue", "red", "green", "grey", "peach", "pear", "plum", "fig"};
        myData.setMenu_list_English(s);
        assertArrayEquals(s, myData.getMenu_list_English());
    }

    @Test
    public void setMenu_list_French(){
        boards_and_menu_data myData = new boards_and_menu_data(12, 3, 4);
        String[] s = {"rose", "bleu", "rouge", "vert", "gris", "pêche", "poire", "prune", "figue", "fr1", "fr2", "fr3"};
        myData.setMenu_list_French(s);
        assertArrayEquals(s, myData.getMenu_list_French());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setMenu_list_EnglishFail(){
        boards_and_menu_data myData = new boards_and_menu_data(4, 2, 2);
        String[] s = {"pink", "blue", "pear", "plum", "fig"};
        myData.setMenu_list_English(s);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setMenu_list_FrenchFail(){
        boards_and_menu_data myData = new boards_and_menu_data(6, 2, 3);
        String[] s = {"pink", "blue", "pear", "plum", "fig"};
        myData.setMenu_list_French(s);
    }

    @Test
    public void getNumber_of_columns()
    {
        boards_and_menu_data myData = new boards_and_menu_data(9, 3, 3);
        assertEquals(9, myData.getNumber_of_columns());
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
    public void getNumber_board() {
        int len[] = new int[]{4, 6, 9, 12};
        int subLen[] = new int[]{2, 2, 3, 3};
        int subWid[] = new int[]{2, 3, 3, 4};

        for(int j = 0; j < 4; j++) {
            //check if we have the legal sudoku game
            for (int i = 0; i < 10; i++) {
                boards_and_menu_data myData = new boards_and_menu_data(len[j], subLen[j], subWid[j]);
                int[] rand_board = myData.getNumber_board();

                boolean boardLegal = isBoardLegal(rand_board, len[j], subLen[j], subWid[j]);
                assertTrue(boardLegal);
            }

            //check if every time we have different board
            boards_and_menu_data myData1 = new boards_and_menu_data(len[j], subLen[j], subWid[j]);
            int[] rand_board1 = myData1.getNumber_board();

            boards_and_menu_data myData2 = new boards_and_menu_data(len[j], subLen[j], subWid[j]);
            int[] rand_board2 = myData2.getNumber_board();

            boolean same = true;
            for (int i = 0; i < len[j]*len[j]; i++) {
                if (rand_board1[i] != rand_board2[i]) {
                    same = false;
                }
            }

            assertFalse(same);
        }

    }

    @Test
    public void getSolvable_board() {
        int len[] = new int[]{4, 6, 9, 12};
        int subLen[] = new int[]{2, 2, 3, 3};
        int subWid[] = new int[]{2, 3, 3, 4};

        for(int j = 0; j < 4; j++) {
            //check if we have the legal sudoku game
            for (int i = 0; i < 10; i++) {
                boards_and_menu_data myData = new boards_and_menu_data(len[j], subLen[j], subWid[j]);
                int[] rand_board = myData.getSolvable_board();

                boolean boardLegal = isBoardLegal(rand_board, len[j], subLen[j], subWid[j]);
                assertTrue(boardLegal);
            }

            //check if every time we have different board
            boards_and_menu_data myData1 = new boards_and_menu_data(len[j], subLen[j], subWid[j]);
            int[] rand_board1 = myData1.getSolvable_board();

            boards_and_menu_data myData2 = new boards_and_menu_data(len[j], subLen[j], subWid[j]);
            int[] rand_board2 = myData2.getSolvable_board();

            boolean same = true;
            for (int i = 0; i < len[j]*len[j]; i++) {
                if (rand_board1[i] != rand_board2[i]) {
                    same = false;
                }
            }

            assertFalse(same);
        }
    }


    @Test
    public void generate_get_grid_French() {
        int len[] = new int[]{4, 6, 9, 12};
        int subLen[] = new int[]{2, 2, 3, 3};
        int subWid[] = new int[]{2, 3, 3, 4};
        String wordList[] = new String[]{"rose", "bleu", "rouge", "vert", "gris", "pêche", "poire", "prune", "figue", "fr1", "fr2", "fr3"};

        for(int j = 0; j < 4; j++) {
            boards_and_menu_data myData = new boards_and_menu_data(len[j], subLen[j], subWid[j]);
            myData.setMenu_list_French(Arrays.copyOfRange(wordList, 0, len[j]));
            int[] rand_board = myData.getNumber_board();
            String[] grid_French = myData.generate_get_grid_French();
            String[] list_French = myData.getMenu_list_French();

            boolean corresponding = true;
            //check whether numbers and words are corresponding
            for (int i = 0; i < len[j]*len[j]; i++) {
                if (rand_board[i] == 0) {
                    if (!grid_French[i].equals(" ")) {
                        corresponding = false;
                    }
                } else {
                    if (!grid_French[i].equals(list_French[rand_board[i] - 1])) {
                        corresponding = false;
                    }
                }
            }

            assertTrue(corresponding);
        }
    }

    @Test
    public void generate_get_grid_English() {
        int len[] = new int[]{4, 6, 9, 12};
        int subLen[] = new int[]{2, 2, 3, 3};
        int subWid[] = new int[]{2, 3, 3, 4};
        String wordList[] = new String[]{"rose", "bleu", "rouge", "vert", "gris", "pêche", "poire", "prune", "figue", "fr1", "fr2", "fr3"};

        for(int j = 0; j < 4; j++) {
            boards_and_menu_data myData = new boards_and_menu_data(len[j], subLen[j], subWid[j]);
            myData.setMenu_list_English(Arrays.copyOfRange(wordList, 0, len[j]));
            int[] rand_board = myData.getNumber_board();
            String[] grid_English = myData.generate_get_grid_English();
            String[] list_English = myData.getMenu_list_English();

            boolean corresponding = true;
            //check whether numbers and words are corresponding
            for (int i = 0; i < len[j]*len[j]; i++) {
                if (rand_board[i] == 0) {
                    if (!grid_English[i].equals(" ")) {
                        corresponding = false;
                    }
                } else {
                    if (!grid_English[i].equals(list_English[rand_board[i] - 1])) {
                        corresponding = false;
                    }
                }
            }

            assertTrue(corresponding);
        }
    }

    @Test
    public void generate_LCmodeGrid() {
        int len[] = new int[]{4, 6, 9, 12};
        int subLen[] = new int[]{2, 2, 3, 3};
        int subWid[] = new int[]{2, 3, 3, 4};

        for(int j = 0; j < 4; j++) {
            boards_and_menu_data myData = new boards_and_menu_data(len[j], subLen[j], subWid[j]);
            int[] rand_board = myData.getNumber_board();
            String[] grid_LC = myData.generate_LCmodeGrid();

            boolean corresponding = true;
            //check whether numbers and words are corresponding
            for (int i = 0; i < len[j]*len[j]; i++) {
                if (rand_board[i] == 0) {
                    if (!grid_LC[i].equals(" ")) {
                        corresponding = false;
                    }
                } else {
                    if (!grid_LC[i].equals(Integer.toString(rand_board[i]))) {
                        corresponding = false;
                    }
                }
            }

            assertTrue(corresponding);
        }

    }


}