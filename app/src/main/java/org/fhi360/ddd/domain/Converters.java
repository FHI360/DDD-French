package org.fhi360.ddd.domain;

import androidx.room.TypeConverter;
import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }



    @TypeConverter
    public static Patient fromPatient(Long value) {
        return value == null ? null : new Patient(value);
    }

    @TypeConverter
    public static Long PatientToId(Patient patient) {
        return patient == null ? null : patient.getId();
    }

}