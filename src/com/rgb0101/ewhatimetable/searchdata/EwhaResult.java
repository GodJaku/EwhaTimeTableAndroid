package com.rgb0101.ewhatimetable.searchdata;

import java.util.StringTokenizer;

import android.content.Context;

public class EwhaResult {

	private Context mContext= null;
	
	private String subName= null;				// 강좌명
	private String subNum= null;				// 학수번호
	private String classNum= null;				// 분반
	private String subKind= null;				// 교과목구분
	private String maj= null;					// 개설학과
	private String grade= null;					// 학년
	private String prof= null;					// 교수명
	private String gradeValue= null;			// 학점
	private String time= null;					// 시간
	private String lecture= null;				// 요일
	private String className= null;				// 교실
	private String isEng= null;					// 영어강의
	private String student= null;				// 수강제한인원
	private String etcmsg= null;				// 비고
	private String korLecturePlan= null;		// 국문강의계획서
	private String engLecturePlan= null;		// 영문강의계획서
	
	private int selectedGrade= -1;
	
	public EwhaResult(){}
	public EwhaResult(Context context){ mContext= context; }
	
	public void setSubName(String str){ subName= str; }
	public String getSubName(){ return subName; }
	public void setSubNum(String str){ subNum= str; }
	public String getSubNum(){ return subNum; }
	public void setClassNum(String str){ classNum= str; }
	public String getClassNum(){ return classNum; }
	public void setSubKind(String str){ subKind= str; }
	public String getSubKind(){ return subKind; }
	public void setMaj(String str){ maj= str; }
	public String getMaj(){ return maj; }
	public void setGrade(String str){ grade= str; }
	public String getGrade(){ return grade; }
	public void setProf(String str){ prof= str; }
	public String getProf(){ return prof; }
	public void setGradeValue(String str){ gradeValue= str; }
	public String getGradeValue(){ return gradeValue; }
	public void setTime(String str){ time= str; }
	public String getTime(){ return time; }
	public void setLecture(String str){ lecture= str; }
	public String getLecture(){ return lecture; }
	public void setClassName(String str){ className= str; }
	public String getClassName(){ return className; }
	public void setIsEng(String str){ isEng= str; }
	public String getIsEng(){ return isEng; }
	public void setStudent(String str){ student= str; }
	public String getStudent(){ return student; }
	public void setEtcmsg(String str){
		str.replace(',', '\n');
		etcmsg= str;
	}
	public String getEtcmsg(){ return etcmsg; }
	public void setKorLecturePlan(String str){ korLecturePlan= str; }
	public String getKorLecturePlan(){ return korLecturePlan; }
	public void setEngLecturePlan(String str){ engLecturePlan= str; }
	public String getEngLecturePlan(){ return engLecturePlan; }
	public void setSelectedGrade(int index){ selectedGrade= index; }
	public int getSelectedGrade(){ return selectedGrade; }
	
	public String toString(){
		String str= "";
		str=getSubName()+","+getSubNum()+","+getClassNum()+","+getSubKind()+","+getMaj()+","
				+getGrade()+","+getProf()+","+getGradeValue()+","+getTime()+","+getLecture()+","
				+getClassName()+","+getIsEng()+","+getStudent()+","+getEtcmsg()+","
				+getKorLecturePlan()+","+getEngLecturePlan(); //+","+getSelectedGrade();
		return str;
	}
	public void parseToObject(String str){
		StringTokenizer token= new StringTokenizer(str, ",");
		if(token.hasMoreTokens()){
			setSubName(token.nextToken());
			setSubNum(token.nextToken());
			setClassNum(token.nextToken());
			setSubKind(token.nextToken());
			setMaj(token.nextToken());
			setGrade(token.nextToken());
			setProf(token.nextToken());
			setGradeValue(token.nextToken());
			setTime(token.nextToken());
			setLecture(token.nextToken());
			setClassName(token.nextToken());
			setIsEng(token.nextToken());
			setStudent(token.nextToken());
			setEtcmsg(token.nextToken());
			setKorLecturePlan(token.nextToken());
			setEngLecturePlan(token.nextToken());
//			setSelectedGrade(token.nextToken());
		}
	}
}
