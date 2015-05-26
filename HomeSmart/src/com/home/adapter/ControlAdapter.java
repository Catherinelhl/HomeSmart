package com.home.adapter;

import java.util.ArrayList;

import com.home.constants.Control;
import com.home.constants.Scene;
import com.home.homesmart.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ControlAdapter extends BaseAdapter {

	Context mContext;

	private ArrayList<Control> list;

	private LayoutInflater inflater;

	public ControlAdapter(Context context, ArrayList<Control> control) {
		mContext = context;
		this.list = control;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.bottommenu_item, null);
		}

		ImageView control_iv = ViewHolder.get(convertView, R.id.control_iv);

		TextView control_tv = ViewHolder.get(convertView, R.id.control_tv);

		Control item = list.get(position);

		control_tv.setText(item.name);

		// row_icon.setImageBitmap(scene.image);

		control_iv.setImageBitmap(item.image);

		return convertView;
	}

}
