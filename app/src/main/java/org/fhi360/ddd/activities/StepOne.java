package org.fhi360.ddd.activities;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.library.NavigationBar;

import org.fhi360.ddd.R;
import org.fhi360.ddd.domain.Patient;
import org.fhi360.ddd.util.DateUtil;
import org.fhi360.ddd.util.SpinnerUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static org.fhi360.ddd.util.Constants.PREFERENCES_ENCOUNTER;

public class StepOne extends Fragment {
    private int id;
    private Long patientId;
    private Date dateVisit;
    private Patient patient;
    private boolean EDIT_MODE;
    private SharedPreferences preferences;
    private NavigationBar bar;
    private int position = 0;
    private Spinner spinner;
    private Double bodyWeight;
    private Double height;
    private Double bmi;
    private String bmiCategory;
    private Double muac;
    private String muacCategory;
    private String supplementaryFood;
    private String nutritionalStatusReferred;
    private EditText bodyWeightEditText;
    private EditText heightEditText;
    private LinearLayout layoutAdult;
    private LinearLayout layoutPediatrics;
    private LinearLayout layoutSupplement;
    private Double bp;
    EditText vital;
    EditText vital1;
    EditText dateVisit1;
    private Calendar myCalendar = Calendar.getInstance();
    EditText howmany, date_next_clinic_visit, viral_load_deu_date, addversreactiontext, dateStartedTbTreatment, duration44, prescribed44,
            dispensed44, dateNextRefill, dispensed32, duration32, prescribed32, duration22,
            dispensed22, prescribed22, bodyWeight1, height1, bp1, duration11, dispensed11, prescribed11;

    @SuppressLint("CutPasteId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_one, container,
                false);
        this.preferences = Objects.requireNonNull(getContext()).getSharedPreferences(PREFERENCES_ENCOUNTER, 0);
        restorePreferences(rootView);
        if (savedInstanceState != null) {
            String json = savedInstanceState.getString("patient");
            patient = new Gson().fromJson(json, Patient.class);
        }

        dateVisit1 = rootView.findViewById(R.id.date_visit);
        bodyWeight1 = rootView.findViewById(R.id.body_weight);
        bp1 = rootView.findViewById(R.id.bp2);
        vital1 = rootView.findViewById(R.id.body_weight);

        return rootView;
    }


    public void restorePreferences(View rootView) {
        EDIT_MODE = preferences.getBoolean("edit_mode", false);
        String json = preferences.getString("patient", "");
        patient = new Gson().fromJson(json, Patient.class);
        patientId = patient.getId();

        id = preferences.getInt("id", 0);
        dateVisit = DateUtil.parseStringToDate(Objects.requireNonNull(preferences.getString("dateVisit", "")), "dd/MM/yyyy");   //dateVisit = DateUtil.unixTimestampToDate(preferences.getLong("dateVisit",  new Date().getTime())/1000L);
        EditText editText = rootView.findViewById(R.id.date_visit);
        editText.setText(DateUtil.parseDateToString(dateVisit, "dd/MM/yyyy"));
        //Vitals
        String value = preferences.getString("bodyWeight", "");
        bodyWeight = Objects.requireNonNull(value).trim().isEmpty() ? null : Double.parseDouble(value);
        value = preferences.getString("height", "");
        height = Objects.requireNonNull(value).trim().isEmpty() ? null : Double.parseDouble(value);
        value = preferences.getString("bmi", "");
        bmi = Objects.requireNonNull(value).trim().isEmpty() ? null : Double.parseDouble(value);
        bmiCategory = preferences.getString("bmiCategory", "");
        value = preferences.getString("muac", "");
        muac = Objects.requireNonNull(value).trim().isEmpty() ? null : Double.parseDouble(value);
        muacCategory = preferences.getString("muacCategory", "");
        supplementaryFood = preferences.getString("supplementaryFood", "");
        nutritionalStatusReferred = preferences.getString("nutritionalStatusReferred", "");
        vital = rootView.findViewById(R.id.bp2);
        vital.setText(bp == null ? "" : Double.toString(bp));

        vital.setText(muac == null ? "" : Double.toString(muac));

        spinner.setSelection(SpinnerUtil.getIndex(spinner, muacCategory));
    }


}
