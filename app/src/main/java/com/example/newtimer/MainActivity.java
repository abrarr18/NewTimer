package com.example.newtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ControlFragment.OnFragmentInteractionListener {
    Button stopStartButton;

    Timer timer;
    ControlFragment ctrlFragment;
    Lap lapTimesFragment;
    //Lap laptimesFragment;
    private TimerAsyncTask asynctask; // will use this in controller to execute timer.
    boolean timerStarted; // boolean to check whether timer should be running or not (changed by clicking on start/stop button).
    ScrollView mScrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        asynctask = new TimerAsyncTask();
        timer = new Timer();
        ctrlFragment = (ControlFragment) getSupportFragmentManager().findFragmentById(R.id.controlfragment);
        lapTimesFragment = (Lap) getSupportFragmentManager().findFragmentById(R.id.laps);
        //timerText.setMovementMethod(new ScrollingMovementMethod());
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("second", timer.getSeconds());
        outState.putInt("minute", timer.getMinutes());
        outState.putInt("hour", timer.getHours());
        outState.putStringArrayList("lapList", timer.getTimeList());
        Log.i("Saved", ""+timerStarted);
        outState.putBoolean("running", timerStarted);

    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        timerStarted = savedInstanceState.getBoolean("running");
        Log.i("Restore", ""+timerStarted);
        timer.setHr(savedInstanceState.getInt("hour"));
        timer.setMin(savedInstanceState.getInt("minute"));
        timer.setSec(savedInstanceState.getInt("second"));
        timer.setTimeList(savedInstanceState.getStringArrayList("lapList"));
        timer.calc();
        ctrlFragment.timerText.setText(String.format("%02d:%02d:%02d",timer.getHours(), timer.getMinutes(), timer.getSeconds()));
        if(timerStarted) {
            asynctask = new TimerAsyncTask();
            asynctask.execute(1);
            Log.i("Start Timer", "here");
        }
    }
    @Override
    protected void onDestroy() {
        //checking if asynctask is still runnning
        if (asynctask != null && asynctask.getStatus() == AsyncTask.Status.RUNNING) {
            //cancel the task before destroying activity
            asynctask.cancel(true);
            asynctask = null;
        }
        super.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            ctrlFragment.viewLaps.setVisibility(View.GONE);
            lapTimesFragment.setText(timer.getTimeList());
        }
        else{
            ctrlFragment.viewLaps.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onButtonClicked(int infoID){
        if(infoID == 0){
            timerStarted = true;
            ctrlFragment.startButton.setVisibility(View.GONE);
            ctrlFragment.stopButton.setVisibility(View.VISIBLE);
            asynctask = new TimerAsyncTask();
            asynctask.execute(1);
        }
        else if(infoID == 1){
            ctrlFragment.startButton.setVisibility(View.VISIBLE);
            ctrlFragment.stopButton.setVisibility(View.GONE);
            timerStarted = false;
        }
        else if(infoID == 2){
            timer.addTime(ctrlFragment.timerText.getText().toString());
            if(lapTimesFragment != null && lapTimesFragment.isInLayout()){
                lapTimesFragment.setText(timer.getTimeList());
            }
        }
        else if(infoID == 3){
            ctrlFragment.startButton.setVisibility(View.VISIBLE);
            ctrlFragment.stopButton.setVisibility(View.GONE);
            timerStarted = false;
            timer.reset();
            timer.calc();
            ctrlFragment.timerText.setText("00:00:00");
            if(lapTimesFragment != null && lapTimesFragment.isInLayout()){
                lapTimesFragment.lapTimeList.setText("");
            }
        }
        else if(infoID == 4){
            Intent i1 = new Intent(this, LapActivity.class);
            i1.putExtra("lapList", timer.getTimeList());
            startActivity(i1);
        }
    }


    private class TimerAsyncTask extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            String curr_time = String.format("%02d:%02d:%02d", values[0], values[1], values[2]); // used to properly format string.
            ctrlFragment.timerText.setText(curr_time); // update UI.
        }

        @Override
        protected Void doInBackground(Integer... times) {
            while (timerStarted) { // running boolean must be updated in controller.
                timer.calc(); // calculate time.
                publishProgress(timer.getHours(), timer.getMinutes(), timer.getSeconds()); // publish progress fomr onProgressUpdate method to be triggered.
                try {
                    Thread.sleep(1000); // sleep for 1 second (1000 milliseconds).
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            return null;
        }
    }
}