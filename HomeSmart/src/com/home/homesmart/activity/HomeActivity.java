package com.home.homesmart.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;

import com.home.adapter.ControlAdapter;
import com.home.application.BaseApp;
import com.home.application.BaseDroidApp;
import com.home.constants.Constants;
import com.home.constants.Control;
import com.home.constants.LayoutValue;
import com.home.constants.Order;
import com.home.constants.Scene;
import com.home.constants.Switch;
import com.home.homesmart.R;
import com.home.homesmart.fragment.AddControlItemFrag;
import com.home.homesmart.fragment.AddRemoteControlListFrag;
import com.home.homesmart.fragment.AddRemotePropertyFrag;
import com.home.homesmart.fragment.AddSceneFrag;
import com.home.homesmart.fragment.AddSwitchFrag;
import com.home.homesmart.fragment.ConfigFragment;
import com.home.homesmart.fragment.ConfigSwitchFrag;
import com.home.homesmart.fragment.OrderControlFrag;
import com.home.homesmart.fragment.RemoteControlFrag;
import com.home.homesmart.fragment.SelectOrderFrag;
import com.home.homesmart.fragment.UpdateRemoteControlFrag;
import com.home.homesmart.fragment.UpdateSceneFrag;
import com.home.homesmart.fragment.ViewPagerContainerFrag;
import com.home.homesmart.fragment.UpdateSwitchFrag;
import com.home.utils.Logger;
import com.home.utils.PrefrenceUtils;
import com.home.utils.StringTools;
import com.home.view.CommonTitleView;
import com.home.view.CommonTitleView.OnClickListener;
import com.home.view.HomeBottomLayout;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Service;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.os.TransactionTooLargeException;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 主页
 * 
 * @author hyc
 * 
 */
public class HomeActivity extends SlideHomeActivity implements OnClickListener {

	CommonTitleView commmonTitleView;

	private Fragment mContent;

	public MenuDrawer mMenuDrawer;

	public static final String TAG_MENU = "菜单";
	public static final String TAG_BACK = "返回";

	public static final String TAG_REMOTECONTROL = "遥控器";
	public static final String TAG_SCENE = "场景";
	public static final String TAG_SWITCH = "开关";
	public static final String TAG_ORDER = "命令";
	public static final String TAG_SAVE = "保存";

	public int titleViewHeight;
	public int statusBarHeight;

