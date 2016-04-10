package com.job.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.job.R;
import com.job.activity.CompanyApplyActivity;
import com.job.activity.ConsultActivity;
import com.job.adapter.CompanyAdapter;
import com.job.bean.CompanyMsg;
import com.job.view.PullToRefreshBase;
import com.job.view.PullToRefreshBase.OnRefreshListener2;
import com.job.view.PullToRefreshListView;

public class ConsultFragment extends Fragment {
	
	private PullToRefreshListView list;
	private List<CompanyMsg> list_msg = new ArrayList<CompanyMsg>();
	private CompanyAdapter adapter;

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
		list
		.setOnRefreshListener(new OnRefreshListener2<ListView>()
				{
					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView)
					{
						Log.e("TAG", "onPullDownToRefresh");
						//	这里写下拉刷新的任务
						String label = DateUtils.formatDateTime(getActivity().getApplicationContext(), System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

						// Do work to refresh the list here.
						new GetDataTask().execute();
					}
		
					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView)
					{
						Log.e("TAG", "onPullUpToRefresh");
						//这里写上拉加载更多的任务
					}
				});
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent i = new Intent(getActivity(), ConsultActivity.class);
				getActivity().startActivity(i);
			}
		});
		
	}

	//下拉刷新时加载最新招聘信息
		private class GetDataTask extends AsyncTask<Void, Void, List<CompanyMsg>> {

			
			
			@Override
			protected List<CompanyMsg> doInBackground(Void... params) {
				
				list_msg.clear();
				
				return list_msg;
			}

			@Override
			protected void onPostExecute(List<CompanyMsg> result) {
				  
				adapter = new CompanyAdapter(getActivity(),result);
				list.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				list.onRefreshComplete();
				super.onPostExecute(result);
			}
			
				 
		}
	
}
