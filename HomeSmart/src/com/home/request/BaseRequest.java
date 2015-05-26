package com.home.request;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

import com.google.gson.Gson;
import com.home.utils.Base64;
import com.home.utils.DeviceCheck;
import com.home.utils.DeviceInfo;
import com.home.utils.Logger;
import com.home.widget.CustomProgressDialog;

public class BaseRequest implements ICommonRequest {

	public static String API = "common";
	public static String VER = "1.3";
	public static String MODE = "RAW";

	public static final String DATA = "DATA";

	public static final int HTTP_GET = 0;
	// 默认用的post
	public static final int HTTP_POST = 1;

	// ------默认显示进度条
	public boolean isShowPro = true;
	// public ProgressDialog dialog;

	public CustomProgressDialog customProgressDialog;

	public Class<?> getResponesclass;

	@Override
	public Object getNetTag() {
		return null;
	}

	@Override
	public int getDataType() {
		return HTTP_POST;
	}

	JSONObject uploadjson;

	@Override
	public void createRequestJson(Object o) {
		// JSONObject json = new JSONObject();
		// json.put("username", username);

		try {
			Gson gson = new Gson();
			String jsonStr = gson.toJson(o);
			JSONObject jsonObj = new JSONObject(jsonStr);
			connectString(jsonObj);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 添加统一的验证
	 */
	public HashMap addHeader(HashMap hashmap) {
		// hashmap.put("deviceUsersUniqueId",DeviceCheck.shareDeviceUserUniqueId());
		// hashmap.put("androidId", DeviceCheck.getAndroidId());
		// hashmap.put("androidVersion", DeviceInfo.getAndroidVersion());
		// hashmap.put("deviceTypeName", DeviceInfo.getDeviceName());
		return hashmap;
	}

	public JSONObject connectString(JSONObject jsonObj) {
		JSONObject finalJson = new JSONObject();
		JSONObject json = new JSONObject();

		Logger.v("API:" + API);
		Logger.v("VER:" + VER);
		Logger.v("MODE:" + MODE);
		try {
			json.put("API", API);
			json.put("VER", VER);
			json.put("MODE", MODE);

			if ("RAW".equals(MODE)) {

				json.put(DATA, jsonObj);
			} else if ("B64".equals(MODE)) {
				String base64Str = Base64.encode(jsonObj.toString().getBytes());
				Logger.v(base64Str);
				json.put(DATA, base64Str);
			}

			// finalJson.put("data", json);

			// json.put(DATA, jsonStr);
		} catch (JSONException e) {
			Logger.v("getJsonString error" + e.getLocalizedMessage());
		}

		Logger.v(finalJson.toString());
		uploadjson = json;
		// uploadjson = finalJson;
		return uploadjson;
	}

	@Override
	public JSONObject getJsonString() {
		return uploadjson;
	}

	@Override
	public boolean isShowProgress() {
		return isShowPro;
	}

	public void setDialog(Context context) {
		if (customProgressDialog == null) {
			// dialog = new ProgressDialog(context);
			customProgressDialog = new CustomProgressDialog(context);
			customProgressDialog.setMessage("数据加载中");
			customProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			customProgressDialog.setCancelable(true);
		}
	}

	@Override
	public Dialog getShowDialog() {
		return customProgressDialog;
	}

	@Override
	public Class<?> getResponesclass() {
		// TODO Auto-generated method stub
		return getResponesclass;
	}

}
