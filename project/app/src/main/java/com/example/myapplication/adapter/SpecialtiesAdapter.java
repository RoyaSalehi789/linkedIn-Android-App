package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.graph.GlobalInfo;
import com.example.myapplication.graph.Person;

import java.util.ArrayList;

public class SpecialtiesAdapter extends RecyclerView.Adapter<SpecialtiesAdapter.ViewHolder> {
    private ArrayList<String> specialties;

    public SpecialtiesAdapter(ArrayList<String> specialties) {
        this.specialties = specialties;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View listItem = layoutInflater.inflate(R.layout.specialities_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String string = specialties.get(position);
        holder.string.setText(string);
    }

    @Override
    public int getItemCount() {
        return specialties.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView string;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.string = (TextView) itemView.findViewById(R.id.sp);
        }
    }
}
