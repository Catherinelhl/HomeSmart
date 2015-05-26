package com.home.homesmart.fragment;

import net.simonvt.menudrawer.MenuDrawer;

import com.home.application.BaseApp;
import com.home.application.BaseDroidApp;
import com.home.constants.Control;
import com.home.homesmart.R;
import com.home.homesmart.activity.HomeActivity;
import com.home.utils.Logger;
import com.home.widget.CustomDialog;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

/**
 * 右侧界面
 * 
 * @author hyc
 * 
 */
public class RightSlidingFragment extends Fragment implements OnClickListener {

	View addcontrolLayout;
	View updatekeyLayout;
	View updatepropertyLayout;
	View delcontrolLayout;

	HomeActivity home;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main_right_fragment, container,
				false);

		addcontrolLayout = view.findViewById(R.id.addcontrolLayout);
		updatekeyLayout = view.findViewById(R.id.updatekeyLayout);
		updatepropertyLayout = view.findViewById(R.id.updatepropertyLayout);
		delcontrolLayout = view.findViewById(R.id.delcontrolLayout);

		addcontrolLayout.setOnClickListener(this);
		updatekeyLayout.setOnClickListener(this);
		updatepropertyLayout.setOnClickListener(this);
		delcontrolLayout.setOnClickListener(this);

		home = (HomeActivity) getActivity();
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

	}

	boolean isShowMenu;

	String title;

	Control control;
	Fragment newContent = null;

	@Override
	public void onClick(View v) {
		if (ViewPagerContainerFrag.control != null) {
			control = ViewPagerContainerFrag.control;
		}
		switch (v.getId()) {
		case R.id.addcontrolLayout:

			newContent = new AddRemoteControlListFrag();
			// title =
			// getActivity().getResources().getString(R.string.addcontrol);
			isShowMenu = false;

			addcontrolLayout.setSelected(true);
			updatekeyLayout.setSelected(false);
			updatepropertyLayout.setSelected(false);
			delcontrolLayout.setSelected(false);

			break;
		case R.id.updatekeyLayout:

			newContent = new UpdateRemoteControlFrag();

			title = getActivity().getResources().getString(
					R.string.updatecontrolkey);
			isShowMenu = false;

			updatekeyLayout.setSelected(true);
			addcontrolLayout.setSelected(false);
			updatepropertyLayout.setSelected(false);
			delcontrolLayout.setSelected(false);
			break;
		case R.id.updatepropertyLayout:

			newContent = new UpdateRemotePropertyFrag();

			title = getActivity().getResources().getString(
					R.string.updatecontrolproperty);
			isShowMenu = false;

			updatepropertyLayout.setSelected(true);
			addcontrolLayout.setSelected(false);
			updatekeyLayout.setSelected(false);
			delcontrolLayout.setSelected(false);
			break;
		case R.id.delcontrolLayout:

			newContent = null;

			// if (control != null) {
			BaseDroidApp.getInstanse().showConfirmDialog("确认删除该遥控器么？", "确认",
					"取消", onClickListener);
			// }

			delcontrolLayout.setSelected(true);

			addcontrolLayout.setSelected(false);
			updatekeyLayout.setSelected(false);
			updatepropertyLayout.setSelected(false);
			break;
		}

		if (newContent != null) {
			switchFragment(newContent, title, isShowMenu);
		}

	}

	OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			switch (Integer.valueOf(String.valueOf(view.getTag()))) {
			case CustomDialog.TAG_CANCLE:
				BaseDroidApp.getInstanse().closeMessageDialog();

				break;
			case CustomDialog.TAG_SURE:
				BaseDroidApp.getInstanse().closeMessageDialog();
				BaseDroidApp.dbOperator.deleteControl(control.id);

				// 前四个保留
				if (control.id != 1 && control.id != 2 && control.id != 3
						&& control.id != 4) {
					BaseDroidApp.dbOperator.deleteControlItemByControlId(control.id);
				}
				

				newContent = new ViewPagerContainerFrag(0);
				
				Control nowControl=BaseDroidApp.dbOperator.getDefaultControl(1);
				
				if (nowControl == null) {
					nowControl = BaseDroidApp.dbOperator.getSelectControl().get(0);
				}
				
				ViewPagerContainerFrag.control=nowControl;
				
				title=nowControl.name;
				switchFragment(newContent, title, isShowMenu);
				
				break;
			}

		}
	};

	/**
	 * 切换fragment
	 * 
	 * @param fragment
	 */
	private void switchFragment(Fragment fragment, String title, boolean isShow) {
		if (getActivity() == null)
			return;

		home.switchContent(fragment, true, null, null);
		home.setSwitchTitle(title);

		if (isShow) {
			home.sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
			home.mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_FULLSCREEN);
		} else {
			home.sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
			home.mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_NONE);
		}

	}
}
