

package org.fhi360.ddd.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.shashank.sony.fancytoastlib.FancyToast;

import org.fhi360.ddd.R;
import org.fhi360.ddd.util.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;

public class AdminRountingReportingPeriod extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private Spinner yearSpinner;
    private Spinner monthSpinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_reporting_period12);
        ArrayList<String> years = new ArrayList<>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear; i >= thisYear - 5; i--) {
            years.add(Integer.toString(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_text_color, years);
        yearSpinner = findViewById(R.id.year);
        adapter.setDropDownViewResource(R.layout.hintcolour);
        yearSpinner.setAdapter(adapter);
        yearSpinner.setOnItemSelectedListener(this);

        monthSpinner = findViewById(R.id.month);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.spinner_month, R.layout.spinner_text_color);
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(R.layout.hintcolour);
        // Apply the adapter to the spinner
        monthSpinner.setAdapter(adapter1);
        monthSpinner.setOnItemSelectedListener(this);
        Button okButton = findViewById(R.id.ok_button);
        okButton.setOnClickListener(this);


        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeRoutineNationalReport.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

    }

    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, ServiceSummaryActivity1.class);
        String value = monthSpinner.getSelectedItem().toString();
        int month = DateUtil.getMonth(value);
        value = yearSpinner.getSelectedItem().toString();
        int year = value.trim().isEmpty() ? null : Integer.parseInt(value);
        if (month != 0 && year != 0) {
            intent.putExtra("month", month);
            intent.putExtra("year", year);
            startActivity(intent);
        } else {
            FancyToast.makeText(getApplicationContext(), "Veuillez saisir le mois et l'année du rapport", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
        }
    }
}
