package com.home.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.constants.Control;
import com.home.constants.Order;
import com.home.homesmart.R;

public class OrderAdapter extends BaseAdapter {

	Context mContext;

	private ArrayList<Order> list;

	private LayoutInflater inflater;

	public OrderAdapter(Context context, ArrayList<Order> list) {
		mContext = context;
		this.list = list;
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
			convertView = inflater.inflate(R.layout.order_item, null);
		}

		ImageView order_image = ViewHolder
				.get(convertView, R.id.order_image_iv);

		TextView order_name = ViewHolder.get(convertView, R.id.order_name_tv);

		TextView order_time = ViewHolder.get(convertView, R.id.order_time_tv);

		Order item = list.get(position);

		order_name.setText(item.name);

		// row_icon.setImageBitmap(scene.image);

		order_image.setImageBitmap(item.image);

		order_time.setText(item.time + "s");

		return convertView;
	}

}