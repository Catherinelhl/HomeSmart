package com.home.homesmart.fragment;

import com.home.homesmart.R;
import com.home.homesmart.activity.HomeActivity;
import com.home.utils.Logger;
import com.home.widget.CircleProgress;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import android.support.v4.app.FragmentManager;

/**
 * 配置开关
 * 
 * @author hyc
 * 
 */
public class ConfigSwitchFrag extends Fragment implements
		OnBackStackChangedListener {

	Button config_view;

	CircleProgress circleProgress;

	private int progress = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Logger.log("ConfigSwitchFrag onCreateView");
		View view = inflater.inflate(R.layout.switch_config_layout, container,
				false);

		config_view = (Button) view.findViewById(R.id.config_view);
		circleProgress = (CircleProgress) view
				.findViewById(R.id.circleprogress);

		config_view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 线程存在问题
				setProgressByThread();

				// setProgressByTimer();
			}
		});

		return view;
	}

	Thread thread;

	private void setProgressByThread() {
		thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (progress < 100) {
					try {
						progress += 1;
						circleProgress.setProgress(progress);
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				if (progress == 100) {
					// 配置完成
					handler.sendEmptyMessageDelayed(0, 1000);

				}

			}
		});

		thread.start();

	}

	Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {

			if (msg.what == 0) {

				if (getActivity() != null) {
					getActivity().onBackPressed();
				}

			}

		};

	};

	@Override
	public void onResume() {
		super.onResume();

		HomeActivity home = (HomeActivity) getActivity();
		home.setLastRightImgHide();
		home.setLeftImgBack();
		home.setleftTextHide();

	}

	@Override
	public void onBackStackChanged() {
		Logger.log("ConfigSwitchFrag onBackStackChanged");
		FragmentManager mFragmentManager = getActivity()
				.getSupportFragmentManager();
		FragmentTransaction tran = mFragmentManager.beginTransaction();
		tran.remove(this);
		tran.detach(this);
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		Logger.log("ConfigSwitchFrag onHiddenChanged");
		super.onHiddenChanged(hidden);
	}

	@Override
	public void onAttach(Activity activity) {
		Logger.log("ConfigSwitchFrag onAttach");
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Logger.log("ConfigSwitchFrag onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Logger.log("ConfigSwitchFrag onActivityCreated");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		Logger.log("ConfigSwitchFrag onStart");
		super.onStart();
	}

	@Override
	public void onStop() {
		Logger.log("ConfigSwitchFrag onStop");
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		Logger.log("ConfigSwitchFrag onDestroyView");
		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		Logger.log("ConfigSwitchFrag onDetach");
		super.onDetach();
	}

	@Override
	public void onDestroy() {
		Logger.log("ConfigSwitchFrag onDestroy");
		super.onDestroy();
		// HomeActivity home = (HomeActivity) getActivity();
		// home.removeSwitch();

		if (thread != null && thread.isAlive()) {
			thread.interrupt();
			thread = null;
		}
	}

}
