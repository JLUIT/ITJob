/**
 * 
 */
package com.job.fragment;

import com.job.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author KID
 *
 */
public class GuideFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View guideLayout = inflater.inflate(R.layout.guide,
				container, false);
		return guideLayout;
	}

	
	
}
