package org.fhi360.ddd.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.fhi360.ddd.Db.DDDDb;
import org.fhi360.ddd.R;
import org.fhi360.ddd.domain.Facility;
import org.fhi360.ddd.domain.Patient;
import org.fhi360.ddd.domain.RegimenHistory;

import java.util.List;


public class DailyRegisterReportAdapter extends RecyclerView.Adapter<DailyRegisterReportAdapter.ViewHolder> {
    //These variables will hold the data for the views
    private List<RegimenHistory> arvList;
    private Context context;

    //Provide a reference to views used in the recycler view. Each ViewHolder will display a CardView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout cardView;

        public ViewHolder(RelativeLayout v) {
            super(v);
            cardView = v;
        }
    }

    //Pass data to the adapter in its constructor
    public DailyRegisterReportAdapter(List<RegimenHistory> arvList, Context context) {
        this.arvList = arvList;
        this.context = context;

    }

    //Create a new view and specify what layout to use for the contents of the ViewHolder
    @NonNull
    @Override
    public DailyRegisterReportAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RelativeLayout cardView = (RelativeLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_daily_register, parent, false);
        return new ViewHolder(cardView);
    }

    //Set the values inside the given view.
    //This method get called whenever the recyler view needs to display data the in a view holder
    //It takes two parameters: the view holder that data needs to be bound to and the position in the data set of the data that needs to be bound.
    //Declare variable position as final because we are referencing it in the inner class View.OnClickListener
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        RelativeLayout cardView = holder.cardView;
        Patient patient = DDDDb.getInstance(context).patientRepository().findOne(arvList.get(position).getId());
        String firstLettersurname = String.valueOf(patient.getSurname().charAt(0));
        String surname = patient.getSurname();
        String otherNames = patient.getOtherNames();
        String firstLettersOtherName = String.valueOf(patient.getOtherNames().charAt(0));

        String fullSurname = firstLettersurname.toUpperCase() + surname.substring(1).toLowerCase();

        String fullOtherName = firstLettersOtherName.toUpperCase() + otherNames.substring(1).toLowerCase();

        String clientName = "<font color='#000'>" + fullSurname + "</font> &nbsp &nbsp" + "<font color='#7F7F7F'>" + fullOtherName + "</font>";

        ((TextView) cardView.findViewById(R.id.patient_profile)).setText(Html.fromHtml(clientName));
        String facilityName = DDDDb.getInstance(context).facilityRepository().findOne(patient.getFacilityId()).getName();
        ((TextView) cardView.findViewById(R.id.facility_name)).setText(Html.fromHtml("<font  color='#000'><big>" + "Facility Name" + "</big></font> " + "<small>" + "&#160;&#160;" + facilityName + "</small>"));
        //    ARV arv = DDDDb.getInstance(context).arvRefillRepository().findByPatientId1(patient.getId());
        ((TextView) cardView.findViewById(R.id.patientNumber)).setText(Html.fromHtml("<font size ='20' color='#000'><big>" + "Patient  No" + "</big></font> " + "<small>" + "&#160;&#160;" + patient.getId() + "</small>"));
        ((TextView) cardView.findViewById(R.id.contactNumber)).setText(Html.fromHtml("<font size ='20' color='#000'><big>" + "Contact(Tel)" + "</big></font> " + "<small>" + "&#160;&#160; " + (patient.getPhone()) + "</small>"));
        ((TextView) cardView.findViewById(R.id.referring_hf)).setText(Html.fromHtml("<font size ='20' color='#000'><big>" + "Referring HF" + "</big></font>  " + "<small>" + "&#160;&#160;" + patient.getUniqueId() + "</small>"));
        ((TextView) cardView.findViewById(R.id.regimen1)).setText(Html.fromHtml("<font size ='20' color='#000'><big>" + "Patient Regimen" + "</big></font> " + "<small>" + arvList.get(position).getRegimen() + "</small>"));

    }


    @Override
    public int getItemCount() {
        return arvList.size();
    }


}
