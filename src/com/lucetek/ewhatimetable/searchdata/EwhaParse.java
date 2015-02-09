package com.lucetek.ewhatimetable.searchdata;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.lucetek.ewhatimetable.EwhaTimeTableActivity;
import com.lucetek.ewhatimetable.R;
import com.lucetek.ewhatimetable.R.id;
import com.lucetek.ewhatimetable.home.EwhaHomeActivity;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Spinner;
import android.widget.Toast;

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
		
//		if(content1.size() < 5)
//			Toast.makeText(((EwhaTimeTableActivity)mContext).getApplicationContext(), ((EwhaTimeTableActivity)mContext).getResources().getString(R.string.noresult), Toast.LENGTH_SHORT).show();
//		else{
			ArrayList<EwhaResult> resultList= ((EwhaHomeActivity)mContext).getSearchFragment().getResult();
			if(resultList == null) resultList= new ArrayList<EwhaResult>();
			else resultList.clear();
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
					temp += (j == 0 ? "" : "\n");
					if(j < a.length) temp += a[j] + " ";
					if(j < b.length) temp += b[j] + " / ";
					if(j < c.length) temp += c[j];
				}
				result.setLecture(temp);
				temp= "";
				
				
				
				result.setIsEng(content1.get(i*19 + 15).text());
				result.setStudent(content1.get(i*19 + 18).text());
				
				result.setProf(content2.get(i*3+1).text());
				
				a= content2.get(i*3 + 2).text().split(",");
				for(j=0; j<a.length; j++)
					temp += (j == 0 ? "" : "\n") + a[j];
				result.setEtcmsg(temp);
				
				j= ((Spinner)((EwhaHomeActivity)mContext).getSearchFragment().getView().findViewById(R.id.gradeCondition)).getSelectedItemPosition();
				temp= (j == 0 ? "" : (Integer.toString(j)) + ".0");
				if(temp.equals("") || (!temp.equals("") && temp.length() > 0 && result.getGradeValue().equals(temp)))
					resultList.add(result);
			}
			
			((EwhaHomeActivity)mContext).getSearchFragment().setResult(resultList);
//		}
	}
	
}
