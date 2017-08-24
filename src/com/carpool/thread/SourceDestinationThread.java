package com.carpool.thread;

import org.json.JSONArray;
import org.json.JSONObject;

import com.carpool.data.SharedData;
import com.carpool.data.SorcAndDes;
import com.carpool.utils.HttpCalls;

public class SourceDestinationThread extends Thread {

	private static String TAG="SourceDestinationThread";

	public interface SourceDestinationThreadInterface {
		public void onSourceDestinationThreadDataReturned();
		public void onSourceDestinationThreadErrorReturned();
	}

	private SourceDestinationThreadInterface mSourceDestinationThreadInterface;

	public SourceDestinationThread(SourceDestinationThreadInterface n) {
		mSourceDestinationThreadInterface = n;
	}

	@Override
	public void run() {
		super.run();
		try {
			/*sss*/
			//String [] mData = result.split(",");
			String [] mData = {"pune","Shivaji nagar","Deccon","Dapodi","Pimpary","Chinchvad"};
			/*JSONArray data=new JSONArray(result);
			SorcAndDes[] mData=new SorcAndDes[data.length()];
			for(int i=0;i<data.length();i++)
			{
				mData[i]=new SorcAndDes();
				JSONObject obj=new JSONObject();
				obj=data.getJSONObject(i);
				mData[i].doParseJSONData(obj);
			}*/
			SharedData.Source=mData;
			SharedData.destination=mData;
			mSourceDestinationThreadInterface.onSourceDestinationThreadDataReturned();			
		} catch (Exception e) {
			e.printStackTrace();
			mSourceDestinationThreadInterface.onSourceDestinationThreadErrorReturned();
		}
	}
}
