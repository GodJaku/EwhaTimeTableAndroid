package com.lucetek.ewhatimetable;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.content.Context;
import android.util.Log;

public class EwhaParse {
	private static final String TAG= "EwhaTimeTableActivity::EwhaParse";
	
	private Context mContext= null;
	private Document doc= null;

	public EwhaParse(Context context){
		mContext= context;
	}
	
	public EwhaParse(Context context, String html){
		mContext= context;
		doc= Jsoup.parse(html);
		
		parseSource();
	}
	
	public void parseSource(){
		Elements content1= doc.select(".th1C");
		Elements content2= doc.select(".th1L");
		Elements day= doc.select(".th1CC");
		String temp= "";
		
//		if(content1.size() < 5){
//			((EwhaTimeTableActivity)mContext).isLast = true;
//			return ;
//		}

		ArrayList<EwhaResult> resultList= ((EwhaTimeTableActivity)mContext).getResult();
		if(resultList == null)
			resultList= new ArrayList<EwhaResult>();
		for(int i=0; i<doc.select(".th1L > a").size(); i++){
			int j, max;
			temp= "";
			EwhaResult result= new EwhaResult(mContext);
			
			result.setSubName(content2.get(i*3).text());
			
			result.setSubNum(content1.get(i*19 + 1).text());
			result.setClassNum(content1.get(i*19 + 2).text());
			String str= content1.get(i*19 + 4).text();
			if(str.length() > 2) result.setSubKind(str);
			else result.setSubKind(content1.get(i*19 + 3).text());
			result.setMaj(content1.get(i*19 + 5).text());
			result.setGrade(content1.get(i*19 + 6).text());
			result.setGradeValue(content1.get(i*19 + 7).text());
			result.setTime(content1.get(i*19 + 8).text());
			
			String[] a= day.get(i).text().split(" ");
			String[] b= content1.get(i*19 + 9).text().split(" ");
			String[] c= content1.get(i*19 + 10).text().split(" ");
			
			max= a.length;
			if(max < b.length) max= b.length;
			if(max < c.length) max= c.length;
			
			for(j=0; j<max; j++){
//				Log.e(TAG, a[j] + " :: " + b[j] + " :: " + c[j]);
				temp += (j == 0 ? "" : "\n"); // + a[j]+" "+b[j] + " / "+c[j];
				if(j < a.length) temp += a[j] + " ";
				if(j < b.length) temp += b[j] + " / ";
				if(j < c.length) temp += c[j];
			}
//			Log.e(TAG, "=============================" + a.length + " :: " + j);
			result.setLecture(temp);
			temp= "";
//			result.setLecture(day.get(i).text() + " " + content1.get(i*19 + 9).text());
//			Log.e(TAG, content1.get(i*19 + 10).text());
//			temp= content1.get(i*19 + 10).text().replace(' ', '\n');
//			result.setClassName(content1.get(i*19 + 10).text().replace(' ', '\n'));
			result.setIsEng(content1.get(i*19 + 15).text());
			result.setStudent(content1.get(i*19 + 18).text());
			
			result.setProf(content2.get(i*3+1).text());
			
			a= content2.get(i*3 + 2).text().split(",");
			for(j=0; j<a.length; j++)
				temp += (j == 0 ? "" : "\n") + a[j];
//			result.setEtcmsg(content2.get(i*3 + 2).text());
			result.setEtcmsg(temp);
			
//			Log.e(TAG, result.getSubName());
//			Log.e(TAG, result.getSubNum());
//			Log.e(TAG, result.getClassNum());
//			Log.e(TAG, result.getSubKind());
//			Log.e(TAG, result.getMaj());
//			Log.e(TAG, result.getGrade());
//			Log.e(TAG, result.getGradeValue());
//			Log.e(TAG, result.getTime());
//			Log.e(TAG, result.getLecture());
//			Log.e(TAG, result.getClassName());
//			Log.e(TAG, result.getIsEng());
//			Log.e(TAG, result.getStudent());
//			Log.e(TAG, result.getProf());
//			Log.e(TAG, result.getEtcmsg());
//			Log.e(TAG, "=================================================");
			resultList.add(result);
		}
		
		((EwhaTimeTableActivity)mContext).setResult(resultList);
	}
	
}
