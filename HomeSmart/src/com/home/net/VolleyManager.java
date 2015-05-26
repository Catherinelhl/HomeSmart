package com.home.net;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.DialogInterface;
import android.widget.BaseAdapter;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.home.application.BaseApp;
import com.home.constants.Constants;
import com.home.request.ICommonRequest;
import com.home.response.BaseResponse;
import com.home.response.ICommonResponse;
import com.home.utils.Base64;
import com.home.utils.Logger;

/**
 * 数据加载volley管理
 * 
 * @author hyc
 * 
 */
public class VolleyManager {

	private static VolleyManager manager;
	// 请求队列
	RequestQueue mQueue = null;

	private VolleyManager() {
		mQueue = Volley.newRequestQueue(BaseApp.getInstance()
				.getApplicationContext());
	}

	public static VolleyManager getInstance() {
		if (manager == null) {
			manager = new VolleyManager();
		}
		return manager;
	}

	public void request(final ICommonRequest request,
			final ICommonResponse response) {

		String requestUrl = String.valueOf(request.getNetTag());
		Logger.v("requestUrl:" + requestUrl);

		int requestMethod = request.getDataType();
		Logger.v("requestMethod:" + requestMethod);

		final JSONObject jsonObject = request.getJsonString();
		Logger.v("request jsonObject:" + jsonObject);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", jsonObject);

		// get方式提交
		// JsonObjectRequest jsonObjectrequest = new JsonObjectRequest(
		// request.getDataType(), requestUrl, jsonObject,
		// new Listener<JSONObject>() {
		//
		// @Override
		// public void onResponse(JSONObject json) {
		// Logger.log(json.toString());
		// BaseResponse baseResponse = (BaseResponse) BaseResponse
		// .parse(json.toString(), BaseResponse.class);
		//
		// Logger.log("CODE:" + baseResponse.CODE);
		// try {
		// if (baseResponse.CODE == 0) {
		// if ("RAW".equals(baseResponse.MODE)) {
		// JSONObject result = json
		// .getJSONObject("RESULT");
		//
		// Object responseClass = BaseResponse.parse(
		// result.toString(),
		// request.getResponesclass());
		// response.updateUi(responseClass);
		//
		// } else if ("B64".equals(baseResponse.MODE)) {
		// String result = (String) json.get("RESULT");
		// byte[] b = Base64.decode(result);
		// String s = new String(b, "utf-8");
		// Object responseClass = BaseResponse.parse(
		// s.toString(),
		// request.getResponesclass());
		// response.updateUi(responseClass);
		//
		// }
		// } else {
		// response.handleErrorData("服务器返回错误");
		// }
		// } catch (Exception e) {
		// closeDialog(request, response);
		// } finally {
		// closeDialog(request, response);
		// }
		//
		// }
		// }, new ErrorListener() {
		//
		// @Override
		// public void onErrorResponse(VolleyError error) {
		// response.handleErrorData("error:" + error.toString());
		// closeDialog(request, response);
		// }
		// }
		//
		// ) {
		// @Override
		// public byte[] getBody() {
		// // TODO Auto-generated method stub
		// return super.getBody();
		// }
		//
		// };

		// post方式提交
		StringRequest requestStr = new StringRequest(request.getDataType(),
				requestUrl, new Response.Listener<String>() {
					@Override
					public void onResponse(String responseStr) {

						try {
							Logger.v(responseStr.toString());
							JSONObject json = new JSONObject(responseStr);
							BaseResponse baseResponse = (BaseResponse) BaseResponse
									.parse(json.toString(), BaseResponse.class);

							Logger.v("CODE:" + baseResponse.CODE);
							if (baseResponse.CODE == 0) {
								if ("RAW".equals(baseResponse.MODE)) {
									JSONObject result = json
											.getJSONObject("RESULT");

									Object responseClass = BaseResponse.parse(
											result.toString(),
											request.getResponesclass());
									response.updateUi(responseClass);

								} else if ("B64".equals(baseResponse.MODE)) {
									String result = (String) json.get("RESULT");
									byte[] b = Base64.decode(result);
									String decryptstr = new String(b, "utf-8");
									Logger.log("decryptstr:" + decryptstr);
									Object responseClass = BaseResponse.parse(
											decryptstr.toString(),
											request.getResponesclass());
									response.updateUi(responseClass);

								}
							} else {
								Logger.v("MSG cn:" + baseResponse.MSG.cn);
								response.handleErrorData(baseResponse.MSG.cn);
								// response.handleErrorData("服务器返回错误");
							}
						} catch (Exception e) {
							if(e instanceof IllegalStateException){
								try {
									JSONArray json = new JSONArray(responseStr);
								} catch (JSONException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							
							Logger.e("Exception:" + e.toString());
							closeDialog(request, response);
						} finally {
							closeDialog(request, response);
						}

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						response.handleErrorData("error:" + error.toString());
						closeDialog(request, response);
					}

				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				map.put("data", jsonObject.toString());
				return map;
			}

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("Content-Type", "application/x-www-form-urlencoded");
				return params;
			}

		};

		requestStr.setShouldCache(false);

		requestStr.setTag(request.getJsonString());
		// mQueue.add(jsonObjectrequest);
		mQueue.add(requestStr);
		mQueue.start();
		showDialog(request, response);
	}

	public void showDialog(ICommonRequest request, ICommonResponse response) {
		if (request.isShowProgress() && null != request.getShowDialog()) {
			request.getShowDialog().show();
			request.getShowDialog().setOnCancelListener(
					new DialogCancle(request, response));
		}
	}

	public void closeDialog(ICommonRequest request, ICommonResponse response) {
		if (null != request.getShowDialog()
				&& request.getShowDialog().isShowing()) {
			request.getShowDialog().dismiss();
			request.getShowDialog().setOnCancelListener(
					new DialogCancle(request, response));
		}
	}

	public class DialogCancle implements DialogInterface.OnCancelListener {

		public ICommonRequest request;
		public ICommonResponse response;

		public DialogCancle(ICommonRequest request,
				final ICommonResponse response) {
			this.request = request;
			this.response = response;
		}

		@Override
		public void onCancel(DialogInterface arg0) {
			mQueue.cancelAll(request.getJsonString());
			response.handleErrorData("放弃加载");
		}

	}
}
