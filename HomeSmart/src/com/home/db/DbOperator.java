package com.home.db;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import net.sqlcipher.database.SQLiteDatabase;

import com.home.constants.Control;
import com.home.constants.ControlItem;
import com.home.constants.ControlItemCode;
import com.home.constants.Order;
import com.home.constants.Scene;
import com.home.constants.Switch;
import com.home.utils.CommonTools;
import com.home.utils.Logger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class DbOperator {

	private static DBHelper dbHelper;

	public static SQLiteDatabase sqlDb;

	public DbOperator(Context context) {
		dbHelper = new DBHelper(context);
	}

	/**
	 * 添加开关
	 * 
	 * @param list
	 */
	public static void insertSwitch(Switch switch1) {
		SQLiteDatabase db = dbHelper.getWritableDatabase(dbHelper.sqlite_key);
		sqlDb = db;
		db.beginTransaction();
		try {
			ContentValues contentValue = new ContentValues();
			contentValue.put("name", switch1.name);
			contentValue.put("isOpen", switch1.isOpen);

			ByteArrayOutputStream os = new ByteArrayOutputStream();
			switch1.image.compress(Bitmap.CompressFormat.PNG, 100, os);

			contentValue.put("image", os.toByteArray());
			db.insert(DBHelper.homeswitchName, null, contentValue);
			db.setTransactionSuccessful();
			// db.endTransaction();

		} catch (Exception e) {
			Logger.log(e.toString());
			db.endTransaction();
		} finally {
			db.endTransaction();

			if (db != null) {
				db.close();
			}
		}
	}

	/**
	 * 编辑开关
	 * 
	 * @param list
	 */
	public static void updateSwitch(Switch switch1) {
		SQLiteDatabase db = dbHelper.getWritableDatabase(dbHelper.sqlite_key);
		sqlDb = db;
		db.beginTransaction();
		try {
			ContentValues contentValue = new ContentValues();
			contentValue.put("name", switch1.name);
			contentValue.put("isOpen", switch1.isOpen);

			ByteArrayOutputStream os = new ByteArrayOutputStream();
			switch1.image.compress(Bitmap.CompressFormat.PNG, 100, os);

			contentValue.put("image", os.toByteArray());

			String where = "id = ?";
			String[] whereValue = { String.valueOf(switch1.id) };
			db.update(DBHelper.homeswitchName, contentValue, where, whereValue);
			db.setTransactionSuccessful();
			// db.endTransaction();

		} catch (Exception e) {
			Logger.log(e.toString());
			db.endTransaction();
		} finally {
			db.endTransaction();

			if (db != null) {
				db.close();
			}
		}
	}

	/**
	 * 
	 * 查询所有添加的开关
	 * */

	public static ArrayList<Switch> getSelectSwitch() {
		ArrayList<Switch> list = new ArrayList<Switch>();

		String str = "select * from " + DBHelper.homeswitchName;
		SQLiteDatabase db = dbHelper.getReadableDatabase(dbHelper.sqlite_key);
		sqlDb = db;
		Cursor cursor = db.query(DBHelper.homeswitchName, null, null, null,
				null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {

				Switch item = new Switch();
				item.id = cursor.getInt(0);
				item.name = cursor.getString(1);
				item.isOpen = cursor.getInt(2);
				byte[] b = cursor.getBlob(3);
				ByteArrayInputStream input = new ByteArrayInputStream(b);
				item.image = BitmapFactory.decodeStream(input);
				list.add(item);
			}
			cursor.close();
		}
		if (db != null) {
			db.close();
		}
		return list;

	}

	/**
	 * 删除开关
	 * 
	 * @param name
	 */
	public static void deleteSwitch(int id) {
		SQLiteDatabase db = dbHelper.getWritableDatabase(dbHelper.sqlite_key);
		sqlDb = db;
		db.beginTransaction();

		try {
			String where = "id = ?";
			String[] whereValue = { String.valueOf(id) };
			db.delete(dbHelper.homeswitchName, where, whereValue);
			db.setTransactionSuccessful();
		} catch (Exception e) {
			Logger.log(e.toString());
		} finally {
			db.endTransaction();
			if (db != null) {
				db.close();
			}
		}

	}

	/**
	 * 添加场景
	 * 
	 * @param list
	 */
	public static void insertScene(Scene scene) {
		SQLiteDatabase db = dbHelper.getWritableDatabase(dbHelper.sqlite_key);
		sqlDb = db;
		db.beginTransaction();
		try {
			ContentValues contentValue = new ContentValues();
			contentValue.put("name", scene.name);
			contentValue.put("time", scene.time);
			contentValue.put("repeatDate", scene.repeatDate);

			ByteArrayOutputStream os = new ByteArrayOutputStream();
			scene.image.compress(Bitmap.CompressFormat.PNG, 100, os);

			contentValue.put("image", os.toByteArray());
			db.insert(DBHelper.homesceneName, null, contentValue);
			db.setTransactionSuccessful();
			// db.endTransaction();

		} catch (Exception e) {
			Logger.log(e.toString());
			db.endTransaction();
		} finally {
			db.endTransaction();
			if (db != null) {
				db.close();
			}
		}
	}

	/**
	 * 编辑场景
	 * 
	 * @param list
	 */
	public static void updateScene(Scene scene) {
		SQLiteDatabase db = dbHelper.getWritableDatabase(dbHelper.sqlite_key);
		sqlDb = db;
		db.beginTransaction();
		try {
			ContentValues contentValue = new ContentValues();
			contentValue.put("name", scene.name);
			contentValue.put("time", scene.time);
			contentValue.put("repeatDate", scene.repeatDate);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			scene.image.compress(Bitmap.CompressFormat.PNG, 100, os);

			contentValue.put("image", os.toByteArray());

			String where = "id = ?";
			String[] whereValue = { String.valueOf(scene.id) };
			db.update(DBHelper.homesceneName, contentValue, where, whereValue);
			db.setTransactionSuccessful();
			// db.endTransaction();

		} catch (Exception e) {
			Logger.log(e.toString());
			db.endTransaction();
		} finally {
			db.endTransaction();
			if (db != null) {
				db.close();
			}
		}
	}

	/**
	 * 
	 * 查询所有的场景
	 * */

	public static ArrayList<Scene> getSelectScene() {
		ArrayList<Scene> list = new ArrayList<Scene>();

		String str = "select * from " + DBHelper.homesceneName;
		SQLiteDatabase db = dbHelper.getReadableDatabase(dbHelper.sqlite_key);
		sqlDb = db;
		Cursor cursor = db.query(DBHelper.homesceneName, null, null, null,
				null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {

				Scene item = new Scene();
				item.id = cursor.getInt(0);
				item.name = cursor.getString(1);
				item.time = cursor.getString(2);
				item.repeatDate = cursor.getString(3);
				byte[] b = cursor.getBlob(4);
				ByteArrayInputStream input = new ByteArrayInputStream(b);
				item.image = BitmapFactory.decodeStream(input);
				list.add(item);
			}
			cursor.close();
		}
		if (db != null) {
			db.close();
		}
		return list;

	}

	/**
	 * 删除场景
	 * 
	 * @param name
	 */
	public static void deleteScene(int id) {
		SQLiteDatabase db = dbHelper.getWritableDatabase(dbHelper.sqlite_key);
		sqlDb = db;
		db.beginTransaction();

		try {
			String where = "id = ?";
			String[] whereValue = { String.valueOf(id) };
			db.delete(dbHelper.homesceneName, where, whereValue);
			db.setTransactionSuccessful();
		} catch (Exception e) {
			Logger.log(e.toString());
		} finally {
			db.endTransaction();
			if (db != null) {
				db.close();
			}
		}

	}

	/**
	 * 添加遥控按钮
	 * 
	 * @param list
	 */
	public static void insertControlItem(ControlItem item) {
		SQLiteDatabase db = dbHelper.getWritableDatabase(dbHelper.sqlite_key);
		sqlDb = db;
		db.beginTransaction();
		try {
			ContentValues contentValue = new ContentValues();
			contentValue.put("x", item.x);
			contentValue.put("y", item.y);
			contentValue.put("width", item.width);
			contentValue.put("height", item.height);
			contentValue.put("code", item.code);
			contentValue.put("time", item.time);
			contentValue.put("repeatDate", item.repeatDate);

			if (item.srcimage != null) {
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				item.srcimage.compress(Bitmap.CompressFormat.PNG, 100, os);
				contentValue.put("srcimage", os.toByteArray());
			}
			if (item.bgimage != null) {
				ByteArrayOutputStream os1 = new ByteArrayOutputStream();
				item.bgimage.compress(Bitmap.CompressFormat.PNG, 100, os1);
				contentValue.put("bgimage", os1.toByteArray());
			}
			contentValue.put("name", item.name);
			contentValue.put("controlId", item.controlId);
			db.insert(DBHelper.homecontrolItemName, null, contentValue);
			db.setTransactionSuccessful();
			// db.endTransaction();

		} catch (Exception e) {
			Logger.log(e.toString());
			db.endTransaction();
		} finally {
			db.endTransaction();
			if (db != null) {
				db.close();
			}
		}
	}

	/**
	 * 
	 * 查询所有的按键
	 * */

	public static ArrayList<ControlItem> getControlItem() {
		ArrayList<ControlItem> list = new ArrayList<ControlItem>();

		String str = "select * from " + DBHelper.homecontrolItemName;
		SQLiteDatabase db = dbHelper.getReadableDatabase(dbHelper.sqlite_key);
		sqlDb = db;
		Cursor cursor = db.query(DBHelper.homecontrolItemName, null, null,
				null, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {

				ControlItem item = new ControlItem();
				item.id = cursor.getInt(0);
				item.x = cursor.getInt(1);
				item.y = cursor.getInt(2);
				item.width = cursor.getInt(3);
				item.height = cursor.getInt(4);
				item.code = cursor.getString(5);
				item.time = cursor.getString(6);
				item.repeatDate = cursor.getString(7);
				byte[] srcbyte = cursor.getBlob(8);
				if (null != srcbyte) {
					ByteArrayInputStream srcinput = new ByteArrayInputStream(
							srcbyte);
					item.srcimage = BitmapFactory.decodeStream(srcinput);
				}
				byte[] bgbyte = cursor.getBlob(9);
				if (null != bgbyte) {
					ByteArrayInputStream bginput = new ByteArrayInputStream(
							bgbyte);
					item.bgimage = BitmapFactory.decodeStream(bginput);
				}
				item.name = cursor.getString(10);
				item.controlId = cursor.getInt(11);
				list.add(item);
			}
			cursor.close();
		}
		if (db != null) {
			db.close();
		}
		return list;

	}

	/**
	 * 
	 * 由遥控器查询所有的按键
	 * */

	public static ArrayList<ControlItem> getControlItemByControl(int controlId) {
		ArrayList<ControlItem> list = new ArrayList<ControlItem>();

		String str = "select * from " + DBHelper.homecontrolItemName
				+ " where controlId=" + controlId;
		SQLiteDatabase db = dbHelper.getReadableDatabase(dbHelper.sqlite_key);
		sqlDb = db;
		Cursor cursor = db.query(DBHelper.homecontrolItemName, null,
				"controlId=?", new String[] { String.valueOf(controlId) },
				null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {

				ControlItem item = new ControlItem();
				item.id = cursor.getInt(0);
				item.x = cursor.getInt(1);
				item.y = cursor.getInt(2);
				item.width = cursor.getInt(3);
				item.height = cursor.getInt(4);
				item.code = cursor.getString(5);
				item.time = cursor.getString(6);
				item.repeatDate = cursor.getString(7);
				byte[] srcbyte = cursor.getBlob(8);
				if (null != srcbyte) {
					ByteArrayInputStream srcinput = new ByteArrayInputStream(
							srcbyte);
					item.srcimage = BitmapFactory.decodeStream(srcinput);
				}
				byte[] bgbyte = cursor.getBlob(9);
				if (null != bgbyte) {
					ByteArrayInputStream bginput = new ByteArrayInputStream(
							bgbyte);
					item.bgimage = BitmapFactory.decodeStream(bginput);
				}
				item.name = cursor.getString(10);
				item.controlId = cursor.getInt(11);
				list.add(item);
			}
			cursor.close();
		}
		if (db != null) {
			db.close();
		}
		return list;

	}

	/**
	 * 编辑遥控器按键
	 * 
	 * @param list
	 */
	public static void updateControlItem(ControlItem item) {
		SQLiteDatabase db = dbHelper.getWritableDatabase(dbHelper.sqlite_key);
		sqlDb = db;
		db.beginTransaction();
		try {
			ContentValues contentValue = new ContentValues();
			contentValue.put("x", item.x);
			contentValue.put("y", item.y);
			contentValue.put("width", item.width);
			contentValue.put("height", item.height);
			contentValue.put("code", item.code);
			contentValue.put("time", item.time);
			contentValue.put("repeatDate", item.repeatDate);
			if (item.srcimage != null) {
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				item.srcimage.compress(Bitmap.CompressFormat.PNG, 100, os);
				contentValue.put("srcimage", os.toByteArray());
			}

			if (item.bgimage != null) {
				ByteArrayOutputStream os1 = new ByteArrayOutputStream();
				item.bgimage.compress(Bitmap.CompressFormat.PNG, 100, os1);
				contentValue.put("bgimage", os1.toByteArray());
			}
			contentValue.put("name", item.name);
			contentValue.put("controlId", item.controlId);

			String where = "id = ?";
			String[] whereValue = { String.valueOf(item.id) };
			db.update(DBHelper.homecontrolItemName, contentValue, where,
					whereValue);
			db.setTransactionSuccessful();
			// db.endTransaction();

		} catch (Exception e) {
			Logger.log(e.toString());
			db.endTransaction();
		} finally {
			db.endTransaction();
			if (db != null) {
				db.close();
			}
		}
	}

	/**
	 * 删除遥控器按键
	 * 
	 * @param name
	 */
	public static void deleteControlItem(int id) {
		SQLiteDatabase db = dbHelper.getWritableDatabase(dbHelper.sqlite_key);
		sqlDb = db;
		db.beginTransaction();

		try {
			String where = "id = ?";
			String[] whereValue = { String.valueOf(id) };
			db.delete(dbHelper.homecontrolItemName, where, whereValue);
			db.setTransactionSuccessful();
		} catch (Exception e) {
			Logger.log(e.toString());
		} finally {
			db.endTransaction();
			if (db != null) {
				db.close();
			}
		}

	}

	/**
	 * 删除遥控器按键
	 * 
	 * @param name
	 */
	public static void deleteControlItemByControlId(int controlId) {
		SQLiteDatabase db = dbHelper.getWritableDatabase(dbHelper.sqlite_key);
		sqlDb = db;
		db.beginTransaction();

		try {
			String where = "controlId = ?";
			String[] whereValue = { String.valueOf(controlId) };
			db.delete(dbHelper.homecontrolItemName, where, whereValue);
			db.setTransactionSuccessful();
		} catch (Exception e) {
			Logger.log(e.toString());
		} finally {
			db.endTransaction();
			if (db != null) {
				db.close();
			}
		}

	}

	/**
	 * 添加遥控器
	 * 
	 * @param list
	 */
	public static void insertControl(Control control) {
		SQLiteDatabase db = dbHelper.getWritableDatabase(dbHelper.sqlite_key);
		sqlDb = db;
		db.beginTransaction();
		try {
			ContentValues contentValue = new ContentValues();
			contentValue.put("name", control.name);
			contentValue.put("type", control.type);
			contentValue.put("isDefault", control.isDefault);

			ByteArrayOutputStream os = new ByteArrayOutputStream();
			control.image.compress(Bitmap.CompressFormat.PNG, 100, os);

			contentValue.put("image", os.toByteArray());
			db.insert(DBHelper.homecontrolName, null, contentValue);
			db.setTransactionSuccessful();
			// db.endTransaction();

		} catch (Exception e) {
			Logger.log(e.toString());
			db.endTransaction();
		} finally {
			db.endTransaction();
			if (db != null) {
				db.close();
			}
		}
	}

	/**
	 * 
	 * 查询遥控器最大Id
	 * */

	public static int getMaxControlId() {
		int id = 0;

		String sql = "select max(id) from " + DBHelper.homecontrolName;
		SQLiteDatabase db = dbHelper.getReadableDatabase(dbHelper.sqlite_key);
		sqlDb = db;
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor != null && cursor.getCount() > 0) {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {
				id = cursor.getInt(0);
			}
			cursor.close();
		}
		if (db != null) {
			db.close();
		}
		return id;

	}

	/**
	 * 
	 * 查询场景最大Id
	 * */

	public static int getMaxSceneId() {
		int id = 0;

		String sql = "select max(id) from " + DBHelper.homesceneName;
		SQLiteDatabase db = dbHelper.getReadableDatabase(dbHelper.sqlite_key);
		sqlDb = db;
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor != null && cursor.getCount() > 0) {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {
				id = cursor.getInt(0);
			}
			cursor.close();
		}
		if (db != null) {
			db.close();
		}
		return id;

	}

	/**
	 * 编辑遥控器
	 * 
	 * @param list
	 */
	public static void updateControl(Control item) {
		SQLiteDatabase db = dbHelper.getWritableDatabase(dbHelper.sqlite_key);
		sqlDb = db;
		db.beginTransaction();
		try {
			ContentValues contentValue = new ContentValues();
			contentValue.put("name", item.name);
			contentValue.put("type", item.type);
			contentValue.put("isDefault", item.isDefault);

			ByteArrayOutputStream os = new ByteArrayOutputStream();
			item.image.compress(Bitmap.CompressFormat.PNG, 100, os);
			contentValue.put("image", os.toByteArray());

			String where = "id = ?";
			String[] whereValue = { String.valueOf(item.id) };
			db.update(DBHelper.homecontrolName, contentValue, where, whereValue);
			db.setTransactionSuccessful();
			// db.endTransaction();

		} catch (Exception e) {
			Logger.log(e.toString());
			db.endTransaction();
		} finally {
			db.endTransaction();
			if (db != null) {
				db.close();
			}
		}
	}

	/**
	 * 
	 * 查询所有遥控器
	 * */

	public static ArrayList<Control> getSelectControl() {
		ArrayList<Control> list = new ArrayList<Control>();

		String str = "select * from " + DBHelper.homecontrolName;
		SQLiteDatabase db = dbHelper.getReadableDatabase(dbHelper.sqlite_key);
		sqlDb = db;
		Cursor cursor = db.query(DBHelper.homecontrolName, null, null, null,
				null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {

				Control item = new Control();
				item.id = cursor.getInt(0);
				item.name = cursor.getString(1);
				item.type = cursor.getString(2);
				item.isDefault = cursor.getInt(3);
				byte[] b = cursor.getBlob(4);
				ByteArrayInputStream input = new ByteArrayInputStream(b);
				item.image = BitmapFactory.decodeStream(input);
				list.add(item);
			}
			cursor.close();
		}
		if (db != null) {
			db.close();
		}
		return list;

	}

	/**
	 * 
	 * 查询默认的遥控器
	 * */

	public static Control getDefaultControl(int isDefault) {
		Control item = null;
		String str = "select * from " + DBHelper.homecontrolName
				+ " where isDefault=" + isDefault;
		SQLiteDatabase db = dbHelper.getReadableDatabase(dbHelper.sqlite_key);
		sqlDb = db;
		Cursor cursor = db.query(DBHelper.homecontrolName, null, "isDefault=?",
				new String[] { String.valueOf(isDefault) }, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			item = new Control();
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {
				item.id = cursor.getInt(0);
				item.name = cursor.getString(1);
				item.type = cursor.getString(2);
				item.isDefault = cursor.getInt(3);
				byte[] b = cursor.getBlob(4);
				ByteArrayInputStream input = new ByteArrayInputStream(b);
				item.image = BitmapFactory.decodeStream(input);
			}
			cursor.close();
		}
		if (db != null) {
			db.close();
		}
		return item;

	}

	/**
	 * 删除遥控器
	 * 
	 * @param name
	 */
	public static void deleteControl(int id) {
		SQLiteDatabase db = dbHelper.getWritableDatabase(dbHelper.sqlite_key);
		sqlDb = db;
		db.beginTransaction();

		try {
			String where = "id = ?";
			String[] whereValue = { String.valueOf(id) };
			db.delete(dbHelper.homecontrolName, where, whereValue);
			db.setTransactionSuccessful();
		} catch (Exception e) {
			Logger.log(e.toString());
		} finally {
			db.endTransaction();
			if (db != null) {
				db.close();
			}
		}

	}

	/**
	 * 添加命令
	 * 
	 * @param list
	 */
	public static void insertOrder(Order order) {
		SQLiteDatabase db = dbHelper.getWritableDatabase(dbHelper.sqlite_key);
		sqlDb = db;
		db.beginTransaction();
		try {
			ContentValues contentValue = new ContentValues();
			contentValue.put("name", order.name);
			contentValue.put("time", order.time);

			ByteArrayOutputStream os = new ByteArrayOutputStream();
			order.image.compress(Bitmap.CompressFormat.PNG, 100, os);

			contentValue.put("image", os.toByteArray());

			contentValue.put("controlItemId", order.controlItemId);
			contentValue.put("sceneId", order.sceneId);

			db.insert(DBHelper.homeorderName, null, contentValue);
			db.setTransactionSuccessful();
			// db.endTransaction();

		} catch (Exception e) {
			Logger.log(e.toString());
			db.endTransaction();
		} finally {
			db.endTransaction();

			if (db != null) {
				db.close();
			}
		}
	}

	/**
	 * 
	 * 查询场景下的所有命令
	 * */

	public static ArrayList<Order> getOrderByScene(int sceneId) {
		ArrayList<Order> list = new ArrayList<Order>();

		String str = "select * from " + DBHelper.homeorderName
				+ " where sceneId=" + sceneId;
		SQLiteDatabase db = dbHelper.getReadableDatabase(dbHelper.sqlite_key);
		sqlDb = db;
		Cursor cursor = db.query(DBHelper.homeorderName, null, "sceneId=?",
				new String[] { String.valueOf(sceneId) }, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {

				Order item = new Order();
				item.id = cursor.getInt(0);
				item.name = cursor.getString(1);
				item.time = cursor.getString(2);
				byte[] srcbyte = cursor.getBlob(3);
				if (null != srcbyte) {
					ByteArrayInputStream srcinput = new ByteArrayInputStream(
							srcbyte);
					item.image = BitmapFactory.decodeStream(srcinput);
				}
				item.sceneId = cursor.getInt(4);
				item.controlItemId = cursor.getInt(5);

				list.add(item);
			}
			cursor.close();
		}
		if (db != null) {
			db.close();
		}
		return list;

	}

	/**
	 * 编辑命令
	 * 
	 * @param list
	 */
	public static void updateOrder(Order item) {
		SQLiteDatabase db = dbHelper.getWritableDatabase(dbHelper.sqlite_key);
		sqlDb = db;
		db.beginTransaction();
		try {
			ContentValues contentValue = new ContentValues();
			contentValue.put("name", item.name);
			contentValue.put("time", item.time);

			ByteArrayOutputStream os = new ByteArrayOutputStream();
			item.image.compress(Bitmap.CompressFormat.PNG, 100, os);
			contentValue.put("image", os.toByteArray());

			contentValue.put("controlItemId", item.controlItemId);
			contentValue.put("sceneId", item.sceneId);

			String where = "id = ?";
			String[] whereValue = { String.valueOf(item.id) };
			db.update(DBHelper.homeorderName, contentValue, where, whereValue);
			db.setTransactionSuccessful();
			// db.endTransaction();

		} catch (Exception e) {
			Logger.log(e.toString());
			db.endTransaction();
		} finally {
			db.endTransaction();
			if (db != null) {
				db.close();
			}
		}
	}

	/**
	 * 删除命令
	 * 
	 * @param name
	 */
	public static void deleteOrder(int id) {
		SQLiteDatabase db = dbHelper.getWritableDatabase(dbHelper.sqlite_key);
		sqlDb = db;
		db.beginTransaction();

		try {
			String where = "id = ?";
			String[] whereValue = { String.valueOf(id) };
			db.delete(dbHelper.homeorderName, where, whereValue);
			db.setTransactionSuccessful();
		} catch (Exception e) {
			Logger.log(e.toString());
		} finally {
			db.endTransaction();
			if (db != null) {
				db.close();
			}
		}

	}

	/**
	 * 添加遥控器红外码
	 * 
	 * @param list
	 */
	public static void insertControlItemCode(ControlItemCode item) {
		SQLiteDatabase db = dbHelper.getWritableDatabase(dbHelper.sqlite_key);
		sqlDb = db;
		db.beginTransaction();
		try {
			ContentValues contentValue = new ContentValues();
			contentValue.put("codeName", item.codeName);
			contentValue.put("code", item.code);

			contentValue.put("time", item.time);
			contentValue.put("controlItemId", item.controlItemId);

			db.insert(DBHelper.homecontrolItemCodeName, null, contentValue);
			db.setTransactionSuccessful();
			// db.endTransaction();

		} catch (Exception e) {
			Logger.log(e.toString());
			db.endTransaction();
		} finally {
			db.endTransaction();
			if (db != null) {
				db.close();
			}
		}
	}

	/**
	 * 编辑遥控器红外码
	 * 
	 * @param list
	 */
	public static void updateControlItemCode(ControlItemCode item) {
		SQLiteDatabase db = dbHelper.getWritableDatabase(dbHelper.sqlite_key);
		sqlDb = db;
		db.beginTransaction();
		try {
			ContentValues contentValue = new ContentValues();
			contentValue.put("codeName", item.codeName);
			contentValue.put("code", item.code);

			contentValue.put("time", item.time);
			contentValue.put("controlItemId", item.controlItemId);

			String where = "id = ?";
			String[] whereValue = { String.valueOf(item.id) };
			db.update(DBHelper.homecontrolItemCodeName, contentValue, where,
					whereValue);
			db.setTransactionSuccessful();
			// db.endTransaction();

		} catch (Exception e) {
			Logger.log(e.toString());
			db.endTransaction();
		} finally {
			db.endTransaction();

			if (db != null) {
				db.close();
			}
		}
	}

	/**
	 * 删除遥控器红外码
	 * 
	 * @param id
	 */
	public static void deleteControlItemCode(int id) {
		SQLiteDatabase db = dbHelper.getWritableDatabase(dbHelper.sqlite_key);
		sqlDb = db;
		db.beginTransaction();

		try {
			String where = "id = ?";
			String[] whereValue = { String.valueOf(id) };
			db.delete(dbHelper.homecontrolItemCodeName, where, whereValue);
			db.setTransactionSuccessful();
		} catch (Exception e) {
			Logger.log(e.toString());
		} finally {
			db.endTransaction();
			if (db != null) {
				db.close();
			}
		}

	}

	/**
	 * 
	 * 查询遥控器下所有码
	 * */

	public ArrayList<ControlItemCode> getCodeBycodeItem(int controlItemId) {
		ArrayList<ControlItemCode> list = new ArrayList<ControlItemCode>();

		String str = "select * from " + DBHelper.homecontrolItemCodeName
				+ " where controlItemId=" + controlItemId;
		SQLiteDatabase db = dbHelper.getReadableDatabase(dbHelper.sqlite_key);
		sqlDb = db;
		Cursor cursor = db.query(DBHelper.homecontrolItemCodeName, null,
				"controlItemId=?",
				new String[] { String.valueOf(controlItemId) }, null, null,
				null);
		if (cursor != null && cursor.getCount() > 0) {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {

				ControlItemCode item = new ControlItemCode();
				item.id = cursor.getInt(0);
				item.code = cursor.getString(1);
				item.codeName = cursor.getString(2);
				item.time = cursor.getString(3);
				item.controlItemId = cursor.getInt(4);

				list.add(item);
			}
			cursor.close();
		}
		if (db != null) {
			db.close();
		}
		return list;

	}

}
