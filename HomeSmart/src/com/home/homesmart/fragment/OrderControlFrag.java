package com.home.homesmart.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.home.constants.Control;
import com.home.constants.ControlItem;
import com.home.constants.Order;
import com.home.homesmart.R;
import com.home.homesmart.activity.HomeActivity;
import com.home.utils.Logger;
import com.home.view.Workspace;

public class OrderControlFrag extends Fragment {

	Workspace workspace;

	HomeActivity home;

	Control control;

	public boolean isUpdate = false;

	Order order;

	public OrderControlFrag(boolean update, Order order) {
		isUpdate = update;
		this.order = order;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		home = (HomeActivity) getActivity();

		Bundle data = getArguments();
		if (data != null) {
			control = (Control) data.getParcelable("parcelable");
		}

		workspace = new Workspace(home, isUpdate, control);

		return workspace;
	}

	public void setIsUpdate(boolean update) {
		isUpdate = update;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		workspace.removeAllViews();
		workspace.setOrder(order);
		workspace.initView();

		home.setLastRightImgHide();
		home.setSwitchTitle("选择命令");
		home.setLeftImgBack();
		home.setleftTextHide();

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Logger.log("OrderControlFrag onDestroy=====================>");
		// home.removeFragment();
	}

	@Override
	public void onDestroyView() {
		Logger.log("OrderControlFrag onDestroyView=====================>");
		super.onDestroyView();
	}

	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		Logger.log("OrderControlFrag onDetach=====================>");

	}

}
