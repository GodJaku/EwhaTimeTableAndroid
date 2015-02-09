package com.lucetek.ewhatimetable.fragment;

import com.lucetek.ewhatimetable.R;

import android.app.Fragment;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EwhaTimeTableGridFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view= inflater.inflate(R.layout.fragment_timetable, null);
		return view;
	}
}
