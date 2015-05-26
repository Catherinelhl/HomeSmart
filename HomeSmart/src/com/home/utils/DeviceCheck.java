package com.home.utils;

import java.io.IOException;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.home.constants.Constants;

/**
 * 设备验证
 * 
 * @author hyc
 * 
 */
public class DeviceCheck {

	/**
	 * androidId
	 * 
	 * @param context
	 * @return
	 */
	public static String getAndroidId() {
		String str = null;
		str = DeviceInfo.getOnlyId();
		return str;
	}

	public static String getWifiSSID(Context context) {
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		String SSID = wifiInfo.getSSID().replace("\"", "");
		return SSID;
	}

}
