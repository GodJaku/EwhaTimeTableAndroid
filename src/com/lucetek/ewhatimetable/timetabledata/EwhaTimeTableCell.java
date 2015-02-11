package com.lucetek.ewhatimetable.timetabledata;

import java.util.StringTokenizer;

import com.lucetek.ewhatimetable.searchdata.EwhaResult;

public class EwhaTimeTableCell {

	private int _id= -1;
	private EwhaResult rawData= null;
	private int mDay= -1;
	private int mTime= 0;
	
	public EwhaTimeTableCell(){}
	public EwhaTimeTableCell(EwhaResult data){
		rawData= data;
	}
	public EwhaTimeTableCell(int day, int time){
		mDay= day;
		mTime= time;
	}
	public EwhaTimeTableCell(int day, int time, EwhaResult data){
		rawData= data;
		mDay= day;
		mTime= time;
	}
	public EwhaTimeTableCell(String str){
		parseToObject(str);
	}
	
	public void setDay(int day){
		mDay= day;
	}
	public void setTime(int time){
		mTime= time;
	}
	
	public EwhaResult getRawData(){
		if(rawData != null) return rawData;
		else return null;
	}
	public int getDay(){ return mDay; }
	public int getTime(){ return mTime; }
	
	public String toString(){
		if(rawData != null){
			String str= "";
			str=rawData.getSubName()+","+rawData.getSubNum()+","+rawData.getClassNum()+","+rawData.getSubKind()+","+rawData.getMaj()+","
					+rawData.getGrade()+","+rawData.getProf()+","+rawData.getGradeValue()+","+rawData.getTime()+","+rawData.getLecture()+","
					+rawData.getClassName()+","+rawData.getIsEng()+","+rawData.getStudent()+","+rawData.getEtcmsg()+","
					+rawData.getKorLecturePlan()+","+rawData.getEngLecturePlan()+",";
			return str;
		}
		else return null;
	}
	public void parseToObject(String str){
		StringTokenizer token= new StringTokenizer(str, ",");
		if(token.hasMoreTokens()){
			rawData.setSubName(token.nextToken());
			rawData.setSubNum(token.nextToken());
			rawData.setClassNum(token.nextToken());
			rawData.setSubKind(token.nextToken());
			rawData.setMaj(token.nextToken());
			rawData.setGrade(token.nextToken());
			rawData.setProf(token.nextToken());
			rawData.setGradeValue(token.nextToken());
			rawData.setTime(token.nextToken());
			rawData.setLecture(token.nextToken());
			rawData.setClassName(token.nextToken());
			rawData.setIsEng(token.nextToken());
			rawData.setStudent(token.nextToken());
			rawData.setEtcmsg(token.nextToken());
			rawData.setKorLecturePlan(token.nextToken());
			rawData.setEngLecturePlan(token.nextToken());
		}
	}
}
