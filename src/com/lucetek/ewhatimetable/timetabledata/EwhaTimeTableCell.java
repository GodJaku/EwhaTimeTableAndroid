package com.lucetek.ewhatimetable.timetabledata;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import android.util.Log;

import com.lucetek.ewhatimetable.searchdata.EwhaResult;

public class EwhaTimeTableCell {

	private int _id= -1;
	private EwhaResult rawData= null;
	private int mDay= -1;
	private int mTime= 0;
	private String mSpot= null;
	private int mColor= -1;
	
	public EwhaTimeTableCell(){}
	public EwhaTimeTableCell(int day, int time){
		mDay= day;
		mTime= time;
	}
	public EwhaTimeTableCell(int day, int time, String spot, EwhaResult data){
		rawData= data;
		mDay= day;
		mTime= time;
		mSpot= spot;
	}
	public EwhaTimeTableCell(int day, int time, int color, String spot, EwhaResult data){
		rawData= data;
		mDay= day;
		mTime= time;
		mSpot= spot;
		mColor= color;
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
	public void setSpot(String spot){
		mSpot= spot;
	}
	
	public EwhaResult getRawData(){
		if(rawData != null) return rawData;
		else return null;
	}
	public int getDay(){ return mDay; }
	public int getTime(){ return mTime; }
	public String getSpot(){ return mSpot; }
	public int getColor(){ return mColor; }
	
	public String toString(){
		if(rawData != null){
			String str= "";
			str+=Integer.toString(mDay)+",";
			str+=Integer.toString(mTime)+",";
			str+=mSpot+",";
			str+=Integer.toString(mColor)+",";
			str+=(rawData.getSubName() != null ? rawData.getSubName() : "null")+",";
			str+=(rawData.getSubNum() != null ? rawData.getSubNum() : "null")+",";
			str+=(rawData.getClassNum() != null ? rawData.getClassNum() : "null")+",";
			str+=(rawData.getSubKind() != null ? rawData.getSubKind() : "null")+",";
			str+=(rawData.getMaj() != null ? rawData.getMaj() : "null")+",";
			str+=(rawData.getGrade() != null ? rawData.getGrade() : "null")+",";
			str+=(rawData.getProf() != null ? rawData.getProf() : "null")+",";
			str+=(rawData.getGradeValue() != null ? rawData.getGradeValue() : "null")+",";
			str+=(rawData.getTime() != null ? rawData.getTime() : "null")+",";
			str+=(rawData.getLecture() != null ? rawData.getLecture() : "null")+",";
			str+=(rawData.getClassName() != null ? rawData.getClassName() : "null")+",";
			str+=(rawData.getIsEng() != null ? rawData.getIsEng() : "null")+",";
			str+=(rawData.getStudent() != null ? rawData.getStudent() : "null")+",";
			str+=(rawData.getEtcmsg() != null ? rawData.getEtcmsg() : "null")+",";
			str+=(rawData.getKorLecturePlan() != null ? rawData.getKorLecturePlan() : "null")+",";
			str+=(rawData.getEngLecturePlan() != null ? rawData.getEngLecturePlan() : "null")+",";
			return str;
		}
		else return null;
	}
	public void parseToObject(String str){
		StringTokenizer token= new StringTokenizer(str, ",");
		Log.e(getClass().toString(), str);
		try{
			if(token.hasMoreTokens()){
				if(rawData == null) rawData= new EwhaResult();
				mDay= Integer.parseInt(token.nextToken());
				mTime= Integer.parseInt(token.nextToken());
				mSpot= token.nextToken();
				mColor= Integer.parseInt(token.nextToken());
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
		} catch (NoSuchElementException e){
			e.printStackTrace();
		}
	}
	
	private String validateCheck(String str){
		if(str == null || str.equalsIgnoreCase("null") || str.length() < 1) return null;
		else return str;
	}
}
