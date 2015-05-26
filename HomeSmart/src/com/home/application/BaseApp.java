package com.home.application;

import java.util.HashMap;

import net.sqlcipher.database.SQLiteDatabase;

import com.home.constants.Constants;
import com.home.constants.LayoutValue;
import com.home.db.DbOperator;
import com.home.homesmart.R;
import com.home.utils.ImageLoader;
import com.home.utils.Logger;
import android.app.Application;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.util.DisplayMetrics;

public class BaseApp extends Application {

	private static BaseApp instance;
	
	public static DbOperator dbOperator;

	public static BaseApp getInstance() {

		return instance;

	}

	@Override
	public void onCreate() {
		
		super.onCreate();
		instance = this;

		ImageLoader.getInstance(instance, 0).init();

		//initSound();
		
		//屏幕宽高
		DisplayMetrics dm = getResources().getDisplayMetrics();
		LayoutValue.SCREEN_WIDTH=dm.widthPixels;
		LayoutValue.SCREEN_HEIGHT = dm.heightPixels;
		// 密度
		LayoutValue.SCREEN_DENSITY = dm.density;
		SQLiteDatabase.loadLibs(this);
		
		if (dbOperator == null) {
			dbOperator = new DbOperator(instance);
		}

		
	}

	private static HashMap<String, Integer> soundMap;
	private static SoundPool soundPool;

	private static boolean loaded = false;

	private void initSound() {

		soundMap = new HashMap<String, Integer>();
		soundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 0);
		soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {

			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId,
					int status) {
				loaded = true;
			}
		});

//		soundMap.put(Constants.BACK, soundPool.load(this, R.raw.back, 1));
//		soundMap.put(Constants.BACK_TO_TOP,
//				soundPool.load(this, R.raw.back_to_top, 1));
//		soundMap.put(Constants.COMFIRE, soundPool.load(this, R.raw.comfire, 1));
//		soundMap.put(Constants.MOVE_DOWN,
//				soundPool.load(this, R.raw.move_bottom, 1));
//		soundMap.put(Constants.MOVE_LEFT,
//				soundPool.load(this, R.raw.move_left, 1));
//		soundMap.put(Constants.MOVE_RIGHT,
//				soundPool.load(this, R.raw.move_right, 1));
//		soundMap.put(Constants.NET_CONNECTED,
//				soundPool.load(this, R.raw.net_connected, 1));
//		soundMap.put(Constants.NET_FOUND,
//				soundPool.load(this, R.raw.net_found, 1));
//		soundMap.put(Constants.TOP_FLOAT_DISABLED,
//				soundPool.load(this, R.raw.top_float_disabled, 1));
//		soundMap.put(Constants.TOP_FLOAT,
//				soundPool.load(this, R.raw.top_float, 1));
//		soundMap.put(Constants.PAGE_CHANGE,
//				soundPool.load(this, R.raw.page_change, 1));
//		soundMap.put(Constants.LOCK, soundPool.load(this, R.raw.lock, 1));
//		soundMap.put(Constants.EFFECT_TICK,
//				soundPool.load(this, R.raw.effect_tick, 1));
	}

	public static void playSound(String action) {
		AudioManager amgr = (AudioManager) instance
				.getSystemService(AUDIO_SERVICE);
		float volumeCurrent = amgr.getStreamVolume(AudioManager.STREAM_MUSIC);
		float volumeMax = amgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volumn = volumeCurrent / volumeMax;
		volumn = volumn / 2;
		Logger.log("volumn:" + volumn);
		if (loaded) {
			soundPool.play(soundMap.get(action), volumn, volumn, 0, 0, 1f);
		}

	}

}
