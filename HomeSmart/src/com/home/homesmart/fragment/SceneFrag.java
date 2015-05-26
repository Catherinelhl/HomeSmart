package com.home.homesmart.fragment;

import java.util.ArrayList;

import com.home.adapter.SceneItemAdapter;
import com.home.application.BaseApp;
import com.home.application.BaseDroidApp;
import com.home.constants.Scene;
import com.home.homesmart.R;
import com.home.homesmart.activity.HomeActivity;
import com.home.utils.CommonTools;
import com.home.utils.Logger;
import com.home.widget.CustomDialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

/**
 * 场景
 * 
 * @author hyc
 * 
 */
public class SceneFrag extends OuterFragment {

	ListView home_item_lv;

	SceneItemAdapter sceneItemAdapter;

	ArrayList<Scene> list;

	Scene scene;

	HomeActivity home;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.scene_layout, null);

		Logger.log("SceneFrag=====onCreateView==========>");

		home_item_lv = (ListView) view.findViewById(R.id.home_item_lv);

		list = new ArrayList<Scene>();

		getListFromDb();

		// addList();

		sceneItemAdapter = new SceneItemAdapter(getActivity(), list);
		home_item_lv.setAdapter(sceneItemAdapter);

		home_item_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				BaseDroidApp.getInstanse().showConfirmDialog("确认启动该场景么？", "确认",
						"取消", onClickListener1);
			}
		});

		home_item_lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				scene = list.get(position);
				BaseDroidApp.getInstanse().showUpdateBottomDialog(
						onClickListener);
				return false;
			}
		});

		return view;
	}

	private void getListFromDb() {
		list = BaseDroidApp.dbOperator.getSelectScene();
	}

	OnClickListener onClickListener = new OnClickListener() {

		public void onClick(View v) {
			switch (Integer.parseInt(String.valueOf(v.getTag()))) {
			case CustomDialog.TAG_UPDATE:
				Logger.log("TAG_UPDATE");

				home.updateScene(scene);
				BaseDroidApp.getInstanse().closeBottomDialog();
				break;
			case CustomDialog.TAG_DELETE:
				Logger.log("TAG_DELETE");

				BaseDroidApp.getInstanse().closeBottomDialog();
				BaseDroidApp.getInstanse().showConfirmDialog("确认删除该场景么？", "确认",
						"取消", onClickListener);

				break;
			case CustomDialog.TAG_SURE:
				BaseDroidApp.getInstanse().closeMessageDialog();
				BaseDroidApp.dbOperator.deleteScene(scene.id);
				list.remove(scene);
				sceneItemAdapter.notifyDataSetChanged();
				break;
			case CustomDialog.TAG_CANCLE:

				BaseDroidApp.getInstanse().closeMessageDialog();
				break;
			}
		}

	};

	OnClickListener onClickListener1 = new OnClickListener() {

		@Override
		public void onClick(View v) {

			switch (Integer.parseInt(String.valueOf(v.getTag()))) {
			case CustomDialog.TAG_SURE:

				Logger.log("启动该场景================>");
				BaseDroidApp.getInstanse().closeMessageDialog();
				
				// 场景下的命令下的按键下的码

				break;
			case CustomDialog.TAG_CANCLE:
				BaseDroidApp.getInstanse().closeMessageDialog();
				break;

			}
		}

	};

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);

		home = (HomeActivity) activity;
	}

	@Override
	public void onResume() {

		super.onResume();

		Logger.log("SceneFrag=====onResume==========>");

		home.setLastRightImg(R.drawable.home_add, "场景");
		home.setSwitchTitle("场景");
		home.setLeftImgShow();
		home.setleftTextHide();

		// home.noBottomMenu();

	}

	private void addList() {
		Scene scene1 = new Scene();
		scene1.name = "客厅";
		list.add(scene1);
		scene1 = new Scene();
		scene1.name = "厨房";
		list.add(scene1);
		scene1 = new Scene();
		scene1.name = "空调";
		list.add(scene1);
		scene1 = new Scene();
		scene1.name = "音响";
		list.add(scene1);
	}
}
