package com.home.view;

import com.home.adapter.ControlAdapter;
import com.home.constants.Control;
import com.home.homesmart.activity.HomeActivity;
import com.home.homesmart.fragment.ViewPagerContainerFrag;
import com.home.utils.Logger;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

/**
 * 底部布局
 * 
 * @author hyc
 * 
 */
public class HomeBottomLayout extends LinearLayout {

	private Context mContext;

	public HomeBottomLayout(Context context) {
		super(context);
		mContext = context;
	}

	public HomeBottomLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}

	int controlId = 0;

	public void setAdapter(ControlAdapter adapter) {

		for (int i = 0; i < adapter.getCount(); i++) {
			final View view = adapter.getView(i, null, null);
			view.setPadding(10, 10, 10, 10);
			Control control = (Control) adapter.getItem(i);
			view.setTag(control);
			view.setId(i);

			view.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View view, MotionEvent event) {

					switch (event.getAction()) {
					case MotionEvent.ACTION_UP:
						Control control = (Control) view.getTag();
						Logger.log("controlId:" + control.id);
						// 进入到相应的遥控器界面
						HomeActivity home = (HomeActivity) mContext;
						ViewPagerContainerFrag.vp.setCurrentItem(view.getId());

						home.closeBottomMenu();
						break;
					}

					return false;
				}
			});

			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// controlId = Integer.parseInt(view.getTag().toString());

					Control control = (Control) view.getTag();
					Logger.log("controlId:" + control.id);
					// 进入到相应的遥控器界面
					HomeActivity home = (HomeActivity) mContext;
					ViewPagerContainerFrag.vp.setCurrentItem(view.getId());

					home.closeBottomMenu();
				}
			});

			LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			// params.setMargins(100, 0, 100, 0);
			this.addView(view, params);
		}
	}

}
