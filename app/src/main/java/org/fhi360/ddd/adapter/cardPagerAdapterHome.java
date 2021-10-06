package org.fhi360.ddd.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.shashank.sony.fancytoastlib.FancyToast;

import org.fhi360.ddd.Db.DDDDb;
import org.fhi360.ddd.activities.PatientList;
import org.fhi360.ddd.R;
import org.fhi360.ddd.activities.ReportHomeOption;
import org.fhi360.ddd.domain.CardItem;
import org.fhi360.ddd.domain.Facility;
import org.fhi360.ddd.domain.Patient;
import org.fhi360.ddd.domain.Pharmacy;
import org.fhi360.ddd.domain.Regimen;
import org.fhi360.ddd.dto.Data;
import org.fhi360.ddd.dto.PatientDto;
import org.fhi360.ddd.dto.Response;
import org.fhi360.ddd.domain.User;
import org.fhi360.ddd.webservice.APIService;
import org.fhi360.ddd.webservice.ClientAPI;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class cardPagerAdapterHome extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<CardItem> mData;
    private float mBaseElevation;
    private Context context;
    private EditText otp_view;
    private ProgressDialog progressDialog;

    private ProgressDialog mPb;

    public cardPagerAdapterHome() {
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
        CardView cardView = view.findViewById(R.id.cardView);
        ImageView imageView = view.findViewById(R.id.imageView);

        Button assigined = view.findViewById(R.id.assigined);
        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        if (position == 0) {
            imageView.setImageResource(R.drawable.firsttimevisit);
            assigined.setBackgroundResource(R.drawable.background_button_accent);
            assigined.setText("VOIR PLUS");
            assigined.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAlert();
                }
            });
        }
        if (position == 1) {
            assigined.setText("VOIR PLUS");
            imageView.setImageResource(R.drawable.revisit);
            assigined.setBackgroundResource(R.drawable.background_button_accent1);
            assigined.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PatientList.class);
                    context.startActivity(intent);
                }
            });
        }

