package com.example.myapplication.Model;

public class boards_and_menu_data {
    private String[] mMenu_list_English;//={"pink", "blue", "red", "green", "grey", "peach", "pear", "plum", "fig"};
    private String[] mSudoku_grid_French;

    private String[] mMenu_list_French;//={"rose", "bleu", "rouge", "vert", "gris", "pêche", "poire", "prune", "figue"};
    private String[] mSudoku_grid_English;//=new String[81];
    private String[] mSudoku_grid_LCmode;


    private int number_board[];
    private int solvable_board[];

    private int number_of_columns;



    public void setMenu_list_English(String[] english_file_data)
    {

        if(english_file_data.length < number_of_columns){
            throw new IllegalArgumentException();
        }
        else {
            mMenu_list_English = new String[number_of_columns];
            mMenu_list_English = english_file_data;
        }
    }


    //board is based on
    public void setMenu_list_French(String[] french_file_data)
    {

        if(french_file_data.length < number_of_columns){
            throw new IllegalArgumentException();
        }
        else {
            mMenu_list_French = new String[number_of_columns];
            mMenu_list_French = french_file_data;
        }
    }



    public boards_and_menu_data(int number_of_columns){

        this.number_of_columns=number_of_columns;

        mMenu_list_French=new String[number_of_columns];
        //mMenu_list_French=Menu_list_French;

        mMenu_list_English=new String[number_of_columns];
        //mMenu_list_English=Menu_list_English;
        //mMenu_list_English
        //Bundle english_bundle = getIntent().getExtras();
        //private String[] mMenu_list_English=english_bundle.getStringArray("EnglishArray");
        mSudoku_grid_English = new String[number_of_columns*number_of_columns];
        mSudoku_grid_French = new String[number_of_columns*number_of_columns];
        mSudoku_grid_LCmode = new String[number_of_columns*number_of_columns];

        //random_sudoku_generator generator = new random_sudoku_generator();
        //int[] rand_board = new int[81];
        number_board = new int[number_of_columns*number_of_columns];
        solvable_board = new int[number_of_columns*number_of_columns];

        //rand_board = generator.generate();
        //for(int i = 0; i < 81; i++){
        //    number_board[i] = rand_board[i];
         //   solvable_board[i] = rand_board[i];
        //}
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


        //DUMB, TEMPORARLY, CODE SHOULD BE CHANGED WITH RANDOM SUDOKU GENERATOR

        int[] example_number_board= new int[]{0, 2, 2, 0, 0, 0, 0, 0, 4,
                2, 0, 0, 0, 0, 1, 0, 3, 0,
                0, 0, 2, 2, 2, 2, 0, 0, 0,
                0, 0, 2, 0, 0, 0, 0, 1, 2,
                1, 2, 3, 2, 0, 0, 0, 2, 0,
                9, 7, 0, 0, 8, 3, 6, 0, 2,
                0, 3, 0, 6, 9, 4, 5, 7, 1,
                4, 6, 0, 0, 0, 2, 9, 8, 0,
                7, 5, 9, 3, 1, 0, 0, 2, 6,
                7, 5, 9, 3, 1, 0, 0, 2, 6,
                7, 5, 9, 3, 1, 0, 0, 2, 6,
                7, 5, 9, 3, 1, 0, 0, 2, 6,
                7, 5, 9, 3, 1, 0, 0, 2, 6,
                7, 5, 9, 3, 1, 0, 0, 2, 6,
                7, 5, 9, 3, 1, 0, 0, 2, 6,
                11, 11, 7, 7, 7, 0, 0, 9, 11};

        int[] example_solvable_board=new int[]{0, 2, 2, 0, 0, 0, 0, 0, 4,
                2, 0, 0, 0, 0, 1, 0, 3, 0,
                0, 0, 2, 2, 2, 2, 0, 0, 0,
                0, 0, 2, 0, 0, 0, 0, 1, 2,
                1, 2, 3, 2, 0, 0, 0, 2, 0,
                9, 7, 0, 0, 8, 3, 6, 0, 2,
                0, 3, 0, 6, 9, 4, 5, 7, 1,
                4, 6, 0, 0, 0, 2, 9, 8, 0,
                7, 5, 9, 3, 1, 0, 0, 2, 6,
                7, 5, 9, 3, 1, 0, 0, 2, 6,
                7, 5, 9, 3, 1, 0, 0, 2, 6,
                7, 5, 9, 3, 1, 0, 0, 2, 6,
                7, 5, 9, 3, 1, 0, 0, 2, 6,
                7, 5, 9, 3, 1, 0, 0, 2, 6,
                7, 5, 9, 3, 1, 0, 0, 2, 6,
                7, 5, 9, 3, 1, 0, 0, 2, 6,
                11, 11, 7, 7, 7, 0, 0, 9, 11};



        for (int i=0; i<number_of_columns*number_of_columns; i++){

            number_board[i]=example_number_board[i];
            solvable_board[i]=example_solvable_board[i];

        }


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

        for (int i=0; i<number_of_columns*number_of_columns; i++) {
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
        for(int i=0; i<number_of_columns*number_of_columns; i++)
        {
            if (number_board[i]==0)
            {
                mSudoku_grid_English[i]=" ";
            }

            else
            {
                String cell=mMenu_list_English[(number_board[i]-1)];
                mSudoku_grid_English[i]=cell;
            }

        }
        return mSudoku_grid_English;
    }

    // GRID WITH NUMBERS FOR LISTENING COMPREHENSION MODE
    public String[] generate_LCmodeGrid() {
        for (int i = 0; i < number_of_columns*number_of_columns; i++) {
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

    public int getNumber_of_columns()
    {
        return  number_of_columns;
    }

}

