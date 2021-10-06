package org.fhi360.ddd.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.fhi360.ddd.Db.DDDDb;
import org.fhi360.ddd.R;
import org.fhi360.ddd.domain.Patient;
import org.fhi360.ddd.domain.RegimenHistory;

import java.util.List;


public class TableViewAdapter extends RecyclerView.Adapter {
    private List<RegimenHistory> arvList;
    private Context context;


    public TableViewAdapter(Context context, List<RegimenHistory> arvList) {
        this.context = context;
        this.arvList = arvList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.table_list_item, parent, false);

        return new RowViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RowViewHolder rowViewHolder = (RowViewHolder) holder;
        int rowPos = rowViewHolder.getAdapterPosition();

        if (rowPos == 0) {
            rowViewHolder.clientName.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.facilityName.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.quantityPrescibed.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.quantity.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.regimen.setBackgroundResource(R.drawable.table_header_cell_bg);


            rowViewHolder.clientName.setText(Html.fromHtml("<font color=\"#ffffff\">" + "Patient Name" + "</font>"));
            rowViewHolder.facilityName.setText(Html.fromHtml("<font color=\"#ffffff\">" + "facility Name" + "</font>"));
            rowViewHolder.quantityPrescibed.setText(Html.fromHtml("<font color=\"#ffffff\">" + "Dispensed Medication" + "</font>"));
            rowViewHolder.regimen.setText(Html.fromHtml("<font color=\"#ffffff\">" + "Regimen" + "</font>"));
            rowViewHolder.quantity.setText(Html.fromHtml("<font color=\"#ffffff\">" + "Quantity" + "</font>"));


        } else {
            final RegimenHistory modal = arvList.get(rowPos - 1);
            rowViewHolder.clientName.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.facilityName.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.quantityPrescibed.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.regimen.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.quantity.setBackgroundResource(R.drawable.table_content_cell_bg);

            Patient patient = DDDDb.getInstance(context).patientRepository().findOne(modal.getId());
            System.out.println("PATIENTs " + patient);
            String firstLettersurname = String.valueOf(patient.getSurname().charAt(0));
            String surname = patient.getSurname();
            String otherNames = patient.getOtherNames();
            String firstLettersOtherName = String.valueOf(patient.getOtherNames().charAt(0));

            String fullSurname = firstLettersurname.toUpperCase() + surname.substring(1).toLowerCase();

            String fullOtherName = firstLettersOtherName.toUpperCase() + otherNames.substring(1).toLowerCase();

            String clientName = "<font color='#000'>" + fullSurname + "</font> &nbsp &nbsp" + "<font color='#7F7F7F'>" + fullOtherName + "</font>";

            rowViewHolder.clientName.setText(Html.fromHtml(clientName));
            String facilityName = DDDDb.getInstance(context).facilityRepository().findOne(patient.getFacilityId()).getName();

            rowViewHolder.facilityName.setText(facilityName);
            rowViewHolder.quantityPrescibed.setText(String.valueOf(modal.getQuantityDispensed()));
            rowViewHolder.quantity.setText(String.valueOf(modal.getQuantityPrescribed()));
            rowViewHolder.regimen.setText(modal.getRegimen());

        }
    }

    @Override
    public int getItemCount() {
        return arvList.size() + 1;
    }

    public class RowViewHolder extends RecyclerView.ViewHolder {
        protected TextView clientName;
        protected TextView facilityName;
        protected TextView quantityPrescibed;
        private TextView regimen;
        private TextView quantity;
        protected LinearLayout relarive_container;

        public RowViewHolder(View itemView) {
            super(itemView);
            clientName = itemView.findViewById(R.id.patient_profile);
            facilityName = itemView.findViewById(R.id.facility_name);
            quantityPrescibed = itemView.findViewById(R.id.quantityPrescibed);
            quantity = itemView.findViewById(R.id.quantity);
            regimen = itemView.findViewById(R.id.regimen1);
            relarive_container = itemView.findViewById(R.id.relarive_container);


        }

    }
}
