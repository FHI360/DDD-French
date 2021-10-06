package org.fhi360.ddd.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import org.fhi360.ddd.R;

import com.shashank.sony.fancytoastlib.FancyToast;

import org.fhi360.ddd.Db.DDDDb;
import org.fhi360.ddd.domain.User;

import java.util.Date;

public class CreateFacilityAccount extends AppCompatActivity {

    private Button button;
    private EditText username, password;
    //district, province, contactDetail;
    private Spinner role;
    private EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility_account);
        button = findViewById(R.id.register);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phoneNumber);
        phone.setHint(R.string.phone_hint);
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
                String username1 = username.getText().toString();
                String password1 = password.getText().toString();
                //String role;
                String phone1 = phone.getText().toString();
                if (validateInput(username1, password1, phone1)) {
                    User user = DDDDb.getInstance(CreateFacilityAccount.this).userRepository().findByUsernameAndPassword(username1, password1);
                    if (user != null) {
                        FancyToast.makeText(getApplicationContext(), "Utilisateur avec ces identifiants déjà enregistré", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    } else {
                        User user1 = new User();
                        user1.setPassword(password1);
                        user1.setUsername(username1);
                        // user1.setName(name1);
                        user1.setRole("Facility");
                        user1.setPhone(phone1);
//                        user1.setDistrict(district1);
//                        user1.setProvince(province1);
//                        user1.setContactDetail(contactDetail1);
                        user1.setDate(new Date());
                        // save(name1);
                        DDDDb.getInstance(CreateFacilityAccount.this).userRepository().save(user1);
                        Intent intent = new Intent(CreateFacilityAccount.this, FacilityActivation.class);
                        startActivity(intent);
                    }
                }
            }
        });

    }

    private boolean validateInput(String username1, String password1, String phone1) {
        if (username1.isEmpty()) {
            username.setError("le nom d'utilisateur ne peut pas être vide");
            return false;
        } else if (password1.isEmpty()) {
            password.setError("le mot de passe ne peut pas être vide");
            return false;

        } else if (phone1.isEmpty()) {
            phone.setError("Le téléphone ne peut pas être vide");
            return false;
        }
//        } else if (district1.isEmpty()) {
//            district.setError("District can not be empty");
//            return false;
//        } else if (province1.isEmpty()) {
//            province.setError("Province can not be empty");
//            return false;
//        } else if (contactDetail1.isEmpty()) {
//            contactDetail.setError("Contact can not be empty");
//            return false;
//        }
        return true;


    }

    public void save(String name) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("name", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putString("name", name);
        editor.apply();
    }
}
