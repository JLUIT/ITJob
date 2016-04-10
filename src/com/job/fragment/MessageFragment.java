package com.job.fragment;

import java.util.ArrayList;
import java.util.List;
import com.job.R;
import com.job.activity.MessageActivity;
import com.job.adapter.MyAdapter;
import com.job.adapter.SpinnerAdapter;
import com.job.bean.MyListItem;
import com.job.db.DBManager;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;

public class MessageFragment extends Fragment {

	
	private DBManager dbm;
	private SQLiteDatabase db;
	private Button filter_btn;
	private Spinner spinner1 = null;//省份
	private Spinner spinner2=null;//城市
	private Spinner spinner4=null;//薪资
	private Spinner spinner5=null;//学历
	private Spinner spinner6=null;//企业性质
	private Spinner spinner7=null;//公司规模
	private Spinner spinner8=null;//发布日期
	private String province,workCity,salary,property,scope,acaQualification,dateTime; 
	boolean b=false;
	boolean c=false;
	boolean d=false;
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		
		super.onViewCreated(view, savedInstanceState);
		filter_btn = (Button) view.findViewById(R.id.btn_finish);
        filter_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//连接服务器在数据库中筛选操作
				Filter();	
			}
		});
        spinner1=(Spinner)view.findViewById(R.id.spinner1);
        spinner2=(Spinner)view.findViewById(R.id.spinner2);
        spinner4=(Spinner)view.findViewById(R.id.spinner4);
        spinner5=(Spinner)view.findViewById(R.id.spinner5);
        spinner6=(Spinner)view.findViewById(R.id.spinner6);
        spinner7=(Spinner)view.findViewById(R.id.spinner7);
        spinner8=(Spinner)view.findViewById(R.id.spinner8);
        initSpinner1();
        String[] arr={"不限","2000以下","2000~5000","5000~10000","10000~30000","30000以上"};
        String[] arr1={"不限","高中及以下","大学专科","大学本科","硕士","博士"};
        String[] arr2={"不限","私营","国企","外企"};
        String[] arr3={"不限","1000以下","1000~2000","2000~5000","5000~10000","10000以上"};
        String[] arr4={"不限","当日","近三天","近一周","近一月"};
        SpinnerAdapter adapter = new SpinnerAdapter(getActivity(),  
        android.R.layout.simple_spinner_item, arr);  
        spinner4.setAdapter(adapter);  
        spinner4.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				salary=spinner4.getSelectedItem().toString().trim();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
        SpinnerAdapter adapter1 = new SpinnerAdapter(getActivity(),  
  	    android.R.layout.simple_spinner_item, arr1);  
  	    spinner5.setAdapter(adapter1);
  	    spinner5.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				acaQualification=spinner5.getSelectedItem().toString().trim();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
  	    SpinnerAdapter adapter2 = new SpinnerAdapter(getActivity(),  
  		android.R.layout.simple_spinner_item, arr2);  
  	    spinner6.setAdapter(adapter2);
  	    spinner6.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				property=spinner6.getSelectedItem().toString().trim();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
  	    SpinnerAdapter adapter3 = new SpinnerAdapter(getActivity(),  
	    android.R.layout.simple_spinner_item, arr3);  
	    spinner7.setAdapter(adapter3); 
	    spinner7.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				scope=spinner7.getSelectedItem().toString().trim();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
	    SpinnerAdapter adapter4 = new SpinnerAdapter(getActivity(),  
        android.R.layout.simple_spinner_item, arr4);  
	    spinner8.setAdapter(adapter4); 
	    spinner8.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				dateTime=spinner8.getSelectedItem().toString().trim();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View messageLayout = inflater.inflate(R.layout.filter_job,
				container, false);
		
		
		return messageLayout;
	}
	
	public void initSpinner1(){
		dbm = new DBManager(getActivity());
	 	dbm.openDatabase();
	 	db = dbm.getDatabase();
	 	List<MyListItem> list = new ArrayList<MyListItem>();
		
	 	try {    
	        String sql = "select * from province";  
	        Cursor cursor = db.rawQuery(sql,null);  
	        cursor.moveToFirst();
	        while (!cursor.isLast()){ 
		        String code=cursor.getString(cursor.getColumnIndex("code")); 
		        byte bytes[]=cursor.getBlob(2); 
		        String name=new String(bytes,"gbk");
		        MyListItem myListItem=new MyListItem();
		        myListItem.setName(name);
		        myListItem.setPcode(code);
		        list.add(myListItem);
		        cursor.moveToNext();
	        }
	        String code=cursor.getString(cursor.getColumnIndex("code")); 
	        byte bytes[]=cursor.getBlob(2); 
	        String name=new String(bytes,"gbk");
	        MyListItem myListItem=new MyListItem();
	        myListItem.setName(name);
	        myListItem.setPcode(code);
	        list.add(myListItem);
	        
	    } catch (Exception e) {  
	    } 
	 	dbm.closeDatabase();
	 	db.close();	
	 	
	 	MyAdapter myAdapter = new MyAdapter(getActivity(),list);
	 	spinner1.setAdapter(myAdapter);
		spinner1.setOnItemSelectedListener(new SpinnerOnSelectedListener1());
	}
    public void initSpinner2(String pcode){
		dbm = new DBManager(getActivity());
	 	dbm.openDatabase();
	 	db = dbm.getDatabase();
	 	List<MyListItem> list = new ArrayList<MyListItem>();
		
	 	try {    
	        String sql = "select * from city where pcode='"+pcode+"'";  
	        Cursor cursor = db.rawQuery(sql,null);  
	        cursor.moveToFirst();
	        while (!cursor.isLast()){ 
		        String code=cursor.getString(cursor.getColumnIndex("code")); 
		        byte bytes[]=cursor.getBlob(2); 
		        String name=new String(bytes,"gbk");
		        MyListItem myListItem=new MyListItem();
		        myListItem.setName(name);
		        myListItem.setPcode(code);
		        list.add(myListItem);
		        cursor.moveToNext();
	        }
	        String code=cursor.getString(cursor.getColumnIndex("code")); 
	        byte bytes[]=cursor.getBlob(2); 
	        String name=new String(bytes,"gbk");
	        MyListItem myListItem=new MyListItem();
	        myListItem.setName(name);
	        myListItem.setPcode(code);
	        list.add(myListItem);
	        
	    } catch (Exception e) {  
	    } 
	 	dbm.closeDatabase();
	 	db.close();	
	 	
	 	MyAdapter myAdapter = new MyAdapter(getActivity(),list);
	 	spinner2.setAdapter(myAdapter);
		spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				MyListItem item=(MyListItem)spinner2.getSelectedItem();
				workCity=item.getName().toString().trim();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
	}

    
	class SpinnerOnSelectedListener1 implements OnItemSelectedListener{
		
		public void onItemSelected(AdapterView<?> adapterView, View view, int position,
				long id) {
			    String pcode =((MyListItem) adapterView.getItemAtPosition(position)).getPcode();
			
			initSpinner2(pcode);
			MyListItem item=(MyListItem)spinner1.getSelectedItem();
			province=item.getName().toString().trim();
			
		}

		public void onNothingSelected(AdapterView<?> adapterView) {
			// TODO Auto-generated method stub
		}		
	}
	public void Filter()
	{
		Intent intent = new Intent();  
        intent.setClass(getActivity(), MessageActivity.class); 
        String content=province+" "+workCity+" "+salary+" "+property+" "+scope+" "+acaQualification+" "+dateTime;
        intent.putExtra("content", content);
        intent.putExtra("type", "filter");//表示筛选
        startActivity(intent); 		
	}
}






