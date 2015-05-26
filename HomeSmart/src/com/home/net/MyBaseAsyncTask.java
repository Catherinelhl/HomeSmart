package com.home.net;

import java.lang.ref.SoftReference;

import com.home.homesmart.R;
import com.home.utils.Logger;
import com.home.widget.CustomProgressDialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

/**
 * AsyncTask简单封装
 * 
 * @author hyc
 * 
 */
public class MyBaseAsyncTask extends AsyncTask<Object, Object, Object> {

	private Context mContext;
	private CustomProgressDialog mDialog;

	// public ProgressDialog mDialog;
	private SoftReference<ProgressDialog> softReference;// ---将 dialog储存在缓存里面

	private boolean isComplete;// ---程序是否完成

	public MyBaseAsyncTask(Context context) {
		mContext = context;
		mDialog = new CustomProgressDialog(context);
		softReference = new SoftReference<ProgressDialog>(mDialog);

		// mDialog = new ProgressDialog(mContext);
		// softReference = new SoftReference<ProgressDialog>(mDialog);

	}

	public String hipMessage = "加载中,请稍后......";

	public MyBaseAsyncTask setHipMessage(String str) {
		hipMessage = str;
		return this;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		isComplete = false;
		// dialog.show(hipMessage);

		if ((!isComplete) && !this.isCancelled()) {
			mDialog = (CustomProgressDialog) softReference.get();
			mDialog.setCancelable(false);
			mDialog.show(hipMessage);
			// mDialog.getWindow().setContentView(R.layout.customprogressdialog);
			mDialog.setOnCancelListener(new DialogCancleLisen());
		}
		if (listener != null) {
			listener.onBeforeTask();
		}

		super.onPreExecute();
	}

	public class DialogCancleLisen implements DialogInterface.OnCancelListener {

		@Override
		public void onCancel(DialogInterface dialog) {
			if ((!isCancelled() && !isComplete)) {
				if (dialog != null) {
					dialog.cancel();
					dialog.dismiss();
					dialog = null;
				}
				cancel(true);
			} else {
				if (dialog != null) {
					dialog.cancel();
					dialog.dismiss();
					dialog = null;
				}
			}
		}

	}

	@Override
	protected void onProgressUpdate(Object... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

	@Override
	protected Object doInBackground(Object... arg0) {
		// TODO Auto-generated method stub
		Object o = null;
		if (listener != null) {
			try {
				o = listener.onTask(arg0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Logger.log(e.toString());
				listener.onTaskError(e);
			}
		}

		return o;
	}

	@Override
	protected void onPostExecute(Object result) {
		super.onPostExecute(result);
		try {
			isComplete = true;
			if (mDialog != null && mDialog.isShowing()) {
				mDialog.dismiss();
				mDialog.cancel();
			}

			if (listener != null) {
				listener.onAfter(result);
			}

		} catch (Exception e) {
			if (mDialog != null) {
				mDialog.dismiss();
				mDialog.cancel();
				mDialog = null;
			}
		}
	}

	private BaseAsyncListener listener;

	public MyBaseAsyncTask setBaseAsyncListener(BaseAsyncListener listener) {
		if (listener != null) {
			this.listener = listener;
		}
		return this;
	}

	public interface BaseAsyncListener {
		// 四个抽象方法
		abstract void onBeforeTask();

		abstract Object onTask(Object... arg0) throws Exception;

		abstract void onAfter(Object result);

		// abstract boolean hasException();
		abstract void onTaskError(Exception e);
	}

}
