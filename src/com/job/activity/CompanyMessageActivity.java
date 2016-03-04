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

public class CompanyMessageActivity extends BaseActivity implements
		OnClickListener {

	private RelativeLayout message, record, change_pwd, binding_tel,
			binding_email;
	private ImageView btn_user;
	private TextView company_name;
	ImageView camera_btn;
	ImageView gallery_btn;
	Button cancel_btn;
	private Dialog alertDialog;
	private CropHelper crophelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_message);
		init();

	}

	private void init() {

		crophelper = new CropHelper(this, OSUtils.getSdCardDirectory()
				+ "/head.png");
		company_name = (TextView) findViewById(R.id.comapny_name);
		message = (RelativeLayout) findViewById(R.id.message_center);
		record = (RelativeLayout) findViewById(R.id.conduct_record);
		change_pwd = (RelativeLayout) findViewById(R.id.change_password);
		binding_tel = (RelativeLayout) findViewById(R.id.binding_phone_number);
		binding_email = (RelativeLayout) findViewById(R.id.binding_email);
		btn_user = (ImageView) findViewById(R.id.dcn_user_image);

		btn_user.setOnClickListener(this);
		message.setOnClickListener(this);
		record.setOnClickListener(this);
		change_pwd.setOnClickListener(this);
		binding_email.setOnClickListener(this);
		binding_tel.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.message_center:
			find_message();
			break;

		case R.id.conduct_record:
			find_record();
			break;

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
				}
				break;
			default:
				break;
			}
		}

	}

	private void bind_email() {

		intent2Activity(CompanyBindEmailActivity.class);

	}

	private void bind_phone() {

		intent2Activity(CompanyBindPhoneActivity.class);
	}

	private void edit_pwd() {

		intent2Activity(CompanyChangePassWordActivity.class);
	}

	private void find_record() {

	}

	private void find_message() {

	}

}
