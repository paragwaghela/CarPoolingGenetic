package com.carpool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.carpool.data.SharedData;
import com.carpool.thread.UpdateCarThread;
import com.carpool.thread.UpdateCarThread.UpdateCarThreadInterface;
import com.carpool.utils.BaseActivity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class EditCar extends BaseActivity implements UpdateCarThreadInterface
{

	private static String TAG="EditCar";
	EditText etCarNo,etCarModel,etCapacity;
	Spinner spComfort,spInsurance;
	
	int type;

	@Override
	public int getLayoutXML() {
		return R.layout.add_car;
	}
	
	static int pos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		pos=getIntent().getIntExtra("position", 0);
		Log.d("position",""+pos);

		Button btnBack=(Button)findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		Button btnNext=(Button)findViewById(R.id.btnNext);
		btnNext.setVisibility(View.GONE);

		TextView tvTitle=(TextView)findViewById(R.id.txtTitle);
		tvTitle.setText("Edit Car");
		spComfort=(Spinner)findViewById(R.id.spComfort);
		
		String[] comfort={"AC","Non-AC"};		
		List<String> items1 = new ArrayList<String>(Arrays.asList(comfort));
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(EditCar.this, android.R.layout.simple_spinner_item, items1);
	    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spComfort.setAdapter(adapter1);
		
	    spInsurance=(Spinner)findViewById(R.id.spInsurance);
		
		String[] isInsured={"No","Yes"};
	    List<String> items2 = new ArrayList<String>(Arrays.asList(isInsured));
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(EditCar.this, android.R.layout.simple_spinner_item, items2);
	    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spInsurance.setAdapter(adapter2);
		etCarNo=(EditText)findViewById(R.id.etCarNo);
		etCarModel=(EditText)findViewById(R.id.etCarModel);
		etCapacity=(EditText)findViewById(R.id.etCapacity);
	
		etCarNo.setText(SharedData.mCarData.car_no);
		etCarModel.setText(SharedData.mCarData.model);
		etCapacity.setText(String.valueOf(SharedData.mCarData.seats));
		
		if(SharedData.mCarData.comfort.equals("AC"))
			spComfort.setSelection(0, true);
		else 
			spComfort.setSelection(1, true);
		
		if(SharedData.mCarData.insurance_yes1_no0 == 0)
			spInsurance.setSelection(0, true);
		else
			spInsurance.setSelection(1, true);
			
		Button btnSave=(Button)findViewById(R.id.btnSave);
		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				String carNo="",comfort="",insurance="";
				int seats = 0;
				carNo=etCarNo.getText().toString();
				String careModel="";
				careModel=etCarModel.getText().toString();
				comfort=spComfort.getSelectedItem().toString();
				insurance=spInsurance.getSelectedItem().toString();
				seats=Integer.parseInt(etCapacity.getText().toString());
				if(carNo.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please Enter Car No.");	
					etCarNo.setText("");
					etCarNo.requestFocus();
				}
				else if(seats==0)
				{
					ShowSimpleDialog("Alert!", "Please Enter Capacity.");
					etCarNo.setText("");
					etCarNo.requestFocus();
				}
				else if(careModel.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please Enter Model.");
					etCarModel.setText("");
					etCarModel.requestFocus();
				}				
				else
				{
					
					showWait();
					new UpdateCarThread(EditCar.this,carNo,careModel,comfort,seats,insurance).start();				
				}
			}
		});
		
		Button btnCancel=(Button)findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();			
			}
		});
		

	}
	
	private final static int CAR_TYPE_DATA_TRUE=1;
	private final static int CAR_TYPE_DATA_FALSE=2;
	private final static int CAR_TYPE_ERROR=3;
	
	private final Handler mHandler=new Handler()
	{
		public void handleMessage(android.os.Message msg) {
			hideWait();
			switch (msg.what) {
			case CAR_TYPE_DATA_TRUE:
			{
				Intent i=new Intent(EditCar.this,UserHome.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
			break;
			case CAR_TYPE_DATA_FALSE:
			{
				final Builder builder = new AlertDialog.Builder(EditCar.this);
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
			case CAR_TYPE_ERROR:
			{
				hideWait();
				final Builder builder = new AlertDialog.Builder(EditCar.this);
				builder.setTitle("Alert!");
				builder.setMessage("App Server not reachable.");
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
	public void onUpdateCarThreadDataReturned(boolean isSuccess, String msg) {
		// TODO Auto-generated method stub
		mHandler.sendMessage(mHandler.obtainMessage(CAR_TYPE_DATA_FALSE,msg));
	}

	@Override
	public void onUpdateCarThreadErrorReturned() {
		// TODO Auto-generated method stub
		mHandler.sendMessage(mHandler.obtainMessage(CAR_TYPE_ERROR,null));
	}

}
