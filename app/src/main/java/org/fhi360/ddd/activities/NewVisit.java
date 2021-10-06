package org.fhi360.ddd.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.shashank.sony.fancytoastlib.FancyToast;

import org.fhi360.ddd.Db.DDDDb;
import org.fhi360.ddd.R;
import org.fhi360.ddd.domain.Facility;
import org.fhi360.ddd.domain.Patient;
import org.fhi360.ddd.domain.Pharmacy;
import org.fhi360.ddd.dto.PatientDto;
import org.fhi360.ddd.dto.Response;
import org.fhi360.ddd.webservice.APIService;
import org.fhi360.ddd.webservice.ClientAPI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;

@RequiresApi(api = Build.VERSION_CODES.O)
public class NewVisit extends AppCompatActivity implements View.OnClickListener {
    private DDDDb dddDb;
    private EditText lastViralLoad, facilityName, dateRegistration,
            viralLoadDueDate, hospitalNum, uniqueId,
            dateBirth,
            dateLastViralLoad, age, address,
            dateLastRefill, dateNextRefill, dateLastClinic, dateNextClinic,
            phone;
    private AppCompatSpinner viralLoadType, lastClinicStage, gender;
    private TextInputLayout ageEstimateLayoutl;
    private Spinner dddoutlet;
    private Button save;
    private HashMap<String, String> user = null;
    private HashMap<String, String> user1 = null;
    private Calendar myCalendar = Calendar.getInstance();
    //private String deviceconfigId;
    private CheckBox estimatedAge;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_visit);
        dddDb = DDDDb.getInstance(this);
        dateRegistration = findViewById(R.id.dateRegistrations);
        dddoutlet = findViewById(R.id.dddoutlet);
        lastClinicStage = findViewById(R.id.lastClinicalStage);
        estimatedAge = findViewById(R.id.estimatedAge);
        // hospitalNum = findViewById(R.id.hospitalNum);
        uniqueId = findViewById(R.id.uniqueId);
        // surname = findViewById(R.id.surnames);
        //   otherNames = findViewById(R.id.otherNamess);
        dateBirth = findViewById(R.id.dateBirthEnrollemt);
        dateLastViralLoad = findViewById(R.id.dateLastViralLoad);
        viralLoadDueDate = findViewById(R.id.viralLoadDueDate);
        age = findViewById(R.id.ageEnrollemt);
        facilityName = findViewById(R.id.facilityName);
        address = findViewById(R.id.addresss);
        lastViralLoad = findViewById(R.id.lastViralLoad);
        gender = findViewById(R.id.genders);
        phone = findViewById(R.id.phones);
        ageEstimateLayoutl = findViewById(R.id.ageEnrollemt1);

        viralLoadType = findViewById(R.id.viralLoadType);
        dateLastRefill = findViewById(R.id.dateLastRefill);
        dateNextRefill = findViewById(R.id.dateNextRefill);
        dateLastClinic = findViewById(R.id.dateLastClinic);
        dateNextClinic = findViewById(R.id.dateNextClinic);
        save = findViewById(R.id.finishButton);
        age.setVisibility(View.VISIBLE);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Enregistrement du patient");
        estimatedAge.setOnClickListener(this);
        String facilityName1 = DDDDb.getInstance(this).facilityRepository().getFacility().getName();
        facilityName.setText(facilityName1);
        facilityName.setEnabled(false);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateRegistration.setText(dateFormat.format(new Date()));
        List<Long> pharmacyId = new ArrayList();
        List<String> outLetName = new ArrayList();
        List<Pharmacy> account = DDDDb.getInstance(this).pharmacistAccountRepository().findByAll();
        for (Pharmacy account1 : account) {
            pharmacyId.add(account1.getId());
            outLetName.add(account1.getName());
        }

        final ArrayAdapter pharmacy = new ArrayAdapter<>(NewVisit.this,
                R.layout.support_simple_spinner_dropdown_item, outLetName);
        pharmacy.notifyDataSetChanged();
        dddoutlet.setAdapter(pharmacy);
        dddoutlet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Long pharmacyId1 = pharmacyId.get(position);
                savePin(pharmacyId1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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


        dateNextClinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final DatePickerDialog mDatePicker = new DatePickerDialog(NewVisit.this, dateNextClinic1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
                mDatePicker.show();
            }
        });

        final DatePickerDialog.OnDateSetListener dateLastClinic1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateOfLastClinic();
            }

        };


        dateLastClinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final DatePickerDialog mDatePicker = new DatePickerDialog(NewVisit.this, dateLastClinic1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show();
            }
        });

        final DatePickerDialog.OnDateSetListener dateNextRefill1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateOfNextRefill();
            }

        };


        dateNextRefill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                final DatePickerDialog mDatePicker = new DatePickerDialog(NewVisit.this, dateNextRefill1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
                mDatePicker.show();


            }
        });

        final DatePickerDialog.OnDateSetListener dateLastRefill1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateOfLastRefill();
            }

        };


        dateLastRefill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final DatePickerDialog mDatePicker = new DatePickerDialog(NewVisit.this, dateLastRefill1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show();


            }
        });


        final DatePickerDialog.OnDateSetListener viralLoadDueDate1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateViralLoadDueDate();
            }

        };


        viralLoadDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                final DatePickerDialog mDatePicker = new DatePickerDialog(NewVisit.this, viralLoadDueDate1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
                mDatePicker.show();


            }
        });


        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabe2();
            }

        };

        final DatePickerDialog.OnDateSetListener dateLastViraLload1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLastViralLoad();
            }

        };

        dateBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final DatePickerDialog mDatePicker = new DatePickerDialog(NewVisit.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show();
            }
        });

        dateLastViralLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final DatePickerDialog mDatePicker = new DatePickerDialog(NewVisit.this, dateLastViraLload1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show();
            }
        });

        age.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                Calendar todayDate = Calendar.getInstance();
                try {
                    todayDate.setTime(sdf.parse(dateRegistration.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //  int curYear = todayDate.get(Calendar.YEAR);
                //  int currentMonth = todayDate.get(Calendar.MONTH);
                // int currentDay = todayDate.get(Calendar.DAY_OF_MONTH);

//                Date now = new Date();
//                Date dob =now.compareTo() //now.cu(curYear).minusMonths(currentMonth).minusDays(currentDay);
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
//                dateBirth.setText(dob.format(formatter));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        setupFloatingLabelError();
        save.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onClick(View v) {
                Long facilityId = DDDDb.getInstance(getApplicationContext()).facilityRepository().getFacility().getId();

                if (validateInput1(facilityName.getText().toString(), uniqueId.getText().toString(),dateBirth.getText().toString(), dateRegistration.getText().toString())) {
                    if (gender.getSelectedItem().toString().equals("")) {
                        FancyToast.makeText(getApplicationContext(), "Select gender", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    } else if (dddDb.patientRepository().findHospitalNum((uniqueId.getText().toString()), facilityId)) {
                        FancyToast.makeText(getApplicationContext(), "Patient already exist", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    } else {
                        try {
                            PatientDto patient = new PatientDto();
                            patient.setHospitalNum(uniqueId.getText().toString());
                            Facility facility = new Facility();
                            facility.setId(facilityId);
                            patient.setFacility(facility);
                            patient.setUniqueId(uniqueId.getText().toString());
                            patient.setSurname("");
                            patient.setOtherNames("");
                            patient.setGender(gender.getSelectedItem().toString());
                            patient.setDateBirth(dateBirth.getText().toString());
                            patient.setAddress(address.getText().toString());
                            patient.setPhone(phone.getText().toString());
                            patient.setDateStarted(dateRegistration.getText().toString());
                            patient.setLastClinicStage(lastClinicStage.getSelectedItem().toString());
                            patient.setLastViralLoad(Double.parseDouble(lastViralLoad.getText().toString()));
                            patient.setDateLastViralLoad(dateLastViralLoad.getText().toString());
                            patient.setViralLoadDueDate(viralLoadDueDate.getText().toString());
                            patient.setViralLoadType(viralLoadType.getSelectedItem().toString());
                            patient.setDateLastClinic(dateLastClinic.getText().toString());
                            patient.setDateNextClinic(dateNextClinic.getText().toString());
                            patient.setDateLastRefill(dateLastRefill.getText().toString());
                            patient.setDateNextRefill(dateNextRefill.getText().toString());
                            HashMap<String, String> pinCode = getPincode();
                            String pharmacyid = pinCode.get("pharmacyid");
                            patient.setPharmacyId(Long.valueOf(pharmacyid));
                            save(patient);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });


    }


    private void save(PatientDto patientDto) {
        ProgressDialog progressdialog = new ProgressDialog(this);
        progressdialog.setMessage("Sauver le patient...");
        progressdialog.setCancelable(false);
        progressdialog.setIndeterminate(false);
        progressdialog.setMax(100);
        progressdialog.show();
        ClientAPI clientAPI = APIService.createService(ClientAPI.class);
        Call<Response> objectCall = clientAPI.savePatient(patientDto);
        objectCall.enqueue(new Callback<Response>() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onResponse(Call<org.fhi360.ddd.dto.Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    org.fhi360.ddd.dto.Response response1 = response.body();
                    if (Objects.requireNonNull(response1).getMessage() != null) {
                        FancyToast.makeText(getApplicationContext(), response1.getMessage(), FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                        progressdialog.dismiss();
                    } else if (Objects.requireNonNull(response1).getPatient() != null) {
                        PatientDto patient = response1.getPatient();
                        Patient patient1 = new Patient();
                        patient1.setHospitalNum(patient.getHospitalNum());
                        patient1.setFacilityId(patient.getFacility().getId());
                        patient1.setUniqueId(patient.getUniqueId());
                        patient1.setSurname(patient.getSurname());
                        patient1.setOtherNames(patient.getOtherNames());
                        patient1.setGender(patient.getGender());
                        patient1.setDateBirth(patient.getDateBirth());
                        patient1.setAddress(patient.getAddress());
                        patient1.setPhone(patient.getPhone());
                        patient1.setDateStarted(patient.getDateStarted());
                        patient1.setLastClinicStage(patient.getLastClinicStage());
                        patient1.setLastViralLoad(patient.getLastViralLoad());
                        patient1.setDateLastViralLoad(patient.getDateLastViralLoad());
                        patient1.setViralLoadDueDate(patient.getViralLoadDueDate());
                        patient1.setViralLoadType(patient.getViralLoadType());
                        patient1.setDateLastClinic(patient.getDateLastClinic());
                        patient1.setDateNextClinic(patient.getDateNextClinic());
                        patient1.setDateLastRefill(patient.getDateLastRefill());
                        patient1.setDateNextRefill(patient.getDateNextRefill());
                        patient1.setPharmacyId(patient.getPharmacyId());
                        patient1.setUuid(UUID.randomUUID().toString());
                        DDDDb.getInstance(getApplicationContext()).patientRepository().save(patient1);
                        FancyToast.makeText(getApplicationContext(), "Enregistrement du patient enregistré avec succès", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
//                        hospitalNum.setText("");
                        uniqueId.setText("");
                        //facilityName.setText("");
                        //surname.setText("");
                      //  otherNames.setText("");
                        dateBirth.setText("");
                        address.setText("");
                        phone.setText("");
                        lastViralLoad.setText("");
                        dateLastClinic.setText("");
                        dateNextClinic.setText("");
                        dateNextRefill.setText("");
                        dateLastRefill.setText("");
                        progressdialog.dismiss();

                    }

                } else {
                    FancyToast.makeText(getApplicationContext(), "Contacter l'administrateur système", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    progressdialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<org.fhi360.ddd.dto.Response> call, Throwable t) {
                t.printStackTrace();
                FancyToast.makeText(getApplicationContext(), "Pas de connexion Internet", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                progressdialog.dismiss();
            }

        });

    }

    private void updateLastViralLoad() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateLastViralLoad.setText(sdf.format(myCalendar.getTime()));

    }

    private void updateViralLoadDueDate() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        viralLoadDueDate.setText(sdf.format(myCalendar.getTime()));

    }


    private void updateDateOfLastRefill() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateLastRefill.setText(sdf.format(myCalendar.getTime()));

    }

    private void updateDateOfNextRefill() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateNextRefill.setText(sdf.format(myCalendar.getTime()));

    }

    private void updateDateOfNxetClinic() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateNextClinic.setText(sdf.format(myCalendar.getTime()));

    }

    private void updateDateOfLastClinic() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateLastClinic.setText(sdf.format(myCalendar.getTime()));

    }


    private void updateLabe3() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateRegistration.setText(sdf.format(myCalendar.getTime()));

    }


    private void setupFloatingLabelError() {
        final TextInputLayout floatingUsernameLabel = findViewById(R.id.invalidPhone);
        floatingUsernameLabel.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                if (text.length() > 0 && text.length() <= 11) {
                    floatingUsernameLabel.setErrorEnabled(false);
                } else {
                    floatingUsernameLabel.setError("Invalid Phone");
                    floatingUsernameLabel.setErrorEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void updateLabe2() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateBirth.setText(sdf.format(myCalendar.getTime()));
//
//        int age1 = 0;
//        try {
//            age1 = getAge(dateBirth.getText().toString());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        String checkIfAgeContainsNegative = String.valueOf(age1);
//        if (checkIfAgeContainsNegative.contains("-")) {
//            age.setError("Invalid Age");
//        } else {
//            age.setText(String.valueOf(age1));
//        }
    }

    public static int getAge(String dateOfbirth) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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


    private boolean validateInput1(String facilityName1,String unique1, String dateBirth1, String dateRegistration) {

        if (unique1.isEmpty()) {
            uniqueId.setError("Enter Unique Id");
            FancyToast.makeText(getApplicationContext(), "Enter Facility Name", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            return false;

        }

        if (facilityName1.isEmpty()) {
            facilityName.setError("Enter Facility Name");
            FancyToast.makeText(getApplicationContext(), "Enter Facility Name", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            return false;

        }


        if (dateRegistration.isEmpty()) {
            facilityName.setError("Enter Date Registration");
            FancyToast.makeText(getApplicationContext(), "Enter Date Registration", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            return false;

        }
//        if (uniqueId1.isEmpty()) {
//            uniqueId.setError("Enter UniqueId");
//            FancyToast.makeText(getApplicationContext(), "Enter UniqueId", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
//            return false;
//
//        }
//
//        if (surname1.isEmpty()) {
//            surname.setError("Enter surname");
//            FancyToast.makeText(getApplicationContext(), "Enter surname", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
//            return false;
//
//        } else if (otherName1.isEmpty()) {
//            otherNames.setError("Enter othername");
//            // hospitalNum.setText(hospitalNum.getText().toString());
//            FancyToast.makeText(getApplicationContext(), "Enter other name", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
//            return false;
//
//        }
        if (dateBirth1.isEmpty()) {
            dateBirth.setError("Enter Date of Birth");
            //hospitalNum.setText(hospitalNum.getText().toString());
            FancyToast.makeText(getApplicationContext(), "Enter Date of Birth", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            return false;
        }


        return true;


    }

    @Override
    public void onClick(View v) {
        if (estimatedAge.isChecked() && dateBirth.getText().toString().equals("")) {
            age.setVisibility(View.VISIBLE);
            ageEstimateLayoutl.setVisibility(View.VISIBLE);
        }
        if (estimatedAge.isChecked() && !dateBirth.getText().toString().equals("")) {
            age.setVisibility(View.INVISIBLE);
            ageEstimateLayoutl.setVisibility(View.INVISIBLE);
            FancyToast.makeText(getApplicationContext(), "Age Can't be estimated due to known date of birth ", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
        }
        if (!estimatedAge.isChecked()) {
            age.setVisibility(View.INVISIBLE);
            ageEstimateLayoutl.setVisibility(View.INVISIBLE);
        }
    }

    public void savePin(Long pinCode) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("pharmacyId", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putString("pharmacyid", String.valueOf(pinCode));
        editor.apply();
    }

    public HashMap<String, String> getPincode() {
        HashMap<String, String> pincode = new HashMap<>();
        SharedPreferences sharedPreferences = this.getSharedPreferences("pharmacyId", Context.MODE_PRIVATE);
        pincode.put("pharmacyid", sharedPreferences.getString("pharmacyid", null));
        return pincode;
    }

}
