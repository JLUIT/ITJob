package com.job.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.job.R;
import com.job.base.BaseActivity;

public class ApplyMessageActivity extends BaseActivity implements OnClickListener{

	private Button send_btn;
	private RelativeLayout job_describe, job_site, job_name, company_number, salary, salaryfuli, study_experience;
	private EditText company_size, company_natural;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_apply_message);
		init();
	}

	private void init() {
		
		send_btn = (Button) findViewById(R.id.send);
		job_describe = (RelativeLayout) findViewById(R.id.job_describe);
		job_site = (RelativeLayout) findViewById(R.id.job_point);
		job_name = (RelativeLayout) findViewById(R.id.job_name);
		company_number = (RelativeLayout) findViewById(R.id.company_number);
		salary = (RelativeLayout) findViewById(R.id.salary);
		salaryfuli = (RelativeLayout) findViewById(R.id.salaryfuli);
		study_experience = (RelativeLayout) findViewById(R.id.study_experience);
		company_natural = (EditText) findViewById(R.id.company_natual);
		company_size = (EditText) findViewById(R.id.companyguimo);
		
		send_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(send_message()){
					intent2Activity(CompanyApplyActivity.class);
				}
			}
		});
		
		job_describe.setOnClickListener(this);
		job_site.setOnClickListener(this);
		job_name.setOnClickListener(this);
		company_number.setOnClickListener(this);
		salary.setOnClickListener(this);
		salaryfuli.setOnClickListener(this);
		study_experience.setOnClickListener(this);
		
		
	}

	
	protected boolean send_message() {
		
		return true;
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.job_describe:
			intent2Activity(JobDescribeActivity.class);
			break;
		case R.id.job_name:
			intent2Activity(JobNameActivity.class);
			break;
		case R.id.job_point:
			intent2Activity(JobSiteActivity.class);
			break;
		case R.id.company_number:
			intent2Activity(CompanyNumberActivity.class);
			break;
		case R.id.study_experience:
			intent2Activity(StudyExperienceActivity.class);
			break;
		case R.id.salary:
			intent2Activity(SalaryActivity.class);
			break;
		case R.id.salaryfuli:
			intent2Activity(SalaryFuliActity.class);
			break;
			
		default:
			break;
		}
	}

	
	
}
