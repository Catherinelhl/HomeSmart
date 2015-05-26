package com.home.homesmart.fragment;

import com.home.application.BaseDroidApp;
import com.home.constants.Switch;
import com.home.homesmart.R;
import com.home.homesmart.activity.HomeActivity;
import com.home.utils.Logger;
import com.home.widget.CustomDialog;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * 编辑开关
 * 
 * @author hyc
 * 
 */
public class UpdateSwitchFrag extends Fragment implements View.OnClickListener {

	ImageView add_image;
	Button saveButton, cancelButton;

	EditText name_et;

	Bitmap preBitmap;

	Switch switch1;

	HomeActivity home;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Logger.log("updateSwitchFrag onCreateView");
		View view = inflater.inflate(R.layout.switch_add, null);
		add_image = (ImageView) view.findViewById(R.id.add_image);
		add_image.setOnClickListener(this);
		saveButton = (Button) view.findViewById(R.id.saveButton);
		cancelButton = (Button) view.findViewById(R.id.cancelButton);
		saveButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);

		name_et = (EditText) view.findViewById(R.id.name_et);

		Bundle data = getArguments();
		switch1 = (Switch) data.getParcelable("parcelable");

		if (switch1 != null) {
			name_et.setText(switch1.name);

			preBitmap = switch1.image;
			add_image.setImageBitmap(preBitmap);
		}

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		Logger.log("updateSwitchFrag onResume");

		home.setLastRightImgHide();
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
			switch1.name = name_et.getText().toString();

			if (resultBitmap != null) {
				switch1.image = resultBitmap;
			} else {
				switch1.image = preBitmap;
			}

			switch1.isOpen = 0;
			// 数据更新到数据库中
			BaseDroidApp.dbOperator.updateSwitch(switch1);
			// getActivity().onBackPressed();

			// 进入到开关配置
			home.configSwitch(switch1);

			break;
		case R.id.cancelButton:
			home.onBackPressed();
			break;
		}

	}

	private String imageTempPath = "";
	private static int RESULT_LOAD_IMAGE = 1000;
	private static final int CAMERA_WITH_DATA = 1001;
	private static final int PHOTO_PICKED_WITH_DATA = 1002;
	private static final int PHOTORESULT = 1003;

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

	@Override
	public void onHiddenChanged(boolean hidden) {
		Logger.log("updateSwitchFrag onHiddenChanged");
		super.onHiddenChanged(hidden);
	}

	@Override
	public void onAttach(Activity activity) {
		Logger.log("updateSwitchFrag onAttach");
		super.onAttach(activity);
		home = (HomeActivity) activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Logger.log("updateSwitchFrag onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Logger.log("updateSwitchFrag onActivityCreated");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		Logger.log("updateSwitchFrag onStart");
		super.onStart();
	}

	@Override
	public void onStop() {
		Logger.log("updateSwitchFrag onStop");
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		Logger.log("updateSwitchFrag onDestroyView");
		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		Logger.log("updateSwitchFrag onDetach");
		super.onDetach();
	}

	@Override
	public void onDestroy() {
		Logger.log("updateSwitchFrag onDestroy");
		super.onDestroy();
		home.removeFragment();
	}

}
