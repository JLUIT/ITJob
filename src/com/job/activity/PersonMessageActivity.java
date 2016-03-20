package com.job.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.job.R;
import com.job.base.BaseActivity;
import com.job.util.CropHelper;
import com.job.util.OSUtils;

public class PersonMessageActivity extends BaseActivity implements
		OnClickListener {

	private RelativeLayout job, school, profession, change_pwd, binding_tel,
			binding_email;
	private ImageView btn_user;
	private TextView person_name;
	private TextView my_job, my_school, my_pro, tel, email;
	ImageView camera_btn;
	ImageView gallery_btn;
	Button cancel_btn;
	private Dialog alertDialog;
	private CropHelper crophelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_message);
		init();
	}

	private void init() {
		crophelper = new CropHelper(this, OSUtils.getSdCardDirectory()
				+ "/head.png");
		job = (RelativeLayout) findViewById(R.id.apply_job);
		school = (RelativeLayout) findViewById(R.id.inapply_job);
		profession = (RelativeLayout) findViewById(R.id.resume);
		change_pwd = (RelativeLayout) findViewById(R.id.change_password);
		binding_email = (RelativeLayout) findViewById(R.id.change_email);
		binding_tel = (RelativeLayout) findViewById(R.id.change_phone);
		btn_user = (ImageView) findViewById(R.id.user_image);
		person_name = (TextView) findViewById(R.id.my_name);
		my_job = (TextView) findViewById(R.id.my_job);
		my_school = (TextView) findViewById(R.id.my_school);
		my_pro = (TextView) findViewById(R.id.my_pro);
		tel = (TextView) findViewById(R.id.phone);
		email = (TextView) findViewById(R.id.email_address);
		job.setOnClickListener(this);
		school.setOnClickListener(this);
		profession.setOnClickListener(this);
		change_pwd.setOnClickListener(this);
		binding_email.setOnClickListener(this);
		binding_tel.setOnClickListener(this);
		btn_user.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.inapply_job:
			choose_college();
			break;
		case R.id.apply_job:
			chosse_job();
			break;
		case R.id.resume:
			choose_pro();
			break;
		case R.id.change_password:
			change_pwd();
			break;
		case R.id.change_email:
			change_email();
			break;
		case R.id.change_phone:
			change_phone();
			break;
		case R.id.user_image:
			choose_img();
			break;
		default:
			break;

		}
	}

	private void choose_pro() {
		// TODO Auto-generated method stub
		intent2Activity(ChooseProActivity.class);
	}

	private void choose_img() {
		if (alertDialog == null) {
			alertDialog = new Dialog(this, R.style.MyDialog);

			alertDialog.setContentView(R.layout.dialog_head);
			alertDialog.setTitle("头像选择");
			alertDialog.setCanceledOnTouchOutside(true);
			camera_btn = (ImageView) alertDialog.findViewById(R.id.camera_btn);
			gallery_btn = (ImageView) alertDialog
					.findViewById(R.id.gallery_btn);
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
				}
				break;
			default:
				break;
			}
		}

	}

	private void change_phone() {
		intent2Activity(CompanyBindPhoneActivity.class);

	}

	private void change_email() {
		intent2Activity(CompanyBindEmailActivity.class);

	}

	private void change_pwd() {
		intent2Activity(CompanyChangePassWordActivity.class);
	}

	private void chosse_job() {
		intent2Activity(JobNameActivity.class);
	}

	private void choose_college() {
		intent2Activity(CollegeChooseActivity.class);

	}

}
