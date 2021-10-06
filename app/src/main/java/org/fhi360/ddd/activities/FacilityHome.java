package org.fhi360.ddd.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import org.fhi360.ddd.R;
import org.fhi360.ddd.adapter.CardFragmentPagerAdapter;
import org.fhi360.ddd.adapter.FacilityCardPagerAdapter;
import org.fhi360.ddd.domain.CardItem;

import java.util.HashMap;

public class FacilityHome extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private Button mButton;
    private ViewPager mViewPager;
    private View layout;

    private FacilityCardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;
    private TextView names;
    private ImageView imageView;
    private boolean mShowingFragments = false;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility_home);
        mViewPager = findViewById(R.id.viewPager);
        imageView = findViewById(R.id.imageView);

        SpringDotsIndicator springDotsIndicator = findViewById(R.id.spring_dots_indicator);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        names = findViewById(R.id.names);

        HashMap<String, String> role = get();
        String role1 = role.get("name");
        if (role1 != null) {
            String firstLettersurname = String.valueOf(role1.charAt(0));
            String fullSurname = firstLettersurname.toUpperCase() + role1.substring(1).toLowerCase();
            names.setText("hi, " + fullSurname);
        } //else {
//            String userName = DDDDb.getInstance(getApplicationContext()).userRepository().findByOne().getUsername();
//            String firstLettersurname = String.valueOf(userName.charAt(0));
//            String fullSurname = firstLettersurname.toUpperCase() + userName.substring(1).toLowerCase();
//            names.setText("hi, " + fullSurname);
//        }

        mCardAdapter = new FacilityCardPagerAdapter();
        mCardAdapter.addCardItem(this, new CardItem(R.string.title1_1, R.string.title1_11));
        mCardAdapter.addCardItem(this, new CardItem(R.string.title1_2, R.string.title1_134));
        mCardAdapter.addCardItem(this, new CardItem(R.string.title1_3, R.string.title1_21));
        mCardAdapter.addCardItem(this, new CardItem(R.string.title1_4, R.string.title1_41));
        mCardAdapter.addCardItem(this, new CardItem(R.string.title1_5, R.string.text_5));
        mFragmentCardAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(),
                dpToPixels(2, this));

        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager, mFragmentCardAdapter);

        mViewPager.setAdapter(mCardAdapter);
        springDotsIndicator.setViewPager(mViewPager);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);


    }

    @Override
    public void onClick(View view) {
        if (!mShowingFragments) {
            mButton.setText("Views");
            mViewPager.setAdapter(mFragmentCardAdapter);
            mViewPager.setPageTransformer(false, mFragmentCardShadowTransformer);
        } else {
            mButton.setText("Fragments");
            mViewPager.setAdapter(mCardAdapter);
            mViewPager.setPageTransformer(false, mCardShadowTransformer);
        }
        mShowingFragments = !mShowingFragments;
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        mCardShadowTransformer.enableScaling(b);
        mFragmentCardShadowTransformer.enableScaling(b);
    }

    public HashMap<String, String> get() {
        HashMap<String, String> name = new HashMap<>();
        SharedPreferences sharedPreferences = this.getSharedPreferences("name", Context.MODE_PRIVATE);
        name.put("name", sharedPreferences.getString("name", null));
        return name;

    }

}
