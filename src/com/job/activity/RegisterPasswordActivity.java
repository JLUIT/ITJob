package com.job.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import com.job.R;

public class RegisterPasswordActivity extends Activity {

	private Button next_btn;
	private EditText password,confirm;
	private String phoneNumber,Password,D_password;
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
		Intent intent=getIntent();
		phoneNumber=intent.getStringExtra("phoneNumber");
		next_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean result=CheckPassword();
				if(result)
				{
					ArrayList<String> list=new ArrayList<String>();
					list.add(phoneNumber);
					list.add(Password);
					Intent i = new Intent(RegisterPasswordActivity.this, RegisterCompanyActivity.class);
					i.putStringArrayListExtra("message", list);
					startActivity(i);
				}
				
			}
		});
		
	}

	protected boolean CheckPassword() {
		
		Password=password.getText().toString().trim();
		D_password=confirm.getText().toString().trim();
		if(Password.equals("")||D_password.equals(""))
		{
			 Toast.makeText(getApplicationContext(), "信息不能为空！", Toast.LENGTH_SHORT).show();  
			 return false;
		}
		else if(!Password.equals(D_password))
		{
			Toast.makeText(getApplicationContext(), "两次密码不一致！", Toast.LENGTH_SHORT).show();  
			 return false;
		}
		else if(Password.length()>12||Password.length()<6)
		{
			Toast.makeText(getApplicationContext(), "密码长度需在6~12位之间", Toast.LENGTH_SHORT).show();  
			 return false;
		}
		else return true;
	}
	
	
	
}
