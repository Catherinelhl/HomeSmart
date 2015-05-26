package com.home.adapter;

import java.util.ArrayList;

import com.home.constants.Scene;
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

public class SceneItemAdapter extends BaseAdapter {

	private Context mContext;

	private ArrayList<Scene> list;

	private LayoutInflater inflater;

	public SceneItemAdapter(Context mContext, ArrayList<Scene> list) {
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

			if (position % 2 == 0) {
				convertView = inflater.inflate(R.layout.scene_left_item, null);
			} else {
				convertView = inflater.inflate(R.layout.scene_right_item, null);
			}

		}

		ImageView row_icon = ViewHolder.get(convertView, R.id.row_icon);

		TextView row_title = ViewHolder.get(convertView, R.id.row_title);

		ImageView row_iv = ViewHolder.get(convertView, R.id.row_iv);

		Scene scene = list.get(position);

		row_title.setText(scene.name);

		// row_icon.setImageBitmap(scene.image);

		//row_iv.setImageResource(R.drawable.home_addimg_bg);

		row_iv.setImageBitmap(scene.image);

		return convertView;
	}
}
