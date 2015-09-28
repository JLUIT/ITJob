package com.job.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.job.R;
import com.job.base.BaseActivity;

public class JobSiteActivity extends BaseActivity {

	private EditText job_site;
	private TextView save;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.job_site);
		init();
	}

	private void init(){
		job_site = (EditText) findViewById(R.id.job_site);
		save = (TextView) findViewById(R.id.save);
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				if(save_jobsite()){
					intent2Activity(ApplyMessageActivity.class);
				}else{
					
				}
			}
		});
	}

	protected boolean save_jobsite() {
		return false;
	}

	
	
}
