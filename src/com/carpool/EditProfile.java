package com.carpool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.carpool.data.SharedData;
import com.carpool.thread.EditProfileInfoThread;
import com.carpool.thread.EditProfileInfoThread.EditProfileInfoThreadInterface;
import com.carpool.utils.BaseActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class EditProfile extends BaseActivity implements EditProfileInfoThreadInterface
{
	private static String TAG="EditProfile";

	@Override
	public int getLayoutXML() {
		return R.layout.edit_driver_profile;
	}

	String fname="",lname="",uname="",contact="",email="",/*passwd="",*/address="",aadhar="",photo="",license="",carNo="",carModel="",carCapacity="",comfort="",source="",destination="",srcTime="",destTime="",convSrcTime="",convDestTime="";
	Spinner spGender,spConversly,spSource,spDestination;
	private int pHour;
    private int pMinute;
    private TextView setTime;
    TextView ConvDestTime,CovSourceTime;
    Button btnConvDestTime,btnConvSouceTime;
    static final int TIME_DIALOG_ID = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Button btnBack=(Button)findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				finish();
			}
		});

		Button btnNext=(Button)findViewById(R.id.btnNext);
		btnNext.setVisibility(View.GONE);

		TextView txtTitle=(TextView)findViewById(R.id.txtTitle);
		txtTitle.setText("Edit Profile");

		spGender=(Spinner)findViewById(R.id.spGender);

		String[] gender={"Male","Female"};		
		List<String> items = new ArrayList<String>(Arrays.asList(gender));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditProfile.this, android.R.layout.simple_spinner_item, items);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spGender.setAdapter(adapter);
		
		spSource=(Spinner)findViewById(R.id.spESource);

		//String[] Source={"pune","Shivaji nagar","Deccon","Dapodi","Pimpary","Chinchvad"};;		
		List<String> items1 = new ArrayList<String>(Arrays.asList(SharedData.Source));
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(EditProfile.this, android.R.layout.simple_spinner_item, items1);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spSource.setAdapter(adapter1);
		
		spDestination=(Spinner)findViewById(R.id.spEDestination);

		//String[] Destination={"pune","Shivaji nagar","Deccon","Dapodi","Pimpary","Chinchvad"};;		
		List<String> items3 = new ArrayList<String>(Arrays.asList(SharedData.destination));
		ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(EditProfile.this, android.R.layout.simple_spinner_item, items3);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spDestination.setAdapter(adapter3);
		spDestination.setSelection(items3.indexOf(SharedData.mDriverData.destination));
		
	    spConversly=(Spinner)findViewById(R.id.spConversly);
			
			String[] isConvers={"No","Yes"};
		    List<String> items2 = new ArrayList<String>(Arrays.asList(isConvers));
			ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(EditProfile.this, android.R.layout.simple_spinner_item, items2);
		    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    spConversly.setAdapter(adapter2);
		    spSource.setSelection(items2.indexOf(SharedData.mDriverData.source));
		    CovSourceTime =(TextView)findViewById(R.id.etConSrcTime);
			btnConvSouceTime = (Button)findViewById(R.id.btnConSrcTime);
		    
			ConvDestTime =(TextView)findViewById(R.id.etConDestTime);
			btnConvDestTime = (Button)findViewById(R.id.btnConDestTime);
				    
			spConversly.setOnItemSelectedListener(new OnItemSelectedListener() {

			@SuppressLint("ParserError")
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view,
					int position, long id) {
				Log.d("Position",""+position);
				// TODO Auto-generated method stub
				switch(position){
				case 0 :
					Log.d("PositionNo",""+position);
					btnConvDestTime.setVisibility(View.INVISIBLE);
					ConvDestTime.setVisibility(View.INVISIBLE);
					CovSourceTime.setVisibility(View.INVISIBLE);
					btnConvSouceTime.setVisibility(View.INVISIBLE);
					break;
				case 1 :
					Log.d("PositionYes",""+position);
					btnConvDestTime.setVisibility(View.VISIBLE);
					ConvDestTime.setVisibility(View.VISIBLE);
					CovSourceTime.setVisibility(View.VISIBLE);
					btnConvSouceTime.setVisibility(View.VISIBLE);
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {
				// TODO Auto-generated method stub
				btnConvDestTime.setVisibility(View.INVISIBLE);
				ConvDestTime.setVisibility(View.INVISIBLE);
				CovSourceTime.setVisibility(View.INVISIBLE);
				btnConvSouceTime.setVisibility(View.INVISIBLE);
			}
		});
		
		EditText etFName=(EditText)findViewById(R.id.editFName);
		EditText etLName=(EditText)findViewById(R.id.editLName);
		EditText etUName=(EditText)findViewById(R.id.editUName);
		EditText etContact=(EditText)findViewById(R.id.editContact);
		EditText etEmail=(EditText)findViewById(R.id.editMail);
		EditText editAddress=(EditText)findViewById(R.id.editAddr);
		EditText etAadhar=(EditText)findViewById(R.id.etAadhar);
		TextView etLicenseNo=(TextView)findViewById(R.id.etLicenseNo);
		//TextView etSource=(EditText)findViewById(R.id.etSource);
		//TextView etDestination=(EditText)findViewById(R.id.etDestination);
		TextView etSourceTime=(TextView)findViewById(R.id.etSourceTime);
		TextView etDestinationTime=(TextView)findViewById(R.id.etDestTime);
		TextView etSourceConVTime=(TextView)findViewById(R.id.etConSrcTime);
		TextView etDestinationConvTime=(TextView)findViewById(R.id.etConDestTime);
		
		
		etFName.setText(SharedData.mDriverData.fname);
		etLName.setText(SharedData.mDriverData.lname);
		etUName.setText(SharedData.mDriverData.uname);
		etContact.setText(SharedData.mDriverData.contact);
		etEmail.setText(SharedData.mDriverData.email);
		etEmail.setEnabled(false);
		editAddress.setText(SharedData.mDriverData.addr);
		etAadhar.setText(SharedData.mDriverData.adharId);
		etLicenseNo.setText(SharedData.mDriverData.licenseNo);
		//spSource.setText(SharedData.mDriverData.source);
		//etDestination.setText(SharedData.mDriverData.destination);
		etSourceTime.setText(SharedData.mDriverData.srcTime);
		etDestinationTime.setText(SharedData.mDriverData.destTime);
		etSourceConVTime.setText(SharedData.mDriverData.conSrcTime);
		etDestinationConvTime.setText(SharedData.mDriverData.conDestTime);
		
		if(SharedData.mDriverData.gender.equals("Male") ||SharedData.mDriverData.gender.equals("male") )
			spGender.setSelection(0, true);
		else
			spGender.setSelection(1, true);
		if(SharedData.mDriverData.conversly.equals("no")||SharedData.mDriverData.conversly.equals("No"))
			spConversly.setSelection(0, true);
		else
			spConversly.setSelection(1, true);
		
		Button btnRegister=(Button)findViewById(R.id.btnRegister);
		btnRegister.setText("Save");
		btnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				EditText etFName=(EditText)findViewById(R.id.editFName);
				EditText etUName=(EditText)findViewById(R.id.editUName);
				EditText etLName=(EditText)findViewById(R.id.editLName);
				EditText etContact=(EditText)findViewById(R.id.editContact);
				EditText editAddress=(EditText)findViewById(R.id.editAddr);
				EditText etAadhar=(EditText)findViewById(R.id.etAadhar);
				EditText etLicenseNo=(EditText)findViewById(R.id.etLicenseNo);
				//TextView etSource=(TextView)findViewById(R.id.etSource);
				//TextView etDestination=(TextView)findViewById(R.id.etDestination);
				TextView etSourceTime=(TextView)findViewById(R.id.etSourceTime);
				TextView etDestinationTime=(TextView)findViewById(R.id.etDestTime);
				TextView etSourceConVTime=(TextView)findViewById(R.id.etConSrcTime);
				TextView etDestinationConvTime=(TextView)findViewById(R.id.etConDestTime);
				
				
				String gender="";
				fname=etFName.getText().toString();
				lname=etLName.getText().toString();
				uname=etUName.getText().toString();
				
				gender=spGender.getSelectedItem().toString();
				contact=etContact.getText().toString();
				address=editAddress.getText().toString();
				aadhar=etAadhar.getText().toString();
				license=etLicenseNo.getText().toString();
				
				//source=etSource.getText().toString();
				//destination=etDestination.getText().toString();
				source = spSource.getSelectedItem().toString();
				destination = spDestination.getSelectedItem().toString();
				srcTime=etSourceTime.getText().toString();
				destTime=etDestinationTime.getText().toString();
				convSrcTime=etSourceConVTime.getText().toString();
				convDestTime=etDestinationConvTime.getText().toString();
					
				if(fname.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please enter First Name.");	
					etFName.setText("");
					etFName.requestFocus();
				}
				
				else if(lname.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please enter Last Name.");	
					etLName.setText("");
					etLName.requestFocus();
				}
				else if(uname.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please enter User Name.");	
					etUName.setText("");
					etUName.requestFocus();
				}
				else if(contact.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please enter Contact Number.");	
					etContact.setText("");
					etContact.requestFocus();
				}
				else if(contact.length()!=10)
				{
					ShowSimpleDialog("Alert!", "Please enter valid Contact Number.");	
					etContact.setText("");
					etContact.requestFocus();
				}
				else if(address.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please enter address.");	
					editAddress.setText("");
					editAddress.requestFocus();
				}
				else if(aadhar.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please enter your aadhar Id number.");	
					etAadhar.setText("");
					etAadhar.requestFocus();
				}
				else if(license.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please enter license number.");	
					etLicenseNo.setText("");
					etLicenseNo.requestFocus();
				}	
				else
				{
					showWait("Updating Profile Data...");
					new EditProfileInfoThread(EditProfile.this,fname,lname,uname,gender,contact,email,address,aadhar,/*photo,*/
							license/*,carModel,carNo,comfort,Integer.parseInt(carCapacity),spInsurance.getSelectedItemPosition()*/,source,destination,spConversly.getSelectedItemPosition(),srcTime,destTime,convSrcTime,convDestTime).start();
				}
			}
		});
		
	Button btnSouceTime = (Button)findViewById(R.id.btnSurceTimer);
	btnSouceTime.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			setTime=(TextView)findViewById(R.id.etSourceTime);
			showDialog(TIME_DIALOG_ID);
		}
	});
