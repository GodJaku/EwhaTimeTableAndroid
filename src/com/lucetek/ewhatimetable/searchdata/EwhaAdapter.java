package com.lucetek.ewhatimetable.searchdata;

import java.util.ArrayList;

import com.lucetek.ewhatimetable.EwhaTimeTableActivity;
import com.lucetek.ewhatimetable.R;
import com.lucetek.ewhatimetable.R.id;
import com.lucetek.ewhatimetable.R.layout;
import com.lucetek.ewhatimetable.home.EwhaHomeActivity;
import com.lucetek.ewhatimetable.home.EwhaHomeInterface;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EwhaAdapter extends ArrayAdapter<EwhaResult>{
	private static final String TAG= "EwhaTimeTable::EwhaAdapter";
	private Context mContext= null;
	private ArrayList<EwhaResult> content;
	
	public EwhaAdapter(Context context, int resId, ArrayList<EwhaResult> obj){
		super(context, resId, obj);
		mContext= context;
		content= obj;
	}
	
	@Override
	public View getView(int pos, View v, ViewGroup Container){
		v= ((EwhaHomeActivity)mContext).getLayoutInflater().inflate(R.layout.listitem, null);
		
		((TextView)v.findViewById(R.id.subjectListItem)).setText(content.get(pos).getSubName());
		((TextView)v.findViewById(R.id.professorListItem)).setText(content.get(pos).getProf());
		((TextView)v.findViewById(R.id.timeListItem)).setText(content.get(pos).getLecture());
		
		return v;
	}
}
