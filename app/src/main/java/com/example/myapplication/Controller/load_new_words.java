package com.example.myapplication.Controller;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

    String[] file_data_aray=new String[9];
    String file_string;

    SharedPreferences pref;// = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor;// = pref.edit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_new_words);

        pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        editor = pref.edit();

        oldButton = (Button) findViewById(R.id.old_button);
        oldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("load_mode_chose", 100);
                editor.commit();
                Intent goLanguageMode =new Intent(v.getContext(), SelectLanguageMode.class);
                startActivity(goLanguageMode);
            }
        });

        loadButton= (Button) findViewById(R.id.load_button);
        loadButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //ask for permission to access external files
                defaultPermission=isStoragePermissionGranted();

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
                //Log.v(TAG,"Permission is granted");
                return true;
            } else {
                //Toast.makeText(load_new_words.this, "inside storage permission function", Toast.LENGTH_LONG).show();
                //Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            //Log.v(TAG,"Permission is granted");
            return true;
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            //Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
            //Toast.makeText(load_new_words.this, "Am I here", Toast.LENGTH_SHORT).show();
            grantedPermission=true;

        }
        else
        {
            //modification-polina
            grantedPermission=false;
            Toast.makeText(load_new_words.this, "No permission.File cannot be loaded", Toast.LENGTH_SHORT).show();
        }


    }

    public void openSelectLanguageMode() {
        //THIS SHOULD BE CHANGE TO LOAD_NEW_WORDS FILE
        Intent goLanguage = new Intent(this, SelectLanguageMode.class);
        startActivity(goLanguage);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch(requestCode){
            case PICKFILE_RESULT_CODE:
                if(resultCode==RESULT_OK){
                    //FilePath = data.getData().getPath();
                    FilePath = data.getData().getPath();
                    Uri u = data.getData();

                    try {
                        InputStream inputStream = getContentResolver().openInputStream(u);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(
                                inputStream));

                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        int i=0;
                        while ((line = reader.readLine()) != null) {
                            stringBuilder.append(line);
                            stringBuilder.append(",");

                        }
                        file_string=stringBuilder.toString();

                        inputStream.close();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(this, file_data_aray[0], Toast.LENGTH_LONG).show();

                    put_data_into_shared_ref(1);
                    Intent goOpenInstr = new Intent(this, SelectLanguageMode.class);
                    startActivity(goOpenInstr);


                }

        }

    }

    public void put_data_into_shared_ref(int chapter_number){


        editor.putString("chapter "+chapter_number, file_string); // Storing string
        editor.commit();
        //load mode
        editor.putInt("load_mode_chose", 200);
        editor.commit();
    }

}
