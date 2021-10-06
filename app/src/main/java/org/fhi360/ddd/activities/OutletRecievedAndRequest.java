package org.fhi360.ddd.activities;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import org.fhi360.ddd.R;
import org.fhi360.ddd.adapter.CardFragmentPagerAdapter;
import org.fhi360.ddd.adapter.InventoryPagerAdapter;
import org.fhi360.ddd.domain.CardItem;

import java.util.HashMap;

public class OutletRecievedAndRequest extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private Button mButton;
    private ViewPager mViewPager;
    private View layout;
    private Button activateButton;
    private EditText userNameEditText;
    private EditText pinEditText;
    private ProgressDialog progressDialog;
    private TextView serviceProvided;
    private InventoryPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;
    private TextView names;
    private ImageView imageView;
    private boolean mShowingFragments = false;
    String serverUrl = null;
    String localIpAdress = null;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlethome);
        mViewPager = findViewById(R.id.viewPager);
        imageView = findViewById(R.id.imageView);


       // SpringDotsIndicator springDotsIndicator = findViewById(R.id.spring_dots_indicator);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OutletHome.class);
                startActivity(intent);
                finish();
            }
        });

        names = findViewById(R.id.names);
        HashMap<String, String> name = get();
        String userName = name.get("name");
        assert userName != null;
        String firstLettersurname = String.valueOf(userName.charAt(0));
        String fullSurname = firstLettersurname.toUpperCase() + userName.substring(1).toLowerCase();
        names.setText("hi, " + fullSurname + " OUTLET");
        mCardAdapter = new InventoryPagerAdapter();
        mCardAdapter.addCardItem(this, new CardItem(R.string.drug_recieved, R.string.drug_recieved_desc));
        mCardAdapter.addCardItem(this, new CardItem(R.string.drug_requestion, R.string.drug_request_desc));
        mFragmentCardAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(),
                dpToPixels(2, this));

        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager, mFragmentCardAdapter);

        mViewPager.setAdapter(mCardAdapter);
      //  springDotsIndicator.setViewPager(mViewPager);
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
