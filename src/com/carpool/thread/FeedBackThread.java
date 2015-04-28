package com.carpool.thread;

import org.json.JSONObject;

import android.util.Log;

import com.carpool.data.SharedData;
import com.carpool.utils.HttpCalls;

public class FeedBackThread extends Thread {
	private static String TAG="EditProfileInfoThread";
	
	public interface FeedBackThreadInterface
	{
		public void onFeedBackThreadDataReturned(boolean isSuccess,String msg);
		public void onFeedBackThreadErrorReturned();
	}
	
	private FeedBackThreadInterface mFeedBackThreadInterface;
	private String mcomfortScore,moverAllScore,mStatus,mrequestId,mdriverId,mPassengerId;
	
	public FeedBackThread(FeedBackThreadInterface n, String driverId, String passengerId, String comfortScore,String overAllScore,String status, String request_id) {
		
		this.mFeedBackThreadInterface=n;
		this.mdriverId=driverId;
		this.mPassengerId=passengerId;
		this.mcomfortScore=comfortScore;
		this.moverAllScore=overAllScore;
		this.mStatus=status;
		this.mrequestId=request_id;
	}

	@Override
	public void run() {
		super.run();
		
		try {
			JSONObject obj=new JSONObject();
			obj.put("driver_id",mdriverId);
			//obj.put("passenger_id",mPassengerId);
			obj.put("request_id",mrequestId);
			obj.put("comfort_score", mcomfortScore);
			obj.put("over_all_score",moverAllScore);
			obj.put("status",mStatus);
			obj.put("passenger_id",SharedData.mMySharedPref.getUserId());
			Log.i("Parameter :",obj.toString());
			String urlParams = HttpCalls.GetUrlFormat(obj);
			final String result = HttpCalls.getPOSTResponseString(
					SharedData.SERVER_URL+"FeedbackServlet"+urlParams);
			JSONObject object=new JSONObject(result);
			boolean resultVal=object.getBoolean("result");
			String msg=object.getString("msg");			
			mFeedBackThreadInterface.onFeedBackThreadDataReturned(resultVal,msg);
		} catch (Exception e) {
			e.printStackTrace();			
			mFeedBackThreadInterface.onFeedBackThreadErrorReturned();
		}
	}

}
