package org.fhi360.ddd.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.shashank.sony.fancytoastlib.FancyToast;

import org.fhi360.ddd.Db.DDDDb;
import org.fhi360.ddd.R;
import org.fhi360.ddd.domain.Facility;
import org.fhi360.ddd.domain.Patient;
import org.fhi360.ddd.domain.Pharmacy;
import org.fhi360.ddd.domain.Regimen;
import org.fhi360.ddd.domain.User;
import org.fhi360.ddd.dto.Data;
import org.fhi360.ddd.dto.PatientDto;
import org.fhi360.ddd.dto.PharmacyDto;
import org.fhi360.ddd.webservice.APIService;
import org.fhi360.ddd.webservice.ClientAPI;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class CreateOutLetAccount extends AppCompatActivity {

    private Button button;
    private EditText pin, username, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ddd_outlet);
        button = findViewById(R.id.register);
        pin = findViewById(R.id.username);
        password = findViewById(R.id.password);
        username = findViewById(R.id.username1);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Account.class);
                startActivity(intent);
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pin1 = pin.getText().toString();
                String password1 = password.getText().toString();
                String userName = username.getText().toString();
                if (validateInput(pin1, userName, password1)) {
                    // User user = DDDDb.getInstance(CreateOutLetAccount.this).userRepository().findByUsernameAndPassword(username1, password1);
//                    if (user != null) {
//                        FancyToast.makeText(getApplicationContext(), "User with these credentials already registered", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
//                    } else {
//                        User user1 = new User();
//                        user1.setPassword(password1);
//                        user1.setUsername(pin1);
                    @SuppressLint("HardwareIds")
                    String deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                    savePharmacy(pin1, userName, password1);
                    getPatient(deviceId, pin1, userName, password1);


                }
            }
        });

    }

    private boolean validateInput(String pin1, String userName1, String password1) {
        if (pin1.isEmpty()) {
            pin.setError("L'activation ne peut pas être vide");
            return false;
        } else if (password1.isEmpty()) {
            password.setError("le mot de passe ne peut pas être vide");
            return false;

        } else if (userName1.isEmpty()) {
            password.setError("Le nom d'utilisateur ne peut pas être vide");
            return false;

        }
        return true;

    }

    public void save(String name) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("name", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putString("name", name);
        editor.apply();
    }


    private void getPatient(String deviceId, String pin, String accountUserName, String accountPassword) {
        ProgressDialog mPb = new ProgressDialog(this);
        mPb.setProgress(0);
        mPb.setMessage("Account activating...");
        mPb.setCancelable(false);
        mPb.setIndeterminate(false);
        mPb.setProgress(0);
        mPb.setMax(100);
        mPb.show();
        @SuppressLint("HardwareIds")
        ClientAPI clientAPI = APIService.createService(ClientAPI.class);
        Call<Data> objectCall = clientAPI.downLoad(deviceId, pin, accountUserName, accountPassword);
        objectCall.enqueue(new Callback<Data>() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onResponse(@NonNull Call<Data> call, @NonNull retrofit2.Response<Data> response) {
                if (response.isSuccessful()) {
                    List<PatientDto> patientDtoList = Objects.requireNonNull(response.body()).getPatients();
                    for (PatientDto patientDto : patientDtoList) {
                        Patient patient = new Patient();
                        patient.setHospitalNum(patientDto.getHospitalNum());
                        patient.setId(patientDto.getId());
                        patient.setFacilityId(patientDto.getFacility().getId());
                        patient.setUniqueId(patientDto.getUniqueId());
                        patient.setSurname(patientDto.getSurname());
                        patient.setOtherNames(patientDto.getOtherNames());
                        patient.setGender(patientDto.getGender());
                        patient.setDateBirth(patientDto.getDateBirth());
                        patient.setAddress(patientDto.getAddress());
                        patient.setPhone(patientDto.getPhone());
                        patient.setDateStarted(patientDto.getDateStarted());
                        patient.setLastClinicStage(patientDto.getLastClinicStage());
                        patient.setLastViralLoad(patientDto.getLastViralLoad());
                        patient.setDateLastViralLoad(patientDto.getDateLastViralLoad());
                        patient.setViralLoadDueDate(patientDto.getViralLoadDueDate());
                        patient.setViralLoadType(patientDto.getViralLoadType());
                        patient.setDateLastClinic(patientDto.getDateLastClinic());
                        patient.setDateNextClinic(patientDto.getDateNextClinic());
                        patient.setDateLastRefill(patientDto.getDateLastRefill());
                        patient.setDateNextRefill(patientDto.getDateNextRefill());
                        patient.setPharmacyId(patientDto.getPharmacyId());
                        Patient patient1 = DDDDb.getInstance(getApplicationContext()).patientRepository().findOne(patientDto.getId());
                        if (patient1 != null) {
                            DDDDb.getInstance(getApplicationContext()).patientRepository().update(patient1);
                        } else {
                            DDDDb.getInstance(getApplicationContext()).patientRepository().save(patient);
                        }

                    }
                    Facility facility2 = DDDDb.getInstance(getApplicationContext()).facilityRepository().findOne(response.body().getFacility().getId());
                    if (facility2 != null) {
                        DDDDb.getInstance(getApplicationContext()).facilityRepository().update(facility2);
                    } else {
                        DDDDb.getInstance(getApplicationContext()).facilityRepository().save(response.body().getFacility());

                    }


                    List<Regimen> regimen = response.body().getRegimens();
                    for (Regimen regimen1 : regimen) {
                        Regimen regimen3 = DDDDb.getInstance(getApplicationContext()).regimenRepository().findByOne(regimen1.getId());
                        if (regimen3 != null) {
                            DDDDb.getInstance(getApplicationContext()).regimenRepository().update(regimen1);
                        } else {
                            DDDDb.getInstance(getApplicationContext()).regimenRepository().save(regimen1);
                        }
                    }

                    FancyToast.makeText(getApplicationContext(), "La réinitialisation du compte a réussi", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                    Intent intent = new Intent(CreateOutLetAccount.this, OutletHome.class);
                    startActivity(intent);
                    mPb.dismiss();

                } else {
                    mPb.hide();
                    FancyToast.makeText(getApplicationContext(), "DDD ne peut pas obtenir les dossiers des patients", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                }


            }

            @Override
            public void onFailure(@NonNull Call<Data> call, @NonNull Throwable t) {
                t.printStackTrace();
                FancyToast.makeText(getApplicationContext(), "Pas de connexion Internet", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                mPb.hide();
            }

        });

    }
//
//    private void savePharmacy(String pin, String userName, String password) {
//        ProgressDialog progressdialog = new ProgressDialog(this);
//        progressdialog.setMessage("Account activating...");
//        progressdialog.setCancelable(false);
//        progressdialog.setIndeterminate(false);
//        progressdialog.setMax(100);
//        progressdialog.show();
//        ClientAPI clientAPI = APIService.createService(ClientAPI.class);
//        @SuppressLint("HardwareIds")
//        String deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
//        Call<Data> objectCall = clientAPI.downLoad("fhi360", pin, deviceId, userName, password);
//        objectCall.enqueue(new Callback<Data>() {
//            @SuppressLint("SimpleDateFormat")
//            @Override
//            public void onResponse(Call<Data> call, retrofit2.Response<Data> response) {
//                if (response.isSuccessful()) {
//                    Data response1 = response.body();
//                    if (Objects.requireNonNull(response1).getAccounts() != null) {
//                        List<PharmacyDto> pharmacyDto = Objects.requireNonNull(response.body()).getAccounts();
//                        for (PharmacyDto account1 : pharmacyDto) {
//                            Pharmacy account = new Pharmacy();
//                            account.setId(account.getId());
//                            account.setAddress(account1.getAddress());
//                            account.setName(account1.getName());
//                            account.setPhone(account1.getPhone());
//                            account.setEmail(account1.getEmail());
//                            account.setType(account1.getType());
//                            account.setPin(account1.getPin());
//                            account.setFacilityId(account1.getFacility().getId());
//                            account.setUsername(account1.getUsername());
//                            account.setDateRegistration(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
//                            User users = DDDDb.getInstance(CreateOutLetAccount.this).userRepository().findByOne();
//                            Pharmacy pharmacy = DDDDb.getInstance(CreateOutLetAccount.this).pharmacistAccountRepository().findbyOne();
//                            if (users == null && pharmacy == null) {
//                                User user1 = new User();
//                                user1.setPassword(password);
//                                user1.setRole("DDD outlet");
//                                user1.setPhone(account1.getPhone());
//                                user1.setUsername(userName);
//                                DDDDb.getInstance(CreateOutLetAccount.this).userRepository().save(user1);
//                                DDDDb.getInstance(getApplicationContext()).pharmacistAccountRepository().save(account);
//                            } else {
//                                User user1 = new User();
//                                user1.setId(users.getId());
//                                user1.setPassword(password);
//                                user1.setRole("DDD outlet");
//                                user1.setPhone(account1.getPhone());
//                                user1.setUsername(userName);
//                                DDDDb.getInstance(CreateOutLetAccount.this).userRepository().save(user1);
//                            }
//                        }
//                        FancyToast.makeText(getApplicationContext(), "Account Reset was successful", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
//                        Intent intent = new Intent(CreateOutLetAccount.this, OutletWelcome.class);
//                        startActivity(intent);
//                        progressdialog.dismiss();
//                    }
//
//                } else {
//                    FancyToast.makeText(getApplicationContext(), "Contact System administrator ", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
//                    progressdialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Data> call, Throwable t) {
//                t.printStackTrace();
//                FancyToast.makeText(getApplicationContext(), "No Internet Connection", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
//                progressdialog.dismiss();
//            }
//
//        });
//
//    }

    private void savePharmacy(String pin, String username, String password) {
        ClientAPI clientAPI = APIService.createService(ClientAPI.class);
        Call<Data> objectCall = clientAPI.activatePharmacy(pin);
        objectCall.enqueue(new Callback<Data>() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onResponse(Call<Data> call, retrofit2.Response<Data> response) {
                if (response.isSuccessful()) {
                    Data response1 = response.body();
                    if (Objects.requireNonNull(response1).getPharmacy() != null) {
                        PharmacyDto account1 = Objects.requireNonNull(response.body()).getPharmacy();
                        Pharmacy account = new Pharmacy();
                        account.setId(account.getId());
                        account.setAddress(account1.getAddress());
                        account.setName(account1.getName());
                        account.setPhone(account1.getPhone());
                        account.setEmail(account1.getEmail());
                        account.setType(account1.getType());
                        account.setPin(account1.getPin());
                        account.setFacilityId(account1.getFacility().getId());
                        account.setUsername(username);
                        account.setDateRegistration(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
                        // User users = DDDDb.getInstance(CreateOutLetAccount.this).userRepository().findByOne();
                        //System.out.println("USER IS "+users);
                        Pharmacy pharmacy = DDDDb.getInstance(CreateOutLetAccount.this).pharmacistAccountRepository().findbyOne();
                        System.out.println("pharmacy IS " + pharmacy);
                        if (pharmacy == null) {
                            User user1 = new User();
                            user1.setUsername(username);
                            user1.setPassword(password);
                            user1.setRole("DDD outlet");
                            user1.setPhone(account1.getPhone());
                            User users = DDDDb.getInstance(CreateOutLetAccount.this).userRepository().findByOne(username);
                            if (users != null) {
                                DDDDb.getInstance(CreateOutLetAccount.this).userRepository().update(user1);
                            } else {
                                DDDDb.getInstance(CreateOutLetAccount.this).userRepository().save(user1);
                            }
                            DDDDb.getInstance(CreateOutLetAccount.this).pharmacistAccountRepository().save(account);
                            //        FancyToast.makeText(getApplicationContext(), "Account Reset was successful", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                        }
//                        FancyToast.makeText(getApplicationContext(), "Account Reset was successful", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
//                        Intent intent = new Intent(CreateOutLetAccount.this, OutletWelcome.class);
//                        startActivity(intent);

                    }

                } else {
                    FancyToast.makeText(getApplicationContext(), "Contacter l'administrateur système", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                t.printStackTrace();
                FancyToast.makeText(getApplicationContext(), "Pas de connexion Internet", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

            }

        });

    }
}
