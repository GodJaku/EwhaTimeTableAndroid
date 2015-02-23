package com.lucetek.ewhatimetable.timetabledata;

import java.util.ArrayList;
import java.util.Arrays;

import com.lucetek.ewhatimetable.R;
import com.lucetek.ewhatimetable.SpinnerAdapter;
import com.lucetek.ewhatimetable.home.EwhaHomeActivity;
import com.lucetek.ewhatimetable.searchdata.EwhaResult;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class EwhaTimeTableCalculatorAdapter extends ArrayAdapter<EwhaResult> {

	private Context mContext= null;
	private ArrayList<EwhaResult> content= null;
	private TextView contentText= null;
	private ArrayList<String> grade= null;
	private ArrayList<Spinner> gradeList= new ArrayList<Spinner>();
	
	private Spinner gradeSpinner= null;
	
//	static class holder{
//		String gradeValue= null;
//		int selected= -1;
//	}
	
	private Spinner mGrade= null;
	
	public EwhaTimeTableCalculatorAdapter(Context context, int resId, ArrayList<EwhaResult> obj){
		super(context, resId, obj);
		mContext= context;
		content= obj;
	}
	
	public EwhaTimeTableCalculatorAdapter(Context context, int resId, int resViewId, ArrayList<EwhaResult> obj){
		super(context, resId, resViewId, obj);
		mContext= context;
		content= obj;
	}
	
	public EwhaTimeTableCalculatorAdapter(Context context, int resId, int resViewId, ArrayList<EwhaResult> obj, ArrayList<String> grade){
		super(context, 0, obj);
		mContext= context;
		content= obj;
		this.grade= grade;
	}
	
	@Override
	public View getView(final int position, View v, ViewGroup container){
		if(v == null) v= LayoutInflater.from(mContext).inflate(R.layout.calculator_listitem, container, false);
		((TextView)v.findViewById(R.id.textviewCalculatorListSubname)).setText(content.get(position).getSubName());
		((TextView)v.findViewById(R.id.textviewCalculatorListGrade)).setText(content.get(position).getGradeValue());
		gradeSpinner= ((Spinner)v.findViewById(R.id.spinnerCalculatorList));
		gradeSpinner.setAdapter(new EwhaTimeTableGradeAdapter((EwhaHomeActivity)mContext, R.layout.grade_listitem, R.id.textviewCalCulatorGradeList, grade));
		if(content.get(position).getSelectedGrade().length() > 0) gradeSpinner.setSelection(Integer.parseInt(content.get(position).getSelectedGrade()));
		gradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
	    	public void onItemSelected(AdapterView<?> adapter, View view, int pos, long id){ content.get(position).setSelectedGrade(Integer.toString(pos)); }
			@Override public void onNothingSelected(AdapterView<?> adapter){}
		});
		return v;
	}
	
	@Override public EwhaResult getItem(int pos){ return content.get(pos); }
	@Override public int getCount(){ return content.size(); }
	@Override public long getItemId(int position){ return position; };
	
}
