package com.example.myapplication.Model;

public class board_checker {
    private int board[];
    private int answer[];
    //private int number_of_rows;
    //private int number_of_columns;


    public board_checker(int[] board1)
    {
        if(board1.length != 16 && board1.length != 36 && board1.length != 81 && board1.length != 144){
            throw new IllegalArgumentException();
        }
        //this.board=new int [81];
        this.board=board1;
        //this.number_of_columns=number_of_columns;
        //this.number_of_rows=number_of_rows;
    }

    public boolean checker(int length, int subLen, int subWid)
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

    public int[] getBoard() {
        return board;
    }
}


