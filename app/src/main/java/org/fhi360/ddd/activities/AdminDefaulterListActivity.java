package org.fhi360.ddd.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.fhi360.ddd.Db.DDDDb;
import org.fhi360.ddd.R;
import org.fhi360.ddd.adapter.DefaulterListAdapter;
import org.fhi360.ddd.domain.ARV;
import org.fhi360.ddd.domain.Patient;

import java.util.List;
import java.util.Objects;

import static org.fhi360.ddd.util.Constants.PREFERENCES_ENCOUNTER;


public class AdminDefaulterListActivity extends AppCompatActivity {
    private int patientId;
    private int facilityId;
    private Cursor cursor;
    private SQLiteDatabase db;
    private DefaulterListAdapter adapter;
    private ListView defaulterListView;
    private List<ARV> patients;
    private SharedPreferences preferences;
    private String SEARCH_OPENED = "search_opened";
    private boolean searchMenuOpened;  //keeps track if the search bar is opened
    private String SEARCH_QUERY = "search_query";
    private String searchQuery;    //holds current text in the search bar
    private Drawable iconCloseSearch;       //icon that shows when the search bar is closed (magnifier)
    private Drawable iconOpenSearch;        //icon that shows when the search bar is opened (x sign)
    private EditText searchEditText;        //search bar text field
    private MenuItem searchAction;          //search bar action button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defaulter_list);

        //Getting the icons
        iconOpenSearch = getResources().getDrawable(R.drawable.ic_search);
        iconCloseSearch = getResources().getDrawable(R.drawable.ic_clear);

        if (savedInstanceState != null) {
            searchMenuOpened = savedInstanceState.getBoolean(SEARCH_OPENED);
            searchQuery = savedInstanceState.getString(SEARCH_QUERY);
        } else {
            searchMenuOpened = false;
            searchQuery = "";
        }

        //If the search bar was opened previously, open it on recreate
        if (searchMenuOpened) {
            openSearchBar(searchQuery);
        }

        defaulterListView = findViewById(R.id.defaulterlistView);
        patients = DDDDb.getInstance(this).arvRefillRepository().getDefaulter();

        adapter = new DefaulterListAdapter(this, patients);
        defaulterListView.setAdapter(adapter);
        defaulterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Patient patient = DDDDb.getInstance(getApplicationContext()).patientRepository().findByPatient(patients.get(position).getId());
                savePreferences(patient);
                Intent intent = new Intent(getApplicationContext(), ClientTrackingActivity.class);
                startActivity(intent);
            }
        });

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Defaulters");
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        saveInstanceState.putBoolean(SEARCH_OPENED, searchMenuOpened);
        saveInstanceState.putString(SEARCH_QUERY, searchQuery);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle other ActionBar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            //When the search button is clicked we have to either open or close the input field
            case R.id.action_search:
                if (searchMenuOpened) {
                    closeSearchBar();
                } else {
                    openSearchBar(searchQuery);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //Extract the search menu item, we want to save in the bundle
        searchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    //This method sets a custom edit text view on the action bar
    //A textChangedListener (implemented by a TextWatcher) is set on the edit text view, the content of the text view is used to filter the the patient list
    private void openSearchBar(String queryText) {
        //Get an action bar and the set the custom view (search_bar layout) on action bar
        //The search bar layout contains an editable text view
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.search_bar);

        //Get the editable text view from the search layout and set a TextChangedListener to listen to text changed
        searchEditText = (EditText) getSupportActionBar().getCustomView().findViewById(R.id.editSearch);
        searchEditText.addTextChangedListener(filterTextWatcher);
        searchEditText.setText(queryText);
        searchEditText.requestFocus();

        //Change search icon accordingly
        searchAction.setIcon(iconCloseSearch);
        searchMenuOpened = true;
    }

    private void closeSearchBar() {
        //Remove custom view
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowCustomEnabled(false);

        //Change search icon accordingly
        searchAction.setIcon(iconOpenSearch);
        searchMenuOpened = false;
    }

    //The TextChangedListener reads the text entered in the editable view and pass the content to the refreshView method of the HomeFragment
    private TextWatcher filterTextWatcher = new TextWatcher() {
        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //  refreshListView(s);
        }
    };

//    public void refreshListView(CharSequence s) {
//        if (!s.toString().equals("")) {
//            long period = 28 * 24 * 60 * 60 * 1000;
//            patients = DDDDb.getInstance(this).patientRepository().getDefaulters(Long.toString(period),s.toString());
//        }
//        else {
//            patients =DDDDb.getInstance(this).patientRepository().getDefaulters();
//        }
//        adapter = new DefaulterListAdapter(this, patients);
//        defaulterListView.setAdapter(adapter);
//    }

    private void savePreferences(Patient patient) {
        preferences = getSharedPreferences(PREFERENCES_ENCOUNTER, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("patient", new Gson().toJson(patient));
        editor.putBoolean("edit_mode", false);
        editor.commit();

    }

}
