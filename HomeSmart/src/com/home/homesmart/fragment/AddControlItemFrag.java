package com.home.homesmart.fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.home.application.BaseApp;
import com.home.application.BaseDroidApp;
import com.home.constants.Constants;
import com.home.constants.Control;
import com.home.constants.ControlItem;
import com.home.constants.LayoutValue;
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
 * 添加遥控器按键
 * 
 * @author hyc
 * 
 */
public class AddControlItemFrag extends Fragment implements OnClickListener {

	Button saveButton, cancelButton;

	HomeActivity home;

	EditText name_et;

	private String[] adapterData;

	public String currentRepeatStr;

	ImageView add_image;

	public String currentTime;

	public Control control;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_addcontrolitem, null);

		saveButton = (Button) view.findViewById(R.id.saveButton);
		cancelButton = (Button) view.findViewById(R.id.cancelButton);

		name_et = (EditText) view.findViewById(R.id.name_et);

		add_image = (ImageView) view.findViewById(R.id.add_image);
		add_image.setOnClickListener(this);

		saveButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);

		home = (HomeActivity) getActivity();

		control = ViewPagerContainerFrag.control;

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();

		HomeActivity home = (HomeActivity) getActivity();
		home.setLastRightImgHide();
		// home.setLastRightImg(R.drawable.home_add, home.TAG_SAVE);
		home.setRightImgHide();
		home.setPreRightImgHide();
		home.setSwitchTitle("增加按键");
		home.setleftTextHide();
		home.setLeftImgBack();
		// home.setLeftTextShow();

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.saveButton:
			ControlItem item = new ControlItem();
			item.name = name_et.getText().toString();

			if (resultBitmap != null) {
				item.bgimage = resultBitmap;
			} else {
				item.bgimage = BitmapFactory.decodeResource(getResources(),
						R.drawable.home_addscene);
			}
			item.width = item.bgimage.getWidth();
			item.height = item.bgimage.getHeight();
			item.x = (LayoutValue.SCREEN_WIDTH - item.bgimage.getWidth()) / 2;
			item.y = (LayoutValue.SCREEN_HEIGHT - item.bgimage.getHeight()) / 2;
			item.controlId = control.id;
			// 插入到数据库中
			BaseDroidApp.dbOperator.insertControlItem(item);
			// 进入到命令选择
			home.onBackPressed();
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
