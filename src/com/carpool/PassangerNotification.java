package com.carpool;

import org.json.JSONException;
import org.json.JSONObject;

import com.carpool.utils.BaseActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PassangerNotification extends BaseActivity{

	private static String TAG="Passanger Notification";
	String requestId,reqstatus,driverName,contactNo,carNo,Model,passangerData,licenceNo;
	@Override
	public int getLayoutXML() {
		return R.layout.passanger_notification;
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

		Bundle b=getIntent().getExtras();
		passangerData=b.getString("passangerData");
		try {
			JSONObject jsonObject = new JSONObject(passangerData);
			String data =jsonObject.getString("DriverData");
			JSONObject jsonData = new JSONObject(data);
			driverName= jsonData.getString("driver_name");
			carNo = jsonData.getString("car_no");
			Model=jsonData.getString("car_model");
			//contactNo=jsonObject.getString("contactNo");
			licenceNo=jsonObject.getString("licence_no");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*requestId = b.getString("requestId");
		reqstatus=b.getString("status");
		driverName=b.getString("driverName");
		contactNo=b.getString("contactNo");
		carNo=b.getString("carNumber");
		Model=b.getString("carModel");*/
		
		
		TextView carModel=(TextView)findViewById(R.id.dCarModel);
		TextView car_No=(TextView)findViewById(R.id.dCarNo);
		TextView dname=(TextView)findViewById(R.id.dName);
		TextView dcontact=(TextView)findViewById(R.id.dContact);
		
		/*reqId.setText(requestId);
		status.setText(reqstatus);
		dname.setText(driverName);
		dcontact.setText(contactNo);
		car_No.setText(carNo);
		carModel.setText(Model);
		*/
		
		dname.setText(driverName);
		dcontact.setText(contactNo);
		car_No.setText(carNo);
		carModel.setText(Model);
		
		
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.passanger_notification, menu);
        return true;
    }

    
}
