package org.fhi360.ddd.activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.shashank.sony.fancytoastlib.FancyToast;

import org.fhi360.ddd.Db.DDDDb;
import org.fhi360.ddd.R;
import org.fhi360.ddd.domain.User;
import org.fhi360.ddd.webservice.APIService;
import org.fhi360.ddd.webservice.ClientAPI;

import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {
    private EditText username1;
    private EditText password1;
    private TextView createUser, text_forget_password;
    private Button login;
    private ProgressDialog progressdialog;
    private String deviceconfigId;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @SuppressLint({"HardwareIds", "ObsoleteSdkInt"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.login);
        disableAutofill();
        deviceconfigId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        username1 = findViewById(R.id.username1);
        password1 = findViewById(R.id.password1);
        createUser = findViewById(R.id.text_create_account);
        login = findViewById(R.id.sign_in_button);
        text_forget_password = findViewById(R.id.text_forget_password1);

        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create();
            }
        });
        createUser.setMovementMethod(LinkMovementMethod.getInstance());
        text_forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert(deviceconfigId);
            }
        });
//        User id = DDDDb.getInstance(this).userRepository().findByOne();
//        if (id == null) {
//            createUser.setVisibility(View.VISIBLE);
//        } else {
//            createUser.setVisibility(View.VISIBLE);
//        }
        //else if (user != null && user.getRole().equalsIgnoreCase("admin")) {
        ////                        Intent intent = new Intent(LoginActivity.this, AdminActivation.class);
        ////                        System.out.println("USERNAME "+user.getUsername());
        ////                        save(user.getName());
        ////                        startActivity(intent);
        //
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput(username1.getText().toString(), password1.getText().toString())) {
                    User user = DDDDb.getInstance(LoginActivity.this).userRepository().findByUsernameAndPassword(username1.getText().toString(), password1.getText().toString());
                    if (user != null && user.getRole().equalsIgnoreCase("DDD outlet")) {
                        Intent intent = new Intent(LoginActivity.this, OutletHome.class);
                        save(user.getName());
                        startActivity(intent);
                    } else if (user != null && user.getRole().equalsIgnoreCase("Facility")) {
                        Intent intent = new Intent(LoginActivity.this, FacilityActivation.class);
                        save(user.getName());
                        startActivity(intent);
                    } else if (user != null && user.getRole().equalsIgnoreCase("admin")) {
                        Intent intent = new Intent(LoginActivity.this, AdminActivation.class);
                        save(user.getUsername());
                        startActivity(intent);
                          } else {
//                        Intent intent = new Intent(LoginActivity.this, AdminActivation.class);
//                        System.out.println("USERNAME "+user.getUsername());
//                        save(user.getName());
//                        startActivity(intent);
                        login(username1.getText().toString(), password1.getText().toString());

                    }
                }

            }
        });
    }

    private void create() {
        Intent intent = new Intent(this, Account.class);
        startActivity(intent);
    }


    private boolean validateInput(String username, String password) {
        if (username.isEmpty()) {
            username1.setError("le nom d'utilisateur ne peut pas être vide");
            return false;
        } else if (password.isEmpty()) {
            password1.setError("le mot de passe ne peut pas être vide");
            return false;
        }
        return true;


    }

    private void showAlert(final String deviceconfigId) {
        LayoutInflater li = LayoutInflater.from(LoginActivity.this);
        View promptsView = li.inflate(R.layout.forget_pop_up, null);
        final AlertDialog dialog = new AlertDialog.Builder(LoginActivity.this).create();
        dialog.setView(promptsView);
        final TextView notitopOk, notitopNotnow;
        //  final EditText notitoptxt;
        notitopOk = promptsView.findViewById(R.id.notitopOk);
        notitopNotnow = promptsView.findViewById(R.id.notitopNotnow);
        //  notitoptxt = promptsView.findViewById(R.id.notitoptxt);
        notitopNotnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        notitopOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User usernameAndPassword = DDDDb.getInstance(LoginActivity.this).userRepository().findByOne();
                if (usernameAndPassword != null) {
                    username1.setText(usernameAndPassword.getUsername());
                    password1.setText(usernameAndPassword.getPassword());
                    dialog.dismiss();
                }

            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }


    public void saveRole(String role) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("usernameDB", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putString("role", role);
        editor.apply();
    }

    public HashMap<String, String> getRole() {
        HashMap<String, String> name = new HashMap<>();
        SharedPreferences sharedPreferences = this.getSharedPreferences("usernameDB", Context.MODE_PRIVATE);
        name.put("role", sharedPreferences.getString("role", null));
        return name;
    }


    public void save(String name) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("name", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putString("name", name);
        editor.apply();
    }

    public HashMap<String, String> get() {
        HashMap<String, String> name = new HashMap<>();
        SharedPreferences sharedPreferences = this.getSharedPreferences("name", Context.MODE_PRIVATE);
        name.put("name", sharedPreferences.getString("name", null));
        return name;
    }


    private void login(String username, String password) {
        ProgressDialog progressdialog = new ProgressDialog(this);
        progressdialog.setMessage("Saving patient...");
        progressdialog.setCancelable(false);
        progressdialog.setIndeterminate(false);
        progressdialog.setMax(100);
        progressdialog.show();
        ClientAPI clientAPI = APIService.createService(ClientAPI.class);
        Call<org.fhi360.ddd.dto.Response> objectCall = clientAPI.login(username, password);
        objectCall.enqueue(new Callback<org.fhi360.ddd.dto.Response>() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onResponse(Call<org.fhi360.ddd.dto.Response> call, retrofit2.Response<org.fhi360.ddd.dto.Response> response) {
                if (response.isSuccessful()) {
                    User user = new User();
                    user.setUsername(Objects.requireNonNull(response.body()).getUser().getUsername());
                    user.setPassword(response.body().getUser().getPassword());
                    user.setRole(response.body().getUser().getRole());
                    DDDDb.getInstance(getApplicationContext()).userRepository().save(user);
                    save(user.getUsername());
                    Intent intent = new Intent(LoginActivity.this, AdminActivation.class);
                    startActivity(intent);
                    progressdialog.dismiss();
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

    @TargetApi(Build.VERSION_CODES.O)
    private void disableAutofill() {
        getWindow().getDecorView().setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS);
    }
}
