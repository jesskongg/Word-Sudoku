package com.example.myapplication.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class ChooseWords {

    public String[] chooseFrench(int gridLength, HashMap<String, Integer> map, String[] frenchList) {
        if(frenchList.length < 9){
            throw new IllegalArgumentException();
        }

        Random r = new Random();
        ArrayList<String> arr = new ArrayList<>();
        for(int i = 0; i < frenchList.length; i++) {
            arr.add(frenchList[i]);
        }

        for(int i = 0; i < frenchList.length; i++) {
            if(map.containsKey(frenchList[i])){
                int num = map.get(frenchList[i]);

                for(int j = 0; j < num; j++){
                    arr.add(frenchList[i]);
                }
            }
        }

        Collections.shuffle(arr);
        String[] mFrenchList = new String[gridLength];
        boolean flag = true;
        for(int i = 0; i < gridLength; i++){
            while(flag){
                int num = r.nextInt(arr.size());
                String word = arr.get(num);
                flag = false;
//                for(int j = 0; j < 9; j++){
//                    if(mFrenchList[j].equals(word)){
//                        flag = true;
//                    }
//                }
                if(flag == false){
                    mFrenchList[i] = word;
                    for(int j = 0; j < arr.size(); j++){
                        if(word.equals(arr.get(j))){
                            arr.remove(j);
                            j--;
                        }
                    }
                }

            }
            flag = true;
        }
        return  mFrenchList;
    }

    public String[] chooseEnglish(int gridLength, String[] mFrenchList, String[] englishList, String[] frenchList){
        String[] mEnglishList = new String[gridLength];
        for(int i = 0; i < gridLength; i++){
            for(int j = 0; j < frenchList.length; j++){
                if(mFrenchList[i].equals(frenchList[j])){
                    mEnglishList[i] = englishList[j];
                    break;
                }
            }
        }
        return mEnglishList;
    }
}
