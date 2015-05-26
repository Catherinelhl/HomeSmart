package com.home.homesmart.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.home.homesmart.R;
import com.home.homesmart.activity.HomeActivity;

/**
 * 反馈信息
 * 
 * @author hyc
 * 
 */
public class FeedBackFrag extends Fragment {

	EditText et_title, et_content;

	HomeActivity home;

	Button saveButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_feedback_content, null);

		et_title = (EditText) view.findViewById(R.id.et_title);
		et_content = (EditText) view.findViewById(R.id.et_content);

		saveButton = (Button) view.findViewById(R.id.saveButton);

		home = (HomeActivity) getActivity();

		saveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				home.onBackPressed();
			}
		});

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();

		home.setLastRightImgHide();
		home.setLeftImgBack();
		home.setleftTextHide();
		home.setSwitchTitle("反馈信息");

	}

}
