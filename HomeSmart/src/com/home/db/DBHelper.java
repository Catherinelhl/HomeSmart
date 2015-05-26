package com.home.db;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import com.home.utils.Base64;
import com.home.utils.Logger;
import com.home.utils.PrefrenceUtils;
import android.content.Context;

public class DBHelper extends SQLiteOpenHelper {

	public static String DBName = "home.db";

	public static String homecontrolName = "homecontrol";

	public static String homecontrolItemName = "homecontrolitem";

	public static String homecontrolItemCodeName = "homecontrolitemcode";

	public static String homesceneName = "homescene";

	public static String homeorderName = "homeorder";

	public static String homeswitchName = "homeswitch";

	public static String NAME = "name";

	private SQLiteDatabase mDB = null;

	public static int version = 1;

	// static PrefrenceUtils prefrenceHandler;

	public String sqlite_key = Base64.encode("hyc".getBytes());

	public static int getVersion(Context context) {

		// version = PrefrenceUtils.getInstance(context).getLiveVersion();

		return version;
	}

	public static void setVersion(int newVersion) {
		version = newVersion;
	}

	private Context mContext;

	public DBHelper(Context context) {
		super(context, DBName, null, getVersion(context));
		mDB = this.getReadableDatabase(sqlite_key);
	}

	String sql1 = "CREATE TABLE IF NOT EXISTS "
			+ homecontrolName
			+ " ( id INTEGER primary key autoincrement,name TEXT NOT NULL,type TEXT ,isDefault INT,image BLOB );";

	String sql2 = "CREATE TABLE IF NOT EXISTS "
			+ homecontrolItemName
			+ " ( id INTEGER primary key autoincrement,x int NOT NULL, y int NOT NULL,width int NOT NULL,height int NOT NULL,code TEXT,time TEXT,repeatDate TEXT, srcimage BLOB, bgimage BLOB, name TEXT,controlId INTEGER,FOREIGN KEY(controlId) REFERENCES homecontrol(id) );";

	String sql3 = "CREATE TABLE IF NOT EXISTS "
			+ homecontrolItemCodeName
			+ " ( id INTEGER primary key autoincrement,code TEXT,codeName TEXT,time TEXT,controlItemId INTEGER,FOREIGN KEY(controlItemId) REFERENCES homecontrolitem(id) );";

	String sql4 = "CREATE TABLE IF NOT EXISTS "
			+ homesceneName
			+ " (id INTEGER primary key autoincrement,name TEXT NOT NULL, time TEXT,repeatDate TEXT,image BLOB );";

	String sql5 = "CREATE TABLE IF NOT EXISTS "
			+ homeswitchName
			+ " (id INTEGER primary key autoincrement,name TEXT NOT NULL,isOpen INT ,image BLOB );";

	String sql6 = "CREATE TABLE IF NOT EXISTS "
			+ homeorderName
			+ " (id INTEGER primary key autoincrement,name TEXT NOT NULL, time TEXT,image BLOB, sceneId INTEGER, controlItemId INTEGER, FOREIGN KEY(sceneId) REFERENCES homescene(id), FOREIGN KEY(controlItemId) REFERENCES homecontrolitem(id) );";

	@Override
	public void onCreate(SQLiteDatabase db) {
		Logger.log("SQLitehelper onCreate!");
		// 随意创建
		try {
			// 创建数据库表(tv)
			db.execSQL(sql1);
			db.execSQL(sql2);
			db.execSQL(sql3);
			db.execSQL(sql4);
			db.execSQL(sql5);
			db.execSQL(sql6);

		} catch (Exception e) {
			Logger.log("创建数据库失败:" + e.toString());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try {
			db.execSQL("DROP TABLE IF EXISTS " + homecontrolName);
			db.execSQL("DROP TABLE IF EXISTS " + homecontrolItemName);
			db.execSQL("DROP TABLE IF EXISTS " + homecontrolItemCodeName);
			db.execSQL("DROP TABLE IF EXISTS " + homeorderName);
			db.execSQL("DROP TABLE IF EXISTS " + homesceneName);
			db.execSQL("DROP TABLE IF EXISTS " + homeswitchName);

			onCreate(db);

		} catch (Exception e) {
			Logger.log("更新数据库失败:" + e.toString());
		}
	}

}
