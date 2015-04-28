package com.carpool.utils;

import com.carpool.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

public class BaseActivity extends Activity
{

	private final String TAG = "BaseActivity";

	private ProgressDialog mPD;
	protected Activity mActivity;
	protected Context mContext;
	private int mLayoutId;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		mActivity = this;
		mContext = this;		
		mLayoutId = getLayoutXML();
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		
		if (mLayoutId != -1)
		{
			setContentView(mLayoutId);
		}
				
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.header);
	}

	protected void ShowSimpleDialog(final String title, final String message) 
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		if (title != null) {
			builder.setTitle(title);
		}
		//builder.setIcon(R.drawable.logo);
		builder.setMessage(message);
		
		builder.setPositiveButton("OK", null);
		builder.create().show();
	}

	public int getLayoutXML()
	{
		return -1;
	}

	public void showWait(String txt) 
	{
		Log.i("ShowWait Called with ",txt);
		if (mPD != null) 
		{
			mPD.dismiss();
		}
		mPD = new ProgressDialog(this);
		mPD.setCancelable(false);
		mPD.setCanceledOnTouchOutside(false);
		mPD.setMessage(txt);
		mPD.show();

	}

	public void showWait()
	{
		showWait("Please wait...");
		Log.i("ShowWait Called with ","Default");
	}

	public void hideWait() 
	{
		Log.i("HideWait","called");
		if (mPD != null && mPD.isShowing())
		{
			Log.d(TAG, "hideWait cancelling...");
			try
			{
				mPD.cancel();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		} 
		else
		{
			Log.d(TAG, "hideWait mPD failed to hide");
		}
	}

	@Override
	public void onResume() 
	{
		super.onResume();
	}

	@Override
	protected void onPause()
	{
		Log.d(TAG, "Came to onpause");
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
