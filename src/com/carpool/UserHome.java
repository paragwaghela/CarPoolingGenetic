package com.carpool;

import java.util.ArrayList;

import com.carpool.adapters.SlideMenuListAdapter;
import com.carpool.data.SharedData;
import com.carpool.utils.BaseActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class UserHome extends BaseActivity
{
	private static String TAG="UserHome";	
	
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	ImageView imgMyProfilePic;

	@Override
	public int getLayoutXML() {
		return R.layout.user_home;
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_home);
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		ArrayList<String> mMenuItems=new ArrayList<String>();
		mMenuItems.add(0, "My Profile");
		//Log.d("Usertype",SharedData.mMySharedPref.getUserType());
		if(SharedData.mMySharedPref.getUserType().equals("driver")){
			mMenuItems.add(1, "Update My Car");
			mMenuItems.add(2, "View History");
			
		}else{
			mMenuItems.add(1, "Make request");
			mMenuItems.add(2, "My Requests");
		}
		//mMenuItems.add(3, "Emergency Contacts");
		mMenuItems.add(3, "Change Password");
		if(SharedData.mMySharedPref.getUserType().equals("driver")){
			mMenuItems.add(4, "Upload Profile");
			mMenuItems.add(5, "Complaint");
			mMenuItems.add(6, "Chnage status");
		}
		
		ArrayList<Integer> mMenuImageId=new ArrayList<Integer>();
		mMenuImageId.add(0,R.drawable.cars);
		mMenuImageId.add(1,R.drawable.cars);
		mMenuImageId.add(2,R.drawable.cars);
		mMenuImageId.add(3,R.drawable.cars);
		//mMenuImageId.add(4,R.drawable.cars);
		if(SharedData.mMySharedPref.getUserType().equals("driver")){
			mMenuImageId.add(4,R.drawable.cars);
			mMenuImageId.add(5,R.drawable.cars);
			mMenuImageId.add(6,R.drawable.cars);
			
		}
		
		mDrawerList.setAdapter(new SlideMenuListAdapter(this,android.R.layout.simple_list_item_1,mMenuItems,mMenuImageId));
		
		Button btnBack=(Button)findViewById(R.id.btnBack);
		btnBack.setText("Menu");
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mDrawerLayout.isDrawerOpen(mDrawerList)){
					mDrawerLayout.closeDrawer(mDrawerList);
				}
				else{
					mDrawerLayout.openDrawer(mDrawerList);
				}
			}
		});

		Button btnNext=(Button)findViewById(R.id.btnNext);
		btnNext.setText("Logout");
		btnNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				SharedData.mMySharedPref.setUserId(0);
				SharedData.mMySharedPref.setUserName("");
				SharedData.mMySharedPref.setPassword("");
				SharedData.mMySharedPref.setUserType("");
				
				Intent i=new Intent(UserHome.this,Login.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				finish();
			}
		});

		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> Parent, View view, int position,
					long id) {
				Intent i=null;
				switch (position) {

				case 0:			
				{
					if(SharedData.mMySharedPref.getUserType().equals("driver")){
						i=new Intent(UserHome.this,MyProfile.class);
						i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(i);
					}
					else{
						i=new Intent(UserHome.this,PassengerProfile.class);
						i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(i);
					}
				}
				break;

				case 1:
				{	if(SharedData.mMySharedPref.getUserType().equals("driver")){
					 i=new Intent(UserHome.this,MyCarInfo.class);
					 i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(i);
						
					}else {
						//i=new Intent(UserHome.this,RequestCar.class);
						i=new Intent(UserHome.this,MakeRequest.class);
						 i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(i);
					}
				}
				break;

				case 2:
				{	
					if(SharedData.mMySharedPref.getUserType().equals("driver")){
						i=new Intent(UserHome.this,DriverReqList.class);
						i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(i);
						//ShowSimpleDialog("Alert!", "Currently this functionality is unavailable.");
					} else {
						i=new Intent(UserHome.this,MyRequestHistory.class);
						i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(i);
						//ShowSimpleDialog("Alert!", "Currently this functionality is unavailable.");
					}
					//ShowSimpleDialog("Alert!", "Currently this functionality is unavailable.");
				}
				break;

				case 3:
				{
					i=new Intent(UserHome.this,ChangePassword.class);
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
				}
				break;
					
				case 4:
				{
					i=new Intent(UserHome.this,updateProfilePic.class);
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
				}
				break;
				case 5:
				{
					i=new Intent(UserHome.this,DriverComplaint.class);
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
				}
				break;
				case 6:
				{
					i=new Intent(UserHome.this,DriverStatusChange.class);
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
				}
				break;
				}
				if(mDrawerLayout.isDrawerOpen(mDrawerList)){
					mDrawerLayout.closeDrawer(mDrawerList);
				}
			} 
		});
	
	}
} 