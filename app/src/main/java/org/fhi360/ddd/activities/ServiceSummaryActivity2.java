package org.fhi360.ddd.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import org.fhi360.ddd.R;
import org.fhi360.ddd.Db.DDDDb;
import org.fhi360.ddd.domain.Patient;
import org.fhi360.ddd.util.DateUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.shashank.sony.fancytoastlib.FancyToast;


public class ServiceSummaryActivity2 extends AppCompatActivity {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private int totalMale1, totalMale2, totalMale3, totalMale4, totalMale5, totalMale6, totalMale7;
    private int totalFemale1, totalFemale2, totalFemale3, totalFemale4, totalFemale5, totalFemale6, totalFemale7;
    private int total1, total2, total3, total4, total5, total6, total7;
    private int month, year;
    int femaleGender;
    int maleGender;

    int femaleGender1;
    int maleGender1;

    int femaleGenderDefualter;
    int maleGender1Defualter;
    int femaleGenderDefualter1;
    int maleGender1Defualter1;
    int countRegimen;
    int femaleRegimen;
    int maleRegiment;
    int femaleRegimen1;
    int maleRegiment1;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facility_rountin_reportes);
        verifyStoragePermissions(this);
        Intent intent = getIntent();
        month = intent.getIntExtra("month", 0);
        year = intent.getIntExtra("year", 0);
        Log.v("ServiceSummary", "Period...." + month + ", " + year);
        if (month != 0 && year != 0) {
            try {
                calculate();
            } catch (Exception e) {
                e.printStackTrace();
            }
            restoreViews();
        }

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReportingPeriod.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.report_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.generateReport) {
            layoutToImage();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void calculate() throws Exception {
        List<Patient> patientList = DDDDb.getInstance(this).patientRepository().findByAll();
        System.out.println("COUNT " + patientList.size());
        for (Patient patient : patientList) {
            String gender = patient.getGender() != null ? patient.getGender() : "";

            if (gender.equalsIgnoreCase("Male")) {
                femaleGender = DDDDb.getInstance(this).patientRepository().genderCount(gender);
            } else {
                maleGender = DDDDb.getInstance(this).patientRepository().genderCount(gender);
            }

            if (gender.equalsIgnoreCase("Male")) {
                femaleGenderDefualter = DDDDb.getInstance(this).patientRepository().getDefaultersCount();
            } else {
                maleGender1Defualter = DDDDb.getInstance(this).patientRepository().getDefaultersCount();
            }
            if (gender.equalsIgnoreCase("Male") && patient.getRegimen() != null) {
                femaleRegimen++;
                System.out.println("REGIMEN " + femaleRegimen);
            } else if (gender.equalsIgnoreCase("Female") && patient.getRegimen() != null) {
                maleRegiment++;
            }

            @SuppressLint("SimpleDateFormat")
            Date dob = new SimpleDateFormat("dd-MM-yyyy").parse(patient.getDateBirth());
            Date ref = DateUtil.getLastDateOfMonth(year, month);
            int age = DateUtil.getAge(String.valueOf(dob));
            //System.out.println("AGE IS " + age1);
            if (gender.equalsIgnoreCase("Male")) {
                if (age < 15) {
                    totalFemale2 = 0;
                    totalMale2++;
                } else {
                    totalFemale2 = 0;
                    totalMale2++;
                }
            }
            if (gender.equalsIgnoreCase("Female")) {
                if (age < 15) {
                    totalFemale2 = 0;
                    totalMale2++;
                } else {
                    totalFemale2 = 0;
                    totalMale2++;
                }
            }

            if (age < 15) {
                totalFemale1 = femaleGender;
                totalMale1 = maleGender;
            }

            if (age > 15) {
                totalFemale1 = femaleGender;
                totalMale1 = maleGender;
                System.out.println("TOTAL FEMALE " + totalFemale1);
            }


            if (age < 15) {
                femaleGenderDefualter1 = femaleGenderDefualter;
                maleGender1Defualter1 = maleGender1Defualter;
            }

            if (age > 15) {
                femaleGenderDefualter1 = femaleGenderDefualter;
                maleGender1Defualter1 = maleGender1Defualter;
                System.out.println("femaleGenderDefualter1 " + femaleGenderDefualter1);
            }

            if (age < 15) {
                femaleRegimen1 = femaleRegimen;
                maleRegiment1 = maleRegiment;
            }

            if (age > 15) {
                femaleRegimen1 = femaleRegimen;
                maleRegiment1 = maleRegiment;
                System.out.println("maleRegiment1 " + maleRegiment1);
            }

        }


    }

    private void restoreViews() {
        String mnth = DateUtil.getMonth(month);
        ((TextView) findViewById(R.id.month)).setText(mnth);
        ((TextView) findViewById(R.id.year)).setText(Integer.toString(year));
        Log.v("ServiceSummary", "Period...." + mnth + ", " + year);
        ((TextView) findViewById(R.id.totalFemale1)).setText(Integer.toString(totalFemale1));
        ((TextView) findViewById(R.id.totalMale1)).setText(Integer.toString(totalMale1));
        int total1 = totalMale1 + totalFemale1;
        ((TextView) findViewById(R.id.total1)).setText(Integer.toString(total1));

        ((TextView) findViewById(R.id.totalMale2)).setText(Integer.toString(totalMale2));
        ((TextView) findViewById(R.id.totalFemale2)).setText(Integer.toString(totalFemale2));

        int total = totalMale2 + totalFemale2;
        ((TextView) findViewById(R.id.total2)).setText(Integer.toString(total));

        ((TextView) findViewById(R.id.totalMale3)).setText(Integer.toString(totalMale2));
        ((TextView) findViewById(R.id.totalFemale3)).setText(Integer.toString(totalFemale2));
        total = totalMale2 + totalFemale2;
        ((TextView) findViewById(R.id.total3)).setText(Integer.toString(total));

        ((TextView) findViewById(R.id.totalMale4)).setText(Integer.toString(maleRegiment1));
        ((TextView) findViewById(R.id.totalFemale4)).setText(Integer.toString(femaleRegimen1));

        total = maleRegiment1 + femaleRegimen1;
        ((TextView) findViewById(R.id.total4)).setText(Integer.toString(total));

        ((TextView) findViewById(R.id.totalMale5)).setText(Integer.toString(femaleGenderDefualter1));
        ((TextView) findViewById(R.id.totalFemale5)).setText(Integer.toString(maleGender1Defualter1));
        total = femaleGenderDefualter1 + maleGender1Defualter1;
        ((TextView) findViewById(R.id.total5)).setText(Integer.toString(total));


    }


    String dirpath;

    public void layoutToImage() {
        // get view group using reference
        ScrollView relativeLayout = findViewById(R.id.activity_step_one);
        // convert view group to bitmap
        relativeLayout.setDrawingCacheEnabled(true);
        relativeLayout.buildDrawingCache();
        Bitmap bm = relativeLayout.getDrawingCache();
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            imageToPDF();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void imageToPDF() {
        try {
            Document document = new Document();
            dirpath = android.os.Environment.getExternalStorageDirectory().toString();
            PdfWriter.getInstance(document, new FileOutputStream(dirpath + "/report.pdf")); //  Change pdf's name.
            document.open();
            Image img = Image.getInstance(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
            float scalar = ((document.getPageSize().getHeight() - document.topMargin() - document.bottomMargin() - 0) / img.getHeight()) * 100;
            img.scalePercent(scalar);
            img.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
            document.add(img);
            document.close();

            FancyToast.makeText(this, "PDF généré avec succès!..", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
            Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "org.fhi360.ddd.fileProvider", new File(dirpath + "/report.pdf"));
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
        } catch (Exception e) {

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


}
