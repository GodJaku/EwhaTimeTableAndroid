package com.lucetek.ewhatimetable.timetabledata;

import com.lucetek.ewhatimetable.searchdata.EwhaResult;

public class EwhaTimeTableCell {

	private EwhaResult rawData= null;
	private int mDay= -1;
	private int mTime= 0;
	
	public EwhaTimeTableCell(EwhaResult data){
		rawData= data;
	}
	
	public EwhaResult getRawData(){
		if(rawData != null) return rawData;
		else return null;
	}
}
