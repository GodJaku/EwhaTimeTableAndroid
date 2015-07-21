package com.rgb0101.ewhatimetable.timetabledata;

import java.util.ArrayList;

import com.rgb0101.ewhatimetable.home.EwhaHomeActivity;
import com.rgb0101.ewhatimetable.searchdata.EwhaResult;

import android.content.Context;
import android.content.SharedPreferences;

public class EwhaTimeTableMyTimeTable {
	
	private Context mContext= null;

	private static ArrayList<ArrayList<EwhaTimeTableCell>> mDays= new ArrayList<ArrayList<EwhaTimeTableCell>>();

	public EwhaTimeTableMyTimeTable(Context context){ mContext= context; }
	public EwhaTimeTableMyTimeTable(Context context, SharedPreferences pref){
		mContext= context;
		getData(pref);
	}
	
	public void getData(SharedPreferences pref){
		for(int i=0; i<6; i++){
			mDays.add(new ArrayList<EwhaTimeTableCell>());
			for(int j=0; j<8; j++){
				String classString= pref.getString("day"+Integer.toString(i)+"time"+Integer.toString(j), null);
				if(classString == null || classString.equalsIgnoreCase("null")) mDays.get(i).add(new EwhaTimeTableCell());
				else mDays.get(i).add(new EwhaTimeTableCell(classString));
			}
		}
	}
	public void saveData(SharedPreferences pref){
		SharedPreferences.Editor edit= pref.edit();
		for(int i=0; i<6; i++)
			for(int j=0; j<8; j++){
				String str= mDays.get(i).get(j).toString();
				if(str != null && str.length() > 1) 
					edit.putString("day"+Integer.toString(i)+"time"+Integer.toString(j), mDays.get(i).get(j).toString());
				else edit.putString("day"+Integer.toString(i)+"time"+Integer.toString(j), "null");
			}
		edit.commit();
	}
	
	public void addSubject(int day, int time, EwhaTimeTableCell cell){ mDays.get(day).set(time, cell); }
	public void addSubject(int day, int time, String spot, String subname, int color){
		EwhaTimeTableCell cell= new EwhaTimeTableCell(day, time, spot, subname, color);
		mDays.get(day).set(time, cell);
	}
	public void addSubject(int day, int time, String spot, EwhaResult data){
		day--;time--;
		
		EwhaTimeTableCell cell= new EwhaTimeTableCell(day, time, EwhaHomeActivity.makeColor(), spot, data);
		mDays.get(day).set(time, cell);
	}
	public void addSubject(int day, int time, int color, String spot, EwhaResult data){
		day--;time--;
		
		EwhaTimeTableCell cell= new EwhaTimeTableCell(day, time, color, spot, data);
		mDays.get(day).set(time, cell);
	}
	public void removeSubject(int day, int time){
		EwhaHomeActivity.mColorUsed.set(mDays.get(day).get(time).getColor(), false);
		mDays.get(day).set(time, new EwhaTimeTableCell());
	}
	public void replaceSubject(int day, int time, EwhaTimeTableCell cell){
		mDays.get(day).set(time, cell);
	}
	
	public EwhaTimeTableCell getSubject(int day, int time){
		return mDays.get(day).get(time);
	}
}
