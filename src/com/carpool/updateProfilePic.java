package com.carpool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.carpool.data.SharedData;
import com.carpool.utils.BaseActivity;
import com.carpool.utils.FileDialog;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class updateProfilePic extends BaseActivity {

	private static String TAG="UploadProfilePic";
	String testing="testing";
	String selected_file_name=null;
	private Bitmap bitmap;
	String message = "",responseBody="";
    HttpResponse response = null;
	@Override
	public int getLayoutXML() {
		return R.layout.update_profile_pic;
	}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Button btnBack=(Button)findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				finish();
			}
		});
		
		Button btnNext=(Button)findViewById(R.id.btnNext);
		btnNext.setVisibility(View.GONE);
		
		TextView tvPhotoURL=(TextView)findViewById(R.id.tvPhotoURL);
		tvPhotoURL.setVisibility(View.GONE);

		Button btnUpload=(Button)findViewById(R.id.btnUpload);
		btnUpload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				select_file_to_share();
			}
			private void select_file_to_share() {
				FileDialog fileDialog;
				File mPath = new File(Environment.getExternalStorageDirectory() + "//DIR//");
				fileDialog = new FileDialog(updateProfilePic.this, mPath);
				fileDialog.addFileListener(new FileDialog.FileSelectedListener() {
					public void fileSelected(File file) {
						String temp_file_name=file.toString();
						String filenameArray[] = temp_file_name.split("\\.");
						String extension = filenameArray[filenameArray.length-1];
						String[] bob = { "jpg", "JPG", "png", "PNG", "gif", "GIF", "bmp", "BMP", "webp", "WebP" };

						if (Arrays.asList(bob).contains(extension)) {
							selected_file_name=temp_file_name;
							BitmapDrawable d = new BitmapDrawable(getResources(), file.getAbsolutePath());
							bitmap= d.getBitmap();

							if (bitmap == null) 
								ShowSimpleDialog("Alert!", "Sorry! Unable to get the image.");
							else
							{
								showWait();
								new UploadTask().execute(bitmap);
								
							}
						}
						else{
							ShowSimpleDialog("Error!","Not a valid image file");
						}

					}
				});
				fileDialog.showDialog();
			}			
		});
    }
    
class UploadTask extends AsyncTask<Bitmap, Void, Void> {
		
		protected Void doInBackground(Bitmap... bitmaps) {
			if (bitmaps[0] == null)
				return null;
			setProgress(0);
			
			Bitmap bitmap = bitmaps[0];
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); // convert Bitmap to ByteArrayOutputStream
			InputStream in = new ByteArrayInputStream(stream.toByteArray()); // convert ByteArrayOutputStream to ByteArrayInputStream
			
			DefaultHttpClient httpclient = new DefaultHttpClient();
			try {
				HttpPost httppost = new HttpPost(
						SharedData.SERVER_URL+"UpdateProfilePicServlet"); // server

				MultipartEntity reqEntity = new MultipartEntity();
				reqEntity.addPart("user_id",String.valueOf(SharedData.mMySharedPref.getUserId()));
				reqEntity.addPart("myFile",
						System.currentTimeMillis() + ".jpg", in);
				httppost.setEntity(reqEntity);
	   			Log.i(TAG, "request " + httppost.getRequestLine());
				//HttpResponse response = null;
				try {
					response = httpclient.execute(httppost);
					responseBody = EntityUtils.toString(response.getEntity());
					Log.d("result",responseBody);
				} catch (ClientProtocolException e) {
					
					//dialog.dismiss();
					e.printStackTrace();
				} catch (IOException e) {
					//dialog.dismiss();
					
					e.printStackTrace();
				}
				try {
					if (response != null)
						Log.i(TAG, "response " + response.getStatusLine().toString());
				} finally {

				}
			} finally {

			}

			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					//dialog.dismiss();
					
					e.printStackTrace();
				}
			}

			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}

			return null;
		}
		
		@Override
		protected void onProgressUpdate(Void... values) {
			
			super.onProgressUpdate(values);
		}
		
		@Override
		protected void onPostExecute(Void result) {
			
			//dialog.dismiss();
			super.onPostExecute(result);
			TextView tvPhotoURL=(TextView)findViewById(R.id.tvPhotoURL);
			tvPhotoURL.setText(responseBody);
			if(response!= null){
				//alert.showAlertDialog(MeterReadingActivity.this,"Detail is!","Sucessfull",false);
				hideWait();
				Toast.makeText(getApplicationContext(), "Profile pic updated successfully", Toast.LENGTH_SHORT).show();
				Intent i=new Intent(updateProfilePic.this,UserHome.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
			else{
				hideWait();
				ShowSimpleDialog("Alert!", ""+response);
			}
				//alert.showAlertDialog(MeterReadingActivity.this,"Detail is!","Fail",false);
							
		}
 }
    
    
}
