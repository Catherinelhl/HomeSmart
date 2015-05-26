package com.home.homesmart.fragment;

import com.home.constants.HelpInfo;
import com.home.constants.Switch;
import com.home.homesmart.R;
import com.home.homesmart.activity.HomeActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 使用说明
 * 
 * @author hyc
 * 
 */
public class IntroDetailFrag extends Fragment {
	HomeActivity home;

	HelpInfo info;

	TextView tv_title, tv_content;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_content, null);

		home = (HomeActivity) getActivity();

		tv_title = (TextView) view.findViewById(R.id.tv_title);
		tv_content = (TextView) view.findViewById(R.id.tv_content);

		Bundle data = getArguments();
		if (data != null) {
			info = (HelpInfo) data.getSerializable("serializable");
		}

		tv_title.setText(info.title);
		tv_content
				.setText("测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试");

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();

		home.setLastRightImgHide();
		home.setLeftImgBack();
		home.setleftTextHide();
		home.setSwitchTitle("内容");

	}

}
