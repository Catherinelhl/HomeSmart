package com.home.application;

import java.util.ArrayList;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import cn.jpush.android.api.JPushInterface;

import com.home.constants.BottomItem;
import com.home.constants.ControlItem;
import com.home.constants.LayoutValue;
import com.home.homesmart.R;
import com.home.homesmart.activity.HomeActivity;
import com.home.widget.CustomDialog;

public class BaseDroidApp extends BaseApp {

	protected static BaseDroidApp instanse = null;// 单例

	private HomeActivity currentAct = null; // 当前act

	private CustomDialog messageDialog, bottomDialog;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		instanse = this;

		// 设置开启日志,发布时请关闭日志
		JPushInterface.setDebugMode(true);
		// 初始化Jpush
		JPushInterface.init(this);
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}

	public static BaseDroidApp getInstanse() {
		return instanse;
	}

	public HomeActivity getCurrentAct() {
		return currentAct;
	}

	public void setCurrentAct(HomeActivity currentAct) {
		this.currentAct = currentAct;
	}

	public void closeBottomDialog() {
		if (bottomDialog != null && bottomDialog.isShowing()) {
			bottomDialog.dismiss();
		}
	}

	public void closeMessageDialog() {
		if (messageDialog != null && messageDialog.isShowing()) {
			messageDialog.dismiss();
		}
	}

	public void showPhotoBottomDialog(OnClickListener onClickListener) {
		if (bottomDialog != null && bottomDialog.isShowing()) {
			bottomDialog.dismiss();
		}
		bottomDialog = new CustomDialog(currentAct, R.style.Theme_Dialog);
		View v = bottomDialog.initPhotoBottomView(onClickListener);
		bottomDialog.setContentView(v);
		WindowManager.LayoutParams lp = bottomDialog.getWindow()
				.getAttributes();
		lp.x = LayoutValue.SCREEN_WIDTH / 2;
		lp.y = LayoutValue.SCREEN_HEIGHT / 12 * 5;
		lp.width = LayoutValue.SCREEN_WIDTH;
		lp.height = LayoutValue.SCREEN_HEIGHT / 12 * 5;
		bottomDialog.getWindow().setAttributes(lp);
		bottomDialog.getWindow().setWindowAnimations(
				R.style.shotcutDialogAnimation);
		bottomDialog.show();
	}

	public void showRemoteBottomDialog(ArrayList<BottomItem> list,
			OnItemClickListener listener, ControlItem item) {
		if (bottomDialog != null && bottomDialog.isShowing()) {
			bottomDialog.dismiss();
		}
		bottomDialog = new CustomDialog(currentAct, R.style.Theme_Dialog);
		View v = bottomDialog.initRemoteBottomView(list, listener, item);
		bottomDialog.setContentView(v);
		WindowManager.LayoutParams lp = bottomDialog.getWindow()
				.getAttributes();
		lp.x = LayoutValue.SCREEN_WIDTH / 2;
		lp.y = LayoutValue.SCREEN_HEIGHT / 12 * 5;
		lp.width = LayoutValue.SCREEN_WIDTH;
		lp.height = LayoutValue.SCREEN_HEIGHT / 12 * 5;
		bottomDialog.getWindow().setAttributes(lp);
		bottomDialog.getWindow().setWindowAnimations(
				R.style.shotcutDialogAnimation);
		bottomDialog.show();
	}

	public void showUpdateBottomDialog(OnClickListener onClickListener) {
		if (bottomDialog != null && bottomDialog.isShowing()) {
			bottomDialog.dismiss();
		}
		bottomDialog = new CustomDialog(currentAct, R.style.Theme_Dialog);
		View v = bottomDialog.initUpdateBottomView(onClickListener);
		bottomDialog.setContentView(v);
		WindowManager.LayoutParams lp = bottomDialog.getWindow()
				.getAttributes();
		lp.x = LayoutValue.SCREEN_WIDTH / 2;
		lp.y = LayoutValue.SCREEN_HEIGHT / 3;
		lp.width = LayoutValue.SCREEN_WIDTH;
		lp.height = LayoutValue.SCREEN_HEIGHT / 3;
		bottomDialog.getWindow().setAttributes(lp);
		bottomDialog.getWindow().setWindowAnimations(
				R.style.shotcutDialogAnimation);
		bottomDialog.show();
	}

	public void showConfirmDialog(String message, String btn1Text,
			String btn2Text, OnClickListener onclickListener) {
		if (messageDialog != null && messageDialog.isShowing()) {
			messageDialog.dismiss();
		}
		messageDialog = new CustomDialog(currentAct, R.style.Theme_Dialog);
		View v = messageDialog.initMentionDialogView(message, btn1Text,
				btn2Text, onclickListener);
		messageDialog.setContentView(v);
		WindowManager.LayoutParams lp = messageDialog.getWindow()
				.getAttributes();
		lp.width = LayoutValue.SCREEN_WIDTH * 6 / 7;
		lp.height = LayoutValue.SCREEN_WIDTH;
		lp.gravity = Gravity.CENTER;
		messageDialog.getWindow().setAttributes(lp);
		messageDialog.setCancelable(true);
		messageDialog.show();
	}

	public void showStudyDialog(String message, OnClickListener onclickListener) {
		if (messageDialog != null && messageDialog.isShowing()) {
			messageDialog.dismiss();
		}
		messageDialog = new CustomDialog(currentAct, R.style.Theme_Dialog);
		View v = messageDialog.initStudyDialogView(message, onclickListener);
		messageDialog.setContentView(v);
		WindowManager.LayoutParams lp = messageDialog.getWindow()
				.getAttributes();
		lp.width = LayoutValue.SCREEN_WIDTH * 6 / 7;
		lp.height = LayoutValue.SCREEN_WIDTH;
		lp.gravity = Gravity.CENTER;
		messageDialog.getWindow().setAttributes(lp);
		messageDialog.setCancelable(true);
		messageDialog.show();
	}

	public void showSelectDialog(String message1, String message2,
			String etmessage1, String etmessage2,
			OnClickListener onclickListener) {
		if (messageDialog != null && messageDialog.isShowing()) {
			messageDialog.dismiss();
		}
		messageDialog = new CustomDialog(currentAct, R.style.Theme_Dialog);
		View v = messageDialog.initSelectDialogView(message1, message2,
				etmessage1, etmessage2, onclickListener);
		messageDialog.setContentView(v);
		WindowManager.LayoutParams lp = messageDialog.getWindow()
				.getAttributes();
		lp.width = LayoutValue.SCREEN_WIDTH * 6 / 7;
		lp.height = LayoutValue.SCREEN_WIDTH;
		lp.gravity = Gravity.CENTER;
		messageDialog.getWindow().setAttributes(lp);
		messageDialog.setCancelable(true);
		messageDialog.show();
	}
}
