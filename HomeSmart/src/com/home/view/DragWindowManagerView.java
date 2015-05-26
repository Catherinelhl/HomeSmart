package com.home.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.home.application.BaseApp;
import com.home.application.BaseDroidApp;
import com.home.constants.ControlItem;
import com.home.constants.LayoutValue;
import com.home.db.DbOperator;
import com.home.homesmart.R;
import com.home.utils.DrawableUtils;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class DragWindowManagerView extends ViewGroup implements
		View.OnTouchListener, View.OnClickListener, View.OnLongClickListener {

	public static final String Tag = "DragGridView";

	protected int colCount, childSize, padding, dpi, scroll = 0;

	int lastX, lastY, screenWidth, screenHeight;

	public WindowManager mWm;
	public WindowManager.LayoutParams layoutParams;

	private Context mContext;

	private int[] location;

	private boolean isDragged = false;

	public DragWindowManagerView(Context context) {
		super(context);

		setListeners();

		mContext = context;
		setBackgroundColor(Color.TRANSPARENT);

		DisplayMetrics metrics = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(metrics);
		dpi = metrics.densityDpi;

		screenWidth = LayoutValue.SCREEN_WIDTH;
		screenHeight = LayoutValue.SCREEN_HEIGHT;
		Log.d(Tag, "screen width =" + screenWidth + ",screen height="
				+ screenHeight);

		mWm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		layoutParams = new WindowManager.LayoutParams();

		location = new int[2];
		// location[0] = 50;
		// location[1] = 50;
		this.getLocationInWindow(location);
		Log.d(Tag, "x = " + location[0] + ",y = " + location[1]);
		// this.setBackgroundColor(Color.WHITE);

		addViewToScreen();
	}

	public DragWindowManagerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setListeners();

		mContext = context;

		DisplayMetrics metrics = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(metrics);
		dpi = metrics.densityDpi;

		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		Log.d(Tag, "screen width =" + screenWidth + ",screen height="
				+ screenHeight);

		mWm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		layoutParams = new WindowManager.LayoutParams();

		location = new int[2];
		this.getLocationInWindow(location);
		Log.d(Tag, "x = " + location[0] + ",y = " + location[1]);
	}

	protected void setListeners() {
		// setOnTouchListener(this);
		// setOnClickListener(this);
		// setOnLongClickListener(this);
	}

	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub

	}

	private View createNewView(Bitmap bitmap) {
		ImageView mImageView = new ImageView(mContext);
		mImageView.setImageBitmap(bitmap);
		// mImageView.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
		return mImageView;
	}

	ArrayList<ControlItem> list;

	// Bitmap bitmap;

	List<HashMap<ImageView, WindowManager.LayoutParams>> imageList = new ArrayList<HashMap<ImageView, WindowManager.LayoutParams>>();

	public void addViewToScreen() {
		// bitmap = BitmapFactory.decodeResource(mContext.getResources(),
		// R.drawable.ic_launcher);

		list = BaseDroidApp.dbOperator.getControlItem();
		// if (list == null || list.size() == 0) {
		// for (int k = 0; k < 10; k++) {
		// ControlItem item = new ControlItem();
		// item.x = 100 + k * 50;
		// item.y = 100 + k * 50;
		//
		// item.width = bitmap.getWidth();
		// item.height = bitmap.getHeight();
		// item.image = bitmap;
		// BaseApp.dbOperator.insertControlItem(item);
		// // list.add(item);
		// }
		// list = BaseApp.dbOperator.getControlItem();
		// }

		// 添加子View
		for (int i = 0; i < list.size(); i++) {
			ControlItem item = list.get(i);
			// ImageView mImageView = new ImageView(mContext);
			ImageButton mImageView = new ImageButton(mContext);
			if (item.srcimage != null) {
				mImageView.setImageBitmap(item.srcimage);
			}
			mImageView.setBackground(new BitmapDrawable(item.bgimage));
			mImageView.setId(item.id);
			mImageView.setTag(false);

			mImageView.setOnTouchListener(this);
			mImageView.setOnLongClickListener(this);
			mImageView.setOnClickListener(this);

			//addView(mImageView, layoutParams);

			mImageView.layout(item.x, item.y, item.x + item.width, item.y
					+ item.height);

			layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
			// layoutParams.x = location[0] + i; // 偏移量x
			// layoutParams.y = location[1] + i; // 偏移量y
			layoutParams.x = item.x; // 偏移量x
			layoutParams.y = item.y; // 偏移量y
			// layoutParams.token = getApplicationWindowToken();
			layoutParams.type = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
			layoutParams.width = item.width;
			layoutParams.height = item.height;
			layoutParams.alpha = 1.0f;
			layoutParams.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明
			layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
					| WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;

			// HashMap<ImageView, WindowManager.LayoutParams> map = new
			// HashMap<ImageView, WindowManager.LayoutParams>();
			// map.put(mImageView, layoutParams);
			// imageList.add(map);
			mWm.addView(mImageView, layoutParams);
		}

	}

	public boolean isShowImage = true;

	/**
	 * 隐藏所有的视图
	 */
	public void hideImage() {
		isShowImage = false;
		if (mWm != null && imageList != null && imageList.size() > 0) {
			for (int i = 0; i < imageList.size(); i++) {
				HashMap<ImageView, WindowManager.LayoutParams> map = imageList
						.get(i);
				Set<ImageView> set = map.keySet();
				for (ImageView obj : set) {
					layoutParams = map.get(obj);
					layoutParams.alpha = 0.0f;
					mWm.updateViewLayout(obj, layoutParams);
				}

			}
		}
	}

	/**
	 * 显示所有的视图
	 */
	public void showImage() {
		isShowImage = true;
		if (mWm != null && imageList != null && imageList.size() > 0) {
			for (int i = 0; i < imageList.size(); i++) {
				HashMap<ImageView, WindowManager.LayoutParams> map = imageList
						.get(i);
				Set<ImageView> set = map.keySet();
				for (ImageView obj : set) {
					layoutParams = map.get(obj);
					layoutParams.alpha = 1.0f;
					mWm.updateViewLayout(obj, layoutParams);
				}

			}
		}
	}

	@Override
	public void addView(View child) {
		// TODO Auto-generated method stub
		super.addView(child);
	}

	@Override
	public void removeViewAt(int index) {
		// TODO Auto-generated method stub
		super.removeViewAt(index);
	}

	@Override
	public boolean onLongClick(View view) {
		Log.d(Tag, "onLongClick");

		isDragged = true;

		view.setTag(isDragged);

		Vibrator vib = (Vibrator) mContext
				.getSystemService(Service.VIBRATOR_SERVICE);
		vib.vibrate(50);

		return false;
	}

	@Override
	public void onClick(View arg0) {
		Log.d(Tag, "onClick");

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		if (v.getTag() != null && true == (Boolean) v.getTag()) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				lastX = (int) event.getRawX();
				lastY = (int) event.getRawY();
				Log.d(Tag, "down x=" + lastX + ", y=" + lastY);
				break;
			case MotionEvent.ACTION_MOVE:
				int dx = (int) event.getRawX() - lastX;
				int dy = (int) event.getRawY() - lastY;
				Log.d(Tag, "move dx=" + dx + ",  dy=" + dy);
				int left = v.getLeft() + dx;
				int top = v.getTop() + dy;
				int right = v.getRight() + dx;
				int bottom = v.getBottom() + dy;
				Log.d(Tag, "view  left=" + left + ", top=" + top + ", right="
						+ right + ",bottom=" + bottom);
				// set bound
				if (left < 0) {
					left = 0;
					right = left + v.getWidth();
				}
				if (right > screenWidth) {
					right = screenWidth;
					left = right - v.getWidth();
				}
				if (top < 50) {
					top = 0;
					bottom = top + v.getHeight();
				}
				if (bottom > screenHeight) {
					bottom = screenHeight;
					top = bottom - v.getHeight();
				}
				v.layout(left, top, right, bottom);

				layoutParams.x = lastX;
				layoutParams.y = lastY - 30;
				mWm.updateViewLayout(v, layoutParams);

				lastX = (int) event.getRawX();
				lastY = (int) event.getRawY();

			case MotionEvent.ACTION_UP:
				// 保存位置
				ControlItem item = new ControlItem();
				item.id = v.getId();
				item.x = lastX;
				item.y = lastY;
				item.width = v.getWidth();
				item.height = v.getHeight();

				Message msg = new Message();
				msg.obj = item;
				// 延时更新
				handler.sendMessageDelayed(msg, 500);

				break;
			}
		}

		return false;
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			BaseDroidApp.dbOperator.updateControlItem((ControlItem) msg.obj);
			super.handleMessage(msg);
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d(Tag, "onKeyDown");
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			System.out.println("返回键执行了！！！");

		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		Log.d(Tag, "dispatchKeyEvent");
		return super.dispatchKeyEvent(event);
	}

}
