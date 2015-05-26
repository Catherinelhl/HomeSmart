package com.home.view;

import com.home.homesmart.R;
import com.home.utils.CommonTools;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 头部视图
 * 
 * @author hyc
 * 
 */
public class CommonTitleView extends LinearLayout implements
		View.OnClickListener {

	private Context mContext;
	private View view;
	public static CommonTitleView instance;

	public CommonTitleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		view = CommonTools.getView(context, R.layout.main_header, this);
		mContext = context;
		instance = this;
		init();

		initData();
	}

	ImageView main_head_back, main_head_left;
	TextView main_head_back_text, main_head_title_text, main_head_right_text;
	ImageView main_head_pre_right, main_head_right, main_head_lastbt;

	private void init() {
		main_head_back = (ImageView) view.findViewById(R.id.main_head_back);
		main_head_left = (ImageView) view.findViewById(R.id.main_head_left);

		main_head_back_text = (TextView) view
				.findViewById(R.id.main_head_back_text);
		main_head_title_text = (TextView) view
				.findViewById(R.id.main_head_title_text);
		main_head_right_text = (TextView) view
				.findViewById(R.id.main_head_right_text);

		main_head_pre_right = (ImageView) view
				.findViewById(R.id.main_head_pre_right);
		main_head_lastbt = (ImageView) view.findViewById(R.id.main_head_lastbt);
		main_head_right = (ImageView) view.findViewById(R.id.main_head_right);

		main_head_pre_right.setOnClickListener(this);
		main_head_back.setOnClickListener(this);
		main_head_left.setOnClickListener(this);
		main_head_back_text.setOnClickListener(this);
		main_head_lastbt.setOnClickListener(this);
		main_head_right.setOnClickListener(this);
	}

	/**
	 * 设置左侧图片
	 * 
	 * @param id
	 */
	public void setleftImg(int id, String tag) {
		main_head_left.setImageResource(id);
		main_head_left.setTag(tag);
		main_head_left.setVisibility(View.VISIBLE);
	}

	/**
	 * 设置左侧图片隐藏
	 */
	public void setLeftImgHide() {
		main_head_left.setVisibility(View.GONE);
	}

	/**
	 * 设置左侧文字
	 * 
	 * @param text
	 */
	public void setLeftText(String text) {
		main_head_back_text.setText(text);
		main_head_back_text.setVisibility(View.VISIBLE);
	}

	/**
	 * 设置左侧文字隐藏
	 */
	public void setLeftTextHide() {
		main_head_back_text.setVisibility(View.GONE);
	}

	/**
	 * 设置最后右侧图片
	 * 
	 * @param id
	 */
	public void setLastRightImg(int id, String tag) {
		main_head_lastbt.setImageResource(id);
		main_head_lastbt.setTag(tag);
		main_head_lastbt.setVisibility(View.VISIBLE);
	}

	/**
	 * 设置最后右侧图片隐藏
	 */
	public void setLastRightImgHide() {
		main_head_lastbt.setVisibility(View.GONE);
	}

	/**
	 * 设置右侧图片
	 * 
	 * @param id
	 */
	public void setPreRightImg(int id) {
		main_head_pre_right.setImageResource(id);
		main_head_pre_right.setVisibility(View.VISIBLE);
	}

	/**
	 * 设置右侧图片隐藏
	 */
	public void setPreRightImgHide() {
		main_head_pre_right.setVisibility(View.GONE);
	}

	/**
	 * 设置右侧图片
	 * 
	 * @param id
	 */
	public void setRightImg(int id) {
		main_head_right.setImageResource(id);
		main_head_right.setVisibility(View.VISIBLE);
	}

	/**
	 * 设置右侧图片隐藏
	 */
	public void setRightImgHide() {
		main_head_right.setVisibility(View.GONE);
	}

	/**
	 * 设置title的文字
	 * 
	 * @param text
	 */
	public void setHeadTitleText(String text) {
		main_head_title_text.setText(text);
		main_head_title_text.setVisibility(View.VISIBLE);
	}

	private void initData() {

	}

	public interface OnClickListener {
		public void onClick(View v);
	}

	public OnClickListener onClick;

	public void setOnClickListener(OnClickListener onclick) {
		this.onClick = onclick;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_head_back:
			if (onClick != null) {
				onClick.onClick(v);
			}

			break;
		case R.id.main_head_left:
			if (onClick != null) {
				onClick.onClick(v);
			}
			break;
		case R.id.main_head_back_text:
			if (onClick != null) {
				onClick.onClick(v);
			}
			break;
		case R.id.main_head_pre_right:
			if (onClick != null) {
				onClick.onClick(v);
			}
			break;
		case R.id.main_head_lastbt:
			if (onClick != null) {
				onClick.onClick(v);
			}
			break;
		case R.id.main_head_right:
			if (onClick != null) {
				onClick.onClick(v);
			}
			break;
		}

	}

}
