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

import com.example.myapplication.Model.Userdata;
import com.example.myapplication.R;
import com.example.myapplication.View.SharedPref;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class load_new_words extends AppCompatActivity {
    int line_counter=0;
    //some required variables for the program to run
    private static final String TAG = "MainActivity";
    private static final int PICKFILE_RESULT_CODE = 1;

    private Button oldButton;
    private Button continueButton;
    private Button loadButton;

    private boolean defaultPermission=false;
    private boolean grantedPermission=false;
    private String FilePath;
    String file_string=null;
    SharedPreferences pref;// = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor;// = pref.edit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final SharedPref sharedPref;

        sharedPref = new SharedPref(this);

        if(sharedPref.loadNightModeState()) {
            setTheme(R.style.DarkMode);
        }
        else setTheme(R.style.AppTheme);

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

        continueButton = (Button) findViewById(R.id.continue_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        loadButton=(Button) findViewById(R.id.load_button);
        loadButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //ask for permission to access external files
                defaultPermission=isStoragePermissionGranted();

                Userdata data = new Userdata();
                data.deleteHashMap(load_new_words.this);

                if (defaultPermission==true || grantedPermission==true )
                {
                    Toast.makeText(load_new_words.this, "Permission is given", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");

                    //after permission is given a user should have access
                    startActivityForResult(intent,PICKFILE_RESULT_CODE);
                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            grantedPermission=true;
            Toast.makeText(load_new_words.this, "Please click the button again to load your own .csv file", Toast.LENGTH_LONG).show();

        }
        else
        {
            //modification-polina
            grantedPermission=false;
            Toast.makeText(load_new_words.this, "Permission is denied.File cannot be loaded", Toast.LENGTH_SHORT).show();
        }


    }

    public void openSelectLanguageMode() {
        //THIS SHOULD BE CHANGE TO LOAD_NEW_WORDS FILE
        Intent goLanguage = new Intent(this, SelectLanguageMode.class);
        startActivity(goLanguage);
    }

    public void openMainActivity() {
        Intent goMainActivity = new Intent(this, MainActivity.class);
        goMainActivity.putExtra("newGame", 0);
        startActivity(goMainActivity);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch(requestCode){
            case PICKFILE_RESULT_CODE:
                if(resultCode==RESULT_OK){

                    FilePath = data.getData().getPath();
                    Uri u = data.getData();
                    read_and_pass_file_string_to_pref(u);
                    Intent goOpenInstr = new Intent(this, SelectLanguageMode.class);
                    startActivity(goOpenInstr);
                }

        }

    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                //request a user to give you permission to access files
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else {
            return true;
        }
    }

    public void read_and_pass_file_string_to_pref(Uri u){
        try {
            InputStream inputStream = getContentResolver().openInputStream(u);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream));

            StringBuilder stringBuilder = new StringBuilder();
            String line=null;
            int i=0;
            line_counter=0;
            /*
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(",");
                line_counter = line_counter + 1;

            }
            */
            Userdata here=new Userdata();
            //it takes file reader and string builder as an input
            //reads file data into a string and sends that into shared pref
            //reads number of lines inside a file and sends that into shared pref
            file_string=here.read_and_pass_file_string_to_pref(reader, stringBuilder);
            line_counter=here.number_of_lines_inside_a_file();
            //file_string=stringBuilder.toString();
            inputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        put_data_into_shared_ref(1, line_counter);

    }
    //this function puts file data into "app storage" which is accesable
    //in different activities
    public void put_data_into_shared_ref(int chapter_number, int number_of_pairs){
        editor.putString("chapter ", file_string); // Storing string
        editor.commit();
        //load mode
        editor.putInt("load_mode_chose", 200);
        editor.commit();
        editor.putInt("line_counter", number_of_pairs);
        editor.commit();
    }


}
