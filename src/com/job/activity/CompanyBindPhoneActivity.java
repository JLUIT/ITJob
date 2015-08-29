package com.job.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.job.R;
import com.job.base.BaseActivity;

public class CompanyBindPhoneActivity extends BaseActivity {

	private EditText phone;
	private Button finish_btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.binding_phone);
		init();
	}

	private void init() {
		
		phone = (EditText) findViewById(R.id.et_phoneNumber);
		finish_btn = (Button) findViewById(R.id.btn_finish);
		finish_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(save_phone()){
					intent2Activity(CompanyMessageActivity.class);
				}
				
			}
		});
		
	}

	//±£´æÐÞ¸Ä²Ù×÷
	protected boolean save_phone() {
		return false;
	}
	
}
