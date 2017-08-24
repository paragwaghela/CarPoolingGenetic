package com.carpool.thread;

import org.json.JSONObject;

import com.carpool.data.SharedData;
import com.carpool.utils.HttpCalls;

import android.util.Log;

public class LoginThread extends Thread {
	
	private static String TAG="LoginThread";
	
	public interface LoginThreadInterface
	{
		public void onLoginThreadDataReturned(Boolean isSuccess,String msg);
		public void onLoginThreadErrorReturned();
	}
	private LoginThreadInterface mLoginThreadInterface;
	private String mUName;
	private String mPassword;
	private String mRole;
	//private String mDeviceId;
	
	public LoginThread(LoginThreadInterface n, final String uname, final String password ,final String role)
	{
		mLoginThreadInterface=n;
		mUName=uname;
		mPassword=password;
		mRole = role;
	}	
	
	@Override
	public void run() {
		super.run();
		
		try 
		{
			JSONObject obj=new JSONObject();
			obj.put("uname",mUName);
			obj.put("password",mPassword);
			if(mRole.equals("Driver"))
				mRole="driver";
			obj.put("role", mRole);
			obj.put("device_id",SharedData.mMySharedPref.getDeviceId());
			
			Log.i("Parameter :",obj.toString());
			String urlParams = HttpCalls.GetUrlFormat(obj);
			final String result=HttpCalls.getPOSTResponseString(SharedData.SERVER_URL+"LoginServlet"+urlParams/*, obj.toString()*/);
			obj=new JSONObject(result);
			boolean resultVal=obj.getBoolean("result");
			String msg = null;
			if(resultVal)
			{
				long user_id=obj.getLong("user_id");
				SharedData.mMySharedPref.setUserId(user_id);
				String user_type= obj.getString("role");
				SharedData.mMySharedPref.setUserType(user_type);
			}
			else
				msg=obj.getString("msg");
			mLoginThreadInterface.onLoginThreadDataReturned(resultVal,msg);
		} 
		catch (Exception e) {			
			e.printStackTrace();
			mLoginThreadInterface.onLoginThreadErrorReturned();
		}
	}

}
