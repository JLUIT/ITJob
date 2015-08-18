package com.job.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.job.R;

public class RegisterPhoneActivity extends Activity {

	private EditText phone;
	private Button next_btn;
	private String type;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phone_register);
		Intent intent = getIntent();
		type = intent.getStringExtra("register_type");
		init();
	}

	private void init() {
	
		phone = (EditText) findViewById(R.id.et_phoneNumber);
		next_btn = (Button) findViewById(R.id.btn_next);
		
		next_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				save_phone();
				if(type.equals("person")){
					Intent i = new Intent(RegisterPhoneActivity.this, RegisterPersonActivity.class);
					startActivity(i);
				}else{
					Intent i = new Intent(RegisterPhoneActivity.this, RegisterPasswordActivity.class);
					startActivity(i);
				}
				
				
			}
		});
	}

	//存储电话信息到服务器中
	protected void save_phone() {
		
		
	}

	
	
}
