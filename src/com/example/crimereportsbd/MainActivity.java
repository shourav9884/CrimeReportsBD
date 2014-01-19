package com.example.crimereportsbd;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts.Data;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;


import com.dibosh.experiments.android.support.customfonthelper.AndroidCustomFontSupport;
import com.example.crimereportsbd.DrawerExpandableListAdapter.CategoryFilterer;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends DrawerActivity implements CategoryFilterer {
	
//	this.name="";
	private String[] mItems;
	private DrawerLayout mDrawerLayout;
	//private ListView mDrawerList;
	private ExpandableListView mDrawerList;
	private CharSequence appTitle;
	private CharSequence menuTitle;
	private ActionBarDrawerToggle mDrawerToggle;

		
	private GoogleMap map;
	static final LatLng HAMBURG = new LatLng(53.558, 9.927);
	static final LatLng KIEL = new LatLng(53.551, 9.993);
	
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.name="with_cat";
		setContentView(R.layout.activity_main); 
//		super.setBack(R.layout.activity_main);
		super.initData();
		super.manageDrawer();	
		
//		initData();
		
		
		
		//mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		
		
	}
	
	@Override
	  protected void onResume() {
	     
	     super.onResume();
	     manageMap();
	     
	  }
	
	
	void manageMap()
	{
		//Map Codes
		map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
		if(map==null){
			Toast.makeText(this, "Map not Loaded", Toast.LENGTH_LONG).show();
		}else {
			Toast.makeText(this, "Map Loaded", Toast.LENGTH_LONG).show();
		
		
//				Marker keil = map.addMarker(new MarkerOptions().position(KIEL)
//						.title("KIEL")
//						.snippet("Kiel is cool"));
			
		map.setInfoWindowAdapter(new MyInfoWindowAdapter(getLayoutInflater(), getApplicationContext(), this));
		int l=StaticData.data_content.size();
		if(l>0)
		{
		LatLng testll=new LatLng(StaticData.data_content.get(0).locations.get(0).getLatitude(), StaticData.data_content.get(0).locations.get(0).getLongitude());
		for(int i=0;i<l;i++)
		{
			LatLng testLatLng=new LatLng(StaticData.data_content.get(i).locations.get(0).getLatitude(), StaticData.data_content.get(i).locations.get(0).getLongitude());
			
			MarkerOptions testOPtion=new MarkerOptions()
				.position(testLatLng)
				.title(StaticData.data_content.get(i).incident_title)
				.icon(BitmapDescriptorFactory.fromResource(getImage(StaticData.data_content.get(i).incident_cats.get(0))));
			Marker hamburg = map.addMarker(testOPtion);
			
		}
		
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(testll, 10));
		
		map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);	
		map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
			
			@Override
			public void onInfoWindowClick(Marker arg0) {
				// TODO Auto-generated method stub
				Log.d("marker", arg0.getId());
				String id=arg0.getId().substring(1);
				int id1=Integer.parseInt(id);
//						String title=pinlist.get(id1).getName();
				Bundle b=new Bundle();
//						b.putParcelable("Object", pinlist.get(id1));
				b.putInt("pos", id1);
				Intent intent=new Intent(MainActivity.this,ViewReportActivity.class);
				intent.putExtras(b);
				startActivity(intent);
				
			}
		});
		}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	} 
	
	
	
	/*private void selectItem(int position){
		Toast.makeText(this, R.string.app_name, Toast.LENGTH_SHORT).show();
		
		mDrawerList.setItemChecked(position, true);
		setTitle(mItems[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}*/
	
	@Override
	public void setTitle(CharSequence title){
		//getActionBar().setTitle(title);
	}
	
	/*private class DrawerItemClickListener implements ListView.OnItemClickListener{
		@Override
		public void onItemClick(AdapterView parent, View view, int position, long id){
			selectItem(position);
		}
	}*/
	
	
	
	
	
	private Integer getImage(String catTitle)
	{
		if(catTitle.equals("Cycle Theft"))
		{
			return R.drawable.cycle_theft;
		}
		else if(catTitle.equals("Rape"))
		{
			return R.drawable.rape;
		}
		else if(catTitle.equals("Dowry"))
		{
			return R.drawable.dowry;
		}
		else if(catTitle.equals("Murder"))
		{
			return R.drawable.murder;
		}
		else if(catTitle.equals("Fraud"))
		{
			return R.drawable.fraud;
		}
		else if(catTitle.equals("Violence on women & children"))
		{
			return R.drawable.woman_violanc;
		}
		else if(catTitle.equals("Violence on maid"))
		{
			return R.drawable.maid_violance;
		}
		else if(catTitle.equals("Corruption"))
		{
			return R.drawable.corruption;
		}
		else if(catTitle.equals("Mugging"))
		{
			return R.drawable.mugging;
		}
		else if(catTitle.equals("Extorsion"))
		{
			return R.drawable.extorsion;
		}
		else if(catTitle.equals("Theft"))
		{
			return R.drawable.other_theft;
		}
		else if(catTitle.equals("Fighting"))
		{
			return R.drawable.fighting;
		}
		else if(catTitle.equals("Campus Violence"))
		{
			return R.drawable.campus;
		}
		else if(catTitle.equals("Drugs"))
		{
			return R.drawable.drugs;
		}
		else if(catTitle.equals("Black Marketeering"))
		{
			return R.drawable.black_market;
		}
		else if(catTitle.equals("Car Theft"))
		{
			return R.drawable.car_theft;
		}
		else if(catTitle.equals("Mobile Theft"))
		{
			return R.drawable.mobile_theft;
		}
		else if(catTitle.equals("Laptop Theft"))
		{
			return R.drawable.laptop_theft;
		}
		else if(catTitle.equals("Electronic Gadget Theft"))
		{
			return R.drawable.electronic_theft;
		}
		else if(catTitle.equals("Eve Teasing"))
		{
			return R.drawable.eve_teasing;
		}
		else if(catTitle.equals("Pickpocketing"))
		{
			return R.drawable.pickpocket;
		}
		
		
		return R.drawable.other;
		
	}
	
	
	@Override
	public void filterCategory(ArrayList<Boolean> categoryCheckList) {
		// TODO Auto-generated method stub
		listCategoryCheck = categoryCheckList;
		refreshMapData();
	}
	
	
	private void refreshMapData()
	{
		if(map!=null)
		{
			ArrayList<DataContent> toShowData=new ArrayList<DataContent>();
			map.clear();
			map.setInfoWindowAdapter(new MyInfoWindowAdapter(getLayoutInflater(), getApplicationContext(), this));
			int l=StaticData.data_content.size();
			for(int m=0;m<l;m++)
			{
				if(isToShow(StaticData.data_content.get(m)))
				{
					toShowData.add(StaticData.data_content.get(m));
				}
			}
			final ArrayList<DataContent> tmpContecnt=toShowData;
			ArrayList<Marker> markers=new ArrayList<Marker>();
			System.out.println(toShowData.size() +" number of markers");
			if(toShowData.size()>0)
			{
				LatLng testll=new LatLng(toShowData.get(0).locations.get(0).getLatitude(), toShowData.get(0).locations.get(0).getLongitude());
				for(int i=0;i<toShowData.size();i++)
				{
					LatLng testLatLng=new LatLng(toShowData.get(i).locations.get(0).getLatitude(), toShowData.get(i).locations.get(0).getLongitude());
					
					MarkerOptions testOPtion=new MarkerOptions()
						.position(testLatLng)
						.title(toShowData.get(i).incident_title)
						.icon(BitmapDescriptorFactory.fromResource(getImage(toShowData.get(i).incident_cats.get(0))));
					Marker hamburg = map.addMarker(testOPtion);
					markers.add(hamburg);
					
				}
				
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(testll, 10));
			}
			final ArrayList<Marker> finMarkers=markers;
			map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);	
			map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
				
				@Override
				public void onInfoWindowClick(Marker arg0) {
					// TODO Auto-generated method stub
					Log.d("marker", arg0.getId());
					String id=arg0.getId().substring(1);
					int id1=Integer.parseInt(id);
//					id1=toShowData.get(id1).id;
//					String title=pinlist.get(id1).getName();
					Bundle b=new Bundle();
//					b.putParcelable("Object", pinlist.get(id1));
					
					
					System.out.println(id1);
					int data_id=0;
					for(int i=0;i<finMarkers.size();i++)
					{
						if(finMarkers.get(i).equals(arg0))
						{
							data_id=getIdFromList(tmpContecnt.get(i));
//							System.out.println("IT is the marker id "+getIdFromList(tmpContecnt.get(i))+""+tmpContecnt.get(i).incident_title);
						}
					}
					b.putInt("pos", data_id);
					Intent intent=new Intent(MainActivity.this,ViewReportActivity.class);
					intent.putExtras(b);
					startActivity(intent);
					
				}
			});
		}
	}
	int getIdFromList(DataContent d)
	{
		for(int i=0;i<StaticData.data_content.size();i++)
		{
			if(StaticData.data_content.get(i).equals(d))
			{
				return i;
			}
		}
		return -1;
	}
	private boolean isToShow(DataContent d)
	{
		if(listCategoryCheck.get(0))
		{
			return true;
		}
		for(int i=0;i<listCategoryCheck.size();i++)
		{
			if(listCategoryCheck.get(i) == true)
			{
				for(int j=0;j<d.incident_cats.size();j++)
				{
					if(d.incident_cats.get(j).equals(listDataChild.get(listDataHeader.get(2)).get(i)))
					{
						return true;
					}
				}
			}
		}
		return false;
	}
}
