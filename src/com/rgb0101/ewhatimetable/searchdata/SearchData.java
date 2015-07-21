package com.rgb0101.ewhatimetable.searchdata;

import android.content.Context;

public class SearchData {
	
	private Context mContext= null;
	
	private String mYearCd= "";
	private int mSemester= -1;
	private int mSemKind= -1;
	private String mUniv= "";
	private String mMaj= "";
	private String mSubKind= "";
	private String mSubNumber= "";
	private String mSubName= "";
	private String mProfessor= "";
	private String mGrade= "";
	private int mDay= 0;
	private int mTime= 0;

	public SearchData(Context context){ mContext= context; }
	
	public void setYearCd(String yearCd){ mYearCd= yearCd; }
	public void setSemester(int sem){ mSemester= sem; }
	public void setSemKind(int sem){ mSemKind= sem; }
	public void setUniv(String univ){ mUniv= univ; }
	public void setMaj(String maj){ mMaj= maj; }
	public void setSubKind(String sub){ mSubKind= sub; }
	public void setSubNumber(String sub){ mSubNumber= sub; }
	public void setSubName(String sub){ mSubName= sub; }
	public void setProfessor(String prof){ mProfessor= prof; }
	public void setGrade(String grade){ mGrade= grade; };
	public void setDay(int day){ mDay= day; }
	public void setTime(int time){ mTime= time; }
	
	public String getYearCd(){ return mYearCd; }
	public int getSemester(){ return mSemester; }
	public int getSemKind(){ return mSemKind; }
	public String getUniv(){ return mUniv; }
	public String getMaj(){ return mMaj; }
	public String getSubKind(){ return mSubKind; }
	public String getSubNumber(){ return mSubNumber; }
	public String getSubName(){ return mSubName; }
	public String getProfessor(){ return mProfessor; }
	public String getGrade(){ return mGrade; }
	public int getDay(){ return mDay; }
	public int getTime(){ return mTime; }
}
