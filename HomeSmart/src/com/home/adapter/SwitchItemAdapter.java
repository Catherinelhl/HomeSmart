package com.home.adapter;

import java.util.ArrayList;

import com.home.constants.Switch;
import com.home.homesmart.R;
import com.home.widget.SwitchView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SwitchItemAdapter extends BaseAdapter {

	private Context mContext;

	private ArrayList<Switch> list;

	private LayoutInflater inflater;

	public SwitchItemAdapter(Context mContext, ArrayList<Switch> list) {
		this.mContext = mContext;
		this.list = list;
		inflater = LayoutInflater.from(mContext);

	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.switch_item, null);
		}

		ImageView row_icon = ViewHolder.get(convertView, R.id.row_icon);

		TextView row_title = ViewHolder.get(convertView, R.id.row_title);
		SwitchView switchView = ViewHolder.get(convertView, R.id.switchView);

		Switch switch1 = list.get(position);

		row_title.setText(switch1.name);

		row_icon.setImageBitmap(switch1.image);

		if (switch1.isOpen == 0) {
			switchView.setChecked(false);
		} else if (switch1.isOpen == 1) {
			switchView.setChecked(true);
		}

		return convertView;
	}

}
