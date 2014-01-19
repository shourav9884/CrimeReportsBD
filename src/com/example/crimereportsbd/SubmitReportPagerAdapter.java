package com.example.crimereportsbd;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SubmitReportPagerAdapter extends FragmentPagerAdapter {
	
	Fragment fragment1;
	Fragment fragment2;

	public SubmitReportPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub	
		
		fragment1 = new SubmitReportFragment_1();
		fragment2 = new SubmitReportFragment_2();
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub		
		if(position == 0){
			return fragment1;
		}
		if(position == 1){
			return fragment2;
		}	
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}

}
