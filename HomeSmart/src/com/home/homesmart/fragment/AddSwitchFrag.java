package com.home.homesmart.fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.home.application.BaseApp;
import com.home.application.BaseDroidApp;
import com.home.constants.Constants;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 添加开关
 * 
 * @author hyc
 * 
 */
public class AddSwitchFrag extends Fragment implements View.OnClickListener {

	ImageView add_image;
	Button saveButton, cancelButton;

	EditText name_et;

	HomeActivity home;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.switch_add, null);
		add_image = (ImageView) view.findViewById(R.id.add_image);
		add_image.setOnClickListener(this);
		saveButton = (Button) view.findViewById(R.id.saveButton);
		cancelButton = (Button) view.findViewById(R.id.cancelButton);
		saveButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);

		name_et = (EditText) view.findViewById(R.id.name_et);

		home = (HomeActivity) getActivity();

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();

		home.setLastRightImgHide();
		home.setSwitchTitle("添加开关");
		home.setLeftImgBack();
		home.setleftTextHide();

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.add_image:
			BaseDroidApp.getInstanse().showPhotoBottomDialog(onclickListener);
			break;
		case R.id.saveButton:
			Switch switch1 = new Switch();
			switch1.name = name_et.getText().toString();

			if (resultBitmap != null) {
				switch1.image = resultBitmap;
			} else {
				switch1.image = BitmapFactory.decodeResource(getResources(),
						R.drawable.home_addimg);
			}
			switch1.isOpen = 0;
			// 数据存储到数据库中
			BaseDroidApp.dbOperator.insertSwitch(switch1);
			// getActivity().onBackPressed();

			// 进入到开关配置
			home.configSwitch(switch1);

			break;
		case R.id.cancelButton:
			getActivity().onBackPressed();
			break;
		}

	}

	OnClickListener onclickListener = new OnClickListener() {

		public void onClick(View v) {
			BaseDroidApp.getInstanse().closeBottomDialog();
			switch (Integer.valueOf(String.valueOf(v.getTag()))) {
			case CustomDialog.TAG_IMAGE_LIB:
				home.selectFromContent();
				break;
			case CustomDialog.TAG_TAKE_PHOTO:
				home.doTakePhoto();
				break;
			case CustomDialog.TAG_SEL_PHONE:
				home.doPickPhotoFromGallery();
				break;
			}
		};
	};

	private String realpicPath = "";

	Bitmap resultBitmap;

	public void onActivityResult(int requestCode, int resultCode,
			android.content.Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == home.PHOTORESULT && resultCode == home.RESULT_OK) {
			// 处理结果
			Bundle extras = data.getExtras();
			if (extras != null) {
				resultBitmap = extras.getParcelable("data");
				// realpicPath = home.savePic(resultBitmap, false);
				add_image.setImageBitmap(resultBitmap);
			}

		}

	};

	@Override
	public void onDestroy() {
		Logger.log("AddSwitchFrag onDestroy");
		super.onDestroy();

		home.removeFragment();

	}
}
