package com.lucetek.ewhatimetable.timetabledata;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lucetek.ewhatimetable.R;

public class EwhaTimeTableGradeAdapter extends ArrayAdapter<String>{
	private Context mContext= null;
	private ArrayList<String> content= null;
	
	public EwhaTimeTableGradeAdapter(Context context, int resId, ArrayList<String> obj){
		super(context, resId, obj);
		mContext= context;
		content= obj;
	}
	
	public EwhaTimeTableGradeAdapter(Context context, int resId, int resViewId, ArrayList<String> obj){
		super(context, resId, resViewId, obj);
		mContext= context;
		content= obj;
	}
	
	@Override
	public View getView(int position, View v, ViewGroup container){
		if(v == null){ v= LayoutInflater.from(mContext).inflate(R.layout.grade_listitem, container, false); }
		((TextView)v.findViewById(R.id.textviewCalCulatorGradeList)).setText(content.get(position));
		return v;
	}
}