//        if (position == 2) {
//            imageView.setImageResource(R.drawable.inventorymgticon);
//            assigined.setText("Click More");
//            assigined.setBackgroundResource(R.drawable.background_button_accent2);
//            assigined.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(context, OutletRecievedAndRequest.class);
//                    context.startActivity(intent);
//                }
//            });
//        }
        if (position == 2) {
            imageView.setImageResource(R.drawable.reporticon);
            assigined.setText("VOIR PLUS");
            assigined.setBackgroundResource(R.drawable.background_button_accent3);
            assigined.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ReportHomeOption.class);
                    context.startActivity(intent);
                }
            });

        }
        if (position == 3) {
            assigined.setText("VOIR PLUS");
            imageView.setImageResource(R.drawable.synchronizeicon);
            assigined.setBackgroundResource(R.drawable.background_button_accent3);
            assigined.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    synchronizeARV();
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
        TextView titleTextView = view.findViewById(R.id.titleTextView);
        TextView contentTextView = view.findViewById(R.id.contentTextView);

        titleTextView.setText(item.getTitle());
        contentTextView.setText(item.getText());
    }


    public void showAlert() {
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.pop_up_activation, null);
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setView(promptsView);
        final EditText notitoptxt;
        final TextView cancel_action;
        final Button activate;
        notitoptxt = promptsView.findViewById(R.id.notitoptxt);
        otp_view = promptsView.findViewById(R.id.otp_view);
        activate = promptsView.findViewById(R.id.activate_button);
        CheckBox downloadCheckbox = (CheckBox) promptsView.findViewById(R.id.download_checkbox);
        cancel_action = promptsView.findViewById(R.id.cancel_action);
        cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  boolean bol = validateInput(otp_view.getText().toString());
                if (downloadCheckbox.isChecked()) {
                    Pharmacy pin2 = DDDDb.getInstance(context).pharmacistAccountRepository().findbyOne();
                    String deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                    User user = DDDDb.getInstance(context).userRepository().findByOne();
                    getPatient(deviceId, pin2.getPin(), user.getUsername(), user.getPassword());
                } else {
                    FancyToast.makeText(context, "La case à cocher ne peut pas être vide", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                }
                dialog.dismiss();
            }


        });

        dialog.setCancelable(false);
        dialog.show();
    }


    private void getPatient(String deviceId, String pin, String accountUserName, String accountPassword) {
        mPb = new ProgressDialog(context);
        mPb.setProgress(0);
        mPb.setMessage("Uploading data please wait...");
        mPb.setCancelable(false);
        mPb.setIndeterminate(false);
        mPb.setProgress(0);
        mPb.setMax(100);
        mPb.show();
        @SuppressLint("HardwareIds")

        ClientAPI clientAPI = APIService.createService(ClientAPI.class);
        Call<Data> objectCall = clientAPI.downLoad(deviceId, pin, accountUserName, accountPassword);
        objectCall.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(@NonNull Call<Data> call, @NonNull retrofit2.Response<Data> response) {
                if (response.isSuccessful()) {
                    List<PatientDto> patientDtoList = Objects.requireNonNull(response.body()).getPatients();
                    for (PatientDto patientDto : patientDtoList) {
                        Patient patient = new Patient();
                       patient.setId(patientDto.getId());
                        patient.setHospitalNum(patientDto.getHospitalNum());
                        patient.setFacilityId(patientDto.getFacility().getId());
                        patient.setUniqueId(patientDto.getUniqueId());
                        patient.setSurname(patientDto.getSurname());
                        patient.setOtherNames(patientDto.getOtherNames());
                        patient.setGender(patientDto.getGender());
                        patient.setDateBirth(patientDto.getDateBirth());
                        patient.setAddress(patientDto.getAddress());
                        patient.setPhone(patientDto.getPhone());
                        patient.setDateStarted(patientDto.getDateStarted());
                        patient.setLastClinicStage(patientDto.getLastClinicStage());
                        patient.setLastViralLoad(patientDto.getLastViralLoad());
                        patient.setDateLastViralLoad(patientDto.getDateLastViralLoad());
                        patient.setViralLoadDueDate(patientDto.getViralLoadDueDate());
                        patient.setViralLoadType(patientDto.getViralLoadType());
                        patient.setDateLastClinic(patientDto.getDateLastClinic());
                        patient.setDateNextClinic(patientDto.getDateNextClinic());
                        patient.setDateLastRefill(patientDto.getDateLastRefill());
                        patient.setDateNextRefill(patientDto.getDateNextRefill());
                        patient.setPharmacyId(patientDto.getPharmacyId());
                        patient.setUuid(patientDto.getUuid());
                        Patient patient1 = DDDDb.getInstance(context).patientRepository().findOne(patientDto.getId());
                        if (patient1!= null) {
                            DDDDb.getInstance(context).patientRepository().update(patient1);
                            Intent intent = new Intent(context, PatientList.class);
                            context.startActivity(intent);
                            mPb.hide();
                            FancyToast.makeText(context, "Records Successfully downloaded", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                        } else {
                            DDDDb.getInstance(context).patientRepository().save(patient);
                            Intent intent = new Intent(context, PatientList.class);
                            context.startActivity(intent);
                            mPb.hide();
                            FancyToast.makeText(context, "Records Successfully downloaded", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                        }

                    }
                    DDDDb.getInstance(context).facilityRepository().delete();
                    Facility facility = Objects.requireNonNull(response.body()).getFacility();
                    facility.setId(Objects.requireNonNull(response.body()).getFacility().getId());
                    DDDDb.getInstance(context).facilityRepository().save(facility);

                    DDDDb.getInstance(context).regimenRepository().delete();
                    List<Regimen> regimen = response.body().getRegimens();
                    for (Regimen regimen1 : regimen) {
                        Regimen regimen2 = new Regimen();
                        regimen2.setId(regimen1.getId());
                        regimen2.setName(regimen1.getName());
                        regimen2.setRegimenTypeId(regimen1.getRegimenTypeId());
                        DDDDb.getInstance(context).regimenRepository().save(regimen2);
                    }

                    Intent intent = new Intent(context, PatientList.class);
                    context.startActivity(intent);
                    mPb.hide();
                    FancyToast.makeText(context, "Records Successfully downloaded", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                } else {
                    mPb.hide();
                    FancyToast.makeText(context, "DDD can't get patient records", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                }


            }

            @Override
            public void onFailure(@NonNull Call<Data> call, @NonNull Throwable t) {
                t.printStackTrace();
                FancyToast.makeText(context, "No internet connection ", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                mPb.hide();
            }

        });

    }


    private void synchronizeARV() {
        mPb = new ProgressDialog(context);
        mPb.setProgress(0);
        mPb.setMessage("Uploading data please wait...");
        mPb.setCancelable(false);
        mPb.setIndeterminate(false);
        mPb.setProgress(0);
        mPb.setMax(100);
        mPb.show();
        ClientAPI clientAPI = APIService.createService(ClientAPI.class);
        Call<Response> objectCall = clientAPI.syncARVRefill(DDDDb.getInstance(context).arvRefillRepository().findAll());
        objectCall.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(@NonNull Call<Response> call, @NonNull retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    FancyToast.makeText(context, "Sync was successful", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                    mPb.dismiss();
                } else {
                    mPb.dismiss();
                    FancyToast.makeText(context, "Syn was not successful to Server ", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                t.printStackTrace();
                FancyToast.makeText(context, "No internet connection ", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                mPb.dismiss();
            }

        });

    }

    private boolean validateInput(String pin) {
        if (pin.isEmpty()) {
            otp_view.setError("Enter Activation code");
            return false;
        }
        return true;


    }


}
