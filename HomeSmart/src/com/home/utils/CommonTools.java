package com.home.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.home.constants.LayoutValue;
import com.home.db.DBHelper;
import com.home.homesmart.R;
import com.home.widget.CustomDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;

public class CommonTools {

	public static String getDataBasePath(Context context) {

		String packageName = context.getPackageName();

		String baseDir = "/data/data/" + packageName + "/databases";

		String DB_PATH = baseDir + File.separator + DBHelper.DBName;

		// if ((new File(DB_PATH).exists() == false)) {
		try {
			File f = new File(DB_PATH);
			if (!f.exists()) {
				f.mkdir();
				// f.createNewFile();

				// 得到assets目录下Sqlite数据库文件作为输入流
				InputStream is = context.getAssets().open(DBHelper.DBName);

				OutputStream os = new FileOutputStream(DB_PATH);

				byte[] buffer = new byte[1024];
				int length;
				while ((length = is.read(buffer)) > 0) {
					os.write(buffer, 0, length);
				}
				// 关闭文件流
				os.flush();
				os.close();
				is.close();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// }

		return DB_PATH;
	}

	/**
	 * @param context
	 * @param id
	 * @param viewGroup
	 * @return 返回 从配置文件中实例化的view对象
	 */
	public static View getView(Context context, int id, ViewGroup viewGroup) {
		return LayoutInflater.from(context).inflate(id, viewGroup);
	}

	// 对话框
	public static AlertDialog.Builder dialogBuilder;

	// 显示提示信息对话框(一个按钮)
	public static void showHintDialog(Activity context, String title,
			String content) {
		// if (dialogBuilder == null) {

		if (context != null || !context.isFinishing()) {

			dialogBuilder = new AlertDialog.Builder(context);
			// }
			dialogBuilder
					.setTitle(title)
					.setMessage(content)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setCancelable(true)
					.setNegativeButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							}).show();
		}
	}

	// 显示提示信息对话框(一个按钮)
	public static void showExitDialog(final Activity context, String title,
			String content) {
		// if (dialogBuilder == null) {

		if (context != null || !context.isFinishing()) {

			dialogBuilder = new AlertDialog.Builder(context);
			// }
			dialogBuilder
					.setTitle(title)
					.setMessage(content)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setCancelable(true)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									context.finish();
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							}).show();
		}
	}

	/**
	 * 获取随机数的新数组
	 * 
	 * @param list
	 * @return
	 */
	public static int[] getRandomList(int[] list) {
		int count = list.length;

		// 结果集
		int[] resultList = new int[count];

		// 用一个LinkedList作为中介
		LinkedList<Integer> temp = new LinkedList<Integer>();

		// 初始化temp
		for (int i = 0; i < count; i++) {
			temp.add((Integer) list[i]);
		}
		// 取数
		Random rand = new Random();
		for (int i = 0; i < count; i++) {
			int num = rand.nextInt(count - i);
			resultList[i] = (Integer) temp.get(num);
			temp.remove(num);
		}
		return resultList;
	}

	/**
	 * 检查应用程序是否已安装
	 * 
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean checkApkExist(Context context, String packageName) {
		if (packageName == null || "".equals(packageName)
				|| "null".equals(packageName))
			return false;
		try {
			ApplicationInfo info = context.getPackageManager()
					.getApplicationInfo(packageName,
							PackageManager.GET_UNINSTALLED_PACKAGES);
			return true;
		} catch (NameNotFoundException e) {
			Logger.log("info name not found error=" + e.getMessage());
			return false;
		}

	}

	// 安装APK
	public static final void installAPK(Activity activity, String fileURL) {
		try {
			String path = Environment.getExternalStorageDirectory().getPath()
					+ fileURL;
			Logger.log("apk path = " + path);

			File file = new File(path);
			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setAction(Intent.ACTION_VIEW);
			String type = "application/vnd.android.package-archive";
			intent.setDataAndType(Uri.fromFile(file), type);
			activity.startActivity(intent);

		} catch (Exception e) {
			Logger.log("installAPK Exception = " + e.toString());
		}
	}

	/**
	 * 打开APP
	 * 
	 * @param packageName
	 * @param context
	 */
	public static void openApp(String packageName, Context context) {
		PackageManager packageManager = context.getPackageManager();
		Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
		resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		resolveIntent.setPackage(packageName);

		List<ResolveInfo> apps = packageManager.queryIntentActivities(
				resolveIntent, 0);
		ResolveInfo ri = null;
		for (Iterator it = apps.iterator(); it.hasNext();) {
			ri = (ResolveInfo) it.next();
		}

		if (ri != null) {
			String className = ri.activityInfo.name;

			Intent intent = new Intent(Intent.ACTION_MAIN, null);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			ComponentName cn = new ComponentName(packageName, className);
			intent.setComponent(cn);
			context.startActivity(intent);
		}

	}

	/**
	 * 打开应用
	 * 
	 * @param packageName
	 * @param context
	 */
	public static void openApk(String packageName, Context context) {

		// if (Intent.ACTION_PACKAGE_ADDED.equals(action)) {
		PackageManager pm = context.getPackageManager();
		Intent intent1 = new Intent();
		try {
			intent1 = pm.getLaunchIntentForPackage(packageName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent1);
		// }

	}

	// 是否有SDCARD
	public static final boolean hasSDCard() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 在setAdapter之后设置
	 * 
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);

	}

	public static int getScreenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

	public static int getScreenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	private static String videoTitle;
	private static String videoName;

	public static String getVideoName() {
		return videoName;
	}

	public static void setVideoName(String videoName) {
		CommonTools.videoName = videoName;
	}

	public static void saveTitle(String title) {
		videoTitle = title;
	}

	public static String getTitle() {
		return videoTitle;
	}

}
