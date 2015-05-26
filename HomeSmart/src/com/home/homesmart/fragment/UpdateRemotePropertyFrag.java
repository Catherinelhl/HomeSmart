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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * 编辑遥控器属性
 * 
 * @author hyc
 * 
 */
public class UpdateRemotePropertyFrag extends Fragment implements
		View.OnClickListener {

	HomeActivity home;

	CheckBox cb_default;

	Button saveButton, cancelButton;

	Control control;

	ImageView add_image;

	EditText name_et;

	Bitmap prebitmap;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.control_update, null);
		home = (HomeActivity) getActivity();

		cb_default = (CheckBox) view.findViewById(R.id.cb_default);
		add_image = (ImageView) view.findViewById(R.id.add_image);
		add_image.setOnClickListener(this);

		saveButton = (Button) view.findViewById(R.id.saveButton);
		cancelButton = (Button) view.findViewById(R.id.cancelButton);

		saveButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);

		name_et = (EditText) view.findViewById(R.id.name_et);

		Bundle data = getArguments();
		// if (data != null) {
		// control = (Control) data.getParcelable("parcelable");
		// if (control == null) {
		control = ViewPagerContainerFrag.control;
		// }
		// }

		if (control != null) {
			name_et.setText(control.name);

			if (control.isDefault == 1) {
				cb_default.setChecked(true);
			} else {
				cb_default.setChecked(false);
			}
			prebitmap = control.image;
			add_image.setImageBitmap(prebitmap);
		}

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();

		home.setLastRightImgHide();
		// home.setLastRightImg(R.drawable.home_add, home.TAG_ORDER);
		home.setSwitchTitle("编辑遥控器属性");
		home.setLeftImgBack();
		home.setleftTextHide();

		home.mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_NONE);

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.saveButton:
			control.name = name_et.getText().toString();

			if (resultBitmap != null) {
				control.image = resultBitmap;
			} else {
				control.image = prebitmap;
			}

			if (cb_default.isChecked()) {
				control.isDefault = 1;

				Control defaultControl = BaseDroidApp.dbOperator
						.getDefaultControl(1);

				if (defaultControl != null) {
					defaultControl.isDefault = 0;
					BaseDroidApp.dbOperator.updateControl(defaultControl);
				}

			} else {
				control.isDefault = 0;
			}

			BaseDroidApp.dbOperator.updateControl(control);
			home.onBackPressed();
			// ViewPagerContainerFrag.vp.setCurrentItem();
			break;
		case R.id.cancelButton:
			home.onBackPressed();
			break;
		case R.id.add_image:
			BaseDroidApp.getInstanse().showPhotoBottomDialog(onclickListener);
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

}
