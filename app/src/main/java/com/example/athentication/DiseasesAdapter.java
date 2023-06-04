package com.example.athentication;


import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DiseasesAdapter extends RecyclerView.Adapter<DiseasesAdapter.myviewholder> {

    private ArrayList<DiseaseModel> DiseasesHolder;
    private Context context;

    public DiseasesAdapter(ArrayList<DiseaseModel> DiseasesHolder, Context context) {
        this.DiseasesHolder = DiseasesHolder;
        this.context = context;
    }


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_disease,parent,false);
        return new myviewholder(view, DiseasesHolder, this, context);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {

        holder.diseaseName.setText(DiseasesHolder.get(position).getDisease());
        holder.diseaseDescription.setText(DiseasesHolder.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return DiseasesHolder.size();
    }

    public static class myviewholder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView diseaseName, diseaseDescription;
        Context context;
        ArrayList<DiseaseModel> DiseasesHolder;

        public myviewholder(@NonNull View itemView, ArrayList<DiseaseModel> DiseasesHolder, DiseasesAdapter diseasesAdapter, Context context) {
            super(itemView);
            diseaseName = itemView.findViewById(R.id.disease_name);
            diseaseDescription = itemView.findViewById(R.id.details);
            this.DiseasesHolder = DiseasesHolder;
            this.context = context;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                DiseaseModel disease = DiseasesHolder.get(position);
                Intent intent = new Intent(context, DiseaseDetailsActivity.class);
                intent.putExtra("diseaseModel", (Parcelable) disease);
                context.startActivity(intent);
            }
        }

    }
}
