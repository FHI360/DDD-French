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
import androidx.cardview.widget.CardView;

import org.fhi360.ddd.R;

import com.highsoft.highcharts.Common.HIChartsClasses.HIChart;
import com.highsoft.highcharts.Common.HIChartsClasses.HIColumn;
import com.highsoft.highcharts.Common.HIChartsClasses.HICondition;
import com.highsoft.highcharts.Common.HIChartsClasses.HICrosshair;
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
import com.highsoft.highcharts.Common.HIChartsClasses.HIStackLabels;
import com.highsoft.highcharts.Common.HIChartsClasses.HISubtitle;
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

public class OutletDashboard extends AppCompatActivity {
    private HIChartView lineChart, barChart;
    private EditText to, from;
    private Calendar myCalendar = Calendar.getInstance();
    private Button search;
    private CardView personalDetailsCard1, personalDetailsCard;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        to = findViewById(R.id.to);
        to = findViewById(R.id.to);
        from = findViewById(R.id.from);
        lineChart = findViewById(R.id.linechart);
        search = findViewById(R.id.search);
        barChart = findViewById(R.id.barchart);
        barChart.setVisibility(View.INVISIBLE);
        lineChart.setVisibility(View.INVISIBLE);

        from.setText("01-10-2019");
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        to.setText(dateFormat.format(new Date()));
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReportHomeOption.class);
                startActivity(intent);
                finish();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Patient> patients = DDDDb.getInstance(getApplicationContext()).patientRepository().dateRange(to.getText().toString(), from.getText().toString());
                barChart.setVisibility(View.VISIBLE);
                lineChart.setVisibility(View.VISIBLE);
                lineChart();
                create();
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
                final DatePickerDialog mDatePicker = new DatePickerDialog(OutletDashboard.this, to1, myCalendar
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
                final DatePickerDialog mDatePicker = new DatePickerDialog(OutletDashboard.this, from1, myCalendar
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
        title.setText("Taux de tenue de rendez-vous");
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
        title.setText("Nombre de clients servis dans ce point de vente");
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
        yaxis.getTitle().setText("Nombre total de clients");
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

        HIPlotOptions plotOptions = new HIPlotOptions();
        plotOptions.setColumn(new HIColumn());
        plotOptions.getColumn().setStacking("normal");
        plotOptions.getColumn().setDataLabels(new HIDataLabels());
        plotOptions.getColumn().getDataLabels().setEnabled(true);
        plotOptions.getColumn().getDataLabels().setColor(HIColor.initWithName("white"));
        options.setPlotOptions(plotOptions);


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

    public void create() {
        HIChartView chartView = findViewById(R.id.barchart);
        HIOptions options = new HIOptions();

        HIChart chart = new HIChart();
        chart.setType("column");
        options.setChart(chart);

        HITitle title = new HITitle();
        title.setText("Nombre de clients servis dans ce point de vente");
        options.setTitle(title);


        final HIXAxis xAxis = new HIXAxis();
        String[] categoriesList = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        xAxis.setCategories(new ArrayList<>(Arrays.asList(categoriesList)));
        xAxis.setCrosshair(new HICrosshair());
        options.setXAxis(new ArrayList<HIXAxis>() {{
            add(xAxis);
        }});

        final HIYAxis yAxis = new HIYAxis();
        yAxis.setMin(0);
        yAxis.setMax(100);
        yAxis.setTitle(new HITitle());
        yAxis.getTitle().setText("Nombre total de clients");
        options.setYAxis(new ArrayList<HIYAxis>() {{
            add(yAxis);
        }});

        HITooltip tooltip = new HITooltip();
        tooltip.setHeaderFormat("<span style=\"font-size:10px\">{point.key}</span><table>");
        tooltip.setPointFormat("<tr><td style=\"color:{series.color};padding:0\">{series.name}: </td><td style=\"padding:0\"><b>{point.y:.1f}</b></td></tr>");
        tooltip.setFooterFormat("</table>");
        tooltip.setShared(true);
        tooltip.setUseHTML(true);
        options.setTooltip(tooltip);

        HIPlotOptions plotOptions = new HIPlotOptions();
        plotOptions.setColumn(new HIColumn());
        plotOptions.getColumn().setPointPadding(0.2);
        plotOptions.getColumn().setBorderWidth(0);
        options.setPlotOptions(plotOptions);

        HIColumn series1 = new HIColumn();
        series1.setName("Male");
        Number[] series1_data = new Number[]{20.1, 25, 30, 40, 50, 60, 70, 80, 90, 30, 20, 15};
        series1.setData(new ArrayList<>(Arrays.asList(series1_data)));
        HIColumn series2 = new HIColumn();
        series2.setName("Female");
        Number[] series2_data = new Number[]{15, 20, 60, 70, 70, 84, 105, 104, 91, 83, 100, 92};
        series2.setData(new ArrayList<>(Arrays.asList(series2_data)));
        options.setSeries(new ArrayList<HISeries>(Arrays.asList(series1, series2)));
        chartView.setOptions(options);
    }
}
