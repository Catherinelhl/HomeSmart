package com.home.homesmart.fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.home.adapter.OrderAdapter;
import com.home.application.BaseApp;
import com.home.application.BaseDroidApp;
import com.home.constants.Constants;
import com.home.constants.Order;
import com.home.constants.Scene;
import com.home.constants.Switch;
import com.home.homesmart.R;
import com.home.homesmart.activity.HomeActivity;
import com.home.utils.CommonTools;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * 编辑场景
 * 
 * @author hyc
 * 
 */
public class UpdateSceneFrag extends Fragment implements View.OnClickListener {
	Button saveButton, cancelButton;

	HomeActivity home;

	EditText name_et;

	EditText timePicker;

	Spinner repeat_spin;

	ArrayAdapter<String> sceneAdapter;

	Calendar c;

	private String[] adapterData;

	public String currentRepeatStr;

	ImageView add_image;

	public String currentTime;

	Scene scene;

	Bitmap preBitmap;

	ListView home_item_lv;
	OrderAdapter adapter;
	ArrayList<Order> orderList;

	Order order;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View headView = inflater.inflate(R.layout.scene_add_headview, null);
		View view = inflater.inflate(R.layout.scene_add, null);
		View footView = inflater.inflate(R.layout.scene_add_footview, null);

		Bundle data = getArguments();
		if (data != null) {
			scene = (Scene) data.getParcelable("parcelable");
		}

		saveButton = (Button) footView.findViewById(R.id.saveButton);
		cancelButton = (Button) footView.findViewById(R.id.cancelButton);

		name_et = (EditText) headView.findViewById(R.id.name_et);
		c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		timePicker = (EditText) headView.findViewById(R.id.timePic);
		timePicker.setOnClickListener(this);

		setCurrentTime(hourOfDay, minute);

		home_item_lv = (ListView) view.findViewById(R.id.home_item_lv);
		repeat_spin = (Spinner) headView.findViewById(R.id.repeat_spin);

		add_image = (ImageView) headView.findViewById(R.id.add_image);
		add_image.setOnClickListener(this);

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

		if (scene != null) {
			name_et.setText(scene.name);

			preBitmap = scene.image;
			add_image.setImageBitmap(preBitmap);

			for (int i = 0; i < adapterData.length; i++) {
				if (adapterData[i].equals(scene.repeatDate)) {
					currentRepeatStr = scene.repeatDate;
					repeat_spin.setSelection(i);
				}
			}
			Constants.sceneId = scene.id;
		}

		orderList = new ArrayList<Order>();
		orderList = BaseDroidApp.dbOperator.getOrderByScene(Constants.sceneId);
		adapter = new OrderAdapter(home, orderList);

		home_item_lv.addHeaderView(headView);
		home_item_lv.addFooterView(footView);

		home_item_lv.setAdapter(adapter);
		// CommonTools.setListViewHeightBasedOnChildren(home_item_lv);

		home_item_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// 去掉head的position
				Logger.log("position:" + (position - 1));
				order = orderList.get(position - 1);

				BaseDroidApp.getInstanse().showUpdateBottomDialog(
						onclickListener);

			}
		});

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();

		home.setLastRightImgHide();
		home.setLastRightImg(R.drawable.home_add, home.TAG_ORDER);
		home.setSwitchTitle("编辑场景");
		home.setLeftImgBack();
		home.setleftTextHide();

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.saveButton:

			scene.name = name_et.getText().toString();
			scene.time = timePicker.getText().toString();
			scene.repeatDate = currentRepeatStr;

			if (resultBitmap != null) {
				scene.image = resultBitmap;
			} else {
				scene.image = preBitmap;
			}

			// 更新到数据库中
			BaseDroidApp.dbOperator.updateScene(scene);
			home.onBackPressed();
			break;
		case R.id.cancelButton:
			home.onBackPressed();
			break;
		case R.id.add_image:
			BaseDroidApp.getInstanse().showPhotoBottomDialog(onclickListener);
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

			case CustomDialog.TAG_UPDATE:
				Logger.log("TAG_UPDATE");
				home.selOrder(scene, true, order);
				BaseDroidApp.getInstanse().closeBottomDialog();
				break;
			case CustomDialog.TAG_DELETE:
				Logger.log("TAG_DELETE");

				BaseDroidApp.dbOperator.deleteOrder(order.id);
				orderList.remove(order);
				adapter.notifyDataSetChanged();

				BaseDroidApp.getInstanse().closeBottomDialog();

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
