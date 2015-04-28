package com.carpool;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.carpool.data.SharedData;
import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {

    private static final String PROJECT_ID = "253985541845";
    
	private static final String TAG = "GCMIntentService";
	
	public GCMIntentService()
	{
		super(PROJECT_ID);
		Log.d(TAG, "GCMIntentService init");
		
	}
	

	@Override
	protected void onError(Context ctx, String sError) {
		// TODO Auto-generated method stub
		Log.d(TAG, "Error: " + sError);
		
	}

	@Override
	protected void onMessage(Context ctx, Intent intent) {
		
		Log.d(TAG, "Message Received");
		
		String message = intent.getStringExtra("message");
		//Log.d("message",message);
		
		sendGCMIntent(ctx, message);
		
		generateNotification(ctx, message);
		
	}

	
	private void sendGCMIntent(Context ctx, String message) {
		
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction("GCM_RECEIVED_ACTION");
		
		broadcastIntent.putExtra("gcm", message);
		
		ctx.sendBroadcast(broadcastIntent);
		
	}
	
	
	@Override
	protected void onRegistered(Context ctx, String regId) {
		// TODO Auto-generated method stub
		// send regId to your server
		Log.d(TAG, regId);
		SharedData.mMySharedPref.setDeviceId(regId);
	}

	@Override
	protected void onUnregistered(Context ctx, String regId) {
		// TODO Auto-generated method stub
		// send notification to your server to remove that regId
		Log.d(TAG, regId);
		
	}
	
	private static void generateNotification(Context context, String message) {
		int icon = R.drawable.ic_launcher;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
       // Notification notification = new Notification(icon, message, when);
         
        String title = context.getString(R.string.app_name);
        try {
			JSONObject jsonObject = new JSONObject(message);
			Log.d("Message",message);
			if(jsonObject.has("DriverData")){
				
				Notification notification = new Notification(icon, "Message Resived", when);
		        Intent notificationIntent = new Intent(context, PassangerNotification.class);
		        // set intent so it does not start a new activity
		        notificationIntent.putExtra("passangerData", message);
		        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
		                Intent.FLAG_ACTIVITY_SINGLE_TOP);
		        PendingIntent intent =
		                PendingIntent.getActivity(context, 0, notificationIntent, 0);
		        notification.setLatestEventInfo(context, title, "Message recived", intent);
		        notification.flags |= Notification.FLAG_AUTO_CANCEL;
		         
		        // Play default notification sound
		        notification.defaults |= Notification.DEFAULT_SOUND;
		         
		        // Vibrate if vibrate is enabled
		        notification.defaults |= Notification.DEFAULT_VIBRATE;
		        notificationManager.notify(0, notification);     
		}else if(jsonObject.has("passangerData")){
			
			Notification notification = new Notification(icon, "Messaged Recived", when);
			Intent notificationIntent = new Intent(context, DriverNotification.class);
			notificationIntent.putExtra("DriverData", message);
	        // set intent so it does not start a new activity
	        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
	                Intent.FLAG_ACTIVITY_SINGLE_TOP);
	        PendingIntent intent =
	                PendingIntent.getActivity(context, 0, notificationIntent, 0);
	        notification.setLatestEventInfo(context, title, "Message Recived", intent);
	        notification.flags |= Notification.FLAG_AUTO_CANCEL;
	         
	        // Play default notification sound
	        notification.defaults |= Notification.DEFAULT_SOUND;
	         
	        // Vibrate if vibrate is enabled
	        notification.defaults |= Notification.DEFAULT_VIBRATE;
	        notificationManager.notify(0, notification); 
		}
        	} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*int icon = R.drawable.ic_launcher;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        
         
        String title = context.getString(R.string.app_name);
        */
        
        /*try {
			JSONObject jsonObject = new JSONObject(message);
			if(jsonObject!=null)
			{
				if(jsonObject.has("passangerData")){
					JSONObject jsonObjectData = new JSONObject(jsonObject.getString("passangerData"));
					Notification notification = new Notification(icon, message, when);
			        Intent notificationIntent = new Intent(context, PassangerNotification.class);
			        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			        notificationIntent.putExtra("requestId", jsonObject.getJSONObject("requestId").toString());
			        notificationIntent.putExtra("status", jsonObject.getString("status").toString());
			        notificationIntent.putExtra("driverName", jsonObjectData.getString("driverName").toString());
			        notificationIntent.putExtra("contactNo", jsonObjectData.getString("contactNo").toString());
			        notificationIntent.putExtra("carModel", jsonObjectData.getString("carModel").toString());
			        notificationIntent.putExtra("carNo", jsonObjectData.getString("carNumber").toString());
			        // set intent so it does not start a new activity
			        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
			                Intent.FLAG_ACTIVITY_SINGLE_TOP);
			        PendingIntent intent =
			                PendingIntent.getActivity(context, 0, notificationIntent, 0);
			        notification.setLatestEventInfo(context, title, message, intent);
			        notification.flags |= Notification.FLAG_AUTO_CANCEL;
			         
			        // Play default notification sound
			        notification.defaults |= Notification.DEFAULT_SOUND;
			         
			        // Vibrate if vibrate is enabled
			        notification.defaults |= Notification.DEFAULT_VIBRATE;
			        notificationManager.notify(0, notification);     
				}
				else if(jsonObject.has("DriverData")){
					JSONObject jsonObjectData = new JSONObject(jsonObject.getString("DriverData"));
					Notification notification = new Notification(icon, message, when);
			        Intent notificationIntent = new Intent(context, DriverNotification.class);
			        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			        notificationIntent.putExtra("requestId", jsonObject.getJSONObject("requestId").toString());
			        notificationIntent.putExtra("status", jsonObject.getString("status").toString());
			        notificationIntent.putExtra("passangerName", jsonObjectData.getString("passangerName").toString());
			        notificationIntent.putExtra("contactNo", jsonObjectData.getString("contactNo").toString());
			        notificationIntent.putExtra("Gender", jsonObjectData.getString("Gender").toString());
			        notificationIntent.putExtra("Source", jsonObjectData.getString("Source").toString());
			        notificationIntent.putExtra("Destination", jsonObjectData.getString("Destination").toString());
			        // set intent so it does not start a new activity
			        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
			                Intent.FLAG_ACTIVITY_SINGLE_TOP);
			        PendingIntent intent =
			                PendingIntent.getActivity(context, 0, notificationIntent, 0);
			        notification.setLatestEventInfo(context, title, message, intent);
			        notification.flags |= Notification.FLAG_AUTO_CANCEL;
			         
			        // Play default notification sound
			        notification.defaults |= Notification.DEFAULT_SOUND;
			         
			        // Vibrate if vibrate is enabled
			        notification.defaults |= Notification.DEFAULT_VIBRATE;
			        notificationManager.notify(0, notification);     
				}*/
			}
		/*} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
 
   // }

}
