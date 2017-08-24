package com.carpool.thread;

import java.util.Iterator;
import java.util.Set;

import org.json.JSONObject;

import android.util.Log;

import com.carpool.data.SharedData;
import com.carpool.utils.HttpCalls;

public class PassangerRegistrationThread extends Thread {
	private static String TAG="RegisterThread";
	
	public interface PassangerRegistrationThreadInterface
	{
		public void onPassangerRegistrationThreadDataReturned(boolean isSuccess,String msg);
		//public void onPassangerRegistrationThreadDataReturned(String msg);
		public void onPassangerRegistrationThreadErrorReturned();
	}
	
	private PassangerRegistrationThreadInterface mRegisterThreadInterface;
	private String mFName,mLName,mUname,mGender,mContactNo,mEMail,mAddress,mAdharId,mPasswd;
	public PassangerRegistrationThread(PassangerRegistrationThreadInterface n, String fname, String username, String lname,String gender,String contact,String eMail,String address,String aadharNo,String passWd) {
		
		this.mRegisterThreadInterface=n;
		this.mFName=fname;
		this.mUname=username;
		this.mLName=lname;
		this.mGender=gender;
		this.mContactNo=contact;
		this.mEMail=eMail;
		this.mAddress=address;
		this.mAdharId=aadharNo;
		this.mPasswd=passWd;
	}

	@Override
	public void run() {
		super.run();
		
		try {
			JSONObject obj=new JSONObject();
			
			obj.put("fname",mFName);
			obj.put("lname",mLName);
			obj.put("uname", mUname);
			obj.put("gender",mGender);			 
			obj.put("contactno",mContactNo);
			obj.put("emailid",mEMail);
			obj.put("address",mAddress);
			obj.put("adhar_id",mAdharId);
			obj.put("upassword",mPasswd);
			obj.put("device_id",SharedData.mMySharedPref.getDeviceId());
			Log.i("Parameter :",obj.toString());
			String urlParams = HttpCalls.GetUrlFormat(obj);
			
		
			//final String result = HttpCalls.getPOSTResponseString(
				//	SharedData.SERVER_URL+"RegisterPassengerServlet",obj.toString());
			// ?fname="+mFName+"&lname="+mLName+"&gender="+mGender+"&contactno="+mContactNo+"&emailId="+mEMail+"&address="+mAddress+"&adhar_id="+mAdharId+"&upassword="+mPasswd
			final String result = HttpCalls.getPOSTResponseString(
				SharedData.SERVER_URL+"RegisterPassengerServlet"+urlParams);
			Log.d("Result",result);
			JSONObject object=new JSONObject(result);
			boolean resultVal=object.getBoolean("result");
			String msg=object.getString("msg");
			
			if(resultVal)
				SharedData.mMySharedPref.setUserType(object.getString("userType"));
			
			mRegisterThreadInterface.onPassangerRegistrationThreadDataReturned(resultVal,msg);
			//mRegisterThreadInterface.onPassangerRegistrationThreadDataReturned(result);
		} catch (Exception e) {
			e.printStackTrace();			
			mRegisterThreadInterface.onPassangerRegistrationThreadErrorReturned();
		}
	}

}