//	TextView DestTime =(TextView)findViewById(R.id.etDestTime);
	
	Button btnDestTime = (Button)findViewById(R.id.btnDestTime);
	btnDestTime.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			setTime=(TextView)findViewById(R.id.etDestTime);
			 showDialog(TIME_DIALOG_ID);

			
		}
	});
	
  	btnConvSouceTime.setOnClickListener(new OnClickListener() {
	   @Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			setTime=(TextView)findViewById(R.id.etConSrcTime);
			 showDialog(TIME_DIALOG_ID);

		}
	});
	
	btnConvDestTime.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			setTime=(TextView)findViewById(R.id.etConDestTime);
			 showDialog(TIME_DIALOG_ID);

		}
	});
} 
	
/** Callback received when the user "picks" a time in the dialog */
private TimePickerDialog.OnTimeSetListener mTimeSetListener =
    new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            pHour = hourOfDay;
            pMinute = minute;
            updateDisplay();
            
        }
    };
 
/** Updates the time in the TextView */
private void updateDisplay() {
	
	String timeSet="";
	if(pHour>12){
		pHour -=12;
		timeSet="PM";
	}else if(pHour == 0){
		pHour +=12;
		timeSet = "AM";
	}else if(pHour == 12){
		timeSet="PM";
	}else
		timeSet="AM";
	
	setTime.setText(
        new StringBuilder()
                .append(pad(pHour)).append("-")
                .append(pad(pMinute)).append(timeSet));
	
}
 
 
/** Add padding to numbers less than ten */
private static String pad(int c) {
    if (c >= 10)
        return String.valueOf(c);
    else
        return "0" + String.valueOf(c);
}

