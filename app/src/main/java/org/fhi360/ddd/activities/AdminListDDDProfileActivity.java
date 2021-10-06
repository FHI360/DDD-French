
package org.fhi360.ddd.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import org.fhi360.ddd.Db.DDDDb;

import com.google.gson.Gson;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.fhi360.ddd.R;
import org.fhi360.ddd.domain.Pharmacy;

import org.fhi360.ddd.util.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Objects;

import static org.fhi360.ddd.util.Constants.PREFERENCES_ENCOUNTER;

public class AdminListDDDProfileActivity extends AppCompatActivity {
    private Pharmacy account;
    private boolean INITIAL = true;
    private SharedPreferences preferences;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ddd_outlet_profile);
        verifyStoragePermissions(this);
        this.preferences = getSharedPreferences(PREFERENCES_ENCOUNTER, 0);
        restorePreferences();
        if (savedInstanceState != null) {
            String json = savedInstanceState.getString("account");
            account = new Gson().fromJson(json, Pharmacy.class);
        }
        String name = account.getName();
        ((TextView) findViewById(R.id.name)).setText(name);

        ((TextView) findViewById(R.id.email)).setText(account.getEmail());
        ((TextView) findViewById(R.id.address)).setText(account.getAddress());
        ((TextView) findViewById(R.id.phone)).setText(account.getPhone());
        ((TextView) findViewById(R.id.dateRegistrations)).setText(account.getDateRegistration());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("DDD Profil");
    }


    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        saveInstanceState.putString("account", new Gson().toJson(account));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String json = savedInstanceState.getString("account");
        account = new Gson().fromJson(json, Pharmacy.class);
    }

    private void restorePreferences() {
        String json = preferences.getString("account", "");
        account = new Gson().fromJson(json, Pharmacy.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                showAlertDelete();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAlertDelete() {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.forget_pop_up, null);
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setView(promptsView);
        final TextView notitopOk, notitopNotnow;
        final TextView notitoptxt;
        notitopOk = promptsView.findViewById(R.id.notitopOk);
        notitopNotnow = promptsView.findViewById(R.id.notitopNotnow);
        notitoptxt = promptsView.findViewById(R.id.notitoptxt);
        notitoptxt.setVisibility(View.VISIBLE);
        notitoptxt.setText("Êtes-vous sûr de vouloir désactiver cet Oulet ?");
        notitopNotnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        notitopOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DDDDb.getInstance(getApplicationContext()).pharmacistAccountRepository().delete(account);
                FancyToast.makeText(getApplicationContext(), "Enregistrement désactivé avec succès", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            }
        });

        dialog.setCancelable(false);
        dialog.show();
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
            Cursor curCSV = DDDDb.getInstance(AdminListDDDProfileActivity.this).patientRepository().getAllPatient();
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
            // Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
        }
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

}
