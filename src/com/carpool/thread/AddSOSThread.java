package com.carpool.thread;

import org.json.JSONObject;

import com.carpool.data.SharedData;
import com.carpool.utils.HttpCalls;

import android.util.Log;

public class AddSOSThread extends Thread {
	private static String TAG="AddSOSThread";
	
	public interface AddSOSThreadInterface
	{
		public void onAddSOSThreadDataReturned(boolean isSuccess,String msg);
		public void onAddSOSThreadErrorReturned();
	}
	
	private AddSOSThreadInterface mAddSOSThreadInterface;
	private String mName,mRelation,mMobile,mEmail;
	private int mCount;
	public AddSOSThread(AddSOSThreadInterface n, int count, String name, String rel,String mob,String email) {
		
		this.mAddSOSThreadInterface=n;
		this.mCount=count;
		this.mName=name;
		this.mRelation=rel;
		this.mMobile=mob;
		this.mEmail=email;
	}

	@Override
	public void run() {
		super.run();
		
		try {
			JSONObject obj=new JSONObject();
			obj.put("count",mCount);
			obj.put("name",mName);
			obj.put("rel",mRelation);
			obj.put("mob",mMobile);			 
			obj.put("email",mEmail);
			obj.put("user_id",SharedData.mMySharedPref.getUserId());
			String urlParams = HttpCalls.GetUrlFormat(obj);
			Log.i("Parameter :",obj.toString());
			final String result = HttpCalls.getPOSTResponseString(
					SharedData.SERVER_URL+"add_sos.php"+urlParams/*, obj.toString()*/);
			JSONObject object=new JSONObject(result);
			boolean resultVal=object.getBoolean("result");
			String msg=object.getString("msg");
			
			mAddSOSThreadInterface.onAddSOSThreadDataReturned(resultVal,msg);
		} catch (Exception e) {
			e.printStackTrace();			
			mAddSOSThreadInterface.onAddSOSThreadErrorReturned();
		}
	}

}
