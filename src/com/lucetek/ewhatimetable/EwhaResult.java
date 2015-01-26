package com.lucetek.ewhatimetable;

import android.content.Context;

public class EwhaResult {

	private Context mContext= null;
	
	private String subName= null;
	private String subNum= null;
	private String classNum= null;
	private String subKind= null;
	private String maj= null;
	private String grade= null;
	private String prof= null;
	private String gradeValue= null;
	private String time= null;
	private String lecture= null;
	private String className= null;
	private String isEng= null;
	private String student= null;
	private String etcmsg= null;
	
	public EwhaResult(Context context){
		mContext= context;
	}
	
	public void setSubName(String str){
		subName= str;
	}
	public String getSubName(){
		return subName;
	}
	public void setSubNum(String str){
		subNum= str;
	}
	public String getSubNum(){
		return subNum;
	}
	public void setClassNum(String str){
		classNum= str;
	}
	public String getClassNum(){
		return classNum;
	}
	public void setSubKind(String str){
		subKind= str;
	}
	public String getSubKind(){
		return subKind;
	}
	public void setMaj(String str){
		maj= str;
	}
	public String getMaj(){
		return maj;
	}
	public void setGrade(String str){
		grade= str;
	}
	public String getGrade(){
		return grade;
	}
	public void setProf(String str){
		prof= str;
	}
	public String getProf(){
		return prof;
	}
	public void setGradeValue(String str){
		gradeValue= str;
	}
	public String getGradeValue(){
		return gradeValue;
	}
	public void setTime(String str){
		time= str;
	}
	public String getTime(){
		return time;
	}
	public void setLecture(String str){
		lecture= str;
	}
	public String getLecture(){
		return lecture;
	}
	public void setClassName(String str){
		className= str;
	}
	public String getClassName(){
		return className;
	}
	public void setIsEng(String str){
		isEng= str;
	}
	public String getIsEng(){
		return isEng;
	}
	public void setStudent(String str){
		student= str;
	}
	public String getStudent(){
		return student;
	}
	public void setEtcmsg(String str){
		str.replace(',', '\n');
		etcmsg= str;
	}
	public String getEtcmsg(){
		return etcmsg;
	}
}
