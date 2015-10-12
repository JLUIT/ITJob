package com.job.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.job.R;

public class ContactFragment extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contactLayout = inflater.inflate(R.layout.contact,
				container, false);
		return contactLayout;
	}
	
}
