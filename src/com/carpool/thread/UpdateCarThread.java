package com.carpool.thread;

import org.json.JSONObject;

import android.util.Log;

import com.carpool.data.SharedData;
import com.carpool.utils.HttpCalls;

public class UpdateCarThread extends Thread {
	private static String TAG="EditProfileInfoThread";
	
	public interface UpdateCarThreadInterface
	{
		public void onUpdateCarThreadDataReturned(boolean isSuccess,String msg);
		public void onUpdateCarThreadErrorReturned();
	}
	
	private UpdateCarThreadInterface mUpdateCarThreadInterface;
	private String mModel,mCarNo,mComfort,mInsurance;
	int mSeats;
	public UpdateCarThread(UpdateCarThreadInterface n,  String carno, String model,String comfort,int seats,String insurance) {
		
		this.mUpdateCarThreadInterface=n;
		this.mModel=model;
		this.mCarNo=carno;
		this.mComfort=comfort;
		this.mInsurance=insurance;
		this.mSeats=seats;
	}

	@Override
	public void run() {
		super.run();
		
		try {
			JSONObject obj=new JSONObject();
			obj.put("model",mModel);
			obj.put("car_number",mCarNo);
			obj.put("comfort",mComfort);
			obj.put("insurance", mInsurance);
			obj.put("seats",mSeats);
			obj.put("user_id",SharedData.mMySharedPref.getUserId());
			Log.i("Parameter :",obj.toString());
			String urlParams = HttpCalls.GetUrlFormat(obj);
			final String result = HttpCalls.getPOSTResponseString(
					SharedData.SERVER_URL+"UpdateCarInfoServlet"+urlParams);
			JSONObject object=new JSONObject(result);
			boolean resultVal=object.getBoolean("result");
			String msg=object.getString("msg");			
			mUpdateCarThreadInterface.onUpdateCarThreadDataReturned(resultVal,msg);
		} catch (Exception e) {
			e.printStackTrace();			
			mUpdateCarThreadInterface.onUpdateCarThreadErrorReturned();
		}
	}

}
