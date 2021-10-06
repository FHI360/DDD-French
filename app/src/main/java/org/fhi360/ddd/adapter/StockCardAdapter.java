package org.fhi360.ddd.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.fhi360.ddd.Db.DDDDb;
import org.fhi360.ddd.R;
import org.fhi360.ddd.domain.ARV;
import org.fhi360.ddd.domain.Patient;
import org.fhi360.ddd.util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.fhi360.ddd.util.Constants.PREFERENCES_ENCOUNTER;


public class StockCardAdapter extends RecyclerView.Adapter<StockCardAdapter.ViewHolder> {
    //These variables will hold the data for the views
    private List<ARV> arvList;
    private Context context;


    //Provide a reference to views used in the recycler view. Each ViewHolder will display a CardView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    //Pass data to the adapter in its constructor
    public StockCardAdapter(List<ARV> patientList, Context context) {
        this.arvList = patientList;
        this.context = context;

    }

    //Create a new view and specify what layout to use for the contents of the ViewHolder
    @NonNull
    @Override
    public StockCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_stock_card, parent, false);
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

        TextView stockacrd = cardView.findViewById(R.id.stockacrd);
        TextView numberpatientbyregimen = cardView.findViewById(R.id.numberpatientbyregimen);
        TextView noOfPatientByDSD = cardView.findViewById(R.id.noOfPatientByDSD);

        //stockacrd.setText(arvList.get(position).);
        int countNumberPatientByRegimen = DDDDb.getInstance(context).arvRefillRepository().count(arvList.get(position).getId());
        numberpatientbyregimen.setText(countNumberPatientByRegimen);

//        cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                savePreferences(patientList.get(position));
//                showAlert(patientList.get(position));
//
//            }
//        });
    }


    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    public void showAlert(Patient patient) {
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.activity_daily_register, null);
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setView(promptsView);
        final TextView cancel_action;
        cancel_action = promptsView.findViewById(R.id.cancel_action);
        ImageView avarter = promptsView.findViewById(R.id.avarter);

        cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        if (patient.getGender().equals("Female")) {
            avarter.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.femaleavataricon));
        } else {
            avarter.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.male));
        }

        int age = 0;
        try {
            System.out.println("DATEBIRT " + patient.getDateBirth());
            age = DateUtil.getAge(patient.getDateBirth());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("NAME " + patient.getSurname());
        if (patient.getGender().equals("Female")) {
            avarter.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.femaleavataricon));
        } else {
            avarter.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.male));
        }
        String firstLetterer = String.valueOf(patient.getSurname().charAt(0));
        String fullSurname = firstLetterer.toUpperCase() + patient.getSurname().substring(1).toLowerCase();
        String firstLettersOtherName = String.valueOf(patient.getOtherNames().charAt(0));
        String fullOtherName = firstLettersOtherName.toUpperCase() + patient.getOtherNames().substring(1).toLowerCase();
        String clientName = "<font size ='30' color='#e0e0e0'><big><b>" + fullSurname + "</font></b></big> &nbsp &nbsp" + "<font color='#e0e0e0'>" + fullOtherName + "</font>";
        ((TextView) promptsView.findViewById(R.id.name)).setText(Html.fromHtml(clientName));
        ARV arv = DDDDb.getInstance(context).arvRefillRepository().findByPatientId1(patient.getId());

        ((TextView) promptsView.findViewById(R.id.patientNumber)).setText(Html.fromHtml("<font size ='20' color='#000'><big>" + "Patient No" + "</big></font> " + "<small>" + "&#160;&#160;" + patient.getId() + "</small>"));
        ((TextView) promptsView.findViewById(R.id.contactNumber)).setText(Html.fromHtml("<font size ='20' color='#000'><big>" + "Contact(Tel)" + "</big></font> " + "<small>" + "&#160;&#160; " + (patient.getPhone()) + "</small>"));
        ((TextView) promptsView.findViewById(R.id.referring_hf)).setText(Html.fromHtml("<font size ='20' color='#000'><big>" + "Referring HF" + "</big></font>  " + "<small>" + "&#160;&#160;" + patient.getUniqueId() + "</small>"));
        if (arv != null) {
            ((TextView) promptsView.findViewById(R.id.regimen1)).setText(Html.fromHtml("<font size ='20' color='#000'><big>" + "Regimen" + "</big></font> " + "<small>" + arv.getRegimen1() + "</small>"));
        } else {
            ((TextView) promptsView.findViewById(R.id.regimen1)).setText(Html.fromHtml("<font size ='20' color='#000'><big>" + "Regimen" + "</big></font> " + "<small>" + "" + "</small>"));

        }
        dialog.setCancelable(false);
        dialog.show();
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
        return arvList.size();
    }


}
