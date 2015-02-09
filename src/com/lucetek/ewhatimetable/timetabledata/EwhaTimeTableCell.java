package com.lucetek.ewhatimetable.timetabledata;

import com.lucetek.ewhatimetable.searchdata.EwhaResult;

public class EwhaTimeTableCell {

	private int _id= -1;
	private EwhaResult rawData= null;
	private int mDay= -1;
	private int mTime= 0;
	
	public EwhaTimeTableCell(EwhaResult data){
		rawData= data;
	}
	
	public void setId(int id){
		_id= id;
	}
	public void setDay(int day){
		mDay= day;
	}
	public void setTime(int time){
		mTime= time;
	}
	
	public int getId(){ return _id; };
	public EwhaResult getRawData(){
		if(rawData != null) return rawData;
		else return null;
	}
	public int getDay(){ return mDay; }
	public int getTime(){ return mTime; }
}
