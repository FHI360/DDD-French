package org.fhi360.ddd.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.shashank.sony.fancytoastlib.FancyToast;
import org.fhi360.ddd.Db.DDDDb;
import org.fhi360.ddd.R;
import org.fhi360.ddd.adapter.PatientAdpter;
import org.fhi360.ddd.adapter.PatientRecyclerAdapter5;
import org.fhi360.ddd.domain.Patient;
import org.fhi360.ddd.dto.Data;
import org.fhi360.ddd.dto.PatientDto;
import org.fhi360.ddd.webservice.APIService;
import org.fhi360.ddd.webservice.ClientAPI;
import retrofit2.Call;
import retrofit2.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FacilityPatientList3 extends AppCompatActivity implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {
    private RecyclerView recyclerViewHts;
    private List<PatientDto> listPatients;
    private PatientAdpter3 patientRecyclerAdapter;
    private ProgressDialog mPb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_patient_recycler1);
        recyclerViewHts = findViewById(R.id.patient_recycler);

        getPatient();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText == null || newText.trim().isEmpty()) {
            resetSearch();
            return false;
        }

        List<PatientDto> filteredValues = new ArrayList<>(listPatients);
        for (PatientDto value : listPatients) {
            if (!value.getUniqueId().toLowerCase().contains(newText.toLowerCase())) {
                filteredValues.remove(value);
            }
        }

        List<PatientDto> filteredValues1 = new ArrayList<>(listPatients);
        for (PatientDto value : listPatients) {
            if (!value.getUniqueId().toLowerCase().contains(newText.toLowerCase())) {
                filteredValues1.remove(value);
            }
        }

        patientRecyclerAdapter = new PatientAdpter3(filteredValues, FacilityPatientList3.this);
        recyclerViewHts.setAdapter(patientRecyclerAdapter);

        return false;
    }

    public void resetSearch() {
        patientRecyclerAdapter = new PatientAdpter3(listPatients, FacilityPatientList3.this);
        recyclerViewHts.setAdapter(patientRecyclerAdapter);

    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return false;
    }


    private void getPatient() {
        mPb = new ProgressDialog(FacilityPatientList3.this);
        mPb.setProgress(0);
        mPb.setMessage("Téléchargement des données, veuillez patienter...");
        mPb.setCancelable(false);
        mPb.setIndeterminate(false);
        mPb.setProgress(0);
        mPb.setMax(100);
        mPb.show();
        @SuppressLint("HardwareIds")

        ClientAPI clientAPI = APIService.createService(ClientAPI.class);
        Call<Data> objectCall = clientAPI.getAllPatients();//downLoad(deviceId, pin, accountUserName, accountPassword);
        objectCall.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(@NonNull Call<Data> call, @NonNull retrofit2.Response<Data> response) {
                if (response.isSuccessful()) {
                    listPatients = Objects.requireNonNull(response.body()).getPatients();
                    patientRecyclerAdapter = new PatientAdpter3(listPatients, FacilityPatientList3.this);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(FacilityPatientList3.this);
                    recyclerViewHts.setLayoutManager(mLayoutManager);
                    recyclerViewHts.setItemAnimator(new DefaultItemAnimator());
                    recyclerViewHts.setHasFixedSize(true);
                    recyclerViewHts.setAdapter(patientRecyclerAdapter);
                    patientRecyclerAdapter.notifyDataSetChanged();
                    recyclerViewHts.scheduleLayoutAnimation();
                    mPb.hide();
                } else {

                    FancyToast.makeText(FacilityPatientList3.this, "DDD ne peut pas obtenir les dossiers des patients", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    mPb.hide();
                }


            }

            @Override
            public void onFailure(@NonNull Call<Data> call, @NonNull Throwable t) {
                t.printStackTrace();
                FancyToast.makeText(FacilityPatientList3.this, "Pas de connexion Internet", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                mPb.hide();
            }

        });

    }


}
