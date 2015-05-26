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
import com.home.constants.ControlItem;
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
public class UpdateControlItemNameFrag extends Fragment implements
		View.OnClickListener {

	HomeActivity home;

	Button saveButton, cancelButton;

	EditText name_et;

	ControlItem item;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.controlitem_upname, null);
		home = (HomeActivity) getActivity();

		saveButton = (Button) view.findViewById(R.id.saveButton);
		cancelButton = (Button) view.findViewById(R.id.cancelButton);

		saveButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);

		name_et = (EditText) view.findViewById(R.id.name_et);

		Bundle data = getArguments();
		if (data != null) {
			item = (ControlItem) data.getParcelable("parcelable");
			if (item == null) {
			}
		}

		if (item != null) {
			name_et.setText(item.name);
		}

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();

		home.setLastRightImgHide();
		// home.setLastRightImg(R.drawable.home_add, home.TAG_ORDER);
		home.setRightImgHide();
		home.setPreRightImgHide();
		home.setSwitchTitle("更改名称");
		home.setLeftImgBack();
		home.setleftTextHide();

		home.mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_NONE);

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.saveButton:
			item.name = name_et.getText().toString();

			BaseDroidApp.dbOperator.updateControlItem(item);
			home.onBackPressed();
			// ViewPagerContainerFrag.vp.setCurrentItem();
			break;
		case R.id.cancelButton:
			home.onBackPressed();
			break;
		}
	}

}
