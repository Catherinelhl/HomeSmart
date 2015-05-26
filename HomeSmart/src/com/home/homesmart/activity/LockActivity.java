package com.home.homesmart.activity;

import java.util.List;
import java.util.concurrent.locks.Lock;

import com.home.constants.Constants;
import com.home.homesmart.R;
import com.home.utils.Logger;
import com.home.utils.PrefrenceUtils;
import com.home.utils.StringTools;
import com.home.widget.LockPatternView;
import com.home.widget.LockPatternView.Cell;
import com.home.widget.LockPatternView.DisplayMode;
import com.home.widget.LockPatternView.OnPatternListener;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

/**
 * 输入手势密码
 * 
 * @author hyc
 * 
 */
public class LockActivity extends Activity implements OnPatternListener {

	private List<Cell> lockPattern;
	private LockPatternView lockPatternView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		String pass = PrefrenceUtils.getInstance(this).getPassValue(
				Constants.LOCK_KEY);

		if (StringTools.isNullOrEmpty(pass)) {
			finish();
			return;
		}

		lockPattern = LockPatternView.stringToPattern(pass);
		setContentView(R.layout.activity_lock);

		lockPatternView = (LockPatternView) findViewById(R.id.lock_pattern);
		lockPatternView.setOnPatternListener(this);
	}

	@Override
	public void onBackPressed() {

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
		Logger.log("onPatternCellAdded:"
				+ LockPatternView.patternToString(pattern));

	}

	@Override
	public void onPatternDetected(List<Cell> pattern) {
		Logger.log("onPatternDetected");

		if (pattern.equals(lockPattern)) {
			finish();
		} else {
			lockPatternView.setDisplayMode(DisplayMode.Wrong);
			Toast.makeText(this, R.string.lockpattern_error, Toast.LENGTH_LONG)
					.show();
		}

	}
}
