package com.hechao.mynote.ui;

import com.hechao.mynote.ui.R;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

public class MyNoteConf extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.i("myLog", " on WidgetConf ... ");

		//如果用户在没有完成配置前退出activity，widget宿主收到的通知为取消配置，然后该widget则不再被添加。
		setResult(RESULT_CANCELED);

		// If they gave us an intent without the widget id, just bail.
		if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
			finish();
		}

		mNoteTitle.setText(getResources().getString(R.string.note_title));
		
	}

	@Override
	protected void showAppWidget(int srcId) {
		// TODO Auto-generated method stub
		RemoteViews views = new RemoteViews(MyNoteConf.this
				.getPackageName(), R.layout.my_note_widget);
		views.setImageViewResource(R.id.my_widget_img, srcId);
		views.setTextViewText(R.id.my_widget_text, mTitleEdit.getText());
		
		Intent intent = new Intent(MyNoteConf.this, MyNoteEdit.class);
		intent.setAction(mPerfName + mAppWidgetId);
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
		PendingIntent pendingIntent = PendingIntent.getActivity(MyNoteConf.this, 0,
				intent, 0);
		views.setOnClickPendingIntent(R.id.my_widget_img, pendingIntent);

		AppWidgetManager appWidgetManager = AppWidgetManager
				.getInstance(MyNoteConf.this);
		appWidgetManager.updateAppWidget(mAppWidgetId, views);

		// return OK
		Intent resultValue = new Intent();
		resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
				mAppWidgetId);

		setResult(RESULT_OK, resultValue);
		finish();
	}
}