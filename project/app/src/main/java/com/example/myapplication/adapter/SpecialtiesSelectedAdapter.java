package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.graph.GlobalInfo;

import java.util.ArrayList;

public class SpecialtiesSelectedAdapter extends RecyclerView.Adapter<SpecialtiesSelectedAdapter.ViewHolder> {
    private ArrayList<String> specialties;

    public SpecialtiesSelectedAdapter(ArrayList<String> specialties) {
        this.specialties = specialties;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View listItem = layoutInflater.inflate(R.layout.specialities_selected_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String string = specialties.get(position);
        holder.string.setText(string);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.imageView.setVisibility(View.VISIBLE);
                holder.imageView.setEnabled(false);
                for (int i = 0; i < GlobalInfo.getSpecialities().size(); i++) {
                    if (!GlobalInfo.getSpecialities().get(i).equals(string)) {
                        GlobalInfo.getSpecialities().add(string);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return specialties.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView string;
        public ImageView imageView;
        public ConstraintLayout constraintLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.string = (TextView) itemView.findViewById(R.id.speciality);
            this.imageView = (ImageView) itemView.findViewById(R.id.selected_imageView);
            this.constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.select_item);
        }
    }
}
