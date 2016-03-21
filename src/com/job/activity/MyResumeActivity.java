package com.job.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import com.job.R;
import com.job.base.BaseActivity;
import com.job.util.DateTimePickDialogUtil;

public class MyResumeActivity extends BaseActivity implements OnClickListener {

	private EditText name;
	private EditText sex;
	private TextView birthdate;
	private RelativeLayout birthday;
	private EditText email;
	private EditText birth_address;
	private EditText stu_exp;
	private EditText phone;
	private EditText work_exp;
	private EditText city;
	private TextView save;
	private EditText introduction;
	private String result="";
	private boolean flag=false;//判断是新创建还是更新
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_resume);
		initView();
		Thread thread=new Thread(new getResume());
		thread.start();
	}

	private void initView() {
		name = (EditText) findViewById(R.id.name);
		sex = (EditText) findViewById(R.id.sex);
		birthdate = (TextView) findViewById(R.id.birthdate);
		birthday = (RelativeLayout) findViewById(R.id.birthday);
		email = (EditText) findViewById(R.id.emai);
		birth_address = (EditText) findViewById(R.id.birth_address);
		stu_exp = (EditText) findViewById(R.id.stu_exp);
		phone = (EditText) findViewById(R.id.phone);
		work_exp = (EditText) findViewById(R.id.work_exp);
		city = (EditText) findViewById(R.id.city);
		introduction = (EditText) findViewById(R.id.introduction);
		save = (TextView) findViewById(R.id.save);
		save.setOnClickListener(this);
		birthday.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.birthday:
			select_birth();
			break;
		case R.id.save:
			submit();
			break;
		default:
			break;

		}
	}

	private void select_birth() {

		DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
				this, birthdate.getText().toString());
		dateTimePicKDialog.dateTimePicKDialog(birthdate);
		
	}

	private void setEditTextEditable(EditText editText, boolean value) {
		if (value) {
			editText.setFocusableInTouchMode(true);
			editText.requestFocus();
		} else {
			editText.setFocusableInTouchMode(false);
			editText.clearFocus();
		}
	}
	
	private void submit() {
		// 验证处理
		String name1 = name.getText().toString().trim();
		if (TextUtils.isEmpty(name1)) {
			Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
			return;
		}

		String sex1 = sex.getText().toString().trim();
		if (TextUtils.isEmpty(sex1)) {
			Toast.makeText(this, "性别不能为空", Toast.LENGTH_SHORT).show();
			return;
		}

		String birth = birthdate.getText().toString().trim();
		if (TextUtils.isEmpty(birth)) {
			Toast.makeText(this, "出生年份不能为空", Toast.LENGTH_SHORT).show();
			return;
		}

		String address1 = birth_address.getText().toString().trim();
		if (TextUtils.isEmpty(address1)) {
			Toast.makeText(this, "籍贯不能为空", Toast.LENGTH_SHORT).show();
			return;
		}

		String exp1 = stu_exp.getText().toString().trim();
		if (TextUtils.isEmpty(exp1)) {
			Toast.makeText(this, "学历不能为空", Toast.LENGTH_SHORT).show();
			return;
		}

		String phone1 = phone.getText().toString().trim();
		if (TextUtils.isEmpty(phone1)) {
			Toast.makeText(this, "电话不能为空", Toast.LENGTH_SHORT).show();
			return;
		}

		String exp = work_exp.getText().toString().trim();
		if (TextUtils.isEmpty(exp)) {
			Toast.makeText(this, "工作年限不能为空", Toast.LENGTH_SHORT).show();
			return;
		}

		String city1 = city.getText().toString().trim();
		if (TextUtils.isEmpty(city1)) {
			Toast.makeText(this, "现居住城市不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		Thread thread=new Thread(new UpdateThread());
		thread.start();
	}

	Handler handler = new Handler()  
    {  
        public void handleMessage(Message msg)  
        {  
            switch(msg.what)  
            {  
            case -1:  
            	flag=true;
            	break;
            case 0:  
            	Toast.makeText(getApplicationContext(), "网络错误", Toast.LENGTH_SHORT).show();
            	break;
            case 1:
            	Initial();
            	break;
            case 2:
            	Toast.makeText(getApplicationContext(), "电话号码格式不正确！", Toast.LENGTH_SHORT).show();
            	break;
            case 3:
            	Toast.makeText(getApplicationContext(), "邮箱地址格式不正确！", Toast.LENGTH_SHORT).show();
            	break;	
            case 4:
            	Toast.makeText(getApplicationContext(), "保存成功！", Toast.LENGTH_SHORT).show();
            	finish();
            	break;
            case 5:
            	Toast.makeText(getApplicationContext(), "保存失败！", Toast.LENGTH_SHORT).show();
            	break;	
            }  
        }  
    }; 
    
    public void Initial()
    {
    	try {
    		
			JSONObject record=new JSONObject(result);
			name.setText(record.getString("name"));
			sex.setText(record.getString("gender"));
			birthdate.setText(record.getString("birthYear"));
			work_exp.setText(record.getString("workedTime"));
			city.setText(record.getString("livingCity"));
			birth_address.setText(record.getString("homeCity"));
			phone.setText(record.getString("phoneNumber"));
			email.setText(record.getString("email"));
			stu_exp.setText(record.getString("degree"));
			introduction.setText(record.getString("introduction"));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    class getResume implements Runnable
    {

    	 @Override  
         public void run() {  
         	 StringBuilder url=new StringBuilder(LoginActivity.URL+"GetRecord");
         	 url.append("?");
         	 url.append("username=");
         	 try {
 				url.append(URLEncoder.encode(LoginActivity.name,"UTF-8"));
 			} catch (UnsupportedEncodingException e1) {
 				// TODO Auto-generated catch block
 				e1.printStackTrace();
 			} 
  	        HttpURLConnection conn;
  	        Message msg = handler.obtainMessage();
 			try {
 				conn = (HttpURLConnection)new URL(url.toString()).openConnection();
 				 conn.setConnectTimeout(5000);  
 		 	        conn.setRequestMethod("GET");  
 		 	        int code=conn.getResponseCode();
 		 	        if(code==200)  
 		            {  
 		 	        	BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
 		 	        	String str="";
 		 	        	while((str=rd.readLine())!=null)
 		 	        		result+=str;
 		 	        	rd.close();
 		 	        	if(result.equals("{}"))
 		 	        		msg.what=-1;
 		 	        	else msg.what=1;
	 	            	handler.sendMessage(msg);
 		            }
 		 	        else
 		 	        {
 		 	        	msg.what=0;
		 	            handler.sendMessage(msg);
 		 	        }
 			} catch (MalformedURLException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			} catch (IOException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}  
         }  
    }
    
    class UpdateThread implements Runnable
    {

    	Message msg = handler.obtainMessage();
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String Name=name.getText().toString().trim();
			String Sex=sex.getText().toString().trim();
			String BirthYear=birthdate.getText().toString().trim();
			String WorkedTime=work_exp.getText().toString().trim();
			String LivingCity=city.getText().toString().trim();
			String HomeCity=birth_address.getText().toString().trim();
			String PhoneNumber=phone.getText().toString().trim();
			String Email=email.getText().toString().trim();
			String Degree=stu_exp.getText().toString().trim();
			String Introduction=introduction.getText().toString().trim();
			JSONObject object=new JSONObject();
			try {
				if(flag)
					object.put("type", "create");
				else object.put("type", "update");
				object.put("username", LoginActivity.name);
				object.put("name", Name);
				object.put("gender", Sex);
				object.put("birthYear", BirthYear);
				object.put("workedTime", WorkedTime);
				object.put("livingCity", LivingCity);
				object.put("homeCity", HomeCity);
				object.put("phoneNumber", PhoneNumber);
				object.put("email", Email);
				object.put("degree", Degree);
				object.put("introduction", Introduction);
				int result=CreateServer(object);
				msg.what=result;
				handler.sendMessage(msg);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
    
    private int CreateServer(JSONObject object)  
    {  
    	String path=LoginActivity.URL+"CreateRecord";
    	try{
    		URL url=new URL(path);
    		String content = String.valueOf(object);
        	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        	conn.setConnectTimeout(5000);
        	conn.setDoOutput(true);
        	conn.setRequestMethod("POST");
        	conn.setRequestProperty("User-Agent", "Fiddler");
        	conn.setRequestProperty("Content-Type", "application/json");
        	OutputStream os = conn.getOutputStream();
        	os.write(content.getBytes());
        	os.close();
        	int code = conn.getResponseCode();
        	if(code == 200)
        	{
        		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        		String result="";
        		result = in.readLine();
        		in.close();
        		return Integer.parseInt(result);
        	}
        	else return 0;
    	}catch (MalformedURLException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return 0;
    } 
}
