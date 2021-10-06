package org.fhi360.ddd.util;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class EditTextDatePicker1 implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    EditText editText;
    private Calendar calendar;
    private Context context;

    public EditTextDatePicker1(Context context, EditText editText) {
        this.editText = editText;
        this.editText.setOnClickListener(this);
        this.calendar = Calendar.getInstance(TimeZone.getDefault());
        this.context = context;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        //Set the selected date on the editText
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        editText.setText(simpleDateFormat.format(calendar.getTime()));

    }

    @Override
    public void onClick(View v) {
        DatePickerDialog dialog = new DatePickerDialog(context, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        dialog.show();
    }
}