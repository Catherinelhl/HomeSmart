package com.home.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * SharedPreferences 管理
 * 
 * @author hyc
 * 
 */
public class PrefrenceUtils {

	static SharedPreferences settings;

	static Context mContext;

	public static final String PREFS_DOWNLOAD_TARGET = "download_target";

	public static final String PREFS_DOWNLOAD_POSITION = "download_position";

	public static final String LIVE_VERSION = "live_version";

	public static final String CURRENT_POSITION = "current_position";

	public PrefrenceUtils(Context context) {

		mContext = context;
	}

	private static PrefrenceUtils instance;

	public static PrefrenceUtils getInstance(Context context) {
		mContext = context;
		settings = PreferenceManager.getDefaultSharedPreferences(context);
		if (instance == null) {
			instance = new PrefrenceUtils(context);
		}
		return instance;
	}

	public String getBaseIP() {
		return this.settings.getString("BASEIP", "");
	}

	public void setBaseIP(String baseIp) {
		this.settings.edit().putString("BASEIP", baseIp).commit();
	}

	public static String getIsSetStr(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				"settingGrade", context.MODE_PRIVATE);
		String isSet = preferences.getString("isSet", "false");
		return isSet;
	}

	public static String getLanguageId(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				"settingGrade", context.MODE_PRIVATE);
		String languageId = preferences.getString("languageId", "");
		return languageId;
	}

	public static String getMathId(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				"settingGrade", context.MODE_PRIVATE);
		String mathId = preferences.getString("mathId", "");
		return mathId;
	}

	public static String getEnglishId(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				"settingGrade", context.MODE_PRIVATE);
		String englishId = preferences.getString("englishId", "");
		return englishId;
	}

	public static String getServerAddress(Context context) {
		String address = context.getSharedPreferences("settingServer",
				context.MODE_PRIVATE).getString("SERVER",
				"http://apidev.itvyun.com/");
		return address;
	}

	public static String getInfos(Context context) {
		String infos = context.getSharedPreferences("aboutus",
				context.MODE_PRIVATE).getString("infos", "");
		return infos;
	}

	public static String getContract(Context context) {
		String contract = context.getSharedPreferences("aboutus",
				context.MODE_PRIVATE).getString("contract", "");
		return contract;
	}

	public void setLiveVersion(int version) {
		setInt(LIVE_VERSION, version);
	}

	public void setCurrentPosition(int position) {
		setInt(CURRENT_POSITION, position);
	}

	public static int getCurrentPosition() {
		return settings.getInt(CURRENT_POSITION, 1);
	}

	public int getLiveVersion() {
		return settings.getInt(LIVE_VERSION, 1);
	}

	public void setDownloadTarget(String targetFile) {
		setString(PREFS_DOWNLOAD_TARGET, targetFile);
	}

	public String getDownloadTarget() {
		return settings.getString(PREFS_DOWNLOAD_TARGET, "");
	}

	public void setDownLoadPos(long position) {
		setLong(PREFS_DOWNLOAD_POSITION, position);
	}

	public void setString(String key, String str) {
		SharedPreferences.Editor mEditor = settings.edit();
		mEditor.putString(key, str);
		mEditor.commit();
	}

	public void setLong(String key, long Long) {
		SharedPreferences.Editor mEditor = settings.edit();
		mEditor.putLong(key, Long);
		mEditor.commit();
	}

	public void setInt(String key, int num) {
		SharedPreferences.Editor mEditor = settings.edit();
		mEditor.putInt(key, num);
		mEditor.commit();
	}

	public String getPassValue(String key) {
		return settings.getString(key, "");
	}

	public Boolean getSwitchState(String key) {
		return settings.getBoolean(key, false);
	}

	public void setBoolean(String key, boolean state) {
		SharedPreferences.Editor mEditor = settings.edit();
		mEditor.putBoolean(key, state);
		mEditor.commit();
	}

	public long getDownloadPos() {
		return settings.getLong(PREFS_DOWNLOAD_POSITION, 0);
	}

}
