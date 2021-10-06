package org.fhi360.ddd.activities;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyViewPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 4;

    public MyViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new StepOne();
                break;
            case 1:
                fragment = new StepTwo();
                break;
            case 2:
                fragment = new StepThree();
                break;
            case 3:
                fragment = new StepFour();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
