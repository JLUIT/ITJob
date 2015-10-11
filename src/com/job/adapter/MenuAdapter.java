package com.job.adapter;

import java.util.List;

import com.job.R;
import com.job.bean.Menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MenuAdapter extends ArrayAdapter<Menu>{

	private int resourceId;
	public MenuAdapter(Context context, int resource, List<Menu> objects) {
		super(context, resource, objects);
		resourceId = resource;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Menu menu = getItem(position);
		View view;
		if(convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		} else {
			view = convertView;
		}
		TextView name = (TextView) view.findViewById(R.id.menu_name);
		name.setText(menu.getName());
		return view;
	}

	
}
