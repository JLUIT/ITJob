package com.job.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.job.R;
import com.job.base.BaseActivity;

public class JobNameActivity extends BaseActivity {

	private TextView save;
	private EditText job_name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.job_name);
		init();
	}
	private void init() {

		save = (TextView) findViewById(R.id.save);
		job_name = (EditText) findViewById(R.id.job_name);
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				if(save_jobname()){
					intent2Activity(ApplyMessageActivity.class);
				
				}else{
					
				}
			}
		});
	}
	protected boolean save_jobname() {
		
		return false;
	}

	
}
