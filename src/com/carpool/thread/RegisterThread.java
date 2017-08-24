package com.carpool.thread;

import org.json.JSONObject;

import com.carpool.data.SharedData;
import com.carpool.utils.HttpCalls;

import android.util.Log;

public class RegisterThread extends Thread {
	private static String TAG="RegisterThread";
	
	public interface RegisterThreadInterface
	{
		public void onRegisterThreadDataReturned(boolean isSuccess,String msg);
		public void onRegisterThreadErrorReturned();
	}
	
	private RegisterThreadInterface mRegisterThreadInterface;
	private String mFName,mLName,mUName,mGender,mSrcTime,mDestTime,mContactNo,mSource,mDestination,mConDestTime,mConSrcTime,mEMail,mAddress,mAdharId,/*mProfilePic,*/mLicenceNo,mPasswd,mModel,mCarNo,mComfort;
	private int mSeats,mInsurance,mConversly;
	public RegisterThread(RegisterThreadInterface n, String fname, String lname, String uname,String gender,
			String contact,String eMail,String address,String aadharNo,/*String profilePic,*/String licenceNo, String passWd,String model, String carno, String comfort,int seats,int insurance,String Source,String Destination,int conversly,String SrcTime,String DestTime,String ConSrcTime,String ConDestTime) {
		
		this.mRegisterThreadInterface=n;
		this.mFName=fname;
		this.mLName=lname;
		this.mUName=uname;
		this.mGender=gender;
		this.mContactNo=contact;
		this.mEMail=eMail;
		this.mAddress=address;
		this.mAdharId=aadharNo;
//		this.mProfilePic=profilePic;
		this.mLicenceNo=licenceNo;
		this.mPasswd=passWd;
		this.mModel=model;
		this.mCarNo=carno;
		this.mComfort=comfort;
		this.mSeats=seats;
		this.mInsurance=insurance;
		this.mSource=Source;
		this.mDestination=Destination;
		this.mConversly=conversly;		
		this.mSrcTime=SrcTime;
		this.mDestTime=DestTime;
		this.mConSrcTime=ConSrcTime;
		this.mConDestTime=ConDestTime;
	}

	@Override
	public void run() {
		super.run();
		
		try {
			JSONObject obj=new JSONObject();
			obj.put("fname",mFName);
			obj.put("lname",mLName);
			obj.put("uname",mUName);
			obj.put("gender",mGender);			 
			obj.put("contactno",mContactNo);
			obj.put("emailid",mEMail);
			obj.put("address",mAddress);
			obj.put("adhar_id",mAdharId);
//			obj.put("profile_pic",mProfilePic);
			obj.put("licence_no",mLicenceNo);
			obj.put("upassword",mPasswd);
			obj.put("model",mModel);
			obj.put("car_number",mCarNo);
			obj.put("comfort",mComfort);
			obj.put("seats",mSeats);
			obj.put("source",mSource);
			obj.put("destination",mDestination);
			obj.put("conversly",mConversly);
			obj.put("src_time",mSrcTime);
			obj.put("dest_time",mDestTime);
			obj.put("convers_src_time",mConSrcTime);
			obj.put("convers_dest_time",mConDestTime);
			obj.put("insurance",mInsurance);
			obj.put("device_id",SharedData.mMySharedPref.getDeviceId());
			Log.i("Parameter :",obj.toString());
			String urlParams = HttpCalls.GetUrlFormat(obj);
			final String result = HttpCalls.getPOSTResponseString(
					SharedData.SERVER_URL+"RegisterDriverServlet"+urlParams);
			JSONObject object=new JSONObject(result);
			boolean resultVal=object.getBoolean("result");
			String msg=object.getString("msg");
									
			mRegisterThreadInterface.onRegisterThreadDataReturned(resultVal,msg);
		} catch (Exception e) {
			e.printStackTrace();			
			mRegisterThreadInterface.onRegisterThreadErrorReturned();
		}
	}

}
