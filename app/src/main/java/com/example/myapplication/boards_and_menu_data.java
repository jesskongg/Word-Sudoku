package com.example.myapplication;

import android.content.Intent;

public class boards_and_menu_data {
    private String[] mMenu_list_English={"pink", "blue", "red", "green", "grey", "peach", "pear", "plum", "fig"};
    private String[] mSudoku_grid_French;

    private String[] mMenu_list_French={"rose", "bleu", "rouge", "vert", "gris", "pêche", "poire", "prune", "figue"};
    private String[] mSudoku_grid_English;//=new String[81];
    private String[] mSudoku_grid_LCmode;


    private int number_board[];
    private int solvable_board[];



    public boards_and_menu_data(){

        mSudoku_grid_English = new String[81];
        mSudoku_grid_French = new String[81];
        mSudoku_grid_LCmode = new String[81];

        random_sudoku_generator generator = new random_sudoku_generator();
        int[] rand_board = new int[81];
        number_board = new int[81];
        solvable_board = new int[81];
        rand_board = generator.generate();
        for(int i = 0; i < 81; i++){
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

        for (int i=0; i<81; i++) {
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


        for(int i=0; i<81; i++)
        {
            if (number_board[i]==0)
            {
                mSudoku_grid_English[i]=" ";
            }

            else
            {

                String cell=mMenu_list_English[number_board[i]-1];
                mSudoku_grid_English[i]=cell;
            }


        }

    return mSudoku_grid_English;
    }


    // GRID WITH NUMBERS FOR LISTENING COMPREHENSION MODE
    public String[] generate_LCmodeGrid() {
        for (int i = 0; i < 81; i++) {
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

}

