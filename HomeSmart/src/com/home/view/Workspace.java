package com.home.view;

import com.home.constants.Control;
import com.home.constants.LayoutValue;
import com.home.constants.Order;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class Workspace extends ViewGroup {

	// public boolean isUpdateState = false;

	Control control;
	boolean isUpdateState;
	Context mContext;

	public Workspace(Context context, boolean isUpdateState, Control control) {
		super(context);
		// TODO Auto-generated constructor stub
		// addView(new MyViewGroup(context));
		this.control = control;
		this.isUpdateState = isUpdateState;
		this.mContext = context;
	}

	Order order;

	public void setOrder(Order order) {
		this.order = order;
	}

	public void initViews() {
		DragEditViewGroup view = new DragEditViewGroup(mContext, control,
				isUpdateState);
		view.setUpdateState(isUpdateState);
		// view.setControl(control);

		addView(view);
	}

	public void initView() {
		DragSelectViewGroup view = new DragSelectViewGroup(mContext, control);
		view.setUpdateState(isUpdateState);
		// view.setControl(control);
		view.setOrder(order);
		addView(view);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);
			child.measure(r - l, b - t);
			child.layout(0, 0, LayoutValue.SCREEN_WIDTH,
					LayoutValue.SCREEN_HEIGHT);
		}
	}

}
