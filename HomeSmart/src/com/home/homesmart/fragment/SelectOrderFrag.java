package com.home.homesmart.fragment;

import java.util.ArrayList;

import com.home.adapter.ControlAdapter;
import com.home.adapter.SelControlAdapter;
import com.home.application.BaseApp;
import com.home.application.BaseDroidApp;
import com.home.constants.Constants;
import com.home.constants.Control;
import com.home.constants.ControlItem;
import com.home.constants.Order;
import com.home.constants.Scene;
import com.home.homesmart.R;
import com.home.homesmart.activity.HomeActivity;
import com.home.utils.Logger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

/**
 * 选择命令
 * 
 * @author hyc
 * 
 */
public class SelectOrderFrag extends Fragment {

	HomeActivity home;

	GridView order_gv;

	SelControlAdapter adapter;

	ArrayList<Control> list;

	Scene scene;

	public boolean isUpdate;

	Order order;

	public SelectOrderFrag(boolean update, Order order) {
		isUpdate = update;
		this.order = order;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.sel_order_layout, null);

		home = (HomeActivity) getActivity();

		Bundle data = getArguments();
		if (data != null) {
			scene = (Scene) data.getParcelable("parcelable");
			Constants.sceneId = scene.id;
		}

		list = BaseDroidApp.dbOperator.getSelectControl();

		order_gv = (GridView) view.findViewById(R.id.order_gv);

		adapter = new SelControlAdapter(home, list);

		order_gv.setAdapter(adapter);

		order_gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				Logger.log("onItemClick=========================>");
				// 进入到遥控器按键
				home.gotoOrderControl(list.get(position),isUpdate,order);

			}
		});

		return view;
	}

	@Override
	public void onResume() {

		super.onResume();

		// HomeActivity home = (HomeActivity) getActivity();
		home.setLastRightImgHide();
		home.setSwitchTitle("选择命令");
		home.setLeftImgBack();
		home.setleftTextHide();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Logger.log("SelectOrderFrag onDestroy=====================>");
		// home.removeFragment();
	}

	@Override
	public void onDestroyView() {
		Logger.log("SelectOrderFrag onDestroyView=====================>");
		super.onDestroyView();
	}

}
