package com.job.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.job.R;
import com.job.base.BaseActivity;

public class CollegeChooseActivity extends BaseActivity {

	private EditText edit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.college_choose);
		edit = (EditText) findViewById(R.id.edit_school);
	}

}
