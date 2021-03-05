package com.example.newtimer;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
public class ControlFragment extends Fragment implements View.OnClickListener{
    Button startButton;
    Button rstButton;
    Button lapButton;
    Button viewLaps;
    Button stopButton;
    boolean running; // boolean to check whether timer should be running or not (changed by clicking on start/stop button).
    Timer timer; // timer used in AsyncTask and Controller.
    TextView timerText;
    private OnFragmentInteractionListener mListener;
    public ControlFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        if(container != null){
            container.removeAllViewsInLayout();
        }
        int orientation = getActivity().getResources().getConfiguration().orientation;


        // Inflate the layout for this fragment
        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            view = inflater.inflate(R.layout.fragment_control, container, false);
        }
        else{
            view = inflater.inflate(R.layout.fragment_control, container, false);
        }

        timerText = (TextView) view.findViewById(R.id.timerTexter);
        startButton = (Button) view.findViewById(R.id.startButton);
        stopButton = (Button) view.findViewById(R.id.stopButton);
        rstButton = (Button) view.findViewById(R.id.resetButton);
        lapButton = (Button) view.findViewById(R.id.lapButton);
        viewLaps = (Button) view.findViewById(R.id.viewLap);
        //Add listeners
        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        rstButton.setOnClickListener(this);
        lapButton.setOnClickListener(this);
        viewLaps.setOnClickListener(this);
        return view;
    }
    @Override
    public void onClick(View view) {
        if(view.getId() == startButton.getId()){
            mListener.onButtonClicked(0);
        }
        else if(view.getId() == stopButton.getId()){
            mListener.onButtonClicked(1);
        }
        else if(view.getId() == lapButton.getId()){
            mListener.onButtonClicked(2);
        }
        else if(view.getId() == rstButton.getId()){
            mListener.onButtonClicked(3);
        }
        else if(view.getId() == viewLaps.getId()){
            mListener.onButtonClicked(4);
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof OnFragmentInteractionListener){
            this.mListener= (OnFragmentInteractionListener) context;
        }else{
            throw new RuntimeException(context.toString()+" must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
    public interface OnFragmentInteractionListener{
        void onButtonClicked(int infoID);
    }

}