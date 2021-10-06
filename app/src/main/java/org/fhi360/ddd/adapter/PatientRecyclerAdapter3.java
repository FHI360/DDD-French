package org.fhi360.ddd.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.fhi360.ddd.Db.DDDDb;
import org.fhi360.ddd.activities.PatientHistory;
import org.fhi360.ddd.R;
import org.fhi360.ddd.domain.ARV;
import org.fhi360.ddd.domain.Patient;

import java.util.List;
import java.util.Random;

import static org.fhi360.ddd.util.Constants.PREFERENCES_ENCOUNTER;


public class PatientRecyclerAdapter3 extends RecyclerView.Adapter<PatientRecyclerAdapter3.ViewHolder> {
    //These variables will hold the data for the views
    private List<Patient> patientList;
    private Context context;
    ARVListAdapter2 patientRecyclerAdapter;
    RecyclerView recyclerViewHts;
    List<ARV> arvList;

    //Provide a reference to views used in the recycler view. Each ViewHolder will display a CardView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    //Pass data to the adapter in its constructor
    public PatientRecyclerAdapter3(List<Patient> patientList, Context context) {
        this.patientList = patientList;
        this.context = context;

    }

    //Create a new view and specify what layout to use for the contents of the ViewHolder
    @NonNull
    @Override
    public PatientRecyclerAdapter3.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_report, parent, false);
        return new ViewHolder(cardView);
    }

    //Set the values inside the given view.
    //This method get called whenever the recyler view needs to display data the in a view holder
    //It takes two parameters: the view holder that data needs to be bound to and the position in the data set of the data that needs to be bound.
    //Declare variable position as final because we are referencing it in the inner class View.OnClickListener
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        CardView cardView = holder.cardView;
        String firstLettersurname = String.valueOf(patientList.get(position).getUniqueId().charAt(0));
        Random mRandom = new Random();
        TextView circleImages = cardView.findViewById(R.id.circleImage);
        int color = Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));
        ((GradientDrawable) circleImages.getBackground()).setColor(color);
        circleImages.setText(firstLettersurname);

        TextView profileView = cardView.findViewById(R.id.patient_profile);

        String surname = patientList.get(position).getUniqueId();

        String fullSurname = firstLettersurname.toUpperCase() + surname.substring(1).toLowerCase();

        // String fullOtherName = firstLettersOtherName.toUpperCase() + otherNames.substring(1).toLowerCase();

        String clientName = "<font color='#000'>" + fullSurname + "</font>";

        profileView.setText(Html.fromHtml(clientName), TextView.BufferType.SPANNABLE);

        TextView facilityView = cardView.findViewById(R.id.facility_name);
        TextView dateRegistration = cardView.findViewById(R.id.dateText);
        dateRegistration.setText(patientList.get(position).getDateNextRefill());
        // TextView starIcon = cardView.findViewById(R.id.starIcon);

//        if (patientList.get(position).getStatus()==0) {
//            TextDrawable drawable = TextDrawable.builder()
//                    .buildRound("Manual", Color.RED);
//            starIcon.setImageDrawable(drawable);
//        }
//        if (patientList.get(position).getStatus()==1) {
//            TextDrawable drawable = TextDrawable.builder()
//                    .buildRound("Server", Color.BLUE);
//            starIcon.setImageDrawable(drawable);
//        }
        //  System.out.println("FACILITYID "+patientList.get(position).getFacilityId());
        //   System.out.println("DATEBITH"+patientList.get(position).getDateBirth());
        String facilityName = DDDDb.getInstance(context).facilityRepository().findOne(patientList.get(position).getFacilityId()).getName();
        facilityView.setText(facilityName);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences(patientList.get(position));
                Intent intent = new Intent(context, PatientHistory.class);
                context.startActivity(intent);
            }
        });
    }


    @SuppressLint("ApplySharedPref")
    private void savePreferences(Patient patient) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_ENCOUNTER, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("patient", new Gson().toJson(patient));
        editor.putBoolean("edit_mode", false);
        editor.commit();
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }


}
