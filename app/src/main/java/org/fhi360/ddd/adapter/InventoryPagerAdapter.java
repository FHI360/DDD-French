package org.fhi360.ddd.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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

import org.fhi360.ddd.activities.MultipleSelectionSpinner;
import org.fhi360.ddd.R;
import org.fhi360.ddd.domain.CardItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InventoryPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<CardItem> mData;
    private float mBaseElevation;
    private Context context;
    private EditText otp_view;
    private ProgressDialog progressDialog;
    String serverUrl = null;

    public InventoryPagerAdapter() {
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
            imageView.setImageResource(R.drawable.refillhistoryicon);
            assigined.setBackgroundResource(R.drawable.background_button_accent);
            assigined.setText("Click More");
            assigined.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAlertInventory();
                }
            });
        }
        if (position == 1) {
            assigined.setText("Click More");
            imageView.setImageResource(R.drawable.inventorymgticon);
            assigined.setBackgroundResource(R.drawable.background_button_accent1);
            assigined.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAlertInventoryRequest();
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

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    public void showAlertInventory() {
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.activity_outlet_inventory, null);
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setView(promptsView);
        final TextView cancel_action;
        cancel_action = promptsView.findViewById(R.id.cancel_action);
        serverUrl = context.getResources().getString(R.string.server_url);
        cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    public void showAlertInventoryRequest() {
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.activity_drug_rquest, null);
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setView(promptsView);
        final TextView cancel_action;
        MultipleSelectionSpinner drugSpinner = promptsView.findViewById(R.id.drugSpinner);
        MultipleSelectionSpinner facilityName = promptsView.findViewById(R.id.facilityName);
        cancel_action = promptsView.findViewById(R.id.cancel_action);
        serverUrl = context.getResources().getString(R.string.server_url);
        Button register = promptsView.findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FancyToast.makeText(context, "Drug Requisition was successfully sent to the facility", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

            }
        });
        List<String> listDrug = new ArrayList<>();
        listDrug.add("TLD 300/300/50mg");
        listDrug.add("TLE300/300/600mg");
        listDrug.add("TL 300/300mg");
        listDrug.add("LPVr 200/50mg");
        listDrug.add("ATVr 300/100mg");
        drugSpinner.setItems(listDrug);
        List<String> listFacilityName = new ArrayList<>();

        listFacilityName.add("Quelimane district");
        facilityName.setItems(listFacilityName);
        cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }

}
