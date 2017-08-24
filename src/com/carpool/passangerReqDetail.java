package com.carpool;

import com.carpool.data.SharedData;
import com.carpool.thread.GetPassengerReqInfoThread;
import com.carpool.thread.GetPassengerReqInfoThread.GetPassengerReqInfoThreadInterface;
import com.carpool.utils.BaseActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class passangerReqDetail extends BaseActivity implements GetPassengerReqInfoThreadInterface{

	@Override
	public int getLayoutXML() {
		return R.layout.passanger_req_detail;
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
		btnNext.setVisibility(View.GONE);
    }

    @Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		showWait();
		new GetPassengerReqInfoThread(passangerReqDetail.this).start();
	}

	private final static int PASSE_DATA_TRUE=1;
	private final static int PASSE_DATA_FALSE=2;
	private final static int PASSE_ERROR=3;

	private Handler mHandler = new Handler() 
	{
		@Override
		public void handleMessage(Message msg) 
		{
			hideWait();
			switch (msg.what)
			{
			case PASSE_DATA_TRUE:
			{

				TextView Source=(TextView)findViewById(R.id.textPSource);
				TextView Destination=(TextView)findViewById(R.id.editpDestination);
				TextView PickTime=(TextView)findViewById(R.id.editpPickTime);
				TextView Status=(TextView)findViewById(R.id.editpStatus);				
				TextView PassName=(TextView)findViewById(R.id.editpPassName);
				TextView PassCont=(TextView)findViewById(R.id.editpPassCont);
				Source.setText(SharedData.mPassReqDetailData.source);
				Destination.setText(SharedData.mPassReqDetailData.dest);
				PickTime.setText(SharedData.mPassReqDetailData.pickuptime);
				Status.setText(SharedData.mPassReqDetailData.status);
				PassName.setText(SharedData.mPassReqDetailData.passName);
				PassCont.setText(SharedData.mPassReqDetailData.contact);
				Button close = (Button)findViewById(R.id.btnClose);
				close.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
					}
				});
						
			}
			break;
			case PASSE_DATA_FALSE:
			{
				final Builder builder = new AlertDialog.Builder(passangerReqDetail.this);
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
			case PASSE_ERROR:
			{
				final Builder builder = new AlertDialog.Builder(passangerReqDetail.this);
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
	public void onGetPassengerReqInfoThreadDataReturned(Boolean isSuccess, String msg) {
		// TODO Auto-generated method stub
		if(isSuccess)
			mHandler.sendMessage(mHandler.obtainMessage(PASSE_DATA_TRUE,null));
		else
			mHandler.sendMessage(mHandler.obtainMessage(PASSE_DATA_FALSE,msg));
	}

	@Override
	public void onGetPassengerReqInfoThreadErrorReturned() {
		// TODO Auto-generated method stub
		mHandler.sendMessage(mHandler.obtainMessage(PASSE_ERROR, null));
	}
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.passanger_req_detail, menu);
        return true;
    }

    
}
