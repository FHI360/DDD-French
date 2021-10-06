package org.fhi360.ddd.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.fhi360.ddd.R;

import com.highsoft.highcharts.Common.HIChartsClasses.HIColumn;
import com.highsoft.highcharts.Common.HIChartsClasses.HICondition;
import com.highsoft.highcharts.Common.HIChartsClasses.HIDataLabels;
import com.highsoft.highcharts.Common.HIChartsClasses.HILabel;
import com.highsoft.highcharts.Common.HIChartsClasses.HILabels;
import com.highsoft.highcharts.Common.HIChartsClasses.HILegend;
import com.highsoft.highcharts.Common.HIChartsClasses.HILine;
import com.highsoft.highcharts.Common.HIChartsClasses.HIOptions;
import com.highsoft.highcharts.Common.HIChartsClasses.HIPlotOptions;
import com.highsoft.highcharts.Common.HIChartsClasses.HIResponsive;
import com.highsoft.highcharts.Common.HIChartsClasses.HIRules;
import com.highsoft.highcharts.Common.HIChartsClasses.HISeries;
import com.highsoft.highcharts.Common.HIChartsClasses.HIStyle;
import com.highsoft.highcharts.Common.HIChartsClasses.HITitle;
import com.highsoft.highcharts.Common.HIChartsClasses.HITooltip;
import com.highsoft.highcharts.Common.HIChartsClasses.HIXAxis;
import com.highsoft.highcharts.Common.HIChartsClasses.HIYAxis;
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

