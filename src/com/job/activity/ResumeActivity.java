package com.job.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.job.R;
import com.job.base.BaseActivity;

public class ResumeActivity extends BaseActivity implements OnClickListener{

	private RelativeLayout apply_job,inapply_job,resume;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resume_activity);
		init();
	}

	private void init() {

		apply_job = (RelativeLayout) findViewById(R.id.apply_job);
		inapply_job = (RelativeLayout) findViewById(R.id.inapply_job);
		resume = (RelativeLayout) findViewById(R.id.resume);
		apply_job.setOnClickListener(this);
		inapply_job.setOnClickListener(this);
		resume.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.apply_job:
			applyJob();
			
			break;
		case R.id.inapply_job:
			inapplyJob();
			break;
		case R.id.resume:
			resumeMag();
			break;
		default:
			break;
		}
	}

	private void resumeMag() {
		intent2Activity(MyResumeActivity.class);
	}

	private void inapplyJob() {
		
	}

	private void applyJob() {
		
	}

}
