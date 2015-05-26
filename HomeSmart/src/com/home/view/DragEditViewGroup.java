package com.home.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.home.application.BaseApp;
import com.home.application.BaseDroidApp;
import com.home.constants.BottomItem;
import com.home.constants.Constants;
import com.home.constants.Control;
import com.home.constants.ControlItem;
import com.home.constants.LayoutValue;
import com.home.homesmart.R;
import com.home.homesmart.activity.HomeActivity;
import com.home.homesmart.fragment.UpdateControlItemNameFrag;
import com.home.utils.DrawableUtils;
import com.home.utils.Logger;
import com.home.utils.PrefrenceUtils;
import com.home.utils.StringTools;
import com.home.widget.CustomDialog;

import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

public class DragEditViewGroup extends ViewGroup implements
		View.OnTouchListener, View.OnLongClickListener, View.OnClickListener {

	private float mLastMotionY;// 最后点击的点
	private GestureDetector detector;
	int move = 0;// 移动距离
	int MAXMOVE = 850;// 最大允许的移动距离
	private Scroller mScroller;
	int up_excess_move = 0;// 往上多移的距离
	int down_excess_move = 0;// 往下多移的距离
	private final static int TOUCH_STATE_REST = 0;
	private final static int TOUCH_STATE_SCROLLING = 1;
	private int mTouchSlop;
	private int mTouchState = TOUCH_STATE_REST;
	Context mContext;

	int lastX, lastY, screenWidth, screenHeight;
	public static final String Tag = "DragGridView";

	protected boolean dragged = false, touching = false, isUpdateState = false;

	ArrayList<ControlItem> list;
	ArrayList<ImageView> imageList = new ArrayList<ImageView>();

	// Bitmap bitmap;

	public WindowManager.LayoutParams layoutParams;

	public void setUpdateState(boolean isUpdate) {
		isUpdateState = isUpdate;
	}

	Control control;

	public void setControl(Control control) {
		this.control = control;
	}

	public DragEditViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		// TODO Auto-generated constructor stub
		setBackgroundColor(Color.TRANSPARENT);
		// mScroller = new Scroller(context);
		// detector = new GestureDetector(this);

		screenWidth = LayoutValue.SCREEN_WIDTH;
		screenHeight = LayoutValue.SCREEN_HEIGHT;
		Logger.log("screen width =" + screenWidth + ",screen height="
				+ screenHeight);

		control = (Control) this.getTag();

		final ViewConfiguration configuration = ViewConfiguration.get(context);
		// 获得可以认为是滚动的距离
		mTouchSlop = configuration.getScaledTouchSlop();

		layoutParams = new WindowManager.LayoutParams();

		if (control == null) {
			// 查询出当前默认的遥控器按键
			Control control = BaseDroidApp.dbOperator.getDefaultControl(1);
			list = BaseDroidApp.dbOperator.getControlItemByControl(control.id);
		} else {
			list = BaseDroidApp.dbOperator.getControlItemByControl(control.id);
		}

		// 添加子View
		for (int i = 0; i < list.size(); i++) {
			ControlItem item = list.get(i);
			// ImageView mImageView = new ImageView(mContext);
			ImageButton mImageView = new ImageButton(mContext);
			if (item.srcimage != null && item.bgimage != null) {
				Drawable srcDrawable = new BitmapDrawable(
						mContext.getResources(), item.srcimage);
				Drawable bgDrawable = new BitmapDrawable(
						mContext.getResources(), item.bgimage);
				mImageView.setBackground(DrawableUtils.newBtnSelector(mContext,
						srcDrawable, bgDrawable, srcDrawable, srcDrawable));
			} else if (item.srcimage == null && item.bgimage != null) {
				Drawable bgDrawable = new BitmapDrawable(
						mContext.getResources(), item.bgimage);
				mImageView.setBackground(DrawableUtils.newBtnSelector(mContext,
						bgDrawable, bgDrawable, bgDrawable, bgDrawable));
			} else if (item.srcimage != null && item.bgimage == null) {
				Drawable srcDrawable = new BitmapDrawable(
						mContext.getResources(), item.srcimage);
				mImageView.setBackground(DrawableUtils.newBtnSelector(mContext,
						srcDrawable, srcDrawable, srcDrawable, srcDrawable));
			}

			mImageView.setId(item.id);
			item.isDraged = dragged;
			mImageView.setTag(item);

			mImageView.setOnTouchListener(this);
			mImageView.setOnLongClickListener(this);
			mImageView.setOnClickListener(this);

			imageList.add(mImageView);

			layoutParams.x = item.x; // 偏移量x
			layoutParams.y = item.y; // 偏移量y
			layoutParams.width = item.width;
			layoutParams.height = item.height;

			addView(mImageView, layoutParams);

			mImageView.layout(item.x, item.y, item.x + item.width, item.y
					+ item.height);

		}

	}

	public DragEditViewGroup(Context context, Control control, boolean isUpdate) {
		super(context);
		mContext = context;
		// TODO Auto-generated constructor stub
		setBackgroundColor(Color.TRANSPARENT);
		// mScroller = new Scroller(context);
		// detector = new GestureDetector(this);

		screenWidth = LayoutValue.SCREEN_WIDTH;
		screenHeight = LayoutValue.SCREEN_HEIGHT;
		Log.d(Tag, "screen width =" + screenWidth + ",screen height="
				+ screenHeight);

		isUpdateState = isUpdate;

		final ViewConfiguration configuration = ViewConfiguration.get(context);
		// 获得可以认为是滚动的距离
		mTouchSlop = configuration.getScaledTouchSlop();
		layoutParams = new WindowManager.LayoutParams();

		if (control == null) {
			// 查询出当前默认的遥控器按键
			control = BaseDroidApp.dbOperator.getDefaultControl(1);

			if (control == null) {
				control = BaseDroidApp.dbOperator.getSelectControl().get(0);
			}

			list = BaseDroidApp.dbOperator.getControlItemByControl(control.id);
		} else {
			list = BaseDroidApp.dbOperator.getControlItemByControl(control.id);
		}

		// 添加子View
		for (int i = 0; i < list.size(); i++) {
			ControlItem item = list.get(i);
			// ImageView mImageView = new ImageView(mContext);
			ImageButton mImageView = new ImageButton(mContext);
			if (item.srcimage != null && item.bgimage != null) {
				Drawable srcDrawable = new BitmapDrawable(
						mContext.getResources(), item.srcimage);
				Drawable bgDrawable = new BitmapDrawable(
						mContext.getResources(), item.bgimage);
				mImageView.setBackground(DrawableUtils.newBtnSelector(mContext,
						srcDrawable, bgDrawable, srcDrawable, srcDrawable));
			} else if (item.srcimage == null && item.bgimage != null) {
				Drawable bgDrawable = new BitmapDrawable(
						mContext.getResources(), item.bgimage);
				mImageView.setBackground(DrawableUtils.newBtnSelector(mContext,
						bgDrawable, bgDrawable, bgDrawable, bgDrawable));
			} else if (item.srcimage != null && item.bgimage == null) {
				Drawable srcDrawable = new BitmapDrawable(
						mContext.getResources(), item.srcimage);
				mImageView.setBackground(DrawableUtils.newBtnSelector(mContext,
						srcDrawable, srcDrawable, srcDrawable, srcDrawable));
			}

			mImageView.setId(item.id);
			item.isDraged = dragged;
			mImageView.setTag(item);

			mImageView.setOnTouchListener(this);
			mImageView.setOnLongClickListener(this);
			mImageView.setOnClickListener(this);

			imageList.add(mImageView);

			layoutParams.x = item.x; // 偏移量x
			layoutParams.y = item.y; // 偏移量y
			layoutParams.width = item.width;
			layoutParams.height = item.height;

			addView(mImageView, layoutParams);

			mImageView.layout(item.x, item.y, item.x + item.width, item.y
					+ item.height);

		}

		Bitmap updateBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.icon_resize);

		if (isUpdateState) {
			for (int i = 0; i < imageList.size(); i++) {
				ImageButton mImageView = new ImageButton(mContext);
				mImageView.setBackgroundResource(R.drawable.icon_resize);

				mImageView.setOnTouchListener(this);

				int x = imageList.get(i).getRight() - 30;
				int y = imageList.get(i).getBottom() - 30;

				layoutParams.x = x; // 偏移量x
				layoutParams.y = y; // 偏移量y
				
				
				layoutParams.width = updateBitmap.getWidth() / 3;
				layoutParams.height = updateBitmap.getHeight() / 3;

				addView(mImageView, layoutParams);

				mImageView.layout(x, y, x + updateBitmap.getWidth(), y
						+ updateBitmap.getHeight());

				ControlItem controlItem = (ControlItem) imageList.get(i)
						.getTag();
				controlItem.itemImageView = mImageView;

				imageList.get(i).setTag(controlItem);

				mImageView.setTag(imageList.get(i));

			}
		}

	}

	// @Override
	// public void computeScroll() {
	// if (mScroller.computeScrollOffset()) {
	// // 返回当前滚动X方向的偏移
	// scrollTo(0, mScroller.getCurrY());
	// postInvalidate();
	// }
	// }

	int Fling_move = 0;

	// public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
	// float velocityY) {
	// // 随手指 快速拨动的代码
	// Log.d("onFling", "onFling");
	// if (up_excess_move == 0 && down_excess_move == 0) {
	//
	// int slow = -(int) velocityY * 3 / 4;
	// mScroller.fling(0, move, 0, slow, 0, 0, 0, MAXMOVE);
	// move = mScroller.getFinalY();
	// computeScroll();
	// }
	// return false;
	// }

	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return true;
	}

	// public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
	// float distanceY) {
	// return false;
	// }

	public void onShowPress(MotionEvent e) {
		// // TODO Auto-generated method stub
	}

	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		// int childTop = 0;
		// int childLeft = 0;
		// final int count = getChildCount();
		// for (int i = 0; i < count; i++) {
		// final View child = getChildAt(i);
		// if (child.getVisibility() != View.GONE) {
		// child.setVisibility(View.VISIBLE);
		// child.measure(r - l, b - t);
		// child.layout(childLeft, childTop, childLeft + screenWidth / 4,
		// childTop + screenWidth / 4);
		// if (childLeft < screenWidth) {
		// childLeft += screenWidth / 4;
		// } else {
		// childLeft = 0;
		// childTop += screenWidth / 4;
		// }
		// }
		// }
	}

	@Override
	public boolean onLongClick(View view) {
		Log.d(Tag, "onLongClick");

		dragged = true;

		ControlItem item = (ControlItem) view.getTag();
		item.isDraged = true;
		view.setTag(item);

		vibrator();

		return false;

	}

	private void vibrator() {
		if (PrefrenceUtils.getInstance(mContext).getSwitchState(
				Constants.VIBRATION)) {
			Vibrator vib = (Vibrator) mContext
					.getSystemService(Service.VIBRATOR_SERVICE);
			vib.vibrate(50);
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {

		// lastX = (int) event.getRawX();
		// lastY = (int) event.getRawY();
		//
		// Logger.log("onInterceptTouchEvent" + "lastX=" + lastX + ", lastY="
		// + lastY);

		return super.onInterceptTouchEvent(event);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		// lastX = (int) event.getRawX();
		// lastY = (int) event.getRawY();
		//
		// Logger.log("dispatchTouchEvent" + "lastX=" + lastX + ", lastY="
		// + lastY);

		return super.dispatchTouchEvent(event);
	}

	int left, top, right, bottom;

	long downTime;
	long upTime;

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		Logger.log("onTouch:" + event.getAction());

		if (isUpdateState) {
			switch (event.getAction() & MotionEvent.ACTION_MASK) {

			case MotionEvent.ACTION_DOWN:
				lastX = (int) event.getRawX();
				lastY = (int) event.getRawY();
				touching = true;
				Logger.log("touching:" + isUpdateState + "===>down x=" + lastX
						+ ", y=" + lastY + "=========>view=" + v);
				downTime = System.currentTimeMillis();

				break;
			case MotionEvent.ACTION_MOVE:
				int dx = (int) event.getRawX() - lastX;
				int dy = (int) event.getRawY() - lastY;
				Logger.log("move dx=" + dx + ",  dy=" + dy);
				left = v.getLeft() + dx;
				top = v.getTop() + dy;
				right = v.getRight() + dx;
				bottom = v.getBottom() + dy;
				Logger.log("view  left=" + left + ", top=" + top + ", right="
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
				if (top < 0) {
					top = 0;
					bottom = top + v.getHeight();
				}
				if (bottom > screenHeight) {
					bottom = screenHeight;
					top = bottom - v.getHeight();
				}

				v.layout(left, top, right, bottom);

				if (v.getTag() instanceof ControlItem) {
					ControlItem controlItem = (ControlItem) v.getTag();
					int left = controlItem.itemImageView.getLeft() + dx;
					int top = controlItem.itemImageView.getTop() + dy;
					int right = controlItem.itemImageView.getRight() + dx;
					int bottom = controlItem.itemImageView.getBottom() + dy;

					controlItem.itemImageView.layout(left, top, right, bottom);
				}

				if (v.getTag() instanceof ImageView) {
					ImageView currentiv = (ImageView) v.getTag();
					left = currentiv.getLeft();
					top = currentiv.getTop();
					right = currentiv.getRight() + dx;
					bottom = currentiv.getBottom() + dy;
					//设置缩小的最小范围
					if (right - left < 30) {
						right = left + 30;
						v.layout(left, top, right, bottom);
					}
					if (bottom - top < 30) {
						bottom = top + 30;
						v.layout(left, top, right, bottom);
					}
					
					currentiv.layout(left, top, right, bottom);
				}

				lastX = (int) event.getRawX();
				lastY = (int) event.getRawY();

			case MotionEvent.ACTION_UP:
				// 保存位置
				// v.setTag(false);
				touching = false;
				// dragged = false;

				if (v.getTag() instanceof ControlItem) {
					// 保存位置
					ControlItem item = (ControlItem) v.getTag();
					// item.id = v.getId();
					item.x = left;
					item.y = top;
					// item.bgimage =
					// DrawableUtils.convertDrawable2BitmapByCanvas(v
					// .getBackground());
					item.width = v.getWidth();
					item.height = v.getHeight();

					upTime = System.currentTimeMillis();

					if (upTime - downTime > 1000) {
						Message msg = new Message();
						msg.obj = item;
						// 延时更新
						handler.sendMessageDelayed(msg, 50);
					}
				} else {
					ImageView itemiv = (ImageView) v.getTag();
					// 保存位置
					ControlItem item = (ControlItem) itemiv.getTag();
					item.x = left;
					item.y = top;
					item.width = itemiv.getWidth();
					item.height = itemiv.getHeight();

					upTime = System.currentTimeMillis();

//					if (upTime - downTime > 2000) {
//						// 作为长按
//						// 弹出编辑按键视图
//						BaseDroidApp.getInstanse().showRemoteBottomDialog(
//								bottomList, onItemClickListener, item);
//					}

					if (upTime - downTime > 1000) {
						Message msg = new Message();
						msg.obj = item;
						// 延时更新
						handler.sendMessageDelayed(msg, 50);
					}
				}

				break;
			}
			return super.onTouchEvent(event);
		} else {
			switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				lastX = (int) event.getRawX();
				lastY = (int) event.getRawY();
				touching = true;
				Logger.log("touching:" + isUpdateState + "===>down x=" + lastX
						+ ", y=" + lastY);
				downTime = System.currentTimeMillis();
				break;
			}
			return super.onTouchEvent(event);
		}
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			BaseDroidApp.dbOperator.updateControlItem((ControlItem) msg.obj);
			super.handleMessage(msg);
		}
	};

	static ArrayList<BottomItem> bottomList = new ArrayList<BottomItem>();

	private void addList() {
		BottomItem item = new BottomItem();
		item.name = "更改名称";
		item.imageId = R.drawable.home_rm_updatename;
		bottomList.add(item);
		item = new BottomItem();
		item.name = "更改图标";
		item.imageId = R.drawable.home_rm_updateimg;
		bottomList.add(item);
		item = new BottomItem();
		item.name = "删除按键";
		item.imageId = R.drawable.home_rm_delkey;
		bottomList.add(item);
		item = new BottomItem();
		item.name = "单键学习";
		item.imageId = R.drawable.home_single_study;
		bottomList.add(item);
		item = new BottomItem();
		item.name = "组合键学习";
		item.imageId = R.drawable.home_multi_study;
		bottomList.add(item);
		item = new BottomItem();
		item.name = "定时开启";
		item.imageId = R.drawable.home_rm_task;
		bottomList.add(item);
	}

	ControlItem item;

	@Override
	public void onClick(View view) {
		Logger.log("onClick======>执行了");

		item = (ControlItem) view.getTag();

		vibrator();

		if (bottomList == null || bottomList.size() == 0) {
			addList();
		}

		if (isUpdateState) {
			// 编辑按键视图
			BaseDroidApp.getInstanse().showRemoteBottomDialog(bottomList,onItemClickListener,
					item);

		} else {
			// 判断是否学习过

			if (StringTools.isNullOrEmpty(item.code)) {
				// 没学习过，弹框是否学习
				BaseDroidApp.getInstanse().showConfirmDialog("按键还未学习,是否开始学习？",
						"取消", "学习", onClickListener);
			} else {
				// 学习过，直接发送指令

			}

		}

	}

	OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			switch (Integer.valueOf(String.valueOf(view.getTag()))) {
			case CustomDialog.TAG_SURE:
				BaseDroidApp.getInstanse().closeMessageDialog();
				//
				
				break;
			case CustomDialog.TAG_CANCLE:
				// 等待学习
				Logger.log("等待学习");
				BaseDroidApp.getInstanse().closeMessageDialog();
				BaseDroidApp.getInstanse().showStudyDialog(
						"请对准设备按您要学习的红外遥控器按键进行学习", onClickListener);

				break;
			case CustomDialog.TAG_CANCEL_BUTTON:
				BaseDroidApp.getInstanse().closeMessageDialog();
				break;
			}

		}
	};

	Fragment mContent;

	OnItemSelectedListener listener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1,
				int position, long arg3) {
			Logger.log("OnItemSelect position:" + position + "<><><><><><>");

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}
	};

	OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// 更改名称
			HomeActivity home = (HomeActivity) mContext;
			Logger.log("OnItemClick position:" + position + "<><><><><><>");

		}
	};

}