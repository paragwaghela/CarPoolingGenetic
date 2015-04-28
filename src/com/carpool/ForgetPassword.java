package com.carpool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.carpool.utils.BaseActivity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ForgetPassword extends BaseActivity 
{

	private static String TAG="ForgetPassword";
	EditText etEmail;

	@Override
	public int getLayoutXML() {
		return R.layout.forget_password;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Button btnBack=(Button)findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		Button btnNext=(Button)findViewById(R.id.btnNext);
		btnNext.setVisibility(View.GONE);

		Button btnSubmit=(Button)findViewById(R.id.btnSubmit);
		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				etEmail=(EditText)findViewById(R.id.etMail);
				String email="";
				email=etEmail.getText().toString();
				
				/////////////////////////////////////
				finish();
				/////////////////////////////////////
//				if(email.equals(""))
//				{
//					ShowSimpleDialog("Alert!", "Please Enter Email-Id.");
//					etEmail.setText("");
//					etEmail.requestFocus();
//				}
//
//				else
//				{
//					Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");
//					Matcher matcher = pattern.matcher(email);
//					if(!matcher.matches())
//					{
//						ShowSimpleDialog("Alert!", "Please Enter Valid Username.");
//						etEmail.setText("");
//						etEmail.requestFocus();
//					}
//					else
//					{
////						showWait();
////						new ForgetPasswordThread(ForgetPassword.this, email.toLowerCase()).start();		
//					}
//				}

			}
		});
	}
	private final static int FORGOTPWD_DATA_TRUE=1;
	private final static int FORGOTPWD_DATA_FALSE=2;
	private final static int FORGOTPWD_ERROR=3;

	private final Handler mHandler=new Handler()
	{
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case FORGOTPWD_DATA_TRUE:
			{
				hideWait();
				final Builder builder = new AlertDialog.Builder(ForgetPassword.this);
				builder.setTitle("Alert!");
				builder.setMessage("Please Check your Mails for new password.");
				builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				});
				builder.setCancelable(false);
				builder.show();
			}
			break;
			case FORGOTPWD_DATA_FALSE:
			{
				hideWait();
				ShowSimpleDialog("Alert!",msg.obj.toString());
			}
			break;

			case FORGOTPWD_ERROR:
			{
				hideWait();
				ShowSimpleDialog("Alert!", "App Server not reachable.");
			}
			break;
			}
		}
	};

//	@Override
//	public void onForgetPasswordThreadDataReturned(boolean isSucess,String msg) {
//		if(isSucess==true)
//			mHandler.sendMessage(mHandler.obtainMessage(FORGOTPWD_DATA_TRUE,msg));
//		else
//			mHandler.sendMessage(mHandler.obtainMessage(FORGOTPWD_DATA_FALSE,msg));
//	}
//
//	@Override
//	public void onForgetPasswordThreadErrorReturned() {
//
//		mHandler.sendMessage(mHandler.obtainMessage(FORGOTPWD_ERROR,null));
//	}
}
