package com.job.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.job.R;
import com.job.base.BaseActivity;

public class CompanyNumberActivity extends BaseActivity {

	private TextView save;
	private EditText company_num;
   	
	@Override  
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.need_number);
		init(); 
	}
	

	private void init() {
  
		save = (TextView) findViewById(R.id.save);
		company_num = (EditText) findViewById(R.id.need_num);
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(save_num()){
					intent2Activity(CompanyNumberActivity.class);
				}else{
					
				}
			}
		}); 
	}

	protected boolean save_num() {

		return false; 
	}
 
		
}
