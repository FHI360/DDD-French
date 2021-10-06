package org.fhi360.ddd.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import org.fhi360.ddd.R;

public class tes2 extends AppCompatActivity {
    MyViewPagerAdapter myViewPagerAdapter;
    ViewPager pagerContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility_home);
        //adapter
        pagerContainer = findViewById(R.id.viewPager);
        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        pagerContainer.setAdapter(myViewPagerAdapter);

    }
}
