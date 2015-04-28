package com.carpool.data;

import org.json.JSONObject;

import com.carpool.utils.JSONUtils;

public class DriverData {
	
	public int id;
	public String fname,lname,uname,gender,contact,email,addr,adharId,/*profilePic,*/licenseNo,source,destination,srcTime,destTime,conversly,conSrcTime,conDestTime;
	
	public void doParseJSONData(final JSONObject obj){
		id = Integer.parseInt((JSONUtils.getString(obj, "user_id")));
		fname = JSONUtils.getString(obj, "first_name");
		lname = JSONUtils.getString(obj, "last_name");
		uname=JSONUtils.getString(obj, "username");
		//password=JSONUtils.getString(obj, "username");
		gender= JSONUtils.getString(obj, "gender");
		contact = JSONUtils.getString(obj, "contact");
		email = JSONUtils.getString(obj, "email");
		addr = JSONUtils.getString(obj, "address");
		adharId = JSONUtils.getString(obj, "adhar_id");
		licenseNo = JSONUtils.getString(obj, "license_no");
		source = JSONUtils.getString(obj, "source");
		destination = JSONUtils.getString(obj, "destination");
		srcTime = JSONUtils.getString(obj, "srcTime");
		destTime = JSONUtils.getString(obj, "destTime");
		conversly = JSONUtils.getString(obj, "conversly");
		conSrcTime = JSONUtils.getString(obj, "converse_src_time");
		conDestTime = JSONUtils.getString(obj, "converse_dest_time");
		//profilePic = JSONUtils.getString(obj, "profile_pic");
		
	}
}


