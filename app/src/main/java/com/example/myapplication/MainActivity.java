package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TableLayout sudokuTable;
    private TableLayout wordKeyboard;
    private String wordListE[] = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
    private String wordListF[] = {"un", "deux", "trois", "quatre", "cinq", "six", "sept", "huit", "neuf"};
    private String wordListSudokuTable[];
    private String wordListKeyboard[];
    private int board[] =
            {0,9,2,0,0,0,0,0,4,5,0,0,0,0,1,0,3,0,0,0,7,8,4,6,0,0,0,0,0,5,0,0,0,0,1,8,1,8,3,2,0,0,0,4,0,9,7,0,0,8,3,6,0,2,0,3,0,6,9,4,5,7,1,4,6,0,0,0,2,9,8,0,7,5,9,3,1,0,0,2,6};
    private TextView sudokucells[][] = new TextView[9][9];
    private int cell_clicked;
    private Button checkBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setWordList
        Intent language = getIntent();
        int numberOfLanguage = language.getIntExtra("language", 0);
        setWordList(numberOfLanguage);

        sudokuTable = (TableLayout) findViewById(R.id.sudokuTable);

        sudokuTable.setBackgroundColor(Color.BLACK);

        wordKeyboard = (TableLayout) findViewById(R.id.wordKeyboard);

        wordKeyboard.setBackgroundColor(Color.BLACK);


        cell_clicked = -1;
        setSudokuTable();
        setWardKeyboard();


        checkBoard = (Button) findViewById(R.id.checkBoard);
        checkBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCorrect;
                isCorrect = checkBoard();
                if(isCorrect == true) {
                    Toast.makeText(MainActivity.this,
                            R.string.boardTrue,
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,
                            R.string.boardFalse,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Create sudokutTable
    private void setSudokuTable() {
        for(int row = 0; row < 9; row++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));

            for(int column = 0; column < 9; column++) {
                //Create sudokuCell(TextView)
                final TextView sudokuCell = new TextView(this);
                sudokuCell.setBackgroundResource(R.drawable.cell_shap);
                sudokuCell.setHeight(100);
                sudokuCell.setWidth(102);
                sudokuCell.setGravity(Gravity.CENTER);

                final int index_board = 9 * row + column;  //get the index of board
                sudokuCell.setText(intToWord(board[index_board], wordListSudokuTable[0]));    //Set text in sudukuCell

                sudokuCell.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                tableRow.addView(sudokuCell);   //add sudokuCell to the tableRow

                //choose the empty sudokuCell
                if(board[index_board] == 0) {
                    sudokuCell.setTextColor(Color.BLUE);

                    //Set listener to the empty sudokuCell
                    sudokuCell.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           cell_clicked = index_board;
                           Toast.makeText(MainActivity.this,
                                   R.string.chooseWord,
                                   Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    //Set hint to the filled sudokuCell
                    sudokuCell.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MainActivity.this,
                                    R.string.hint,
                                    Toast.LENGTH_SHORT).show();
                            sudokuCell.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(MainActivity.this,
                                            intToWord(board[index_board], wordListKeyboard[0]),
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }

                //Add sudukuCell to the arrayList
                sudokucells[row][column] = sudokuCell;
            }

            sudokuTable.addView(tableRow);  //add the tableRow to the table
        }
    }

    private void setWardKeyboard() {
        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
        for(int i = 0; i < 9; i++) {
            TextView keyboardCell = new TextView(this);
            keyboardCell.setBackgroundResource(R.drawable.cell_shap);
            keyboardCell.setHeight(100);
            keyboardCell.setWidth(102);
            keyboardCell.setGravity(Gravity.CENTER);

            keyboardCell.setText(wordListKeyboard[i]);

            final int number = wordToInt(wordListKeyboard[i]);
            final String text = wordListKeyboard[i];

            keyboardCell.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            tableRow.addView(keyboardCell);

            keyboardCell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cell_clicked == -1) {
                        Toast.makeText(MainActivity.this,
                                R.string.chooseCell,
                                Toast.LENGTH_SHORT).show();
                    }
                    else {
                        setWordInCell(cell_clicked, text, number);

                    }

                }
            });
        }

        wordKeyboard.addView(tableRow);
    }

    private void setWordInCell(int position, String text, int number) {
        int row = position / 9;
        int column = position % 9;

        sudokucells[row][column].setText(text);

        board[position] = number;
    }

    private int wordToInt(String word) {
        int i;
        for(i = 0; i < 9; i++) {
            if(word == wordListE[i] || word == wordListF[i] )
                return i + 1;
        }

        return -1;
    }

    private String intToWord (int number, String language) {
        if(number == 0) {
            return "";
        }
        else if(language == wordListE[0]){
            return  wordListE[number-1];
        }
        else {
            return wordListF[number - 1];
        }
    }


    private boolean checkBoard() {
        int index;

//        //check rows
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

    //Choose to use English or French as the "fill in" language. Number 1 is for French, and number 2 is for English.
    private void setWordList(int caseNumber){
        if(caseNumber == 1){
            wordListSudokuTable = wordListE;
            wordListKeyboard = wordListF;
        }
        else{
            wordListSudokuTable = wordListF;
            wordListKeyboard = wordListE;
        }
    }

}
