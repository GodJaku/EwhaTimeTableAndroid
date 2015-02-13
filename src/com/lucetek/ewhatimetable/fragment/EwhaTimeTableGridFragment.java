package com.lucetek.ewhatimetable.fragment;

import java.util.ArrayList;

import com.lucetek.ewhatimetable.R;
import com.lucetek.ewhatimetable.home.EwhaHomeActivity;
import com.lucetek.ewhatimetable.timetabledata.EwhaTimeTableCell;
import com.lucetek.ewhatimetable.timetabledata.EwhaTimeTableMyTimeTable;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EwhaTimeTableGridFragment extends Fragment {
	
	private EwhaTimeTableMyTimeTable mTimeTable= null;
	private static ArrayList<ArrayList<TextView>> dayClass= new ArrayList<ArrayList<TextView>>();
	private static ArrayList<ArrayList<EwhaTimeTableCell>> cellClass= new ArrayList<ArrayList<EwhaTimeTableCell>>();
	
	private View wholeView, popup;
	private PopupWindow mPopup= null;
	private int clickedRow, clickedCol;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		wholeView= inflater.inflate(R.layout.fragment_timetable, null);
		return wholeView;
	}
	
	@Override
	public void onResume(){
		super.onResume();
		
		Log.e(getClass().toString(), "on resume");
		makeView();
		makeResource();
	}
	
	@Override
	public void onPause(){
		super.onPause();
		Log.e(getClass().toString(), "on pause");
		dayClass.clear();
		cellClass.clear();
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
	}
	
	private void makeResource(){
		mTimeTable= ((EwhaHomeActivity)getActivity()).getTimeTable();
		
		for(int i=0; i<6; i++){
			for(int j=0; j<8; j++){
				EwhaTimeTableCell cell= mTimeTable.getSubject(i, j);
				if(cell.getRawData() != null){
					String str= cell.getRawData().getSubName()+"\n"+cell.getRawData().getProf()+"\n"+cell.getSpot();
					Log.d(getClass().toString(), cell.toString());
					cellClass.get(i).set(j, cell);
					dayClass.get(i).get(j).setText(str);
					if(cellClass.get(i).get(j).getColor() >= 0)
						dayClass.get(i).get(j).setBackgroundColor(EwhaHomeActivity.mColor.get(cellClass.get(i).get(j).getColor()));
				}
			}
		}
		
		Log.e(getClass().toString(), Integer.toString(dayClass.size()));
	}
	
	private void makePopup(){
		popup= getActivity().getLayoutInflater().inflate(R.layout.fragment_timetable_popup, null);
		mPopup= new PopupWindow(popup, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		mPopup.setAnimationStyle(0);
		mPopup.setFocusable(false);
		
		((Button)popup.findViewById(R.id.buttonTablePopupAccept)).setOnClickListener(click);
		((Button)popup.findViewById(R.id.buttonTablePopupClose)).setOnClickListener(click);
		mPopup.showAtLocation(popup, Gravity.CENTER, 0, 0);
	}
	
	View.OnClickListener click= new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			int i=0, j=0, id= v.getId();
			
			if(id == R.id.buttonTablePopupAccept){
				String subject= ((EditText)popup.findViewById(R.id.edittextTablePopupSubname)).getText().toString();
				String className= ((EditText)popup.findViewById(R.id.edittextTablePopupClass)).getText().toString();
				
				if(subject == null || subject.length() <1)
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.gridFragmentPopupText04), Toast.LENGTH_SHORT).show();
				else{
					mPopup.dismiss();
				}
			}
			else if(id == R.id.relative01timetablePopup || id == R.id.buttonTablePopupClose) mPopup.dismiss();
			
			id= ((TextView)v).getId();
			for(i=0; i<6; i++)
				for(j=0; j<8; j++){
					Log.d(getClass().toString(), Integer.toString(id)+"__"+Integer.toString(dayClass.get(i).get(j).getId()));
					if(id == dayClass.get(i).get(j).getId())
						break;
				}
			
			Log.d(getClass().toString(), Integer.toString(i)+"__"+Integer.toString(j));
			if(i<6 && j<8){
				if(cellClass.get(i).get(j).getRawData() != null)
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.gridFragmentPopupText03), Toast.LENGTH_SHORT).show();
				else{
					makePopup();
				}
			}
		}
	};
}