package com.job.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.job.R;
import com.job.base.BaseActivity;

public class ChooseProActivity extends BaseActivity {

	private EditText edit_pro;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pro_choose);
		edit_pro = (EditText) findViewById(R.id.edit_pro);
	}

}
