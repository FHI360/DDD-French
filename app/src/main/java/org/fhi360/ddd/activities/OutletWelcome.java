package org.fhi360.ddd.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.fhi360.ddd.Db.DDDDb;
import org.fhi360.ddd.R;
import org.fhi360.ddd.domain.Pharmacy;

import java.util.HashMap;

public class OutletWelcome extends AppCompatActivity {
    TextView pharmacy, next;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outletwelcome);
        pharmacy = findViewById(R.id.pharmacy);
        next = findViewById(R.id.next);
        Pharmacy user = DDDDb.getInstance(getApplicationContext()).pharmacistAccountRepository().findbyOne();
        String firstLettersurname = String.valueOf(user.getName().charAt(0));
        String fullSurname = firstLettersurname.toUpperCase() + user.getName().substring(1).toLowerCase();
        pharmacy.setText(fullSurname);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OutletWelcome.this, OutletHome.class);
                startActivity(intent);
            }
        });

    }


    public HashMap<String, String> get() {
        HashMap<String, String> name = new HashMap<>();
        SharedPreferences sharedPreferences = this.getSharedPreferences("name", Context.MODE_PRIVATE);
        name.put("name", sharedPreferences.getString("name", null));
        return name;

    }
}

