package com.carpool.thread;

import org.json.JSONObject;

import android.util.Log;

import com.carpool.data.CarData;
import com.carpool.data.DrivReqDetailData;
import com.carpool.data.SharedData;
import com.carpool.utils.HttpCalls;

public class GetDriverReqInfoThread extends Thread {
	
	private static String TAG="GetProfileInfoThread";
	
	public interface GetDriverReqInfoThreadInterface
	{
		public void onGetDriverReqInfoThreadDataReturned(Boolean isSuccess,String msg);
		public void onGetDriverReqInfoThreadErrorReturned();
	}
	private GetDriverReqInfoThreadInterface mGetDriverReqInfoThreadInterface;
	private String mRequestId;
	public GetDriverReqInfoThread(GetDriverReqInfoThreadInterface n,String request_id)
	{
		mGetDriverReqInfoThreadInterface=n;
		mRequestId=request_id;
	}	
	
	@Override
	public void run() {
		super.run();
		
		try 
		{
			JSONObject obj=new JSONObject();
			obj.put("request_id",mRequestId);
			obj.put("role", SharedData.mMySharedPref.getUserType());
			String urlParams = HttpCalls.GetUrlFormat(obj);
			Log.i("Parameter :",obj.toString());
			final String result=HttpCalls.getPOSTResponseString(SharedData.SERVER_URL+"RequestInfoServlet"+urlParams);
			Log.d("Result",result);
			obj=new JSONObject(result);
			boolean resultVal=obj.getBoolean("result");
			String msg = null;
			if(resultVal)
			{
				SharedData.mDrivReqDetailData=new DrivReqDetailData();
				String dataObject = obj.getString("data");
				SharedData.mDrivReqDetailData.doParseJSONData(new JSONObject(dataObject));
				mGetDriverReqInfoThreadInterface.onGetDriverReqInfoThreadDataReturned(resultVal,null);
			}
			else
			{
				msg=obj.getString("msg");
				mGetDriverReqInfoThreadInterface.onGetDriverReqInfoThreadDataReturned(resultVal,msg);
			}
		} 
		catch (Exception e) {			
			e.printStackTrace();
			mGetDriverReqInfoThreadInterface.onGetDriverReqInfoThreadErrorReturned();
		}
	}

}
