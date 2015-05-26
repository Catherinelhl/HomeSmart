package com.home.homesmart.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.home.constants.Constants;
import com.home.homesmart.R;
import com.home.utils.Logger;
import com.home.utils.PrefrenceUtils;
import com.home.widget.LockPatternView;
import com.home.widget.LockPatternView.Cell;
import com.home.widget.LockPatternView.DisplayMode;
import com.home.widget.LockPatternView.OnPatternListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * 设置手势密码
 * 
 * @author hyc
 * 
 */
public class LockSetupActivity extends Activity implements OnClickListener,
		OnPatternListener {

	private LockPatternView lockPatternView;
	private Button leftButton;
	private Button rightButton;

	private static final int STEP_1 = 1; // 开始
	private static final int STEP_2 = 2; // 第一次设置手势完成
	private static final int STEP_3 = 3; // 按下继续按钮
	private static final int STEP_4 = 4; // 第二次设置手势完成

	private int step;

	private List<Cell> choosePattern;

	private boolean confirm = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lock_setup);

		lockPatternView = (LockPatternView) findViewById(R.id.lock_pattern);
		lockPatternView.setOnPatternListener(this);
		leftButton = (Button) findViewById(R.id.left_btn);
		rightButton = (Button) findViewById(R.id.right_btn);

		step = STEP_1;

		updateView();
	}

	private void updateView() {
		switch (step) {
		case STEP_1:
			leftButton.setText(R.string.cancel);
			rightButton.setText("");
			rightButton.setEnabled(false);
			choosePattern = null;
			confirm = false;
			lockPatternView.clearPattern();
			lockPatternView.enableInput();

			break;
		case STEP_2:
			leftButton.setText(R.string.try_again);
			rightButton.setText(R.string.goon);
			rightButton.setEnabled(true);
			lockPatternView.disableInput();
			break;
		case STEP_3:
			leftButton.setText(R.string.cancel);
			rightButton.setText("");
			rightButton.setEnabled(false);
			lockPatternView.clearPattern();
			lockPatternView.enableInput();
			break;

		case STEP_4:
			leftButton.setText(R.string.cancel);
			if (confirm) {
				rightButton.setText(R.string.confirm);
				rightButton.setEnabled(true);
				lockPatternView.disableInput();
			} else {
				rightButton.setText("");
				lockPatternView.setDisplayMode(DisplayMode.Wrong);
				lockPatternView.enableInput();
				rightButton.setEnabled(false);
			}
			break;

		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.left_btn:
			if (step == STEP_1 || step == STEP_3 || step == STEP_4) {
				finish();
			} else if (step == STEP_2) {
				step = STEP_1;
				updateView();
			}
			break;
		case R.id.right_btn:
			if (step == STEP_2) {
				step = STEP_3;
				updateView();
			} else if (step == STEP_4) {
				PrefrenceUtils.getInstance(this).setBoolean(Constants.GESTUREPASS, true);
				PrefrenceUtils.getInstance(this).setString(Constants.LOCK_KEY,
						LockPatternView.patternToString(choosePattern));
				finish();
			}

		}

	}

	@Override
	public void onPatternStart() {
		Logger.log("onPatternStart");

	}

	@Override
	public void onPatternCleared() {
		Logger.log("onPatternCleared");

	}

	@Override
	public void onPatternCellAdded(List<Cell> pattern) {
		Logger.log("onPatternCellAdded");

	}

	@Override
	public void onPatternDetected(List<Cell> pattern) {
		Logger.log("onPatternDetected");
		if (pattern.size() < LockPatternView.MIN_LOCK_PATTERN_SIZE) {
			Toast.makeText(this,
					R.string.lockpattern_recording_incorrect_too_short,
					Toast.LENGTH_SHORT).show();
			return;
		}

		if (choosePattern == null) {
			choosePattern = new ArrayList<LockPatternView.Cell>(pattern);
			Logger.log("onPatternDetected:"
					+ Arrays.toString(choosePattern.toArray()));

			step = STEP_2;
			updateView();
			return;
		}

		Logger.log("onPatternDetected: choosePattern===>"
				+ Arrays.toString(choosePattern.toArray()));
		Logger.log("onPatternDetected:pattern===>"
				+ Arrays.toString(pattern.toArray()));

		if (choosePattern.equals(pattern)) {

			confirm = true;
		} else {
			confirm = false;
		}

		step = STEP_4;
		updateView();

	}

}
