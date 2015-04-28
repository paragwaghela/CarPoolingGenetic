package com.carpool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.carpool.data.SharedData;
import com.carpool.thread.GetProfileInfoThread;
import com.carpool.thread.SourceDestinationThread;
import com.carpool.thread.GetProfileInfoThread.GetProfileInfoThreadInterface;
import com.carpool.thread.SourceDestinationThread.SourceDestinationThreadInterface;
import com.carpool.utils.BaseActivity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MyProfile extends BaseActivity implements GetProfileInfoThreadInterface ,SourceDestinationThreadInterface
{

	private static String TAG="MyProfile";
	ImageView imgPhoto;
	TextView tvLicenseNo;
	@Override
	public int getLayoutXML() {
		return R.layout.my_profile;
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
		btnNext.setText("Edit Profile");
		btnNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				/*Intent i=new Intent(MyProfile.this,EditProfile.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);*/
				//ShowSimpleDialog("Alert!", "Currently this functionality is unavailable.");
				showWait("Request sending...");
				new SourceDestinationThread(MyProfile.this).start();
			}
		});
		
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		showWait();
		new GetProfileInfoThread(MyProfile.this).start();
	}

	private final static int PROFILE_DATA_TRUE=1;
	private final static int PROFILE_DATA_FALSE=2;
	private final static int PROFILE_ERROR=3;
	private final static int SOURCE_DEST_DATA=4;
	private final static int SOURCE_DEST_ERROR=5;

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
				imgPhoto=(ImageView)findViewById(R.id.imgPic);
				tvLicenseNo=(TextView)findViewById(R.id.tvLicenseNo);
				TextView tvName=(TextView)findViewById(R.id.tvName);
				TextView tvEMail=(TextView)findViewById(R.id.tvEMail);
				TextView tvContact=(TextView)findViewById(R.id.tvContact);
				TextView tvGender=(TextView)findViewById(R.id.tvGender);				
				TextView editAddr=(TextView)findViewById(R.id.editAddr);
				TextView tvAadharId=(TextView)findViewById(R.id.tvAadharId);
				TextView tvLicenceNo=(TextView)findViewById(R.id.tvLicenseNo);
				TextView tvSource=(TextView)findViewById(R.id.tvSource);
				TextView tvDestination=(TextView)findViewById(R.id.tvDest);
				TextView tvSrcTime=(TextView)findViewById(R.id.tvSrcTime);
				TextView tvDestTime=(TextView)findViewById(R.id.tvDestTime);
				TextView tvConverse=(TextView)findViewById(R.id.tvConverse);
				TextView tvConvSrcTime=(TextView)findViewById(R.id.tvConvSrcTime);
				TextView tvConvDestTime=(TextView)findViewById(R.id.tvConvDestTime);
				//SharedData.imageLoader.DisplayImage(SharedData.mUserData.base_url+SharedData.mUserData.profilePic, imgPhoto);
				tvName.setText(SharedData.mDriverData.fname+" "+SharedData.mDriverData.lname);
				tvEMail.setText("Email : "+SharedData.mDriverData.email);
				tvContact.setText("Mob. +91"+SharedData.mDriverData.contact);
				tvGender.setText(SharedData.mDriverData.gender);
				editAddr.setText(SharedData.mDriverData.addr);
				tvAadharId.setText(SharedData.mDriverData.adharId);
				tvLicenceNo.setText(SharedData.mDriverData.licenseNo);
				
				tvSource.setText(SharedData.mDriverData.source);
				tvDestination.setText(SharedData.mDriverData.destination);	
				
				tvSrcTime.setText(SharedData.mDriverData.srcTime);	
				tvDestTime.setText(SharedData.mDriverData.destTime);	
				tvConverse.setText(SharedData.mDriverData.conversly);	
				tvConvSrcTime.setText(SharedData.mDriverData.conSrcTime);	
				tvConvDestTime.setText(SharedData.mDriverData.conDestTime);	
				
				BitmapFactory.Options options = new BitmapFactory.Options();
		        options = new BitmapFactory.Options();
		        options.inDither = false;
		        options.inPurgeable = true;
		        options.inInputShareable = true;
		        options.inTempStorage = new byte[1024 *32];
		       /* Bitmap bm =   BitmapFactory.decodeByteArray(imageAsBytes , 0, imageAsBytes.length, options);
		        imgPhoto.setImageBitmap(bm);*/
			}
			break;
			case PROFILE_DATA_FALSE:
			{
				final Builder builder = new AlertDialog.Builder(MyProfile.this);
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
				final Builder builder = new AlertDialog.Builder(MyProfile.this);
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
			case SOURCE_DEST_DATA:
			{
				Intent i=new Intent(MyProfile.this,EditProfile.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				
			}
				break;
				
			case SOURCE_DEST_ERROR:
			{
				ShowSimpleDialog("Alert!", "App Server not reachable.");
			}
				break;
			}
		}
	};


	@Override
	public void onGetProfileInfoThreadDataReturned(Boolean isSuccess, String msg) {
		// TODO Auto-generated method stub
		if(isSuccess)
			mHandler.sendMessage(mHandler.obtainMessage(PROFILE_DATA_TRUE,null));
		else
			mHandler.sendMessage(mHandler.obtainMessage(PROFILE_DATA_FALSE,msg));
	}

	@Override
	public void onGetProfileInfoThreadErrorReturned() {
		// TODO Auto-generated method stub
		mHandler.sendMessage(mHandler.obtainMessage(PROFILE_ERROR, null));
	}

	@Override
	public void onSourceDestinationThreadDataReturned() {
		// TODO Auto-generated method stub
		mHandler.sendMessage(mHandler.obtainMessage(SOURCE_DEST_DATA, null));
		
	}
	@Override
	public void onSourceDestinationThreadErrorReturned() {
		// TODO Auto-generated method stub
		mHandler.sendMessage(mHandler.obtainMessage(SOURCE_DEST_ERROR,null));
		
	}
}


