package com.example.uni.memo_test;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.uni.memo_test.TextPage;

public class ViewPagerAdapter extends FragmentPagerAdapter {
	private static final int PAGE_NUM = 3;

	public ViewPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = null;
		switch (position){
			case 0:
				fragment = new TextPage();
				break;
		}
		return fragment;
	}

	@Override
	public int getCount() {
		return PAGE_NUM;
	}
}
