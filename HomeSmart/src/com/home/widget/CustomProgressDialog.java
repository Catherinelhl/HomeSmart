package com.home.widget;

import com.home.homesmart.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * 自定义Dialog
 * 
 * @author hyc
 * 
 */
public class CustomProgressDialog extends ProgressDialog {

	private Context mContext;

	CustomProgressDialog instance;

	public CustomProgressDialog(Context context) {
		super(context);
		mContext = context;
		instance = this;
	}

	public TextView chan_tv;

	public View view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customprogressdialog);

		chan_tv = (TextView) findViewById(R.id.chan_tv);

	}

	// public static CustomProgressDialog createDialog(){
	//
	// }
	//

	public void setText(String str) {

	}

	public CustomProgressDialog show(String str) {
		// CustomProgressDialog dialog = new CustomProgressDialog(context);
		view = LayoutInflater.from(mContext).inflate(
				R.layout.customprogressdialog, null);

		TextView chan_tv = (TextView) view.findViewById(R.id.chan_tv);

		if (chan_tv != null) {
			chan_tv.setText(str);
		}
		instance.show();
		//必须show之后调用
		instance.setContentView(view);
		return instance;
	}

}
