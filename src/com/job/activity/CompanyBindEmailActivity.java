package com.job.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.job.R;
import com.job.base.BaseActivity;

public class CompanyBindEmailActivity extends BaseActivity {

	private Button finish_btn;
	private EditText edit_pwd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.binding_email);
		init();
	}

	private void init() {
		
		finish_btn = (Button) findViewById(R.id.btn_finish);
		edit_pwd = (EditText) findViewById(R.id.et_email);
		finish_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(save_email()){
					intent2Activity(CompanyMessageActivity.class);
				}
				
			}
		});
		
	}

	//保存邮箱信息到服务器
	protected boolean save_email() {
		return false;
		
		
	}

	
	
}