public class AdminDashboard extends AppCompatActivity {
    private HIChartView lineChart, barChart;
    private EditText to, from;
    private Calendar myCalendar = Calendar.getInstance();
    private Button search;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main22);
        to = findViewById(R.id.to);
        from = findViewById(R.id.from);
        lineChart = findViewById(R.id.linechart);
        search = findViewById(R.id.search);
        barChart = findViewById(R.id.barchart);
        barChart.setVisibility(View.INVISIBLE);
        lineChart.setVisibility(View.INVISIBLE);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReportHomeOptionAdmin.class);
                startActivity(intent);
                finish();
            }
        });

        from.setText("01-10-2019");
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
                    lineChart();
                    setBarChart();
                } else {
                    barChart.setVisibility(View.VISIBLE);
                    lineChart.setVisibility(View.VISIBLE);
                    lineChart();
                    setBarChart();
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
                final DatePickerDialog mDatePicker = new DatePickerDialog(AdminDashboard.this, to1, myCalendar
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
                final DatePickerDialog mDatePicker = new DatePickerDialog(AdminDashboard.this, from1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.show();
            }
        });


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


    public void lineChart() {
        HIChartView chartView = findViewById(R.id.linechart);
        HIOptions options = new HIOptions();

        HITitle title = new HITitle();
        title.setText("Taux de tenue de rendez-vous,");
        options.setTitle(title);

        HIYAxis yaxis = new HIYAxis();
        yaxis.setTitle(new HITitle());
        yaxis.setLabels(new HILabels());
        yaxis.setMax(100);
        yaxis.setMin(0);
        yaxis.getTitle().setText("");
        yaxis.getLabels().setFormat("{value}%");
        options.setYAxis(new ArrayList<>(Collections.singletonList(yaxis)));

        HILegend legend = new HILegend();
        legend.setLayout("vertical");
        legend.setAlign("right");
        legend.setVerticalAlign("middle");
        options.setLegend(legend);

        HIXAxis xaxis = new HIXAxis();
        xaxis.setCategories(new ArrayList<>(Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")));
        options.setXAxis(new ArrayList<>(Collections.singletonList(xaxis)));

        HIPlotOptions plotoptions = new HIPlotOptions();
        plotoptions.setSeries(new HISeries());
        plotoptions.getSeries().setLabel(new HILabel());
        plotoptions.getSeries().getLabel().setConnectorAllowed(false);
        options.setPlotOptions(plotoptions);

        HILine line1 = new HILine();
        line1.setName("Pharmacy");
        line1.setData(new ArrayList<>(Arrays.asList(90, 100, 92, 93, 94, 96, 97, 98)));

        HILine line2 = new HILine();
        line2.setName("CARG");
        line2.setData(new ArrayList<>(Arrays.asList(100, 96, 97, 98, 99, 95, 92, 90)));

        HILine line3 = new HILine();
        line3.setName("Home Delivery");
        line3.setData(new ArrayList<>(Arrays.asList(90, 96, 97, 98, 99, 95, 92, 95)));


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
        options.setSeries(new ArrayList<HISeries>(Arrays.asList(line1, line2, line3)));
        chartView.setOptions(options);
    }

    public void setBarChart() {
        HIOptions options = new HIOptions();
        HITitle title = new HITitle();
        title.setText("Nombre de clients servis dans les points de vente DDD");
        options.setTitle(title);

        final HIXAxis xAxis = new HIXAxis();
        xAxis.setType("category");
        xAxis.setLabels(new HILabels());
        xAxis.getLabels().setRotation(-45);
        xAxis.getLabels().setStyle(new HIStyle());
        xAxis.getLabels().getStyle().setFontSize("13px");
        xAxis.getLabels().getStyle().setFontFamily("Verdana, sans-serif");
        options.setXAxis(new ArrayList<HIXAxis>() {{
            add(xAxis);
        }});

        final HIYAxis yAxis = new HIYAxis();
        yAxis.setMin(0);
        yAxis.setTitle(new HITitle());
        yAxis.getTitle().setText("Outlets");
        options.setYAxis(new ArrayList<HIYAxis>() {{
            add(yAxis);
        }});

        HILegend legend = new HILegend();
        legend.setEnabled(false);
        options.setLegend(legend);

        HITooltip tooltip = new HITooltip();
        tooltip.setPointFormat("Nombre total de clients DDD servis : <b>{point.y:.1f}</b>");
        options.setTooltip(tooltip);

        HIColumn series1 = new HIColumn();
        series1.setName("Outlets");
//        List<Patient> patients = DDDDb.getInstance(getApplicationContext()).patientRepository().dateRange(to.getText().toString(), from.getText().toString());
//        Object[] object = new Object[0];
//        int size = 0;
//        for (Patient patient : patients) {
//            size++;
//            Account account = DDDDb.getInstance(getApplicationContext()).pharmacistAccountRepository().findByCommunityPin(patient.getPinCode());
//            object = new Object[]{account.getPharmacy() + "  " + account.getType(), (double) size};
//        }
        Object[] object1 = new Object[]{"CARE Pharmacy", 23};
        Object[] object2 = new Object[]{"DUMMERICS Pharmacy", 16};
        Object[] object3 = new Object[]{"HIGHIRE Pharmacy", 14};
        Object[] object4 = new Object[]{"INVIVO Pharmacy", 14};
        Object[] object5 = new Object[]{"SILVERINE Pharmacy", 12};
        Object[] object6 = new Object[]{"ROZEC Pharmacy", 12};
        Object[] object7 = new Object[]{"MEBIK Pharmacy", 11};
        Object[] object8 = new Object[]{"VERGATE  Pharmacy", 11};
        Object[] object9 = new Object[]{"DEVAN Pharmacy", 11};
        Object[] object10 = new Object[]{"DRUGSERVE Pharmacy", 11};
        Object[] object11 = new Object[]{"JAYKAY CARG", 10};
        Object[] object12 = new Object[]{"ZIKAMED  CARG", 10};
        Object[] object13 = new Object[]{"JOGEN CARG", 10};
        Object[] object14 = new Object[]{"COMELY CARG", 9};
        Object[] object15 = new Object[]{"ALPHA CARG", 9};
        Object[] object16 = new Object[]{"LOPEZ Home Delivery", 9};
        Object[] object17 = new Object[]{"EMMACITY Home Delivery", 8};
        Object[] object18 = new Object[]{"LINK Home Delivery", 8};
        Object[] object19 = new Object[]{"BOLA Home Delivery", 8};
        Object[] object20 = new Object[]{"WIMOC Home Delivery ", 8};
        series1.setData(new ArrayList<>(Arrays.asList(object1, object2, object3, object4, object5, object6, object7, object8, object9, object10, object11, object12, object13, object14, object15, object16, object17, object18, object19, object20)));
        series1.setDataLabels(new HIDataLabels());
        series1.getDataLabels().setEnabled(true);
        series1.getDataLabels().setRotation(-90);
        series1.getDataLabels().setColor(HIColor.initWithHexValue("4A47A7"));
        series1.getDataLabels().setAlign("left");
        // series1.getDataLabels().setFormat("{point.y:.1f}");
        series1.getDataLabels().setY(10);
        series1.getDataLabels().setStyle(new HIStyle());
        series1.getDataLabels().getStyle().setFontSize("13px");
        series1.getDataLabels().getStyle().setFontFamily("Verdana, sans-serif");
        options.setSeries(new ArrayList<HISeries>(Collections.singletonList(series1)));
        barChart.setOptions(options);

    }


//    public void barChart() {
//        HIChartView chartView = findViewById(R.id.barchart);
//        HIOptions options = new HIOptions();
//        HITitle title = new HITitle();
//        title.setText("Number of Clients served in DDD outlets");
//        options.setTitle(title);
//
//        HISubtitle subtitle = new HISubtitle();
//        subtitle.setText("Plain");
//        options.setSubtitle(subtitle);
//
//        HIXAxis xaxis = new HIXAxis();
//        xaxis.setCategories(new ArrayList<>(Arrays.asList("Pharmacie", "CAG", "Accueil Drive", "Livraison à domicile")));
//        options.setXAxis(new ArrayList<>(Collections.singletonList(xaxis)));
//
//        HIYAxis yaxis = new HIYAxis();
//        yaxis.setMin(0);
//        yaxis.setTitle(new HITitle());
//        yaxis.getTitle().setText("Total fruit consumption");
//        yaxis.setStackLabels(new HIStackLabels());
//        yaxis.getStackLabels().setEnabled(true);
//        options.setYAxis(new ArrayList<>(Collections.singletonList(yaxis)));
//
//        HILegend legend = new HILegend();
//        legend.setAlign("right");
//        legend.setX(-30);
//        legend.setVerticalAlign("top");
//        legend.setY(25);
//        legend.setFloating(true);
//        legend.setBackgroundColor(HIColor.initWithName("white"));
//        legend.setBorderColor(HIColor.initWithHexValue("ccc"));
//        legend.setBorderWidth(1);
//        legend.setShadow(false);
//        options.setLegend(legend);
//
//        HITooltip tooltip = new HITooltip();
//        tooltip.setPointFormat("{series.name}: {point.y}<br/>Total: {point.stackTotal}");
//        tooltip.setHeaderFormat("<b>{point.x}</b><br/>");
//        options.setTooltip(tooltip);
//
//        HIPlotOptions plotoptions = new HIPlotOptions();
//        plotoptions.setColumn(new HIColumn());
//        plotoptions.getColumn().setStacking("normal");
//        plotoptions.getColumn().setDataLabels(new HIDataLabels());
//        plotoptions.getColumn().getDataLabels().setEnabled(true);
//        plotoptions.getColumn().getDataLabels().setColor(HIColor.initWithName("white"));
//        options.setPlotOptions(plotoptions);
//
//        HIColumn column1 = new HIColumn();
//        column1.setName("John Pharmacy");
//        Number[] column1Data = new Number[]{5, 3, 4, 7, 2};
//        column1.setData(new ArrayList<>(Arrays.asList(column1Data)));
//
//        HIColumn column2 = new HIColumn();
//        column2.setName("Jane Pharmacy");
//        Number[] column2Data = new Number[]{2, 2, 3, 2, 1};
//        column2.setData(new ArrayList<>(Arrays.asList(column2Data)));
//
//        HIColumn column3 = new HIColumn();
//        column3.setName("Joe Pharmacy");
//        Number[] column3Data = new Number[]{3, 4, 4, 2, 5};
//        column3.setData(new ArrayList<>(Arrays.asList(column3Data)));
//
//        options.setSeries(new ArrayList<HISeries>(Arrays.asList(column1, column2, column3)));
//        chartView.setOptions(options);
//
//    }


}
