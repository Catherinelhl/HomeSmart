package com.home.homesmart.activity;

import java.util.HashMap;

import com.home.homesmart.R;
import com.home.utils.Logger;
import com.home.utils.PrefrenceUtils;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.utils.UIHandler;
import cn.sharesdk.renren.Renren;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements Callback,
		PlatformActionListener {

	private static final int MSG_USERID_FOUND = 1;
	private static final int MSG_LOGIN = 2;
	private static final int MSG_AUTH_CANCEL = 3;
	private static final int MSG_AUTH_ERROR = 4;
	private static final int MSG_AUTH_COMPLETE = 5;

	private Button tvWeibo, tvQq;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ShareSDK.initSDK(this);

		setContentView(R.layout.third_party_login_page);

		tvWeibo = (Button) findViewById(R.id.tvWeibo);
		tvQq = (Button) findViewById(R.id.tvQq);

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		ShareSDK.stopSDK(this);
	}

	public void sinaLogin(View view) {
		authorize(new SinaWeibo(this));
	}

	public void qqLogin(View view) {
		authorize(new QQ(this));
	}

	public void tencentWeiboLogin(View view) {
		authorize(new TencentWeibo(this));
	}

	public void qZoneLogin(View view) {
		authorize(new QZone(this));
	}

	public void renrenLogin(View view) {
		authorize(new Renren(this));
	}

	String userId, userIcon;

	public void authorize(Platform plat) {
		if (plat.isValid()) {
			userId = plat.getDb().getUserId();
			Logger.log("userId:" + userId);
			userIcon = plat.getDb().getUserIcon();
			Logger.log("userIcon:" + userIcon);
			
			if (!TextUtils.isEmpty(userId)) {
				// 存本地
				PrefrenceUtils.getInstance(this).setString("userId", userId);
				PrefrenceUtils.getInstance(this)
						.setString("userIcon", userIcon);

				UIHandler.sendEmptyMessage(MSG_USERID_FOUND, this);
				login(plat.getName(), userId, null);

				return;
			}
		}

		plat.setPlatformActionListener(this);
		// true不使用SSO授权，false使用SSO授权
		plat.SSOSetting(true);
		// 请求授权用户的资料
		plat.showUser(null);
	}

	private void login(String plat, String userId,
			HashMap<String, Object> userInfo) {
		Message msg = new Message();
		msg.what = MSG_LOGIN;
		msg.obj = plat;
		UIHandler.sendMessage(msg, this);
	}

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case MSG_USERID_FOUND: {
			Toast.makeText(this, R.string.userid_found, Toast.LENGTH_SHORT)
					.show();
			Intent it = new Intent(LoginActivity.this, HomeActivity.class);
			it.putExtra("userIcon", userIcon);
			setResult(RESULT_OK, it);
			finish();
		}
			break;
		case MSG_LOGIN: {
			String text = getString(R.string.logining, msg.obj);
			Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
		}
			break;

		case MSG_AUTH_CANCEL: {
			Toast.makeText(this, R.string.auth_cancel, Toast.LENGTH_SHORT)
					.show();
		}
			break;
		case MSG_AUTH_ERROR: {
			Toast.makeText(this, R.string.auth_error, Toast.LENGTH_SHORT)
					.show();
		}
			break;
		case MSG_AUTH_COMPLETE: {
			Toast.makeText(this, R.string.auth_complete, Toast.LENGTH_SHORT)
					.show();
			Intent it = new Intent(LoginActivity.this, HomeActivity.class);
			it.putExtra("userIcon", userIcon);
			setResult(RESULT_OK, it);
			finish();
		}
			break;
		}
		return false;

	}

	@Override
	public void onCancel(Platform platform, int action) {
		if (action == Platform.ACTION_USER_INFOR) {
			UIHandler.sendEmptyMessage(MSG_AUTH_CANCEL, this);
		}
	}

	@Override
	public void onComplete(Platform platform, int action,
			HashMap<String, Object> res) {
		if (action == Platform.ACTION_USER_INFOR) {
			UIHandler.sendEmptyMessage(MSG_AUTH_COMPLETE, this);
			userId = platform.getDb().getUserId();
			userIcon = platform.getDb().getUserIcon();

			// 存本地
			PrefrenceUtils.getInstance(this).setString("userId", userId);
			PrefrenceUtils.getInstance(this).setString("userIcon", userIcon);

			login(platform.getName(), platform.getDb().getUserId(), res);
		}
		Logger.log(res + "");
	}

	@Override
	public void onError(Platform platform, int action, Throwable t) {
		if (action == Platform.ACTION_USER_INFOR) {
			UIHandler.sendEmptyMessage(MSG_AUTH_ERROR, this);
		}
		t.printStackTrace();

	}
}
