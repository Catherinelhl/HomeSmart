package com.home.homesmart.fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import net.simonvt.menudrawer.MenuDrawer;

import com.home.application.BaseApp;
import com.home.application.BaseDroidApp;
import com.home.constants.Constants;
import com.home.constants.Control;
import com.home.constants.Scene;
import com.home.constants.Switch;
import com.home.homesmart.R;
import com.home.homesmart.activity.HomeActivity;
import com.home.utils.CommonTools;
import com.home.utils.Logger;
import com.home.utils.PrefrenceUtils;
import com.home.view.Workspace;
import com.home.widget.CustomDialog;

import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * 编辑遥控器
 * 
 * @author hyc
 * 
 */
public class UpdateRemoteControlFrag extends Fragment implements
		View.OnClickListener {

	HomeActivity home;

	static Workspace workspace;

	public boolean isupdateRemote = false;

	Control control;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		home = (HomeActivity) getActivity();
		isupdateRemote = true;

		control = ViewPagerContainerFrag.control;
		// control = RemoteControlFrag.control;
		workspace = new Workspace(home, isupdateRemote, control);

		return workspace;
	}

	@Override
	public void onResume() {
		super.onResume();
		Logger.log("UpdateRemoteControlFrag=========onResume==========>");

		home.setLastRightImg(R.drawable.home_save, home.TAG_SAVE);
		home.setRightImg(R.drawable.home_shu);
		home.setPreRightImg(R.drawable.home_right_add);
		home.setLeftImg(R.drawable.home_left_back);
		home.setleftTextShow("编辑遥控器");
		home.setSwitchTitle("");

		home.mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_NONE);
		initView();

	}

	public static void initView() {
		workspace.removeAllViews();
		workspace.initViews();
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		Logger.log("UpdateRemoteControlFrag=========isVisibleToUser==========>"
				+ isVisibleToUser);
		if (isVisibleToUser) {

		} else {

		}

	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		Logger.log("onHiddenChanged=========hidden==========>" + hidden);
	}

	@Override
	public void onClick(View view) {

	}

	@Override
	public void onDestroy() {
		Logger.log("UpdateRemoteControlFrag onDestroy");
		super.onDestroy();

		// home.removeFragment();
	}

}
