package com.lucetek.ewhatimetable.home;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

public class DrawerAdapter extends ArrayAdapter {
	
	private Context mContext= null;
	private ArrayList mData= null;

	public DrawerAdapter(Context context, int resource, ArrayList obj) {
		super(context, resource, obj);
		mContext= context;
		mData= obj;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		return null;
	}

	@Override
	public int getCount() {
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

}
