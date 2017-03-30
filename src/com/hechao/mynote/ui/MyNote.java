package com.hechao.mynote.ui;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

public class MyNote extends AppWidgetProvider {
	/** Called when the activity is first created. */
	
	//String mPerfName = "com.hechao.MyColorNoteConf";
	//private int srcId;
	//private String title;
	//private String content;

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		//Toast.makeText(context, "onUpdate", Toast.LENGTH_LONG).show();
		final int N = appWidgetIds.length;
		for (int i = 0; i < N; i++) {
			//Toast.makeText(context, "for " + String.valueOf(N), Toast.LENGTH_LONG).show();
			updateWidget(context, appWidgetManager, appWidgetIds[i]);
			
			Log.i("myLog", "this is [" + appWidgetIds[i] + "] onUpdate!");
		}
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		final int N = appWidgetIds.length;
		for (int i = 0; i < N; i++) {
			int appWidgetId = appWidgetIds[i];
			Log.i("myLog", "this is [" + appWidgetId + "] onDelete!");
		}
	}
	
	public void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
		
		String mPerfName = "com.hechao.MyNoteConf";
		RemoteViews views = new RemoteViews(context
				.getPackageName(), R.layout.my_note_widget);
		
		SharedPreferences spfs = context.getSharedPreferences(mPerfName, 0);
		
		int srcId = spfs.getInt("DAT_IMAGE_ID" + appWidgetId, 0);
		String title = spfs.getString("DAT0" + appWidgetId, "¼ÇÊÂ±¾");
		
//		Toast.makeText(context, String.valueOf(srcId) + " --- " + 
//		title + "   appWidgetId   " + String.valueOf(appWidgetId), Toast.LENGTH_LONG).show();
		
		views.setImageViewResource(R.id.my_widget_img, srcId);
		views.setTextViewText(R.id.my_widget_text, title);
		
		Intent intent = new Intent(context, MyNoteEdit.class);
		intent.setAction(mPerfName + appWidgetId);
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				intent, 0);
		views.setOnClickPendingIntent(R.id.my_widget_img, pendingIntent);

		appWidgetManager = AppWidgetManager
				.getInstance(context);
		
		appWidgetManager.updateAppWidget(appWidgetId, views);
	}
	

}