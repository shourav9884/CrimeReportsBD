package com.example.crimereportsbd;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.crimereportsbd.DrawerExpandableListAdapter.CategoryFilterer;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

public class SubscribeActivity extends Activity implements CategoryFilterer,GoogleMap.OnMarkerDragListener{
	
	private GridView categoryGridView;
	private CategoryGridViewAdapter adapter;
	private Button buttonSaveAlert;
	String dataToSend;
	private EditText emailText;
	
	SeekBar radiusSeekBar;
	TextView radiusTextView;
	private ArrayList<Boolean> listCategoryCheck;
	private List<String> categoryList;
	private ArrayList<NameValuePair> nameValue;
	private ProgressDialog pd;
	private int radius=20;
	GPSTracker gps;
	double lat,lon;
	private GoogleMap map;
	Marker m;
	CircleOptions rad_option;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subscribe);
		
		init();
		
		categoryGridView = (GridView)findViewById(R.id.gridview_subscribe_category);
		adapter = new CategoryGridViewAdapter(this, categoryList, listCategoryCheck, this);
		categoryGridView.setAdapter(adapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void init(){
		emailText=(EditText)findViewById(R.id.editText_subscribe_email);
		radiusSeekBar=(SeekBar)findViewById(R.id.radiusSeekBar);
		radiusTextView=(TextView)findViewById(R.id.radiusTextView);
		map=((MapFragment)getFragmentManager().findFragmentById(R.id.map_subscribe)).getMap();
		
		rad_option=new CircleOptions().strokeWidth(5).fillColor(Color.parseColor("#20FF7F50"));
		
		initiateMap();
		
		radiusSeekBar.setMax(100);
		radiusSeekBar.setProgress(radius);
		
		radiusSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				radiusTextView.setText("Radius: "+radius+" KM");
				rad_option.radius(radius*1000);
				map.clear();
				map.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).draggable(true));
				map.addCircle(rad_option);
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				radius=progress;
				
			}
		});
		
		radiusTextView.setText("Radius: "+20+"KM");
		buttonSaveAlert=(Button)findViewById(R.id.button_save_alert);
		
		buttonSaveAlert.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Alert a=new Alert();
				a.setEmail(emailText.getText().toString());
				a.setLat(lat);
				a.setLon(lon);
				ArrayList<String> toSendCats=new ArrayList<String>();
				if(listCategoryCheck.get(0))
				{
					for(int i=0;i<categoryList.size();i++)
					{
						toSendCats.add(categoryList.get(i));
					}
//					toSendCats=categoryList;
				}
				else
				{
				
					for(int i=0;i<categoryList.size();i++)
					{
						if(listCategoryCheck.get(i))
						{
							toSendCats.add(categoryList.get(i));
						}
					}
				}
				a.setCats(toSendCats);
				a.setRadius(radius);
				Gson gson=new Gson();
				dataToSend=gson.toJson(a);
				
				pd=ProgressDialog.show(SubscribeActivity.this,"","Subscribing...",true,false);
				nameValue=new ArrayList<NameValuePair>(1); 
				nameValue.add(new BasicNameValuePair("data", dataToSend));
//				nameValue.add(new BasicNameValuePair("data", ""+12));
				
				System.out.println(dataToSend);
				
				
				MyThread threadObj=new MyThread(handler, nameValue,Settings.URL+"subscribe.php");
				threadObj.start();
			}
		});
		categoryList = new ArrayList<String>();
		listCategoryCheck = new ArrayList<Boolean>();
		
		
		
//		categoryList = StaticData.CATAGORIES;
		categoryList.add("All");
		for(int i=0;i<StaticData.CATAGORIES.size();i++)
		{
			categoryList.add(StaticData.CATAGORIES.get(i));
		}
		for(int i = 0;i<categoryList.size();i++){
			if(i==0)listCategoryCheck.add(i, true);
			else listCategoryCheck.add(i, false);
		}
	}

	@Override
	public void filterCategory(ArrayList<Boolean> categoryCheckList) {
		// TODO Auto-generated method stub
		listCategoryCheck = categoryCheckList;
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
	private class Alert
	{
		private int radius;
		private double lat,lon;
		private String email;
		private ArrayList<String> cats;
		/**
		 * @return the radius
		 */
		public int getRadius() {
			return radius;
		}
		/**
		 * @param radius the radius to set
		 */
		public void setRadius(int radius) {
			this.radius = radius;
		}
		/**
		 * @return the lat
		 */
		public double getLat() {
			return lat;
		}
		/**
		 * @param lat the lat to set
		 */
		public void setLat(double lat) {
			this.lat = lat;
		}
		/**
		 * @return the lon
		 */
		public double getLon() {
			return lon;
		}
		/**
		 * @param lon the lon to set
		 */
		public void setLon(double lon) {
			this.lon = lon;
		}
		/**
		 * @return the email
		 */
		public String getEmail() {
			return email;
		}
		/**
		 * @param email the email to set
		 */
		public void setEmail(String email) {
			this.email = email;
		}
		/**
		 * @return the cats
		 */
		public ArrayList<String> getCats() {
			return cats;
		}
		/**
		 * @param cats the cats to set
		 */
		public void setCats(ArrayList<String> cats) {
			this.cats = cats;
		}
		
		
	}
	
	public void initiateMap()
	{
		
		
		if(map==null)
		{
			Toast.makeText(this.getApplicationContext(), "Maps not shown", Toast.LENGTH_LONG).show();
		}
		else
		{
			
			gps=new GPSTracker(this.getApplicationContext(),this);
			
			if(gps.canGetLocation())
			{
				Location l=gps.getLocation();
				if(lat==0&&lon==0)
				{
					lat=gps.latitude;
					lon=gps.longitude;
				}
				
				rad_option.center(new LatLng(lat, lon))
				
				.radius(radius*1000);
				map.addCircle(rad_option);
				Toast.makeText(this.getApplicationContext(), "Your location is - \nlat"+lat+" longi "+lon, Toast.LENGTH_LONG).show();
				System.out.println("Your location is - \nlat"+lat+" longi "+lon);
			}
			else
			{
				gps.showSettingsAlert();
			}
//			Toast.makeText(getApplicationContext(), "Maps", Toast.LENGTH_LONG).show();
			LatLng dhaka=new LatLng(lat, lon);
			MarkerOptions op=new MarkerOptions(); 
			op.position(dhaka).title("Pato").snippet("This is my Location");
			m=map.addMarker(op); 
			m.setDraggable(true);
			
//			markers.add(m);
			map.setOnMarkerDragListener(this);
			
			// Move the camera instantly to hamburg with a zoom of 15.
		    map.moveCamera(CameraUpdateFactory.newLatLngZoom(dhaka, 15));

		    // Zoom in, animating the camera.
		    map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
		}
	}
	@Override
	public void onMarkerDrag(Marker arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onMarkerDragEnd(Marker arg0) {
		// TODO Auto-generated method stub
//		m.remove();
		map.clear();
		LatLng test;
		test=arg0.getPosition();
		arg0.setDraggable(true);
		MarkerOptions op=new MarkerOptions(); 
		op.position(test).title("New").snippet("This is my Location");
		
		m=map.addMarker(op);
		lat=m.getPosition().latitude;
		lon=m.getPosition().longitude;
		rad_option.radius(radius*1000).center(new LatLng(lat, lon));
		map.addCircle(rad_option);
		m.setDraggable(true);
	}
	@Override
	public void onMarkerDragStart(Marker arg0) {
		// TODO Auto-generated method stub
		
	}
	

}

