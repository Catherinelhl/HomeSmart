package com.home.homesmart.activity;

import java.util.Timer;

import com.home.constants.Constants;
import com.home.constants.LayoutValue;
import com.home.homesmart.R;
import com.home.utils.DeviceCheck;
import com.home.utils.Logger;
import com.home.utils.PrefrenceUtils;
import com.home.utils.StringTools;
import com.home.view.CommonTitleView;
import com.home.widget.CircleProgress;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 配置页
 * 
 * @author hyc
 * 
 */
public class ConfigActivity extends Activity {

	TextView no_text, err_text;

	CircleProgress circleProgress;

	EditText ssid_value;
	String ssid;

	Timer timer;

	CommonTitleView commmonTitleView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.config_layout);

		if (PrefrenceUtils.getInstance(this).getSwitchState(
				Constants.GESTUREPASS)
				&& !StringTools.isNullOrEmpty(PrefrenceUtils.getInstance(this)
						.getPassValue(Constants.LOCK_KEY))) {
			Intent intent = new Intent(this, LockActivity.class);
			startActivity(intent);
		}

		commmonTitleView = (CommonTitleView) findViewById(R.id.toplayout);
		commmonTitleView.setVisibility(View.VISIBLE);
		commmonTitleView.setHeadTitleText("添加设备");

		no_text = (TextView) findViewById(R.id.no_text);
		err_text = (TextView) findViewById(R.id.err_text);
		no_text.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);// 下划线
		err_text.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);// 下划线

		ssid_value = (EditText) findViewById(R.id.ssid_value);

		ssid_value.setText(DeviceCheck.getWifiSSID(this));

		timer = new Timer();

		circleProgress = (CircleProgress) findViewById(R.id.submit);
		circleProgress.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				closeInputMethod();
				ssid = ssid_value.getText().toString();

				timer.schedule(new Job(), 1 * 1000, 1 * 1000);
			}
		});

		no_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent it = new Intent(ConfigActivity.this, HomeActivity.class);
				startActivity(it);
				finish();
			}
		});
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);

		// 获取状态栏高度
		Rect frame = new Rect();
		getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		LayoutValue.statusBarHeight = frame.top;

		LayoutValue.titleViewHeight = commmonTitleView.getHeight();
		Logger.log("statusBarHeight:" + LayoutValue.statusBarHeight
				+ ",titleViewHeight:" + LayoutValue.titleViewHeight);

	}

	class Job extends java.util.TimerTask {

		@Override
		public void run() {

			handler.sendEmptyMessage(0);

		}

	}

	int progress = 0;

	Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			progress += 1;
			circleProgress.setProgress(progress);
		};

	};

	public void closeInputMethod() {
		InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	@Override
	protected void onDestroy() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		super.onDestroy();
	}
}
