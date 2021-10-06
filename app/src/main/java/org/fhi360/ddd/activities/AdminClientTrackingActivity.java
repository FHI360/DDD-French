package org.fhi360.ddd.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.fhi360.ddd.Db.DDDDb;
import org.fhi360.ddd.R;
import org.fhi360.ddd.domain.Appointment;
import org.fhi360.ddd.domain.Patient;
import org.fhi360.ddd.util.DateUtil;
import org.fhi360.ddd.util.EditTextDatePicker;
import org.fhi360.ddd.util.SpinnerUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static org.fhi360.ddd.util.Constants.PREFERENCES_ENCOUNTER;



public class AdminClientTrackingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private int id;
    private Long patientId;
    private Date dateLastVisit;
    private Date dateNextVisit;
    private Date dateTracked;
    private String typeTracking;
    private String trackingOutcome;
    private Date dateAgreed;
    private LinearLayout layoutAgreedYes;
    private Spinner typeTrackingSpinner;
    private Spinner trackingOutcomeSpinner;
    private Patient patient;
    private boolean EDIT_MODE;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_tracking);

        this.preferences = getSharedPreferences(PREFERENCES_ENCOUNTER, 0);
        restorePreferences();
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminDefaulterListActivity.class);
                startActivity(intent);
                finish();
            }
        });


        Log.v("patient ", String.valueOf(patientId));
        Patient encounter = DDDDb.getInstance(this).patientRepository().findByPatient(patientId);

        try {
            dateLastVisit = new SimpleDateFormat("dd-MM-yyyy").parse(encounter.getDateStarted());
            dateNextVisit = new SimpleDateFormat("dd-MM-yyyy").parse(encounter.getDateNextRefill());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        layoutAgreedYes = findViewById(R.id.agreed_yes);
        layoutAgreedYes.setVisibility(View.GONE);
        //Get reference to the spinners
        typeTrackingSpinner = findViewById(R.id.type_tracking);
        trackingOutcomeSpinner = (Spinner) findViewById(R.id.tracking_outcome);
        trackingOutcomeSpinner.setOnItemSelectedListener(this);

        //Set date picker on the edit text
        new EditTextDatePicker(this, (EditText) findViewById(R.id.date_tracked));
        new EditTextDatePicker(this, (EditText) findViewById(R.id.date_agreed));


    }


    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String outcome = String.valueOf(trackingOutcomeSpinner.getSelectedItem());
        if (outcome.equalsIgnoreCase("Accepté de revenir")) {
            layoutAgreedYes.setVisibility(View.VISIBLE);
        } else {
            layoutAgreedYes.setVisibility(View.GONE);
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onResume() {
        super.onResume();
        restorePreferences();
    }

    @Override
    public void onPause() {
        super.onPause();
        savePreferences();
    }

    public void onClickSaveButton(View view) {
        dateTracked = DateUtil.parseStringToDate(((EditText) findViewById(R.id.date_tracked)).getText().toString(), "dd/MM/yyyy");
        typeTracking = String.valueOf(((Spinner) findViewById(R.id.type_tracking)).getSelectedItem());
        trackingOutcome = String.valueOf(((Spinner) findViewById(R.id.tracking_outcome)).getSelectedItem());
        dateAgreed = DateUtil.parseStringToDate(((EditText) findViewById(R.id.date_agreed)).getText().toString(), "dd/MM/yyyy");

        if (dateTracked != null) {
            int id = DDDDb.getInstance(this).appointmentRepository().getId(patientId, dateTracked);
            if (id != 0) {
                Appointment appointment = new Appointment();
                appointment.setId(id);
                appointment.setPatientId(patientId);
                appointment.setDateTracked(dateTracked);
                appointment.setTypeTracking(typeTracking);
                appointment.setTrackingOutcome(trackingOutcome);
                appointment.setDateLastVisit(dateLastVisit);
                appointment.setDateNextVisit(dateNextVisit);
                appointment.setDateAgreed(dateAgreed);
                DDDDb.getInstance(this).appointmentRepository().update(appointment);
                FancyToast.makeText(getApplicationContext(), "Client tracking data updated", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

            } else {
                Appointment appointment = new Appointment();
                appointment.setPatientId(patientId);
                appointment.setDateTracked(dateTracked);
                appointment.setTypeTracking(typeTracking);
                appointment.setTrackingOutcome(trackingOutcome);
                appointment.setDateLastVisit(dateLastVisit);
                appointment.setDateNextVisit(dateNextVisit);
                appointment.setDateAgreed(dateAgreed);
                DDDDb.getInstance(this).appointmentRepository().save(appointment);
                FancyToast.makeText(getApplicationContext(), "Données de suivi client enregistrées", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

            }
            Intent intent = new Intent(this, DefaulterListActivity.class);
            startActivity(intent);
        } else {
            FancyToast.makeText(getApplicationContext(), "Veuillez saisir la date de suivi", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

        }
    }

    private void savePreferences() {
        extractViewData();
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("dateTracked", DateUtil.parseDateToString(dateTracked, "dd/MM/yyyy"));
        editor.putString("typeTracking", typeTracking);
        editor.putString("trackingOutcome", trackingOutcome);
        editor.putString("dateAgreed", DateUtil.parseDateToString(dateAgreed, "dd/MM/yyyy"));
        editor.putString("dateLastVisit", DateUtil.parseDateToString(dateLastVisit, "dd/MM/yyyy"));
        editor.putString("dateNextVisit", DateUtil.parseDateToString(dateNextVisit, "dd/MM/yyyy"));
        editor.commit();
    }

    private void restorePreferences() {
        EDIT_MODE = preferences.getBoolean("edit_mode", false);
        String json = preferences.getString("patient", "");
        patient = new Gson().fromJson(json, Patient.class);

        //  facilityId = patient.getFacilityId();
        patientId = patient.getId();
        if (patientId == 0L) {
            patientId = patient.getId();
        }


        id = preferences.getInt("id", 0);
        dateTracked = DateUtil.parseStringToDate(Objects.requireNonNull(preferences.getString("dateTracked", "")), "dd/MM/yyyy");
        typeTracking = preferences.getString("typeTracking", "");
        trackingOutcome = preferences.getString("trackingOutcome", "");
        dateAgreed = DateUtil.parseStringToDate(Objects.requireNonNull(preferences.getString("dateAgreed", "")), "dd/MM/yyyy");
        dateLastVisit = DateUtil.parseStringToDate(Objects.requireNonNull(preferences.getString("dateLastVisit", "")), "dd/MM/yyyy");
        dateNextVisit = DateUtil.parseStringToDate(Objects.requireNonNull(preferences.getString("dateNextVisit", "")), "dd/MM/yyyy");

        EditText editText = (EditText) findViewById(R.id.date_tracked);
        editText.setText(DateUtil.parseDateToString(dateTracked, "dd/MM/yyyy"));
        Spinner spinner = (Spinner) findViewById(R.id.type_tracking);
        spinner.setSelection(SpinnerUtil.getIndex(spinner, typeTracking));
        spinner = (Spinner) findViewById(R.id.tracking_outcome);
        spinner.setSelection(SpinnerUtil.getIndex(spinner, trackingOutcome));
        editText = (EditText) findViewById(R.id.date_agreed);
        editText.setText(DateUtil.parseDateToString(dateAgreed, "dd/MM/yyyy"));
    }

    private void extractViewData() {
        dateTracked = DateUtil.parseStringToDate(((EditText) findViewById(R.id.date_tracked)).getText().toString(), "dd/MM/yyyy");
        typeTracking = String.valueOf(((Spinner) findViewById(R.id.type_tracking)).getSelectedItem());
        trackingOutcome = String.valueOf(((Spinner) findViewById(R.id.tracking_outcome)).getSelectedItem());
        dateAgreed = DateUtil.parseStringToDate(((EditText) findViewById(R.id.date_agreed)).getText().toString(), "dd/MM/yyyy");
    }

    private void clearPreferences() {
        //Reset application shared preference
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.putBoolean("edit_mode", false);
        editor.putString("patient", new Gson().toJson(patient));
        editor.apply();
    }
}
