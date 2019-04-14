package com.example.myapplication.Controller;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Model.Userdata;
import com.example.myapplication.R;
import com.example.myapplication.View.SharedPref;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import android.support.v4.app.DialogFragment;

public class chapters extends AppCompatActivity {
    private Button add;
    private Button deleteButton;
    private GridView chapter_grid;
    private ArrayList<String> chapter_list;
    private static final String DIALOG = "chapter";

    boolean delete = false;

    private boolean defaultPermission=false;
    private boolean grantedPermission=false;
    private String FilePath;
    private static final int PICKFILE_RESULT_CODE = 1;

    String file_string=null;
    int line_counter=0;

    SharedPreferences pref;// = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor;// = pref.edit();

    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //restore state of dark/light mode selection that user selected before closing app
        final SharedPref sharedPref;

        sharedPref = new SharedPref(this);

        if(sharedPref.loadNightModeState()) {
            setTheme(R.style.DarkMode);
        }
        else setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapters);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        chapter_grid = (GridView) findViewById(R.id.c_grid);
        chapter_grid.setNumColumns(1);

        pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        editor = pref.edit();

        Userdata data = new Userdata();
        chapter_list = data.getChapterList(chapters.this);
//        chapter_list = new ArrayList<>();
//        chapter_list.add("aaa");
//        chapter_list.add("BBB");

        final ArrayAdapter<String> adapter;

        final DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        adapter = new ArrayAdapter<String>(this, R.layout.cell_menu_layout, chapter_list){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

//                if (board[position]==0 && solvable_board[position]!=0)
//                {
//                    view.setBackgroundResource(R.drawable.cell_shape_after_click);
//                }

                int width = (displayMetrics.widthPixels);
                int height = (displayMetrics.heightPixels);

                //variables for portrait mode cell dimensions
                int gridWidth = width;
                int gridHeight = gridWidth/8;

                //variables for landscape mode cell dimensions
                int gridHeightLand = height;
                int gridWidthLand = gridHeightLand+20;


                int orientation = getResources().getConfiguration().orientation;
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    // In landscape
                    view.setLayoutParams(new GridView.LayoutParams(gridWidthLand, gridHeightLand));
                } else {
                    // In portrait
                    view.setLayoutParams(new GridView.LayoutParams(gridWidth,gridHeight));
                }

                return view;
            }
        };

        chapter_grid.setAdapter(adapter);

        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chapter_list.add("NEW");
                adapter.notifyDataSetChanged();
            }
        });

        deleteButton = (Button) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete = true;
            }
        });

        chapter_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                if(chapter_list.get(position) == "NEW") {
                    if(delete == true){
                        chapter_list.remove(position);
                        adapter.notifyDataSetChanged();
                        delete = false;
                    }
                    else {
//                    FragmentManager manager = getSupportFragmentManager();
//                    ChapterDialog dialog = new ChapterDialog();
//                    dialog.show(manager, DIALOG);
                        AlertDialog.Builder builder = new AlertDialog.Builder(chapters.this);
                        builder.setTitle("Chapter Name");

                        final View v = getLayoutInflater().inflate(R.layout.dialoglayout, null);
                        builder.setView(v);

                        builder.setPositiveButton(R.string.LOAD, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText name = v.findViewById(R.id.chapterName);
                                String nameString = name.getText().toString();
                                chapter_list.set(position, nameString);
                                adapter.notifyDataSetChanged();

                                //ask for permission to access external files
                                defaultPermission = isStoragePermissionGranted();

//                            Userdata data = new Userdata();
//                            data.deleteHashMap(load_new_words.this);

                                if (defaultPermission == true || grantedPermission == true) {
                                    mPosition = position;
                                    Toast.makeText(chapters.this, "Permission is given", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                    intent.setType("*/*");

                                    //after permission is given a user should have access
                                    startActivityForResult(intent, PICKFILE_RESULT_CODE);
                                }
                            }
                        });

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        AlertDialog dialog = null;
                        dialog = builder.create();

                        dialog.show();
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                    }
                }
                else {
                    if(delete == true){
                        Userdata data = new Userdata();
                        data.deleteChapterWords(chapter_list.get(position), chapters.this);
                        chapter_list.remove(position);
                        adapter.notifyDataSetChanged();
                        delete = false;
                    }
                    else{
                        Userdata data = new Userdata();
                        String testEmpty = data.getChapterWords(chapter_list.get(position), chapters.this);
                        if(testEmpty.equals("empty")){
                            Toast.makeText(getApplicationContext(), R.string.empty_chapter, Toast.LENGTH_SHORT).show();
                        }
                        else {
                            editor.putString("chapterName", chapter_list.get(position));
                            editor.commit();
                            Intent goOpenInstr = new Intent(chapters.this, SelectLanguageMode.class);
                            startActivity(goOpenInstr);
                        }
                    }
                }
            }
        });
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
        put_data_into_shared_ref(line_counter);

    }

    public void put_data_into_shared_ref(int number_of_pairs){
        Userdata data = new Userdata();
        data.saveChapterWords(chapter_list.get(mPosition), file_string, number_of_pairs, chapters.this);
//        editor.putString("chapter ", file_string); // Storing string
//        editor.commit();
//        //load mode
        editor.putInt("load_mode_chose", 200);
        editor.commit();
        editor.putString("chapterName", chapter_list.get(mPosition));
        editor.commit();
//        editor.putInt("line_counter", number_of_pairs);
//        editor.commit();
    }

}
