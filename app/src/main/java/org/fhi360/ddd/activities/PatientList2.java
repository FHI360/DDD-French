package org.fhi360.ddd.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import com.mlsdev.animatedrv.AnimatedRecyclerView;

import org.fhi360.ddd.Db.DDDDb;
import org.fhi360.ddd.R;
import org.fhi360.ddd.adapter.PatientRecyclerAdapter2;
import org.fhi360.ddd.domain.Patient;

import java.util.ArrayList;
import java.util.List;

public class PatientList2 extends AppCompatActivity implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {
    private RecyclerView recyclerViewHts;
    private List<Patient> listPatients;
    private PatientRecyclerAdapter2 patientRecyclerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_patient_recycler1);
        recyclerViewHts = (AnimatedRecyclerView) findViewById(R.id.patient_recycler);

        listPatients = new ArrayList<>();
        listPatients.clear();
        listPatients = DDDDb.getInstance(PatientList2.this).patientRepository().findByAll();
        patientRecyclerAdapter = new PatientRecyclerAdapter2(listPatients, PatientList2.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(PatientList2.this);
        recyclerViewHts.setLayoutManager(mLayoutManager);
        recyclerViewHts.setItemAnimator(new DefaultItemAnimator());
        recyclerViewHts.setHasFixedSize(true);
        recyclerViewHts.setAdapter(patientRecyclerAdapter);
        patientRecyclerAdapter.notifyDataSetChanged();
        recyclerViewHts.scheduleLayoutAnimation();


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

        List<Patient> filteredValues = new ArrayList<>(listPatients);
        for (Patient value : listPatients) {
            if (!value.getUniqueId().toLowerCase().contains(newText.toLowerCase())) {
                filteredValues.remove(value);
            }
        }

        List<Patient> filteredValues1 = new ArrayList<>(listPatients);
        for (Patient value : listPatients) {
            if (!value.getUniqueId().toLowerCase().contains(newText.toLowerCase())) {
                filteredValues1.remove(value);
            }
        }

        patientRecyclerAdapter = new PatientRecyclerAdapter2(filteredValues, PatientList2.this);
        recyclerViewHts.setAdapter(patientRecyclerAdapter);

        return false;
    }

    public void resetSearch() {
        patientRecyclerAdapter = new PatientRecyclerAdapter2(listPatients, PatientList2.this);
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


}
