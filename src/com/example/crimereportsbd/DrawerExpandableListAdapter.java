package com.example.crimereportsbd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class DrawerExpandableListAdapter extends BaseExpandableListAdapter{
	
	private Context context;
	private List<String> _listDataHeader;
	private HashMap<String, List<String>> _listDataChild;	
	private ArrayList<Boolean> _listCategoryCheck;
	private Activity activity;
	
	public DrawerExpandableListAdapter(Activity activity, Context context, 
			List<String> listDataHeader, 
			HashMap<String, List<String>> listDataChild,
			ArrayList<Boolean> listCategoryCheck){
		
		this.activity = activity;
		this.context = context;
		this._listDataChild = listDataChild;
		this._listDataHeader = listDataHeader;
		this._listCategoryCheck = listCategoryCheck;
	}		

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		if(getChildrenCount(groupPosition)==0)return null;
		
		return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		if(getChildrenCount(groupPosition)==0)return 0;
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {
		// TODO Auto-generated method stub
		final int childPos = childPosition;
		final String childText = (String)getChild(groupPosition, childPosition);
		final CategoryFilterer filterer = (CategoryFilterer)activity;
		LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//if(convertView==null){
				
			if(groupPosition==2){
				
				convertView = inflater.inflate(R.layout.drawer_category_list_item, null);
				TextView txtListChild = (TextView)convertView.findViewById(R.id.drawer_category_child_text);
				txtListChild.setText(childText);
				CheckBox categoryCheckBox = (CheckBox)convertView.findViewById(R.id.category_checkbox);
				
				categoryCheckBox.setChecked(_listCategoryCheck.get(childPosition));
				
				categoryCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						// TODO Auto-generated method stub
						if(_listCategoryCheck.get(childPos) == true)_listCategoryCheck.set(childPos, false);
						else if(_listCategoryCheck.get(childPos) == false)_listCategoryCheck.set(childPos, true);
						if(childPos!=0)_listCategoryCheck.set(0, false);
						if(childPos==0){
							for(int i = 1;i<_listCategoryCheck.size();i++){
								_listCategoryCheck.set(i, false);
							}
						}
						notifyDataSetChanged();
						filterer.filterCategory(_listCategoryCheck);
					}
				});
				convertView.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						//Toast.makeText(context, "list clicked", Toast.LENGTH_SHORT).show();						
						
						if(_listCategoryCheck.get(childPos) == true)_listCategoryCheck.set(childPos, false);
						else if(_listCategoryCheck.get(childPos) == false)_listCategoryCheck.set(childPos, true);
						if(childPos!=0)_listCategoryCheck.set(0, false);
						if(childPos==0){
							for(int i = 1;i<_listCategoryCheck.size();i++){
								_listCategoryCheck.set(i, false);
							}
						}
						notifyDataSetChanged();
						filterer.filterCategory(_listCategoryCheck);						
					}
				});
			}
			else {
				
				convertView = inflater.inflate(R.layout.drawer_list_item, null);
				TextView txtListChild = (TextView)convertView.findViewById(R.id.drawer_child_text);
				txtListChild.setText(childText);				
			}
		//}	
		
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		if(groupPosition==0 || groupPosition==3 || groupPosition==4|| groupPosition==5 || groupPosition==6)return 0;
		return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		
		return this._listDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return this._listDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		String headerTitle = (String)getGroup(groupPosition);
		if(convertView==null){
			LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.drawer_list_group, null);
		}
		
		TextView lblListHeader = (TextView)convertView.findViewById(R.id.list_group_text);
		lblListHeader.setText(headerTitle);
		
		ImageView navIcon = (ImageView)convertView.findViewById(R.id.image_nav_icon);
		switch (groupPosition) {
		case 0:
			navIcon.setImageResource(R.drawable.ic_action_home);
			break;
		case 1:
			navIcon.setImageResource(R.drawable.ic_av_make_available_offline);
			break;
		case 2:
			navIcon.setImageResource(R.drawable.ic_nav_category);
			break;
		case 3:
			navIcon.setImageResource(R.drawable.ic_nav_subscribe);
			break;
		case 4:
			navIcon.setImageResource(R.drawable.ic_content_email);
			break;
		case 5:
			navIcon.setImageResource(R.drawable.ic_action_about);
			break;
		case 6:
			navIcon.setImageResource(R.drawable.ic_action_help);
			break;

		default:
			break;
		}
		
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	public void refreshCategoryCheck(ArrayList<Boolean> categoryRefreshList){
		_listCategoryCheck = categoryRefreshList;
		this.notifyDataSetChanged();
	}
	
	public ArrayList<Boolean> getSelectedCategories(){
		return _listCategoryCheck;
	}
	
	public static interface CategoryFilterer{
		public void filterCategory(ArrayList<Boolean> categoryCheckList);
	}

}
