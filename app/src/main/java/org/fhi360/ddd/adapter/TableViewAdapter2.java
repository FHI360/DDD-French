package org.fhi360.ddd.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.fhi360.ddd.R;
import org.fhi360.ddd.domain.RegimenHistory;

import java.util.List;


public class TableViewAdapter2 extends RecyclerView.Adapter {
    private List<RegimenHistory> arvList;
    private Context context;

    public TableViewAdapter2(Context context, List<RegimenHistory> arvList) {
        this.context = context;
        this.arvList = arvList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.listview_row, parent, false);
        return new RowViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RowViewHolder rowViewHolder = (RowViewHolder) holder;
        int rowPos = rowViewHolder.getAdapterPosition();
        if (rowPos == 0) {
            rowViewHolder.datePickUp.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.regimen.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.qtyregimen.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.date_next_clinic_visit.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.datePickUp.setTextColor(Color.parseColor("#ffffff"));
            rowViewHolder.datePickUp.setText("Date Visit");
            rowViewHolder.regimen.setTextColor(Color.parseColor("#ffffff"));
            rowViewHolder.regimen.setText("Regimen");
            rowViewHolder.qtyregimen.setTextColor(Color.parseColor("#ffffff"));
            rowViewHolder.qtyregimen.setText("Quantity Dispensed");
            rowViewHolder.date_next_clinic_visit.setText("Date Next Refill");
            rowViewHolder.date_next_clinic_visit.setTextColor(Color.parseColor("#ffffff"));

        } else {
            final RegimenHistory modal = arvList.get(rowPos - 1);
            rowViewHolder.datePickUp.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.regimen.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.qtyregimen.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.date_next_clinic_visit.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.datePickUp.setText(Html.fromHtml("<<big>" + modal.getDateVisit() + "</big>"));
            rowViewHolder.regimen.setText(Html.fromHtml("<<big>" + modal.getRegimen() + "</big>"));
            rowViewHolder.qtyregimen.setText(Html.fromHtml("<<big>" + modal.getQuantityDispensed() + "</big>"));
            rowViewHolder.date_next_clinic_visit.setText(Html.fromHtml("<<big>" + modal.getDateNextRefill() + "</big>"));
        }
    }

    @Override
    public int getItemCount() {
        return arvList.size() + 1;
    }

    public class RowViewHolder extends RecyclerView.ViewHolder {
        protected TextView datePickUp;
        protected TextView regimen;
        protected TextView qtyregimen;
        protected TextView date_next_clinic_visit;
        protected LinearLayout relarive_container;

        public RowViewHolder(View itemView) {
            super(itemView);
            datePickUp = itemView.findViewById(R.id.date_of_pick_up);
            regimen = itemView.findViewById(R.id.regimen);
            qtyregimen = itemView.findViewById(R.id.qtyregimen);
            date_next_clinic_visit = itemView.findViewById(R.id.date_next_clinic_visit);
            relarive_container = itemView.findViewById(R.id.relarive_container);

        }

    }
}
