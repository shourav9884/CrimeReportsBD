package com.example.crimereportsbd;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements OnDateSetListener{
	
	SubmitReportActivity activity;
	DateHandler dateHandler;
	
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
		if(activity instanceof DateHandler){
			dateHandler = (DateHandler)activity;
		}
	}
	
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		dateHandler = null;
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		Date d = new Date(year-1900, monthOfYear, dayOfMonth);
		/*d.setDate(dayOfMonth);
		d.setMonth(monthOfYear);
		d.setYear(year);*/
		//activity.setDate(d);
		dateHandler.setPickerDate(d);
		
//		System.out.println("Year "+d.toGMTString());
		
	}
	
	public interface DateHandler{
		public void setPickerDate(Date date);
	}
}
