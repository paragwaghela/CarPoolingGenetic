package com.carpool.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.carpool.R;

//define your custom adapter
public class SlideMenuListAdapter extends ArrayAdapter<String>//ArrayAdapter<HashMap<String, Object>>
{
	//ArrayList<HashMap<String, Object>> myData;
	ArrayList<String> myData;
	ArrayList<Integer> myImages;
	Context con;
	public SlideMenuListAdapter(Context context, int textViewResourceId,ArrayList<String> abc,ArrayList<Integer> images){//ArrayList<HashMap<String, Object>> abc) {

		//let android do the initializing :)
		super(context, textViewResourceId, abc);
		myData=abc;
		con=context;
		myImages=images;
		//viewMaster=new ArrayList<ViewHolder>();
	}


	//class for caching the views in a row  
	private class ViewHolder
	{
		TextView name;
		ImageView imgUserPic;
	}
	
	//ArrayList<ViewHolder> viewMaster;
	ViewHolder viewHolder;
	
	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		
		if(convertView==null)
		{        
			//inflate the custom layout   
			LayoutInflater inflater = LayoutInflater.from(con);
			convertView=inflater.inflate(R.layout.menu_list_item, null);
			viewHolder=new ViewHolder();

			//cache the views
			viewHolder.imgUserPic=(ImageView) convertView.findViewById(R.id.imgUserPic);
			viewHolder.name=(TextView) convertView.findViewById(R.id.txtUName);
			
			//link the cached views to the convertview
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		
		//set the data to be displayed
		viewHolder.name.setText(myData.get(position));
		viewHolder.imgUserPic.setImageResource(myImages.get(position));
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(40, 40);
		viewHolder.imgUserPic.setLayoutParams(layoutParams);
		//viewMaster.add(viewHolder);
		
		//return the view to be displayed
		return convertView;
	}
}