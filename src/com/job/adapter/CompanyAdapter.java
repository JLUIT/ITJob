package com.job.adapter;

import java.util.List;

import com.job.R;
import com.job.bean.CompanyMsg;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CompanyAdapter extends BaseAdapter {

	
	private Context context;
	private List<CompanyMsg> list;
	
	
	public CompanyAdapter(Context context, List<CompanyMsg> list) {
		super();
		this.context = context;
		this.list = list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view;
		ViewHolder viewHolder;
		if(convertView == null) {
			view = LayoutInflater.from(context).inflate(R.layout.item_company_msg, null);
			viewHolder = new ViewHolder();
			viewHolder.job_name = (TextView) view.findViewById(R.id.job_name);
			viewHolder.company_name = (TextView) view.findViewById(R.id.company_name);
			viewHolder.company_size = (TextView) view.findViewById(R.id.company_size);
			viewHolder.experience = (TextView) view.findViewById(R.id.experience);
			viewHolder.salary = (TextView) view.findViewById(R.id.salary);
			viewHolder.site = (TextView) view.findViewById(R.id.site);
			view.setTag(viewHolder);
			
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		
		viewHolder.job_name.setText(list.get(position).getJob_name());
		viewHolder.company_name.setText(list.get(position).getCompany_name());
		viewHolder.company_size.setText(list.get(position).getCompany_size());
		viewHolder.experience.setText(list.get(position).getExperience());
		viewHolder.salary.setText(list.get(position).getSalary());
		viewHolder.site.setText(list.get(position).getSite());
		return view;
		
	}

	class ViewHolder{
		
		TextView job_name;
		TextView company_name;
		TextView company_size;
		TextView experience;
		TextView salary;
		TextView site;
		
	}
	
	
}
