package com.job.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.job.R;
import com.job.base.BaseActivity;

public class IntroductionActivity extends BaseActivity {

	
	private EditText introduction;
	private TextView save;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.introduction);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		introduction = (EditText) findViewById(R.id.introduction);
		save = (TextView) findViewById(R.id.save);
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
