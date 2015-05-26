package com.home.widget;

import java.util.ArrayList;

import com.home.adapter.BottomAdapter;
import com.home.constants.BottomItem;
import com.home.constants.ControlItem;
import com.home.homesmart.R;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

/**
 * 通用Dialog
 * 
 * @author hyc
 * 
 */
public class CustomDialog extends Dialog {

	private Context mContext;

	private View contentView;

	public final static int TAG_IMAGE_LIB = 1;
	public final static int TAG_TAKE_PHOTO = 2;
	public final static int TAG_SEL_PHONE = 3;
	public final static int TAG_CANCEL_BUTTON = 4;

	public final static int TAG_UPDATE = 5;
	public final static int TAG_DELETE = 6;

	public final static int TAG_SURE = 7;
	public final static int TAG_CANCLE = 8;

	public CustomDialog(Context context) {
		super(context, R.style.Theme_Dialog);
		mContext = context;
		setCancelable(true);
		setCanceledOnTouchOutside(true);
		// getWindow().setBackgroundDrawable(new BitmapDrawable()); // 去除黑色背景
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
						| WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
	}

	public CustomDialog(Context context, int theme) {
		super(context, theme);
		mContext = context;
		setCancelable(true);
		setCanceledOnTouchOutside(true);
		// getWindow().setBackgroundDrawable(new BitmapDrawable()); // 去除黑色背景
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
						| WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
	}

	public View initPhotoBottomView(View.OnClickListener onclickListener) {

		contentView = LayoutInflater.from(mContext).inflate(
				R.layout.custom_add_dialog_items, null);
		Button imagelib = (Button) contentView.findViewById(R.id.imagelib);
		imagelib.setTag(TAG_IMAGE_LIB);
		imagelib.setOnClickListener(onclickListener);
		Button takephoto = (Button) contentView.findViewById(R.id.takephoto);
		takephoto.setTag(TAG_TAKE_PHOTO);
		takephoto.setOnClickListener(onclickListener);
		Button selfromphone = (Button) contentView
				.findViewById(R.id.selfromphone);
		selfromphone.setTag(TAG_SEL_PHONE);
		selfromphone.setOnClickListener(onclickListener);
		Button canclebtn = (Button) contentView.findViewById(R.id.canclebtn);
		canclebtn.setTag(TAG_CANCEL_BUTTON);
		canclebtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dismiss();
			}
		});
		return contentView;
	}

	/**
	 * setOnItemClickListener 不执行
	 * 
	 * @param list
	 * @param listener
	 * @param item
	 * @return
	 */
	public View initRemoteBottomView(ArrayList<BottomItem> list,
			OnItemClickListener listener, ControlItem item) {

		contentView = LayoutInflater.from(mContext).inflate(
				R.layout.home_rm_alert_layout, null);

		GridView content_list = (GridView) contentView
				.findViewById(R.id.content_list);
		BottomAdapter adapter = new BottomAdapter(mContext, list, item);
		content_list.setAdapter(adapter);
		content_list.setOnItemClickListener(listener);
		// content_list.setOnItemSelectedListener(listener);
		Button canclebtn = (Button) contentView.findViewById(R.id.canclebtn);
		canclebtn.setTag(TAG_CANCEL_BUTTON);
		canclebtn.setEnabled(false);
		canclebtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dismiss();
			}
		});
		return contentView;
	}

	public View initUpdateBottomView(View.OnClickListener onclickListener) {

		contentView = LayoutInflater.from(mContext).inflate(
				R.layout.custom_update_dialog_items, null);

		Button update = (Button) contentView.findViewById(R.id.update);
		update.setTag(TAG_UPDATE);
		update.setOnClickListener(onclickListener);
		Button delete = (Button) contentView.findViewById(R.id.delete);
		delete.setTag(TAG_DELETE);
		delete.setOnClickListener(onclickListener);
		Button canclebtn = (Button) contentView.findViewById(R.id.canclebtn);
		canclebtn.setTag(TAG_CANCEL_BUTTON);
		canclebtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dismiss();
			}
		});
		return contentView;
	}

	public View initMentionDialogView(String message, String btn1Text,
			String btn2Text, View.OnClickListener onclickListener) {

		contentView = LayoutInflater.from(mContext).inflate(
				R.layout.home_message_dialog, null);

		TextView tvMentionMsg = (TextView) contentView
				.findViewById(R.id.tv_metion_msg);
		tvMentionMsg.setText(message);

		Button sureBtn = (Button) contentView.findViewById(R.id.sure_btn);
		sureBtn.setOnClickListener(onclickListener);
		sureBtn.setText(btn1Text);
		sureBtn.setTag(TAG_SURE);

		Button cancleBtn = (Button) contentView.findViewById(R.id.cancel_btn);
		cancleBtn.setOnClickListener(onclickListener);
		cancleBtn.setText(btn2Text);
		cancleBtn.setTag(TAG_CANCLE);
		return contentView;

	}

	public View initStudyDialogView(String message,
			View.OnClickListener onclickListener) {

		contentView = LayoutInflater.from(mContext).inflate(
				R.layout.home_study_dialog, null);

		TextView tvMentionMsg = (TextView) contentView
				.findViewById(R.id.tv_metion_msg);
		tvMentionMsg.setText(message);

		Button cancleBtn = (Button) contentView.findViewById(R.id.cancel_btn);
		cancleBtn.setOnClickListener(onclickListener);
		cancleBtn.setText("取消");
		cancleBtn.setTag(TAG_CANCEL_BUTTON);
		return contentView;

	}

	public static EditText tv_name_value, tv_time_value;

	public View initSelectDialogView(String message1, String message2,String etmessage1,String etmessage2,
			View.OnClickListener onclickListener) {

		contentView = LayoutInflater.from(mContext).inflate(
				R.layout.home_select_dialog, null);

		TextView tvMentionMsg1 = (TextView) contentView
				.findViewById(R.id.tv_metion_msg1);
		tvMentionMsg1.setText(message1);

		TextView tvMentionMsg2 = (TextView) contentView
				.findViewById(R.id.tv_metion_msg2);
		tvMentionMsg2.setText(message2);

		tv_name_value = (EditText) contentView.findViewById(R.id.tv_name_value);
		tv_name_value.setText(etmessage1);
		
		tv_time_value = (EditText) contentView.findViewById(R.id.tv_time_value);
		tv_time_value.setText(etmessage2);
		
		Button sureBtn = (Button) contentView.findViewById(R.id.sure_btn);
		sureBtn.setOnClickListener(onclickListener);
		sureBtn.setText("确定");
		sureBtn.setTag(TAG_SURE);

		Button cancleBtn = (Button) contentView.findViewById(R.id.cancel_btn);
		cancleBtn.setOnClickListener(onclickListener);
		cancleBtn.setText("取消");
		cancleBtn.setTag(TAG_CANCLE);
		return contentView;
	}

	@Override
	public void dismiss() {
		super.dismiss();
	}

}
