package com.carpool;

import com.carpool.data.SharedData;
import com.carpool.lazyload.ImageLoader;
import com.carpool.utils.MySharedPref;
import com.google.android.gcm.GCMRegistrar;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.Window;

public class SplashScreen extends Activity {

	private static String TAG="SplashScreen";

	private static final String PROJECT_ID ="253985541845";

	// This string will hold the lengthy registration id that comes
	// from GCMRegistrar.register()
	private String regId = "";

	// This intent filter will be set to filter on the string "GCM_RECEIVED_ACTION"
	IntentFilter gcmFilter;

	private String registrationStatus = "Not yet registered";
	private String broadcastMessage = "No broadcast message";

	// A BroadcastReceiver must override the onReceive() event.
	private BroadcastReceiver gcmReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			broadcastMessage = intent.getExtras().getString("gcm");

			if (broadcastMessage != null) {
				// display our received message
				Log.d("message", broadcastMessage);
			}
		}
	};

	private final int SPLASH_DISPLAY_LENGHT = 5000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);

		SharedData.mMySharedPref=new MySharedPref(this);
		SharedData.imageLoader=new ImageLoader(this);

		// Create our IntentFilter, which will be used in conjunction with a
		// broadcast receiver.
		gcmFilter = new IntentFilter();
		gcmFilter.addAction("GCM_RECEIVED_ACTION");

		registerClient();	
	}
	
	// This registerClient() method checks the current device, checks the
		// manifest for the appropriate rights, and then retrieves a registration id
		// from the GCM cloud.  If there is no registration id, GCMRegistrar will
		// register this device for the specified project, which will return a
		// registration id.
		public void registerClient() {

			if(SharedData.mMySharedPref.getDeviceId().equals(""))
			{
				try {
					// Check that the device supports GCM (should be in a try / catch)
					GCMRegistrar.checkDevice(this);

					// Check the manifest to be sure this app has all the required
					// permissions.
					GCMRegistrar.checkManifest(this);

					// Get the existing registration id, if it exists.
					regId = GCMRegistrar.getRegistrationId(this);

					if (regId.equals("")) {

						registrationStatus = "Registering...";

						// register this device for this project
						GCMRegistrar.register(this, PROJECT_ID);
						regId = GCMRegistrar.getRegistrationId(this);
						Log.d("Set device id",regId);
						SharedData.mMySharedPref.setDeviceId(regId);
						
						registrationStatus = "Registration Acquired";
						
						// This is actually a dummy function.  At this point, one
						// would send the registration id, and other identifying
						// information to your server, which should save the id
						// for use when broadcasting messages.
						sendRegistrationToServer();

					} else {

						registrationStatus = "Already registered";
						SharedData.mMySharedPref.setDeviceId(regId);
					}

				} catch (Exception e) {

					e.printStackTrace();
					registrationStatus = e.getMessage();
					
					final Builder builder = new AlertDialog.Builder(this);
					builder.setTitle("Alert!");
					builder.setMessage("Unable to register with Google Cloud Server. Please check google api support.");
					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					});
					builder.setCancelable(false);
					builder.show();
				}
			}
			SharedData.mMySharedPref.setDeviceId(regId);
			Log.d(TAG, registrationStatus +" : "+regId);
			Log.d("GCM Registration Id", regId);
			//tvRegStatusResult.setText(registrationStatus);

			// This is part of our CHEAT.  For this demo, you'll need to
			// capture this registration id so it can be used in our demo web
			// service.
			Log.d(TAG, regId);
			
			new Handler().postDelayed(new Runnable(){
	            @Override
	            public void run() {
	                /* Create an Intent that will start the Menu-Activity. */
	            	if(SharedData.mMySharedPref.getUserId()!=0)
	            	{
		            	Intent i = new Intent(SplashScreen.this,UserHome.class);
		                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        		startActivity(i);
		                finish();
	            	}
	            	else
	            	{
	            		Intent i = new Intent(SplashScreen.this,Login.class);
		                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        		startActivity(i);
		                finish();
	            	}
	            }
	        }, SPLASH_DISPLAY_LENGHT);	

		}
		
		private void sendRegistrationToServer() {
			// This is an empty placeholder for an asynchronous task to post the
			// registration
			// id and any other identifying information to your server.
			
			
		}

		// If the user changes the orientation of his phone, the current activity
		// is destroyed, and then re-created.  This means that our broadcast message
		// will get wiped out during re-orientation.
		// So, we save the broadcastmessage during an onSaveInstanceState()
		// event, which is called prior to the destruction of the activity.
		@Override
		public void onSaveInstanceState(Bundle savedInstanceState) {

			super.onSaveInstanceState(savedInstanceState);

			savedInstanceState.putString("BroadcastMessage", broadcastMessage);

		}

		// When an activity is re-created, the os generates an onRestoreInstanceState()
		// event, passing it a bundle that contains any values that you may have put
		// in during onSaveInstanceState()
		// We can use this mechanism to re-display our last broadcast message.

		@Override
		public void onRestoreInstanceState(Bundle savedInstanceState) {

			super.onRestoreInstanceState(savedInstanceState);

			broadcastMessage = savedInstanceState.getString("BroadcastMessage");
		}

		// If our activity is paused, it is important to UN-register any
		// broadcast receivers.
		@Override
		protected void onPause() {
			unregisterReceiver(gcmReceiver);
			super.onPause();
		}

		// When an activity is resumed, be sure to register any
		// broadcast receivers with the appropriate intent
		@Override
		protected void onResume() {
			super.onResume();
			registerReceiver(gcmReceiver, gcmFilter);

		}

		// NOTE the call to GCMRegistrar.onDestroy()
		@Override
		public void onDestroy() {

			GCMRegistrar.onDestroy(this);

			super.onDestroy();
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}

}
