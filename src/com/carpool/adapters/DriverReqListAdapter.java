package com.carpool.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import com.carpool.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DriverReqListAdapter extends ArrayAdapter<HashMap<String, Object>>{
	
	ArrayList<HashMap<String, Object>> myData;
	Context con;
	public DriverReqListAdapter(Context context, int textViewResourceId,ArrayList<HashMap<String, Object>> abc) {
		super(context, textViewResourceId,abc);
		// TODO Auto-generated constructor stub
		myData=abc;
		con=context;
		Log.d("mydat",""+myData);
	}
	
	//class for caching the views in a row  
	private class ViewHolder
	{
		TextView tvDHSource,tvDHDestination,tvDHPickUpTime,tvDHStatus;
	}
	
	ViewHolder viewHolder;
	
	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		
		if(convertView==null)
		{        
			//inflate the custom layout   
			LayoutInflater inflater = LayoutInflater.from(con);
			convertView=inflater.inflate(R.layout.driver_request_history, null);
			viewHolder=new ViewHolder();

			//cache the views
			viewHolder.tvDHSource=(TextView) convertView.findViewById(R.id.tvDHSource);
			viewHolder.tvDHDestination=(TextView) convertView.findViewById(R.id.tvDHDestination);
			viewHolder.tvDHPickUpTime=(TextView) convertView.findViewById(R.id.tvDHPickUpTime);	
			viewHolder.tvDHStatus=(TextView)convertView.findViewById(R.id.tvDHStatus);
			//link the cached views to the convertview
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		
		//set the data to be displayed
		viewHolder.tvDHSource.setText(myData.get(position).get("source").toString());
		viewHolder.tvDHDestination.setText(myData.get(position).get("destination").toString());
		viewHolder.tvDHPickUpTime.setText(myData.get(position).get("pickuptime").toString());
		viewHolder.tvDHStatus.setText(myData.get(position).get("status").toString());
		
		//return the view to be displayed
		return convertView;
	}

}
