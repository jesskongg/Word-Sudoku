package com.example.myapplication;

import org.junit.Test;

import static org.junit.Assert.*;

public class board_checkerTest {
    @Test
    public void testCreate(){
        int[] testBoard = new int[81];
        for(int i = 0; i < 81; i++){
            testBoard[i] = 0;
        }
        board_checker boardChecker = new board_checker(testBoard);
        assertArrayEquals(testBoard, boardChecker.getBoard());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateThrows(){
        int[] testBoard = new int[]{1, 2, 3, 4, 5};
        board_checker boardChecker = new board_checker(testBoard);
    }

    @Test
    public void testChecker(){
        int[] testBoard = new int[]{5, 9, 4, 3, 6, 8, 2, 1, 7,
                                    6, 2, 3, 7, 1, 4, 9, 8, 5,
                                    8, 7, 1, 5, 2, 9, 6, 4, 3,
                                    7, 6, 2, 9, 3, 1, 4, 5, 8,
                                    1, 4, 5, 2, 8, 6, 3, 7, 9,
                                    9, 3, 8, 4, 7, 5, 1, 2, 6,
                                    4, 1, 7, 6, 5, 3, 8, 9, 2,
                                    2, 8, 6, 1, 9, 7, 5, 3, 4,
                                    3, 5, 9, 8, 4, 2, 7, 6, 1};
        board_checker boardChecker = new board_checker(testBoard);
        assertTrue(boardChecker.checker());
    }

    @Test
    public void testCheckerFail(){
        int[] testBoard = new int[81];
        for(int i = 0; i < 81; i++){
            testBoard[i] = i;
        }
        board_checker boardChecker = new board_checker(testBoard);
        assertFalse(boardChecker.checker());

        //another case
        for(int i = 0; i < 81; i++){
            testBoard[i] = 0;
        }
        board_checker boardChecker1 = new board_checker(testBoard);
        assertFalse(boardChecker1.checker());

        //another case
        testBoard = new int[]{5, 9, 4, 3, 6, 8, 2, 1, 7,
                6, 2, 3, 7, 1, 4, 9, 8, 5,
                8, 7, 1, 5, 2, 9, 6, 4, 3,
                7, 6, 2, 9, 3, 1, 4, 5, 8,
                1, 4, 5, 2, 8, 6, 3, 7, 9,
                9, 3, 8, 4, 7, 5, 1, 2, 6,
                4, 1, 7, 6, 5, 3, 8, 9, 2,
                2, 8, 6, 1, 9, 7, 5, 3, 4,
                3, 5, 9, 8, 4, 2, 7, 6, 1};
        testBoard[41] = 9;
        board_checker boardChecker2 = new board_checker(testBoard);
        assertFalse(boardChecker1.checker());
    }


}