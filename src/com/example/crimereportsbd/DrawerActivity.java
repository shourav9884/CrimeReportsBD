package com.example.crimereportsbd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

public class DrawerActivity extends FragmentActivity {
	
	public String name="";
	private String[] mItems;
	private DrawerLayout mDrawerLayout;
	//private ListView mDrawerList;
	private ExpandableListView mDrawerList;
	private CharSequence appTitle;
	private CharSequence menuTitle;
	private ActionBarDrawerToggle mDrawerToggle;
	
	private DrawerExpandableListAdapter drawerExpandableListAdapter;
	public List<String> listDataHeader;
	public HashMap<String, List<String>> listDataChild;
	
	public ArrayList<Boolean> listCategoryCheck;
	
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		initData();
//		manageDrawer();
	}
	
	public void setBack(Integer layout)
	{
		setContentView(layout);
	}
	
	public void manageDrawer()
	{
		//Navigation Drawer Codes
				appTitle = "CrimeReportsBD";
				menuTitle = "Menu";
				
				mItems = new String[]{"Home","Reports","Submit"};
				mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
				mDrawerLayout.setScrimColor(Color.TRANSPARENT);
				mDrawerList = (ExpandableListView)findViewById(R.id.expandable_list_view);
				 
				mDrawerList.setAdapter(new DrawerExpandableListAdapter(DrawerActivity.this, DrawerActivity.this, listDataHeader, listDataChild, listCategoryCheck));
				
				mDrawerList.setOnGroupClickListener(new DrawerGroupClickListener());
				mDrawerList.setOnChildClickListener(new DrawerChildClickListener());
				
				mDrawerToggle = new ActionBarDrawerToggle(this, 
						mDrawerLayout, 
						R.drawable.ic_drawer_app,
						R.string.drawer_open, 
						R.string.drawer_close){
					
					@Override
			        public void onDrawerSlide ( View drawerView, float slideOffset )
			        {
			            bringDrawerToFront();
			            super.onDrawerSlide( drawerView, slideOffset );
			        }

			        @Override
			        public void onDrawerStateChanged ( int newState )
			        {
			            bringDrawerToFront();
			            super.onDrawerStateChanged( newState );
			        }
			        private void bringDrawerToFront()
			        {
			            mDrawerList.bringToFront();
			            mDrawerLayout.requestLayout();
			        }           
					
					public void onDrawerClosed(View view){
						getActionBar().setTitle(appTitle);
						filterCategories();
					}
					
					public void onDrawerOpened(View drawerView){
						getActionBar().setTitle(menuTitle);
					}
				};
				
				mDrawerToggle.setDrawerIndicatorEnabled(true);
				mDrawerLayout.setDrawerListener(mDrawerToggle);
				
				getActionBar().setDisplayHomeAsUpEnabled(true);
				getActionBar().setHomeButtonEnabled(true);
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		System.out.println(item.getItemId());
		 if (item.getItemId() == android.R.id.home) {
			 System.out.println("Home Menu");
			if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
	            mDrawerLayout.closeDrawer(mDrawerList);
	        } else {
	            mDrawerLayout.openDrawer(mDrawerList);
	        }
		 }
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState){
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig){
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	public void initData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();
		listCategoryCheck = new ArrayList<Boolean>();
		
		listDataHeader.add("Home");
		listDataHeader.add("Report");
		if(name.equals("with_cat"))
		{
			listDataHeader.add("Category");
			List<String> categoryChild = new ArrayList<String>();
			categoryChild.add("All");
			for(int i=0;i<StaticData.CATAGORIES.size();i++)
			{
				categoryChild.add(StaticData.CATAGORIES.get(i));
				
			}
			for(int i=0;i<categoryChild.size();i++)
			{
				if(i==0)listCategoryCheck.add(i, true);
				else listCategoryCheck.add(i, false);
			}
			listDataChild.put(listDataHeader.get(2), categoryChild);
		}
		listDataHeader.add("Subscription");
		listDataHeader.add("Feedback");
		listDataHeader.add("About");
		listDataHeader.add("Help");
		
				
		List<String> reportChild = new ArrayList<String>();
		reportChild.add("Submit");
		reportChild.add("View");
		
		
		
		listDataChild.put(listDataHeader.get(1), reportChild);
		
		
		
	}
	private ArrayList<Boolean> filterCategories(){
		String tmp = "";
		
		for(int i=0;i<listCategoryCheck.size();i++){
			if(listCategoryCheck.get(i) == true){
				tmp += listDataChild.get(listDataHeader.get(2)).get(i) + ";";
			}
		}
//		refreshMapData();
		Toast.makeText(getApplicationContext(), tmp, Toast.LENGTH_SHORT).show();
		return listCategoryCheck;
	}
	private class DrawerGroupClickListener implements ExpandableListView.OnGroupClickListener{

		@Override
		public boolean onGroupClick(ExpandableListView parent, View v,
				int groupPosition, long id) {
			// TODO Auto-generated method stub
			
			/*if(listDataHeader.get(groupPosition) == "Home"){
				Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
			}*/
			
			switch (groupPosition){
				case 0:
					Intent intent_home = new Intent(DrawerActivity.this, MainActivity.class);
					intent_home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					DrawerActivity.this.startActivity(intent_home);
					mDrawerLayout.closeDrawers();
					break;
				case 3:
					Intent intent1 = new Intent(DrawerActivity.this, SubscribeActivity.class);
					intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					DrawerActivity.this.startActivity(intent1);
					mDrawerLayout.closeDrawers();
					break;
				case 4:
					Intent intent = new Intent(DrawerActivity.this, FeedbackActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					DrawerActivity.this.startActivity(intent);
					mDrawerLayout.closeDrawers();
					break;
				default:
						break;
			}
			
			return false;
		}
		
	}
	
	private class DrawerChildClickListener implements ExpandableListView.OnChildClickListener{

		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			// TODO Auto-generated method stub
			
			if(groupPosition == 1){
				
				if(childPosition == 0){
					Intent intent = new Intent(DrawerActivity.this, SubmitReportActivity.class);
					DrawerActivity.this.startActivity(intent);
					mDrawerLayout.closeDrawers();
				}
				
				if(childPosition == 1){
					Intent intent = new Intent(DrawerActivity.this, ReportsListActivity.class);
					DrawerActivity.this.startActivity(intent);
					mDrawerLayout.closeDrawers();
				}				
			}
			
			
			return true;
		}
		
	}
}
