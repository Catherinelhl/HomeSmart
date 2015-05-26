package com.home.homesmart.fragment;

import net.simonvt.menudrawer.MenuDrawer;

import com.home.application.BaseApp;
import com.home.application.BaseDroidApp;
import com.home.constants.Constants;
import com.home.constants.Control;
import com.home.homesmart.R;
import com.home.homesmart.activity.HomeActivity;
import com.home.homesmart.activity.LoginActivity;
import com.home.utils.ImageLoader;
import com.home.utils.Logger;
import com.home.utils.PrefrenceUtils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 左侧视图
 * 
 * @author hyc
 * 
 */
public class LeftSlidingFragment extends Fragment implements OnClickListener {

	private View controlLayout;
	private View sceneLayout;
	private View switchLayout;
	private View setLayout;
	private View helpLayout;
	private View shareLayout;
	private View aboutusLayout;

	private ImageView headImageView;

	ImageLoader imageLoader;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Logger.log("LeftSlidingFragment==========onCreateView================>");
		View view = inflater.inflate(R.layout.main_left_fragment, container,
				false);
		controlLayout = view.findViewById(R.id.controlLayout);
		sceneLayout = view.findViewById(R.id.sceneLayout);
		switchLayout = view.findViewById(R.id.switchLayout);
		setLayout = view.findViewById(R.id.setLayout);
		helpLayout = view.findViewById(R.id.helpLayout);
		shareLayout = view.findViewById(R.id.shareLayout);
		aboutusLayout = view.findViewById(R.id.aboutusLayout);

		headImageView = (ImageView) view.findViewById(R.id.headImageView);
		headImageView.setOnClickListener(this);

		controlLayout.setOnClickListener(this);
		sceneLayout.setOnClickListener(this);
		switchLayout.setOnClickListener(this);
		setLayout.setOnClickListener(this);
		helpLayout.setOnClickListener(this);
		shareLayout.setOnClickListener(this);
		aboutusLayout.setOnClickListener(this);

		imageLoader = new ImageLoader(home, R.drawable.head_default);

		return view;
	}

	@Override
	public void onResume() {
		Logger.log("LeftSlidingFragment==========onResume================>");
		// imageLoader.displayImage(Constants.userIcon, headImageView);
		String userIcon = PrefrenceUtils.getInstance(home).getPassValue(
				"userIcon");
		if (!TextUtils.isEmpty(userIcon)) {
			imageLoader.displayImage(userIcon, headImageView);
		}
		super.onResume();
	}

	public void setHeadIcon() {
		imageLoader.displayImage(home.userIcon, headImageView);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	HomeActivity home;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		home = (HomeActivity) activity;
	}

	String title;
	boolean isShowRightMenu;

	@Override
	public void onClick(View v) {
		Fragment newContent = null;
		switch (v.getId()) {
		case R.id.headImageView:
			Intent it = new Intent(home, LoginActivity.class);
			home.startActivityForResult(it, home.REQUEST_FOR_LOGIN);

			break;
		case R.id.controlLayout:
			title = "遥控器";
			// newContent = new RemoteControlFrag();

			// Control control = BaseApp.dbOperator.getDefaultControl(1);
			// Logger.log("control id:" + control.id + "<><><><><><>");
			// if (control != null) {
			// newContent = new ViewPagerContainerFrag(control.id - 1);
			// } else {
			newContent = new ViewPagerContainerFrag(0);
			// }
			// newContent=null;
			// ViewPagerContainerFrag.vp.setCurrentItem(0);

			controlLayout.setSelected(true);

			sceneLayout.setSelected(false);
			switchLayout.setSelected(false);
			setLayout.setSelected(false);
			helpLayout.setSelected(false);
			shareLayout.setSelected(false);
			aboutusLayout.setSelected(false);

			isShowRightMenu = true;

			setRightImg(R.drawable.ic_launcher, title);

			break;
		case R.id.sceneLayout:
			title = "场景";
			newContent = new SceneFrag();
			sceneLayout.setSelected(true);

			controlLayout.setSelected(false);
			switchLayout.setSelected(false);
			setLayout.setSelected(false);
			helpLayout.setSelected(false);
			shareLayout.setSelected(false);
			aboutusLayout.setSelected(false);

			isShowRightMenu = false;

			setRightImg(R.drawable.ic_launcher, title);
			break;

		case R.id.switchLayout:
			title = "开关";
			newContent = new SwitchFrag();
			switchLayout.setSelected(true);

			controlLayout.setSelected(false);
			sceneLayout.setSelected(false);
			setLayout.setSelected(false);
			helpLayout.setSelected(false);
			shareLayout.setSelected(false);
			aboutusLayout.setSelected(false);

			isShowRightMenu = false;
			setRightImg(R.drawable.ic_launcher, title);
			break;

		case R.id.setLayout:
			title = "设置";
			newContent = new SettingFrag();
			setLayout.setSelected(true);

			controlLayout.setSelected(false);
			sceneLayout.setSelected(false);
			switchLayout.setSelected(false);
			helpLayout.setSelected(false);
			shareLayout.setSelected(false);
			aboutusLayout.setSelected(false);

			isShowRightMenu = false;
			setRightImgHide();
			break;

		case R.id.helpLayout:
			title = "帮助";
			newContent = new HelpFragment();
			helpLayout.setSelected(true);
			setRightImgHide();

			controlLayout.setSelected(false);
			sceneLayout.setSelected(false);
			switchLayout.setSelected(false);
			setLayout.setSelected(false);
			shareLayout.setSelected(false);
			aboutusLayout.setSelected(false);

			isShowRightMenu = false;
			setRightImgHide();
			break;

		case R.id.shareLayout:
			title = "分享";
			newContent = new ShareFrag();
			setRightImgHide();

			shareLayout.setSelected(true);

			controlLayout.setSelected(false);
			sceneLayout.setSelected(false);
			switchLayout.setSelected(false);
			setLayout.setSelected(false);
			helpLayout.setSelected(false);
			aboutusLayout.setSelected(false);

			isShowRightMenu = false;
			setRightImgHide();
			break;

		case R.id.aboutusLayout:
			title = "关于我们";
			newContent = new AboutUsFrag();
			setRightImgHide();

			aboutusLayout.setSelected(true);

			controlLayout.setSelected(false);
			sceneLayout.setSelected(false);
			switchLayout.setSelected(false);
			setLayout.setSelected(false);
			helpLayout.setSelected(false);
			shareLayout.setSelected(false);

			isShowRightMenu = false;
			setRightImgHide();
			break;
		}

		if (newContent != null) {
			switchFragment(newContent, title, isShowRightMenu);
		}

	}

	private void setRightImg(int res, String tag) {
		if (getActivity() == null)
			return;
		home.setLastRightImg(res, tag);
	}

	private void setRightImgHide() {
		if (getActivity() == null)
			return;
		home.setLastRightImgHide();
	}

	/**
	 * 切换fragment
	 * 
	 * @param fragment
	 */
	private void switchFragment(Fragment fragment, String title, boolean isShow) {
		if (getActivity() == null)
			return;

		home.switchContent(fragment, false, null, null);
		home.setSwitchTitle(title);

		if (isShow) {
			home.sm.setMode(SlidingMenu.LEFT_RIGHT);
			home.mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_FULLSCREEN);
		} else {
			home.sm.setMode(SlidingMenu.LEFT);
			home.mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_NONE);
		}

	}
}
