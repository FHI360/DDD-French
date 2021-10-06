package org.fhi360.ddd.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.shashank.sony.fancytoastlib.FancyToast;

import org.fhi360.ddd.Db.DDDDb;
import org.fhi360.ddd.R;
import org.fhi360.ddd.domain.Drug;
import org.fhi360.ddd.domain.Regimen;
import org.fhi360.ddd.dto.Response;
import org.fhi360.ddd.webservice.APIService;
import org.fhi360.ddd.webservice.ClientAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class DrugSetup extends AppCompatActivity {
    private Button button;
    private EditText basicUnit;
    private Spinner drugName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_setup);
        button = findViewById(R.id.register);
        drugName = findViewById(R.id.drugName);
        basicUnit = findViewById(R.id.basicUnit);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InventorySetup.class);
                startActivity(intent);
                finish();
            }
        });

        List<Long> regimenId = new ArrayList();
        List<String> regimenName = new ArrayList();
        List<Regimen> regimen = DDDDb.getInstance(this).regimenRepository().findByAll();
        for (Regimen regimen1 : regimen) {
            regimenId.add(regimen1.getId());
            regimenName.add(regimen1.getName());
        }

        final ArrayAdapter arrayAdapter = new ArrayAdapter<>(DrugSetup.this,
                R.layout.support_simple_spinner_dropdown_item, regimenName);
        arrayAdapter.notifyDataSetChanged();
        drugName.setAdapter(arrayAdapter);
        drugName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Long regimenid1 = regimenId.get(position);
                savePin(regimenid1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String drugName1 = drugName.getSelectedItem().toString();
                String basicUnit1 = basicUnit.getText().toString();
                if (validateInput(basicUnit1)) {
                    Drug drug = DDDDb.getInstance(DrugSetup.this).drugRepository().findByDrugName(drugName1);
                    if (drug != null) {
                        FancyToast.makeText(getApplicationContext(), "Un médicament avec ces informations d'identification existe déjà", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    } else {
                        HashMap<String, String> stringHashMap = getPincode();
                        String rgId = stringHashMap.get("regimeId");
                        Drug drug1 = new Drug();
                        drug1.setRegimeId(Long.valueOf(Objects.requireNonNull(rgId)));
                        drug1.setDrugName(drugName1);
                        drug1.setBasicUnit(basicUnit1);
                        DDDDb.getInstance(DrugSetup.this).drugRepository().save(drug1);
                        save(drug1);
                        FancyToast.makeText(getApplicationContext(), "Drug Save avec succès", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                        /// drugName.setText("");
                        basicUnit.setText("");
                    }
                }
            }
        });

    }

    private void save(Drug drug) {
        ProgressDialog progressdialog = new ProgressDialog(this);
        progressdialog.setMessage("Sauver");
        progressdialog.setCancelable(false);
        progressdialog.setIndeterminate(false);
        progressdialog.setMax(100);
        progressdialog.show();
        ClientAPI clientAPI = APIService.createService(ClientAPI.class);
        Call<Response> objectCall = clientAPI.saveDrug(drug);
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

    private boolean validateInput(String basicUnit1) {
        if (basicUnit1.isEmpty()) {
            basicUnit.setError("l'unité de base ne peut pas être vide");
            return false;

        }
        return true;


    }

    public void savePin(Long regimeId) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("regimeId", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putString("regimeId", String.valueOf(regimeId));
        editor.apply();
    }

    public HashMap<String, String> getPincode() {
        HashMap<String, String> pincode = new HashMap<>();
        SharedPreferences sharedPreferences = this.getSharedPreferences("regimeId", Context.MODE_PRIVATE);
        pincode.put("regimeId", sharedPreferences.getString("regimeId", null));
        return pincode;
    }


}
