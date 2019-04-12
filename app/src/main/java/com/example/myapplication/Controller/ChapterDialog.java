package com.example.myapplication.Controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.R;

public class ChapterDialog extends DialogFragment {
    public static final String chapterName = "";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialoglayout, null);
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("chapter")
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                EditText nameText = (R.id.chapterName);
                            }
                        })
                .create();


    }

    private void sendResult(int resultCode, String name){
        if (getTargetFragment() == null){
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("chapterName", name);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

}
