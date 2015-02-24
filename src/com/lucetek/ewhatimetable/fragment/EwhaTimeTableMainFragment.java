package com.lucetek.ewhatimetable.fragment;

import java.util.ArrayList;

import com.lucetek.ewhatimetable.R;
import com.lucetek.ewhatimetable.home.EwhaHomeActivity;
import com.lucetek.ewhatimetable.timetabledata.EwhaTimeTableCell;
import com.lucetek.ewhatimetable.timetabledata.EwhaTimeTableMyTimeTable;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EwhaTimeTableMainFragment extends Fragment {
	private boolean tutorialHidden= false;
	private View wholeView;
	
	private static ArrayList<ArrayList<TextView>> dayClass= new ArrayList<ArrayList<TextView>>();
	private EwhaTimeTableMyTimeTable mTimeTable= null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		wholeView= inflater.inflate(R.layout.fragment_main, null);
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
		
		dayClass.clear();
		SharedPreferences.Editor edit= ((EwhaHomeActivity)getActivity()).getPref().edit();
		edit.putBoolean("mainTutorialHidden", tutorialHidden);
		edit.commit();
	}
	
	private void makeView(){
		int i;
		for(i=0; i<6; i++){
			dayClass.add(new ArrayList<TextView>());
				
			dayClass.get(i).add(((TextView)wholeView.findViewById(R.id.mainFragmentMonClass01+i)));
			dayClass.get(i).add(((TextView)wholeView.findViewById(R.id.mainFragmentMonClass02+i)));
			dayClass.get(i).add(((TextView)wholeView.findViewById(R.id.mainFragmentMonClass03+i)));
			dayClass.get(i).add(((TextView)wholeView.findViewById(R.id.mainFragmentMonClass04+i)));
			dayClass.get(i).add(((TextView)wholeView.findViewById(R.id.mainFragmentMonClass05+i)));
			dayClass.get(i).add(((TextView)wholeView.findViewById(R.id.mainFragmentMonClass06+i)));
			dayClass.get(i).add(((TextView)wholeView.findViewById(R.id.mainFragmentMonClass07+i)));
			dayClass.get(i).add(((TextView)wholeView.findViewById(R.id.mainFragmentMonClass08+i)));
			for(int j=0; j<8; j++) dayClass.get(i).get(j).setOnClickListener(click);
		}
		
		((CheckBox)wholeView.findViewById(R.id.tutorial01HiddenForever)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					tutorialHidden= true;
					((RelativeLayout)wholeView.findViewById(R.id.mainFragmentTutorial)).setVisibility(View.INVISIBLE);
				}
			}
		});
		((RelativeLayout)wholeView.findViewById(R.id.tutorial01HiddenOnce)).setOnClickListener(click);
	}
	private void makeResource(){
		mTimeTable= ((EwhaHomeActivity)getActivity()).getTimeTable();
		
		String str= "";
		for(int i=0; i<6; i++){
			for(int j=0; j<8; j++){
				EwhaTimeTableCell cell= mTimeTable.getSubject(i, j);
				if(cell.getSubname() != null && cell.getSubname().length()>0){
					str= "";
					if(cell.getRawData() != null)
						str+= cell.getSubname()+"\n"+(cell.getRawData().getProf() != null ? cell.getRawData().getProf()+"\n" : "")+cell.getSpot();
					else str= cell.getSubname()+"\n"+cell.getSpot();
					dayClass.get(i).get(j).setText(str);
					if(cell.getColor() >= 0) dayClass.get(i).get(j).setBackgroundColor(EwhaHomeActivity.mColor.get(cell.getColor()));
				} else {
					dayClass.get(i).get(j).setText("");
					dayClass.get(i).get(j).setBackgroundColor(getActivity().getResources().getColor(R.color.samplecolor));
				}
			}
		}
		
		tutorialHidden= ((EwhaHomeActivity)getActivity()).getPref().getBoolean("mainTutorialHidden", false);
		if(tutorialHidden) ((RelativeLayout)wholeView.findViewById(R.id.mainFragmentTutorial)).setVisibility(View.INVISIBLE);
	}
	
	View.OnClickListener click= new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			int id= v.getId();
			
			if(id == R.id.tutorial01HiddenOnce) ((RelativeLayout)wholeView.findViewById(R.id.mainFragmentTutorial)).setVisibility(View.INVISIBLE);
			else Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.mainFragmentText02), Toast.LENGTH_SHORT).show();
		}
	};
}
