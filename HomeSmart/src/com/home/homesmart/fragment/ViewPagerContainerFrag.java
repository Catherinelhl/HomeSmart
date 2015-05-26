package com.home.homesmart.fragment;

import java.util.ArrayList;

import com.home.application.BaseApp;
import com.home.application.BaseDroidApp;
import com.home.constants.Control;
import com.home.homesmart.R;
import com.home.homesmart.activity.HomeActivity;
import com.home.utils.Logger;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewPagerContainerFrag extends OuterFragment {

	public int index = 0;

	public static ViewPager vp;

	public static FragmentPagerAdapter fpa;

	public static ArrayList<Control> list;

	public HomeActivity home;

	public ViewPagerContainerFrag(int index) {
		super();
		this.index = index;
		Logger.log("index:" + index + "<><><><><><>");
		// if (vp != null) {
		// vp.setCurrentItem(index);
		// }
		// if (fpa != null) {
		// fpa.notifyDataSetChanged();
		// }
	}

	public static void notifyAgin() {
		list = BaseDroidApp.dbOperator.getSelectControl();
		fpa.notifyDataSetChanged();
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		home = (HomeActivity) getActivity();
	}

	public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

		private ArrayList<Fragment> mFragments;

		public MyFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
			mFragments = new ArrayList<Fragment>();
			// 加载4个
			// mFragments.add(new RemoteControlFrag());
			// mFragments.add(new SceneFrag());
			// mFragments.add(new SwitchFrag());
			// mFragments.add(new TimingTaskFrag());

			// 查询出遥控器的个数进行加载
			list = BaseDroidApp.dbOperator.getSelectControl();
			for (int i = 0; i < list.size(); i++) {
				Control control = list.get(i);
				RemoteControlFrag rcf = new RemoteControlFrag();
				Bundle bundle = new Bundle();
				bundle.putParcelable("parcelable", control);
				rcf.setArguments(bundle);
				mFragments.add(rcf);
			}
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			RemoteControlFrag rcf = (RemoteControlFrag) super.instantiateItem(
					container, position);
			Control control = list.get(position);
			// container.setId(control.id);
			Logger.log("instantiateItem:id" + control.id + "<><><><><><>");
			// rcf.setControl(control);
			return rcf;
		}

		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return PagerAdapter.POSITION_NONE;
		}

		@Override
		public Fragment getItem(int position) {
			// TODO Auto-generated method stub
			return mFragments.get(position);
		}

		@Override
		public int getCount() {
			return mFragments.size();
		}

	}

	static Control control;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		vp = new ViewPager(home);
		vp.setId(vp.hashCode());
		Logger.log("vp id:" + vp.getId());
		fpa = new MyFragmentPagerAdapter(getActivity()
				.getSupportFragmentManager());
		// vp.setOffscreenPageLimit(0);
		vp.setAdapter(fpa);

		control = list.get(0);
		vp.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				index = position;
				Logger.log("onPageSelected position:" + position
						+ "<><><><><><>");
				// vp.setId(position);
				control = list.get(position);
				Logger.log("onPageSelected===============>control name:"
						+ control.name);
				home.setSwitchTitle(control.name);

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

		vp.setCurrentItem(index);

		((SlidingFragmentActivity) getActivity()).getSlidingMenu()
				.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

		return vp;
	}
}
