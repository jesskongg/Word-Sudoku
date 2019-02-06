package com.example.myapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TableLayout sudokuTable;
//    private TextView text;
//    private LinearLayout lin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sudokuTable = (TableLayout) findViewById(R.id.sudokuTable);

        sudokuTable.setBackgroundColor(Color.BLACK);

//        text = (TextView) findViewById(R.id.textView2);

//        sudokuTable.setBackgroundColor(Color.BLACK);

//        text.setText("");
//        text.setBackgroundColor(Color.BLACK);

//        TableRow r1 = new TableRow(this);
////        r1.setBackgroundColor(Color.BLACK);
//        r1.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
//
//        TextView t1 = new TextView(this);
//        t1.setText("TABLE");
//        t1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
//        r1.addView(t1);
//        sudokuTable.addView(r1);
//        lin = (LinearLayout) findViewById(R.id.lin);
//        t1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//        lin.addView(t1);

        setSudokuTable();

    }

    private void setSudokuTable() {
//        sudokuTable = findViewById(R.id.sudokuTable);

//        TableRow r1 = new TableRow(this);
////        r1.setBackgroundColor(Color.BLACK);
//        r1.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
//
//        TextView t1 = new TextView(this);
//        t1.setText("TABLE");
//        t1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
//        r1.addView(t1);
//        sudokuTable.addView(r1);

        for(int row = 0; row < 9; row++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));

            for(int column = 0; column < 9; column++) {
                TextView sudokuCell = new TextView(this);
                sudokuCell.setBackgroundResource(R.drawable.cell_shap);
                sudokuCell.setHeight(100);
                sudokuCell.setWidth(100);
                sudokuCell.setGravity(Gravity.CENTER);
                sudokuCell.setText("0");
                sudokuCell.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                tableRow.addView(sudokuCell);

                
            }

            sudokuTable.addView(tableRow);
        }
    }

}
