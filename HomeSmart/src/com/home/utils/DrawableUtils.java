package com.home.utils;

import java.io.IOException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

/**
 * Drawable工具类
 * 
 * @author hyc
 * 
 */
public class DrawableUtils {
	/**
	 * 由URL返回Drawable
	 * 
	 * @param imageUrl
	 * @return
	 */
	public static Drawable loadImageFromNetwork(String imageUrl) {
		Drawable drawable = null;
		try {
			// 可以在这里通过文件名来判断，是否本地有此图片
			drawable = Drawable.createFromStream(
					new URL(imageUrl).openStream(), "image.jpg");
		} catch (IOException e) {
			Logger.d(e.getMessage());
		}
		if (drawable == null) {
			Logger.d("null drawable");
		} else {
			Logger.d("not null drawable");
		}
		return drawable;
	}
	
	/**
	 * Drawable转Bitmap
	 * @param drawable
	 * @return
	 */
	public static Bitmap convertDrawable2BitmapByCanvas(Drawable drawable) {
		Bitmap bitmap = Bitmap
				.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
								: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		// canvas.setBitmap(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * 代码中设置一般selector
	 * 
	 * @param context
	 * @param idNormal
	 * @param idSelected
	 * @param idFocused
	 * @param idUnable
	 * @return
	 */
	public static StateListDrawable newDrawableSelector(Context context,
			Drawable idNormal, Drawable idSelected, Drawable idFocused,
			Drawable idUnable) {

		StateListDrawable bg = new StateListDrawable();
		// View.PRESSED_ENABLED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_selected,
				android.R.attr.state_enabled }, idSelected);
		// View.ENABLED_FOCUSED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_enabled,
				android.R.attr.state_focused }, idFocused);
		// View.ENABLED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_enabled }, idNormal);
		// View.FOCUSED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_focused }, idFocused);
		// View.WINDOW_FOCUSED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_window_focused }, idUnable);
		// View.EMPTY_STATE_SET
		bg.addState(new int[] {}, idNormal);

		return bg;
	}

	/**
	 * 代码中设置一般selector
	 * 
	 * @param context
	 * @param idNormal
	 * @param idSelected
	 * @param idFocused
	 * @param idUnable
	 * @return
	 */
	public static StateListDrawable newSelector(Context context, int idNormal,
			int idSelected, int idFocused, int idUnable) {

		StateListDrawable bg = new StateListDrawable();
		// View.PRESSED_ENABLED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_selected,
				android.R.attr.state_enabled }, context.getResources()
				.getDrawable(idSelected));
		// View.ENABLED_FOCUSED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_enabled,
				android.R.attr.state_focused }, context.getResources()
				.getDrawable(idFocused));
		// View.ENABLED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_enabled }, context
				.getResources().getDrawable(idNormal));
		// View.FOCUSED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_focused }, context
				.getResources().getDrawable(idFocused));
		// View.WINDOW_FOCUSED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_window_focused }, context
				.getResources().getDrawable(idUnable));
		// View.EMPTY_STATE_SET
		bg.addState(new int[] {}, context.getResources().getDrawable(idNormal));

		return bg;
	}

	/**
	 * 代码中设置按钮selector
	 * 
	 * @param context
	 * @param idNormal
	 * @param idPressed
	 * @param idFocused
	 * @param idUnable
	 * @return
	 */
	public static StateListDrawable newBtnSelector(Context context,
			Drawable idNormal, Drawable idPressed, Drawable idFocused,
			Drawable idUnable) {

		StateListDrawable bg = new StateListDrawable();
		// View.PRESSED_ENABLED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_pressed,
				android.R.attr.state_enabled }, idPressed);
		// View.ENABLED_FOCUSED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_enabled,
				android.R.attr.state_focused }, idFocused);
		// View.ENABLED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_enabled }, idNormal);
		// View.FOCUSED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_focused }, idFocused);
		// View.WINDOW_FOCUSED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_window_focused }, idUnable);
		// View.EMPTY_STATE_SET
		bg.addState(new int[] {}, idNormal);

		return bg;
	}

}
