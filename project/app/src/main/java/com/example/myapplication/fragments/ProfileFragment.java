package com.example.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.HomeActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.adapter.PeopleAdapter;
import com.example.myapplication.adapter.RecommendationAdapter;
import com.example.myapplication.adapter.SpecialtiesAdapter;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.databinding.FragmentProfileBinding;
import com.example.myapplication.graph.GlobalInfo;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        binding.dateOfBirth1.setText(GlobalInfo.getMe().getDateOfBirth());
        binding.field1.setText(GlobalInfo.getMe().getField());
        binding.id1.setText(String.valueOf(GlobalInfo.getMe().getId()));
        binding.name1.setText(GlobalInfo.getMe().getName());
        binding.university1.setText(GlobalInfo.getMe().getUniversityLocation());
        binding.workplace1.setText(GlobalInfo.getMe().getWorkplace());
        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchActivity = new Intent(requireContext(), MainActivity.class);
                startActivity(switchActivity);
            }
        });

        SpecialtiesAdapter specialtiesAdapter = new SpecialtiesAdapter(GlobalInfo.getMe().getSpecialties());
        binding.specialtiesRecyclerView.setHasFixedSize(true);
        binding.specialtiesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.specialtiesRecyclerView.setAdapter(specialtiesAdapter);

        PeopleAdapter connectionsAdapter = new PeopleAdapter(GlobalInfo.getMe().getConnections(), requireContext());
        binding.connections.setHasFixedSize(true);
        binding.connections.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.connections.setAdapter(connectionsAdapter);

        return binding.getRoot();
    }
}