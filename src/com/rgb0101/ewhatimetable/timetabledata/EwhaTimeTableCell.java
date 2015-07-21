package com.rgb0101.ewhatimetable.timetabledata;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import com.rgb0101.ewhatimetable.searchdata.EwhaResult;

public class EwhaTimeTableCell {
	public static final int NULL= -1;
	public static final int SEARCHED= 0;
	public static final int USER_INPUT= 1;

	private int _id= -1;
	private int mType= -1;
	private int mDay= -1;
	private int mTime= 0;
	private String mSubname= null;
	private String mSpot= null;
	private int mColor= -1;
	private EwhaResult rawData= null;
	
	public EwhaTimeTableCell(){}
	public EwhaTimeTableCell(int day, int time, String subname, String spot, int color){
		mType= USER_INPUT;
		mDay= day;
		mTime= time;
		mSubname= subname;
		mSpot= spot;
		mColor= color;
	}
	public EwhaTimeTableCell(int day, int time, int color, String spot, EwhaResult data){
		mType= SEARCHED;
		rawData= data;
		mDay= day;
		mTime= time;
		mSubname= rawData.getSubName();
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
	public void setSubname(String subname){
		mSubname= subname;
	}
	public void setSpot(String spot){
		mSpot= spot;
	}
	
	public EwhaResult getRawData(){
		if(rawData != null) return rawData;
		else return null;
	}
	public int getType(){ return mType; }
	public int getDay(){ return mDay; }
	public int getTime(){ return mTime; }
	public String getSubname(){ return mSubname; }
	public String getSpot(){ return mSpot; }
	public int getColor(){ return mColor; }
	
	public String toString(){
		if(mType == SEARCHED || mType == USER_INPUT){
			String str= "";
			str+=Integer.toString(mType)+",";
			str+=Integer.toString(mDay)+",";
			str+=Integer.toString(mTime)+",";
			str+=mSpot+",";
			str+=Integer.toString(mColor)+",";
			str+=(mSubname != null ? mSubname : "null")+",";
			if(mType == SEARCHED && rawData != null){
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
			}
			return str;
		}
		else return null;
	}
	//
	public void parseToObject(String str){
		StringTokenizer token= new StringTokenizer(str, ",");
		try{
			if(token.hasMoreTokens()){
				mType= Integer.parseInt(token.nextToken());
				mDay= Integer.parseInt(token.nextToken());
				mTime= Integer.parseInt(token.nextToken());
				mSpot= token.nextToken();
				mColor= Integer.parseInt(token.nextToken());
				mSubname= token.nextToken();
				
				if(mType == SEARCHED){
					if(rawData == null) rawData= new EwhaResult();
					rawData.setSubName(mSubname);
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
		} catch (NoSuchElementException e){
			e.printStackTrace();
		}
	}
}
