package org.fhi360.ddd.activities;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import org.fhi360.ddd.R;
import org.fhi360.ddd.Db.DDDDb;
import org.fhi360.ddd.adapter.PatientRecyclerAdapter;
import org.fhi360.ddd.domain.Patient;
import java.util.ArrayList;
import java.util.List;

public class PatientListServer extends AppCompatActivity implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {
    private RecyclerView recyclerViewHts;
    private List<Patient> listPatients;
    private PatientRecyclerAdapter patientRecyclerAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_patient_recycler);
        recyclerViewHts = findViewById(R.id.patient_recycler);

        listPatients = new ArrayList<>();
        listPatients.clear();
        listPatients = DDDDb.getInstance(PatientListServer.this).patientRepository().findByAll();
        patientRecyclerAdapter = new PatientRecyclerAdapter(listPatients, PatientListServer.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(PatientListServer.this);
        recyclerViewHts.addItemDecoration(new DividerItemDecoration(PatientListServer.this, LinearLayoutManager.VERTICAL));
        recyclerViewHts.setLayoutManager(mLayoutManager);
        recyclerViewHts.setItemAnimator(new DefaultItemAnimator());
        recyclerViewHts.setHasFixedSize(true);
        recyclerViewHts.setAdapter(patientRecyclerAdapter);
        patientRecyclerAdapter.notifyDataSetChanged();
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

        List<Patient> filteredValues = new ArrayList<Patient>(listPatients);
        for (Patient value : listPatients) {
            if (!value.getUniqueId().toLowerCase().contains(newText.toLowerCase())) {
                filteredValues.remove(value);
            }
        }

        List<Patient> filteredValues1 = new ArrayList<Patient>(listPatients);
        for (Patient value : listPatients) {
            if (!value.getUniqueId().toLowerCase().contains(newText.toLowerCase())) {
                filteredValues1.remove(value);
            }
        }
        patientRecyclerAdapter = new PatientRecyclerAdapter(filteredValues, PatientListServer.this);
        recyclerViewHts.setAdapter(patientRecyclerAdapter);
        return false;
    }

    public void resetSearch() {
        patientRecyclerAdapter = new PatientRecyclerAdapter(listPatients, PatientListServer.this);
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
