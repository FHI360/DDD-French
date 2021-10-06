package org.fhi360.ddd.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.fhi360.ddd.R;

import org.fhi360.ddd.activities.DefaulterListActivity;
import org.fhi360.ddd.activities.FacilityDashboard;
import org.fhi360.ddd.activities.ListDDDClient;
import org.fhi360.ddd.activities.PatientList2;
import org.fhi360.ddd.activities.ReportingPeriod1;
import org.fhi360.ddd.util.MyList;

import java.util.List;
import java.util.Random;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {
    private List<MyList> myLists;
    private Context context;

    public ReportAdapter(List<MyList> myLists, Context context) {
        this.myLists = myLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_image_one, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyList myList = myLists.get(position);
        holder.textView.setText(myList.getDesc());
        String firstLetter = String.valueOf(myList.getDesc().charAt(0));
        Random mRandom = new Random();
        ImageView circleImages = holder.circleImages.findViewById(R.id.circleImage);
        int color = Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));
        ((GradientDrawable) circleImages.getBackground()).setColor(color);
        if (myList.getDesc().equals("Dashboard")) {
//            circleImages.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.dashboard));
//            circleImages.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.dashboard));
//            holder.circleImages.setBackgroundColor(ContextCompat.getColor(context,R.color.navigationBarColorSkipped));
//            holder.cardView.setBackgroundColor(ContextCompat.getColor(context,R.color.navigationBarColorSkipped));
//            holder.textView.setTextColor(Color.WHITE);
     }


        if (myList.getDesc().equals("Routine reports")) {
//            circleImages.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.rountinreport));
//            holder.circleImages.setBackgroundColor(ContextCompat.getColor(context,R.color.colorPurple));
//            holder.cardView.setBackgroundColor(ContextCompat.getColor(context,R.color.colorPurple));
//            holder.textView.setTextColor(Color.WHITE);
        }
        if (myList.getDesc().equals("List of registered DDD outlets")) {
//            circleImages.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.listddclient1));
//            holder.circleImages.setBackgroundColor(ContextCompat.getColor(context,R.color.lightGreen));
//            holder.cardView.setBackgroundColor(ContextCompat.getColor(context,R.color.lightGreen));
//            holder.textView.setTextColor(Color.WHITE);
        }
        if (myList.getDesc().equals("List of clients for DDD")) {
//            circleImages.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.listddclient));
//            holder.circleImages.setBackgroundColor(ContextCompat.getColor(context,R.color.colorAccent));
//            holder.cardView.setBackgroundColor(ContextCompat.getColor(context,R.color.colorAccent));
//            holder.textView.setTextColor(Color.WHITE);
        }

        if (myList.getDesc().equals("Defaulters List")) {
//            circleImages.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.defaulters));
//            circleImages.setBackgroundColor(ContextCompat.getColor(context,R.color.colorBackgroundshape1));
//            holder.cardView.setBackgroundColor(ContextCompat.getColor(context,R.color.colorBackgroundshape1));
//            holder.textView.setTextColor(Color.WHITE);
        }


        //circleImages.setText(firstLetter);
        //holder.img.setImageDrawable(context.getResources().getDrawable(myList.getImage()));

        switch (position) {
            case 0:
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), FacilityDashboard.class);
                        v.getContext().startActivity(intent);
                    }
                });
                break;
            case 1:
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), ReportingPeriod1.class);
                        v.getContext().startActivity(intent);
                    }
                });
                break;

            case 2:
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), ListDDDClient.class);
                        v.getContext().startActivity(intent);
                    }
                });
                break;
            case 3:
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), PatientList2.class);
                        v.getContext().startActivity(intent);
                    }
                });
                break;




            case 4:
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), DefaulterListActivity.class);
                        v.getContext().startActivity(intent);
                    }
                });
                break;
        }

    }

    @Override
    public int getItemCount() {
        return myLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView textView;
        private CardView cardView;
        ImageView circleImages;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.desc);
            cardView = itemView.findViewById(R.id.cardView);
            circleImages = itemView.findViewById(R.id.circleImage);
        }
    }


}
