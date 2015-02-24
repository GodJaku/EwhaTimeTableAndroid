package com.lucetek.ewhatimetable.fragment;

import java.util.ArrayList;

import com.lucetek.ewhatimetable.R;
import com.lucetek.ewhatimetable.home.EwhaHomeActivity;
import com.lucetek.ewhatimetable.timetabledata.EwhaTimeTableCell;
import com.lucetek.ewhatimetable.timetabledata.EwhaTimeTableMyTimeTable;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EwhaTimeTableGridFragment extends Fragment {
	private boolean tutorialHidden= false;
	
	private EwhaTimeTableMyTimeTable mTimeTable= null;
	private static ArrayList<ArrayList<TextView>> dayClass= new ArrayList<ArrayList<TextView>>();
	private static ArrayList<ArrayList<EwhaTimeTableCell>> cellClass= new ArrayList<ArrayList<EwhaTimeTableCell>>();
	
	private View wholeView, popup;
	private PopupWindow mPopup= null;
	private int clickedTime, clickedDay;

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
		dayClass.clear();
		cellClass.clear();
		
		SharedPreferences.Editor edit= ((EwhaHomeActivity)getActivity()).getPref().edit();
		edit.putBoolean("gridTutorialHidden", tutorialHidden);
		edit.commit();
	}
	
	private void makeView(){
		int i;
		for(i=0; i<6; i++){
			dayClass.add(new ArrayList<TextView>());
			cellClass.add(new ArrayList<EwhaTimeTableCell>());
				
			dayClass.get(i).add(((TextView)wholeView.findViewById(R.id.monClass1+i)));
			dayClass.get(i).add(((TextView)wholeView.findViewById(R.id.monClass2+i)));
			dayClass.get(i).add(((TextView)wholeView.findViewById(R.id.monClass3+i)));
			dayClass.get(i).add(((TextView)wholeView.findViewById(R.id.monClass4+i)));
			dayClass.get(i).add(((TextView)wholeView.findViewById(R.id.monClass5+i)));
			dayClass.get(i).add(((TextView)wholeView.findViewById(R.id.monClass6+i)));
			dayClass.get(i).add(((TextView)wholeView.findViewById(R.id.monClass7+i)));
			dayClass.get(i).add(((TextView)wholeView.findViewById(R.id.monClass8+i)));
			for(int j=0; j<8; j++){
				dayClass.get(i).get(j).setOnClickListener(click);
				cellClass.get(i).add(new EwhaTimeTableCell());
			}
		}
		
		((CheckBox)wholeView.findViewById(R.id.tutorial03HiddenForever)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(arg1){
					tutorialHidden= true;
					((RelativeLayout)wholeView.findViewById(R.id.gridFragmentTutorial)).setVisibility(View.INVISIBLE);
				}
			}
		});
		((RelativeLayout)wholeView.findViewById(R.id.tutorial03HiddenOnce)).setOnClickListener(click);
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
					cellClass.get(i).set(j, cell);
					dayClass.get(i).get(j).setText(str);
					if(cellClass.get(i).get(j).getColor() >= 0){
						dayClass.get(i).get(j).setBackgroundColor(EwhaHomeActivity.mColor.get(cellClass.get(i).get(j).getColor()));
						EwhaHomeActivity.mColorUsed.set(cellClass.get(i).get(j).getColor(), true);
					}
				} else {
					cellClass.get(i).set(j, new EwhaTimeTableCell());
					dayClass.get(i).get(j).setText("");
					dayClass.get(i).get(j).setBackgroundColor(getActivity().getResources().getColor(R.color.samplecolor));
				}
			}
		}
		
		tutorialHidden= ((EwhaHomeActivity)getActivity()).getPref().getBoolean("gridTutorialHidden", false); 
		if(tutorialHidden) ((RelativeLayout)wholeView.findViewById(R.id.gridFragmentTutorial)).setVisibility(View.INVISIBLE);
	}
	
	private void makeAddPopup(){
		popup= getActivity().getLayoutInflater().inflate(R.layout.fragment_timetable_popup, null);
		mPopup= new PopupWindow(popup, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		mPopup.setAnimationStyle(0);
		mPopup.setFocusable(true);
		
		((Button)popup.findViewById(R.id.buttonTablePopupAccept)).setOnClickListener(click);
		((Button)popup.findViewById(R.id.buttonTablePopupClose)).setOnClickListener(click);
		mPopup.showAtLocation(popup, Gravity.CENTER, 0, 0);
	}
	
	private void makeDeletePopup(){
		popup= getActivity().getLayoutInflater().inflate(R.layout.fragment_timetable_delete_popup, null);
		mPopup= new PopupWindow(popup, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		mPopup.setAnimationStyle(0);
		mPopup.setFocusable(true);
		
		((Button)popup.findViewById(R.id.buttonTableDeletePopupClose)).setOnClickListener(click);
		((Button)popup.findViewById(R.id.buttonTableDeletePopupDelete)).setOnClickListener(click);
		mPopup.showAtLocation(popup, Gravity.CENTER, 0, 0);
	}
	
	View.OnClickListener click= new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			int i=0, j=0, id= v.getId();
			
			if(id == R.id.tutorial03HiddenOnce) ((RelativeLayout)wholeView.findViewById(R.id.gridFragmentTutorial)).setVisibility(View.INVISIBLE);
			else if(id == R.id.buttonTablePopupAccept){
				String subject= ((EditText)popup.findViewById(R.id.edittextTablePopupSubname)).getText().toString();
				String className= ((EditText)popup.findViewById(R.id.edittextTablePopupClass)).getText().toString();
				
				if(subject == null || subject.length() <1) Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.gridFragmentPopupText04), Toast.LENGTH_SHORT).show();
				else{
					if(className == null || className.length()<1) className= " ";
					mTimeTable.addSubject(clickedDay, clickedTime, subject, className, EwhaHomeActivity.makeColor());
				}
				
				mPopup.dismiss();
				((EwhaHomeActivity)getActivity()).refreshTimeTable();
				makeResource();
			}
			else if(id == R.id.buttonTableDeletePopupDelete){
				EwhaTimeTableCell cell= cellClass.get(clickedDay).get(clickedTime);
				if(cell.getType() == 0){
					for(i=0; i<6; i++){
						for(j=0; j<8; j++)
							if((i != clickedDay && j != clickedTime) && cellClass.get(i).get(j).getRawData() != null)
								if(cell.getRawData().toString().equalsIgnoreCase(cellClass.get(i).get(j).getRawData().toString()))
									mTimeTable.removeSubject(i, j);
					}	
				}
				mTimeTable.removeSubject(clickedDay, clickedTime);
				
				mPopup.dismiss();
				((EwhaHomeActivity)getActivity()).refreshTimeTable();
				makeResource();
			}
			else if(id == R.id.relative01timetablePopup || id == R.id.buttonTablePopupClose
					|| id == R.id.relative01timetableDeletePopup || id == R.id.buttonTableDeletePopupClose) mPopup.dismiss();
			else{
				for(clickedDay=0; clickedDay<6; clickedDay++){
					clickedTime= findObjectFromArrayList(dayClass.get(clickedDay), (TextView)v);
					if(clickedTime != -1)
						break;
				}
				if(clickedDay<6 && clickedTime<8){
					EwhaTimeTableCell cell= cellClass.get(clickedDay).get(clickedTime);
//					String str=cellClass.get(clickedDay).get(clickedTime).getSubname();
					String str= cell.getSubname();
					if(str == null || str.length()<1) makeAddPopup();
					else makeDeletePopup(); //Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.gridFragmentPopupText03), Toast.LENGTH_SHORT).show();
				}
			}
		}
	};
	
	private int findObjectFromArrayList(ArrayList<TextView> list, TextView v){
		return list.indexOf(v);
	}
}