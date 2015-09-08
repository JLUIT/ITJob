package com.job.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.job.R;
import com.job.base.BaseActivity;

public class JobDescribeActivity extends BaseActivity {

	private TextView save;
	private EditText job_describe;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.job_describe);
		init();
	}

	private void init() {
		save = (TextView) findViewById(R.id.save);
		job_describe = (EditText) findViewById(R.id.job_describe);
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				if(save_jobdescribe()){
					intent2Activity(ApplyMessageActivity.class);
				}else{
					
				}
			}
		});
		
	}

	protected boolean save_jobdescribe() {
		return true;
	}
	

	
}
