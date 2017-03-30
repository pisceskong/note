package com.hechao.mynote.ui;

import com.hechao.mynote.ui.R;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public abstract class BaseActivity extends Activity{
	
	/**
	 * widget的唯一标示码
	 */
	protected int mAppWidgetId;
	
	/**
	 * 标题输入框
	 */
	protected EditText mTitleEdit;
	
	/**
	 * 内容输入框
	 */
	protected EditText mContentExit;
	
	/**
	 * 选择图标按钮
	 */
	protected ImageButton mImBtn1, mImBtn2, mImBtn3, mImBtn4;
	
	/**
	 * 输入提示
	 */
	protected TextView mNoteTitle = null;
	
	/**
	 * 可输入字符数
	 */
	protected TextView mAcceptNum = null;
	
	/**
	 * 临时文件名
	 */
	protected static final String mPerfName = "com.hechao.MyNoteConf";
	
	/**
	 * 最大输入字符数
	 */
	protected static final int MAX_NUM = 140;
	
	/**
	 * 临时文件存储
	 */
	protected SharedPreferences mPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_note_conf);
		
		// Find the widget id from the intent.
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null) {
			mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
		}
		
		//获取sp
		mPref = getSharedPreferences(mPerfName,  MODE_WORLD_WRITEABLE);
		
		//初始化控件
		initView();
		
		//edit内容变化响应函数
		mContentExit.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				//实时监控输入字符数量
				int length = mContentExit.getText().length();
				int acceptLength = MAX_NUM - length;
				mAcceptNum.setText(Integer.toString(acceptLength));
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	/**
	 * 初始化控件
	 */
	private void initView(){
		mTitleEdit = (EditText) findViewById(R.id.input_title);
		mContentExit = (EditText) findViewById(R.id.input_edit);
		mNoteTitle = (TextView) findViewById(R.id.note_title);
		mAcceptNum = (TextView) findViewById(R.id.acceptnumber);
		
		mImBtn1 = (ImageButton) findViewById(R.id.ImageButton01);
		mImBtn2 = (ImageButton) findViewById(R.id.ImageButton02);
		mImBtn3 = (ImageButton) findViewById(R.id.ImageButton03);
		mImBtn4 = (ImageButton) findViewById(R.id.ImageButton04);

		mImBtn1.setOnClickListener(mBtnClick);
		mImBtn2.setOnClickListener(mBtnClick);
		mImBtn3.setOnClickListener(mBtnClick);
		mImBtn4.setOnClickListener(mBtnClick);
	}
	
	OnClickListener mBtnClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			
			if (!checkEditContent()) {
				return;
			}

			int srcId = R.drawable.sketchy_paper_008;
			switch (v.getId()) {
			case R.id.ImageButton01:
				srcId = R.drawable.sketchy_paper_003;
				break;
			case R.id.ImageButton02:
				srcId = R.drawable.sketchy_paper_004;
				break;
			case R.id.ImageButton03:
				srcId = R.drawable.sketchy_paper_007;
				break;
			case R.id.ImageButton04:
				srcId = R.drawable.sketchy_paper_011;
				break;
			}
			
			SharedPreferences.Editor prefsEdit = mPref.edit();
			prefsEdit.putString("DAT0" + mAppWidgetId, mTitleEdit.getText()
					.toString());
			prefsEdit.putString("DAT" + mAppWidgetId, mContentExit.getText()
					.toString());
			//图片id存入sp
			prefsEdit.putInt("DAT_IMAGE_ID" + mAppWidgetId, srcId);
			prefsEdit.commit();
			
			showAppWidget(srcId);
		}
	};
	
	/**
	 * 检查输入内容的有效性
	 * 
	 * @return true 输入有效
	 */
	protected boolean checkEditContent(){
		int length = mTitleEdit.getText().length();
		if (length <= 0) {
			Toast.makeText(this, getResources().getString(R.string.savefail), 
					Toast.LENGTH_SHORT).show();
			return false;
		}else {
			return true;
		}
	}
	
	/**
	 * 显示桌面控件，并实现相关跳转
	 * 
	 * @param srcId
	 */
	protected abstract void showAppWidget(int srcId);
}
