package com.example.crimereportsbd;


/*
 * 1. Location editText Multiline
 * 2. MAP NOT ADDED
 * 3. Icons Set not finalized
 * 4. Button Selector not set
 * 
 */



import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import org.json.JSONException;




import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SubmitReportFragment_2 extends Fragment implements GoogleMap.OnMarkerDragListener{
	IncidentMedia testMedia;
	IncidentPerson p;
	Button buttonBack;
	Button buttonSubmit;
	Button cameraOn,buttonNewsLink,buttonVideoLink;
	int linkType = 0;
	EditText locationName;
//	EditText titleEt;
	String title,desc,date;
	ArrayList<String> cats;
	InsertReport insObj;
	ArrayList<Marker> markers;
	GPSTracker gps;
	double lat=0,lon=0;
	Marker m;
	String imageStr,newsLink="",videoLink="";
	private GoogleMap map;
	private static final int CAMERA_REQUEST = 1888;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.submit_report_fragment_2,
	        container, false);
		
		buttonBack = (Button)view.findViewById(R.id.button_submit_back);
	    buttonSubmit = (Button)view.findViewById(R.id.button_submit_confirm);
	    
	    locationName=(EditText)view.findViewById(R.id.editText_location);
	    
	    cameraOn=(Button)view.findViewById(R.id.button_upload_photo);
	    buttonNewsLink=(Button)view.findViewById(R.id.button_news_link);
	    buttonVideoLink=(Button)view.findViewById(R.id.button_video_link);
	    Activity a=getActivity();
	    
	    
	    map=((MapFragment)a.getFragmentManager().findFragmentById(R.id.map_add_pin)).getMap();
	    markers=new ArrayList<Marker>();
	    initiateMap();
//	    titleEt=(EditText)view.findViewById(R.id.editText_title);
	    
	    buttonBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SubmitReportActivity.getViewPager().setCurrentItem(0);
			}
		});
	    
	    buttonSubmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stubi
//				String title=titleEt.getText().toString();
				DataContent dataCont=null;
				try {
					dataCont=new DataContent(null);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dataCont.incident_date=date; 
				dataCont.incident_title=title;
//				dataCont.incident_category=cat;
				dataCont.incident_description=desc;
				
				String locName=locationName.getText().toString();
				
				ArrayList<IncidentLocation> locations=new ArrayList<IncidentLocation>();
				IncidentLocation loc1=new IncidentLocation();
				loc1.setLatitude(lat);
				loc1.setLongitude(lon);
				loc1.setLocationName(locName);
				locations.add(loc1);
				
				dataCont.locations=locations;
				
				ArrayList<IncidentMedia> media=new ArrayList<IncidentMedia>();
				if(testMedia!=null)
				{
					media.add(testMedia);
					dataCont.media=media;
				}
				if(!newsLink.equals(""))
				{
					IncidentMedia newsMedia=new IncidentMedia();
					newsMedia.setMedia_type(4);
					newsMedia.setMedia_content(newsLink);
					media.add(newsMedia);
					dataCont.media=media;
				}
				if(!videoLink.equals(""))
				{
					IncidentMedia videoMedia=new IncidentMedia();
					videoMedia.setMedia_type(2);
					videoMedia.setMedia_content(videoLink);
					media.add(videoMedia);
					dataCont.media=media;
				}
				if(p!=null)
				{
					dataCont.person=p;
				}
				
				
				dataCont.incident_cats=cats;
				
				
				insObj.addReport(dataCont);
				
						
				Toast.makeText(getActivity(), "Report Submitted"+title+" "+desc, Toast.LENGTH_SHORT).show();				
			}
		});
	    
	    cameraOn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
	            getActivity().startActivityForResult(cameraIntent, CAMERA_REQUEST);
//	            return true;
				
			}
		});

	    buttonNewsLink.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new ExternalLinkDialog().show(getFragmentManager(), "External News Link");
				linkType = 1;
			}
		});
	    
	    buttonVideoLink.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new ExternalLinkDialog().show(getFragmentManager(), "External Video Link");
				linkType = 2;
			}
		});
		
	    return view;
	  }
	public void getDataFromfirst(String title,String desc,String date,ArrayList<String> cat,IncidentPerson p)
	{
		this.title=title;
		this.desc=desc;
		this.date=date;
		this.cats=cat;
		this.p=p;
	}
	
	public interface InsertReport
	{
		public void addReport(DataContent d);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.DialogFragment#onAttach(android.app.Activity)
	 */
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		if (activity instanceof InsertReport) {
		      insObj = (InsertReport) activity;
		    } else {
		      throw new ClassCastException(activity.toString()
		          + " must implemenet MyListFragment.OnItemSelectedListener");
		    }
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.DialogFragment#onDetach()
	 */
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		insObj=null;
	}
	
	public void getImageStr(String image)
	{
		testMedia=new IncidentMedia();
		testMedia.setMedia_type(1);
		testMedia.setMedia_content(image);
	}
	public void initiateMap()
	{
		
		
		if(map==null)
		{
			Toast.makeText(getActivity().getApplicationContext(), "Maps not shown", Toast.LENGTH_LONG).show();
		}
		else
		{
			
			gps=new GPSTracker(getActivity().getApplicationContext(),getActivity());
			
			if(gps.canGetLocation())
			{
				Location l=gps.getLocation();
				if(lat==0&&lon==0)
				{
					lat=gps.latitude;
					lon=gps.longitude;
				}
				Toast.makeText(getActivity().getApplicationContext(), "Your location is - \nlat"+lat+" longi "+lon, Toast.LENGTH_LONG).show();
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
			
			markers.add(m);
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
		m.remove();
		LatLng test;
		test=arg0.getPosition();
		arg0.setDraggable(true);
		MarkerOptions op=new MarkerOptions(); 
		op.position(test).title("New").snippet("This is my Location");
		
		m=map.addMarker(op);
		lat=m.getPosition().latitude;
		lon=m.getPosition().longitude;
		m.setDraggable(true);
	}
	@Override
	public void onMarkerDragStart(Marker arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void getLink(String link)
	{
		if(linkType==1)
		{
			newsLink=link;
		}
		else if(linkType==2)
		{
			videoLink=link;
		}
	}
}

