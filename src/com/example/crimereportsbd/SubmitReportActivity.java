package com.example.crimereportsbd;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.widget.Toast;

import com.example.crimereportsbd.CategoryDialogFragment.CategoryDataInterface;
import com.example.crimereportsbd.DatePickerFragment.DateHandler;
import com.example.crimereportsbd.ExternalLinkDialog.LinkHandler;
import com.google.gson.Gson;

public class SubmitReportActivity extends FragmentActivity implements ExternalLinkDialog.LinkHandler,CategoryDataInterface,DateHandler,SubmitReportFragment_1.DataHandler,SubmitReportFragment_2.InsertReport {
	
	public Activity a;
	public static ViewPager pager;
	public static SubmitReportPagerAdapter pagerAdapter;
	private static final int CAMERA_REQUEST = 1888;
	String dataToSend;
	ArrayList<NameValuePair> nameValue;
	private ProgressDialog pd;
	Date date;
	public ArrayList<String> selectedCategories;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submit_report);
		
		getActionBar().hide();
		
		a=this;
		
		pagerAdapter = new SubmitReportPagerAdapter(getSupportFragmentManager());
		pager = (ViewPager)findViewById(R.id.pager);
		pager.setAdapter(pagerAdapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onBackPressed(){
		if(pager.getCurrentItem()==0){
			super.onBackPressed();
		}
		else {
			pager.setCurrentItem(0, true);
		}
	}
	
	public static ViewPager getViewPager(){
		return pager;
	}

	@Override
	public void addData(String title, String description, String date,
			ArrayList<String> cat,IncidentPerson p) {
		// TODO Auto-generated method stub
		SubmitReportFragment_2 fragment=(SubmitReportFragment_2)pagerAdapter.getItem(pager.getCurrentItem()+1);
		fragment.getDataFromfirst(title,description,date,cat,p);
		
	}

	@Override
	public void addReport(DataContent d) {
		// TODO Auto-generated method stub
		
		System.out.println(d.incident_title+" "+d.incident_description+" "+d.incident_date);
		Gson gson = new Gson();
		dataToSend=gson.toJson(d);  
		
//		String Url="http://10.0.2.2/crime/ins-test.php";
		
		sendData();
		
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// TODO Auto-generated method stub
		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {  
            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            image.setImageBitmap(photo);
            String imageStr=imageToString(photo);
            SubmitReportFragment_2 fragment=(SubmitReportFragment_2)pagerAdapter.getItem(pager.getCurrentItem());
            fragment.getImageStr(imageStr);
           
        }
	}
	String imageToString(Bitmap photo)
	{
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		
		photo.compress(Bitmap.CompressFormat.PNG, 100, bytes);
		      

		byte[] ba = bytes.toByteArray();

		String ba1 = Base64.encodeBytes(ba);
		return ba1;
	}
    public void sendData()
    {
    	pd=ProgressDialog.show(SubmitReportActivity.this,"","Loading...",true,false);
		nameValue=new ArrayList<NameValuePair>(1); 
		nameValue.add(new BasicNameValuePair("data", dataToSend));
//		nameValue.add(new BasicNameValuePair("data", ""+12));
		
		System.out.println(dataToSend);
		
		
		MyThread threadObj=new MyThread(handler, nameValue,Settings.URL+"ins-test.php");
		threadObj.start();
    }
	  
	Handler handler=new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			JSONObject responseObj=null;
			pd.dismiss();
			Bundle b=msg.getData();
			System.out.println(b.toString());
		}
	};

	@Override
	public void setPickerDate(Date date) {
		// TODO Auto-generated method stub
		this.date = date;
		Toast.makeText(getApplicationContext(), date.toString(), Toast.LENGTH_SHORT).show();
		SubmitReportFragment_1 fragment = (SubmitReportFragment_1)pagerAdapter.getItem(0);
		fragment.setDateText(date);
		
	}

	@Override
	public void setSelectedCategory(ArrayList<String> title) {
		// TODO Auto-generated method stub
		selectedCategories = title;
		SubmitReportFragment_1 fragment = (SubmitReportFragment_1)pagerAdapter.getItem(0);
		fragment.setSelectedCategories(selectedCategories);
		
	}

	@Override
	public void setLink(String link, int type) {
		// TODO Auto-generated method stub
		SubmitReportFragment_2 fragment = (SubmitReportFragment_2)pagerAdapter.getItem(pager.getCurrentItem());
		fragment.getLink(link);
	}

	
    
}
