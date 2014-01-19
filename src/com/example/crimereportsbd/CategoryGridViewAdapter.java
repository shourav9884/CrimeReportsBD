package com.example.crimereportsbd;

import java.util.ArrayList;
import java.util.List;

import com.example.crimereportsbd.DrawerExpandableListAdapter.CategoryFilterer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class CategoryGridViewAdapter extends BaseAdapter{
	
	private Context context;
	private List<String> categories;
	private ArrayList<Boolean> categoryCheckList;
	private SubscribeActivity activity;
	
	public CategoryGridViewAdapter(Context context, List<String> categories, 
			ArrayList<Boolean> categoryCheckList, SubscribeActivity activity){
		this.context = context;
		this.categories = categories;
		this.categoryCheckList = categoryCheckList;
		this.activity = activity;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return categories.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		LayoutInflater inflate;
		final int pos = position;
		final CategoryFilterer filterer = (CategoryFilterer)activity;
		
		
		holder = new ViewHolder();
		inflate = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflate.inflate(R.layout.grid_category_row, null);
        holder.textView = (TextView) convertView.findViewById(R.id.grid_category_text);
        holder.checkBox = (CheckBox) convertView.findViewById(R.id.grid_category_checkBox);        
		
		holder.textView.setText(categories.get(position));
		holder.checkBox.setChecked(categoryCheckList.get(position));
		
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(categoryCheckList.get(pos) == true){
					categoryCheckList.set(pos, false);
				}
				else if(categoryCheckList.get(pos) == false){
					categoryCheckList.set(pos, true);
				}
				if(pos!=0)categoryCheckList.set(0, false);
				if(pos==0){
					for(int i = 1;i<categoryCheckList.size();i++){
						categoryCheckList.set(i, false);
					}
				}
				notifyDataSetChanged();
				filterer.filterCategory(categoryCheckList);
			}
		});
		
        return convertView;   
	}
	
	class ViewHolder{
		TextView textView;
		CheckBox checkBox;
	}

}
