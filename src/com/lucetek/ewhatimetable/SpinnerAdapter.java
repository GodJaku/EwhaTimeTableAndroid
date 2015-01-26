package com.lucetek.ewhatimetable;

import java.util.ArrayList;

import com.lucetek.ewhatimetable.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpinnerAdapter extends ArrayAdapter<String>{
	
	private Context mContext= null;
	private ArrayList<String> content= null;
	private TextView contentText= null;
	
	public SpinnerAdapter(Context context, int resId, ArrayList<String> obj){
		super(context, resId, obj);
		mContext= context;
		content= obj;
	}
	
	public SpinnerAdapter(Context context, int resId, int resViewId, ArrayList<String> obj){
		super(context, resId, resViewId, obj);
		mContext= context;
		content= obj;
	}
	
	@Override
	public View getView(int position, View v, ViewGroup container){
		if(v == null){ v= ((EwhaTimeTableActivity)mContext).getLayoutInflater().inflate(R.layout.spinner_layout, container, false); }
		contentText= (TextView)v.findViewById(R.id.spinner_text);
		contentText.setText(content.get(position));
		return v;
	}

}
