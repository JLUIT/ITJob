package com.job.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.job.R;
import com.job.adapter.LoginPagerAdapter;
import com.job.base.BaseActivity;


public class LoginActivity extends BaseActivity implements OnClickListener{

	public static final String TAG = "LoginActivity";
	private SharedPreferences sp;
	private ViewPager viewPager;
	private CheckBox remember_password1,remember_password2;
	private ImageView imageView;
	private EditText P_username,P_password,E_username,E_password;
	private TextView textView1,textView2;
	private List<View> views;
	private TextView forget_pass1,forget_pass2;
	private Button login_btn1,login_btn2,register_btn1,register_btn2;
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private View view1,view2;//各个页卡
	private ProgressDialog mDialog;
	private String type="";//登录类型 0  个人，1  公司
	public static String name="";
	public static String pass="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		sp = getSharedPreferences("login", Context.MODE_PRIVATE);
		
		initImageView();
		initTextView();
		
		initViewPager();
		init();
	}

	private void init() {
		forget_pass1 = (TextView) view1.findViewById(R.id.tv_forget_password1);
		forget_pass2 = (TextView) view2.findViewById(R.id.tv_forget_password2);
		login_btn1 = (Button) view1.findViewById(R.id.login1);
		login_btn2 = (Button) view2.findViewById(R.id.login2);
		register_btn1 = (Button) view1.findViewById(R.id.register1);
		register_btn2 = (Button) view2.findViewById(R.id.register2);
		P_username = (EditText) view1.findViewById(R.id.account1);
		P_password = (EditText) view1.findViewById(R.id.password1);
		E_username=(EditText) view2.findViewById(R.id.account2);
		E_password=(EditText) view2.findViewById(R.id.password2);
		remember_password1 = (CheckBox) view1.findViewById(R.id.remember_password);
		remember_password2 = (CheckBox) view2.findViewById(R.id.remember_password);
		login_btn1.setOnClickListener(this);
		login_btn2.setOnClickListener(this);
		register_btn1.setOnClickListener(this);
		register_btn2.setOnClickListener(this);
		forget_pass1.setOnClickListener(this);
		forget_pass2.setOnClickListener(this);
		
		if(sp.getBoolean("ISCHECK1", false)){
			
			remember_password1.setChecked(true);
			P_username.setText(sp.getString("USER_NAME", ""));
			P_password.setText(sp.getString("USER_PASSWORD", ""));
			
		}
		
		if(sp.getBoolean("ISCHECK2", false)){
			
			remember_password2.setChecked(true);
			E_username.setText(sp.getString("COMPANY_NAME", ""));
			E_password.setText(sp.getString("COMPANY_PASSWORD", ""));
			
		}
		
		//监听记住密码个人登陆的单选框
		remember_password1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				if(remember_password1.isChecked()){
					
					sp.edit().putBoolean("ISCHECK1", true).commit();
				
				}else{
					sp.edit().putBoolean("ISCHECK1", false).commit();
				}
				
			}
		});
		
		//监听记住密码公司登陆的单选框
		remember_password2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				if(remember_password2.isChecked()){
					
					sp.edit().putBoolean("ISCHECK2", true).commit();
				
				}else{
					sp.edit().putBoolean("ISCHECK2", false).commit();
				}
				
			}
		});
	}

	private void initImageView() {
		
		imageView= (ImageView) findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a).getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 3 - bmpW) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		imageView.setImageMatrix(matrix);// 设置动画初始位置
		
	}

	private void initViewPager() {
		viewPager=(ViewPager) findViewById(R.id.vPager);
		views=new ArrayList<View>();
		LayoutInflater inflater=getLayoutInflater();
		view1=inflater.inflate(R.layout.person_login, null);
		view2=inflater.inflate(R.layout.company_login, null);
		
		views.add(view1);
		views.add(view2);

		viewPager.setAdapter(new LoginPagerAdapter(views));
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		
	}

	private void initTextView() {
		
		textView1 = (TextView) findViewById(R.id.text1);
		textView2 = (TextView) findViewById(R.id.text2);	
		
		textView1.setOnClickListener(new MyOnClickListener(0));
		textView2.setOnClickListener(new MyOnClickListener(1));
		
		
	}

	/** 
	 *     
	 * 头标点击监听 3 */
	private class MyOnClickListener implements OnClickListener{
        private int index=0;
        public MyOnClickListener(int i){
        	index=i;
        }
		public void onClick(View v) {
			viewPager.setCurrentItem(index);			
		}
		
	}
	
	public class MyOnPageChangeListener implements OnPageChangeListener{

    	int one = offset * 4 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量
		public void onPageScrollStateChanged(int arg0) {
			
			
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
			
		}

		public void onPageSelected(int arg0) {
			
			Animation animation = new TranslateAnimation(one*currIndex, one*arg0, 0, 0);
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			imageView.startAnimation(animation);
			
		}
    	
    }

	@Override
	public void onClick(View v) {
			
			switch (v.getId()) {
			case R.id.login1:
				type="0";
				rememeber();
				Login();
				break;
			case R.id.login2:
				type="1";
				rememeber();
				Login();
				break;
			case R.id.register1:
				registerPerson();
				break;
			case R.id.register2:
				registerCompany();
				break;
			case R.id.tv_forget_password1:
				forget_person();
				break;
			case R.id.tv_forget_password2:
				forget_company();
				break;
			default:
				break;
			}
			
		}
	
	private void rememeber() {
		
		if(remember_password1.isChecked()){
			
			//记住用户名和密码
			String name = P_username.getText().toString().trim();
			String password = P_password.getText().toString().trim();
			Editor editor = sp.edit();
			editor.putString("USER_NAME", name);
			editor.putString("USER_PASSWORD", password);
			editor.commit();
		}
		
		if(remember_password2.isChecked()){
			
			//记住公司名和密码
			String name = E_username.getText().toString().trim();
			String password = E_password.getText().toString().trim();
			Editor editor = sp.edit();
			editor.putString("COMPANY_NAME", name);
			editor.putString("COMPANY_PASSWORD", password);
			editor.commit();
		}
	}

	Handler handler = new Handler()  
    {  
        public void handleMessage(Message msg)  
        {  
            switch(msg.what)  
            {  
            case 0:  
                mDialog.cancel();  
                Toast.makeText(getApplicationContext(), "登录成功！", Toast.LENGTH_SHORT).show();  
                if(type.equals("0"))
                {
                	Intent intent = new Intent(LoginActivity.this,PersonActivity.class);
                	startActivity(intent);
                }
                else
                {
                	Intent intent = new Intent(LoginActivity.this,CompanyActivity.class);
                	startActivity(intent);
                }
    			finish();
    			overridePendingTransition(R.anim.fade_in, R.anim.fade_out); 
                break;  
            case 1:  
                mDialog.cancel();  
                Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_SHORT).show();  
                break;  
            case 2:
            	mDialog.cancel();
            	Toast.makeText(getApplicationContext(), "用户名，密码或登录类型不能为空",Toast.LENGTH_SHORT).show();
            	break;
            }  
              
        }  
    }; 
    
    public class LoginThread implements Runnable  
    {  
  
        @Override  
        public void run() { 
        	if(type.equals("0"))
        	{
        		name = P_username.getText().toString().trim();  
        		pass = P_password.getText().toString().trim();  
        	}
        	else
        	{
        		name = E_username.getText().toString().trim();  
        		pass = E_password.getText().toString().trim();  
        	}
            Message msg = handler.obtainMessage();
            if(name.equals("")||pass.equals(""))
            {
            	msg.what=2;
            	handler.sendMessage(msg);
            }
            else{
		            boolean result=loginServer(name,pass);  
		            if(result)  
		            {   
		                    msg.what = 0;  
		                    handler.sendMessage(msg);  
		            }else  
		            {  
		                msg.what = 1;  
		                handler.sendMessage(msg);  
		            }  
                }
        }  
          
    }  
	
	 private boolean loginServer(String username, String password)  
	    {  
		 String path="http://49.140.60.236:8080/IT/Login";  
	        //将用户名和密码放入HashMap中  
	        Map<String,String> params=new HashMap<String,String>();  
	        params.put("userName", username);  
	        params.put("passWord", password);
	        params.put("type",type);
	        try {  
	            return sendGETRequest(path,params,"UTF-8");  
	        } catch (MalformedURLException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
	        return false;  
	    }  
	    private static boolean sendGETRequest(String path,Map<String,String> params,String encode) throws MalformedURLException, IOException {  
	        StringBuilder url=new StringBuilder(path);  
	        url.append("?");  
	        for(Map.Entry<String, String> entry:params.entrySet())  
	        {  
	            url.append(entry.getKey()).append("=");  
	            url.append(URLEncoder.encode(entry.getValue(),encode));  
	            url.append("&");  
	        }  
	      //删掉最后一个&   
	        url.deleteCharAt(url.length()-1);
	        String str=url.toString();
	        HttpURLConnection conn=(HttpURLConnection)new URL(str).openConnection();  
	        conn.setConnectTimeout(5000);  
	        conn.setRequestMethod("GET");  
	        int code=conn.getResponseCode();
	        if(code==200)  
           {  
	        	BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        	String result=rd.readLine();
	        	rd.close();
	        	if(result.equals("success"))
	        		return true; 
	        	else return false;
	         }  
	        else  return false;  
	    }

	private void forget_company() {
				
	}

	private void forget_person() {
		
		
	}

	private void registerCompany() {
		
		Intent intent = new Intent(this, RegisterPhoneActivity.class);
		intent.putExtra("register_type", "company");
		startActivity(intent);
		
	}

	private void registerPerson() {
		
		Intent intent = new Intent(this, RegisterPhoneActivity.class);  
		intent.putExtra("register_type", "person");
		startActivity(intent);
	}

	private void Login() {
		
		mDialog = new ProgressDialog(LoginActivity.this);  
        mDialog.setTitle("登陆");  
        mDialog.setMessage("正在登陆，请稍后...");  
        mDialog.show();  
        Thread loginThread = new Thread(new LoginThread());
        loginThread.start();
		
	}
}
