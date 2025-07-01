package com.example.sportdoctorfollow;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.sportdoctorfollow.R;

import java.util.ArrayList;


public class RecyclerAdapter  extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {


    private Context context;
    private int[] imageExams;
    private String[] exams;
    public RecyclerAdapter(Context context,int[] imageExams,String[] exams){
        this.context=context;
        this.imageExams=imageExams;
        this.exams=exams;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_recycler_exams,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.text_name.setText(exams[position]);
        viewHolder.image_item.setImageResource(imageExams[position]);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });
    }

    @Override
    public int getItemCount() {
        return exams.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image_item;
        TextView text_name;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_item=itemView.findViewById(R.id.item_image);
            text_name=itemView.findViewById(R.id.item_name);

        }
    }
}
