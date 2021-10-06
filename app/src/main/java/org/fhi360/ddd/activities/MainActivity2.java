package org.fhi360.ddd.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.fhi360.ddd.R;
import com.highsoft.highcharts.Common.HIChartsClasses.*;
import com.highsoft.highcharts.Common.HIColor;
import com.highsoft.highcharts.Core.HIChartView;

import org.fhi360.ddd.Db.DDDDb;
import org.fhi360.ddd.domain.Patient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {
    HIChartView lineChart, barChart;
    private EditText to, from;
    private Calendar myCalendar = Calendar.getInstance();
    private Button search;
    private CardView personalDetailsCard1, personalDetailsCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        to = findViewById(R.id.to);
//        personalDetailsCard1 = findViewById(R.id.personalDetailsCard1);
//        personalDetailsCard = findViewById(R.id.personalDetailsCard);
        from = findViewById(R.id.from);
        lineChart = findViewById(R.id.linechart);
        search = findViewById(R.id.search);
        barChart = findViewById(R.id.barchart);
        barChart.setVisibility(View.INVISIBLE);
        lineChart.setVisibility(View.INVISIBLE);
        personalDetailsCard1.setVisibility(View.INVISIBLE);
        personalDetailsCard.setVisibility(View.INVISIBLE);
        from.setText("27-12-2016");
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        to.setText(dateFormat.format(new Date()));

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Patient> patients = DDDDb.getInstance(getApplicationContext()).patientRepository().dateRange(to.getText().toString(), from.getText().toString());
                if (!patients.isEmpty()) {
                    barChart.setVisibility(View.VISIBLE);
                    lineChart.setVisibility(View.VISIBLE);
                    personalDetailsCard1.setVisibility(View.VISIBLE);
                    personalDetailsCard.setVisibility(View.VISIBLE);
                    lineChart();
                    barChart();
                } else {
//                    FancyToast.makeText(getApplicationContext(), "No Data Found", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    barChart.setVisibility(View.VISIBLE);
                    lineChart.setVisibility(View.VISIBLE);
                    personalDetailsCard1.setVisibility(View.VISIBLE);
                    personalDetailsCard.setVisibility(View.VISIBLE);
                    lineChart();
                    barChart();
                }
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
                final DatePickerDialog mDatePicker = new DatePickerDialog(MainActivity2.this, to1, myCalendar
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
                final DatePickerDialog mDatePicker = new DatePickerDialog(MainActivity2.this, from1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.show();
            }
        });


    }

    public void lineChart() {
        HIChartView chartView = findViewById(R.id.linechart);
        HIOptions options = new HIOptions();

        HITitle title = new HITitle();
        title.setText("Appointment Keeping rates, " + from.getText().toString() + " to " + to.getText().toString());
        options.setTitle(title);

        HIYAxis yaxis = new HIYAxis();
        yaxis.setTitle(new HITitle());
        yaxis.setLabels(new HILabels());
        yaxis.setMax(100);
        yaxis.getTitle().setText("");
        yaxis.getLabels().setFormat("{value}%");
        options.setYAxis(new ArrayList<>(Collections.singletonList(yaxis)));

        HILegend legend = new HILegend();
        legend.setLayout("vertical");
        legend.setAlign("right");
        legend.setVerticalAlign("middle");
        options.setLegend(legend);

        HIXAxis xaxis = new HIXAxis();
        String[] categoriesList = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        xaxis.setCategories(new ArrayList<>(Arrays.asList(categoriesList)));
        options.setXAxis(new ArrayList<>(Collections.singletonList(xaxis)));

        HIPlotOptions plotoptions = new HIPlotOptions();
        plotoptions.setSeries(new HISeries());
        plotoptions.getSeries().setLabel(new HILabel());
        plotoptions.getSeries().getLabel().setConnectorAllowed(false);
        options.setPlotOptions(plotoptions);

        HILine line1 = new HILine();
        line1.setName("Male");
        line1.setData(new ArrayList<>(Arrays.asList(90, 100, 92, 93, 94, 96, 97, 98)));

        HILine line2 = new HILine();
        line2.setName("Female");
        line2.setData(new ArrayList<>(Arrays.asList(100, 96, 97, 98, 99, 95, 92, 90)));


        HIResponsive responsive = new HIResponsive();
        HIRules rules1 = new HIRules();
        rules1.setCondition(new HICondition());
        rules1.getCondition().setMaxWidth(500);
        HashMap<String, HashMap> chartLegend = new HashMap<>();
        HashMap<String, String> legendOptions = new HashMap<>();
        legendOptions.put("layout", "horizontal");
        legendOptions.put("align", "center");
        legendOptions.put("verticalAlign", "bottom");
        chartLegend.put("legend", legendOptions);
        rules1.setChartOptions(chartLegend);
        responsive.setRules(new ArrayList<>(Collections.singletonList(rules1)));
        options.setResponsive(responsive);
        options.setSeries(new ArrayList<HISeries>(Arrays.asList(line1, line2)));
        chartView.setOptions(options);
    }

    public void barChart() {
        HIChartView chartView = findViewById(R.id.barchart);
        HIOptions options = new HIOptions();
        HITitle title = new HITitle();
        title.setText("Number of clients served in this outlet" + from.getText().toString() + " to " + to.getText().toString());
        options.setTitle(title);

        HISubtitle subtitle = new HISubtitle();
        subtitle.setText("Plain");
        options.setSubtitle(subtitle);

        HIXAxis xaxis = new HIXAxis();
        xaxis.setCategories(new ArrayList<>(Arrays.asList("Male", "Female", "Male", "Female")));
        options.setXAxis(new ArrayList<>(Collections.singletonList(xaxis)));

        HIYAxis yaxis = new HIYAxis();
        yaxis.setMin(0);
        yaxis.setTitle(new HITitle());
        yaxis.getTitle().setText("Total Number of Clients");
        yaxis.setStackLabels(new HIStackLabels());
        yaxis.getStackLabels().setEnabled(true);
        options.setYAxis(new ArrayList<>(Collections.singletonList(yaxis)));

        HILegend legend = new HILegend();
        legend.setAlign("right");
        legend.setX(-30);
        legend.setVerticalAlign("top");
        legend.setY(25);
        legend.setFloating(true);
        legend.setBackgroundColor(HIColor.initWithName("white"));
        legend.setBorderColor(HIColor.initWithHexValue("ccc"));
        legend.setBorderWidth(1);
        legend.setShadow(false);
        options.setLegend(legend);

        HITooltip tooltip = new HITooltip();
        tooltip.setPointFormat("{series.name}: {point.y}<br/>Total: {point.stackTotal}");
        tooltip.setHeaderFormat("<b>{point.x}</b><br/>");
        options.setTooltip(tooltip);

        HIPlotOptions plotoptions = new HIPlotOptions();
        plotoptions.setColumn(new HIColumn());
        plotoptions.getColumn().setStacking("normal");
        plotoptions.getColumn().setDataLabels(new HIDataLabels());
        plotoptions.getColumn().getDataLabels().setEnabled(true);
        plotoptions.getColumn().getDataLabels().setColor(HIColor.initWithName("white"));
        options.setPlotOptions(plotoptions);

        HIColumn column1 = new HIColumn();
        column1.setName("Male");
        Number[] column1Data = new Number[]{5, 3, 4, 7};
        column1.setData(new ArrayList<>(Arrays.asList(column1Data)));

        HIColumn column2 = new HIColumn();
        column2.setName("Female");
        Number[] column2Data = new Number[]{2, 2, 3, 2};
        column2.setData(new ArrayList<>(Arrays.asList(column2Data)));


        options.setSeries(new ArrayList<HISeries>(Arrays.asList(column1, column2)));
        chartView.setOptions(options);

    }

    private void to() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        to.setText(sdf.format(myCalendar.getTime()));
    }

    private void from() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        from.setText(sdf.format(myCalendar.getTime()));
    }

}

