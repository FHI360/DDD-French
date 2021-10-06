package org.fhi360.ddd.adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.mlsdev.animatedrv.AnimatedRecyclerView;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.fhi360.ddd.Db.DDDDb;
import org.fhi360.ddd.R;
import org.fhi360.ddd.domain.CardItem;
import org.fhi360.ddd.domain.Patient;
import org.fhi360.ddd.domain.RegimenHistory;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class InventoryPagerAdapterReport extends PagerAdapter implements CardAdapter {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private PopupWindow mPopupMenuBg;
    private List<CardView> mViews;
    private List<CardItem> mData;
    private float mBaseElevation;
    List<RegimenHistory> arvList;
    private Context context;
    private EditText otp_view;
    private ProgressDialog progressDialog;
    String serverUrl = null;
    EditText mSearchField;
    AutoCompleteTextView mSearchField1;
    ImageView mSearchBtn;
    RecyclerView recyclerViewHts;
    List<Patient> listPatients;
    PatientRecyclerAdapter3 patientRecyclerAdapter;
    PatientRecyclerAdapter4 patientRecyclerAdapter1;
    private Calendar myCalendar = Calendar.getInstance();
    private Patient patient;
    private SharedPreferences preferences;

    public InventoryPagerAdapterReport() {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public void addCardItem(Context context, CardItem item) {
        this.context = context;
        mViews.add(null);
        mData.add(item);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @SuppressLint("SetTextI18n")
    @NotNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.adapter, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);
        ImageView imageView = view.findViewById(R.id.imageView);

        Button assigined = view.findViewById(R.id.assigined);
        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        if (position == 0) {
            imageView.setImageResource(R.drawable.refillhistoryicon);
            assigined.setBackgroundResource(R.drawable.background_button_accent);
            assigined.setText(R.string.clickmore);
            assigined.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAlertPatientHistory();
                }
            });
        }
        if (position == 1) {
            assigined.setText(R.string.clickmore);
            imageView.setImageResource(R.drawable.inventorymgticon);
            assigined.setBackgroundResource(R.drawable.background_button_accent1);
            assigined.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAlertDailyRegister();
                }
            });
        }


        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }


    public HashMap<String, String> get() {
        HashMap<String, String> name = new HashMap<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("name", Context.MODE_PRIVATE);
        name.put("name", sharedPreferences.getString("name", null));
        return name;
    }


    private void bind(CardItem item, View view) {
        TextView titleTextView = view.findViewById(R.id.titleTextView);
        TextView contentTextView = view.findViewById(R.id.contentTextView);
        titleTextView.setText(item.getTitle());
        contentTextView.setText(item.getText());
    }

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    public void showAlertPatientHistory() {
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.fragment_patient_recycler2, null);
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setView(promptsView);
        final TextView cancel_action = promptsView.findViewById(R.id.cancel_action);
        cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mSearchField1 = (AutoCompleteTextView) promptsView.findViewById(R.id.search_field);
        mSearchBtn = (ImageView) promptsView.findViewById(R.id.search_btn);

        recyclerViewHts = (AnimatedRecyclerView) promptsView.findViewById(R.id.patient_recycler);
        listPatients = new ArrayList<>();
        listPatients.clear();
        listPatients = DDDDb.getInstance(context).patientRepository().findByAll1();
        patientRecyclerAdapter = new PatientRecyclerAdapter3(listPatients, context);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerViewHts.setLayoutManager(mLayoutManager);
        recyclerViewHts.setItemAnimator(new DefaultItemAnimator());
        recyclerViewHts.setHasFixedSize(true);
        recyclerViewHts.setAdapter(patientRecyclerAdapter);
        patientRecyclerAdapter.notifyDataSetChanged();
        recyclerViewHts.scheduleLayoutAnimation();
        dialog.setCancelable(false);
        dialog.show();

        ArrayList names = new ArrayList();
        for (Patient patient : listPatients) {
            names.add(patient.getSurname());
        }

        final ArrayAdapter districtAdapter = new ArrayAdapter<>(context,
                R.layout.spinner_items, names);
        mSearchField1.setThreshold(2);
        mSearchField1.setAdapter(districtAdapter);

        mSearchField1.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0) {
                    search(mSearchField1.getText().toString());
                } else {
                    search(mSearchField1.getText().toString());
                }

            }
        });
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = mSearchField.getText().toString();

                search(searchText);

            }
        });
    }

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    public void showAlertDailyRegister() {
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.list_view, null);
        final AlertDialog dialog = new AlertDialog.Builder(context).create();

        Button generateReport = promptsView.findViewById(R.id.heading_label);
        mPopupMenuBg = new PopupWindow(promptsView, LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT, false);
        dialog.setView(promptsView);
        final TextView cancel_action = promptsView.findViewById(R.id.cancel_action);
        generateReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get view group using reference
                RelativeLayout relativeLayout = promptsView.findViewById(R.id.id);
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
        });


        cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        mSearchField = (EditText) promptsView.findViewById(R.id.search_field);
        mSearchBtn = (ImageView) promptsView.findViewById(R.id.search_btn);

        final DatePickerDialog.OnDateSetListener dateVisit2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateOfDateVisit();
            }

        };

        mSearchField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePickerDialog mDatePicker = new DatePickerDialog(context, dateVisit2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show();
            }
        });

        recyclerViewHts = (AnimatedRecyclerView) promptsView.findViewById(R.id.patient_recycler);
        arvList = new ArrayList<>();
        arvList.clear();
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = mSearchField.getText().toString();
                arvList = DDDDb.getInstance(context).regimenHistoryRepository().findByDateVisit(searchText);
                TableViewAdapter adapter = new TableViewAdapter(context, arvList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                recyclerViewHts.setLayoutManager(mLayoutManager);
                recyclerViewHts.setItemAnimator(new DefaultItemAnimator());
                recyclerViewHts.setHasFixedSize(true);
                recyclerViewHts.setAdapter(adapter);
            }
        });


        dialog.setCancelable(false);
        dialog.show();
    }

    private void search(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            resetSearch();
        }

        List<Patient> filteredValues = new ArrayList<>(listPatients);
        for (Patient value : listPatients) {
            assert searchText != null;
            if (!value.getSurname().toLowerCase().contains(searchText.toLowerCase())) {
                filteredValues.remove(value);
            }
        }

        List<Patient> filteredValues1 = new ArrayList<>(listPatients);
        for (Patient value : listPatients) {
            assert searchText != null;
            if (!value.getSurname().toLowerCase().contains(searchText.toLowerCase())) {
                filteredValues1.remove(value);
            }
        }
        patientRecyclerAdapter = new PatientRecyclerAdapter3(filteredValues, context);
        recyclerViewHts.setAdapter(patientRecyclerAdapter);
    }

    public void resetSearch() {
        patientRecyclerAdapter = new PatientRecyclerAdapter3(listPatients, context);
        recyclerViewHts.setAdapter(patientRecyclerAdapter);

    }

    private void updateDateOfDateVisit() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        mSearchField.setText(sdf.format(myCalendar.getTime()));

    }

    String dirpath;

    public void imageToPDF() {
        try {
            Document document = new Document();
            dirpath = Environment.getExternalStorageDirectory().toString();
            PdfWriter.getInstance(document, new FileOutputStream(dirpath + "/report.pdf")); //  Change pdf's name.
            document.open();
            Image img = Image.getInstance(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
            float scalar = ((document.getPageSize().getHeight() - document.topMargin() - document.bottomMargin() - 0) / img.getHeight()) * 100;
            img.scalePercent(scalar);
            img.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
            document.add(img);
            document.close();

            FancyToast.makeText(context, "PDF Generated successfully!..", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
            Uri contentUri = FileProvider.getUriForFile(context, "org.fhi360.ddd.fileProvider", new File(dirpath + "/report.pdf"));
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.setType("plain/text");
            intent.putExtra(Intent.EXTRA_STREAM, contentUri);
            intent.putExtra(Intent.EXTRA_SUBJECT, "Patient Data");
            Intent chooser = Intent.createChooser(intent, "Share File");
            List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(chooser, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                context.grantUriPermission(packageName, contentUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            context.startActivity(chooser);
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

}