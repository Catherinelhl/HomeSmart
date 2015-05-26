package com.home.homesmart.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.home.constants.Constants;
import com.home.homesmart.R;
import com.home.homesmart.fragment.LeftSlidingFragment;
import com.home.homesmart.fragment.OuterFragment;
import com.home.homesmart.fragment.RightSlidingFragment;
import com.home.utils.Logger;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class SlideHomeActivity extends SlidingFragmentActivity {

	protected Fragment fragment;

	public SlidingMenu sm;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setBehindContentView(R.layout.slide_menu_layout);

		if (savedInstanceState == null) {
			FragmentTransaction t = getSupportFragmentManager()
					.beginTransaction();
			fragment = new LeftSlidingFragment();
			t.replace(R.id.slide_menu_frame, fragment);
			t.commit();
		} else {
			fragment = (Fragment) getSupportFragmentManager().findFragmentById(
					R.id.slide_menu_frame);

		}

		sm = getSlidingMenu();
		// 设置阴影图片
		sm.setShadowDrawable(R.drawable.shadow);
		// 设置阴影图片的宽度
		sm.setShadowWidthRes(R.dimen.shadow_width);
		// 设置左右滑菜单
		sm.setMode(SlidingMenu.LEFT_RIGHT);
		// slidemenu拉出的最大宽度
		sm.setBehindWidth(R.dimen.slidingmenu_width);
		// SlidingMenu划出时主页面显示的剩余宽度
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// slidingmenu滑动时的渐变程度
		sm.setFadeDegree(0.35f);
		// 设置滑动的屏幕范围，该设置为全屏区域都可以滑动
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

		sm.setSecondaryMenu(R.layout.main_right_layout);
		FragmentTransaction rightTranscation = getSupportFragmentManager()
				.beginTransaction();
		Fragment rightFrag = new RightSlidingFragment();
		rightTranscation.replace(R.id.main_right_fragment, rightFrag);
		rightTranscation.commit();

	}

	public String imageTempPath = "";
	public static final String IMAGE_UNSPECIFIED = "image/*";
	public static int RESULT_LOAD_IMAGE = 1000;
	public static final int CAMERA_WITH_DATA = 1001;
	public static final int PHOTO_PICKED_WITH_DATA = 1002;
	public static final int PHOTORESULT = 1003;

	public static Bitmap resultBitmap;

	/**
	 * 从图库选择
	 */
	public void selectFromContent() {
		Logger.log("TAG_IMAGE_LIB");
		Intent i = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(i, RESULT_LOAD_IMAGE);
	}

	/**
	 * 拍照
	 */
	public void doTakePhoto() {
		try {
			Logger.log("TAG_TAKE_PHOTO");
			String status = Environment.getExternalStorageState();
			if (!status.equals(Environment.MEDIA_MOUNTED)) {
				showToast("没有SD卡");
				return;
			}
			String path = Environment.getExternalStorageDirectory()
					+ "/IMG_TEMP/";
			SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
			String fileName = sd.format(new Date()) + ".jpg";
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			imageTempPath = path + fileName;
			file = new File(path, fileName);
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
			startActivityForResult(intent, CAMERA_WITH_DATA);

		} catch (Exception e) {
			Logger.e(e.toString());
		}
	}

	/**
	 * 相册选择
	 */
	public void doPickPhotoFromGallery() {
		try {
			Logger.log("TAG_SEL_PHONE");
			String status = Environment.getExternalStorageState();
			if (!status.equals(Environment.MEDIA_MOUNTED)) {
				showToast("没有SD卡");
				return;
			}

			Intent intent = new Intent();
			intent.setType(IMAGE_UNSPECIFIED);
			intent.setAction(Intent.ACTION_GET_CONTENT);
			// Intent intent2=Intent.createChooser(intent, "选择图片");
			startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
		} catch (Exception e) {
			Logger.e(e.toString());
		}
	}

	/**
	 * 系统裁剪
	 * 
	 * @param uri
	 */
	protected void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 400);
		intent.putExtra("outputY", 400);
		intent.putExtra("return-data", true);
		getSupportFragmentManager().findFragmentById(R.id.vp_frame_container)
				.startActivityForResult(intent, PHOTORESULT);
	}

	/**
	 * 自定义裁剪
	 * 
	 * @param uri
	 */
	protected void startMyPhotoZoom(Uri uri) {

	}

	/**
	 * 保存图片
	 * 
	 * @param bm
	 * @param tipFlag
	 * @return
	 */
	public String savePic(Bitmap bm, boolean tipFlag) {
		String path = Constants.imagePath;
		String name = String.valueOf(System.currentTimeMillis());

		try {
			File dirFile = new File(path);
			if (!dirFile.exists()) {
				dirFile.mkdir();
			}

			File myCaptureFile = new File(path + name + ".jpg");
			FileOutputStream fos = new FileOutputStream(myCaptureFile);

			if (bm.compress(Bitmap.CompressFormat.JPEG, 100, fos)) {
				if (tipFlag)
					showToast("保存成功:" + path + name + ".jpg");
			}
			fos.close();
		} catch (Exception e) {
			Logger.e(e.toString());
		}

		return path + name + ".jpg";
	}

	/**
	 * @param id
	 *            显示 toast
	 */
	public void showToast(int id) {
		Toast.makeText(this, getResources().getString(id), Toast.LENGTH_SHORT)
				.show();
	}

	/**
	 * 显示toast
	 * 
	 * @param txt
	 */
	public void showToast(String txt) {
		// Toast.makeText(this, txt, Toast.LENGTH_LONG).show();
		Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 关闭输入法
	 */
	public void closeInputMethod() {
		InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (manager != null) {
			manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if (getSupportFragmentManager().findFragmentById(
					R.id.vp_frame_container) instanceof OuterFragment) {
				showExitToast();
				return true;
			}

		}
		return super.onKeyDown(keyCode, event);
	}

	private long mExitTime;

	private void showExitToast() {
		if ((System.currentTimeMillis() - mExitTime) > 3000) {
			showToast("再按一次退出......");
			mExitTime = System.currentTimeMillis();
		} else {
			finish();
		}

	}

}
