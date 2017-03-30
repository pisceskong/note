package com.hechao.mynote.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootBroadcastReceiver extends BroadcastReceiver{
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		Log.i("myLog", "this is onBootReceive");
//		Toast.makeText(context, "开机成功", Toast.LENGTH_LONG).show();
//		Intent mIntent = new Intent();
//		mIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//		context.sendBroadcast(mIntent);
		
	}
	
}
