package org.fhi360.ddd.activities;
//
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.view.View;
//import android.widget.LinearLayout;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.shashank.sony.fancytoastlib.FancyToast;
//
//import java.util.HashMap;
//import java.util.Objects;
//
//public class OutletHome extends AppCompatActivity {
//    private LinearLayout newVisit;
//    private LinearLayout reVisit;
//    private LinearLayout summaryReport;
//    private LinearLayout synchronize;
//    private ProgressDialog progressdialog;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_outlet_home);
//        newVisit = findViewById(R.id.newVisit);
//        reVisit = findViewById(R.id.reVisit);
//        summaryReport = findViewById(R.id.summaryReport);
//        synchronize = findViewById(R.id.synchronize);
//        HashMap<String, String> name = get();
//        String userName = name.get("name");
//        assert userName != null;
//        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome, " + userName.toUpperCase());
//
//        newVisit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(OutletHome.this, ActivityOptionsNewPatient.class);
//                startActivity(intent);
//            }
//        });
//
//        reVisit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(OutletHome.this, PatientList.class);
//                startActivity(intent);
//            }
//        });
//
//        summaryReport.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(OutletHome.this, ReportHomeOption.class);
//                startActivity(intent);
//            }
//        });
//
//        synchronize.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                progressdialog = new ProgressDialog(OutletHome.this);
//                progressdialog.setMessage("App Synchronization with Server in Progress...");
//                progressdialog.setCancelable(false);
//                progressdialog.setIndeterminate(false);
//                progressdialog.setMax(100);
//                progressdialog.show();
//                countDownTimer.start();
//            }
//        });
//    }
//
//    private CountDownTimer countDownTimer = new CountDownTimer(10000, 100) {
//        public void onTick(long millisUntilFinished) {
//            progressdialog.setProgress(Math.abs((int) millisUntilFinished / 100 - 100));
//        }
//
//        @Override
//        public void onFinish() {
//            FancyToast.makeText(getApplicationContext(), "Sync Was successful", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
//            progressdialog.dismiss();
//        }
//    };
//
//    public HashMap<String, String> get() {
//        HashMap<String, String> name = new HashMap<>();
//        SharedPreferences sharedPreferences = this.getSharedPreferences("name", Context.MODE_PRIVATE);
//        name.put("name", sharedPreferences.getString("name", null));
//        return name;
//    }
//}


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

import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import org.fhi360.ddd.R;
import org.fhi360.ddd.adapter.CardFragmentPagerAdapter;
import org.fhi360.ddd.adapter.CardPagerAdapterOutletReport;
import org.fhi360.ddd.domain.CardItem;

import java.util.HashMap;

public class OutletRoutineNationalReport extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private Button mButton;
    private ViewPager mViewPager;
    private View layout;
    private Button activateButton;
    private EditText userNameEditText;
    private EditText pinEditText;
    private ProgressDialog progressDialog;
    private TextView serviceProvided;
    private CardPagerAdapterOutletReport mCardAdapter;
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

        SpringDotsIndicator springDotsIndicator = (SpringDotsIndicator) findViewById(R.id.spring_dots_indicator);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReportHomeOption.class);
                startActivity(intent);
                finish();
            }
        });

        names = findViewById(R.id.names);
        names.setText(R.string.routine_national_report);
        mCardAdapter = new CardPagerAdapterOutletReport();
        mCardAdapter.addCardItem(this, new CardItem(R.string.rountine_report, R.string.text_routine_report_description));
        mCardAdapter.addCardItem(this, new CardItem(R.string.national_report, R.string.text_national_report_description));
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
