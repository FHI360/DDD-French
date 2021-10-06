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

import org.fhi360.ddd.R;
import org.fhi360.ddd.domain.RegimenHistory;

import java.util.List;


public class ARVListAdapter2 extends RecyclerView.Adapter<ARVListAdapter2.ViewHolder> {
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
    public ARVListAdapter2(List<RegimenHistory> arvList, Context context) {
        this.arvList = arvList;
        this.context = context;

    }

    //Create a new view and specify what layout to use for the contents of the ViewHolder
    @NonNull
    @Override
    public ARVListAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RelativeLayout cardView = (RelativeLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_patient_history, parent, false);
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
        ((TextView) cardView.findViewById(R.id.date_of_pick_up)).setText(Html.fromHtml("<font  color='#000'><big>" + "Date Visit" + "</big></font> " + "<small>" + "&#160;&#160;" + arvList.get(position).getDateVisit() + "</small>"));
        ((TextView) cardView.findViewById(R.id.regimen)).setText(Html.fromHtml("<font size ='20' color='#000'><big>" + "Regime dispensed" + "</big></font> " + "<small>" + arvList.get(position).getRegimen() + "</small>"));
        ((TextView) cardView.findViewById(R.id.qtyregimen)).setText(Html.fromHtml("<font size ='20' color='#000'><big>" + "Quantity dispensed" + "</big></font> " + "<small>" + "&#160;&#160;" + arvList.get(position).getQuantityDispensed() + "</small>"));
        ((TextView) cardView.findViewById(R.id.date_next_clinic_visit)).setText(Html.fromHtml("<font size ='20' color='#000'><big>" + "Next Refill" + "</big></font> " + "<small>" + "&#160;&#160; " + arvList.get(position).getDateNextRefill() + "</small>"));

    }


    @Override
    public int getItemCount() {
        return arvList.size();
    }


}
