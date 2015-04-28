package com.carpool.thread;

import org.json.JSONObject;

import android.util.Log;

import com.carpool.data.SharedData;
import com.carpool.utils.HttpCalls;

public class EditPassangerProfileInfoThread extends Thread {
	private static String TAG="EditProfileInfoThread";
	
	public interface EditPassangerProfileInfoThreadInterface
	{
		public void onEditPassangerProfileInfoThreadDataReturned(boolean isSuccess,String msg);
		public void onEditPassangerProfileInfoThreadErrorReturned();
	}
	
	private EditPassangerProfileInfoThreadInterface mEditPassangerProfileInfoThreadInterface;
	private String mFName,mUName,mLName,mGender,mContactNo,mAddress,mAdhar,mEmail;
	public EditPassangerProfileInfoThread(EditPassangerProfileInfoThreadInterface n, String fname, String uname, String lname,String gender,
			String contact,String address,String aadhar,String email) {
		
		this.mEditPassangerProfileInfoThreadInterface=n;
		this.mFName=fname;
		this.mUName=uname;
		this.mLName=lname;
		this.mGender=gender;
		this.mContactNo=contact;
		this.mAddress=address;
		this.mAdhar=aadhar;
		this.mEmail=email;
	}

	@Override
	public void run() {
		super.run();
		
		try {
			JSONObject obj=new JSONObject();
			obj.put("fname",mFName);
			obj.put("uname",mUName);
			obj.put("lname",mLName);
			obj.put("gender",mGender);			 
			obj.put("contactno",mContactNo);
			obj.put("address",mAddress);
			obj.put("adhar_id",mAdhar);
			obj.put("email", mEmail);
			obj.put("user_id",SharedData.mMySharedPref.getUserId());
			Log.i("Parameter :",obj.toString());
			String urlParams = HttpCalls.GetUrlFormat(obj);
			final String result = HttpCalls.getPOSTResponseString(
					SharedData.SERVER_URL+"UpdatePassengerServlet"+urlParams);
			JSONObject object=new JSONObject(result);
			boolean resultVal=object.getBoolean("result");
			String msg=object.getString("msg");			
			mEditPassangerProfileInfoThreadInterface.onEditPassangerProfileInfoThreadDataReturned(resultVal,msg);
		} catch (Exception e) {
			e.printStackTrace();			
			mEditPassangerProfileInfoThreadInterface.onEditPassangerProfileInfoThreadErrorReturned();
		}
	}

}
