package com.carpool.data;

import org.json.JSONObject;

import com.carpool.utils.JSONUtils;

public class UserData {
	
	public int id;
	public String fname,lname,uname,gender,contact,email,addr,adharId,profilePic,licenseNo,base_url;
	
	public void doParseJSONData(final JSONObject obj){
		id = Integer.parseInt((JSONUtils.getString(obj, "user_id")));
		fname = JSONUtils.getString(obj, "first_name");
		lname = JSONUtils.getString(obj, "last_name");
		uname=JSONUtils.getString(obj, "username");
		gender= JSONUtils.getString(obj, "gender");
		contact = JSONUtils.getString(obj, "contact");
		email = JSONUtils.getString(obj, "email");
		addr = JSONUtils.getString(obj, "address");
		adharId = JSONUtils.getString(obj, "adhar_id");
		
	}
}
