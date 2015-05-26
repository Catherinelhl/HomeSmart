package com.home.homesmart.fragment;

import java.util.ArrayList;

import net.simonvt.menudrawer.MenuDrawer;

import com.home.application.BaseDroidApp;
import com.home.application.BaseDroidApp;
import com.home.constants.Constants;
import com.home.constants.Control;
import com.home.constants.ControlItem;
import com.home.constants.Scene;
import com.home.homesmart.R;
import com.home.homesmart.activity.HomeActivity;
import com.home.net.MyBaseAsyncTask;
import com.home.net.MyBaseAsyncTask.BaseAsyncListener;
import com.home.utils.DrawableUtils;
import com.home.utils.Logger;
import com.home.utils.PrefrenceUtils;
import com.home.utils.StringTools;
import com.home.view.CommonTitleView;
import com.home.view.DragEditViewGroup;
import com.home.view.DragWindowManagerView;
import com.home.view.Workspace;
import com.home.widget.CustomDialog;

import android.app.Service;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnWindowFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * 遥控
 * 
 * @author hyc
 * 
 */
public class RemoteControlFrag extends Fragment implements OnClickListener,
		BaseAsyncListener {

	ImageButton btn_tv_close, btn_power, btn_tv_menu, btn_tv_exit,
			btn_sound_add, btn_sound_reduce;

	ImageButton btn_tv_ok, btn_tv_up, btn_tv_down, btn_tv_left, btn_tv_right;

	ImageButton btn_num_1, btn_num_2, btn_num_3, btn_num_4, btn_num_5,
			btn_num_6, btn_num_7, btn_num_8, btn_num_9, btn_num_0,
			btn_num_repeat, btn_num_nosound;

	// public static DragWindowManagerView dragGridView;

	HomeActivity home;

	// public DragViewGroup dragView;

	Workspace workspace;

	int[] location = new int[2];

	int btn_tv_close_count = 0, btn_power_count = 0, btn_tv_menu_count = 0,
			btn_tv_exit_count = 0, btn_sound_add_count = 0,
			btn_sound_reduce_count = 0;
	int btn_tv_ok_count = 0, btn_tv_up_count = 0, btn_tv_down_count = 0,
			btn_tv_left_count = 0, btn_tv_right_count = 0;

	int btn_num_0_count = 0, btn_num_1_count = 0, btn_num_2_count = 0,
			btn_num_3_count = 0, btn_num_4_count = 0, btn_num_5_count = 0,
			btn_num_6_count = 0, btn_num_7_count = 0, btn_num_8_count = 0,
			btn_num_9_count = 0, btn_num_repeat_count = 0,
			btn_num_nosound_count = 0;

	Control control;

	public RemoteControlFrag() {
		// setRetainInstance(true);
	}

	int controlId;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		home = (HomeActivity) getActivity();
		// home.setTitleViewHide();

		if (control == null) {
			control = BaseDroidApp.dbOperator.getDefaultControl(1);

			if (control == null) {
				control = BaseDroidApp.dbOperator.getSelectControl().get(0);
			}
		}
		Bundle data = getArguments();
		if (data != null) {
			control = (Control) data.getParcelable("parcelable");
		}

		if (PrefrenceUtils.getInstance(home).getSwitchState(
				Constants.UPDATE_REMOTE)) {

			// View view = inflater.inflate(R.layout.rm_tv_show_layout, null);
			//
			// CommonTitleView titleView = (CommonTitleView) view
			// .findViewById(R.id.toplayout);
			// titleView.setHeadTitleText(control.name);
			// titleView.setleftImg(R.drawable.home_back, home.TAG_BACK);
			// titleView.setLastRightImg(R.drawable.home_menu,
			// home.TAG_REMOTECONTROL);
			//
			// DragEditViewGroup de_viewgroup = (DragEditViewGroup) view
			// .findViewById(R.id.de_viewgroup);
			// de_viewgroup.setTag(control);
			// de_viewgroup.setUpdateState(false);
			//
			// return view;

			workspace = new Workspace(home, false, control);
			workspace.removeAllViews();
			workspace.initViews();

			setRemoteState();

			return workspace;
		} else {
			View view = inflater.inflate(R.layout.rm_tv_layout, null);

			btn_tv_close = (ImageButton) view.findViewById(R.id.btn_tv_close);
			btn_power = (ImageButton) view.findViewById(R.id.btn_power);
			btn_tv_menu = (ImageButton) view.findViewById(R.id.btn_tv_menu);
			btn_tv_exit = (ImageButton) view.findViewById(R.id.btn_tv_exit);
			btn_sound_add = (ImageButton) view.findViewById(R.id.btn_sound_add);
			btn_sound_reduce = (ImageButton) view
					.findViewById(R.id.btn_sound_reduce);

			btn_tv_ok = (ImageButton) view.findViewById(R.id.btn_tv_ok);
			btn_tv_up = (ImageButton) view.findViewById(R.id.btn_tv_up);
			btn_tv_down = (ImageButton) view.findViewById(R.id.btn_tv_down);
			btn_tv_left = (ImageButton) view.findViewById(R.id.btn_tv_left);
			btn_tv_right = (ImageButton) view.findViewById(R.id.btn_tv_right);

			btn_num_1 = (ImageButton) view.findViewById(R.id.btn_num_1);
			btn_num_2 = (ImageButton) view.findViewById(R.id.btn_num_2);
			btn_num_3 = (ImageButton) view.findViewById(R.id.btn_num_3);
			btn_num_4 = (ImageButton) view.findViewById(R.id.btn_num_4);
			btn_num_5 = (ImageButton) view.findViewById(R.id.btn_num_5);
			btn_num_6 = (ImageButton) view.findViewById(R.id.btn_num_6);
			btn_num_7 = (ImageButton) view.findViewById(R.id.btn_num_7);
			btn_num_8 = (ImageButton) view.findViewById(R.id.btn_num_8);
			btn_num_9 = (ImageButton) view.findViewById(R.id.btn_num_9);
			btn_num_0 = (ImageButton) view.findViewById(R.id.btn_num_0);

			btn_num_repeat = (ImageButton) view
					.findViewById(R.id.btn_num_repeat);
			btn_num_nosound = (ImageButton) view
					.findViewById(R.id.btn_num_nosound);

			btn_tv_close.setOnClickListener(this);
			btn_power.setOnClickListener(this);
			btn_tv_menu.setOnClickListener(this);
			btn_tv_exit.setOnClickListener(this);
			btn_sound_add.setOnClickListener(this);
			btn_sound_reduce.setOnClickListener(this);

			btn_tv_ok.setOnClickListener(this);
			btn_tv_up.setOnClickListener(this);
			btn_tv_down.setOnClickListener(this);
			btn_tv_left.setOnClickListener(this);
			btn_tv_right.setOnClickListener(this);

			btn_num_1.setOnClickListener(this);
			btn_num_2.setOnClickListener(this);
			btn_num_3.setOnClickListener(this);
			btn_num_4.setOnClickListener(this);
			btn_num_5.setOnClickListener(this);
			btn_num_6.setOnClickListener(this);
			btn_num_7.setOnClickListener(this);
			btn_num_8.setOnClickListener(this);
			btn_num_9.setOnClickListener(this);
			btn_num_0.setOnClickListener(this);
			btn_num_repeat.setOnClickListener(this);
			btn_num_nosound.setOnClickListener(this);

			// ------------------------------------------------方法一
			// int w = View.MeasureSpec.makeMeasureSpec(0,
			// View.MeasureSpec.UNSPECIFIED);
			// int h = View.MeasureSpec.makeMeasureSpec(0,
			// View.MeasureSpec.UNSPECIFIED);
			// btn_tv_ok.measure(w, h);
			// int height = btn_tv_ok.getMeasuredHeight();
			// int width = btn_tv_ok.getMeasuredWidth();
			// Logger.log("height:" + height + ",width:" + width);

			// -----------------------------------------------方法二
			// ViewTreeObserver vto = btn_tv_ok.getViewTreeObserver();
			// vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener()
			// {
			// public boolean onPreDraw() {
			// int height = btn_tv_ok.getMeasuredHeight();
			// int width = btn_tv_ok.getMeasuredWidth();
			//
			// }
			// });

			setRemoteState();

			return view;
		}

		// dragGridView = new DragWindowManagerView(getActivity());
		// dragView = new DragViewGroup(home);
		// workspace = new Workspace(home, false);
		// return workspace;

	}

	public void setRemoteState() {
		home.setLastRightImg(R.drawable.home_menu, home.TAG_REMOTECONTROL);
		// home.setSwitchTitle(control.name);
		if (ViewPagerContainerFrag.control != null) {
			Logger.log("onCreateView===============>control name:"
					+ ViewPagerContainerFrag.control.name);
			home.setSwitchTitle(ViewPagerContainerFrag.control.name);
		}

		home.setLeftImgShow();
		home.setleftTextHide();
		home.setRightImgHide();
		home.setPreRightImgHide();

		home.mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_FULLSCREEN);

		home.initBottomMenu();

		if (PrefrenceUtils.getInstance(home).getSwitchState(
				Constants.UPDATE_REMOTE)) {

		} else {
			new MyBaseAsyncTask(home).setBaseAsyncListener(this).execute(
					new Object[] { null });
		}

		PrefrenceUtils.getInstance(home).setBoolean(Constants.UPDATE_REMOTE,
				true);
	}

	public void setControl(Control control) {
		this.control = control;
	}

	public void insertAllView() {
		Logger.log("insertAllView====================>");
		try {
			// 初始化遥控器
			// initControl();

			ViewTreeObserver vto = btn_tv_close.getViewTreeObserver();
			vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {
					btn_tv_close_count++;
					Logger.log("btn_tv_close_count:" + btn_tv_close_count);
					if (btn_tv_close_count == 2) {
						ControlItem item = new ControlItem();
						item.width = btn_tv_close.getWidth();
						item.height = btn_tv_close.getHeight();
						item.srcimage = BitmapFactory.decodeResource(
								home.getResources(), R.drawable.home_power);
						item.bgimage = BitmapFactory.decodeResource(
								home.getResources(),
								R.drawable.home_power_press);
						btn_tv_close.getLocationOnScreen(location);
						Logger.log("btn_tv_close location[0]:" + location[0]);
						Logger.log("btn_tv_close location[1]:" + location[1]);
						item.x = location[0];
						item.y = location[1] - home.titleViewHeight
								- home.statusBarHeight;
						Logger.log("item.y:" + item.y);
						item.name = "开关机";
						item.controlId = 1;
						btn_tv_close.setTag(item);
						BaseDroidApp.dbOperator.insertControlItem(item);
						item.controlId = 2;
						BaseDroidApp.dbOperator.insertControlItem(item);
					}
				}
			});

			vto = btn_power.getViewTreeObserver();
			vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {
					btn_power_count++;
					if (btn_power_count == 2) {
						ControlItem item = new ControlItem();
						item.width = btn_power.getWidth();
						item.height = btn_power.getHeight();
						item.srcimage = BitmapFactory.decodeResource(
								home.getResources(), R.drawable.home_tvavswitch);
						item.bgimage = BitmapFactory.decodeResource(
								home.getResources(),
								R.drawable.home_tvavswitch_press);
						btn_power.getLocationOnScreen(location);
						Logger.log("btn_power location[0]:" + location[0]);
						Logger.log("btn_power location[1]:" + location[1]);
						item.x = location[0];
						item.y = location[1] - home.titleViewHeight
								- home.statusBarHeight;
						Logger.log("item.y:" + item.y);
						item.name = "模式切换";
						item.controlId = 1;
						btn_power.setTag(item);
						BaseDroidApp.dbOperator.insertControlItem(item);
						item.controlId = 2;
						BaseDroidApp.dbOperator.insertControlItem(item);
					}
				}
			});

			vto = btn_tv_menu.getViewTreeObserver();
			vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {
					btn_tv_menu_count++;
					if (btn_tv_menu_count == 2) {
						ControlItem item = new ControlItem();
						item.width = btn_tv_menu.getWidth();
						item.height = btn_tv_menu.getHeight();
						item.srcimage = BitmapFactory.decodeResource(
								home.getResources(),
								R.drawable.home_remote_menu);
						item.bgimage = BitmapFactory.decodeResource(
								home.getResources(),
								R.drawable.home_remote_menu_press);
						btn_tv_menu.getLocationOnScreen(location);
						Logger.log("btn_tv_menu location[0]:" + location[0]);
						Logger.log("btn_tv_menu location[1]:" + location[1]);
						item.x = location[0];
						item.y = location[1] - home.titleViewHeight
								- home.statusBarHeight;
						Logger.log("item.y:" + item.y);
						item.name = "菜单";
						item.controlId = 1;
						btn_tv_menu.setTag(item);
						BaseDroidApp.dbOperator.insertControlItem(item);
						item.controlId = 2;
						BaseDroidApp.dbOperator.insertControlItem(item);
					}
				}
			});

			vto = btn_tv_exit.getViewTreeObserver();
			vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {
					btn_tv_exit_count++;
					if (btn_tv_exit_count == 2) {
						ControlItem item = new ControlItem();
						item.width = btn_tv_exit.getWidth();
						item.height = btn_tv_exit.getHeight();
						item.srcimage = BitmapFactory.decodeResource(
								home.getResources(),
								R.drawable.home_remote_back);
						item.bgimage = BitmapFactory.decodeResource(
								home.getResources(),
								R.drawable.home_remote_back_press);
						btn_tv_exit.getLocationOnScreen(location);
						Logger.log("btn_tv_exit location[0]:" + location[0]);
						Logger.log("btn_tv_exit location[1]:" + location[1]);
						item.x = location[0];
						item.y = location[1] - home.titleViewHeight
								- home.statusBarHeight;
						Logger.log("item.y:" + item.y);
						item.name = "退出";
						item.controlId = 1;
						btn_tv_exit.setTag(item);
						BaseDroidApp.dbOperator.insertControlItem(item);
						item.controlId = 2;
						BaseDroidApp.dbOperator.insertControlItem(item);
					}
				}
			});

			vto = btn_sound_add.getViewTreeObserver();
			vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {
					btn_sound_add_count++;
					if (btn_sound_add_count == 2) {
						ControlItem item = new ControlItem();
						item.width = btn_sound_add.getWidth();
						item.height = btn_sound_add.getHeight();
						item.srcimage = BitmapFactory.decodeResource(
								home.getResources(),
								R.drawable.home_remote_plus);
						item.bgimage = BitmapFactory.decodeResource(
								home.getResources(),
								R.drawable.home_remote_plus_press);
						btn_sound_add.getLocationOnScreen(location);
						Logger.log("btn_sound_add location[0]:" + location[0]);
						Logger.log("btn_sound_add location[1]:" + location[1]);
						item.x = location[0];
						item.y = location[1] - home.titleViewHeight
								- home.statusBarHeight;
						Logger.log("item.y:" + item.y);
						item.name = "音量加";
						item.controlId = 1;
						btn_sound_add.setTag(item);
						BaseDroidApp.dbOperator.insertControlItem(item);
						item.controlId = 2;
						BaseDroidApp.dbOperator.insertControlItem(item);
					}
				}
			});

			vto = btn_sound_reduce.getViewTreeObserver();
			vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {
					btn_sound_reduce_count++;
					if (btn_sound_reduce_count == 2) {
						ControlItem item = new ControlItem();
						item.width = btn_sound_reduce.getWidth();
						item.height = btn_sound_reduce.getHeight();
						item.srcimage = BitmapFactory.decodeResource(
								home.getResources(), R.drawable.home_remote_min);
						item.bgimage = BitmapFactory.decodeResource(
								home.getResources(),
								R.drawable.home_remote_min_press);
						btn_sound_reduce.getLocationOnScreen(location);
						Logger.log("btn_sound_reduce location[0]:"
								+ location[0]);
						Logger.log("btn_sound_reduce location[1]:"
								+ location[1]);
						item.x = location[0];
						item.y = location[1] - home.titleViewHeight
								- home.statusBarHeight;
						Logger.log("item.y:" + item.y);
						item.name = "音量减";
						item.controlId = 1;
						btn_sound_reduce.setTag(item);
						BaseDroidApp.dbOperator.insertControlItem(item);
						item.controlId = 2;
						BaseDroidApp.dbOperator.insertControlItem(item);
					}
				}
			});

			vto = btn_tv_ok.getViewTreeObserver();
			vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {
					btn_tv_ok_count++;
					if (btn_tv_ok_count == 2) {
						ControlItem item = new ControlItem();
						item.width = btn_tv_ok.getWidth();
						item.height = btn_tv_ok.getHeight();
						item.srcimage = BitmapFactory.decodeResource(
								home.getResources(), R.drawable.home_remote_ok);
						item.bgimage = BitmapFactory.decodeResource(
								home.getResources(),
								R.drawable.home_remote_ok_press);
						btn_tv_ok.getLocationOnScreen(location);
						Logger.log("btn_tv_ok location[0]:" + location[0]);
						Logger.log("btn_tv_ok location[1]:" + location[1]);
						item.x = location[0];
						item.y = location[1] - home.titleViewHeight
								- home.statusBarHeight;
						Logger.log("item.y:" + item.y);
						item.name = "确定";
						item.controlId = 1;
						btn_tv_ok.setTag(item);
						BaseDroidApp.dbOperator.insertControlItem(item);
						item.controlId = 2;
						BaseDroidApp.dbOperator.insertControlItem(item);
					}
				}
			});

			vto = btn_tv_down.getViewTreeObserver();
			vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {
					btn_tv_down_count++;
					if (btn_tv_down_count == 2) {
						ControlItem item = new ControlItem();
						item.width = btn_tv_down.getWidth();
						item.height = btn_tv_down.getHeight();
						item.srcimage = BitmapFactory.decodeResource(
								home.getResources(),
								R.drawable.home_remote_down_bg);
						item.bgimage = BitmapFactory.decodeResource(
								home.getResources(),
								R.drawable.home_remote_down_bg_press);
						btn_tv_down.getLocationOnScreen(location);
						Logger.log("btn_tv_down location[0]:" + location[0]);
						Logger.log("btn_tv_down location[1]:" + location[1]);
						item.x = location[0];
						item.y = location[1] - home.titleViewHeight
								- home.statusBarHeight;
						Logger.log("item.y:" + item.y);
						item.name = "下";
						item.controlId = 1;
						btn_tv_down.setTag(item);
						BaseDroidApp.dbOperator.insertControlItem(item);
						item.controlId = 2;
						BaseDroidApp.dbOperator.insertControlItem(item);
					}
				}
			});

			vto = btn_tv_up.getViewTreeObserver();
			vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {
					btn_tv_up_count++;
					if (btn_tv_up_count == 2) {
						ControlItem item = new ControlItem();
						item.width = btn_tv_up.getWidth();
						item.height = btn_tv_up.getHeight();
						item.srcimage = BitmapFactory.decodeResource(
								home.getResources(),
								R.drawable.home_remote_up_bg);
						item.bgimage = BitmapFactory.decodeResource(
								home.getResources(),
								R.drawable.home_remote_up_bg_press);
						btn_tv_up.getLocationOnScreen(location);
						Logger.log("btn_tv_up location[0]:" + location[0]);
						Logger.log("btn_tv_up location[1]:" + location[1]);
						item.x = location[0];
						item.y = location[1] - home.titleViewHeight
								- home.statusBarHeight;
						Logger.log("item.y:" + item.y);
						item.name = "上";
						item.controlId = 1;
						btn_tv_up.setTag(item);
						BaseDroidApp.dbOperator.insertControlItem(item);
						item.controlId = 2;
						BaseDroidApp.dbOperator.insertControlItem(item);
					}
				}
			});

			vto = btn_tv_left.getViewTreeObserver();
			vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {
					btn_tv_left_count++;
					if (btn_tv_left_count == 2) {
						ControlItem item = new ControlItem();
						item.width = btn_tv_left.getWidth();
						item.height = btn_tv_left.getHeight();
						item.srcimage = BitmapFactory.decodeResource(
								home.getResources(),
								R.drawable.home_remote_left_bg);
						item.bgimage = BitmapFactory.decodeResource(
								home.getResources(),
								R.drawable.home_remote_left_bg_press);
						btn_tv_left.getLocationOnScreen(location);
						Logger.log("btn_tv_left location[0]:" + location[0]);
						Logger.log("btn_tv_left location[1]:" + location[1]);
						item.x = location[0];
						item.y = location[1] - home.titleViewHeight
								- home.statusBarHeight;
						Logger.log("item.y:" + item.y);
						item.name = "左";
						item.controlId = 1;
						btn_tv_left.setTag(item);
						BaseDroidApp.dbOperator.insertControlItem(item);
						item.controlId = 2;
						BaseDroidApp.dbOperator.insertControlItem(item);
					}
				}
			});

			vto = btn_tv_right.getViewTreeObserver();
			vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {
					btn_tv_right_count++;
					if (btn_tv_right_count == 2) {
						ControlItem item = new ControlItem();
						item.width = btn_tv_right.getWidth();
						item.height = btn_tv_right.getHeight();
						item.srcimage = BitmapFactory.decodeResource(
								home.getResources(),
								R.drawable.home_remote_right_bg);
						item.bgimage = BitmapFactory.decodeResource(
								home.getResources(),
								R.drawable.home_remote_right_bg_press);
						btn_tv_right.getLocationOnScreen(location);
						Logger.log("btn_tv_right location[0]:" + location[0]);
						Logger.log("btn_tv_right location[1]:" + location[1]);
						item.x = location[0];
						item.y = location[1] - home.titleViewHeight
								- home.statusBarHeight;
						Logger.log("item.y:" + item.y);
						item.name = "右";
						item.controlId = 1;
						btn_tv_right.setTag(item);
						BaseDroidApp.dbOperator.insertControlItem(item);
						item.controlId = 2;
						BaseDroidApp.dbOperator.insertControlItem(item);
					}
				}
			});

			vto = btn_num_0.getViewTreeObserver();
			vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {
					btn_num_0_count++;
					if (btn_num_0_count == 2) {
						ControlItem item = new ControlItem();
						item.width = btn_num_0.getWidth();
						item.height = btn_num_0.getHeight();
						item.srcimage = BitmapFactory.decodeResource(
								home.getResources(), R.drawable.home_remote_0);
						item.bgimage = BitmapFactory.decodeResource(
								home.getResources(),
								R.drawable.home_remote_0_press);
						btn_num_0.getLocationOnScreen(location);
						Logger.log("btn_num_0 location[0]:" + location[0]);
						Logger.log("btn_num_0 location[1]:" + location[1]);
						item.x = location[0];
						item.y = location[1] - home.titleViewHeight
								- home.statusBarHeight;
						Logger.log("item.y:" + item.y);
						item.name = "0";
						item.controlId = 1;
						btn_num_0.setTag(item);
						BaseDroidApp.dbOperator.insertControlItem(item);
						item.controlId = 2;
						BaseDroidApp.dbOperator.insertControlItem(item);
					}
				}
			});

			vto = btn_num_1.getViewTreeObserver();
			vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {
					btn_num_1_count++;
					if (btn_num_1_count == 2) {
						ControlItem item = new ControlItem();
						item.width = btn_num_1.getWidth();
						item.height = btn_num_1.getHeight();
						item.srcimage = BitmapFactory.decodeResource(
								home.getResources(), R.drawable.home_remote_1);
						item.bgimage = BitmapFactory.decodeResource(
								home.getResources(),
								R.drawable.home_remote_1_press);
						btn_num_1.getLocationOnScreen(location);
						Logger.log("btn_num_1 location[0]:" + location[0]);
						Logger.log("btn_num_1 location[1]:" + location[1]);
						item.x = location[0];
						item.y = location[1] - home.titleViewHeight
								- home.statusBarHeight;
						Logger.log("item.y:" + item.y);
						item.name = "1";
						item.controlId = 1;
						btn_num_1.setTag(item);
						BaseDroidApp.dbOperator.insertControlItem(item);
						item.controlId = 2;
						BaseDroidApp.dbOperator.insertControlItem(item);
					}
				}
			});

			vto = btn_num_2.getViewTreeObserver();
			vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {
					btn_num_2_count++;
					if (btn_num_2_count == 2) {
						ControlItem item = new ControlItem();
						item.width = btn_num_2.getWidth();
						item.height = btn_num_2.getHeight();
						item.srcimage = BitmapFactory.decodeResource(
								home.getResources(), R.drawable.home_remote_2);
						item.bgimage = BitmapFactory.decodeResource(
								home.getResources(),
								R.drawable.home_remote_2_press);
						btn_num_2.getLocationOnScreen(location);
						Logger.log("btn_num_2 location[0]:" + location[0]);
						Logger.log("btn_num_2 location[1]:" + location[1]);
						item.x = location[0];
						item.y = location[1] - home.titleViewHeight
								- home.statusBarHeight;
						Logger.log("item.y:" + item.y);
						item.name = "2";
						item.controlId = 1;
						btn_num_2.setTag(item);
						BaseDroidApp.dbOperator.insertControlItem(item);
						item.controlId = 2;
						BaseDroidApp.dbOperator.insertControlItem(item);
					}
				}
			});

			vto = btn_num_3.getViewTreeObserver();
			vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {
					btn_num_3_count++;
					if (btn_num_3_count == 2) {
						ControlItem item = new ControlItem();
						item.width = btn_num_3.getWidth();
						item.height = btn_num_3.getHeight();
						item.srcimage = BitmapFactory.decodeResource(
								home.getResources(), R.drawable.home_remote_3);
						item.bgimage = BitmapFactory.decodeResource(
								home.getResources(),
								R.drawable.home_remote_3_press);
						btn_num_3.getLocationOnScreen(location);
						Logger.log("btn_num_3 location[0]:" + location[0]);
						Logger.log("btn_num_3 location[1]:" + location[1]);
						item.x = location[0];
						item.y = location[1] - home.titleViewHeight
								- home.statusBarHeight;
						Logger.log("item.y:" + item.y);
						item.name = "3";
						item.controlId = 1;
						btn_num_3.setTag(item);
						BaseDroidApp.dbOperator.insertControlItem(item);
						item.controlId = 2;
						BaseDroidApp.dbOperator.insertControlItem(item);
					}
				}
			});

			vto = btn_num_4.getViewTreeObserver();
			vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {
					btn_num_4_count++;
					if (btn_num_4_count == 2) {
						ControlItem item = new ControlItem();
						item.width = btn_num_4.getWidth();
						item.height = btn_num_4.getHeight();
						item.srcimage = BitmapFactory.decodeResource(
								home.getResources(), R.drawable.home_remote_4);
						item.bgimage = BitmapFactory.decodeResource(
								home.getResources(),
								R.drawable.home_remote_4_press);
						btn_num_4.getLocationOnScreen(location);
						Logger.log("btn_num_4 location[0]:" + location[0]);
						Logger.log("btn_num_4 location[1]:" + location[1]);
						item.x = location[0];
						item.y = location[1] - home.titleViewHeight
								- home.statusBarHeight;
						Logger.log("item.y:" + item.y);
						item.name = "4";
						item.controlId = 1;
						btn_num_4.setTag(item);
						BaseDroidApp.dbOperator.insertControlItem(item);
						item.controlId = 2;
						BaseDroidApp.dbOperator.insertControlItem(item);
					}
				}
			});

			vto = btn_num_5.getViewTreeObserver();
			vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {
					btn_num_5_count++;
					if (btn_num_5_count == 2) {
						ControlItem item = new ControlItem();
						item.width = btn_num_5.getWidth();
						item.height = btn_num_5.getHeight();
						item.srcimage = BitmapFactory.decodeResource(
								home.getResources(), R.drawable.home_remote_5);
						item.bgimage = BitmapFactory.decodeResource(
								home.getResources(),
								R.drawable.home_remote_5_press);
						btn_num_5.getLocationOnScreen(location);
						Logger.log("btn_num_5 location[0]:" + location[0]);
						Logger.log("btn_num_5 location[1]:" + location[1]);
						item.x = location[0];
						item.y = location[1] - home.titleViewHeight
								- home.statusBarHeight;
						Logger.log("item.y:" + item.y);
						item.name = "5";
						item.controlId = 1;
						btn_num_5.setTag(item);
						BaseDroidApp.dbOperator.insertControlItem(item);
						item.controlId = 2;
						BaseDroidApp.dbOperator.insertControlItem(item);
					}
				}
			});

			vto = btn_num_6.getViewTreeObserver();
			vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {
					btn_num_6_count++;
					if (btn_num_6_count == 2) {
						ControlItem item = new ControlItem();
						item.width = btn_num_6.getWidth();
						item.height = btn_num_6.getHeight();
						item.srcimage = BitmapFactory.decodeResource(
								home.getResources(), R.drawable.home_remote_6);
						item.bgimage = BitmapFactory.decodeResource(
								home.getResources(),
								R.drawable.home_remote_6_press);
						btn_num_6.getLocationOnScreen(location);
						Logger.log("btn_num_6 location[0]:" + location[0]);
						Logger.log("btn_num_6 location[1]:" + location[1]);
						item.x = location[0];
						item.y = location[1] - home.titleViewHeight
								- home.statusBarHeight;
						Logger.log("item.y:" + item.y);
						item.name = "6";
						item.controlId = 1;
						btn_num_6.setTag(item);
						BaseDroidApp.dbOperator.insertControlItem(item);
						item.controlId = 2;
						BaseDroidApp.dbOperator.insertControlItem(item);
					}
				}
			});

			vto = btn_num_7.getViewTreeObserver();
			vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {
					btn_num_7_count++;
					if (btn_num_7_count == 2) {
						ControlItem item = new ControlItem();
						item.width = btn_num_7.getWidth();
						item.height = btn_num_7.getHeight();
						item.srcimage = BitmapFactory.decodeResource(
								home.getResources(), R.drawable.home_remote_7);
						item.bgimage = BitmapFactory.decodeResource(
								home.getResources(),
								R.drawable.home_remote_7_press);
						btn_num_7.getLocationOnScreen(location);
						Logger.log("btn_num_7 location[0]:" + location[0]);
						Logger.log("btn_num_7 location[1]:" + location[1]);
						item.x = location[0];
						item.y = location[1] - home.titleViewHeight
								- home.statusBarHeight;
						Logger.log("item.y:" + item.y);
						item.name = "7";
						item.controlId = 1;
						btn_num_7.setTag(item);
						BaseDroidApp.dbOperator.insertControlItem(item);
						item.controlId = 2;
						BaseDroidApp.dbOperator.insertControlItem(item);
					}
				}
			});

			vto = btn_num_8.getViewTreeObserver();
			vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {
					btn_num_8_count++;
					if (btn_num_8_count == 2) {
						ControlItem item = new ControlItem();
						item.width = btn_num_8.getWidth();
						item.height = btn_num_8.getHeight();
						item.srcimage = BitmapFactory.decodeResource(
								home.getResources(), R.drawable.home_remote_8);
						item.bgimage = BitmapFactory.decodeResource(
								home.getResources(),
								R.drawable.home_remote_8_press);
						btn_num_8.getLocationOnScreen(location);
						Logger.log("btn_num_8 location[0]:" + location[0]);
						Logger.log("btn_num_8 location[1]:" + location[1]);
						item.x = location[0];
						item.y = location[1] - home.titleViewHeight
								- home.statusBarHeight;
						Logger.log("item.y:" + item.y);
						item.name = "8";
						item.controlId = 1;
						btn_num_8.setTag(item);
						BaseDroidApp.dbOperator.insertControlItem(item);
						item.controlId = 2;
						BaseDroidApp.dbOperator.insertControlItem(item);
					}
				}
			});

			vto = btn_num_9.getViewTreeObserver();
			vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {
					btn_num_9_count++;
					if (btn_num_9_count == 2) {
						ControlItem item = new ControlItem();
						item.width = btn_num_9.getWidth();
						item.height = btn_num_9.getHeight();
						item.srcimage = BitmapFactory.decodeResource(
								home.getResources(), R.drawable.home_remote_9);
						item.bgimage = BitmapFactory.decodeResource(
								home.getResources(),
								R.drawable.home_remote_9_press);
						btn_num_9.getLocationOnScreen(location);
						Logger.log("btn_num_9 location[0]:" + location[0]);
						Logger.log("btn_num_9 location[1]:" + location[1]);
						item.x = location[0];
						item.y = location[1] - home.titleViewHeight
								- home.statusBarHeight;
						Logger.log("item.y:" + item.y);
						item.name = "9";
						item.controlId = 1;
						btn_num_9.setTag(item);
						BaseDroidApp.dbOperator.insertControlItem(item);
						item.controlId = 2;
						BaseDroidApp.dbOperator.insertControlItem(item);
					}
				}
			});

			vto = btn_num_repeat.getViewTreeObserver();
			vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {
					btn_num_repeat_count++;
					if (btn_num_repeat_count == 2) {
						ControlItem item = new ControlItem();
						item.width = btn_num_repeat.getWidth();
						item.height = btn_num_repeat.getHeight();
						item.srcimage = BitmapFactory.decodeResource(
								home.getResources(),
								R.drawable.home_remote_repeat);
						item.bgimage = BitmapFactory.decodeResource(
								home.getResources(),
								R.drawable.home_remote_repeat_press);
						btn_num_repeat.getLocationOnScreen(location);
						Logger.log("btn_num_repeat location[0]:" + location[0]);
						Logger.log("btn_num_repeat location[1]:" + location[1]);
						item.x = location[0];
						item.y = location[1] - home.titleViewHeight
								- home.statusBarHeight;
						Logger.log("item.y:" + item.y);
						item.name = "重复";
						item.controlId = 1;
						btn_num_repeat.setTag(item);
						BaseDroidApp.dbOperator.insertControlItem(item);
						item.controlId = 2;
						BaseDroidApp.dbOperator.insertControlItem(item);
					}
				}
			});

			vto = btn_num_nosound.getViewTreeObserver();
			vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {
					btn_num_nosound_count++;
					if (btn_num_nosound_count == 2) {
						ControlItem item = new ControlItem();
						item.width = btn_num_nosound.getWidth();
						item.height = btn_num_nosound.getHeight();
						item.srcimage = BitmapFactory.decodeResource(
								home.getResources(),
								R.drawable.home_remote_nosound);
						item.bgimage = BitmapFactory.decodeResource(
								home.getResources(),
								R.drawable.home_remote_nosound_press);
						btn_num_nosound.getLocationOnScreen(location);
						Logger.log("btn_num_nosound location[0]:" + location[0]);
						Logger.log("btn_num_nosound location[1]:" + location[1]);
						item.x = location[0];
						item.y = location[1] - home.titleViewHeight
								- home.statusBarHeight;
						Logger.log("item.y:" + item.y);
						item.name = "静音";
						item.controlId = 1;
						btn_num_nosound.setTag(item);
						BaseDroidApp.dbOperator.insertControlItem(item);
						item.controlId = 2;
						BaseDroidApp.dbOperator.insertControlItem(item);
					}
				}
			});

		} catch (Exception e) {
			Logger.log("没有全部插入成功：" + e.toString());
		}

	}

	private void initControl() {
		Bitmap bitmap = BitmapFactory.decodeResource(home.getResources(),
				R.drawable.home_addimg_bg);

		Control control = new Control();
		control.name = "客厅电视";
		control.image = bitmap;
		BaseDroidApp.dbOperator.insertControl(control);
		control = new Control();
		control.name = "卧室电视";
		control.image = bitmap;
		BaseDroidApp.dbOperator.insertControl(control);
		control = new Control();
		control.name = "客厅空调";
		control.image = bitmap;
		BaseDroidApp.dbOperator.insertControl(control);
		control = new Control();
		control.name = "卧室空调";
		control.image = bitmap;
		BaseDroidApp.dbOperator.insertControl(control);
	}

	/**
	 * 每次都会执行，所以不放任何操作(而且会执行两次，可能是FragmentPagerAdapter导致)
	 */
	@Override
	public void onResume() {
		Logger.log("<=============onResume===============>id:" + control.id
				+ ",name:" + control.name);
		super.onResume();

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onClick(View view) {
		home.vibrator();

		// 判断是否学习过
		if (view.getTag() instanceof ControlItem) {
			ControlItem item = (ControlItem) view.getTag();

			if (StringTools.isNullOrEmpty(item.code)) {
				// 没学习过，弹框是否学习
				BaseDroidApp.getInstanse().showConfirmDialog("按键还未学习,是否开始学习？",
						"取消", "学习", onClickListener);
			} else {
				// 学习过，直接发送指令

			}
		}

	}

	OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			switch (Integer.valueOf(String.valueOf(view.getTag()))) {
			case CustomDialog.TAG_SURE:
				BaseDroidApp.getInstanse().closeMessageDialog();
				break;
			case CustomDialog.TAG_CANCLE:
				// 等待学习
				Logger.log("等待学习");
				BaseDroidApp.getInstanse().closeMessageDialog();
				BaseDroidApp.getInstanse().showStudyDialog(
						"请对准设备按您要学习的红外遥控器按键进行学习", onClickListener);

				break;
			case CustomDialog.TAG_CANCEL_BUTTON:
				BaseDroidApp.getInstanse().closeMessageDialog();
				break;
			}

		}
	};

	@Override
	public void onBeforeTask() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object onTask(Object... arg0) throws Exception {

		insertAllView();
		// Thread.sleep(1 * 1000);

		return "插入成功";
	}

	@Override
	public void onAfter(Object result) {
		if ("插入成功".equals(String.valueOf(result))) {
			// 重新刷新
			// ViewPagerContainerFrag.notifyAgin();

			// home.showToast("插入成功");
		} else {
			// home.showToast("插入失败");
		}
	}

	@Override
	public void onTaskError(Exception e) {
		// TODO Auto-generated method stub

	}
}
