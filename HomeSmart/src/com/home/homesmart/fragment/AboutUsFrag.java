package com.home.homesmart.fragment;

import com.home.homesmart.R;
import com.home.homesmart.activity.HomeActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnCreateContextMenuListener;
import android.view.animation.Animation;

/**
 * 关于我们
 * 
 * @author hyc
 * 
 */
public class AboutUsFrag extends OuterFragment {

	HomeActivity home;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		home = (HomeActivity) getActivity();

		return inflater.inflate(R.layout.about_us, null);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		home.setLastRightImgHide();
		home.setLeftImgShow();
		home.setleftTextHide();
		home.setSwitchTitle("关于我们");
	}

	@Override
	public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
		// TODO Auto-generated method stub
		return super.onCreateAnimation(transit, enter, nextAnim);
	}

}
