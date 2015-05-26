package com.home.homesmart.fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.home.application.BaseApp;
import com.home.application.BaseDroidApp;
import com.home.constants.Constants;
import com.home.constants.ControlItem;
import com.home.constants.Scene;
import com.home.homesmart.R;
import com.home.homesmart.activity.HomeActivity;
import com.home.utils.Logger;
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
 * 定时任务
 * 
 * @author hyc
 * 
 */
public class TimingTaskFrag extends Fragment implements OnClickListener {

	Button saveButton, cancelButton;

	HomeActivity home;

	TextView name_et;

	EditText timePicker;

	Spinner repeat_spin;

	ArrayAdapter<String> sceneAdapter;

	Calendar c;

	private String[] adapterData;

	public String currentRepeatStr;

	public String currentTime;

	ControlItem controlItem;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.timing_task, null);

		Bundle data = getArguments();
		if (data != null) {
			controlItem = (ControlItem) data.getParcelable("parcelable");
		}

		saveButton = (Button) view.findViewById(R.id.saveButton);
		cancelButton = (Button) view.findViewById(R.id.cancelButton);

		name_et = (TextView) view.findViewById(R.id.name_et);
		if (controlItem != null) {
			name_et.setText(controlItem.name);
		}

		c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		timePicker = (EditText) view.findViewById(R.id.timePic);
		timePicker.setOnClickListener(this);

		setCurrentTime(hourOfDay, minute);

		repeat_spin = (Spinner) view.findViewById(R.id.repeat_spin);

		saveButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);

		home = (HomeActivity) getActivity();

		adapterData = new String[] { "周一", "周二", "周三", "周四", "周五", "周六", "周日",
				"工作日", "周末", "周一到周日" };
		sceneAdapter = new ArrayAdapter<String>(home,
				android.R.layout.simple_list_item_1, adapterData);

		// sceneAdapter
		// .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		repeat_spin.setAdapter(sceneAdapter);

		repeat_spin.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				currentRepeatStr = adapterData[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();

		home.setLastRightImgHide();
		home.setRightImgHide();
		home.setPreRightImgHide();
		home.setleftTextHide();
		home.setSwitchTitle("定时开启");
		home.setLeftImgBack();
		// home.setLeftTextShow();

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.saveButton:
			controlItem.name = name_et.getText().toString();
			controlItem.time = timePicker.getText().toString();
			controlItem.repeatDate = currentRepeatStr;

			// 插入到数据库中
			BaseDroidApp.dbOperator.updateControlItem(controlItem);
			home.onBackPressed();
			// 开启定时服务

			break;
		case R.id.cancelButton:
			home.onBackPressed();
			break;
		case R.id.timePic:
			c.setTimeInMillis(System.currentTimeMillis());
			int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);
			TimePickerDialog timeDialog = new TimePickerDialog(getActivity(),
					new OnTimeSetListener() {

						@Override
						public void onTimeSet(TimePicker view, int hourOfDay,
								int minute) {
							c.set(Calendar.HOUR_OF_DAY, hourOfDay);
							c.set(Calendar.MINUTE, minute);

							setCurrentTime(hourOfDay, minute);
						}
					}, hourOfDay, minute, true);
			timeDialog.show();

			break;
		}

	}

	private void setCurrentTime(int hourOfDay, int minute) {

		StringBuilder sb = new StringBuilder();
		String date;

		if (hourOfDay < 10) {
			sb.append("0" + hourOfDay);
		} else {
			sb.append(hourOfDay);
		}
		sb.append(":");
		if (minute < 10) {
			sb.append("0" + minute);
		} else {
			sb.append(minute);
		}
		date = sb.toString();
		timePicker.setText(date);
	}

}
