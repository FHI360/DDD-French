package org.fhi360.ddd.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.fhi360.ddd.Db.DDDDb;
import org.fhi360.ddd.R;
import org.fhi360.ddd.domain.Pharmacy;
import org.fhi360.ddd.domain.Drug;
import org.fhi360.ddd.domain.IssuedDrug;
import org.fhi360.ddd.dto.Response;
import org.fhi360.ddd.webservice.APIService;
import org.fhi360.ddd.webservice.ClientAPI;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

import static org.fhi360.ddd.util.Constants.PREFERENCES_ENCOUNTER;


public class test extends AppCompatActivity {
    private TextView noOfPatient;
    private Pharmacy account;
    private View view1, view2, view3, view4, view5, view6;
    private LinearLayout layout1, layout2, layout3, layout4, layout5, layout6;
    private EditText quantity, basicUnit, batchNumber, expireDate;
    private Spinner drugName;
    private SharedPreferences preferences;
    private Calendar myCalendar = Calendar.getInstance();
    private Button button;
    private TextView name;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drug_issue);
        this.preferences = getSharedPreferences(PREFERENCES_ENCOUNTER, 0);
        restorePreferences();
        if (savedInstanceState != null) {
            String json = savedInstanceState.getString("account");
            account = new Gson().fromJson(json, Pharmacy.class);
        }
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InventorySetup.class);
                startActivity(intent);
                finish();
            }
        });

        noOfPatient = findViewById(R.id.noOfPatient);
        name = findViewById(R.id.name);
        expireDate = findViewById(R.id.expiredDate);
        basicUnit = findViewById(R.id.basicUnit);
        batchNumber = findViewById(R.id.batcNumber);
        drugName = findViewById(R.id.drugName);
        quantity = findViewById(R.id.quantity);
        button = findViewById(R.id.register);
        name.setText(account.getName().toUpperCase());

        view3 = findViewById(R.id.view3);
        view4 = findViewById(R.id.view4);
        view5 = findViewById(R.id.view5);
        view6 = findViewById(R.id.view6);


        layout2 = findViewById(R.id.layout2);
        layout3 = findViewById(R.id.layout3);
        layout4 = findViewById(R.id.layout4);
        layout5 = findViewById(R.id.layout5);

        view3.setVisibility(View.INVISIBLE);
        view4.setVisibility(View.INVISIBLE);
        view5.setVisibility(View.INVISIBLE);
        view6.setVisibility(View.INVISIBLE);

        layout2.setVisibility(View.INVISIBLE);
        layout3.setVisibility(View.INVISIBLE);
        layout4.setVisibility(View.INVISIBLE);
        layout5.setVisibility(View.INVISIBLE);
        final ArrayList regimenId = new ArrayList();
        final ArrayList drugId = new ArrayList();
        ArrayList drugNames = new ArrayList();
//        drugId.add(0, 0);
//        regimenId.add(0, 0);
//        drugNames.add(0, "");

        List<Drug> drugs = DDDDb.getInstance(this).drugRepository().findByAll();
        for (Drug drug : drugs) {
            System.out.println("DRUG " + drug);
            drugId.add(drug.getId());
            regimenId.add(drug.getRegimeId());
            drugNames.add(drug.getDrugName());
        }

        int count = DDDDb.getInstance(this).patientRepository().count(account.getId());
        noOfPatient.setText("Nombre de patient  " + count);
        final ArrayAdapter drug = new ArrayAdapter<>(test.this,
                R.layout.support_simple_spinner_dropdown_item, drugNames);
        drugName.setAdapter(drug);

        drugName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Long regimenId1 = (Long) regimenId.get(position);
                Integer drug1 = (Integer) drugId.get(position);
                if (regimenId1 != 0) {
                    savePin(regimenId1 + "");
                    Drug drug2 = DDDDb.getInstance(getApplicationContext()).drugRepository().findOne(drug1);
                    basicUnit.setText(drug2.getBasicUnit());
                    view3.setVisibility(View.VISIBLE);
                    view4.setVisibility(View.VISIBLE);
                    view4.setVisibility(View.VISIBLE);
                    view5.setVisibility(View.VISIBLE);
                    view6.setVisibility(View.VISIBLE);
                    layout2.setVisibility(View.VISIBLE);
                    layout3.setVisibility(View.VISIBLE);
                    layout4.setVisibility(View.VISIBLE);
                    layout5.setVisibility(View.VISIBLE);
                } else {
                    view3.setVisibility(View.INVISIBLE);
                    view4.setVisibility(View.INVISIBLE);
                    view4.setVisibility(View.INVISIBLE);
                    view5.setVisibility(View.INVISIBLE);
                    view6.setVisibility(View.INVISIBLE);
                    layout2.setVisibility(View.INVISIBLE);
                    layout3.setVisibility(View.INVISIBLE);
                    layout4.setVisibility(View.INVISIBLE);
                    layout5.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
                final DatePickerDialog mDatePicker = new DatePickerDialog(test.this, dateLastClinic1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.show();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                HashMap<String, String> regId = getId();
                Long id = Long.valueOf(regId.get("id"));

                IssuedDrug drug = DDDDb.getInstance(test.this).drugIssuedRepository().findByAllBYId(id, account.getPin());
                if (drug != null) {
                    FancyToast.makeText(getApplicationContext(), "Un médicament avec ces informations d'identification existe déjà", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                } else {
                    String basicUnit1 = basicUnit.getText().toString();
                    String expireDate1 = expireDate.getText().toString();
                    String batchNumber1 = batchNumber.getText().toString();
                    String quantity1 = quantity.getText().toString();
                    if (validateInput(basicUnit1, expireDate1, batchNumber1, quantity1)) {
                        IssuedDrug issuedDrug = new IssuedDrug();
                        issuedDrug.setBatchNumber(batchNumber1);
                        issuedDrug.setRegimenId(id);
                        issuedDrug.setPinCode(account.getPin());
                        issuedDrug.setExpireDate(expireDate1);
                        issuedDrug.setQuantity(Double.parseDouble(quantity1));
                        DDDDb.getInstance(getApplicationContext()).drugIssuedRepository().save(issuedDrug);
                        save(issuedDrug);
                        FancyToast.makeText(getApplicationContext(), "Médicament délivré avec succès", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                    }
                }
            }
        });

    }

    private void save(IssuedDrug issuedDrug) {
        ProgressDialog progressdialog = new ProgressDialog(this);
        progressdialog.setMessage("Sauver");
        progressdialog.setCancelable(false);
        progressdialog.setIndeterminate(false);
        progressdialog.setMax(100);
        progressdialog.show();
        ClientAPI clientAPI = APIService.createService(ClientAPI.class);
        Call<Response> objectCall = clientAPI.saveInventory(issuedDrug);
        objectCall.enqueue(new Callback<Response>() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onResponse(Call<org.fhi360.ddd.dto.Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    progressdialog.dismiss();
                } else {
                    FancyToast.makeText(getApplicationContext(), "Contacter l'administrateur système", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    progressdialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<org.fhi360.ddd.dto.Response> call, Throwable t) {
                t.printStackTrace();
                FancyToast.makeText(getApplicationContext(), "Pas de connexion Internet", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                progressdialog.dismiss();
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
