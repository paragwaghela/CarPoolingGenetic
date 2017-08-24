package com.carpool.data;

import java.util.ArrayList;

import com.carpool.lazyload.ImageLoader;
import com.carpool.utils.MySharedPref;

public class SharedData {
	
	private static String TAG="SharedData";
	
	//public static String SERVER_URL="http://192.168.56.1:8081/GeneticCarpooling/";
	public static String SERVER_URL="";
	
	public static boolean DriverStatus;
	
	public static MySharedPref mMySharedPref;
	
	public static ImageLoader imageLoader;
	
	public static CarData mCarData;

	public static UserData mUserData;
	
	public static DriverData mDriverData;
	
	public static PasseReqDetailData mPassReqDetailData;
	
	public static DrivReqDetailData mDrivReqDetailData;
	
	public static ArrayList<passData> mpassData;
	
	public static ArrayList<ReqData> mReqData;
	
	public static ArrayList<DriverReqdata> mDriverReqData;
	
	public static String[] Source;
	public static String[] destination;
	
	public static SorcAndDes[] mSourceDest;
	
}
