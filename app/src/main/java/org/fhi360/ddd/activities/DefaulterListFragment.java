package org.fhi360.ddd.activities;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import org.fhi360.ddd.R;
import org.fhi360.ddd.Db.DDDDb;
import org.fhi360.ddd.adapter.DefaulterListAdapter;
import org.fhi360.ddd.domain.ARV;
import org.fhi360.ddd.domain.Patient;
import java.util.List;
import static org.fhi360.ddd.util.Constants.PREFERENCES_ENCOUNTER;
public class DefaulterListFragment  extends AppCompatActivity {
    private Cursor cursor;
    private SQLiteDatabase db;
    private DefaulterListAdapter adapter;
    private ListView defaulterListView;
    private List<ARV> patients;
    private Context context;
    private int patientId;
    private int facilityId;
    private SharedPreferences preferences;
    public DefaulterListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_defaulter_list);

        defaulterListView =  findViewById(R.id.defaulterlistView);
        patients = DDDDb.getInstance(this).arvRefillRepository().getDefaulter();
        adapter = new DefaulterListAdapter((Activity) context, patients);
        defaulterListView.setAdapter(adapter);
        defaulterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Patient patient = DDDDb.getInstance(getApplicationContext()).patientRepository().findByPatient(patients.get(position).getId());
                savePreferences(patient);
                Intent intent = new Intent(context, ClientTrackingActivity.class);
                startActivity(intent);
            }
        });

    }

//    //This method is called by the onTextChanged method of the TextWatcher in MainActivity
//    public void refreshListView(CharSequence s) {
//        if (!s.toString().equals("")) {
//           // long today = System.currentTimeMillis();
//            long period = 28 * 24 * 60 * 60 * 1000;
//            patients = DDDDb.getInstance(this).patientRepository().getDefaulters(Long.toString(period),s.toString());
//        }
//        else {
//            patients = DDDDb.getInstance(this).patientRepository().getDefaulters();
//        }
//        adapter = new DefaulterListAdapter(this, patients);
//        defaulterListView.setAdapter(adapter);
//    }

    private void savePreferences(Patient patient) {
        preferences =  this.getSharedPreferences(PREFERENCES_ENCOUNTER, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("patient", new Gson().toJson(patient));
        editor.putBoolean("edit_mode", false);
        editor.apply();

    }

}
