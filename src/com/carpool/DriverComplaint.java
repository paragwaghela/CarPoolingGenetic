package com.carpool;

import com.carpool.thread.DriverComplaintThread;
import com.carpool.thread.DriverComplaintThread.DriverComplaintThreadInterface;
import com.carpool.utils.BaseActivity;

import android.os.Bundle;
import android.os.Handler;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class DriverComplaint extends  BaseActivity implements DriverComplaintThreadInterface{

	@Override
	public int getLayoutXML() {
		return R.layout.driver_complaint;
	}
	String PassangerName="",Complaint="";
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
		Button btnCancel=(Button)findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(DriverComplaint.this,UserHome.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				finish();
			}
			
		});
		
		Button btnSend=(Button)findViewById(R.id.btnSend);
		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				EditText etCurrentPass=(EditText)findViewById(R.id.etPassangerName);
				EditText etNewPass=(EditText)findViewById(R.id.etComplaint);
				//EditText etConfirmPass=(EditText)findViewById(R.id.etConfirmPassword);

				PassangerName=etCurrentPass.getText().toString();
				Complaint=etNewPass.getText().toString();
				//confirmPass=etConfirmPass.getText().toString();

				if(PassangerName.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please enter Passanger Name.");
					etCurrentPass.setText("");
					etCurrentPass.requestFocus();
				}
				else if(Complaint.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please Enter Complaint.");
					etNewPass.setText("");
					etNewPass.requestFocus();
				}
				/*else if(confirmPass.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please confirm your new password.");
					etConfirmPass.setText("");
					etConfirmPass.requestFocus();
				}*/
				/*else if(!currentPass.equals(SharedData.mMySharedPref.getPassword()))
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
				}*/
				else
				{
					showWait();
					new DriverComplaintThread(DriverComplaint.this,PassangerName,Complaint).start();
				}
			}
		});
    }

	
	private final static int PASS_COMM_DATA_TRUE=1;
	private final static int PASS_COMM_DATA_FALSE=2;
	private final static int LOGIN_ERROR=3;

	private final Handler mHandler=new Handler()
	{
		public void handleMessage(android.os.Message msg) {
			hideWait();
			switch (msg.what) {
			case PASS_COMM_DATA_TRUE:
			{				
				//SharedData.mMySharedPref.setPassword(newPass);
				
				final Builder builder = new AlertDialog.Builder(DriverComplaint.this);
				builder.setTitle("Alert!");
				builder.setMessage(msg.obj.toString());
				builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent i=new Intent(DriverComplaint.this,UserHome.class);
						i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(i);
						finish();
						
					}
				});
				builder.setCancelable(false);
				builder.show();
			}
			break;
			case PASS_COMM_DATA_FALSE:
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
	public void onDriverComplaintThreadDataReturned(Boolean isSuccess, String msg) {
		// TODO Auto-generated method stub
		if(isSuccess)
			mHandler.sendMessage(mHandler.obtainMessage(PASS_COMM_DATA_TRUE,msg));
		else
			mHandler.sendMessage(mHandler.obtainMessage(PASS_COMM_DATA_FALSE,msg));
	}

	@Override
	public void onDriverComplaintThreadErrorReturned() {
		// TODO Auto-generated method stub
		mHandler.sendMessage(mHandler.obtainMessage(LOGIN_ERROR,null));
	}

    
}
