package com.lucetek.ewhatimetable.fragment;

import com.lucetek.ewhatimetable.R;
import com.lucetek.ewhatimetable.timetabledata.EwhaTimeTableMyTimeTable;

import android.app.Fragment;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EwhaTimeTableGridFragment extends Fragment {
	
	private EwhaTimeTableMyTimeTable mTimeTable= null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view= inflater.inflate(R.layout.fragment_timetable, null);
		return view;
	}
	
	@Override
	public void onResume(){
		super.onResume();
		
		makeView();
		makeResource();
	}
	
	@Override
	public void onPause(){
		super.onPause();
	}
	
	private void makeView(){
		
	}
	
	private void makeResource(){
		mTimeTable= new EwhaTimeTableMyTimeTable();
	}
}