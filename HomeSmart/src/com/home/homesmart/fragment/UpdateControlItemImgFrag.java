package com.home.homesmart.fragment;

import net.simonvt.menudrawer.MenuDrawer;
import com.home.application.BaseDroidApp;
import com.home.constants.ControlItem;
import com.home.homesmart.R;
import com.home.homesmart.activity.HomeActivity;
import com.home.widget.CustomDialog;
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
 * 编辑遥控器属性
 * 
 * @author hyc
 * 
 */
public class UpdateControlItemImgFrag extends Fragment implements
		View.OnClickListener {

	HomeActivity home;

	Button saveButton, cancelButton;

	EditText name_et;

	ImageView add_image;

	ControlItem item;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.controlitem_upimg, null);
		home = (HomeActivity) getActivity();

		saveButton = (Button) view.findViewById(R.id.saveButton);
		cancelButton = (Button) view.findViewById(R.id.cancelButton);

		saveButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);

		add_image = (ImageView) view.findViewById(R.id.add_image);
		add_image.setOnClickListener(this);

		name_et = (EditText) view.findViewById(R.id.name_et);

		Bundle data = getArguments();
		if (data != null) {
			item = (ControlItem) data.getParcelable("parcelable");
		}

		if (item != null) {
			name_et.setText(item.name);
			add_image.setImageBitmap(item.srcimage);
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
		home.setSwitchTitle("更改图标");
		home.setLeftImgBack();
		home.setleftTextHide();

		home.mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_NONE);

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.add_image:
			BaseDroidApp.getInstanse().showPhotoBottomDialog(onclickListener);
			break;
		case R.id.saveButton:

			if (resultBitmap != null) {
				item.srcimage = resultBitmap;
				item.bgimage = resultBitmap;
			}

			BaseDroidApp.dbOperator.updateControlItem(item);
			home.onBackPressed();
			// ViewPagerContainerFrag.vp.setCurrentItem();
			break;
		case R.id.cancelButton:
			home.onBackPressed();
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
