package org.fhi360.ddd.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.gson.Gson;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.fhi360.ddd.Db.DDDDb;
import org.fhi360.ddd.R;
import org.fhi360.ddd.domain.Patient;
import org.fhi360.ddd.dto.Response;
import org.fhi360.ddd.util.CSVWriter;
import org.fhi360.ddd.util.DateUtil;
import org.fhi360.ddd.webservice.APIService;
import org.fhi360.ddd.webservice.ClientAPI;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

import static org.fhi360.ddd.util.Constants.PREFERENCES_ENCOUNTER;

public class ClientProfileActivity2 extends AppCompatActivity {

    private int[] layouts;
    private Context context;
    private Patient patient;
    private int id;
    private Long patientId;
    private Spinner reasonDiscontinued;
    private Calendar myCalendar = Calendar.getInstance();
    private EditText dateDiscontinued1;
    private SharedPreferences preferences;
    private TextView textView;
    private ImageView avarter, refillhistory;
    private LinearLayout clientprofile, appointment, drugrefil, discontinueService, refilHistory;
    String clientName;

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile);
        clientprofile = findViewById(R.id.clientprofile);
        avarter = findViewById(R.id.avarter);
        appointment = findViewById(R.id.appointment);
        drugrefil = findViewById(R.id.drugrefil);
        refillhistory = findViewById(R.id.refillhistory);
        discontinueService = findViewById(R.id.discontinueService);
        refilHistory = findViewById(R.id.refilHistory);
        textView = findViewById(R.id.textView);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientProfileActivity2.this, PatientList2.class);
                startActivity(intent);
            }
        });
        this.preferences = getSharedPreferences(PREFERENCES_ENCOUNTER, 0);
        restorePreferences();
        if (savedInstanceState != null) {
            String json = savedInstanceState.getString("patient");
            patient = new Gson().fromJson(json, Patient.class);
        }

        String firstLetterer = String.valueOf(patient.getUniqueId().charAt(0));
        String fullSurname = firstLetterer.toUpperCase() + patient.getUniqueId().substring(1).toLowerCase();

        clientName = "<font size ='30' color='#DFE6E9'><big><b>" + fullSurname + "</font></b></big> </font>";

        ((TextView) findViewById(R.id.name)).setText(Html.fromHtml(clientName), TextView.BufferType.SPANNABLE);
        if (patient.getGender().equalsIgnoreCase("Feminin")) {
            avarter.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.femaleavataricon));
        } else {
            avarter.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.male));
        }

        clientprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlert();
            }
        });

        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertAppointment();
            }
        });

        drugrefil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ARVRefill.class);
                startActivity(intent);
            }
        });

        discontinueService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDiscontinueService();
            }
        });

        refilHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refilHistory.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.blues));
                textView.setTextColor(getApplicationContext().getResources().getColor(R.color.white));
                refillhistory.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.refillhistorywhite));

                Intent intent = new Intent(getApplicationContext(), SearchARVActivity.class);
                startActivity(intent);
            }
        });
    }


    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    public void showAlert() {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.activity_client_profile_model, null);
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setView(promptsView);
        final TextView cancel_action;
        cancel_action = promptsView.findViewById(R.id.cancel_action);
        avarter = promptsView.findViewById(R.id.avarter);
        cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        if (patient.getGender().equals("Female")) {
            avarter.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.femaleavataricon));
        } else {
            avarter.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.male));
        }


        int age = 0;
        try {
            System.out.println("DATEBIRT " + patient.getDateBirth());
            age = DateUtil.getAge(patient.getDateBirth());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("NAME " + patient.getSurname());
        if (patient.getGender().equalsIgnoreCase("Femelle")) {
            avarter.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.femaleavataricon));
        } else {
            avarter.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.male));
        }
        String firstLetterer = String.valueOf(patient.getUniqueId().charAt(0));
        String fullSurname = firstLetterer.toUpperCase() + patient.getUniqueId().substring(1).toLowerCase();
        clientName = "<font size ='30' color='#000'><big><b>" + fullSurname + "</font></b></big> </font>";
        ((TextView) promptsView.findViewById(R.id.name)).setText(Html.fromHtml(clientName));

        ((TextView) promptsView.findViewById(R.id.age)).setText(Html.fromHtml("<font size ='30' color='#000'><big>" + "Âge" + "</big></font> &nbsp &nbsp" + "<font ><small>" + (age) + "</small></font>"));
        ((TextView) promptsView.findViewById(R.id.gender)).setText(Html.fromHtml("<font size ='30' color='#000'><big>" + "Genre" + "</big></font> &nbsp &nbsp" + "<font ><small>" + (patient.getGender()) + "</small></font>"));
        ((TextView) promptsView.findViewById(R.id.address)).setText(Html.fromHtml("<font size ='30' color='#000'><big>" + "Adresse" + "</big></font> &nbsp &nbsp" + "<font ><small>" + patient.getAddress() + "</small></font>"));
        ((TextView) promptsView.findViewById(R.id.phone)).setText(Html.fromHtml("<font size ='30' color='#000'><big>" + "Téléphoner" + "</big></font> &nbsp &nbsp" + "<font ><small>" + patient.getPhone() + "</small></font>"));
        dialog.setCancelable(false);
        dialog.show();
    }


    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    public void showAlertAppointment() {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.appointment, null);
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setView(promptsView);
        final TextView cancel_action;
        ConstraintLayout constraintLayout = promptsView.findViewById(R.id.appointment);
        cancel_action = promptsView.findViewById(R.id.cancel_action);
        Button reminder = promptsView.findViewById(R.id.sendReminder);
        constraintLayout.setBackgroundResource(0);
        avarter = promptsView.findViewById(R.id.avarter);
        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FancyToast.makeText(getApplicationContext(), "L'e-mail % SMS au client a été effectué avec succès", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
            }
        });
        cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        System.out.println("NAME " + patient.getSurname());
        if (patient.getGender().equals("Female")) {
            avarter.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.femaleavataricon));
        } else {
            avarter.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.male));
        }
        String firstLetterer = String.valueOf(patient.getSurname().charAt(0));
        String fullSurname = firstLetterer.toUpperCase() + patient.getSurname().substring(1).toLowerCase();
        String firstLettersOtherName = String.valueOf(patient.getOtherNames().charAt(0));
        String fullOtherName = firstLettersOtherName.toUpperCase() + patient.getOtherNames().substring(1).toLowerCase();
        clientName = "<font size ='30' color='#FFFFFF'><big><b>" + fullSurname + "</font></b></big> &nbsp &nbsp" + "<font color='#FFFFFF'>" + fullOtherName + "</font>";
        ((TextView) promptsView.findViewById(R.id.name)).setText(Html.fromHtml(clientName));
        ((TextView) promptsView.findViewById(R.id.lastViralLoad)).setText(Html.fromHtml("<font size ='30' color='#000'><big>" + "Dernier approvisionnement" + "</big></font> &nbsp &nbsp" + "<font ><small>" + (patient.getDateLastRefill()) + "</small></font>"));
        ((TextView) promptsView.findViewById(R.id.dateLastViralLoad)).setText(Html.fromHtml("<font size ='30' color='#000'><big>" + "Dernière CV/Date" + "</big></font> &nbsp &nbsp" + "<font ><small>" + patient.getLastViralLoad() + "</small></font>"));
        ((TextView) promptsView.findViewById(R.id.next_clinic)).setText(Html.fromHtml("<font size ='30' color='#000'><big>" + "Prochaine visite clinique" + "</big></font> &nbsp &nbsp" + "<font ><small>" + patient.getDateNextClinic() + "</small></font>"));
        ((TextView) promptsView.findViewById(R.id.next_viralLoad)).setText(Html.fromHtml("<font size ='30' color='#000'><big>" + "Prochaine CV" + "</big></font> &nbsp &nbsp" + "<font ><small>" + patient.getViralLoadDueDate() + "</small></font>"));

        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        //saveInstanceState.putBoolean("edit_mode", EDIT_MODE);
        saveInstanceState.putString("patient", new Gson().toJson(patient));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String json = savedInstanceState.getString("patient");
        patient = new Gson().fromJson(json, Patient.class);
    }

    private void restorePreferences() {
        String json = preferences.getString("patient", "");
        patient = new Gson().fromJson(json, Patient.class);
    }

    private void clearPreferences() {
        //Reset application shared preference
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.putBoolean("edit_mode", false);
        editor.putString("patient", new Gson().toJson(patient));
        editor.apply();
    }

    public void export() {
        File exportDir = Environment.getExternalStorageDirectory();
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }
        File file = new File(exportDir.getAbsolutePath(), "/patient_data.csv");
        try {
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            Cursor curCSV = DDDDb.getInstance(ClientProfileActivity2.this).patientRepository().getAllPatient();
            csvWrite.writeNext(curCSV.getColumnNames());
            while (curCSV.moveToNext()) {
                String[] arrStr = new String[curCSV.getColumnCount()];
                for (int i = 0; i < curCSV.getColumnCount() - 1; i++)
                    arrStr[i] = curCSV.getString(i);
                csvWrite.writeNext(arrStr);
            }
            csvWrite.close();
            curCSV.close();

            FancyToast.makeText(this, "Exported", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
            Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "org.fhi360.ddd.fileProvider", file);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.setType("plain/text");
            intent.putExtra(Intent.EXTRA_STREAM, contentUri);
            intent.putExtra(Intent.EXTRA_SUBJECT, "Patient Data");
            Intent chooser = Intent.createChooser(intent, "Share File");
            List<ResolveInfo> resInfoList = this.getPackageManager().queryIntentActivities(chooser, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                this.grantUriPermission(packageName, contentUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            startActivity(chooser);

        } catch (Exception sqlEx) {
            //Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
        }
    }


    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    public void showAlertDiscontinueService() {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.activity_discontinue_service, null);
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setView(promptsView);
        final TextView cancel_action;
        dateDiscontinued1 = promptsView.findViewById(R.id.date_discontinued);
        cancel_action = promptsView.findViewById(R.id.cancel_action);

        Button save = promptsView.findViewById(R.id.save_button);
        avarter = promptsView.findViewById(R.id.avarter);
        reasonDiscontinued = promptsView.findViewById(R.id.reason_discontinued);

        cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        System.out.println("NAME " + patient.getSurname());
        if (patient.getGender().equals("Female")) {
            avarter.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.femaleavataricon));
        } else {
            avarter.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.male));
        }
        String firstLetterer = String.valueOf(patient.getSurname().charAt(0));
        String fullSurname = firstLetterer.toUpperCase() + patient.getSurname().substring(1).toLowerCase();
        String firstLettersOtherName = String.valueOf(patient.getOtherNames().charAt(0));
        String fullOtherName = firstLettersOtherName.toUpperCase() + patient.getOtherNames().substring(1).toLowerCase();
        clientName = "<font size ='30' color='#FFFFFF'><big><b>" + fullSurname + "</font></b></big> &nbsp &nbsp" + "<font color='#FFFFFF'>" + fullOtherName + "</font>";
        ((TextView) promptsView.findViewById(R.id.name)).setText(Html.fromHtml(clientName));


        save.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onClick(View view) {

                if (!dateDiscontinued1.getText().toString().isEmpty() && !reasonDiscontinued.getSelectedItem().toString().isEmpty()) {
                    DDDDb.getInstance(ClientProfileActivity2.this).devolveRepository().update1(patient.getId(), dateDiscontinued1.getText().toString(), reasonDiscontinued.getSelectedItem().toString());
                    discontinue(dateDiscontinued1.getText().toString(), reasonDiscontinued.getSelectedItem().toString(), patient.getId());
                    dialog.dismiss();
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
                final DatePickerDialog mDatePicker = new DatePickerDialog(ClientProfileActivity2.this, dateNextClinic1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
                mDatePicker.show();
            }
        });
        dialog.setCancelable(false);
        dialog.show();

    }

    private void updateDateOfNxetClinic() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateDiscontinued1.setText(sdf.format(myCalendar.getTime()));

    }


    private void discontinue(String dateDiscontinue, String reasonDiscontinued, Long id) {
        ProgressDialog mPb = new ProgressDialog(ClientProfileActivity2.this);
        mPb.setProgress(0);
        mPb.setMessage("Téléchargement des données, veuillez patienter...");
        mPb.setCancelable(false);
        mPb.setIndeterminate(false);
        mPb.setProgress(0);
        mPb.setMax(100);
        mPb.show();
        @SuppressLint("HardwareIds")

        ClientAPI clientAPI = APIService.createService(ClientAPI.class);
        Call<Response> objectCall = clientAPI.discontinued(dateDiscontinue, reasonDiscontinued, id);
        objectCall.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(@NonNull Call<Response> call, @NonNull retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    mPb.hide();
                    FancyToast.makeText(ClientProfileActivity2.this, "Client retiré du service", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                } else {
                    mPb.hide();
                    FancyToast.makeText(ClientProfileActivity2.this, "Erreur système contacter l'administrateurr", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                }


            }

            @Override
            public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                t.printStackTrace();
                FancyToast.makeText(ClientProfileActivity2.this, "Pas de connexion Internet", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                mPb.hide();
            }

        });


    }
}


