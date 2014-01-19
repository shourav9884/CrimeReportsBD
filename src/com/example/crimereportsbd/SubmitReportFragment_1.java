package com.example.crimereportsbd;


/* 1. Date and Time Picker not Implemented
 * 2. Category Pop up Dialog not Implemented 
 * 
 */



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SubmitReportFragment_1 extends Fragment{
	
	Date date;
	Button buttonCancel;
	Button buttonNext;
	Button buttonModifyTime,buttonSelectCategory;
	DataHandler dataHandler;
	EditText titleText,descText,firstNameText,lastNameText,emailText;
	String title,description;
	TextView dateText;
	ArrayList<String> selectedCategory;
	TextView categoryText;
	String dateString;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.submit_report_fragment_1,
	        container, false);
	    
	    buttonCancel = (Button)view.findViewById(R.id.button_submit_cancel);
	    buttonNext = (Button)view.findViewById(R.id.button_submit_next);
	    buttonModifyTime = (Button)view.findViewById(R.id.text_modify_time);
	    dateText = (TextView)view.findViewById(R.id.text_submit_time);
	    categoryText = (TextView)view.findViewById(R.id.text_selected_category);
	    titleText=(EditText)view.findViewById(R.id.editText_title);
	    descText=(EditText)view.findViewById(R.id.editText_description);
	    buttonSelectCategory = (Button)view.findViewById(R.id.text_select_category);
	    firstNameText=(EditText)view.findViewById(R.id.editText_first_name);
	    lastNameText=(EditText)view.findViewById(R.id.editText_last_name);
	    emailText=(EditText)view.findViewById(R.id.editText_email);
	    
	    Date tmpDate=new Date();
	    SimpleDateFormat dateFormater = new SimpleDateFormat("dd MMM yyyy");
		String tmp = dateFormater.format(tmpDate);
		
		dateString=tmp;
	    
	    buttonCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().onBackPressed();
			}
		});
	    
	    buttonNext.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getData();
				
				System.out.println("Date: "+dateString);
				IncidentPerson p=null;
				String fName,lName,email;
				fName=firstNameText.getText().toString();
				lName=lastNameText.getText().toString();
				email=emailText.getText().toString();
				
				if(!fName.equals("")||!lName.equals("")||!email.equals(""))
				{
					p=new IncidentPerson();
					p.setFirstName(fName);
					p.setLastName(lName);
					p.setEmail(email);
				}
				dataHandler.addData(title, description, dateString, selectedCategory,p);
				SubmitReportActivity.getViewPager().setCurrentItem(1);				
			}
		});
	    
	    	buttonModifyTime.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DialogFragment datePicker = new DatePickerFragment();
				datePicker.show(getFragmentManager(), "Pick Date");
				
			}
		});
	    	buttonSelectCategory.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					DialogFragment selectCategoryDialog = new CategoryDialogFragment();
					selectCategoryDialog.show(getFragmentManager(), "Select Category");				
				}
			});
	    
	    return view;
	}
	public interface DataHandler
	{
		public void addData(String title,String description,String date,ArrayList<String> cats,IncidentPerson p);
	}
	
	private void getData()
	{
		title=titleText.getText().toString();
		description=descText.getText().toString();
	}
	/* (non-Javadoc)
	 * @see android.support.v4.app.DialogFragment#onAttach(android.app.Activity)
	 */
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		if (activity instanceof DataHandler) {
			dataHandler = (DataHandler) activity;
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
		dataHandler=null;
	}
	public void setDateText(Date d){
		date = d;
		
		
		System.out.println(d.toString());
		SimpleDateFormat dateFormater = new SimpleDateFormat("dd MMM yyyy");
		String tmp = dateFormater.format(d);
		System.out.println(tmp);
		dateText.setText(tmp);
		dateString=tmp;
	}
	public void setSelectedCategories(ArrayList<String> categories){
		selectedCategory = categories;
		if(selectedCategory.size()>0)categoryText.setText(selectedCategory.get(0));
		
		for(int i = 1;i<selectedCategory.size();i++){
			categoryText.append(", " + selectedCategory.get(i));
		}
	}
}
