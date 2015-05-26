package com.home.homesmart.fragment;

import com.home.constants.HelpInfo;
import com.home.homesmart.R;
import com.home.homesmart.activity.HomeActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * 使用说明
 * 
 * @author hyc
 * 
 */
public class UseIntroFrag extends Fragment implements OnClickListener {

	Button btn_intro, btn_question, btn_feekback;

	HomeActivity home;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_useintro, null);

		btn_intro = (Button) view.findViewById(R.id.btn_intro);
		btn_question = (Button) view.findViewById(R.id.btn_question);
		btn_feekback = (Button) view.findViewById(R.id.btn_feekback);

		btn_intro.setOnClickListener(this);
		btn_question.setOnClickListener(this);
		btn_feekback.setOnClickListener(this);

		home = (HomeActivity) getActivity();

		return view;
	}

	Fragment introdetail = new IntroDetailFrag();
	HelpInfo helpInfo = new HelpInfo();

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_intro:
			helpInfo.title = btn_intro.getText().toString();
			home.switchContent(introdetail, true, null, helpInfo);
			break;
		case R.id.btn_question:
			helpInfo.title = btn_feekback.getText().toString();
			home.switchContent(introdetail, true, null, helpInfo);
			break;
		case R.id.btn_feekback:
			helpInfo.title = btn_feekback.getText().toString();
			home.switchContent(introdetail, true, null, helpInfo);
			break;
		}

	}

	@Override
	public void onResume() {
		super.onResume();

		home.setLastRightImgHide();
		home.setLeftImgBack();
		home.setleftTextHide();
		home.setSwitchTitle("使用说明");

	}

}
