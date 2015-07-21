package com.rgb0101.ewhatimetable.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rgb0101.ewhatimetable.R;

public class EwhaTimeTableAboutDeveloperFragment extends Fragment {
	private View wholeView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		wholeView= inflater.inflate(R.layout.fragment_aboutdeveloper, null);
		return wholeView;
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
		((ImageView)wholeView.findViewById(R.id.developerProfile)).setImageDrawable(getActivity().getResources().getDrawable(R.drawable.profile));
	}
	private void makeResource(){
	}
}
