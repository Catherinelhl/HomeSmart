package com.home.response;

/**
 * 更新页面的回调接口
 * 
 * @author hyc
 * 
 */
public interface ICommonResponse {

	// ----返回数据处理后更新页面接口
	public void updateUi(Object o);

	public void handleErrorData(String info);

}

