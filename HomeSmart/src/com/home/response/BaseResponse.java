package com.home.response;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.home.utils.Logger;

public class BaseResponse {

	public String VER;
	public String API;
	public String MODE;

	public int CODE;
	public String RESULT;

	public MSG MSG;

	public class MSG {
		public String cn;
		public String en;
	}

	public void paseRespone(JSONObject object) {

	}

	/**
	 * 
	 * @param str
	 * @param c
	 * @return
	 */
	public static Object parse(String str, Class<?> c) {

		Gson gson = new Gson();

		Object o = gson.fromJson(str, c);

		Logger.v("class--1---" + c.toString());
		Logger.v("class--1---" + o.getClass().toString());

		return o;
	}

	// to define a method to get the data
	public void paseRespone(int position) {

	}

}
