package com.job.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.job.R;
import com.job.base.BaseActivity;

public class CompanyMessageActivity extends BaseActivity implements OnClickListener{

	private RelativeLayout message, record, change_pwd, binding_tel, binding_email;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_message);
		init();
		
		
	}

	private void init() {
		
		message = (RelativeLayout) findViewById(R.id.message_center);
		record = (RelativeLayout) findViewById(R.id.conduct_record);
		change_pwd = (RelativeLayout) findViewById(R.id.change_password);
		binding_tel = (RelativeLayout) findViewById(R.id.binding_phone_number);
		binding_email = (RelativeLayout) findViewById(R.id.binding_email);
		
	
		message.setOnClickListener(this);
		record.setOnClickListener(this);
		change_pwd.setOnClickListener(this);
		binding_email.setOnClickListener(this);
		binding_tel.setOnClickListener(this);
		
		
	}

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.message_center:
			find_message();
			break;
		
		case R.id.conduct_record:
			find_record();
			break;
		
		case R.id.change_password:
			edit_pwd();
			break;
		
		case R.id.binding_phone_number:
			bind_phone();
			break;
		
		case R.id.binding_email:
			bind_email();
			break;
			
		default:
			break;
		}
		
	}

	private void bind_email() {
		
		intent2Activity(CompanyBindEmailActivity.class);
		
	}

	private void bind_phone() {
		
		intent2Activity(CompanyBindPhoneActivity.class);
	}

	private void edit_pwd() {
		
		intent2Activity(CompanyChangePassWordActivity.class);
	}

	private void find_record() {
		
	}

	private void find_message() {
		
	}

	
	
	
}
