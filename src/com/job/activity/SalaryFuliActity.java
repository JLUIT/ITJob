package com.job.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.job.R;
import com.job.adapter.TagAdapter;
import com.job.base.BaseActivity;
import com.job.view.FlowLayout;
import com.job.view.TagFlowLayout;

public class SalaryFuliActity extends BaseActivity{

	private TextView save;
	private String[] mVals = new String[]
            {"五险一金", "交通补贴", "餐饮补贴", "专业培训", "年终奖金", "员工旅游"};
	 private TagFlowLayout mFlowLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.salary_fuli);
		init();
	}

	private void init() {
		
		save = (TextView) findViewById(R.id.save);
		mFlowLayout = (TagFlowLayout) findViewById(R.id.tag_id);
		final LayoutInflater mInflater = LayoutInflater.from(this);
		mFlowLayout.setAdapter(new TagAdapter<String>(mVals)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                        mFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });
		
	}


	private void save_salary() {
		
	}

	
	
}
