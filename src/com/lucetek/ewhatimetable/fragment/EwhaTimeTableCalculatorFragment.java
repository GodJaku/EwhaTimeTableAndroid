package com.lucetek.ewhatimetable.fragment;

import java.util.ArrayList;
import java.util.Arrays;

import com.lucetek.ewhatimetable.R;
import com.lucetek.ewhatimetable.home.EwhaHomeActivity;
import com.lucetek.ewhatimetable.searchdata.EwhaResult;
import com.lucetek.ewhatimetable.timetabledata.EwhaTimeTableCalculatorAdapter;
import com.lucetek.ewhatimetable.timetabledata.EwhaTimeTableCell;
import com.lucetek.ewhatimetable.timetabledata.EwhaTimeTableMyTimeTable;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class EwhaTimeTableCalculatorFragment extends Fragment{
	
	private ListView mSubject= null;
	private EwhaTimeTableMyTimeTable mTimeTable= null;
	private ArrayList<EwhaResult> mContent= new ArrayList<EwhaResult>();
	private EwhaTimeTableCalculatorAdapter mAdapter= null;
	private ArrayList<String> gradeList= null;
	double[] gradeArray= {4.3, 4.0, 3.7, 3.3, 3.0, 2.7, 2.3, 2.0, 1.7, 1.3, 1.0, 0.7, 0.0, 0.0};
	
	private View wholeView= null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		wholeView= inflater.inflate(R.layout.fragment_calculator, null);
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
		mContent.clear();
	}
	
	private void makeView(){
		mSubject= (ListView)wholeView.findViewById(R.id.listviewCalculator);
		((TextView)wholeView.findViewById(R.id.buttonCalculate)).setOnClickListener(click);
	}
	
	private void makeResource(){
		mTimeTable= ((EwhaHomeActivity)getActivity()).getTimeTable();
		
		for(int i=0; i<6; i++){
			for(int j=0; j<8; j++){
				EwhaTimeTableCell cell= mTimeTable.getSubject(i, j);
				if(cell.getRawData() != null && !existedSubject(cell.getRawData())) {
					Log.d("test", cell.getSubname()+"___"+cell.getRawData().getGradeValue());
					mContent.add(cell.getRawData());
				}
			}
		}
		gradeList= new ArrayList<String>(Arrays.asList(getActivity().getResources().getStringArray(R.array.gradelist)));
		mAdapter= new EwhaTimeTableCalculatorAdapter(getActivity(), R.layout.calculator_listitem, R.id.textviewCalculatorListSubname, mContent, gradeList);
		if(mSubject != null) mSubject.setAdapter(mAdapter);
	}
	
	private boolean existedSubject(EwhaResult dat){
		for(int i=0; i<mContent.size(); i++) if(mContent.get(i).toString().equalsIgnoreCase(dat.toString())) return true;
		return false;
	}
	
	View.OnClickListener click= new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			int i, id= v.getId();
			
			if(id == R.id.buttonCalculate){
				double result= 0.0, timetotal= 0.0;
				for(i=0; i<mContent.size(); i++){
					result += gradeArray[mContent.get(i).getSelectedGrade()] * Double.parseDouble(mAdapter.getItem(i).getGradeValue());
					timetotal += Double.parseDouble(mAdapter.getItem(i).getGradeValue());
				}
				result /= timetotal;
				((TextView)wholeView.findViewById(R.id.textviewCalculateResult)).setText(getActivity().getResources().getString(R.string.calculateFragmentResultText)+" "+String.format("%.2f", result));
			}
		}
	};
}
