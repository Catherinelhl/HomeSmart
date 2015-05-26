package com.home.adapter;

import java.util.ArrayList;

import com.home.application.BaseApp;
import com.home.application.BaseDroidApp;
import com.home.constants.BottomItem;
import com.home.constants.Control;
import com.home.constants.ControlItem;
import com.home.constants.Scene;
import com.home.homesmart.R;
import com.home.homesmart.activity.HomeActivity;
import com.home.homesmart.fragment.MultiKeyFrag;
import com.home.homesmart.fragment.TimingTaskFrag;
import com.home.homesmart.fragment.UpdateControlItemImgFrag;
import com.home.homesmart.fragment.UpdateControlItemNameFrag;
import com.home.homesmart.fragment.UpdateRemoteControlFrag;
import com.home.utils.Logger;
import com.home.widget.CustomDialog;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BottomAdapter extends BaseAdapter {

	Context mContext;

	private ArrayList<BottomItem> list;

	private LayoutInflater inflater;

	HomeActivity home;

	Fragment mContent;

	ControlItem item;

	public BottomAdapter(Context context, ArrayList<BottomItem> control,
			ControlItem items) {
		mContext = context;
		this.list = control;
		this.item = items;
		inflater = LayoutInflater.from(mContext);
		home = (HomeActivity) mContext;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.bottommenu_item, null);
		}

		ImageView control_iv = ViewHolder.get(convertView, R.id.control_iv);

		TextView control_tv = ViewHolder.get(convertView, R.id.control_tv);

		BottomItem bottomItem = list.get(position);

		control_tv.setText(bottomItem.name);
		control_iv.setImageResource(bottomItem.imageId);

		convertView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View view, MotionEvent event) {

				switch (event.getAction()) {
				case MotionEvent.ACTION_UP:
					Logger.log("BottomAdapter position:" + position);
					BaseDroidApp.getInstanse().closeBottomDialog();
					switch (position) {
					case 0:
						mContent = new UpdateControlItemNameFrag();
						home.switchContent(mContent, true, item, null);
						break;
					case 1:
						mContent = new UpdateControlItemImgFrag();
						home.switchContent(mContent, true, item, null);
						break;
					case 2:
						BaseDroidApp.getInstanse().showConfirmDialog(
								"确认删除该按键么？", "确认", "取消", onclickListener);
						break;
					case 3:
						BaseDroidApp.getInstanse().showStudyDialog(
								"请对准设备按您要学习的红外遥控器按键进行学习", onclickListener);
						break;
					case 4:
						mContent = new MultiKeyFrag();
						home.switchContent(mContent, true, item, null);
						break;
					case 5:
						mContent = new TimingTaskFrag();
						home.switchContent(mContent, true, item, null);
						break;

					}
				}
				return false;
			}
		});

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Logger.log("BottomAdapter position:" + position);
				BaseDroidApp.getInstanse().closeBottomDialog();
				switch (position) {
				case 0:
					mContent = new UpdateControlItemNameFrag();
					home.switchContent(mContent, true, item, null);
					break;
				case 1:
					mContent = new UpdateControlItemImgFrag();
					home.switchContent(mContent, true, item, null);
					break;
				case 2:
					BaseDroidApp.getInstanse().showConfirmDialog("确认删除该按键么？",
							"确认", "取消", onclickListener);
					break;
				case 3:
					BaseDroidApp.getInstanse().showStudyDialog(
							"请对准设备按您要学习的红外遥控器按键进行学习", onclickListener);
					break;
				case 4:
					mContent = new TimingTaskFrag();
					home.switchContent(mContent, true, item, null);
					break;
				case 5:
					mContent = new TimingTaskFrag();
					home.switchContent(mContent, true, item, null);
					break;

				}
			}
		});

		return convertView;
	}

	OnClickListener onclickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			BaseDroidApp.getInstanse().closeMessageDialog();
			switch (Integer.parseInt(String.valueOf(v.getTag()))) {
			case CustomDialog.TAG_SURE:
				BaseDroidApp.dbOperator.deleteControlItem(item.id);
				// mContent = new UpdateRemoteControlFrag();
				// home.switchContent(mContent, false, null, null);
				UpdateRemoteControlFrag.initView();
				break;
			case CustomDialog.TAG_CANCLE:

				break;
			case CustomDialog.TAG_CANCEL_BUTTON:

				// 取消学习

				break;
			}

		}
	};
}
