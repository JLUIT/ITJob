package com.job.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.job.R;

public class RegisterCompanyActivity extends Activity {

	private EditText company_name, email, lisence;
	private Button finish_btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_register);
		init();
	}

	private void init() {
		
		company_name = (EditText) findViewById(R.id.name2);
		email = (EditText) findViewById(R.id.email2);
		lisence = (EditText) findViewById(R.id.license);
		finish_btn = (Button) findViewById(R.id.register_complete2);
		finish_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				save_company();
				Intent i = new Intent(RegisterCompanyActivity.this, CompanyActivity.class);
				startActivity(i);
			}
		});
		
	}

	//保存信息到服务器中
	protected void save_company() {
		
		
	}

	
	
}
