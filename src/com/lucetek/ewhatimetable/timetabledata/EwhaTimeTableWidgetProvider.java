package com.lucetek.ewhatimetable.timetabledata;

import com.lucetek.ewhatimetable.R;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

public class EwhaTimeTableWidgetProvider extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager manager, int[] widgetId){
		RemoteViews remote= new RemoteViews(context.getPackageName(), R.layout.layout_widget);
		ComponentName name= new ComponentName(context, EwhaTimeTableWidgetProvider.class);
		
		manager= AppWidgetManager.getInstance(context);
		manager.updateAppWidget(name, remote);
	}
}
