package org.fhi360.ddd.util;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by aalozie on 2/13/2017.
 */

public class DateUtil {
    public static Date addYear(Date currentDate, int number) {
        Date date = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.YEAR, number);
        try {
            date = calendar.getTime();     //date = (Date) dateFormat.parse(dateFormat.format(calendar.getTime()));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return date;
    }

    public static Date addMonth(Date currentDate, int number) {
        Date date = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MONTH, number);
        try {
            date = calendar.getTime();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return date;
    }

    public static Date addDay(Date currentDate, int number) {
        Date date = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, number);
        try {
            date = calendar.getTime();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return date;
    }

    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        int returnVal = 0;
        if (date != null) {
            cal.setTime(date);
            returnVal = cal.get(Calendar.DAY_OF_YEAR);
        }

        return returnVal;
    }

    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        int returnVal = 0;
        if (date != null) {
            cal.setTime(date);
            returnVal = cal.get(Calendar.MONTH);
        }


        return returnVal;
    }

    public static int getYear(Date date) {

        Calendar cal = Calendar.getInstance();
        int returnVal = 0;
        if (date != null) {
            cal.setTime(date);
            returnVal = cal.get(Calendar.YEAR);
        }

        return returnVal;
    }


    public static int getAge(String dateOfbirth) throws Exception {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar dob = Calendar.getInstance();
        dob.setTime(sdf.parse(dateOfbirth));
        Calendar today = Calendar.getInstance();
        int curYear = today.get(Calendar.YEAR);
        int dobYear = dob.get(Calendar.YEAR);
        int age = curYear - dobYear;
        int curMonth = today.get(Calendar.MONTH);
        int dobMonth = dob.get(Calendar.MONTH);
        if (dobMonth > curMonth) { // this year can't be counted!
            age--;
        } else if (dobMonth == curMonth) { // same month? check for day
            int curDay = today.get(Calendar.DAY_OF_MONTH);
            int dobDay = dob.get(Calendar.DAY_OF_MONTH);
            if (dobDay > curDay) { // this year can't be counted!
                age--;
            }
        }

        return age;
    }

    public static int getMonth(String month) {
        int number = 0;
        month = month.toUpperCase();
        if (month.equals("JANUARY")) {
            number = 1;
        }
        if (month.equals("FEBRUARY")) {
            number = 2;
        }
        if (month.equals("MARCH")) {
            number = 3;
        }
        if (month.equals("APRIL")) {
            number = 4;
        }
        if (month.equals("MAY")) {
            number = 5;
        }
        if (month.equals("JUNE")) {
            number = 6;
        }
        if (month.equals("JULY")) {
            number = 7;
        }
        if (month.equals("AUGUST")) {
            number = 8;
        }
        if (month.equals("SEPTEMBER")) {
            number = 9;
        }
        if (month.equals("OCTOBER")) {
            number = 10;
        }
        if (month.equals("NOVEMBER")) {
            number = 11;
        }
        if (month.equals("DECEMBER")) {
            number = 12;
        }
        return number;
    }

    public static String getMonth(int i) {
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        String string = "";

        if (i > 0 && i <= 12) {
            string = months[i - 1];
        }
        return string;
    }

    public static Date formatDate(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date datefmt = null;
        try {
            String dateString = dateFormat.format(date);
            datefmt = (Date) dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return datefmt;
    }

    public static Date parseStringToDate(String dateString, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
        Date date = null;
        try {
            if (!dateString.equals("") && !dateString.isEmpty()) {
                date = dateFormat.parse(dateString);
            }
        } catch (ParseException exception) {
            exception.printStackTrace();
        }
        return date;
    }

    public static String parseDateToString(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
        String dateString = "";
        try {
            dateString = (date == null ? "" : dateFormat.format(date));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return dateString;
    }

    public static Date unixTimestampToDate(long unixSeconds, String format) {
        Date date = null;
        try {
            String dateString = unixTimestampToDateString(unixSeconds, format);
            date = (Date) new SimpleDateFormat(format).parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String unixTimestampToDateString(long unixSeconds, String format) {
        Date date = new Date(unixSeconds);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format); // the format of your date e.g "yyyy-MM-dd HH:mm:ss z"
        return simpleDateFormat.format(date);
    }


    public static Date getLastDateOfMonth(int year, int month) {

        Date date = null;
        Calendar calendar = new GregorianCalendar(year, month - 1, Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        try {
            date = calendar.getTime();


        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return date;
    }

    public static Date getFirstDateOfMonth(int year, int month) {
        Date date = null;
        Calendar calendar = new GregorianCalendar(year, month - 1, Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        try {
            date = calendar.getTime();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return date;
    }

}
