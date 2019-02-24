package com.example.myapplication;

public class board_checker {
    private int board[];
    private int number_of_rows;
    private int number_of_columns;


    public board_checker(int[] board, int number_of_columns, int number_of_rows)
    {
        this.board=board;
        this.number_of_columns=number_of_columns;
        this.number_of_rows=number_of_rows;
    }

    public boolean checker()
    {
        int index;
        //check rows
        int row_sum = 0;
        for(int row = 0; row < 9; row++) {
            for(int column = 0; column < 9; column++) {
                index = 9*row + column;
                row_sum += board[index];
            }
            if(row_sum != 45) {
                return false;
            }
            //reset row_sum
            row_sum = 0;
        }

        //check columns
        int column_sum = 0;
        for(int column = 0; column < 9; column++) {
            for(int row = 0; row <9; row++) {
                index = 9*row + column;
                column_sum += board[index];
            }

            if(column_sum != 45) {
                return false;
            }

            //reset column_sum
            column_sum = 0;
        }
//
//        //check groups
        int group_sum = 0;
        for(int group = 0; group < 9; group++) {
            for (int row = (group / 3) * 3; row < (group / 3) * 3 + 3; row++) {
                for (int column = (group % 3) * 3; column < (group % 3) * 3 + 3; column++) {
                    index = 9 * row + column;
                    group_sum += board[index];
                }
            }

            if (group_sum != 45) {
                return false;
            }
            //reset group_sum
            group_sum = 0;
        }
        return true;

    }

}


