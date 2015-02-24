package com.lucetek.ewhatimetable.fragment;

import java.io.IOException;
import java.net.URL;

import com.lucetek.ewhatimetable.R;
import com.lucetek.ewhatimetable.home.EwhaHomeActivity;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
