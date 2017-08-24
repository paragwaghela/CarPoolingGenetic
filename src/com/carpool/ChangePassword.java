package com.carpool;

import com.carpool.data.SharedData;
import com.carpool.thread.ChangePasswordThread;
import com.carpool.thread.ChangePasswordThread.ChangePasswordThreadInterface;
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

public class ChangePassword extends BaseActivity implements ChangePasswordThreadInterface
{

	private static String TAG="ChangePassword";

	String currentPass="",newPass="",confirmPass="";

	@Override
	public int getLayoutXML() {
		return R.layout.change_password;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Button btnBack=(Button)findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		Button btnNext=(Button)findViewById(R.id.btnNext);
		btnNext.setVisibility(View.GONE);

		Button btnSave=(Button)findViewById(R.id.btnSave);
		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText etCurrentPass=(EditText)findViewById(R.id.etCurrentPassword);
				EditText etNewPass=(EditText)findViewById(R.id.etNewPassword);
				EditText etConfirmPass=(EditText)findViewById(R.id.etConfirmPassword);

				currentPass=etCurrentPass.getText().toString();
				newPass=etNewPass.getText().toString();
				confirmPass=etConfirmPass.getText().toString();

				if(currentPass.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please enter the current password.");
					etCurrentPass.setText("");
					etCurrentPass.requestFocus();
				}
				else if(newPass.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please enter the new password.");
					etNewPass.setText("");
					etNewPass.requestFocus();
				}
				else if(confirmPass.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please confirm your new password.");
					etConfirmPass.setText("");
					etConfirmPass.requestFocus();
				}
				else if(!currentPass.equals(SharedData.mMySharedPref.getPassword()))
				{
					ShowSimpleDialog("Alert!", "Please enter the correct current password.");
					etConfirmPass.setText("");
					etConfirmPass.requestFocus();
				}
				else if(!newPass.equals(confirmPass))
				{
					ShowSimpleDialog("Alert!", "New password do not matches to confirm password. Please re-enter.");
					etNewPass.setText("");
					etConfirmPass.setText("");
					etNewPass.requestFocus();
				}
				else
				{
					showWait();
					new ChangePasswordThread(ChangePassword.this,newPass).start();
				}
			}
		});

		Button btnCancel=(Button)findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}

	private final static int PASS_CHANGE_DATA_TRUE=1;
	private final static int PASS_CHANGE_DATA_FALSE=2;
	private final static int LOGIN_ERROR=3;

	private final Handler mHandler=new Handler()
	{
		public void handleMessage(android.os.Message msg) {
			hideWait();
			switch (msg.what) {
			case PASS_CHANGE_DATA_TRUE:
			{				
				SharedData.mMySharedPref.setPassword(newPass);
				
				final Builder builder = new AlertDialog.Builder(ChangePassword.this);
				builder.setTitle("Alert!");
				builder.setMessage(msg.obj.toString());
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
			case PASS_CHANGE_DATA_FALSE:
			{
				ShowSimpleDialog("Alert!", msg.obj.toString());
			}
			break;
			case LOGIN_ERROR:
			{
				ShowSimpleDialog("Alert!", "App Server not reachable.");
			}
			break;
			}
		}
	};

	@Override
	public void onChangePasswordThreadDataReturned(Boolean isSuccess, String msg) {
		// TODO Auto-generated method stub

		if(isSuccess)
			mHandler.sendMessage(mHandler.obtainMessage(PASS_CHANGE_DATA_TRUE,msg));
		else
			mHandler.sendMessage(mHandler.obtainMessage(PASS_CHANGE_DATA_FALSE,msg));
	}

	@Override
	public void onChangePasswordThreadErrorReturned() {
		// TODO Auto-generated method stub
		mHandler.sendMessage(mHandler.obtainMessage(LOGIN_ERROR,null));
	}
}

