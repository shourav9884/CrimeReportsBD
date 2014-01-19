package com.example.crimereportsbd;


import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.crimereportsbd.CategoryDialogFragment.CategoryDataInterface;
import com.example.crimereportsbd.DrawerExpandableListAdapter.CategoryFilterer;

public class ReportsListActivity extends DrawerActivity implements CategoryFilterer {

	private ListView reportsList;
	private ReportsListAdapter adapter;
	
	
	private ArrayList<DataContent> data;
	private ArrayList<String> selectedCat,allcats;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		super.name="with_cat";
		setContentView(R.layout.activity_reports_list);
//		super.setBack(R.layout.activity_main);
		super.initData();
		super.manageDrawer();
		listCategoryCheck = new ArrayList<Boolean>();
		data=new ArrayList<DataContent>();
		reportsList = (ListView)findViewById(R.id.reports_list);
		
		selectedCat=new ArrayList<String>();
		allcats=new ArrayList<String>();
		allcats.add("All");
		for(int i=0;i<StaticData.CATAGORIES.size();i++)
		{
			allcats.add(StaticData.CATAGORIES.get(i));
		}
		for(int i=0;i<allcats.size();i++)
		{
			if(i==0)
			{
				listCategoryCheck.add(i, true);
			}
			else
			{
				listCategoryCheck.add(i, false);
			}
			
		}
		
		
	}
	
	public void filterCat()
	{
		DialogFragment selectCategoryDialog = new CategoryDialogFragment(allcats);
		selectCategoryDialog.show(getSupportFragmentManager(), "Select Categories");	
	}
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		data=StaticData.data_content;
		adapter = new ReportsListAdapter(this,data);
		
		reportsList.setAdapter(adapter);
		
		reportsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub		
				
				Bundle b=new Bundle();
				System.out.println(position+" "+StaticData.data_content.size());
				DataContent d=data.get(position);
				for(int i=0;i<StaticData.data_content.size();i++)
				{
					if(StaticData.data_content.get(i).equals(d))
					{
						position=i;
					}
				}
				b.putInt("pos", position);
				
				
//				System.out.println("POsition "+position+" id "+id);
//				System.out.println("Title+position: "+JsonParsing.data_content.get(position).incident_title+" \nTitle+id: "+JsonParsing.data_content.get((int)id).incident_title);
				Intent intent = new Intent(ReportsListActivity.this, ViewReportActivity.class);
				intent.putExtras(b);
				ReportsListActivity.this.startActivity(intent);				
			}
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	
	void refreshList()
	{
		ArrayList<DataContent> toshow=new ArrayList<DataContent>();
		for(int i=0;i<StaticData.data_content.size();i++)
		{
			if(isToShow(StaticData.data_content.get(i)))
			{
				toshow.add(StaticData.data_content.get(i));
			}
		}
		System.out.println("To show "+toshow.size());
		data=toshow;
		
		adapter=new ReportsListAdapter(this, toshow);
		reportsList.setAdapter(adapter);
		
		
		
		
	}
	void updateBooleanList(ArrayList<String> cats)
	{
		ArrayList<Boolean> tmp=new ArrayList<Boolean>();
		if(cats.size()>0)
		{
			for(int i=0;i<listCategoryCheck.size();i++)
			{
				int flag=0;
				for(int j=0;j<cats.size();j++)
				{
					if(allcats.get(i).equals(cats.get(j)))
					{
						tmp.add(i, true);
						flag=1;
					}
				}
				if(flag==0)
				{
					tmp.add(i,false);
				}
			}
			if(cats.size()==1 && cats.get(0)=="All")
			{
				for(int i=0;i<listCategoryCheck.size();i++)
				{
					tmp.add(i, true);
				}
			}
		}
		else
		{
			for(int i=0;i<listCategoryCheck.size();i++)
			{
				tmp.add(i, true);
			}
		}
		listCategoryCheck=tmp;
	}
	boolean isToShow(DataContent d)
	{
		
		for(int i=0;i<listCategoryCheck.size();i++)
		{
			if(listCategoryCheck.get(i) == true)
			{
				for(int j=0;j<d.incident_cats.size();j++)
				{
					if(d.incident_cats.get(j).equals(allcats.get(i)))
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public void filterCategory(ArrayList<Boolean> categoryCheckList) {
		// TODO Auto-generated method stub
		listCategoryCheck = categoryCheckList;
		refreshList();
	}

}
