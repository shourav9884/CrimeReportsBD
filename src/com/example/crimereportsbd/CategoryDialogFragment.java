package com.example.crimereportsbd;

import java.util.ArrayList;

import com.example.crimereportsbd.DatePickerFragment.DateHandler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

@SuppressLint("ValidFragment")
public class CategoryDialogFragment extends DialogFragment {
	
	ArrayList<Integer> mSelectedItems;
	ArrayList<String> catnames;
	ArrayList<String> selectedTitle;
	CategoryDataInterface categoryDataSender;
	
	
	public CategoryDialogFragment()
	{
		
	}
	public CategoryDialogFragment(ArrayList<String> cats)
	{
		this.catnames=cats;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    mSelectedItems = new ArrayList<Integer>();  // Where we track the selected items
	    if(catnames==null)
	    {
	    	catnames=StaticData.CATAGORIES;
	    }
	    
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    // Set the dialog title
	    builder.setTitle("Select Category")
	    // Specify the list array, the items to be selected by default (null for none),
	    // and the listener through which to receive callbacks when items are selected
	           .setMultiChoiceItems(catnames.toArray(new CharSequence[catnames.size()]), null,
	                      new DialogInterface.OnMultiChoiceClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int which,
	                       boolean isChecked) {
	                   if (isChecked) {
	                       // If the user checked the item, add it to the selected items
	                       mSelectedItems.add(which);
	                   } else if (mSelectedItems.contains(which)) {
	                       // Else, if the item is already in the array, remove it 
	                       mSelectedItems.remove(Integer.valueOf(which));
	                   }
	               }
	           })
	    // Set the action buttons
	           .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	                   // User clicked OK, so save the mSelectedItems results somewhere
	                   // or return them to the component that opened the dialog
	            	   
	            	   selectedTitle = new ArrayList<String>();
	            	   
	            	   for(int i = 0;i<mSelectedItems.size();i++){
	            		   selectedTitle.add(catnames.get(mSelectedItems.get(i)));
	            		   System.out.println(selectedTitle.get(i));
	            	   } 	   
	            	   categoryDataSender.setSelectedCategory(selectedTitle);
	               }
	           })
	           .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	                   
	               }
	           });

	    return builder.create();
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
		if(activity instanceof CategoryDataInterface){
			categoryDataSender = (CategoryDataInterface)activity;
		}
	}
	
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		categoryDataSender = null;
	}
	
	public interface CategoryDataInterface{
		public void setSelectedCategory(ArrayList<String> title);
	}
}
