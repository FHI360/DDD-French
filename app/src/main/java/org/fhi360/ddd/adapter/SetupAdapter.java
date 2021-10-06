
package org.fhi360.ddd.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.shashank.sony.fancytoastlib.FancyToast;

import org.fhi360.ddd.activities.DrugSetup;
import org.fhi360.ddd.activities.ListDDDClient2;

import org.fhi360.ddd.R;
import org.fhi360.ddd.domain.CardItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SetupAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<CardItem> mData;
    private float mBaseElevation;
    private Context context;
    private EditText otp_view;
    private ProgressDialog progressDialog;
    String serverUrl = null;

    public SetupAdapter() {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public void addCardItem(Context context, CardItem item) {
        this.context = context;
        mViews.add(null);
        mData.add(item);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @SuppressLint("SetTextI18n")
    @NotNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.adapter, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);
        ImageView imageView = view.findViewById(R.id.imageView);

        Button assigined = view.findViewById(R.id.assigined);
        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        if (position == 0) {
            imageView.setImageResource(R.drawable.arvrefillicon);
            assigined.setBackgroundResource(R.drawable.background_button_accent);
            assigined.setText("VOIR PLUS");
            assigined.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DrugSetup.class);
                    context.startActivity(intent);
                }
            });
        }
        if (position == 1) {
            assigined.setText("VOIR PLUS");
            imageView.setImageResource(R.drawable.clientregistrationicon);
            assigined.setBackgroundResource(R.drawable.background_button_accent1);
            assigined.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ListDDDClient2.class);
                    context.startActivity(intent);
                }
            });
        }


        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private CountDownTimer countDownTimer = new CountDownTimer(10000, 100) {
        public void onTick(long millisUntilFinished) {
            progressDialog.setProgress(Math.abs((int) millisUntilFinished / 100 - 100));
        }

        @Override
        public void onFinish() {
            FancyToast.makeText(context, "Sync Was successful", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
            progressDialog.dismiss();
        }
    };

    public HashMap<String, String> get() {
        HashMap<String, String> name = new HashMap<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("name", Context.MODE_PRIVATE);
        name.put("name", sharedPreferences.getString("name", null));
        return name;
    }


    private void bind(CardItem item, View view) {
        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        TextView contentTextView = (TextView) view.findViewById(R.id.contentTextView);
        titleTextView.setText(item.getTitle());
        contentTextView.setText(item.getText());
    }


}
