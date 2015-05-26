package com.home.homesmart.fragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import cn.jpush.android.api.JPushInterface;

import com.home.application.BaseApp;
import com.home.application.BaseDroidApp;
import com.home.constants.Constants;
import com.home.homesmart.R;
import com.home.homesmart.activity.HomeActivity;
import com.home.homesmart.activity.LockSetupActivity;
import com.home.net.MyBaseAsyncTask;
import com.home.net.MyBaseAsyncTask.BaseAsyncListener;
import com.home.utils.Logger;
import com.home.utils.PrefrenceUtils;
import com.home.widget.SwitchView;
import com.home.widget.SwitchView.OnChangedListener;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * 设置
 * 
 * @author hyc
 * 
 */
public class SettingFrag extends OuterFragment implements OnClickListener {

	Button btn_backup, btn_recover, btn_update, btn_exit;
	SwitchView slipswitch1, slipswitch2, slipswitch3;

	HomeActivity home;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_setting, null);

		btn_backup = (Button) view.findViewById(R.id.btn_backup);
		btn_recover = (Button) view.findViewById(R.id.btn_recover);
		btn_update = (Button) view.findViewById(R.id.btn_update);
		btn_exit = (Button) view.findViewById(R.id.btn_exit);

		slipswitch1 = (SwitchView) view.findViewById(R.id.slipswitch1);
		slipswitch2 = (SwitchView) view.findViewById(R.id.slipswitch2);
		slipswitch3 = (SwitchView) view.findViewById(R.id.slipswitch3);

		btn_backup.setOnClickListener(this);
		btn_recover.setOnClickListener(this);
		btn_update.setOnClickListener(this);
		btn_exit.setOnClickListener(this);

		home = (HomeActivity) getActivity();

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Logger.log("<==============================onResume==========================>");

		slipswitch1.setChecked(PrefrenceUtils.getInstance(getActivity())
				.getSwitchState(Constants.VIBRATION));
		slipswitch1.setOnChangedListener(new OnChangedListener() {

			@Override
			public void OnChanged(SwitchView wiperSwitch, boolean checkState) {
				PrefrenceUtils.getInstance(getActivity()).setBoolean(
						Constants.VIBRATION, checkState);

			}
		});

		slipswitch2.setChecked(PrefrenceUtils.getInstance(getActivity())
				.getSwitchState(Constants.GESTUREPASS));
		slipswitch2.setOnChangedListener(new OnChangedListener() {

			@Override
			public void OnChanged(SwitchView wiperSwitch, boolean checkState) {
				// utils.setBoolean(Constants.GESTUREPASS, checkState);

				if (checkState) {
					Intent it = new Intent(getActivity(),
							LockSetupActivity.class);
					startActivity(it);
				} else {
					PrefrenceUtils.getInstance(getActivity()).setBoolean(
							Constants.GESTUREPASS, checkState);
				}

			}
		});

		slipswitch3.setChecked(PrefrenceUtils.getInstance(getActivity())
				.getSwitchState(Constants.PUSHNOTIFICATION));
		slipswitch3.setOnChangedListener(new OnChangedListener() {

			@Override
			public void OnChanged(SwitchView wiperSwitch, boolean checkState) {
				PrefrenceUtils.getInstance(getActivity()).setBoolean(
						Constants.PUSHNOTIFICATION, checkState);

				if (checkState) {
					JPushInterface.resumePush(BaseDroidApp.getInstanse());
				} else {
					JPushInterface.stopPush(BaseDroidApp.getInstanse());
				}

			}
		});

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_backup:// 备份
			backup();
			break;
		case R.id.btn_recover:// 恢复
			recover();
			break;
		case R.id.btn_update:// 更新

			break;
		case R.id.btn_exit:// 退出
			// 清空数据
			PrefrenceUtils.getInstance(home).setString("userId", "");
			PrefrenceUtils.getInstance(home).setString("userIcon", "");

			home.finish();
			System.exit(0);
			android.os.Process.killProcess(android.os.Process.myPid());
			break;

		}

	}

	private void backup() {
		new MyBaseAsyncTask(home).setHipMessage("备份中,请稍后......")
				.setBaseAsyncListener(new BaseAsyncListener() {

					@Override
					public void onTaskError(Exception e) {
						Logger.log(e.toString());
					}

					@Override
					public Object onTask(Object... arg0) throws Exception {
						if (!Environment.MEDIA_MOUNTED.equals(Environment
								.getExternalStorageState())) {
							return "未找到SD卡";
						}

						try {
							if (null != BaseDroidApp.dbOperator.sqlDb
									&& BaseDroidApp.dbOperator.sqlDb.isOpen()) {
								BaseDroidApp.dbOperator.sqlDb.close();
							}

							File dbFile = new File(
									Environment.getDataDirectory()
											+ "/data/com.home.homesmart/databases/home.db");
							File file = new File(Environment
									.getExternalStorageDirectory()
									+ "/homesmart/", "home.db");

							if (!file.getParentFile().exists()) {
								file.getParentFile().mkdirs();
							}
							// 创建文件，如果文件存在的话会自动把原来的覆盖掉
							file.createNewFile();

							copyFile(dbFile, file);

							return "备份成功";
						} catch (Exception e) {
							Logger.log(e.toString());
							return "备份失败";
						}

					}

					@Override
					public void onBeforeTask() {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAfter(Object result) {
						home.showToast(String.valueOf(result));
					}
				}).execute(new Object[] { null });
	}

	private void recover() {
		new MyBaseAsyncTask(home).setHipMessage("恢复中,请稍后......")
				.setBaseAsyncListener(new BaseAsyncListener() {

					@Override
					public void onTaskError(Exception e) {
						// TODO Auto-generated method stub

					}

					@Override
					public Object onTask(Object... arg0) throws Exception {
						if (!Environment.MEDIA_MOUNTED.equals(Environment
								.getExternalStorageState())) {
							return "未找到SD卡";
						}
						File dbBackupFile = new File(Environment
								.getExternalStorageDirectory() + "/homesmart/",
								"home.db");

						if (null != BaseDroidApp.dbOperator.sqlDb
								&& BaseDroidApp.dbOperator.sqlDb.isOpen()) {
							BaseDroidApp.dbOperator.sqlDb.close();
						}

						if (!dbBackupFile.exists()) {
							return "找不到备份的文件!";
						} else if (!dbBackupFile.canRead()) {
							return "已找到备份的文件，但无法读取!";
						}

						try {
							File dbFile = new File(
									Environment.getDataDirectory()
											+ "/data/com.home.homesmart/databases/home.db");
							if (dbFile.exists()) {
								dbFile.delete();
							}

							dbFile.createNewFile();
							// 把dbBackupFile文件里的内容写入dbFile文件(从头写到尾)
							copyFile(dbBackupFile, dbFile);
							return "成功的恢复数据!";
						} catch (IOException e) {
							return "恢复失败!";
						}

					}

					@Override
					public void onBeforeTask() {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAfter(Object result) {
						home.showToast(String.valueOf(result));
					}
				}).execute(new Void[] { null });
	}

	public static void copyFile(File src, File dist) throws Exception {
		FileChannel inChannel = null;
		FileChannel outChannel = null;
		try {
			inChannel = new FileInputStream(src).getChannel();
			outChannel = new FileOutputStream(dist).getChannel();

			inChannel.transferTo(0, inChannel.size(), outChannel);
		} finally {
			if (inChannel != null) {
				inChannel.close();
			}
			if (outChannel != null) {
				outChannel.close();
			}
		}

	}

}