	public static final int REQUEST_FOR_LOGIN = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);

		Resources res = getResources();
		String pkgname = getPackageName();

		setContentView(res.getIdentifier("activity_main", "layout", pkgname));

		titleViewHeight = LayoutValue.titleViewHeight;
		statusBarHeight = LayoutValue.statusBarHeight;

		Logger.log("statusBarHeight:" + statusBarHeight + ",titleViewHeight:"
				+ titleViewHeight);

		initView();
		// initData();

		BaseDroidApp.getInstanse().setCurrentAct(this);

		Logger.log("HomeActivity=====onCreate==========>" + savedInstanceState);

		if (savedInstanceState == null) {
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.vp_frame_container,
							new ViewPagerContainerFrag(0)).commit();

			// getSupportFragmentManager().beginTransaction()
			// .replace(R.id.vp_frame_container, new ConfigFragment())
			// .commit();

		}

	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);

		// 获取状态栏高度
		Rect frame = new Rect();
		getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		statusBarHeight = frame.top;

		titleViewHeight = commmonTitleView.getHeight();

		Logger.log("statusBarHeight:" + statusBarHeight + ",titleViewHeight:"
				+ titleViewHeight);

		LayoutValue.statusBarHeight = statusBarHeight;
		LayoutValue.titleViewHeight = titleViewHeight;
	}

	public void initView() {
		mMenuDrawer = MenuDrawer.attach(this, Position.BOTTOM);
		mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_FULLSCREEN);

		View view = LayoutInflater.from(this).inflate(R.layout.activity_main,
				null);

		commmonTitleView = (CommonTitleView) view.findViewById(R.id.toplayout);
		commmonTitleView.setOnClickListener(this);

		mMenuDrawer.setContentView(view);
		// mMenuDrawer.setMenuView(R.layout.activity_bottommenu);

		initBottomMenu();
		setLeftImgShow();

		commmonTitleView.setLastRightImg(R.drawable.home_menu,
				TAG_REMOTECONTROL);
		commmonTitleView.setHeadTitleText(controlList.get(0).name);
	}

	/**
	 * 没有底部菜单
	 */
	public void noBottomMenu() {
		mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_NONE);
	}

	/**
	 * 关闭底部菜单
	 */
	public void closeBottomMenu() {
		mMenuDrawer.closeMenu(true);
	}

	ArrayList<Control> controlList;

	public void initBottomMenu() {
		View bottomView = LayoutInflater.from(this).inflate(
				R.layout.activity_bottommenu, null);
		// LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
		// 100);

		controlList = BaseDroidApp.dbOperator.getSelectControl();

		if (PrefrenceUtils.getInstance(this).getSwitchState(
				Constants.UPDATE_REMOTE)) {
		} else {
			if (controlList == null || controlList.size() == 0) {
				initControl();
				controlList = BaseApp.dbOperator.getSelectControl();
			}
		}

		ControlAdapter adapter = new ControlAdapter(this, controlList);
		HomeBottomLayout hs_view = (HomeBottomLayout) bottomView
				.findViewById(R.id.hs_view);
		hs_view.setAdapter(adapter);
		mMenuDrawer.setMenuView(bottomView);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Logger.log("HomeActivity=====onResume==========>");
		BaseDroidApp.getInstanse().setCurrentAct(this);

	}

	private void initControl() {
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.home_addimg_bg);

		Control control = new Control();
		control.name = "客厅电视";
		control.image = bitmap;
		control.isDefault = 1;
		control.type = Constants.TYPE_TV;
		BaseDroidApp.dbOperator.insertControl(control);

		control = new Control();
		control.name = "卧室电视";
		control.image = bitmap;
		control.isDefault = 0;
		control.type = Constants.TYPE_TV;
		BaseApp.dbOperator.insertControl(control);

		control = new Control();
		control.name = "客厅空调";
		control.image = bitmap;
		control.isDefault = 0;
		control.type = Constants.TYPE_AIR;
		BaseApp.dbOperator.insertControl(control);

		control = new Control();
		control.name = "卧室空调";
		control.image = bitmap;
		control.isDefault = 0;
		control.type = Constants.TYPE_AIR;
		BaseApp.dbOperator.insertControl(control);
	}

	private void initData() {
		mContent = new RemoteControlFrag();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.vp_frame_container, mContent).commit();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_head_left:

			if (TAG_MENU.equals(v.getTag())) {
				sm.showMenu();
			} else if (TAG_BACK.equals(v.getTag())) {
				onBackPressed();
			}
			break;
		case R.id.main_head_back_text:
			onBackPressed();
			break;
		case R.id.main_head_lastbt:
			// 弹出(判断tag的值)
			if (TAG_REMOTECONTROL.equals(v.getTag())) {
				sm.showSecondaryMenu();
			} else if (TAG_SCENE.equals(v.getTag())) {
				mContent = new AddSceneFrag();
				switchContent(mContent, true, null, null);
			} else if (TAG_ORDER.equals(v.getTag())) {
				selOrder(Constants.scene, false, null);
			} else if (TAG_SWITCH.equals(v.getTag())) {
				mContent = new AddSwitchFrag();
				switchContent(mContent, true, null, null);
			} else if (TAG_SAVE.equals(v.getTag())) {
				onBackPressed();
			}

			break;
		case R.id.main_head_pre_right:
			// 添加遥控器按键
			mContent = new AddControlItemFrag();
			switchContent(mContent, true, null, null);
			break;
		}

	}

	/**
	 * 设置title隐藏
	 */
	public void setTitleViewHide() {
		commmonTitleView.setVisibility(View.GONE);
	}

	/**
	 * 设置title显示
	 */
	public void setTitleViewShow() {
		commmonTitleView.setVisibility(View.VISIBLE);
	}

	// 振动
	public void vibrator() {

		if (PrefrenceUtils.getInstance(this)
				.getSwitchState(Constants.VIBRATION)) {
			Vibrator vib = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
			vib.vibrate(50);
		}
	}

	/**
	 * 遥控器控制
	 */
	public void gotoRemoteControl(Control control) {
		mContent = new RemoteControlFrag();
		switchContent(mContent, false, control, null);
	}

	/**
	 * 遥控器控制
	 */
	public void enterRemoteControl(Control control) {
		mContent = new ViewPagerContainerFrag(control.id - 1);
		switchContent(mContent, false, control, null);
	}

	/**
	 * 添加遥控器
	 */
	public void gotoAddControl(String type) {
		mContent = new AddRemotePropertyFrag();
		switchContent(mContent, false, null, type);
	}

	/**
	 * 选择命令
	 */
	public void selOrder(Scene scene, boolean isUpdate, Order order) {
		mContent = new SelectOrderFrag(isUpdate, order);
		switchContent(mContent, true, scene, null);
	}

	/**
	 * 遥控器选择
	 */
	public void gotoOrderControl(Control control, boolean isUpdate, Order order) {
		mContent = new OrderControlFrag(isUpdate, order);
		switchContent(mContent, true, control, null);
	}

	/**
	 * 编辑开关
	 */
	public void updateSwitch(Switch switch1) {
		mContent = new UpdateSwitchFrag();
		switchContent(mContent, true, switch1, null);
	}

	/**
	 * 配置开关
	 */
	public void configSwitch(Switch switch1) {
		mContent = new ConfigSwitchFrag();
		switchContent(mContent, false, switch1, null);
	}

	/**
	 * 编辑场景
	 * 
	 * @param scene
	 */
	public void updateScene(Scene scene) {
		mContent = new UpdateSceneFrag();
		switchContent(mContent, true, scene, null);
	}

	/**
	 * 移除开关(fragment返回的问题，出现重叠，所以remove掉)
	 */
	public void removeFragment() {
		Logger.log("mContent:" + mContent.getClass());

		if (mContent instanceof ConfigSwitchFrag) {
			FragmentManager mFragmentManager = getSupportFragmentManager();
			FragmentTransaction tran = mFragmentManager.beginTransaction();
			tran.remove(mContent);
			tran.commit();
		}

		else if (mContent instanceof AddRemotePropertyFrag) {
			FragmentManager mFragmentManager = getSupportFragmentManager();
			FragmentTransaction tran = mFragmentManager.beginTransaction();
			tran.remove(mContent);
			tran.commit();
		}

		else if (mContent instanceof OrderControlFrag) {
			FragmentManager mFragmentManager = getSupportFragmentManager();
			FragmentTransaction tran = mFragmentManager.beginTransaction();
			tran.remove(mContent);
			tran.commit();
		}

	}

	/**
	 * 设置左侧文字
	 * 
	 * @param text
	 */
	public void setleftTextShow(String tag) {
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		commmonTitleView.setLeftText(tag);
	}

	/**
	 * 设置左侧文字隐藏
	 */
	public void setleftTextHide() {
		commmonTitleView.setLeftTextHide();
	}

	/**
	 * 设置左侧图片显示
	 */
	public void setLeftImgShow() {
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		commmonTitleView.setleftImg(R.drawable.home_menu, TAG_MENU);
	}

	public void setLeftImgBack() {
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		commmonTitleView.setleftImg(R.drawable.home_back, TAG_BACK);
	}

	public void setLeftImg(int id) {
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		commmonTitleView.setleftImg(id, TAG_BACK);
	}

	/**
	 * 设置左侧图片隐藏
	 */
	public void setleftImgHide() {
		commmonTitleView.setLeftImgHide();
	}

	/**
	 * 设置右侧菜单图片
	 * 
	 * @param res
	 */
	public void setLastRightImg(int res, String tag) {
		commmonTitleView.setLastRightImg(res, tag);
	}

	public void setRightImg(int res) {
		commmonTitleView.setRightImg(res);
	}

	public void setRightImgHide() {
		commmonTitleView.setRightImgHide();
	}

	public void setPreRightImg(int res) {
		commmonTitleView.setPreRightImg(res);
	}

	public void setPreRightImgHide() {
		commmonTitleView.setPreRightImgHide();
	}

	/**
	 * 设置右侧菜单图片隐藏
	 */
	public void setLastRightImgHide() {
		commmonTitleView.setLastRightImgHide();
	}

	/**
	 * 切换标题
	 * 
	 * @param text
	 */
	public void setSwitchTitle(String text) {
		commmonTitleView.setHeadTitleText(text);
	}

	/**
	 * 点击菜单切换内容
	 * 
	 * @param fragment
	 *            要切换的fragment
	 * @param isAddToBackStack
	 *            是否添加到返回栈中
	 * @param obj
	 *            传递Parcelable对象
	 * @param obj
	 *            传递Serializable对象
	 */
	public void switchContent(Fragment fragment, boolean isAddToBackStack,
			Parcelable parcelable, Serializable serializable) {
		mContent = fragment;
		FragmentManager mFragmentManager = getSupportFragmentManager();
		FragmentTransaction tran = mFragmentManager.beginTransaction();

		tran.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		tran.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out,
				R.anim.push_left_in, R.anim.push_left_out);

		if (parcelable != null) {
			Bundle bundle = new Bundle();
			bundle.putParcelable("parcelable", parcelable);
			fragment.setArguments(bundle);
		}

		if (serializable != null) {
			Bundle bundle = new Bundle();
			bundle.putSerializable("serializable", serializable);
			fragment.setArguments(bundle);
		}

		tran.replace(R.id.vp_frame_container, fragment);
		int index = 0;

		if (isAddToBackStack) {
			tran.addToBackStack(null);
		} else {
			// tran.detach(fragment);
			// mFragmentManager.popBackStack();
			mFragmentManager.popBackStack(index, 0);
		}
		index = tran.commit();
		getSlidingMenu().showContent();
	}

	public void switchMultiContent(Control control) {
		ViewPagerContainerFrag frag = (ViewPagerContainerFrag) getSupportFragmentManager()
				.findFragmentById(R.id.vp_frame_container);

		if (frag != null) {
			Logger.log("frag index:" + control.id);
			frag.vp.setCurrentItem(0);
			frag.index = control.id - 1;
			frag.fpa.notifyDataSetChanged();
		}
		getSlidingMenu().showContent();
	}

	public String userIcon = "";

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Logger.log("HomeActivity======================>onActivityResult");

		if (resultCode == RESULT_OK && requestCode == REQUEST_FOR_LOGIN) {
			userIcon = data.getStringExtra("userIcon");
			Constants.userIcon = userIcon;
			// 保存到本地

		} else if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			File picture = new File(picturePath);
			startPhotoZoom(Uri.fromFile(picture));

			// resultBitmap = BitmapFactory.decodeFile(picturePath);
			// add_image.setImageBitmap(resultBitmap);
		} else if (requestCode == CAMERA_WITH_DATA && resultCode == RESULT_OK) {
			File picture = new File(imageTempPath);
			startPhotoZoom(Uri.fromFile(picture));

		} else if (requestCode == PHOTO_PICKED_WITH_DATA
				&& resultCode == RESULT_OK) {
			Uri uri = data.getData();
			startPhotoZoom(uri);
		}

		else if (requestCode == PHOTORESULT && resultCode == RESULT_OK) {
			// 处理结果
			Bundle extras = data.getExtras();
			if (extras != null) {
				resultBitmap = extras.getParcelable("data");
				// realpicPath = home.savePic(resultBitmap, false);
				// add_image.setImageBitmap(resultBitmap);
			}

		}

		super.onActivityResult(requestCode, resultCode, data);
	}

}
