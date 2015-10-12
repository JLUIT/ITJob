package com.job.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.job.R;
import com.job.base.BaseActivity;

public class SalaryActivity extends BaseActivity {

	private EditText low_salary, high_salary;
	private TextView save_btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.salary);
		init();
	}

	private void init() {

		low_salary = (EditText) findViewById(R.id.low_salary);
		high_salary = (EditText) findViewById(R.id.high_salary);
		save_btn = (TextView) findViewById(R.id.save);
		save_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//保存成功时跳转到发布页面，失败时弹出对话框
				if(save_salary()){
					intent2Activity(ApplyMessageActivity.class);
				}else{
					
					
				}
				
			}
		});
		
		
	}
	
	
	protected boolean save_salary() {
		// TODO Auto-generated method stub
		return false;
	}

	
	
}
