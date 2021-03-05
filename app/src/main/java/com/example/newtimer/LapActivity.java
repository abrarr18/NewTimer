package com.example.newtimer;

import android.content.res.Configuration;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class LapActivity extends AppCompatActivity {
    Lap laptimesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lap);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            finish();
            return;
        }
        Bundle bundle1 = getIntent().getExtras();
        ArrayList<String> laps = bundle1.getStringArrayList("lapList");
        laptimesList = (Lap) getSupportFragmentManager().findFragmentById(R.id.listFrag);
        laptimesList.setText(laps);
    }
}