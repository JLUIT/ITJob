package com.job.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.job.R;
import com.job.base.BaseActivity;

public class StudyExperienceActivity extends BaseActivity implements OnClickListener{

	private RelativeLayout ss, us, rs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.study_experience);
		init();
	}
	private void init() {
		ss = (RelativeLayout) findViewById(R.id.specialize_student);
		us = (RelativeLayout) findViewById(R.id.undergraduates);
		rs = (RelativeLayout) findViewById(R.id.research_student);
		
		ss.setOnClickListener(this);
		us.setOnClickListener(this);
		rs.setOnClickListener(this);
		
		
	}
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.specialize_student:
			save_ss();
			break;
		case R.id.undergraduates:
			save_us();
			break;
		case R.id.research_student:
			save_rs();
			break;
		default:
			break;
		}
	}
	
	private void save_rs() {
		intent2Activity(ApplyMessageActivity.class);
	}
	
	private void save_us() {
		intent2Activity(ApplyMessageActivity.class);
	}
	
	private void save_ss() {
		intent2Activity(ApplyMessageActivity.class);
	}

	
	
}
