package com.carpool;

import com.carpool.data.SharedData;
import com.carpool.thread.GetPassangerProfileInfoThread;
import com.carpool.thread.GetPassangerProfileInfoThread.GetPassangerProfileInfoThreadInterface;
import com.carpool.utils.BaseActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class PassengerProfile extends BaseActivity implements GetPassangerProfileInfoThreadInterface{

	public int getLayoutXML() {
		return R.layout.passanger_profile;
	}
    @Override
    public void onCreate(Bundle savedInstanceState) {
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
		btnNext.setText("Edit Profile");
		btnNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(PassengerProfile.this,EditPassangerProfile.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});
    }
    @Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		showWait();
		new GetPassangerProfileInfoThread(PassengerProfile.this).start();
	}
    private final static int PROFILE_DATA_TRUE=1;
	private final static int PROFILE_DATA_FALSE=2;
	private final static int PROFILE_ERROR=3;

	private Handler mHandler = new Handler() 
	{
		@Override
		public void handleMessage(Message msg) 
		{
			hideWait();
			switch (msg.what)
			{
			case PROFILE_DATA_TRUE:
			{
				TextView tvName=(TextView)findViewById(R.id.tvName);
				TextView tvEMail=(TextView)findViewById(R.id.tvEMail);
				TextView tvContact=(TextView)findViewById(R.id.tvContact);
				TextView tvGender=(TextView)findViewById(R.id.tvGender);				
				TextView editAddr=(TextView)findViewById(R.id.editAddr);
				TextView tvAadharId=(TextView)findViewById(R.id.tvAadharId);
				
				tvName.setText(SharedData.mUserData.fname+" "+SharedData.mUserData.lname);
				tvEMail.setText("Email : "+SharedData.mUserData.email);
				tvContact.setText("Mob. +91"+SharedData.mUserData.contact);
				tvGender.setText(SharedData.mUserData.gender);
				editAddr.setText(SharedData.mUserData.addr);
				tvAadharId.setText(SharedData.mUserData.adharId);
					
			}
			break;
			case PROFILE_DATA_FALSE:
			{
				final Builder builder = new AlertDialog.Builder(PassengerProfile.this);
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
			case PROFILE_ERROR:
			{
				final Builder builder = new AlertDialog.Builder(PassengerProfile.this);
				builder.setTitle("Alert!");
				builder.setMessage("App Server not rechable.");
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
			}
		}
	};


	@Override
	public void onGetPassangerProfileInfoThreadDataReturned(Boolean isSuccess, String msg) {
		// TODO Auto-generated method stub
		if(isSuccess)
			mHandler.sendMessage(mHandler.obtainMessage(PROFILE_DATA_TRUE,null));
		else
			mHandler.sendMessage(mHandler.obtainMessage(PROFILE_DATA_FALSE,msg));
	}

	@Override
	public void onGetPassangerProfileInfoThreadErrorReturned() {
		// TODO Auto-generated method stub
		mHandler.sendMessage(mHandler.obtainMessage(PROFILE_ERROR, null));
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.passanger_profile, menu);
        return true;
    }

    
}
