package org.fhi360.ddd.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import org.fhi360.ddd.Db.DDDDb;
import org.fhi360.ddd.R;
import org.fhi360.ddd.adapter.CardAdapter;
import org.fhi360.ddd.domain.CardItem;
import org.fhi360.ddd.domain.Facility;
import org.fhi360.ddd.domain.Patient;
import org.fhi360.ddd.dto.PatientDto;
import org.fhi360.ddd.dto.Response;
import org.fhi360.ddd.webservice.APIService;
import org.fhi360.ddd.webservice.ClientAPI;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class AdminHomeCardPagerAdapter extends PagerAdapter implements CardAdapter {
    private ProgressDialog progressDialog;
    private List<CardView> mViews;
    private List<CardItem> mData;
    private float mBaseElevation;
    private Context context;
    private EditText otp_view;

    String serverUrl = null;

    public AdminHomeCardPagerAdapter() {
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
            imageView.setImageResource(R.drawable.clientregistrationicon);
            assigined.setBackgroundResource(R.drawable.background_button_accent);
            assigined.setText("VOIR PLUS");
            assigined.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, RegisterOutLet.class);
                    context.startActivity(intent);
                }
            });
        }

        if (position == 1) {
            assigined.setText("VOIR PLUS");
            imageView.setImageResource(R.drawable.firstvisit);
            assigined.setBackgroundResource(R.drawable.background_button_accent1);
            assigined.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, NewVisit.class);
                    context.startActivity(intent);
                }
            });
        }

        if (position == 2) {
            assigined.setText("VOIR PLUS");
            imageView.setImageResource(R.drawable.inventorymgticon);
            assigined.setBackgroundResource(R.drawable.background_button_accent1);
            assigined.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, InventorySetup.class);
                    context.startActivity(intent);
                }
            });
        }

        if (position == 3) {
            assigined.setText("VOIR PLUS");
            imageView.setImageResource(R.drawable.reporticon);
            assigined.setBackgroundResource(R.drawable.background_button_accent2);
            assigined.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ReportHomeOptionAdmin.class);
                    context.startActivity(intent);
                }
            });
        }
        if (position == 4) {
            assigined.setText("VOIR PLUS");
            imageView.setImageResource(R.drawable.synchronizeicon);
            assigined.setBackgroundResource(R.drawable.background_button_accent3);
            assigined.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    sync();
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


    private void bind(CardItem item, View view) {
        TextView titleTextView = view.findViewById(R.id.titleTextView);
        TextView contentTextView = view.findViewById(R.id.contentTextView);
        titleTextView.setText(item.getTitle());
        contentTextView.setText(item.getText());
    }

    private void sync() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("DDD Téléchargement...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.show();
        List<Patient> patients = DDDDb.getInstance(context).patientRepository().findByAll();
        List<PatientDto> patientDtos = new ArrayList<>();
        for (Patient patient1 : patients) {
            PatientDto patient = new PatientDto();
            patient.setHospitalNum(patient1.getHospitalNum());
            Facility facility = new Facility();
            facility.setId(patient1.getFacilityId());
            patient.setFacility(facility);
            patient.setUniqueId(patient1.getUniqueId());
            patient.setSurname(patient1.getSurname());
            patient.setOtherNames(patient1.getOtherNames());
            patient.setGender(patient1.getGender());
            patient.setDateBirth(patient1.getDateBirth());
            patient.setAddress(patient1.getAddress());
            patient.setPhone(patient1.getPhone());
            patient.setDateStarted(patient1.getDateStarted());
            patient.setLastClinicStage(patient1.getLastClinicStage());
            patient.setLastViralLoad(patient1.getLastViralLoad());
            patient.setDateLastViralLoad(patient1.getDateLastViralLoad());
            patient.setViralLoadDueDate(patient1.getViralLoadDueDate());
            patient.setViralLoadType(patient1.getViralLoadType());
            patient.setDateLastClinic(patient1.getDateLastClinic());
            patient.setDateNextClinic(patient1.getDateNextClinic());
            patient.setDateLastRefill(patient1.getDateLastRefill());
            patient.setDateNextRefill(patient1.getDateNextRefill());
            patient.setPharmacyId(patient1.getPharmacyId());
            patientDtos.add(patient);
        }

        ClientAPI clientAPI = APIService.createService(ClientAPI.class);
        Call<Response> objectCall = clientAPI.syncPatient(patientDtos);
        objectCall.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    FancyToast.makeText(context, "La synchronisation a réussi", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                    progressDialog.dismiss();
                } else {
                    FancyToast.makeText(context, "Syn n'a pas réussi, contactez l'administrateur système", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    progressDialog.dismiss();
                }


            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                t.printStackTrace();
                FancyToast.makeText(context, "Pas de connexion Internet", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                progressDialog.dismiss();
            }


        });

    }

    public HashMap<String, String> get() {
        HashMap<String, String> name = new HashMap<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("name", Context.MODE_PRIVATE);
        name.put("name", sharedPreferences.getString("name", null));
        return name;

    }


}
