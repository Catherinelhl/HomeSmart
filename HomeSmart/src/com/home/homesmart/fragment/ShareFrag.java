package com.home.homesmart.fragment;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.wechat.favorite.WechatFavorite;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import com.home.homesmart.R;
import com.home.homesmart.activity.HomeActivity;
import com.home.utils.Logger;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * 分享
 * 
 * @author hyc
 * 
 */
public class ShareFrag extends OuterFragment implements OnClickListener {

	LinearLayout secondLayout, thirdLayout, forthLayout, fiveLayout, sixLayout,
			sevenLayout;

	HomeActivity home;

	ShareParams sp = new ShareParams();

	public static final int TAG_SUCCESS = 1;
	public static final int TAG_FAILED = 2;
	public static final int TAG_CANCEL = 3;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.share_layout, null);

		home = (HomeActivity) getActivity();

		ShareSDK.initSDK(home);

		secondLayout = (LinearLayout) view.findViewById(R.id.secondLayout);
		thirdLayout = (LinearLayout) view.findViewById(R.id.thirdLayout);
		forthLayout = (LinearLayout) view.findViewById(R.id.forthLayout);
		fiveLayout = (LinearLayout) view.findViewById(R.id.fiveLayout);
		sixLayout = (LinearLayout) view.findViewById(R.id.sixLayout);
		sevenLayout = (LinearLayout) view.findViewById(R.id.sevenLayout);

		secondLayout.setOnClickListener(this);
		thirdLayout.setOnClickListener(this);
		forthLayout.setOnClickListener(this);
		fiveLayout.setOnClickListener(this);
		sixLayout.setOnClickListener(this);
		sevenLayout.setOnClickListener(this);

		initShareParams();

		return view;
	}

	@Override
	public void onClick(View v) {
		Logger.log("分享");
		switch (v.getId()) {
		case R.id.secondLayout:
			weChatFriShare();
			break;
		case R.id.thirdLayout:
			weChatMomentsShare();
			break;
		case R.id.forthLayout:
			sinaWeiboShare();
			break;
		case R.id.fiveLayout:
			qqShare();
			break;
		case R.id.sixLayout:
			qZoneShare();
			break;
		case R.id.sevenLayout:
			qqWeiboShare();
			break;

		}

	}

	public void initShareParams() {
		sp.setTitle("智能家居");
		sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
		sp.setText(home.getResources().getString(R.string.share_text));
		sp.setImageUrl("http://www.someserver.com/测试图片网络地址.jpg");
		sp.setImagePath("http://www.someserver.com/测试图片网络地址.jpg");
		// sp.setSite("发布分享的网站名称");
		// sp.setSiteUrl("发布分享网站的地址");
	}

	private void weChatFriShare() {
		Platform plat = ShareSDK.getPlatform(home, Wechat.NAME);
		Logger.log("plat:" + plat);
		if (plat != null) {
			plat.setPlatformActionListener(paListener); // 设置分享事件回调
			plat.share(sp);
		}

	}

	private void weChatMomentsShare() {
		Platform plat = ShareSDK.getPlatform(home, WechatMoments.NAME);
		Logger.log("plat:" + plat);
		if (plat != null) {
			plat.setPlatformActionListener(paListener); // 设置分享事件回调
		}

	}

	private void sinaWeiboShare() {
		Platform qzone = ShareSDK.getPlatform(home, SinaWeibo.NAME);
		qzone.setPlatformActionListener(paListener); // 设置分享事件回调
		qzone.share(sp);
	}

	private void qqShare() {
		Platform qzone = ShareSDK.getPlatform(home, QQ.NAME);
		qzone.setPlatformActionListener(paListener); // 设置分享事件回调
		qzone.share(sp);
	}

	private void qZoneShare() {
		Platform qzone = ShareSDK.getPlatform(home, QZone.NAME);
		qzone.setPlatformActionListener(paListener); // 设置分享事件回调
		qzone.share(sp);
	}

	private void qqWeiboShare() {
		Platform qzone = ShareSDK.getPlatform(home, TencentWeibo.NAME);
		qzone.setPlatformActionListener(paListener); // 设置分享事件回调
		qzone.share(sp);
	}

	PlatformActionListener paListener = new PlatformActionListener() {

		@Override
		public void onComplete(Platform arg0, int arg1,
				HashMap<String, Object> arg2) {
			Logger.log("分享成功");
			handler.sendEmptyMessage(TAG_SUCCESS);
		}

		@Override
		public void onError(Platform arg0, int action, Throwable t) {
			Logger.log("分享失败" + action + t.toString());
			handler.sendEmptyMessage(TAG_FAILED);
		}

		@Override
		public void onCancel(Platform plat, int action) {
			Logger.log("分享失败" + action);
			handler.sendEmptyMessage(TAG_CANCEL);
		}
	};

	Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case TAG_SUCCESS:
				home.showToast("分享成功");
				break;
			case TAG_FAILED:
				home.showToast("分享失败");
				break;
			case TAG_CANCEL:
				home.showToast("分享取消");
				break;
			}
		};
	};

	/**
	 * 一键分享
	 */
	private void showShare() {
		// ShareSDK.initSDK(home);
		// OnekeyShare oks = new OnekeyShare();
		// // 关闭sso授权
		// oks.disableSSOWhenAuthorize();
		//
		// // 分享时Notification的图标和文字
		// oks.setNotification(R.drawable.ic_launcher,
		// getString(R.string.app_name));
		// // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		// oks.setTitle(getString(R.string.share));
		// // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		// oks.setTitleUrl("http://sharesdk.cn");
		// // text是分享文本，所有平台都需要这个字段
		// oks.setText("我是分享文本");
		// // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		// oks.setImagePath("/sdcard/test.jpg");
		// // url仅在微信（包括好友和朋友圈）中使用
		// oks.setUrl("http://sharesdk.cn");
		// // comment是我对这条分享的评论，仅在人人网和QQ空间使用
		// oks.setComment("我是测试评论文本");
		// // site是分享此内容的网站名称，仅在QQ空间使用
		// oks.setSite(getString(R.string.app_name));
		// // siteUrl是分享此内容的网站地址，仅在QQ空间使用
		// oks.setSiteUrl("http://sharesdk.cn");
		//
		// // 启动分享GUI
		// oks.show(this);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ShareSDK.stopSDK(home);
	}

}
