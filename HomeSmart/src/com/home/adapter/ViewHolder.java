package com.home.adapter;

import android.util.SparseArray;
import android.view.View;

/**
 * 封装ViewHolder
 * 
 * @author hyc
 * 
 */
public class ViewHolder {

	private ViewHolder() {

	}

	/**
	 * SparseArray内部采用二分法，android推荐使用
	 * 
	 * @param view
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends View> T get(View view, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = view.findViewById(id);
			viewHolder.put(id, childView);
		}

		return (T) childView;
	}
}
