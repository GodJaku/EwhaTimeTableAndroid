package com.lucetek.ewhatimetable.fragment;

import java.util.ArrayList;

import com.lucetek.ewhatimetable.R;
import com.lucetek.ewhatimetable.home.EwhaHomeActivity;
import com.lucetek.ewhatimetable.timetabledata.EwhaTimeTableCell;
import com.lucetek.ewhatimetable.timetabledata.EwhaTimeTableMyTimeTable;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class EwhaTimeTableGridFragment extends Fragment {
	
	private EwhaTimeTableMyTimeTable mTimeTable= null;
	private static ArrayList<ArrayList<TextView>> dayClass= new ArrayList<ArrayList<TextView>>();
	private static ArrayList<ArrayList<EwhaTimeTableCell>> cellClass= new ArrayList<ArrayList<EwhaTimeTableCell>>();
	
	private View wholeView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		wholeView= inflater.inflate(R.layout.fragment_timetable, null);
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
		int i;
		for(i=0; i<6; i++){
			dayClass.add(new ArrayList<TextView>());
			dayClass.get(i).add(((TextView)wholeView.findViewById(R.id.monClass1+i)));
			dayClass.get(i).add(((TextView)wholeView.findViewById(R.id.monClass2+i)));
			dayClass.get(i).add(((TextView)wholeView.findViewById(R.id.monClass3+i)));
			dayClass.get(i).add(((TextView)wholeView.findViewById(R.id.monClass4+i)));
			dayClass.get(i).add(((TextView)wholeView.findViewById(R.id.monClass5+i)));
			dayClass.get(i).add(((TextView)wholeView.findViewById(R.id.monClass6+i)));
			dayClass.get(i).add(((TextView)wholeView.findViewById(R.id.monClass7+i)));
			dayClass.get(i).add(((TextView)wholeView.findViewById(R.id.monClass8+i)));
			for(int j=0; j<8; j++)
				dayClass.get(i).get(j).setOnClickListener(click);
		}
	}
	
	View.OnClickListener click= new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Toast.makeText(getActivity(), "textview clicked", Toast.LENGTH_SHORT).show();
		}
	};
	
	private void makeResource(){
		mTimeTable= ((EwhaHomeActivity)getActivity()).getTimeTable();
	}
}