protected Dialog onCreateDialog(int id) {
    switch (id) {
    case TIME_DIALOG_ID:
        return new TimePickerDialog(this,
                mTimeSetListener, pHour, pMinute, false);
    }
    return null;
} 
	private final static int UPDATE_DATA_TRUE=1;
	private final static int UPDATE_DATA_FALSE=2;
	private final static int UPDATE_ERROR=3;

	private final Handler mHandler=new Handler()
	{
		public void handleMessage(android.os.Message msg) {
			hideWait();
			switch (msg.what) {
			case UPDATE_DATA_TRUE:
			{
				Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
				finish();
			}
			break;
			case UPDATE_DATA_FALSE:
			{
				ShowSimpleDialog("Alert!",msg.obj.toString());
			}
			break;
			case UPDATE_ERROR:
			{
				ShowSimpleDialog("Alert!", "App Server not reachable.");
			}
			break;
			}
		}
	};
	@Override
	public void onEditProfileInfoThreadDataReturned(boolean isSuccess,
			String msg) {
		// TODO Auto-generated method stub
		if(isSuccess)
			mHandler.sendMessage(mHandler.obtainMessage(UPDATE_DATA_TRUE,msg));
		else
			mHandler.sendMessage(mHandler.obtainMessage(UPDATE_DATA_FALSE,msg));
	}

	@Override
	public void onEditProfileInfoThreadErrorReturned() {
		// TODO Auto-generated method stub
		mHandler.sendMessage(mHandler.obtainMessage(UPDATE_ERROR,null));
	}
}
