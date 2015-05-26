package com.home.request;

import org.json.JSONObject;

import android.app.Dialog;

public interface ICommonRequest {

	public Object getNetTag();

	// -------获取获取数据的途径 如get post socket等
	public int getDataType();

	public JSONObject getJsonString();

	// ---- 是否显示进度条
	public boolean isShowProgress();

	public Dialog getShowDialog();

	public Class<?> getResponesclass();
	
	public void createRequestJson(Object o);

}
