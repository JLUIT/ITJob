package com.job.fragment;

import com.job.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SchoolFragment extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View schoolLayout = inflater.inflate(R.layout.school,
				container, false);
		return schoolLayout;
	}
	
}
