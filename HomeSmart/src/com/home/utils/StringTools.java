package com.home.utils;

/**
 * String转换的工具
 * 
 * @author huangyuchao
 * 
 */
public class StringTools {

	/**
	 * 检测字符串是否为空或无内容
	 * 
	 * @param srcString
	 * @return
	 */
	public static boolean isNullOrEmpty(String srcString) {
		if (srcString != null && !srcString.equals("")) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * @param isostr
	 * @return 默认值变成utf-8类型
	 */
	public static String defaultToUtf(String isostr) {
		if (isostr != null) {
			try {
				isostr = new String(isostr.getBytes(), "UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return isostr;
	}
	
	/**
	 * 获取订单状态
	 * @param state
	 * @return
	 */
	public static String getStateStr(String state) {
		/*
		有true才是订单的最终状态，这种状态的订单才能被删除
		BUYER_REQUEST(1), 待接受
		SELLER_ACCEPT(2), 交易中
		BUYER_CANCEL(3, true), 买家取消订单
		SELLER_CANCEL(4, true), 卖家取消订单
		BUYER_PAY(5), 买家付款
		SELLER_FINISH_SERVICE(6), 卖家完成服务
		BUYER_FINISH_DEAL(7), 买家完成交易
		SELLER_CANCEL_AFTER_PAY(8, true), 卖家取消交易（付款后）
		BUY_CANCEL_AFTER_PAY(9), 买家取消交易（付款后）
		SELLER_ACCEPT_BUYER_CANCEL_AFTER_PAY(10, true); 卖家同意撤单
		 */
		String str = "";
		if ("BUYER_REQUEST".equals(state)) {
			str = "待接受";
		} else if ("SELLER_ACCEPT".equals(state)) {
			str = "卖家已接受";
		} else if ("BUYER_CANCEL".equals(state)) {
			str = "买家取消";
		} else if ("SELLER_CANCEL".equals(state)) {
			str = "卖家取消";
		} else if ("BUYER_PAY".equals(state)) {
			str = "已付款";
		} else if ("SELLER_FINISH_SERVICE".equals(state)) {
			str = "卖家完成服务";
		} else if ("BUYER_FINISH_DEAL".equals(state)) {
			str = "买家完成交易";
		}
		else if ("SELLER_CANCEL_AFTER_PAY".equals(state)) {
			str = "卖家取消交易";
		}
		else if ("BUY_CANCEL_AFTER_PAY".equals(state)) {
			str = "买家取消交易";
		}
		else if ("SELLER_ACCEPT_BUYER_CANCEL_AFTER_PAY".equals(state)) {
			str = "卖家同意撤单";
		}

		return str;
	}

}
