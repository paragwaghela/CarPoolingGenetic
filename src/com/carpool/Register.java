package com.carpool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;

import com.carpool.data.SharedData;
import com.carpool.thread.RegisterThread;
import com.carpool.thread.SourceDestinationThread;
import com.carpool.thread.RegisterThread.RegisterThreadInterface;
import com.carpool.thread.SourceDestinationThread.SourceDestinationThreadInterface;
import com.carpool.utils.BaseActivity;
import com.carpool.utils.FileDialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class Register extends BaseActivity implements RegisterThreadInterface,SourceDestinationThreadInterface
{

	private static String TAG="Register";
	private int pHour;
    private int pMinute;
    private TextView setTime;
    TextView ConvDestTime,CovSourceTime;
    Button btnConvDestTime,btnConvSouceTime;
    String message = "",responseBody="";
    HttpResponse response = null;
    /** This integer will uniquely define the dialog to be used for displaying time picker.*/
    static final int TIME_DIALOG_ID = 0;
	@Override
	public int getLayoutXML() {
		return R.layout.register;
	}
	
	String fname="",lname="",uname="",contact="",email="",passwd="",address="",aadhar="",photo="",license="",carNo="",carModel="",carCapacity="",comfort="",source="",destination="",srcTime="",destTime="",convSrcTime="",convDestTime="";
	Spinner spGender,spComfort,spInsurance,spConversly,spSource,spDestination;

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

		spGender=(Spinner)findViewById(R.id.spGender);
		
		String[] gender={"Male","Female"};	
		List<String> items = new ArrayList<String>(Arrays.asList(gender));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(Register.this, android.R.layout.simple_spinner_item, items);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spGender.setAdapter(adapter);
		
		spComfort=(Spinner)findViewById(R.id.spComfort);
		
		/*String[] comfort={"AC","Non-AC"};		
		List<String> items1 = new ArrayList<String>(Arrays.asList(comfort));
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(Register.this, android.R.layout.simple_spinner_item, items1);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spComfort.setAdapter(adapter1);*/
		
		String[] comfort={"AC","Non-AC"};	
		List<String> items1 = new ArrayList<String>(Arrays.asList(comfort));
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(Register.this, android.R.layout.simple_spinner_item, items1);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spComfort.setAdapter(adapter1);
		
		
	    spInsurance=(Spinner)findViewById(R.id.spInsurance);
		
		String[] isInsured={"No","Yes"};
	    List<String> items2 = new ArrayList<String>(Arrays.asList(isInsured));
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(Register.this, android.R.layout.simple_spinner_item, items2);
	    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spInsurance.setAdapter(adapter2);
	    
	    spConversly=(Spinner)findViewById(R.id.spConversly);
		
		String[] isConvers={"No","Yes"};
	    List<String> items3 = new ArrayList<String>(Arrays.asList(isConvers));
		ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(Register.this, android.R.layout.simple_spinner_item, items3);
	    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spConversly.setAdapter(adapter3);
	    
	    spSource=(Spinner)findViewById(R.id.spRSource);
		
		String[] Source={"pune","Shivaji nagar","Deccon","Dapodi","Pimpary","Chinchvad"};
	    List<String> items4 = new ArrayList<String>(Arrays.asList(Source));
		ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(Register.this, android.R.layout.simple_spinner_item, items4);
	    adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spSource.setAdapter(adapter4);
	   
	    spDestination=(Spinner)findViewById(R.id.spRDestination);
		
		String[] Dest={"pune","Shivaji nagar","Deccon","Dapodi","Pimpary","Chinchvad"};
	    List<String> items5 = new ArrayList<String>(Arrays.asList(Dest));
		ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(Register.this, android.R.layout.simple_spinner_item, items5);
	    adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spDestination.setAdapter(adapter5);
	  
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
	   
		Button btnRegister=(Button)findViewById(R.id.btnRegister);
		btnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				EditText etFName=(EditText)findViewById(R.id.editFName);
				EditText etLName=(EditText)findViewById(R.id.editLName);
				EditText etUName=(EditText)findViewById(R.id.editUName);
				EditText etContact=(EditText)findViewById(R.id.editContact);
				EditText etEmail=(EditText)findViewById(R.id.editMail);
				EditText etPassword=(EditText)findViewById(R.id.editPassword);
				EditText editAddress=(EditText)findViewById(R.id.editAddr);
				EditText etAadhar=(EditText)findViewById(R.id.etAadhar);

				//TextView tvPhotoURL=(TextView)findViewById(R.id.tvPhotoURL);
				TextView etLicenseNo=(TextView)findViewById(R.id.etLicenseNo);
				EditText etCarModel=(EditText)findViewById(R.id.etCarModel);
				EditText etCarNo=(EditText)findViewById(R.id.etCarNo);		
				EditText etCarCapacity=(EditText)findViewById(R.id.etCapacity);
				//EditText etSource=(EditText)findViewById(R.id.etSource);
				//EditText etDestination=(EditText)findViewById(R.id.etDestination);
				TextView etSourceTime=(TextView)findViewById(R.id.etSourceTime);
				TextView etDestTime=(TextView)findViewById(R.id.etDestTime);
				TextView etConDestTime=(TextView)findViewById(R.id.etConDestTime);
				TextView etConSrcTime=(TextView)findViewById(R.id.etConSrcTime);
				
				
				String gender="",comfort="";
				fname=etFName.getText().toString();
				lname=etLName.getText().toString();
				uname=etUName.getText().toString();
				gender=spGender.getSelectedItem().toString();
				contact=etContact.getText().toString();
				email=etEmail.getText().toString();
				passwd=etPassword.getText().toString();
				address=editAddress.getText().toString();
				aadhar=etAadhar.getText().toString();
				//photo=tvPhotoURL.getText().toString();
				license=etLicenseNo.getText().toString();
				carNo=etCarNo.getText().toString();
				carModel=etCarModel.getText().toString();
				carCapacity=etCarCapacity.getText().toString();
				comfort=spComfort.getSelectedItem().toString();
				//source=etSource.getText().toString();
				//destination=etDestination.getText().toString();
				source=spSource.getSelectedItem().toString();
				destination=spDestination.getSelectedItem().toString();
				srcTime=etSourceTime.getText().toString();
				destTime=etDestTime.getText().toString();
				convSrcTime=etConSrcTime.getText().toString();
				convDestTime=etConDestTime.getText().toString();
				Log.d("DeviceId",SharedData.mMySharedPref.getDeviceId());
				
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
				else if(email.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please enter email address.");	
					etEmail.setText("");
					etEmail.requestFocus();
				}
				else if(passwd.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please enter password.");	
					etPassword.setText("");
					etPassword.requestFocus();
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
				else if(source.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please enter Source .");	
					/*etSource.setText("");
					etSource.requestFocus();*/
				}
				else if(destination.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please enter Destination.");	
					/*etDestination.setText("");
					etDestination.requestFocus();
			*/	}			
				else if(srcTime.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please enter Source Time.");	
					etSourceTime.setText("");
					etSourceTime.requestFocus();
				}			
				else if(destTime.equals(""))
				{
					ShowSimpleDialog("Alert!", "Please enter Destination Time.");	
					etDestTime.setText("");
					etDestTime.requestFocus();
				}
				else if(convSrcTime.equals("")&&(spConversly.getSelectedItemPosition() == 1))
				{
					ShowSimpleDialog("Alert!", "Please enter Conversly Source Time.");	
					etConSrcTime.setText("");
					etConSrcTime.requestFocus();
				}			
				else if(convDestTime.equals("")&&(spConversly.getSelectedItemPosition() == 1))
				{
					ShowSimpleDialog("Alert!", "Please enter Conversly Destination Time.");	
					etConDestTime.setText("");
					etConDestTime.requestFocus();
				}			
				else
				{
					Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");
					Matcher matcher = pattern.matcher(email);
					if(!matcher.matches())
					{
						ShowSimpleDialog("Alert!", "Please Enter Valid Username as Mail Id.");
						etEmail.setText("");
						etEmail.requestFocus();
					}
					else
					{
						showWait("Registering...");
						
						new RegisterThread(Register.this,fname,lname,uname,gender,contact,email,address,aadhar,/*photo,*/
								license,passwd,carModel,carNo,comfort,Integer.parseInt(carCapacity),spInsurance.getSelectedItemPosition(),source,destination,spConversly.getSelectedItemPosition(),srcTime,destTime,convSrcTime,convDestTime).start();
					}
				}
			}
		});

		
		
