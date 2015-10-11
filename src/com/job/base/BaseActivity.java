package com.job.base;


import com.job.util.ToastUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class BaseActivity extends Activity {


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d("BaseActivity", getClass().getSimpleName());
	}

	protected void intent2Activity(Class<? extends Activity> tarActivity) {
		Intent intent = new Intent(this, tarActivity);
		startActivity(intent);
	}
	
	protected void showToast(String msg) {
		ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
	}

	
}
