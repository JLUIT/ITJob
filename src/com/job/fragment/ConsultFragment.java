package com.job.fragment;

import com.job.R;
import com.job.activity.ConsultActivity;
import com.job.view.PullToRefreshListView;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ConsultFragment extends Fragment {
	
	private PullToRefreshListView list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View consultLayout = inflater.inflate(R.layout.message,
				container, false);
		return consultLayout;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		list = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent i = new Intent(getActivity(), ConsultActivity.class);
				getActivity().startActivity(i);
			}
		});
		
	}

	
	
}
