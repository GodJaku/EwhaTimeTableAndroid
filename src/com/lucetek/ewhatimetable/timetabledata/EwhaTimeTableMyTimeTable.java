package com.lucetek.ewhatimetable.timetabledata;

import java.util.ArrayList;
import java.util.StringTokenizer;

import com.lucetek.ewhatimetable.searchdata.EwhaResult;

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
	
	private void getData(SharedPreferences pref){
		for(int i=0; i<6; i++){
			mDays.add(new ArrayList<EwhaTimeTableCell>());
			for(int j=0; j<8; j++){
				String classString= pref.getString("day"+Integer.toString(i)+"time"+Integer.toString(j), null);
				if(classString == null || classString.equalsIgnoreCase("null"))
					mDays.get(i).add(new EwhaTimeTableCell());
				else{
					mDays.get(i).add(new EwhaTimeTableCell(classString));
				}
			}
		}
	}
	private void saveData(SharedPreferences pref){
		SharedPreferences.Editor edit= pref.edit();
		for(int i=0; i<6; i++)
			for(int j=0; j<8; j++)
				edit.putString("day"+Integer.toString(i)+"time"+Integer.toString(j), mDays.get(i).get(j).toString());
	}
	
	private void addSubject(int day, int time, EwhaResult data){
		day--;
		EwhaTimeTableCell cell= new EwhaTimeTableCell(day, time, data);
		mDays.get(day).set(time, cell);
	}
}
