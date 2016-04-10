package com.job.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
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
import com.job.activity.CompanyBindPhoneActivity.SubmitThread;
import com.job.base.BaseActivity;
import com.job.util.CropHelper;
import com.job.util.OSUtils;

public class CompanyMessageActivity extends BaseActivity implements
		OnClickListener {

	private RelativeLayout change_pwd, binding_tel,
			binding_email;
	private ImageView btn_user;
	private TextView company_name,bindPhone,bindEmail;
	ImageView camera_btn;
	ImageView gallery_btn;
	Button cancel_btn;
	private Dialog alertDialog;
	private CropHelper crophelper;
	private String result="";
	private String getPhone,getEmail,getCompanyView,headPicture;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_message);
		init();
		Thread thread=new Thread(new InitialThread());
		thread.start();
	}

	private void init() {

		crophelper = new CropHelper(this, OSUtils.getSdCardDirectory()
				+ "/head.png");
		company_name = (TextView) findViewById(R.id.comapny_name);
		
		change_pwd = (RelativeLayout) findViewById(R.id.change_password);
		binding_tel = (RelativeLayout) findViewById(R.id.binding_phone_number);
		binding_email = (RelativeLayout) findViewById(R.id.binding_email);
		btn_user = (ImageView) findViewById(R.id.dcn_user_image);
		
		bindPhone=(TextView)findViewById(R.id.dcn_phone_bound);
		bindEmail=(TextView)findViewById(R.id.dcn_question_set);
		
		btn_user.setOnClickListener(this);
		change_pwd.setOnClickListener(this);
		binding_email.setOnClickListener(this);
		binding_tel.setOnClickListener(this);
		company_name.setText(LoginActivity.name);
	}

	Handler handler = new Handler()  
    {  
        public void handleMessage(Message msg)  
        {  
            switch(msg.what)  
            {  
            case 0:  
            	Toast.makeText(getApplicationContext(), "网络错误", Toast.LENGTH_SHORT).show();
            	break;
            case 1:
            	try {
					JSONObject view=new JSONObject(result);
					getPhone=view.getString("phoneNumber");
					bindPhone.setText(getPhone);
					getEmail=view.getString("email");
					bindEmail.setText(getEmail);
					getCompanyView=view.getString("companyView");
					if(view.getString("headPicture").equals(""))
						btn_user.setImageResource(R.drawable.user);
					else
					{
						byte[] bytes = Base64.decode(view.getString("headPicture"), Base64.DEFAULT);
						btn_user.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	break;
            case 4:
            	Toast.makeText(getApplicationContext(), "头像上传成功", Toast.LENGTH_SHORT).show();
            	break;
            case 6:
            	Toast.makeText(getApplicationContext(), "头像上传失败", Toast.LENGTH_SHORT).show();
            }   
        }  
    }; 
    
    class InitialThread implements Runnable
    {

    	 @Override  
         public void run() {  
         	 StringBuilder url=new StringBuilder(LoginActivity.URL+"InfoManager");
         	 String type="e";//公司
         	 url.append("?");
         	 url.append("type=");
         	 url.append(type);
         	 url.append("&");
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
 		 	        	String str;
 		 	        	while((str=rd.readLine())!=null)
 		 	        		result+=str;
 		 	        	rd.close();
 		 	        	if(result.equals(""))
 		 	        	{
 		 	        		msg.what=0;
 		 	            	handler.sendMessage(msg);	 
 		 	        	}
 		 	        	else{
 		 	        		msg.what=1;
 		 	            	handler.sendMessage(msg);
 		 	        	}
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
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.change_password:
			edit_pwd();
			break;

		case R.id.binding_phone_number:
			bind_phone();
			break;

		case R.id.binding_email:
			bind_email();
			break;

		case R.id.dcn_user_image:
			choose_img();
			break;
		default:
			break;
		}

	}

	private void choose_img() {
		if (alertDialog == null) {
			alertDialog = new Dialog(this, R.style.MyDialog);

			alertDialog.setContentView(R.layout.dialog_head);
			alertDialog.setTitle("头像选择");
			alertDialog.setCanceledOnTouchOutside(true);
			camera_btn = (ImageView) alertDialog.findViewById(R.id.camera_btn);
			gallery_btn = (ImageView) alertDialog.findViewById(R.id.gallery_btn);
			cancel_btn = (Button) alertDialog.findViewById(R.id.select_cancel);
			cancel_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					alertDialog.dismiss();
				}
			});
			camera_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (alertDialog != null) {
						alertDialog.dismiss();
						crophelper.startCamera();
					}

				}
			});

			gallery_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (alertDialog != null) {
						alertDialog.dismiss();
						crophelper.startAlbum();

					}

				}
			});

		}

		alertDialog.show();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.e("onActivityResult", requestCode + "**" + resultCode);
		if (requestCode == RESULT_CANCELED) {
			return;
		} else {
			switch (requestCode) {
			case CropHelper.HEAD_FROM_ALBUM:
				crophelper.getDataFromAlbum(data);
				Log.e("onActivityResult", "接收到图库图片");
				break;
			case CropHelper.HEAD_FROM_CAMERA:
				crophelper.getDataFromCamera(data);
				Log.e("onActivityResult", "接收到拍照图片");
				break;
			case CropHelper.HEAD_SAVE_PHOTO:
				if (data != null && data.getParcelableExtra("data") != null) {
					btn_user.setImageBitmap((Bitmap) data
							.getParcelableExtra("data"));
					crophelper.savePhoto(data, OSUtils.getSdCardDirectory()
							+ "/myHead.png");
					edit_headPicture((Bitmap) data.getParcelableExtra("data"));//上传头像
				}
				break;
			default:
				break;
			}
		}

	}

	private void bind_email() {
		Intent intent=new Intent();
		intent.setClass(CompanyMessageActivity.this, CompanyBindEmailActivity.class);
		intent.putExtra("email", getEmail);
		intent.putExtra("P_E", "E");
		startActivity(intent);

	}

	private void bind_phone() {
		Intent intent=new Intent();
		intent.setClass(CompanyMessageActivity.this, CompanyBindPhoneActivity.class);
		intent.putExtra("phone", getPhone);
		intent.putExtra("P_E", "E");
		startActivity(intent);
	}

	private void edit_pwd() {
		Intent intent=new Intent();
		intent.setClass(CompanyMessageActivity.this, CompanyChangePassWordActivity.class);
		intent.putExtra("P_E", "E");
		startActivity(intent);
	}
	
	private void edit_headPicture(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();//将Bitmap转成Byte[]
		bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);//压缩
		headPicture =Base64.encodeToString(baos.toByteArray(),Base64.DEFAULT);//加密转换成String
		int n=headPicture.length();
		Thread submit=new Thread(new SubmitThread());
		submit.start();
	}

    class SubmitThread implements Runnable
    {
    	Message msg = handler.obtainMessage();
		@Override
		public void run() {
			// TODO Auto-generated method stub
			JSONObject object=new JSONObject();
			try {
				object.put("type", false);//非密码修改
				object.put("P_E", "E");//公司
				object.put("which", "headPicture");//修改头像
				object.put("name", LoginActivity.name);
				object.put("headPicture", headPicture);
				msg.what=Server(object);
				handler.sendMessage(msg);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
    public int Server(JSONObject object)
    {
    	String path=LoginActivity.URL+"SetInfoManager";
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
