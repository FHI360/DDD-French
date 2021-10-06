//package org.fhi360.ddd.adapter;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.graphics.drawable.GradientDrawable;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.cardview.widget.CardView;
//import androidx.core.content.ContextCompat;
//import androidx.recyclerview.widget.RecyclerView;
//import org.fhi360.ddd.R;
//import org.fhi360.ddd.util.MyList;
//
//import java.util.List;
//import java.util.Random;
//
//public class NewPatientAdapter extends RecyclerView.Adapter<NewPatientAdapter.ViewHolder> {
//    private List<MyList> myLists;
//    private Context context;
//
//    public NewPatientAdapter(List<MyList> myLists, Context context) {
//        this.myLists = myLists;
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_image_one, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        MyList myList = myLists.get(position);
//        holder.textView.setText(myList.getDesc());
//    //    String descIstLetter = String.valueOf(myLists.get(position).getDesc().charAt(0));
//        Random mRandom = new Random();
//        int color = Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));
//        ((GradientDrawable) holder.circleImages.getBackground()).setColor(color);
//        if (myList.getDesc().equals("Check list of assigned clients")) {
//            holder.circleImages.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.dashboard));
//            holder.circleImages.setBackgroundColor(ContextCompat.getColor(context, R.color.navigationBarColorSkipped));
//            holder.cardView.setBackgroundColor(ContextCompat.getColor(context, R.color.navigationBarColorSkipped));
//            holder.textView.setTextColor(Color.WHITE);
//        }
//
//
////        switch (position) {
////            case 0:
////                holder.cardView.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        Intent intent = new Intent(v.getContext(), Activation.class);
////                        v.getContext().startActivity(intent);
////                    }
////                });
////                break;
////
////            case 1:
////                holder.cardView.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        Intent intent = new Intent(v.getContext(), Activation.class);
////                        v.getContext().startActivity(intent);
////                    }
////                });
////                break;
//        }
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return myLists.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        private ImageView img;
//        private TextView textView;
//        private CardView cardView;
//        ImageView circleImages;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            img = itemView.findViewById(R.id.image);
//            textView = itemView.findViewById(R.id.desc);
//            cardView = itemView.findViewById(R.id.cardView);
//            circleImages = itemView.findViewById(R.id.circleImage);
//        }
//    }
//}
