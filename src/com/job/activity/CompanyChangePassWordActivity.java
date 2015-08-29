package com.job.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.job.R;
import com.job.base.BaseActivity;

public class CompanyChangePassWordActivity extends BaseActivity {

	private EditText edit_old_pwd, edit_new_pwd, edit_confirm_pwd;
	private Button finish_btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_change_password);
		init();
	}

	private void init() {
		
		edit_old_pwd = (EditText) findViewById(R.id.old_password);
		edit_new_pwd = (EditText) findViewById(R.id.new_password);
		edit_confirm_pwd = (EditText) findViewById(R.id.password_again);
		finish_btn = (Button) findViewById(R.id.finish_btn);
		finish_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				save_pwd();
				
			}
		});
	}

	protected void save_pwd() {
		
		if(check_confirm_pwd() && check_old_pwd()){
			
			//±£¥Ê√‹¬Î
			
			finish();
		}
		
		
	}

	private boolean check_confirm_pwd() {
		
		String pwd_new = edit_new_pwd.getText().toString().trim();
		String pwd_again = edit_confirm_pwd.getText().toString().trim();
		
		if(!pwd_new.equals(pwd_again)){
			
			Toast.makeText(this, R.string.check_confirm_password, Toast.LENGTH_SHORT).show();
			return false;
		}
		
		return true;
	}

	private boolean check_old_pwd() {
		
		return false;
	}

	
	
}
