package com.carpool;

import com.carpool.data.SharedData;
import com.carpool.thread.GetCarInfoThread;
import com.carpool.thread.GetProfileInfoThread;
import com.carpool.thread.GetCarInfoThread.GetCarInfoThreadInterface;
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

public class MyCarInfo extends BaseActivity implements GetCarInfoThreadInterface {

	private static String TAG="MyCar";

	@Override
	public int getLayoutXML() {
		return R.layout.my_car_info;
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
		btnNext.setText("Edit Car");
		btnNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(MyCarInfo.this,EditCar.class);
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
		new GetCarInfoThread(MyCarInfo.this).start();
	}

	private final static int CAR_DATA_TRUE=1;
	private final static int CAR_DATA_FALSE=2;
	private final static int CAR_ERROR=3;

	private Handler mHandler = new Handler() 
	{
		@Override
		public void handleMessage(Message msg) 
		{
			hideWait();
			switch (msg.what)
			{
			case CAR_DATA_TRUE:
			{
				TextView CarNo=(TextView)findViewById(R.id.txCarNo);
				TextView CarModel=(TextView)findViewById(R.id.txCarModel);
				TextView Capacity=(TextView)findViewById(R.id.txCapacity);
				TextView Confort=(TextView)findViewById(R.id.txComfort);				
				TextView Insurence=(TextView)findViewById(R.id.txInsurance);
				CarNo.setText(SharedData.mCarData.car_no);
				CarModel.setText(SharedData.mCarData.model);
				Capacity.setText(String.valueOf(SharedData.mCarData.seats));
				Confort.setText(SharedData.mCarData.comfort);
				if(SharedData.mCarData.insurance_yes1_no0 == 1)
					Insurence.setText("Yes");
				else
					Insurence.setText("No");
						
			}
			break;
			case CAR_DATA_FALSE:
			{
				final Builder builder = new AlertDialog.Builder(MyCarInfo.this);
				builder.setTitle("Alert!");
				builder.setMessage(msg.obj.toString());
				builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//finish();
					}
				});
				builder.setCancelable(false);
				builder.show();
			}
			break;
			case CAR_ERROR:
			{
				final Builder builder = new AlertDialog.Builder(MyCarInfo.this);
				builder.setTitle("Alert!");
				builder.setMessage("App Server not rechable.");
				builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//finish();
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
	public void onGetCarInfoThreadDataReturned(Boolean isSuccess, String msg) {
		// TODO Auto-generated method stub
		if(isSuccess)
			mHandler.sendMessage(mHandler.obtainMessage(CAR_DATA_TRUE,null));
		else
			mHandler.sendMessage(mHandler.obtainMessage(CAR_DATA_FALSE,msg));
	}

	@Override
	public void onGetCarInfoThreadErrorReturned() {
		// TODO Auto-generated method stub
		mHandler.sendMessage(mHandler.obtainMessage(CAR_ERROR, null));
	}
    
}
