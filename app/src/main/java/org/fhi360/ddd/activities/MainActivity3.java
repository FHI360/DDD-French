package org.fhi360.ddd.activities;

import android.content.Context;
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
import org.fhi360.ddd.adapter.cardPagerAdapterHome;
import org.fhi360.ddd.domain.CardItem;

import java.util.HashMap;

public class MainActivity3 extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private Button mButton;
    private ViewPager mViewPager;

    private cardPagerAdapterHome mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;
    private TextView names;
    private ImageView imageView;
    private boolean mShowingFragments = false;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlethome);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        imageView =  findViewById(R.id.imageView);
        SpringDotsIndicator springDotsIndicator = (SpringDotsIndicator) findViewById(R.id.spring_dots_indicator);

        names = findViewById(R.id.names);
        HashMap<String, String> name = get();

        String userName = name.get("name");
        assert userName != null;
        String firstLettersurname = String.valueOf(userName.charAt(0));
        String fullSurname = firstLettersurname.toUpperCase() + userName.substring(1).toLowerCase();
        names.setText("hi, " + fullSurname + " OUTLET");
        mCardAdapter = new cardPagerAdapterHome();
        mCardAdapter.addCardItem(this,new CardItem(R.string.title_1, R.string.text_1));
        mCardAdapter.addCardItem(this,new CardItem(R.string.title_2, R.string.text_2));
        mCardAdapter.addCardItem(this,new CardItem(R.string.title_3, R.string.text_3));
        mCardAdapter.addCardItem(this,new CardItem(R.string.title_4, R.string.text_4));
        mCardAdapter.addCardItem(this,new CardItem(R.string.title_5, R.string.text_5));
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
