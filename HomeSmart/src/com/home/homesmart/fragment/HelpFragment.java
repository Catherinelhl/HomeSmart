package com.home.homesmart.fragment;

import com.home.homesmart.R;
import com.home.homesmart.activity.HomeActivity;
import com.home.utils.PrefrenceUtils;
import com.home.widget.SwitchView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * 帮助
 * 
 * @author hyc
 * 
 */
public class HelpFragment extends OuterFragment implements View.OnClickListener {

	Button btn_intro, btn_question, btn_feekback;

	HomeActivity home;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_help, null);

		btn_intro = (Button) view.findViewById(R.id.btn_intro);
		btn_question = (Button) view.findViewById(R.id.btn_question);
		btn_feekback = (Button) view.findViewById(R.id.btn_feekback);

		btn_intro.setOnClickListener(this);
		btn_question.setOnClickListener(this);
		btn_feekback.setOnClickListener(this);

		home = (HomeActivity) getActivity();

		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		home.setLastRightImgHide();
		home.setLeftImgShow();
		home.setleftTextHide();
		home.setSwitchTitle("帮助");

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_intro:

			Fragment userinfo = new UseIntroFrag();
			home.switchContent(userinfo, true, null, null);
			break;
		case R.id.btn_question:
			Fragment question = new CommonQuestionFrag();
			home.switchContent(question, true, null, null);
			break;
		case R.id.btn_feekback:
			Fragment feekback = new FeedBackFrag();
			home.switchContent(feekback, true, null, null);
			break;
		}

	}

}
