package com.home.homesmart.fragment;

import java.util.ArrayList;

import com.home.adapter.ControlItemCodeAdapter;
import com.home.application.BaseDroidApp;
import com.home.constants.ControlItem;
import com.home.constants.ControlItemCode;
import com.home.db.DbOperator;
import com.home.homesmart.R;
import com.home.homesmart.activity.HomeActivity;
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
import android.widget.ListView;

/**
 * 组合键学习
 * 
 * @author hyc
 * 
 */
public class MultiKeyFrag extends Fragment implements OnClickListener {

	HomeActivity home;

	Button saveButton, cancelButton;

	ControlItem controlItem;

	ListView home_item_lv;
	ArrayList<ControlItemCode> list;
	ControlItemCodeAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View headView = inflater.inflate(R.layout.multikey_headview, null);
		View view = inflater.inflate(R.layout.scene_add, null);
		View footView = inflater.inflate(R.layout.scene_add_footview, null);

		home = (HomeActivity) getActivity();

		Bundle data = getArguments();
		if (data != null) {
			controlItem = (ControlItem) data.getParcelable("parcelable");
		}

		saveButton = (Button) footView.findViewById(R.id.saveButton);
		saveButton.setText("添加");
		saveButton.setOnClickListener(this);

		cancelButton = (Button) footView.findViewById(R.id.cancelButton);
		cancelButton.setOnClickListener(this);

		home_item_lv = (ListView) view.findViewById(R.id.home_item_lv);
		list = new ArrayList<ControlItemCode>();
		Logger.log("controlItem.id:" + controlItem.id);

		home_item_lv.addHeaderView(headView);
		home_item_lv.addFooterView(footView);

		return view;
	}

	int controlItemCodeId;
	ControlItemCode controlItemCode;

	@Override
	public void onResume() {
		super.onResume();

		Logger.log("<========MultiKeyFrag===============>onResume()");
		home.setLastRightImgHide();
		home.setRightImgHide();
		home.setPreRightImgHide();
		home.setleftTextHide();
		home.setSwitchTitle("组合键学习");
		home.setLeftImgBack();
		// home.setLeftTextShow();

		list = BaseDroidApp.dbOperator.getCodeBycodeItem(controlItem.id);
		adapter = new ControlItemCodeAdapter(home, list);
		home_item_lv.setAdapter(adapter);
		adapter.notifyDataSetChanged();

		home_item_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				controlItemCode = list.get(position - 1);
				controlItemCodeId = controlItemCode.id;

				BaseDroidApp.getInstanse().showUpdateBottomDialog(
						onclickListener);
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.saveButton:
			BaseDroidApp.getInstanse().showSelectDialog("提示", "请输入所选按钮执行命令的名称",
					"", "", onclickListener);
			break;
		case R.id.cancelButton:
			home.onBackPressed();
			break;
		}

	}

	OnClickListener onclickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (Integer.valueOf(String.valueOf(v.getTag()))) {
			case CustomDialog.TAG_SURE:
				String name = CustomDialog.tv_name_value.getText().toString();
				String time = CustomDialog.tv_time_value.getText().toString();

				ControlItemCode item = new ControlItemCode();
				item.codeName = name;
				item.controlItemId = controlItem.id;
				item.time = time;

				BaseDroidApp.dbOperator.insertControlItemCode(item);

				list = BaseDroidApp.dbOperator
						.getCodeBycodeItem(controlItem.id);
				adapter = new ControlItemCodeAdapter(home, list);
				home_item_lv.setAdapter(adapter);
				adapter.notifyDataSetChanged();

				BaseDroidApp.getInstanse().closeMessageDialog();

				BaseDroidApp.getInstanse().showStudyDialog(
						"请对准设备按您要学习的红外遥控器按键进行学习", onclickListener);

				break;
			case CustomDialog.TAG_CANCLE:
				BaseDroidApp.getInstanse().closeMessageDialog();
				break;
			case CustomDialog.TAG_CANCEL_BUTTON:
				BaseDroidApp.getInstanse().closeMessageDialog();
				break;

			case CustomDialog.TAG_UPDATE:
				Logger.log("TAG_UPDATE");
				BaseDroidApp.getInstanse().closeBottomDialog();

				BaseDroidApp.getInstanse().showSelectDialog("提示",
						"请输入所选按钮执行命令的名称", controlItemCode.codeName,
						controlItemCode.time, onClickListener);

				break;
			case CustomDialog.TAG_DELETE:
				Logger.log("TAG_DELETE");

				BaseDroidApp.dbOperator
						.deleteControlItemCode(controlItemCodeId);
				list.remove(controlItemCode);
				adapter.notifyDataSetChanged();

				BaseDroidApp.getInstanse().closeBottomDialog();

				break;

			}

		}
	};

	OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			BaseDroidApp.getInstanse().closeMessageDialog();
			switch (Integer.valueOf(String.valueOf(v.getTag()))) {
			case CustomDialog.TAG_SURE:
				String name = CustomDialog.tv_name_value.getText().toString();
				String time = CustomDialog.tv_time_value.getText().toString();

				controlItemCode.codeName = name;
				controlItemCode.time = time;
				controlItemCode.controlItemId = controlItem.id;

				BaseDroidApp.dbOperator.updateControlItemCode(controlItemCode);

				list = BaseDroidApp.dbOperator
						.getCodeBycodeItem(controlItem.id);
				adapter = new ControlItemCodeAdapter(home, list);
				home_item_lv.setAdapter(adapter);
				adapter.notifyDataSetChanged();

				BaseDroidApp.getInstanse().showStudyDialog(
						"请对准设备按您要学习的红外遥控器按键进行学习", onclickListener);

				break;

			case CustomDialog.TAG_CANCLE:

				break;
			}

		}
	};
}
