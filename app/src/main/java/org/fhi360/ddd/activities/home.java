//package org.fhi360.ddd;
//
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.github.clans.fab.FloatingActionButton;
//import com.github.clans.fab.FloatingActionMenu;
//
//import com.mlsdev.animatedrv.AnimatedRecyclerView;
//
//import org.fhi360.ddd.util.MyList;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class home extends AppCompatActivity {
//    private List<MyList> myLists;
//    private TestAdapter adapter;
//    private RecyclerView recyclerView;
//    private FloatingActionMenu materialDesignFAM;
//    private FloatingActionButton home;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.splash_screen);
//        // Objects.requireNonNull(getSupportActionBar()).setTitle("Home Page");
//        recyclerView = (AnimatedRecyclerView) findViewById(R.id.patient_recycler);
//        materialDesignFAM = findViewById(R.id.material_design_android_floating_action_menu);
//        home = findViewById(R.id.material_design_floating_action_menu_item3);
//        recyclerView = findViewById(R.id.rec);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        myLists = new ArrayList<>();
//        getdata();
//
//    }
//
//
//    private void getdata() {
//        myLists.add(new MyList("Register DDD outlet"));
//        myLists.add(new MyList("Client Registration"));
//        myLists.add(new MyList("Reports"));
//        myLists.add(new MyList("Inventory Management"));
//        myLists.add(new MyList("Synchronize"));
//        adapter = new TestAdapter(myLists, getApplicationContext());
//        recyclerView.setAdapter(adapter);
//    }
//}