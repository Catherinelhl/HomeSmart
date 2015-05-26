package com.home.homesmart.fragment;

import java.util.ArrayList;

import com.home.adapter.SwitchItemAdapter;
import com.home.application.BaseApp;
import com.home.application.BaseDroidApp;
import com.home.constants.Switch;
import com.home.homesmart.R;
import com.home.homesmart.activity.HomeActivity;
import com.home.utils.CommonTools;
import com.home.utils.Logger;
import com.home.widget.CustomDialog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * 开关
 * 
 * @author hyc
 * 
 */
public class SwitchFrag extends OuterFragment {

	View view;

	Button switch_btn;

	ListView home_item_lv;

	SwitchItemAdapter switchItemAdapter;

	ArrayList<Switch> list;

	Switch switch1;
	
	ImageView switch_iv;

	public boolean switchViewStatus = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.switch_layout, container, false);

		Logger.log("SwitchFrag=====onCreateView==========>");
		
		switch_btn = (Button) view.findViewById(R.id.switch_btn);
		switch_iv= (ImageView) view.findViewById(R.id.switch_iv);
		
		switch_iv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				setSwitchState();

				// 所有开关的开和关
				for (Switch switch1 : list) {
					if (switchViewStatus) {
						switch1.isOpen = 1;
					} else {
						switch1.isOpen = 0;
					}
				}
				switchItemAdapter.notifyDataSetChanged();
			}
		});
		
		
		switch_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				setSwitchState();

				// 所有开关的开和关
				for (Switch switch1 : list) {
					if (switchViewStatus) {
						switch1.isOpen = 1;
					} else {
						switch1.isOpen = 0;
					}
				}
				switchItemAdapter.notifyDataSetChanged();
			}
		});

		home_item_lv = (ListView) view.findViewById(R.id.home_item_lv);

		list = new ArrayList<Switch>();

		getListFromDb();

		// addList();

		switchItemAdapter = new SwitchItemAdapter(getActivity(), list);
		home_item_lv.setAdapter(switchItemAdapter);

		home_item_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				switch1 = list.get(position);
				BaseDroidApp.getInstanse().showUpdateBottomDialog(
						onClickListener);

			}
		});

		return view;
	}

	public void setSwitchState() {
		if (switchViewStatus) {
			switchViewStatus = false;
			switch_iv.setBackgroundResource(R.drawable.home_switch_close);
		} else {
			switchViewStatus = true;
			switch_iv.setBackgroundResource(R.drawable.home_switch_open);
		}
	}

	OnClickListener onClickListener = new OnClickListener() {

		public void onClick(View v) {
			switch (Integer.parseInt(String.valueOf(v.getTag()))) {
			case CustomDialog.TAG_UPDATE:
				Logger.log("TAG_UPDATE");

				HomeActivity home = (HomeActivity) getActivity();
				home.updateSwitch(switch1);
				BaseDroidApp.getInstanse().closeBottomDialog();
				break;
			case CustomDialog.TAG_DELETE:
				Logger.log("TAG_DELETE");

				BaseDroidApp.getInstanse().closeBottomDialog();
				BaseDroidApp.getInstanse().showConfirmDialog("确认删除该开关么？", "确认",
						"取消", onClickListener);

				break;
			case CustomDialog.TAG_SURE:
				BaseDroidApp.getInstanse().closeMessageDialog();
				BaseDroidApp.dbOperator.deleteSwitch(switch1.id);
				list.remove(switch1);
				switchItemAdapter.notifyDataSetChanged();
				break;
			case CustomDialog.TAG_CANCLE:

				BaseDroidApp.getInstanse().closeMessageDialog();
				break;
			}
		}

	};

	private void getListFromDb() {
		list = BaseDroidApp.dbOperator.getSelectSwitch();
	}

	private void addList() {
		Switch switch1 = new Switch();
		switch1.name = "主卧室";
		switch1.isOpen = 1;
		list.add(switch1);

		Switch switch2 = new Switch();
		switch2.name = "次卧室";
		switch2.isOpen = 0;
		list.add(switch2);

		Switch switch3 = new Switch();
		switch3.name = "客厅";
		switch3.isOpen = 0;
		list.add(switch3);
	}

	@Override
	public void onResume() {
		super.onResume();

		Logger.log("SwitchFrag=====onResume==========>");
		
		HomeActivity home = (HomeActivity) getActivity();
		home.setLastRightImg(R.drawable.home_add, "开关");
		home.setSwitchTitle("开关");
		home.setleftTextHide();
		home.setLeftImgShow();

	}
}
