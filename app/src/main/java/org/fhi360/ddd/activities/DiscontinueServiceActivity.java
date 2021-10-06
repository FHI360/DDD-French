package org.fhi360.ddd.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.fhi360.ddd.R;

import com.shashank.sony.fancytoastlib.FancyToast;


import org.fhi360.ddd.Db.DDDDb;
import org.fhi360.ddd.domain.Patient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static org.fhi360.ddd.util.Constants.PREFERENCES_ENCOUNTER;


public class DiscontinueServiceActivity extends AppCompatActivity {
    private int id;
    private Long patientId;
    private Date dateDiscontinued;
    private String reasonDiscontinued;
    private Calendar myCalendar = Calendar.getInstance();
    private Patient patient;
    private EditText dateDiscontinued1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discontinue_service);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiscontinueServiceActivity.this, ClientProfileActivity.class);
                startActivity(intent);
            }
        });
        SharedPreferences preferences = getSharedPreferences(PREFERENCES_ENCOUNTER, 0);
        String json = preferences.getString("patient", "");
        patient = new Gson().fromJson(json, Patient.class);
        patientId = patient.getId();
        //Set date picker on the edit text
        dateDiscontinued1 = findViewById(R.id.date_discontinued);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Discontinue Service");

        Button save = findViewById(R.id.save_button);
        save.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onClick(View view) {
                try {
                    dateDiscontinued = new SimpleDateFormat("yyyy-MM-dd").parse(dateDiscontinued1.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                reasonDiscontinued = String.valueOf(((Spinner) findViewById(R.id.reason_discontinued)).getSelectedItem());
                if (dateDiscontinued != null) {
                    DDDDb.getInstance(DiscontinueServiceActivity.this).devolveRepository().update1(patientId, String.valueOf(dateDiscontinued), reasonDiscontinued);
                    FancyToast.makeText(getApplicationContext(), "Client retiré du service", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                    Intent intent = new Intent(DiscontinueServiceActivity.this, ClientProfileActivity.class);
                    startActivity(intent);
                } else {
                    FancyToast.makeText(getApplicationContext(), "Veuillez saisir la date d'arrêt du service", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                }
            }
        });

        final DatePickerDialog.OnDateSetListener dateNextClinic1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateOfNxetClinic();
            }

        };

        dateDiscontinued1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePickerDialog mDatePicker = new DatePickerDialog(DiscontinueServiceActivity.this, dateNextClinic1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
                mDatePicker.show();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }


    private void updateDateOfNxetClinic() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateDiscontinued1.setText(sdf.format(myCalendar.getTime()));

    }
}
