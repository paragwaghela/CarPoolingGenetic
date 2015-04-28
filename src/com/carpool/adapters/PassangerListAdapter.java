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

public class PassangerListAdapter  extends ArrayAdapter<HashMap<String, Object>>{
	ArrayList<HashMap<String, Object>> myData;
	Context con;
	public PassangerListAdapter(Context context, int textViewResourceId,ArrayList<HashMap<String, Object>> abc) {
		super(context, textViewResourceId,abc);
		// TODO Auto-generated constructor stub
		Log.d("MyData",""+abc);
		myData=abc;
		con=context;
	}
	
	//class for caching the views in a row  
	private class ViewHolder
	{
		TextView pName,pContact,pSource,pDest;
	}
	
	ViewHolder viewHolder;
	
	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		Log.d("Inside View","Here");
		if(convertView==null)
		{        
			//inflate the custom layout   
			LayoutInflater inflater = LayoutInflater.from(con);
			convertView=inflater.inflate(R.layout.passenger_detail, null);
			viewHolder=new ViewHolder();

			//cache the views
			viewHolder.pName=(TextView) convertView.findViewById(R.id.pName);
			viewHolder.pContact=(TextView) convertView.findViewById(R.id.pContact);
			viewHolder.pSource=(TextView) convertView.findViewById(R.id.pSource);	
			viewHolder.pDest=(TextView) convertView.findViewById(R.id.pDest);	
			
			//link the cached views to the convertview
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		Log.d("Pname",myData.get(position).get("pname").toString());
		//set the data to be displayed
		viewHolder.pName.setText(myData.get(position).get("pname").toString());
		viewHolder.pContact.setText(myData.get(position).get("pcontact").toString());
		viewHolder.pSource.setText(myData.get(position).get("psource").toString());
		viewHolder.pDest.setText(myData.get(position).get("pdest").toString());
		
		
		
		//return the view to be displayed
		return convertView;
	}

}
