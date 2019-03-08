package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class load_new_words extends AppCompatActivity {

    //some required variables for the program to run
    private static final String TAG = "MainActivity";
    private static final int PICKFILE_RESULT_CODE = 1;


    private Button oldButton;
    private Button loadButton;

    private boolean defaultPermission=false;
    private boolean grantedPermission=false;

    private String FilePath;




    //variables for reading data
    String[] file_data_aray=new String[9];
    String[] English_string=new String[9];
    String[] French_string=new String[9];

    String[] values =new String[2];

    //size can be 9, not 20. now it's 20. it's okay
    String[][] array_of_arrays = new String[20][2];

    String[]  english_data= new String[9];
    String[]   french_data_here=new String[9];








    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_new_words);

        //isStoragePermissionGranted();
        //onRequestPermissionsResult();


        oldButton = (Button) findViewById(R.id.old_button);
        oldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectLanguageMode();
            }
        });

        loadButton= (Button) findViewById(R.id.load_button);
        loadButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //ask for permission to access external files

                defaultPermission=isStoragePermissionGranted();

                //if permission is set by default

                if (defaultPermission==true || grantedPermission==true )
                {

                    Toast.makeText(load_new_words.this, "Permission is given", Toast.LENGTH_SHORT).show();
                    //@Override
                    //public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");
                    startActivityForResult(intent,PICKFILE_RESULT_CODE);

                }
                else {

                }

            }
        });

    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {
                Toast.makeText(load_new_words.this, "inside storage permission function", Toast.LENGTH_LONG).show();
                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
            //Toast.makeText(load_new_words.this, "Am I here", Toast.LENGTH_SHORT).show();
            grantedPermission=true;

        }
        else
        {
            //modification-polina
            grantedPermission=false;
            Toast.makeText(load_new_words.this, "NO PERMISSION.NO FUN", Toast.LENGTH_SHORT).show();
        }


    }

    public void openSelectLanguageMode() {
        //THIS SHOULD BE CHANGE TO LOAD_NEW_WORDS FILE
        Intent goLanguage = new Intent(this, SelectLanguageMode.class);
        startActivity(goLanguage);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch(requestCode){
            case PICKFILE_RESULT_CODE:
                if(resultCode==RESULT_OK){
                    //FilePath = data.getData().getPath();
                    FilePath = data.getData().getPath();
                    Uri u = data.getData();
                    Utils object=new Utils();
                    String filePath_actual_one = object.getActualPath(this, u);
                    //Toast.makeText(this, filePath, Toast.LENGTH_LONG).show();
                    File my_file = new File(filePath_actual_one);
                    //String file_here = "file exists";
                    Toast.makeText(this, filePath_actual_one, Toast.LENGTH_LONG).show();

                    try {
                        FileInputStream fin = new FileInputStream(my_file);
                        BufferedReader myReader = new BufferedReader(new InputStreamReader(fin));
                        String line; //=myReader.readLine();

                        //line = " ";
                        for (int i = 0; i < 9; i++) {
                            line = myReader.readLine();
                            file_data_aray[i] = line;
                            //Toast.makeText(this, file_data_aray[i],Toast.LENGTH_SHORT).show();
                        }


                    } catch (FileNotFoundException e) {
                        //mTextLine.setText("we are inside catch block");
                        e.printStackTrace();
                    } catch (IOException e) {
                        //mTextLine.setText("we are inside 2nd catch block");
                        e.printStackTrace();
                    }

                    Toast.makeText(this, file_data_aray[0],Toast.LENGTH_SHORT).show();
                    Intent data_array=new Intent(this, MainActivity.class);
                    Bundle data_array_bundle=new Bundle();
                    data_array_bundle.putStringArray("DataArray", file_data_aray);
                    data_array.putExtras(data_array_bundle);


                    startActivity(data_array);



                    /*
                    //English_string=get_English_string(file_data_aray);
                    //French_string=get_French_string(file_data_aray);
                    Toast.makeText(this, French_string[8],Toast.LENGTH_SHORT).show();


                    //Pass data between activities;
                    //THIS IS FOR ENGLISH STRING
                    Intent english_Intent=new Intent(this, MainActivity.class);
                    //englishIntent.putExtra("englishArray", English_string);
                    Bundle english_bundle = new Bundle();
                    english_bundle.putStringArray("EnglishArray", English_string);
                    english_Intent.putExtras(english_bundle);
                    //startActivity(english_Intent);


                    //Intent frenchIntent=new Intent(this, MainActivity.class);
                    //englishIntent.putExtra("frenchArray", French_string);
                    //startActivity(frenchIntent);

                    Intent french_Intent=new Intent(this, MainActivity.class);
                    //englishIntent.putExtra("englishArray", English_string);
                    Bundle french_bundle = new Bundle();
                    french_bundle.putStringArray("FrenchArray", French_string);
                    french_Intent.putExtras(french_bundle);
                    startActivity(french_Intent);

                    */








                }


                //end of opening a file
        }

        //break;
    }


    /*
    String[] get_English_string(String[] data_array){


        for (int i=0; i<9; i++)
        {

            values = data_array[i].split(",");
            array_of_arrays[i]=values;
            english_data[i]=array_of_arrays[i][0];

        }
        return english_data;
    }




    String[] get_French_string(String data_array[]){

        for (int i=0; i<9; i++)
        {
            values = data_array[i].split(",");
            array_of_arrays[i]=values;
            french_data_here[i]=array_of_arrays[i][1];
        }

        return french_data_here;
    }

*/

}
