package org.fhi360.ddd.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.shashank.sony.fancytoastlib.FancyToast;

import org.fhi360.ddd.Db.DDDDb;
import org.fhi360.ddd.R;
import org.fhi360.ddd.domain.Facility;
import org.fhi360.ddd.domain.Pharmacy;
import org.fhi360.ddd.dto.PharmacyDto;
import org.fhi360.ddd.webservice.APIService;
import org.fhi360.ddd.webservice.ClientAPI;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterOutLet extends AppCompatActivity {

    private Button button;
    private EditText name, email, username, address;
    private EditText phone;
    private Spinner type;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    private ProgressDialog progressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet_register);

        button = findViewById(R.id.register);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        type = findViewById(R.id.type);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FacilityHome.class);
                startActivity(intent);
                finish();
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("IntentReset")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, Contacts.CONTENT_URI);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(intent, 1);
            }
        });
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.outlet_type, R.layout.spinner_text_color);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.hintcolour);
        // Apply the adapter to the spinner
        type.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onClick(View v) {
                String name1 = name.getText().toString();
                String type1 = type.getSelectedItem().toString();
                String phone1 = phone.getText().toString();
                String address1 = address.getText().toString();
                String email1 = email.getText().toString();
                if (validateInput(name1, type1, phone1, address1)) {
                    Pharmacy user = DDDDb.getInstance(RegisterOutLet.this).pharmacistAccountRepository().findByPhoneAndEmail(phone1, email1);
                    if (user != null) {
                        FancyToast.makeText(getApplicationContext(), "Outlet avec ces identifiants déjà enregistrés", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    } else {
                        Long facilityId = DDDDb.getInstance(getApplicationContext()).facilityRepository().getFacility().getId();
                        PharmacyDto pharmacyDto = new PharmacyDto();
                        pharmacyDto.setAddress(address1);
                        pharmacyDto.setName(name1);
                        pharmacyDto.setPhone(phone1);
                        pharmacyDto.setEmail(email1);
                        pharmacyDto.setType(type1);
                        Facility facility = new Facility();
                        facility.setId(facilityId);
                        pharmacyDto.setFacility(facility);
                        pharmacyDto.setUsername(username.getText().toString());
                        save(pharmacyDto);

                    }
                }
            }
        });

    }

    private boolean validateInput(String name1, String type1, String phone1, String address1) {
        if (name1.isEmpty()) {
            name.setError("le nom ne peut pas être vide");
            return false;
        } else if (type1.isEmpty()) {
            TextView errorText = (TextView) type.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            errorText.setText("Sélectionnez une valeur");
            return false;
        } else if (phone1.isEmpty()) {
            phone.setError("Le téléphone ne peut pas être vide");
            return false;
        } else if (address1.isEmpty()) {
            address.setError("L'adresse ne peut pas être vide");
            return false;
        }
        return true;
    }

    protected void sendSMSMessage() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phone.getText().toString(), null, "Code d'activation DDD" + 111, null, null);
                    Toast.makeText(getApplicationContext(), "SMS envoyé.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS échoué, veuillez réessayer.", Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    @SuppressLint("Recycle")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 1) && (resultCode == RESULT_OK)) {
            Cursor cursor = null;
            try {
                Uri uri = data.getData();
                assert uri != null;
                cursor = getContentResolver().query(uri, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
                if (cursor != null && cursor.moveToNext()) {
                    String phone1 = cursor.getString(0);
                    phone.setText(phone1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void save(PharmacyDto account) {
        progressdialog = new ProgressDialog(RegisterOutLet.this);
        progressdialog.setMessage("DDD outlet saving...");
        progressdialog.setCancelable(false);
        progressdialog.setIndeterminate(false);
        progressdialog.setMax(100);
        progressdialog.show();
        ClientAPI clientAPI = APIService.createService(ClientAPI.class);
        Call<org.fhi360.ddd.dto.Response> objectCall = clientAPI.saveAccount(account);
        objectCall.enqueue(new Callback<org.fhi360.ddd.dto.Response>() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onResponse(Call<org.fhi360.ddd.dto.Response> call, Response<org.fhi360.ddd.dto.Response> response) {
                if (response.isSuccessful()) {
                    org.fhi360.ddd.dto.Response response1 = response.body();
                    if (Objects.requireNonNull(response1).getMessage() != null) {
                        FancyToast.makeText(getApplicationContext(), response1.getMessage(), FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                        progressdialog.dismiss();
                    } else if (Objects.requireNonNull(response1).getPharmacy() != null) {
                        PharmacyDto account1 = Objects.requireNonNull(response.body()).getPharmacy();
                        System.out.println("ACCOUNT " + account1);
                        Pharmacy account = new Pharmacy();
                        account.setId(account.getId());
                        account.setAddress(account1.getAddress());
                        account.setName(account1.getName());
                        account.setPhone(account1.getPhone());
                        account.setEmail(account1.getEmail());
                        account.setType(account1.getType());
                        account.setPin(account1.getPin());
                        account.setFacilityId(account1.getFacility().getId());
                        account.setUsername(account1.getUsername());
                        account.setDateRegistration(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
                        DDDDb.getInstance(RegisterOutLet.this).pharmacistAccountRepository().save(account);
                        FancyToast.makeText(getApplicationContext(), "la notification a été envoyée par e-mail, client gentiment attribué", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                        address.setText("");
                        name.setText("");
                        phone.setText("");
                        email.setText("");
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
}
