package com.home.homesmart.fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.home.application.BaseApp;
import com.home.application.BaseDroidApp;
import com.home.constants.Constants;
import com.home.constants.HelpInfo;
import com.home.constants.Switch;
import com.home.db.DbOperator;
import com.home.homesmart.R;
import com.home.homesmart.activity.HomeActivity;
import com.home.utils.CommonTools;
import com.home.utils.Logger;
import com.home.widget.CustomDialog;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.content.Context;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 添加遥控器的列表
 * 
 * @author hyc
 * 
 */
public class AddRemoteControlListFrag extends Fragment implements
		View.OnClickListener {

	HomeActivity home;

	ListView home_item_lv;

	ArrayAdapter<String> remoteAdapter;

	ArrayList<String> list;

	ArrayList<String> typeList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_rmlist, null);
		home = (HomeActivity) getActivity();

		home_item_lv = (ListView) view.findViewById(R.id.home_item_lv);

		addList();

		remoteAdapter = new ArrayAdapter<String>(home,
				android.R.layout.simple_list_item_1, list);

		home_item_lv.setAdapter(remoteAdapter);

		home_item_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				home.gotoAddControl(typeList.get(position));
			}
		});

		return view;
	}

	private void addList() {
		list = new ArrayList<String>();
		list.add("电视");
		list.add("空调");
		list.add("音响");
		list.add("小米盒子");
		list.add("百度盒子");
		list.add("APPlE TV");
		list.add("乐视TV");

		typeList = new ArrayList<String>();

		typeList.add(Constants.TYPE_TV);
		typeList.add(Constants.TYPE_AIR);
		typeList.add(Constants.TYPE_SOUND);
		typeList.add(Constants.TYPE_TV);
		typeList.add(Constants.TYPE_TV);
		typeList.add(Constants.TYPE_TV);
		typeList.add(Constants.TYPE_TV);
	}

	@Override
	public void onResume() {
		super.onResume();

		home.setLastRightImgHide();
		home.setSwitchTitle("添加遥控");
		home.setLeftImgBack();
		home.setleftTextHide();

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void onDestroy() {
		Logger.log("AddRemoteControlListFrag onDestroy");
		super.onDestroy();

		home.removeFragment();

	}

}
