package com.job.fragment;

import com.job.R;
import com.job.activity.JobRequireActivity;
import com.job.activity.SchoolRecruitActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SchoolFragment extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View schoolLayout = inflater.inflate(R.layout.school_enterprise,
				container, false);
		Button btn1=(Button)schoolLayout.findViewById(R.id.bn1);
        Button btn2=(Button)schoolLayout.findViewById(R.id.bn2);
        Button btn3=(Button)schoolLayout.findViewById(R.id.bn3);
        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),JobRequireActivity.class);
                startActivity(i);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),SchoolRecruitActivity.class);
                startActivity(i);
            }
        });
		return schoolLayout;
	}
	
}
