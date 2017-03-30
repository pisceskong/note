package com.hechao.mynote.ui;

import com.hechao.mynote.ui.R;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MyNoteEdit extends BaseActivity {
	
	/**
	 * 输入法
	 */
	private InputMethodManager imm;
	
	private Boolean isEditAble;
	
	RemoteViews views;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

		String titleContent = mPref.getString("DAT0" + mAppWidgetId, "");
		String noteContent = mPref.getString("DAT" + mAppWidgetId, "");

		//设置edit状态
		setEditState(mContentExit, noteContent);
		setEditState(mTitleEdit, titleContent);
		/*mEditText.setText(noteContent);
		mEditText.setFocusable(false);
		mEditText.setFocusableInTouchMode(false);*/
		
		mNoteTitle.setText(getResources().getString(R.string.input_tip));
		
		mAcceptNum.setText(Integer.toString(MAX_NUM - noteContent.length()));
		
		views = new RemoteViews(MyNoteEdit.this
				.getPackageName(), R.layout.my_note_widget);
		
		isEditAble = false;
		
		
		/**
		 * 单击标题框即可编辑
		 */
		mTitleEdit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!isEditAble) {
					mContentExit.setFocusable(true);
					mContentExit.setFocusableInTouchMode(true);
					editNoteContent(mTitleEdit);
					isEditAble = true;
				}
				
			}
		});
		
		/**
		 * 单击内容框即可编辑
		 */
		mContentExit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!isEditAble) {
					mTitleEdit.setFocusable(true);
					mTitleEdit.setFocusableInTouchMode(true);
					editNoteContent(mContentExit);
					isEditAble = true;
				}
				
			}
		});
		
	}
	
	@Override
	protected void showAppWidget(int srcId) {
		// TODO Auto-generated method stub
		
		views.setImageViewResource(R.id.my_widget_img, srcId);
		views.setTextViewText(R.id.my_widget_text, mTitleEdit.getText());

		AppWidgetManager appWidgetManager = AppWidgetManager
				.getInstance(MyNoteEdit.this);
		appWidgetManager.updateAppWidget(mAppWidgetId, views);

		MyNoteEdit.this.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.option_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.edit_menu:
			//editNoteContent(mEditText);
			mContentExit.setFocusable(true);
			mContentExit.setFocusableInTouchMode(true);
			editNoteContent(mTitleEdit);
			break;
		case R.id.save_menu:
			saveNoteContent();
			break;
		default:
			break;
		}
		return true;
	}

	/**
	 * 对edittext进行编辑
	 * 
	 * @param editText
	 */
	private void editNoteContent(EditText editText){
		editText.setFocusable(true);
		editText.setFocusableInTouchMode(true);
		//获取焦点
		editText.requestFocus();
		//全选
		editText.selectAll();
		
		//调出软键盘
		//imm.showSoftInput(mEditText, 0);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}
	
	/**
	 * 保存内容
	 */
	private void saveNoteContent(){
		
		if (!checkEditContent()) {
			return;
		}
		
		SharedPreferences.Editor prefsEdit = mPref.edit();
		prefsEdit.putString("DAT0" + mAppWidgetId, mTitleEdit.getText()
				.toString());
		prefsEdit.putString("DAT" + mAppWidgetId, mContentExit.getText()
				.toString());
		prefsEdit.commit();
		
		int srcId = mPref.getInt("DAT_IMAGE_ID" + mAppWidgetId, 0);
		showAppWidget(srcId);
		
		//判断是否有数据输入
		//保存成功提示
		Toast.makeText(this, getResources().getString(R.string.savesucc),
				Toast.LENGTH_SHORT).show();
		finish();
	}	
	
	/**
	 * 设置edit状态
	 * 
	 * @param editText
	 * @param str
	 */
	private void setEditState(EditText editText, String str){
		editText.setText(str);
		editText.setFocusable(false);
		editText.setFocusableInTouchMode(false);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		isEditAble = false;
}






	
	
	
}
