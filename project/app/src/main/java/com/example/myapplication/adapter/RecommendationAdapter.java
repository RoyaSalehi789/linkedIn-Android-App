package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.HomeActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.ProfileActivity;
import com.example.myapplication.R;
import com.example.myapplication.graph.GlobalInfo;
import com.example.myapplication.graph.Person;

import java.util.ArrayList;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.ViewHolder> {
    private ArrayList<Pair<Person, Integer>> peopleList;
    Context context;

    public RecommendationAdapter(ArrayList<Pair<Person, Integer>> peopleList, Context context) {
        this.peopleList = peopleList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View listItem = layoutInflater.inflate(R.layout.person_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Person person = peopleList.get(position).first;
        holder.name.setText(person.getName());
        holder.field.setText(String.valueOf(person.getId()));
        holder.item_poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalInfo.setOther(person);
                Intent switchActivity = new Intent(context, ProfileActivity.class);
                context.startActivity(switchActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return peopleList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView field;
        public ConstraintLayout item_poster;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.field = (TextView) itemView.findViewById(R.id.field);
            item_poster = (ConstraintLayout) itemView.findViewById(R.id.item_poster);
        }
    }
}
