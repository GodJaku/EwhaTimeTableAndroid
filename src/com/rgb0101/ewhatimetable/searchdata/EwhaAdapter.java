package com.rgb0101.ewhatimetable.searchdata;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rgb0101.ewhatimetable.R;
import com.rgb0101.ewhatimetable.home.EwhaHomeActivity;

public class EwhaAdapter extends ArrayAdapter<EwhaResult>{
	private Context mContext= null;
	private ArrayList<EwhaResult> content;
	
	public EwhaAdapter(Context context, int resId, ArrayList<EwhaResult> obj){
		super(context, resId, obj);
		mContext= context;
		content= obj;
	}
	
	@Override
	public View getView(int pos, View v, ViewGroup container){
//		v= ((EwhaHomeActivity)mContext).getLayoutInflater().inflate(R.layout.listitem, container, false);
		v= ((EwhaHomeActivity)mContext).getLayoutInflater().inflate(R.layout.listitem, null);
		
		((TextView)v.findViewById(R.id.subjectListItem)).setText(content.get(pos).getSubName());
		((TextView)v.findViewById(R.id.professorListItem)).setText(content.get(pos).getProf());
		((TextView)v.findViewById(R.id.timeListItem)).setText(content.get(pos).getLecture());
		
		return v;
	}
	
	@Override
	public int getCount(){
		if(content != null) return content.size();
		else return 0;
	}
}