//		TextView SurceTime =(TextView)findViewById(R.id.etSourceTime);
		Button btnSouceTime = (Button)findViewById(R.id.btnSurceTimer);
		btnSouceTime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setTime=(TextView)findViewById(R.id.etSourceTime);
				showDialog(TIME_DIALOG_ID);
			}
		});
//		TextView DestTime =(TextView)findViewById(R.id.etDestTime);
		
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
		showWait("Request sending...");
		new SourceDestinationThread(Register.this).start();
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
	
	String selected_file_name=null;		
	private Bitmap bitmap;

	private final static int REGISTER_DATA_TRUE=1;
	private final static int REGISTER_DATA_FALSE=2;
	private final static int REGISTER_ERROR=3;
	private final static int SOURCE_DEST_DATA=4;
	private final static int SOURCE_DEST_ERROR=5;

	private final Handler mHandler=new Handler()
	{
		public void handleMessage(android.os.Message msg) {
			hideWait();
			switch (msg.what) {
			case REGISTER_DATA_TRUE:
			{
				//SharedData.mMySharedPref.setInsurence(spInsurance.getSelectedItemPosition());
				Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
				finish();
			}
			break;
			case REGISTER_DATA_FALSE:
			{
				ShowSimpleDialog("Alert!",msg.obj.toString());
			}
			break;
			case REGISTER_ERROR:
			{
				ShowSimpleDialog("Alert!", "App Server not reachable.");
			}
			break;
			case SOURCE_DEST_DATA:
			{ 
				spSource=(Spinner)findViewById(R.id.spRSource);
			
				//String[] Source={"pune","Shivaji nagar","Deccon","Dapodi","Pimpary","Chinchvad"};
			    List<String> items4 = new ArrayList<String>(Arrays.asList(SharedData.Source));
				ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(Register.this, android.R.layout.simple_spinner_item, items4);
			    adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    spSource.setAdapter(adapter4);
			   
			    spDestination=(Spinner)findViewById(R.id.spRDestination);
				
				//String[] Dest={"pune","Shivaji nagar","Deccon","Dapodi","Pimpary","Chinchvad"};
			    List<String> items5 = new ArrayList<String>(Arrays.asList(SharedData.destination));
				ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(Register.this, android.R.layout.simple_spinner_item, items5);
			    adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    spDestination.setAdapter(adapter5);
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
		public void onRegisterThreadDataReturned(boolean isSuccess,String msg) {
			if(isSuccess)
				mHandler.sendMessage(mHandler.obtainMessage(REGISTER_DATA_TRUE,msg));
			else
				mHandler.sendMessage(mHandler.obtainMessage(REGISTER_DATA_FALSE,msg));
		}
	
		@Override
		public void onRegisterThreadErrorReturned() {	
			mHandler.sendMessage(mHandler.obtainMessage(REGISTER_ERROR,null));
		}

	boolean isSuccess;
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

