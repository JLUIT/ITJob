package com.job.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.job.R;

public class RegisterPersonActivity extends Activity {

	private EditText username,email,password;
	private Button finish_btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_register);
		init();
	}

	private void init() {
		
		username = (EditText) findViewById(R.id.name1);
		email = (EditText) findViewById(R.id.email1);
		password = (EditText) findViewById(R.id.password1);
		finish_btn = (Button) findViewById(R.id.register_complete1);
		finish_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				save_person();
				Intent i = new Intent(RegisterPersonActivity.this,PersonActivity.class);
				startActivity(i);
			}
		});
		
	}

	//保存信息到服务器中
	protected void save_person() {
		
		
	}

	
	
}
