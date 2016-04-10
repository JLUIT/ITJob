/**
 * 
 */
package com.job.fragment;

import com.job.R;
import com.job.activity.InterviewHelpActivity;
import com.job.activity.JobEnvironmentActivity;
import com.job.activity.LabourRuleActivity;
import com.job.activity.RusumeActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * @author KID
 *
 */
public class GuideFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View guideLayout = inflater.inflate(R.layout.job_guidance,
				container, false);
		Button btn1=(Button)guideLayout.findViewById(R.id.bn1);
        Button btn2=(Button)guideLayout.findViewById(R.id.bn2);
        Button btn3=(Button)guideLayout.findViewById(R.id.bn3);
        Button btn4=(Button)guideLayout.findViewById(R.id.bn4);
		btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),RusumeActivity.class);
                startActivity(i);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),InterviewHelpActivity.class);
                startActivity(i);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),JobEnvironmentActivity.class);
                startActivity(i);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),LabourRuleActivity.class);
                startActivity(i);
            }
        });
    
		return guideLayout;
	}

	
	
}
