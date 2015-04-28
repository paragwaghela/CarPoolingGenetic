package com.carpool;

import com.carpool.data.SharedData;
import com.carpool.thread.GetDriverReqInfoThread;
import com.carpool.thread.GetDriverReqInfoThread.GetDriverReqInfoThreadInterface;
import com.carpool.utils.BaseActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DriverReqDetail extends  BaseActivity implements GetDriverReqInfoThreadInterface{
	public int getLayoutXML() {
		return R.layout.driver_req_detail;
	}
	 String request_id="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b= getIntent().getExtras();
        request_id=b.getString("request_id");
       // setContentView(R.layout.driver_req_detail);
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
    }
    @Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		showWait();
		new GetDriverReqInfoThread(DriverReqDetail.this,request_id).start();
	}

	private final static int DRI_REQ_DATA_TRUE=1;
	private final static int DRI_REQ_DATA_FALSE=2;
	private final static int DRI_REQ_ERROR=3;

	private Handler mHandler = new Handler() 
	{
		@Override
		public void handleMessage(Message msg) 
		{
			hideWait();
			switch (msg.what)
			{
			case DRI_REQ_DATA_TRUE:
			{
				/*TextView source=(TextView)findViewById(R.id.textdSource);
				TextView destination=(TextView)findViewById(R.id.textdDestination);
				TextView pickUpTime=(TextView)findViewById(R.id.editdPickTime);
				TextView status=(TextView)findViewById(R.id.editdStatus);	*/	
				//TextView driverId=(TextView)findViewById(R.id.editdDrivId);		
				TextView driverName=(TextView)findViewById(R.id.editdDrivName);
				TextView driverContacNo=(TextView)findViewById(R.id.editdDrivCont);
				TextView carNo=(TextView)findViewById(R.id.editdCarNo);
				TextView carModel=(TextView)findViewById(R.id.editdCarModel);
				
				/*source.setText(SharedData.mDrivReqDetailData.source);
				destination.setText(SharedData.mDrivReqDetailData.destination);
				pickUpTime.setText(SharedData.mDrivReqDetailData.pickuptime);
				status.setText(SharedData.mDrivReqDetailData.status);*/
				//driverName.setText(SharedData.mDrivReqDetailData.driver_id);
				
				driverName.setText(SharedData.mDrivReqDetailData.driver_name);
				driverContacNo.setText(SharedData.mDrivReqDetailData.driver_contact);
				carNo.setText(SharedData.mDrivReqDetailData.Car_no);
				carModel.setText(SharedData.mDrivReqDetailData.car_model);
				
				Button close = (Button)findViewById(R.id.btndClose);
				close.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i=new Intent(DriverReqDetail.this,FeedBack.class);
						i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						//i.putExtra("driver_id", driver_id);
						i.putExtra("passener_id", SharedData.mMySharedPref.getUserId());
						i.putExtra("driver_id", SharedData.mDrivReqDetailData.driver_id);
						i.putExtra("request_id",request_id );
						startActivity(i);
					}
				});
				
						
			}
			break;
			case DRI_REQ_DATA_FALSE:
			{
				final Builder builder = new AlertDialog.Builder(DriverReqDetail.this);
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
			case DRI_REQ_ERROR:
			{
				final Builder builder = new AlertDialog.Builder(DriverReqDetail.this);
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
	public void onGetDriverReqInfoThreadDataReturned(Boolean isSuccess, String msg) {
		// TODO Auto-generated method stub
		if(isSuccess)
			mHandler.sendMessage(mHandler.obtainMessage(DRI_REQ_DATA_TRUE,null));
		else
			mHandler.sendMessage(mHandler.obtainMessage(DRI_REQ_DATA_FALSE,msg));
	}

	@Override
	public void onGetDriverReqInfoThreadErrorReturned() {
		// TODO Auto-generated method stub
		mHandler.sendMessage(mHandler.obtainMessage(DRI_REQ_ERROR, null));
	}
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.driver_req_detail, menu);
        return true;
    }

    
}

