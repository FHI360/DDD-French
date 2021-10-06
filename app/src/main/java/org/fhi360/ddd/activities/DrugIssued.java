package org.fhi360.ddd.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.fhi360.ddd.Db.DDDDb;
import org.fhi360.ddd.R;
import org.fhi360.ddd.domain.Pharmacy;
import org.fhi360.ddd.domain.Drug;
import org.fhi360.ddd.domain.IssuedDrug;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static org.fhi360.ddd.util.Constants.PREFERENCES_ENCOUNTER;

public class DrugIssued extends AppCompatActivity {
    private TextView noOfPatient;
    private Pharmacy account;
    private View view1, view2, view3, view4, view5, view6;
    private LinearLayout layout1, layout2, layout3, layout4, layout5, layout6;
    private EditText quantity, name, basicUnit, batchNumber, expireDate;
    private Spinner drugName;
    private SharedPreferences preferences;
    private Calendar myCalendar = Calendar.getInstance();
    private Button button;

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState,
                         @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.drug_issue);
        this.preferences = getSharedPreferences(PREFERENCES_ENCOUNTER, 0);
        restorePreferences();
        if (savedInstanceState != null) {
            String json = savedInstanceState.getString("account");
            account = new Gson().fromJson(json, Pharmacy.class);
        }
        noOfPatient = findViewById(R.id.noOfPatient);
        name = findViewById(R.id.name);
        expireDate = findViewById(R.id.expiredDate);
        basicUnit = findViewById(R.id.basicUnit);
        batchNumber = findViewById(R.id.batcNumber);
        drugName = findViewById(R.id.drugName);
        quantity = findViewById(R.id.quantity);
        button = findViewById(R.id.register);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListDDDClient2.class);
                startActivity(intent);
                finish();
            }
        });


//        //view2.setVisibility(View.INVISIBLE);
//        view3.setVisibility(View.INVISIBLE);
//        view4.setVisibility(View.INVISIBLE);
//        view4.setVisibility(View.INVISIBLE);
//        view5.setVisibility(View.INVISIBLE);
//        view6.setVisibility(View.INVISIBLE);
//        layout1.setVisibility(View.INVISIBLE);
//        layout2.setVisibility(View.INVISIBLE);
//        layout3.setVisibility(View.INVISIBLE);
//        layout4.setVisibility(View.INVISIBLE);
//        layout5.setVisibility(View.INVISIBLE);

        name.setText(account.getName());
        final ArrayList drugId = new ArrayList();
        ArrayList drugNames = new ArrayList();
        List<Drug> drugs = DDDDb.getInstance(this).drugRepository().findByAll();
        for (Drug drug : drugs) {
            drugId.add(drug.getId());
            drugNames.add(drug.getDrugName());

        }

        int count =0; //DDDDb.getInstance(this).patientRepository().count(account.getPin());
        noOfPatient.setText("nombre de malades " + count);
        final ArrayAdapter drug = new ArrayAdapter<>(DrugIssued.this,
                R.layout.support_simple_spinner_dropdown_item, drugNames);
        drug.notifyDataSetChanged();
        drugName.setAdapter(drug);
        drugName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String drug1 = (String) drugId.get(position);
                savePin(drug1);
                view3.setVisibility(View.VISIBLE);
                view4.setVisibility(View.VISIBLE);
                view4.setVisibility(View.VISIBLE);
                view5.setVisibility(View.VISIBLE);
                view6.setVisibility(View.VISIBLE);
                layout1.setVisibility(View.VISIBLE);
                layout2.setVisibility(View.VISIBLE);
                layout3.setVisibility(View.VISIBLE);
                layout4.setVisibility(View.VISIBLE);
                layout5.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final DatePickerDialog.OnDateSetListener dateLastClinic1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }

        };


        expireDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final DatePickerDialog mDatePicker = new DatePickerDialog(DrugIssued.this, dateLastClinic1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String basicUnit1 = basicUnit.getText().toString();
                String expireDate1 = expireDate.getText().toString();
                String batchNumber1 = batchNumber.getText().toString();
                HashMap<String, String> regId = getId();
                String id = regId.get("id");
                String quantity1 = quantity.getText().toString();
                if (validateInput(basicUnit1, expireDate1, batchNumber1, quantity1)) {
                    IssuedDrug issuedDrug = new IssuedDrug();
                    issuedDrug.setBatchNumber(batchNumber1);
                    issuedDrug.setRegimenId(Long.valueOf(id));
                    issuedDrug.setPinCode(account.getPin());
                    issuedDrug.setExpireDate(expireDate1);
                    issuedDrug.setQuantity(Double.parseDouble(quantity1));
                    DDDDb.getInstance(getApplicationContext()).drugIssuedRepository().save(issuedDrug);
                    FancyToast.makeText(getApplicationContext(), "Médicament délivré avec succès", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                }
            }
        });
    }


    private void restorePreferences() {
        String json = preferences.getString("account", "");
        account = new Gson().fromJson(json, Pharmacy.class);
    }

    public void savePin(String drugid) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("drug", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putString("id", drugid);
        editor.apply();
    }

    public HashMap<String, String> getId() {
        HashMap<String, String> pincode = new HashMap<>();
        SharedPreferences sharedPreferences = this.getSharedPreferences("drug", Context.MODE_PRIVATE);
        pincode.put("id", sharedPreferences.getString("id", null));
        return pincode;
    }

    private void updateDate() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        expireDate.setText(sdf.format(myCalendar.getTime()));

    }

    private boolean validateInput(String basicUnit1, String expired1, String batchNumber1, String qty) {
        if (basicUnit1.isEmpty()) {
            basicUnit.setError("l'unité de base ne peut pas être vide");
            return false;


        } else if (expired1.isEmpty()) {
            expireDate.setError("La date d'expiration ne peut pas être vide");
            return false;

        } else if (batchNumber1.isEmpty()) {
            batchNumber.setError("Le numéro de lot ne peut pas être vide");
            return false;

        } else if (qty.isEmpty()) {
            quantity.setError("La quantité ne peut pas être vide");
            return false;

        }
        return true;


    }


}
