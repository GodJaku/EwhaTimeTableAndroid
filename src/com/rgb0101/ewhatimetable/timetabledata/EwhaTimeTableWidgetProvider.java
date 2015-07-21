package com.rgb0101.ewhatimetable.timetabledata;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

import com.rgb0101.ewhatimetable.R;

public class EwhaTimeTableWidgetProvider extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager manager, int[] widgetId){
		RemoteViews remote= new RemoteViews(context.getPackageName(), R.layout.layout_widget);
		ComponentName name= new ComponentName(context, EwhaTimeTableWidgetProvider.class);
		
		manager= AppWidgetManager.getInstance(context);
		manager.updateAppWidget(name, remote);
	}
}
