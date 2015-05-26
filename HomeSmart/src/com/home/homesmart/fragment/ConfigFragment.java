package com.home.homesmart.fragment;

import java.util.Timer;

import com.home.constants.Constants;
import com.home.constants.LayoutValue;
import com.home.homesmart.R;
import com.home.homesmart.activity.ConfigActivity;
import com.home.homesmart.activity.HomeActivity;
import com.home.homesmart.activity.LockActivity;
import com.home.utils.DeviceCheck;
import com.home.utils.Logger;
import com.home.utils.PrefrenceUtils;
import com.home.utils.StringTools;
import com.home.view.CommonTitleView;
import com.home.widget.CircleProgress;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 配置页
 * 
 * @author hyc
 * 
 */
public class ConfigFragment extends Fragment {

	HomeActivity home;

	TextView no_text, err_text;

	CircleProgress circleProgress;

	EditText ssid_value;
	String ssid;

	Timer timer;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.config_layout, null);

		home = (HomeActivity) getActivity();

		if (PrefrenceUtils.getInstance(home).getSwitchState(
				Constants.GESTUREPASS)
				&& !StringTools.isNullOrEmpty(PrefrenceUtils.getInstance(home)
						.getPassValue(Constants.LOCK_KEY))) {
			Intent intent = new Intent(home, LockActivity.class);
			startActivity(intent);
		}

		no_text = (TextView) view.findViewById(R.id.no_text);
		err_text = (TextView) view.findViewById(R.id.err_text);
		no_text.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);// 下划线
		err_text.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);// 下划线

		ssid_value = (EditText) view.findViewById(R.id.ssid_value);
		ssid_value.setText(DeviceCheck.getWifiSSID(home));

		timer = new Timer();

		circleProgress = (CircleProgress) view.findViewById(R.id.submit);
		circleProgress.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// home.closeInputMethod();
				ssid = ssid_value.getText().toString();

				timer.schedule(new Job(), 1 * 1000, 1 * 1000);
			}
		});

		no_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// home.getSupportFragmentManager().beginTransaction()
				// .replace(R.id.vp_frame_container, new ConfigFragment())
				// .commit();
				ViewPagerContainerFrag gFrag = new ViewPagerContainerFrag(0);
				home.switchContent(gFrag, false, null, null);
			}
		});

		return view;
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

	@Override
	public void onResume() {
		super.onResume();

		home.setLastRightImgHide();
		home.setSwitchTitle("添加设备");
		home.setleftImgHide();
		home.setleftTextHide();
	}

	@Override
	public void onDestroy() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		super.onDestroy();
	}

}
