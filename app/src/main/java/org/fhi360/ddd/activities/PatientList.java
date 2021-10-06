package org.fhi360.ddd.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mlsdev.animatedrv.AnimatedRecyclerView;

import org.fhi360.ddd.Db.DDDDb;
import org.fhi360.ddd.R;
import org.fhi360.ddd.adapter.PatientRecyclerAdapter;
import org.fhi360.ddd.domain.Facility;
import org.fhi360.ddd.domain.Patient;

import java.util.ArrayList;
import java.util.List;

public class PatientList extends AppCompatActivity {
    private RecyclerView recyclerViewHts;
    private List<Patient> listPatients;
    private PatientRecyclerAdapter patientRecyclerAdapter;
    private AutoCompleteTextView mSearchField;
    //private ImageView mSearchBtn;
    private TextView totalNumber, heading_labe2;

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_patient_recycler);
        mSearchField = findViewById(R.id.search_field);
        totalNumber = findViewById(R.id.heading_label);
        // mSearchBtn = (ImageView) findViewById(R.id.search_btn);
        recyclerViewHts = findViewById(R.id.patient_recycler);
        heading_labe2 = findViewById(R.id.heading_labe2);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OutletHome.class);
                startActivity(intent);
                finish();
            }
        });
        List<Facility> facility = DDDDb.getInstance(getApplicationContext()).facilityRepository().findAll();
        if (facility.size() > 1) {
            int count = DDDDb.getInstance(getApplicationContext()).patientRepository().total(facility.get(0).getId());
            totalNumber.setText(facility.get(0).getName() + ": " + count);
            int count1 = DDDDb.getInstance(getApplicationContext()).patientRepository().total(facility.get(1).getId());
            heading_labe2.setText(facility.get(1).getName() + ": " + count1);
        } else {
            int count = DDDDb.getInstance(getApplicationContext()).patientRepository().total(facility.get(0).getId());
            totalNumber.setText(facility.get(0).getName() + ": " + count);
            heading_labe2.setVisibility(View.INVISIBLE);
        }

        listPatients = new ArrayList<>();
        listPatients.clear();
        listPatients = DDDDb.getInstance(PatientList.this).patientRepository().findByAll1();
        patientRecyclerAdapter = new PatientRecyclerAdapter(listPatients, PatientList.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(PatientList.this);
        recyclerViewHts.setLayoutManager(mLayoutManager);
        recyclerViewHts.setItemAnimator(new DefaultItemAnimator());
        recyclerViewHts.setHasFixedSize(true);
        recyclerViewHts.setAdapter(patientRecyclerAdapter);
        patientRecyclerAdapter.notifyDataSetChanged();
        recyclerViewHts.scheduleLayoutAnimation();


        ArrayList names = new ArrayList();
        for (Patient patient : listPatients) {
            names.add(patient.getSurname());
        }

        final ArrayAdapter districtAdapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.spinner_items, names);
        mSearchField.setThreshold(2);
        mSearchField.setAdapter(districtAdapter);

        mSearchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0) {
                    search(mSearchField.getText().toString());
                } else {
                    search(mSearchField.getText().toString());
                }

            }
        });
//        mSearchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String searchText = mSearchField.getText().toString();
//
//                search(searchText);
//
//            }
//        });
    }

    private void search(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            resetSearch();
            return;
        }

        List<Patient> filteredValues = new ArrayList<>(listPatients);
        for (Patient value : listPatients) {
            if (!value.getUniqueId().toLowerCase().contains(searchText.toLowerCase())) {
                filteredValues.remove(value);
            }
        }

        List<Patient> filteredValues1 = new ArrayList<>(listPatients);
        for (Patient value : listPatients) {
            if (!value.getUniqueId().toLowerCase().contains(searchText.toLowerCase())) {
                filteredValues1.remove(value);
            }
        }
        patientRecyclerAdapter = new PatientRecyclerAdapter(filteredValues, PatientList.this);
        recyclerViewHts.setAdapter(patientRecyclerAdapter);
    }

    public void resetSearch() {
        patientRecyclerAdapter = new PatientRecyclerAdapter(listPatients, PatientList.this);
        recyclerViewHts.setAdapter(patientRecyclerAdapter);

    }


}
