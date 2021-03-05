package com.example.newtimer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
public class Lap extends Fragment {
    TextView lapTimeList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lap, container, false);
        lapTimeList = (TextView) view.findViewById(R.id.laps);
        return view;
    }
    public void setText(ArrayList<String> laps){
        String result = "";
        for(int i = 0; i< laps.size(); i++){
            Log.i("results", laps.get(i));
            result += "         " + (i+1)+"." + " " + laps.get(i)+"\n";
        }
        lapTimeList.setText(result);
    }
}