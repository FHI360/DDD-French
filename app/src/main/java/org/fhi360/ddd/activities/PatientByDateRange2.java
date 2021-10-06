package org.fhi360.ddd.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mlsdev.animatedrv.AnimatedRecyclerView;

import com.shashank.sony.fancytoastlib.FancyToast;
import org.fhi360.ddd.Db.DDDDb;
import org.fhi360.ddd.R;
import org.fhi360.ddd.adapter.PatientAdpter;
import org.fhi360.ddd.adapter.PatientRecyclerAdapter;
import org.fhi360.ddd.domain.Facility;
import org.fhi360.ddd.domain.Patient;
import org.fhi360.ddd.dto.Data;
import org.fhi360.ddd.dto.PatientDto;
import org.fhi360.ddd.webservice.APIService;
import org.fhi360.ddd.webservice.ClientAPI;
import retrofit2.Call;
import retrofit2.Callback;

import java.text.SimpleDateFormat;
import java.util.*;

public class PatientByDateRange2 extends AppCompatActivity {
    private RecyclerView recyclerViewHts;
    private List<PatientDto> listPatients;
    private PatientAdpter2 patientRecyclerAdapter;
    //private AutoCompleteTextView mSearchField;
    //private ImageView mSearchBtn;
    private EditText to, from;
    private Calendar myCalendar = Calendar.getInstance();
    private Button search;

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_patient_recycler_range);
        search = findViewById(R.id.search);
        to = findViewById(R.id.to);
        from = findViewById(R.id.from);
        // mSearchBtn = (ImageView) findViewById(R.id.search_btn);
        recyclerViewHts = findViewById(R.id.patient_recycler);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReportHomeOptionAdmin.class);
                startActivity(intent);
                finish();
            }
        });
        from.setText("2019-01-01");
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        to.setText(dateFormat.format(new Date()));


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPatient(from.getText().toString(),to.getText().toString());
            }
        });

        final DatePickerDialog.OnDateSetListener to1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                to();
            }

        };

        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePickerDialog mDatePicker = new DatePickerDialog(PatientByDateRange2.this, to1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.show();
            }
        });


        final DatePickerDialog.OnDateSetListener from1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                from();
            }

        };


        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePickerDialog mDatePicker = new DatePickerDialog(PatientByDateRange2.this, from1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.show();
            }
        });


    }


    private void to() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        to.setText(sdf.format(myCalendar.getTime()));
    }

    private void from() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        from.setText(sdf.format(myCalendar.getTime()));

    }


    public void getPatient(String start, String end) {
        ProgressDialog mPb = new ProgressDialog(PatientByDateRange2.this);
        mPb.setProgress(0);
        mPb.setMessage("Téléchargement des données, veuillez patienter...");
        mPb.setCancelable(false);
        mPb.setIndeterminate(false);
        mPb.setProgress(0);
        mPb.setMax(100);
        mPb.show();
        @SuppressLint("HardwareIds")
        ClientAPI clientAPI = APIService.createService(ClientAPI.class);
        Call<Data> objectCall = clientAPI.getAllPatientsByDateRange(start, end);
        objectCall.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(@NonNull Call<Data> call, @NonNull retrofit2.Response<Data> response) {
                if (response.isSuccessful()) {
                    listPatients = Objects.requireNonNull(response.body()).getPatients();
                    patientRecyclerAdapter = new PatientAdpter2(listPatients, PatientByDateRange2.this);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(PatientByDateRange2.this);
                    recyclerViewHts.setLayoutManager(mLayoutManager);
                    recyclerViewHts.setItemAnimator(new DefaultItemAnimator());
                    recyclerViewHts.setHasFixedSize(true);
                    recyclerViewHts.setAdapter(patientRecyclerAdapter);
                    patientRecyclerAdapter.notifyDataSetChanged();
                    recyclerViewHts.scheduleLayoutAnimation();
                    mPb.hide();

                } else {
                    FancyToast.makeText(PatientByDateRange2.this, "DDD ne peut pas obtenir les dossiers des patients", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    mPb.hide();
                }


            }

            @Override
            public void onFailure(@NonNull Call<Data> call, @NonNull Throwable t) {
                t.printStackTrace();
                FancyToast.makeText(PatientByDateRange2.this, "Pas de connexion Internet", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                mPb.hide();
            }

        });

    }


}
