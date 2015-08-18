package com.job.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.job.R;

public class RegisterPasswordActivity extends Activity {

	private Button next_btn;
	private EditText password,confirm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_password);
		init();
	}
	
	private void init() {
		
		next_btn = (Button) findViewById(R.id.register_next);
		password = (EditText) findViewById(R.id.password2);
		confirm = (EditText) findViewById(R.id.password_confirm);
		
		next_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				save_password();
				Intent i = new Intent(RegisterPasswordActivity.this, RegisterCompanyActivity.class);
				startActivity(i);
			}
		});
		
	}

	protected void save_password() {
		
		
	}
	
	
	
}
