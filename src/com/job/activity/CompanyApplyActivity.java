package com.job.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.job.R;
import com.job.base.BaseActivity;

public class CompanyApplyActivity extends BaseActivity {

	private ImageView edit_msg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_apply_message);
		init();
	}

	private void init() {
		 edit_msg = (ImageView) findViewById(R.id.release);
		 edit_msg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				intent2Activity(ApplyMessageActivity.class);
			}
		});
	}

	
	
}
