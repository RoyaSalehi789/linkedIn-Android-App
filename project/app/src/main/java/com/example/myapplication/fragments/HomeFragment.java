package com.example.myapplication.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.myapplication.adapter.PeopleAdapter;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.graph.GlobalInfo;
import com.example.myapplication.graph.Person;

import org.json.JSONException;

import java.io.IOException;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        try {
            PeopleAdapter peopleAdapter = new PeopleAdapter(GlobalInfo.getPeople(requireContext()).getPeople());
            binding.recyclerView.setHasFixedSize(true);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            binding.recyclerView.setAdapter(peopleAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        binding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String search = binding.search.getQuery().toString();
                try {
                    Person person = GlobalInfo.getPeople(requireContext()).getPerson(Integer.parseInt(search));
                    if (person == null) {
                        Toast.makeText(requireContext(), "The user not found", Toast.LENGTH_SHORT).show();
                    } else {
                        binding.result.setVisibility(View.VISIBLE);
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.resultName.setText(person.getName());
                        binding.resultId.setText(String.valueOf(person.getId()));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return  binding.getRoot();
    }
}